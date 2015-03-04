package com.dasixes.spritestable;
//TODO: HouseRule: Track matrix action usage, sort by frequency
//TODO: Used Edge isn't updating correctly
//TODO: Add Hot-Sim checkbox, +2 to technomancer rolls when checked

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MatrixFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();
    int diePoolMod=0;
    int limitMod=0;


    // Die modifier spinner, Limit Modifier Spinner

    // Action Name, Total Dice, Total/Total/Total assisting dice, Limit(Assisted Limit),
    // Opposed dice, Marks Needed, Action Type,
    // Dropdown of lead actor default to character Multi-dropdown of valid assistors
    //Roll Assist button
    //Auto calculate Total dice and Assistant dice based on totals/limits
    //Expend services checkbox, default to checked

    //Two rows per action?
    //Spinner to pick action?

    public void setDieMod(Integer mod){
        diePoolMod=mod;
        Button btnMAViewDiePool = (Button) getActivity().findViewById(R.id.btnMAViewDiePool);
        btnMAViewDiePool.setText("Die Pool Mod: " + mod);
    }
    public void setLimitMod(Integer mod){
        limitMod=mod;
        Button btnMAViewLimitMod = (Button) getActivity().findViewById(R.id.btnMAViewLimitMod);
        btnMAViewLimitMod.setText("Limit Modifier: " + mod);
    }
    @Override
    public void onPause() {
        //Log.e("DEBUG", "onResume of CompileFragment");
        super.onPause();
        Main.data.SaveAllToDB();
    }
