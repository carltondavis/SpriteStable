package com.dasixes.spritestable;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ComplexFormsFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();


    public void UpdateAssistance(){
        //Loop through all actions
        Spinner spLeader= (Spinner) getActivity().findViewById(R.id.spLeader);
        MultiSelectionSpinner spAssistance= (MultiSelectionSpinner) getActivity().findViewById(R.id.spAssistance);
        CheckBox checkEdge = (CheckBox) getActivity().findViewById(R.id.CheckEdge);
        Button secondChance = (Button) getActivity().findViewById(R.id.SecondChance);

        checkEdge.setChecked(false);




        List<Sprite> tempSelectedAssistants = new ArrayList<Sprite>(Main.data.pvSprites);
        List<Sprite> activeAssistants= new ArrayList<Sprite>();

  /*  if(spLeader.getSelectedItemPosition()!=Main.data.pvSprites.size()) {//Lead by technomancer

    }else {//Add the technomancer, remove the leader
    }
    */
        for(int i: spAssistance.getSelectedIndicies()){
            activeAssistants.add(tempSelectedAssistants.get(i));
        }

        String assistantDice = "";
        int actionLimit=0;
        int actionDice=0;
        int spriteType=0;
        int spriteRating =0;


        for(ComplexForm cf :Main.data.pvComplexForms){


            assistantDice="";
            //Loop through assistants for each action
            for(Sprite assistantSprite: activeAssistants){
                actionDice+= assistantSprite.getRating();
            }
            LinearLayout tableMatrix = (LinearLayout) getActivity().findViewById(R.id.tableMatrix);
            LinearLayout currentRow = (LinearLayout) tableMatrix.findViewWithTag("Row" + cf.getName());
            Button btnAction = (Button) currentRow.findViewWithTag("Action" + cf.getName());
            btnAction.setText(cf.getName() + " (" + Main.data.getDice("Resonance", "Software", cf.getName()) + assistantDice +")");
            btnAction.setTextColor(Color.BLACK);
            btnAction.setClickable(true);
        }
    }

    public void rollAction(ComplexForm cf){

        int actionLimit=0;
        NumberPicker npDieModifier= (NumberPicker) getActivity().findViewById(R.id.npDieModifier);
        NumberPicker npLimitModifier= (NumberPicker) getActivity().findViewById(R.id.npLimitMod);
        CheckBox checkEdge = (CheckBox) getActivity().findViewById(R.id.CheckEdge);
        Button secondChance = (Button) getActivity().findViewById(R.id.SecondChance);

        Dice dice = new Dice();
        int result=0;
        Spinner spLeader= (Spinner) getActivity().findViewById(R.id.spLeader);
        //Reset assistant buttons

        int actionDice;
        boolean useEdge=false;
            actionDice= Main.data.getDice("Resonance","Software",cf.getName());

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
        List<Sprite> tempSelectedAssistants = new ArrayList<Sprite>(Main.data.pvSprites);
        List<Sprite> activeAssistants= new ArrayList<Sprite>();

  /*  if(spLeader.getSelectedItemPosition()!=Main.data.pvSprites.size()) {//Lead by technomancer

    }else {//Add the technomancer, remove the leader
    }
    */
        MultiSelectionSpinner spAssistance= (MultiSelectionSpinner) getActivity().findViewById(R.id.spAssistance);

        for(int i: spAssistance.getSelectedIndicies()){
            activeAssistants.add(tempSelectedAssistants.get(i));
        }

            //Loop through assistants for each action
            for(Sprite assistantSprite: activeAssistants){
                actionDice+= assistantSprite.getRating();
            }


        actionDice+= npDieModifier.getValue()-20;
        actionLimit+= npLimitModifier.getValue()-10;
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

        final MultiSelectionSpinner spAssistance = (MultiSelectionSpinner) v.findViewById(R.id.spAssistance);

        //Remove selected Leader
        String[] arrayAssistance = new String[Main.data.pvSpriteList.size()+1];
        List<String> assistants = new ArrayList<String>(Main.data.pvSpriteList);
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
                int myNewValue = newVal+ minLimitValue;
            }
        });

//TODO: Checkbox for pre-edge
//Todo: Update Stats for  Edge when used
        //Todo: Add box for current damage, so it can be changed on the fly
        //Todo: Update Stats for time so it can be changed on the fly (increment 15  minutes?)
        //Todo: add onResume to refresh from DB
        boolean rowColor = false;
        int bgColor;


        for(ComplexForm cf: Main.data.pvComplexForms){
            if(rowColor){
                bgColor=0xffd3d3d3;
            }else{
                bgColor= Color.WHITE;
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

            final ComplexForm action=cf;
            Button btnAction = new Button(v.getContext(), null, android.R.attr.buttonStyleSmall);
            btnAction.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    rollAction(action);
                }
            });
            int actiondice= Main.data.getDice("Resonance","Software",cf.getName());
            btnAction.setText(cf.getName() + " ("+ actiondice +")");
            btnAction.setTag("Action" + cf.getName());
            //btnAction.setHeight(75);
            //btnAction.setWidth(150);
            btnAction.setTextSize(8);
            btnAction.setMaxLines(3);
            btnAction.setLayoutParams(params);
            newRow.addView(btnAction);

            TextView oppdice = new TextView(v.getContext());
            oppdice.setText(cf.getFading());
            oppdice.setTextSize(8);
            oppdice.setLines(2);

            newRow.addView(oppdice);
            newRow.setTag("Row" + cf.getName());
            linearLayout.addView(newRow);

            rowColor=!rowColor;
        }
        return v;
    }



    public static ComplexFormsFragment newInstance() {

       ComplexFormsFragment f = new ComplexFormsFragment();
       /* Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);
*/
        return f;
    }
}
