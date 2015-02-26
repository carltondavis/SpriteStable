package com.dasixes.spritestable;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MatrixFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();



    // Die modifier spinner, Limit Modifier Spinner

    // Action Name, Total Dice, Total/Total/Total assisting dice, Limit(Assisted Limit),
    // Opposed dice, Marks Needed, Action Type,
    // Dropdown of lead actor default to character Multi-dropdown of valid assistors
    //Roll Assist button
    //Auto calculate Total dice and Assistant dice based on totals/limits
    //Expend services checkbox, default to checked

    //Two rows per action?
    //Spinner to pick action?


public void UpdateActions(List<String> activeAssistants){

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
    //Todo: Calculate penalties for each action from Qualites and spinner
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
            actiondice= Main.data.getStatValue(ma.getLinkedAttribute()) +Main.data.getSkillValue(ma.getLinkedSkill(),ma.getActionName());
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
        btnAction.setText(ma.getActionName() + " (" + actiondice +") [" + actionLimit + "]");
    }
    String[] arrayAssistance = new String[Main.data.pvSpriteList.size()];
    arrayAssistance=assistants.toArray(arrayAssistance);
    spAssistance.setItems(arrayAssistance);

//Todo: Calculate limits for rolls, add in [] to Skill button
    //newRow.getChildCount()
    //newRow.getChildAt(i)


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
    public int getAssistantDice(MatrixActions ma, List<String> assistants){
        //TODO: Calculate Assistant dice
        //newRow.getChildCount()
        //newRow.getChildAt(i)
        return 0;
    }

    public void rollAssistance(MatrixActions ma, List<String> assistants){
        //TODO: Roll dice
        //Update Limits, Update ActionDice

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_matrix, container, false);
        Main = (MainActivity)getActivity();


        //TODO: pop-up for NumberPicker selection to edit value field?
        //TODO: Perhaps reuse this on all number entry options?
        //TODO Pre-edge checkbox
        //TODO Post-edge button
        //TODO: Remember karma use when character is assisting

//TODO: One Service= An entire combat, one entire combat turn's worth of actions with a single action (job?), One use of a power
        //TODO: One service = Assist Threading = + dice pool by level


        Spinner spLeader = (Spinner) v.findViewById(R.id.spLeader);
        MultiSelectionSpinner spAssistance = (MultiSelectionSpinner) v.findViewById(R.id.spAssistance);
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
                        List<String> selectedAssistants = selected;
                        UpdateActions(selectedAssistants);
                        //TODO: Update assistance dice
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        NumberPicker npDieModifier = (NumberPicker) v.findViewById(R.id.npDieModifier);
        final int minDieValue = -20;
        final int maxDieValue = 20;
        npDieModifier.setMinValue(0);
        npDieModifier.setMaxValue(maxDieValue - minDieValue);
        npDieModifier.setValue(20);
        npDieModifier.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minDieValue);
            }
        });
        npDieModifier.setEnabled(true);
        npDieModifier.setWrapSelectorWheel(true);
        npDieModifier.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    //TODO: Update dice for Leader and each Assistant
                int myNewValue = newVal+ minDieValue;
            }
        });

        NumberPicker npLimitMod = (NumberPicker) v.findViewById(R.id.npLimitMod);
        final int minLimitValue = -10;
        final int maxLimitValue = 10;
        npLimitMod.setMinValue(0);
        npLimitMod.setMaxValue(maxLimitValue - minLimitValue);
        npLimitMod.setValue(10);
        npLimitMod.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int index) {
                return Integer.toString(index + minLimitValue);
            }
        });
        npLimitMod.setEnabled(true);
        npLimitMod.setWrapSelectorWheel(true);
        npLimitMod.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //TODO: Update Limit mod for Technomancer
                int myNewValue = newVal+ minLimitValue;
            }
        });

//TODO: Checkbox for pre-edge
//Todo: add code to calculate dice pools for assistance rolls
//Todo: Roll dice button, determine final pool button (assistance), and list manual die rolls
//ToDo Add Post-edge buttons for skill and drain. Set minimum number of hits desired for roll, re-roll failures and subtract edge if that number not met. Use Toast if edge used this way
//Todo: Update Stats for  karma when used
        //Todo: Add box for current damage, so it can be changed on the fly
        //Todo: Update Stats for time so it can be changed on the fly (increment 15  minutes?)
        //Todo: add onResume to refresh from DB
        //TODO: Add option to enable/disable Threads known
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



            //TODO: Button to roll action  Needs listener to call dice rolling function
            Button btnAction = new Button(v.getContext(), null, android.R.attr.buttonStyleSmall);
            int actiondice= Main.data.getStatValue(ma.getLinkedAttribute()) +Main.data.getSkillValue(ma.getLinkedSkill(),ma.getActionName());
            btnAction.setText(ma.getActionName() + " ("+ actiondice +")");
            btnAction.setTag("Action" + ma.getActionName());
            btnAction.setHeight(75);
            btnAction.setWidth(125);
            btnAction.setTextSize(8);
            btnAction.setMaxLines(3);
            newRow.addView(btnAction);

//todo text for number of dice

            //TODO: Button to roll assistance (grey itself and checkbox out after doing it, decrement services, add dice and limit to roll)
            Button btnAssist= new Button(v.getContext(), null, android.R.attr.buttonStyleSmall);
            btnAssist.setTag("Assist" + ma.getActionName());
            btnAssist.setHeight(75);
            btnAssist.setWidth(100);
            btnAssist.setTextSize(8);
            btnAssist.setMaxLines(2);

            btnAssist.setText("Roll "+ getAssistantDice(ma, assistants) +" to Assist");

            newRow.addView(btnAssist);
            final MatrixActions action=ma;
            final List<String> ActionAssistants=new ArrayList<String>(assistants);
            btnAssist.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {
                                                rollAssistance(action,  ActionAssistants);
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
        return v;
    }
//todo function to get limit for action
    public int getLimit(MatrixActions ma){
        switch(ma.getLimitType()){
            case 1:
                return Main.data.getStatValue("Charisma");

            case 2:
                return Main.data.getStatValue("Intuition");

            case 3:
                return Main.data.getStatValue("Logic");

            case 4:
                return Main.data.getStatValue("Willpower");

        }
return 0;
    }

    //TODO: Dice rolling function that accepts MatrixAction object + Sprite object list for assistance
public int rollAction(MatrixActions ma, ArrayList<Sprite> sl){
 Dice dice = new Dice();
   return dice.rollDice(Main.data.getSkillValue(ma.getLinkedSkill(),ma.getActionName())+ Main.data.getStatValue(ma.getLinkedSkill()),false, getLimit(ma));
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