public void UpdateAssistance(){
    //Loop through all actions
    Spinner spLeader= (Spinner) getActivity().findViewById(R.id.spLeader);
    MultiSelectionSpinner spAssistance= (MultiSelectionSpinner) getActivity().findViewById(R.id.spAssistance);
    CheckBox checkEdge = (CheckBox) getActivity().findViewById(R.id.CheckEdge);
    Button secondChance = (Button) getActivity().findViewById(R.id.SecondChance);

    checkEdge.setChecked(false);




    List<Sprite> tempSelectedAssistants = new ArrayList<Sprite>(Main.data.pvSprites);
    List<Sprite> activeAssistants= new ArrayList<Sprite>();
    boolean includeTechnomancer=false;
  /*  if(spLeader.getSelectedItemPosition()!=Main.data.pvSprites.size()) {//Lead by technomancer

    }else {//Add the technomancer, remove the leader
    }
    */
    if(spLeader.getSelectedItemPosition()!=tempSelectedAssistants.size()) {
        tempSelectedAssistants.remove(spLeader.getSelectedItemPosition());
    }


    for(int i: spAssistance.getSelectedIndicies()){
        if(i==(spLeader.getCount()-2) && spLeader.getSelectedItemPosition()!=Main.data.pvSprites.size() ){
            includeTechnomancer=true;
        }else{
            activeAssistants.add(tempSelectedAssistants.get(i));
        }
    }

    String assistantDice = "";
    int actionLimit=0;
    int actionDice=0;
    int spriteType=0;
    int spriteRating =0;


    for(MatrixActions ma :Main.data.pvMatrixActions){


        assistantDice="";
        //Loop through assistants for each action
        for(Sprite assistantSprite: activeAssistants){
            spriteType = assistantSprite.getSpriteType();
            spriteRating = assistantSprite.getRating();
            actionLimit=GetLimit(ma.getLimitType(),spriteType, spriteRating);
            actionDice=GetSpriteActionDice(spriteType, spriteRating, ma.getActionName());
            if(actionDice>=1){//Only add if it can help
                if(assistantDice.length()>1){
                    assistantDice+=", ";
                }
                assistantDice+="("+actionDice+")["+actionLimit+"]";
            }
        }
        if(includeTechnomancer){
            actionDice= Main.data.getDice(ma.getLinkedAttribute(),ma.getLinkedSkill(),ma.getActionName());
            if(actionDice>0) {
                if (assistantDice.length() > 1) {
                    assistantDice += ", ";
                }

                actionLimit = GetLimit(ma.getLimitType());
                assistantDice += "(" + actionDice + ")[" + actionLimit + "]";
            }
        }
        if(assistantDice.length()==0){assistantDice="nothing";};
        LinearLayout tableMatrix = (LinearLayout) getActivity().findViewById(R.id.tableMatrix);
        LinearLayout currentRow = (LinearLayout) tableMatrix.findViewWithTag("Row" + ma.getActionName());
        Button btnAssist = (Button) currentRow.findViewWithTag("Assist" + ma.getActionName());
        btnAssist.setText("Roll " + assistantDice +" to assist.");
        btnAssist.setTextColor(Color.BLACK);
        btnAssist.setClickable(true);
        Main.data.setMatrixActionAssistDice(ma.getActionName(), 0);
        Main.data.setMatrixActionAssistLimit(ma.getActionName(), 0);

    }
}
    public int GetLimit(int limitType){
        switch (limitType){
            case 1:
                return Main.data.getStatValue("Charisma");
            case 2:
                return Main.data.getStatValue("Intuition");
            case 3:
                return Main.data.getStatValue("Logic");
            case 4:
                return Main.data.getStatValue("Willpower");
            default:
                return Main.data.getStatValue("Resonance");
        }
    }
    public int GetLimit(int limitType, int spriteType, int spriteRating){
        switch (spriteType){
            case 1:
                switch (limitType){
                    case 1:
                        return spriteRating;
                    case 2:
                        return spriteRating+3;
                    case 3:
                        return spriteRating+1;
                    case 4:
                        return spriteRating+2;
                }
            case 2:
                switch (limitType){
                    case 1:
                        return spriteRating;
                    case 2:
                        return spriteRating+3;
                    case 3:
                        return spriteRating+2;
                    case 4:
                        return spriteRating+1;
                }
            case 3:
                switch (limitType){
                    case 1:
                        return spriteRating-1;
                    case 2:
                        return spriteRating;
                    case 3:
                        return spriteRating+4;
                    case 4:
                        return spriteRating+1;
                }
            case 4:
                switch (limitType){
                    case 1:
                        return spriteRating+3;
                    case 2:
                        return spriteRating;
                    case 3:
                        return spriteRating+1;
                    case 4:
                        return spriteRating+2;
                }
            case 5:
                switch (limitType){
                    case 1:
                        return spriteRating+1;
                    case 2:
                        return spriteRating;
                    case 3:
                        return spriteRating+3;
                    case 4:
                        return spriteRating+2;
                }
    }
        return spriteRating;
    }
public void UpdateLeader(boolean isTechnomancer){

    Spinner spLeader= (Spinner) getActivity().findViewById(R.id.spLeader);
    MultiSelectionSpinner spAssistance= (MultiSelectionSpinner) getActivity().findViewById(R.id.spAssistance);

    //Remove selected Leader
    List<String> assistants= new ArrayList<String>(Main.data.pvSpriteList);
    if(!isTechnomancer){
        assistants.add("Technomancer");
        assistants.remove(spLeader.getSelectedItem());


    }
    for(MatrixActions ma :Main.data.pvMatrixActions){
        LinearLayout tableMatrix = (LinearLayout) getActivity().findViewById(R.id.tableMatrix);
        LinearLayout currentRow = (LinearLayout) tableMatrix.findViewWithTag("Row" + ma.getActionName());
        Button btnAction = (Button) currentRow.findViewWithTag("Action" + ma.getActionName());
        Button btnAssist = (Button) currentRow.findViewWithTag("Assist" + ma.getActionName());
        int actiondice;
        int actionLimit;
        if(isTechnomancer){
            currentRow.setEnabled(true);
            currentRow.setVisibility(View.VISIBLE);
            actiondice= Main.data.getDice(ma.getLinkedAttribute(),ma.getLinkedSkill(),ma.getActionName());
            actionLimit=GetLimit(ma.getLimitType());
        }else{
            int spriteType = Main.data.pvSprites.get(spLeader.getSelectedItemPosition()).getSpriteType();
            int spriteRating = Main.data.pvSprites.get(spLeader.getSelectedItemPosition()).getSpriteType();
            actionLimit=GetLimit(ma.getLimitType(),spriteType, spriteRating);

            actiondice=GetSpriteActionDice(spriteType, spriteRating, ma.getActionName());
            if(actiondice==-1){
                currentRow.setEnabled(false);
                currentRow.setVisibility(View.INVISIBLE);
            }else{
                currentRow.setEnabled(true);
                currentRow.setVisibility(View.VISIBLE);
            }
        }
        if(actiondice<=0){
            btnAction.setText(ma.getActionName() + " No Roll");
        }else {
            btnAction.setText(ma.getActionName() + " (" + actiondice + ")[" + actionLimit + "]");
        }
    }
    String[] arrayAssistance = new String[Main.data.pvSpriteList.size()];
    arrayAssistance=assistants.toArray(arrayAssistance);
    spAssistance.setItems(arrayAssistance);

}

    public int GetSpriteSkill(int spriteType, int spriteRating, String skill){
        switch (spriteType){
            case 1:
                if(skill.equals("Computer")||skill.equals("Hacking")){return spriteRating*2;}
                break;
            case 2:
                if(skill.equals("Computer")||skill.equals("Hacking")||skill.equals("Electronic Warfare")){return spriteRating*2;}
                break;
            case 3:
                if(skill.equals("Computer")||skill.equals("Electronic Warfare")){return spriteRating*2;}
                break;
            case 4:
                if(skill.equals("Computer")||skill.equals("Hacking")||skill.equals("Cybercombat")){return spriteRating*2;}
                break;
            case 5:
                if(skill.equals("Computer")||skill.equals("Hacking")||skill.equals("Electronic Warfare")){return spriteRating*2;}
                break;
            default:
                return -1;
        }
        if(skill.equals("Software")||skill.equals("Hardware")||skill.equals("Electronic Warfare")){return -1;}
        return spriteRating-1;
    }

public int GetSpriteActionDice(int spriteType, int spriteRating, String actionName){
    //TODO: Add Houserule: Can Sprites Default?
    //TODO: Add Houserule: Deduce Sprite Attributes or Use Resonance for Sprite Skills?

    switch (actionName) {
        case "BRUTE FORCE":
            return GetSpriteSkill(spriteType, spriteRating,"Cybercombat");

        case "CHANGE ICON":
            return 0;

        case "CHECK OVERWATCH SCORE":
            return GetSpriteSkill(spriteType, spriteRating,"Electronic Warfare");

        case "CONTROL DEVICE":
            return GetSpriteSkill(spriteType, spriteRating,"Electronic Warfare");

        case "CRACK FILE":
            return GetSpriteSkill(spriteType, spriteRating, "Hacking");

        case "CRASH PROGRAM":
            return GetSpriteSkill(spriteType, spriteRating,"Cybercombat");

        case "DATA SPIKE":
            return GetSpriteSkill(spriteType, spriteRating,"Cybercombat");

        case "DISARM DATA BOMB":
            return GetSpriteSkill(spriteType, spriteRating, "Software");

        case "EDIT FILE":
            return GetSpriteSkill(spriteType, spriteRating,"Computer");

        case "ENTER/EXIT HOST":
            return 0;

        case "ERASE MARK":
            return GetSpriteSkill(spriteType, spriteRating, "Computer");


        case "ERASE MATRIX SIGNATURE":
            return GetSpriteSkill(spriteType, spriteRating,"Computer");

        case "FORMAT DEVICE":
            return GetSpriteSkill(spriteType, spriteRating, "Computer");

        case "FULL MATRIX DEFENSE":
            return 0;

        case "GRID HOP":
            return 0;

        case "HACK ON THE FLY":
            return GetSpriteSkill(spriteType, spriteRating, "Hacking");

        case "HIDE":
            return GetSpriteSkill(spriteType, spriteRating, "Electronic Warfare");

        case "INVITE MARK":
           return 0;

        case "JACK OUT":
            return -1;

        case "JAM SIGNALS":
            return GetSpriteSkill(spriteType, spriteRating,"Electronic Warfare");

        case "JUMP INTO RIGGED DEVICE":
            return GetSpriteSkill(spriteType, spriteRating, "Electronic Warfare");

        case "MATRIX PERCEPTION":
            return GetSpriteSkill(spriteType, spriteRating, "Computer");

        case "MATRIX SEARCH":
            return GetSpriteSkill(spriteType, spriteRating, "Computer");

        case "REBOOT DEVICE":
            return GetSpriteSkill(spriteType, spriteRating, "Computer");

        case "SEND MESSAGE":
            return -1;

        case "SET DATA BOMB":
            return GetSpriteSkill(spriteType, spriteRating, "Software");

        case "SNOOP":
            return GetSpriteSkill(spriteType, spriteRating, "Electronic Warfare");

        case "SPOOF COMMAND":
            return GetSpriteSkill(spriteType, spriteRating,"Hacking");

        case "SWITCH INTERFACE MODE":
            return -1;

        case "TRACE ICON":
            return GetSpriteSkill(spriteType, spriteRating, "Computer");

        default:
            return -1;
    }

}

    public void rollAssistance(MatrixActions ma){
        int assistDiceBonus=0 ;
        ma.setAssistDiceBonus(0);
        int assistLimitBonus=0;
        ma.setAssistLimitBonus(0);
        Dice dice = new Dice();


        Spinner spLeader= (Spinner) getActivity().findViewById(R.id.spLeader);
        MultiSelectionSpinner spAssistance= (MultiSelectionSpinner) getActivity().findViewById(R.id.spAssistance);
        CheckBox checkEdge = (CheckBox) getActivity().findViewById(R.id.CheckEdge);

        List<Sprite> tempSelectedAssistants = new ArrayList<Sprite>(Main.data.pvSprites);
        List<Sprite> activeAssistants= new ArrayList<Sprite>();
        boolean includeTechnomancer=false;
        if(spLeader.getSelectedItemPosition()!=Main.data.pvSprites.size()) {//It's the technomancer

        }else {//Add the technomancer, remove the leader
            if(spLeader.getSelectedItemPosition()!=tempSelectedAssistants.size()) {
                tempSelectedAssistants.remove(spLeader.getSelectedItemPosition());
            }

        }
        for(int i: spAssistance.getSelectedIndicies()){
            if(i==(spLeader.getCount()-2)){
                includeTechnomancer=true;
            }else{
                activeAssistants.add(tempSelectedAssistants.get(i));
            }
        }

        String assistantDice = "";
        int actionLimit=0;
        int actionDice=0;
        int spriteType=0;
        int spriteRating =0;
        int result=0;


            assistantDice="";
            //Loop through assistants for each action
            for(Sprite assistantSprite: activeAssistants){
                spriteType = assistantSprite.getSpriteType();
                spriteRating = assistantSprite.getSpriteType();
                actionLimit=GetLimit(ma.getLimitType(),spriteType, spriteRating);
                actionDice=GetSpriteActionDice(spriteType, spriteRating, ma.getActionName());
                if(actionDice>=1){//Only add if it can help
                    actionLimit+= limitMod;
                    actionDice+=diePoolMod;
                    result = dice.rollDice(actionDice,false, actionLimit);
                    if(result>0&& !dice.isGlitch && assistLimitBonus!=-1) {
                    assistLimitBonus++;
                    }else {
                        if (dice.isCriticalGlitch) {//If it's a critical glitch, nobody provides a limit bonus
                            assistLimitBonus=-1;
                        }
                    }
                    assistDiceBonus+=result;
                }
            }
            if(includeTechnomancer){
                if(assistantDice.length()>1){
                    assistantDice+=", ";
                }
                actionDice= Main.data.getDice(ma.getLinkedAttribute(),ma.getLinkedSkill(),ma.getActionName());
                actionLimit=GetLimit(ma.getLimitType());
                assistantDice+="("+actionDice+")["+actionLimit+"]";
                //TODO: Remember Edge use when character is assisting
                //Todo: Calculate penalties for each action from Qualities
                actionDice+= diePoolMod;
                actionLimit+= limitMod;
                result = dice.rollDice(actionDice,false, actionLimit);
                if(result>0&& !dice.isGlitch && assistLimitBonus!=-1) {
                    assistLimitBonus++;
                }else {
                    if (dice.isCriticalGlitch) {//If it's a critical glitch, nobody provides a limit bonus
                        assistLimitBonus=-1;
                    }
                }
                assistDiceBonus+=result;
               /* if(checkEdge.isChecked()){
                    Main.data.addStatValue("EdgeUsed", 1);
                    checkEdge.setChecked(false);
                }
                if(Main.data.getStatValue("EdgeUsed")>=Main.data.getStatValue("Edge")){
                    secondChance.setEnabled(false);
                    secondChance.setVisibility(View.INVISIBLE);
                    checkEdge.setEnabled(false);
                }else{
                    secondChance.setEnabled(true);
                    secondChance.setVisibility(View.VISIBLE);
                    checkEdge.setEnabled(true);
                }*/



            }
            if(assistantDice.length()==0){assistantDice="nothing";};
            LinearLayout tableMatrix = (LinearLayout) getActivity().findViewById(R.id.tableMatrix);
            LinearLayout currentRow = (LinearLayout) tableMatrix.findViewWithTag("Row" + ma.getActionName());
            Button btnAssist = (Button) currentRow.findViewWithTag("Assist" + ma.getActionName());
            UpdateAssistance(); //Reset all the other assistance buttons
            if(assistLimitBonus==-1){
                btnAssist.setText("Bonus: (+" + assistDiceBonus + ")[GLITCH]");
            }else {
                btnAssist.setText("Bonus: (+" + assistDiceBonus + ")[+" + assistLimitBonus + "]");
            }
            btnAssist.setTextColor(Color.RED);
            btnAssist.setClickable(false);

        Main.data.setMatrixActionAssistDice(ma.getActionName(), assistDiceBonus);
        Main.data.setMatrixActionAssistLimit(ma.getActionName(), assistLimitBonus);

        //Main.data.pvMatrixActions.get(Main.data.pvMatrixActions.indexOf(ma)).setAssistDiceBonus(assistDiceBonus);
        //Main.data.pvMatrixActions.get(Main.data.pvMatrixActions.indexOf(ma)).setAssistLimitBonus(assistLimitBonus);
        return;
    }


    public void rollAction(MatrixActions ma){
        CheckBox checkEdge = (CheckBox) getActivity().findViewById(R.id.CheckEdge);
        Button secondChance = (Button) getActivity().findViewById(R.id.SecondChance);

        Dice dice = new Dice();
        int result=0;
        Spinner spLeader= (Spinner) getActivity().findViewById(R.id.spLeader);
        //Reset assistant buttons

            int actionDice;
            int actionLimit;
        boolean useEdge=false;
            if(spLeader.getSelectedItemPosition()==spLeader.getCount()-1){//Technomancer
                actionDice= Main.data.getDice(ma.getLinkedAttribute(),ma.getLinkedSkill(),ma.getActionName());
                actionLimit=GetLimit(ma.getLimitType());

                //Todo: Calculate penalties for each action from Qualities
                //TODO: RememberEdge use when character is leading
                if(checkEdge.isChecked()){
                    Main.data.addStatValue("EdgeUsed", 1);
                    checkEdge.setChecked(false);
                }
                if(Main.data.getStatValue("EdgeUsed")>=Main.data.getStatValue("Edge")){
                    secondChance.setEnabled(false);
                    secondChance.setVisibility(View.INVISIBLE);
                    checkEdge.setEnabled(false);
                }else{
                    secondChance.setEnabled(true);
                    secondChance.setVisibility(View.VISIBLE);
                    checkEdge.setEnabled(true);
                }
            }else{
                int spriteType = Main.data.pvSprites.get(spLeader.getSelectedItemPosition()).getSpriteType();
                int spriteRating = Main.data.pvSprites.get(spLeader.getSelectedItemPosition()).getSpriteType();
                actionLimit=GetLimit(ma.getLimitType(),spriteType, spriteRating);
                actionDice=GetSpriteActionDice(spriteType, spriteRating, ma.getActionName());
            }

        actionDice+=Main.data.getMatrixActionAssistDice(ma.getActionName());




        if(Main.data.getMatrixActionAssistLimit(ma.getActionName())>0){
            actionLimit+=Main.data.getMatrixActionAssistLimit(ma.getActionName());
        }
        actionDice+= diePoolMod;
        actionLimit+= limitMod;
        if(actionDice<0){
                actionDice=0;
                result=0;
            }else {
                result = dice.rollDice(actionDice,checkEdge.isChecked(), actionLimit);
            }
        TextView hitsText = (TextView) getActivity().findViewById(R.id.textHitsResult);
        TextView diceText = (TextView) getActivity().findViewById(R.id.textDiceNumber);
        TextView glitchText = (TextView) getActivity().findViewById(R.id.textGlitchStatus);
        hitsText.setText(String.valueOf(result));
        diceText.setText(String.valueOf(actionDice));
        if(dice.isCriticalGlitch){
            glitchText.setText("CRITICAL GLITCH");
            glitchText.setTextColor(Color.RED);
        }else {
            if (dice.isGlitch) {
                glitchText.setText("GLITCH");
                glitchText.setTextColor(Color.RED);
            } else {
                glitchText.setText("NO Glitch");
                glitchText.setTextColor(Color.BLACK);
            }
        }
        UpdateAssistance();
        return;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_matrix, container, false);
        Main = (MainActivity)getActivity();


        //TODO: pop-up for NumberPicker selection to edit value field?
        //https://stackoverflow.com/questions/17944061/how-to-use-number-picker-with-dialog
        //TODO: Perhaps reuse this on all number entry options?

        //TODO Push the Limit checkbox
        //TODO Second Chance button
        //TODO: Remember Edge use when character is assisting

//TODO: One Service= An entire combat, one entire combat turn's worth of actions with a single action (job?), One use of a power
        //TODO: One service = Assist Threading = + dice pool by level

//TODO: Assistant is adding an exta person. Possibly the technomancer?

        final Button secondChance = (Button) v.findViewById(R.id.SecondChance);
        final CheckBox checkEdge = (CheckBox) v.findViewById(R.id.CheckEdge);
        secondChance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dice dice = new Dice();
                //TODO: Store limit somewhere for second chance option
                TextView hitsText = (TextView) getActivity().findViewById(R.id.textHitsResult);
                TextView diceText = (TextView) getActivity().findViewById(R.id.textDiceNumber);
                //Re-roll failures
                //TODO: Re-roll failures for technomancer assistant
                //TODO: Re-roll failures for sprite assistant
                int newDice = Integer.valueOf( diceText.getText().toString()) - Integer.valueOf( hitsText.getText().toString());
                int result = dice.rollDice(newDice,false);
                hitsText.setText(String.valueOf(result + Integer.valueOf( hitsText.getText().toString()) ));
                Main.data.addStatValue("EdgeUsed",1);
                secondChance.setVisibility(View.INVISIBLE);
                secondChance.setEnabled(false);
                if(Main.data.getStatValue("EdgeUsed")>=Main.data.getStatValue("Edge")){
                    checkEdge.setEnabled(false);
                }
            }
        });

        final Spinner spLeader = (Spinner) v.findViewById(R.id.spLeader);
        final MultiSelectionSpinner spAssistance = (MultiSelectionSpinner) v.findViewById(R.id.spAssistance);
        List<String> aa =  new ArrayList<String>(Main.data.pvSpriteList);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, aa);
        adp1.add("Technomancer");
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spLeader.setAdapter(adp1);
        spLeader.setSelection(adp1.getCount()-1);

        spLeader.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (position == Main.data.pvSprites.size()) {//It's the Technomancer
                            UpdateLeader(true);
                        } else {
                            if (position != Main.data.pvActiveSpriteId) {
                                Main.data.pvActiveSpriteId = position;
                                // UpdateLeaderSkills and Active Actions();
                                UpdateLeader(false);
                                UpdateAssistance();
                            }
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });




        //Remove selected Leader
        String[] arrayAssistance = new String[Main.data.pvSpriteList.size()+1];
        List<String> assistants = new ArrayList<String>(Main.data.pvSpriteList);
        assistants.add("Technomancer");
        assistants.remove(spLeader.getSelectedItem());
        arrayAssistance=assistants.toArray(arrayAssistance);
        spAssistance.setItems(arrayAssistance);
        spAssistance.setOnMultiSpinnerListener(
                new MultiSelectionSpinner.MultiSpinnerListener() {
                    public void onItemsSelected(List<String> selected) {
                        UpdateAssistance();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        RelativeLayout dpmLayout = new RelativeLayout(v.getContext());
        final NumberPicker diePoolNumberPicker = new NumberPicker(v.getContext());
        final int minDieValue = -20;
        final int maxDieValue = 20;
        diePoolNumberPicker.setMinValue(0);
        diePoolNumberPicker.setMaxValue(maxDieValue - minDieValue);
        diePoolNumberPicker.setValue(20);
        diePoolNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minDieValue);
            }
        });
        diePoolNumberPicker.setEnabled(true);
        diePoolNumberPicker.setWrapSelectorWheel(true);

        RelativeLayout.LayoutParams rlparams = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        dpmLayout.setLayoutParams(rlparams);
        dpmLayout.addView(diePoolNumberPicker,numPicerParams);

        AlertDialog.Builder dpmDialogBuilder = new AlertDialog.Builder(v.getContext());
        dpmDialogBuilder.setTitle("Die Pool Modifier:");
        dpmDialogBuilder.setView(dpmLayout);
        dpmDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                setDieMod(diePoolNumberPicker.getValue()+minDieValue);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog dpmalertDialog = dpmDialogBuilder.create();
        final Button btnMAViewDiePool = (Button) v.findViewById(R.id.btnMAViewDiePool);
        btnMAViewDiePool.setText("Die Pool Mod: " + diePoolMod);
        btnMAViewDiePool.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dpmalertDialog.show();
            }
        });

        RelativeLayout dplLayout = new RelativeLayout(v.getContext());
        final NumberPicker dplNumberPicker = new NumberPicker(v.getContext());
        final int minLimitValue = -10;
        final int maxLimitValue = 10;
        dplNumberPicker.setMinValue(0);
        dplNumberPicker.setMaxValue(maxLimitValue - minLimitValue);
        dplNumberPicker.setValue(10);
        dplNumberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minLimitValue);
            }
        });
        dplNumberPicker.setEnabled(true);
        dplNumberPicker.setWrapSelectorWheel(true);

        dplLayout.setLayoutParams(rlparams);
        dplLayout.addView(dplNumberPicker,numPicerParams);

        AlertDialog.Builder dplDialogBuilder = new AlertDialog.Builder(v.getContext());
        dplDialogBuilder.setTitle("Pick Limit Modifier:");
        dplDialogBuilder.setView(dplLayout);
        dplDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                setLimitMod(dplNumberPicker.getValue()+ minLimitValue);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog dplDialog = dplDialogBuilder.create();
        final Button btnMAViewLimitMod = (Button) v.findViewById(R.id.btnMAViewLimitMod);
        btnMAViewLimitMod.setText("Limit Modifier: " + limitMod);
        btnMAViewLimitMod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dplDialog.show();
            }
        });

//TODO: Checkbox for pre-edge
//Todo: Update Stats for  Edge when used
        //Todo: Add box for current damage, so it can be changed on the fly
        //Todo: Update Stats for time so it can be changed on the fly (increment 15  minutes?)
        //Todo: add onResume to refresh from DB
        boolean rowColor = false;
        int bgColor;


        for(MatrixActions ma: Main.data.pvMatrixActions){
            if(rowColor){
                bgColor=0xffd3d3d3;
            }else{
                bgColor=Color.WHITE;
            }
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.tableMatrix);
            LinearLayout newRow = new LinearLayout(v.getContext());
            newRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            newRow.setOrientation(LinearLayout.HORIZONTAL);
            newRow.setBackgroundColor(bgColor);


            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels ;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/4,150);

            final MatrixActions action=ma;
            Button btnAction = new Button(v.getContext(), null, android.R.attr.buttonStyleSmall);
            btnAction.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    rollAction(action);
                }
            });
            int actiondice= Main.data.getDice(ma.getLinkedAttribute(),ma.getLinkedSkill(),ma.getActionName());
            btnAction.setText(ma.getActionName() + " ("+ actiondice +")");
            btnAction.setTag("Action" + ma.getActionName());
            //btnAction.setHeight(75);
            //btnAction.setWidth(150);
            btnAction.setTextSize(8);
            btnAction.setMaxLines(3);
            btnAction.setLayoutParams(params);
            newRow.addView(btnAction);


            Button btnAssist= new Button(v.getContext(), null, android.R.attr.buttonStyleSmall);
            btnAssist.setTag("Assist" + ma.getActionName());
            //btnAssist.setHeight(75);
            //btnAssist.setWidth(150);
            btnAssist.setLayoutParams(params);
            btnAssist.setTextSize(8);
            btnAssist.setMaxLines(3);
            List<Integer> listAssistants  =new ArrayList<Integer>(spAssistance.getSelectedIndicies());

            newRow.addView(btnAssist);

            btnAssist.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {
                                                rollAssistance(action);
                                             }
                                         });

//after hit, roll dice, add hits to dice, bump limit, remove button

//add sum of assistance dice to assist button
            TextView oppdice = new TextView(v.getContext());
            if(ma.getOpposedAttribute().isEmpty()){
                oppdice.setText("No Opposed Roll");
            }else{
                if(ma.getOpposedAttribute().equals(ma.getOpposedSkill())){
                    oppdice.setText("Vs. 2*" + ma.getOpposedAttribute());
                }else{
                    if(ma.getOpposedSkill().isEmpty()){
                        oppdice.setText("Vs. " + ma.getOpposedAttribute());
                    }else{
                        oppdice.setText("Vs. " + ma.getOpposedAttribute() + "+" + ma.getOpposedSkill());
                    }
                }
            }
            oppdice.setTextSize(8);
            oppdice.setLines(2);

            newRow.addView(oppdice);
            newRow.setTag("Row" + ma.getActionName());
            linearLayout.addView(newRow);

            rowColor=!rowColor;
        }
        spLeader.setSelection(0);
        spLeader.setSelection(spLeader.getCount()-1);
        return v;
    }




    public static MatrixFragment newInstance() {

        MatrixFragment f = new MatrixFragment();
       /* Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);
*/
        return f;
    }
}
