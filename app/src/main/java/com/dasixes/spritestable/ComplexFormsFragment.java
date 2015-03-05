package com.dasixes.spritestable;
//TODO: Add second chance drain karma button
//TODO: Assisting uses a service
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ComplexFormsFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();
    int Force=1;
    int diePoolMod=0;
    int limitMod=0;



    public void UpdateAssistance(){
        //Loop through all actions
        MultiSelectionSpinner spAssistance= (MultiSelectionSpinner) getActivity().findViewById(R.id.CFspAssistance);
        CheckBox checkEdge = (CheckBox) getActivity().findViewById(R.id.CFCheckEdge);
        Button secondChance = (Button) getActivity().findViewById(R.id.CFSecondChance);

        checkEdge.setChecked(false);




        List<Sprite> tempSelectedAssistants = new ArrayList<Sprite>(Main.data.pvSprites);
        List<Sprite> activeAssistants= new ArrayList<Sprite>();

        for(int i: spAssistance.getSelectedIndicies()) {
            if (tempSelectedAssistants.get(i).getServicesOwed() > 0 && tempSelectedAssistants.get(i).getRegistered() == 1){
                activeAssistants.add(tempSelectedAssistants.get(i));
            }
        }

        Integer actionDice;
        for(ComplexForm cf :Main.data.pvComplexForms){
            //Loop through assistants for each action
            actionDice=Main.data.getDice("Resonance", "Software", cf.getName());
            for(Sprite assistantSprite: activeAssistants){
                actionDice+= assistantSprite.getRating();
            }
            LinearLayout tableComplexForm = (LinearLayout) getActivity().findViewById(R.id.CFtableComplexForm);
            LinearLayout currentRow = (LinearLayout) tableComplexForm.findViewWithTag("CFRow" + cf.getName());
            Button btnAction = (Button) currentRow.findViewWithTag("CF" + cf.getName());
            btnAction.setText(cf.getName() + " (" + actionDice +")");
            btnAction.setTextColor(Color.BLACK);
            btnAction.setClickable(true);
        }
    }
    @Override
    public void onPause() {
        //Log.e("DEBUG", "onResume of CompileFragment");
        super.onPause();
        Main.data.SaveAllToDB();

    }
    @Override
    public void onResume() {
        //Log.e("DEBUG", "onResume of CompileFragment");
        super.onResume();
        Main = (MainActivity)getActivity();
        Main.data.RestoreFromDB(Main);
        UpdateDamage();
        UpdateAssistance();
    }

    public void rollAction(ComplexForm cf){
//TODO: Add damage penalties to die rolls
//TODO: Disable actions when unconscious or dead

        int actionLimit=Force;
        CheckBox checkEdge = (CheckBox) getActivity().findViewById(R.id.CFCheckEdge);
        Button secondChance = (Button) getActivity().findViewById(R.id.CFSecondChance);

        Dice dice = new Dice();
        int result=0;
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
        MultiSelectionSpinner spAssistance= (MultiSelectionSpinner) getActivity().findViewById(R.id.CFspAssistance);

        for(int i: spAssistance.getSelectedIndicies()){
            activeAssistants.add(tempSelectedAssistants.get(i));
        }

        //Loop through assistants for each action
        for(Sprite assistantSprite: activeAssistants){
            actionDice+= assistantSprite.getRating();
        }


        actionDice+= diePoolMod;
        actionLimit+= limitMod;
        if(actionDice<0){
            actionDice=0;
            result=0;
        }else {
            result = dice.rollDice(actionDice,checkEdge.isChecked(), actionLimit);

            if(Main.data.DoIHaveBadLuck()&&checkEdge.isChecked()){
                result = dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower") - Main.data.getStatValue("Edge"), false);
            }else{
                result = dice.rollDice(actionDice,checkEdge.isChecked(), actionLimit);
            }
        }
        TextView hitsText = (TextView) getActivity().findViewById(R.id.CFtextHitsResult);
        TextView diceText = (TextView) getActivity().findViewById(R.id.CFtextDiceNumber);
        TextView glitchText = (TextView) getActivity().findViewById(R.id.CFtextGlitchStatus);
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

        //TODO: Second Chance Drain
        Integer Fading = Force + Integer.valueOf(cf.getFading().replace("+", "").replace("L", ""));

        //Roll Drain
        CheckBox checkDrainEdgeCF= (CheckBox) getActivity().findViewById(R.id.checkDrainEdgeCF);
        if(Main.data.getQualityValue("Sensitive System")==1){
            if(dice.rollDice(Main.data.getStatValue("Willpower"),false)<2){Fading+=2;}
        }
        if(Main.data.DoIHaveBadLuck()&&checkDrainEdgeCF.isChecked()){
            Fading = Fading - dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower") - Main.data.getStatValue("Edge"), false);
        }else{
            if(checkDrainEdgeCF.isChecked()){
                Fading = Fading - dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower") + Main.data.getStatValue("Edge"), true);
            }else{
                Fading = Fading - dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower"), false);
            }

        }
        if (Fading < 0) {
            Fading = 0;
        }
        if (result > Main.data.getStatValue("Resonance")) {//Big Success, physical damage
            Main.data.addStatValue("Physical", Fading);
        } else {//Small Success: Stun
            Main.data.addStatValue("Stun", Fading);
        }
        UpdateDamage();
        UpdateAssistance();
        return;
    }
    private void UpdateDamage() {
        UpdateDamage(Main.data.getStatValue("Stun"), Main.data.getStatValue("Physical"));
    }
    private void UpdateDamage(int stun, int physical) {
        RatingBar stunDamage = (RatingBar) getActivity().findViewById(R.id.cfstunTrack);
        if(stunDamage!=null) {
            int MaxStun = (int) Math.floor(Main.data.getStatValue("Willpower") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Stun");
            int MaxPhysical = (int) Math.floor(Main.data.getStatValue("Body") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Physical");
            int _overflow = 0;

            stunDamage.setClickable(false);
            stunDamage.setEnabled(false);
            if (stunDamage.getRating() != stun || MaxStun != stunDamage.getMax()) {
                stunDamage.setNumStars(MaxStun);
                stunDamage.setMax(MaxStun);
                if (stun > MaxStun) {//Did we exceed the stun condition monitor?
                    physical += (int) Math.floor((stun - MaxStun) / 2);  //(TotalStun - StunMax)/2 rounded down is overflow
                    Main.data.setStatValue("Stun", MaxStun);
                }
                stunDamage.setRating(Main.data.getStatValue("Stun"));
            }
            RatingBar physicalDamage = (RatingBar) getActivity().findViewById(R.id.cfphysicalTrack);
            physicalDamage.setClickable(false);
            physicalDamage.setEnabled(false);
            if (physicalDamage.getRating() != physical || MaxPhysical != physicalDamage.getMax()) {
                physicalDamage.setNumStars(MaxPhysical);
                physicalDamage.setMax(MaxPhysical);

                RatingBar overflowDamage = (RatingBar) getActivity().findViewById(R.id.cfoverflowTrack);
                overflowDamage.setClickable(false);
                overflowDamage.setEnabled(false);
                overflowDamage.setNumStars(Main.data.getStatValue("Body"));
                overflowDamage.setMax(Main.data.getStatValue("Body"));

                if (physical > MaxPhysical) {
                    _overflow = physical - MaxPhysical;
                }
                physicalDamage.setRating(physical - _overflow);//Don't count overflow when drawing boxes of damage
                overflowDamage.setRating(_overflow);
                Main.data.setStatValue("Physical", physical);
            }

            EditText stuntext = (EditText) getActivity().findViewById(R.id.editStun);
            if (stuntext != null) {
                stuntext.setText(String.valueOf(Main.data.getStatValue("Stun")));
                EditText physicaltext = (EditText) getActivity().findViewById(R.id.editPhysical);
                physicaltext.setText(String.valueOf(Main.data.getStatValue("Physical")));
            }
            Main.data.SaveAllStatsToDB();
        }
    }

public void setForce(Integer force){
    Force=force;
    Button btnForce = (Button) getActivity().findViewById(R.id.btnForce);
    btnForce.setText("Force: " + Force);
}
    public void setDieMod(Integer mod){
        diePoolMod=mod;
        Button btnCFViewDiePool = (Button) getActivity().findViewById(R.id.btnCFViewDiePool);
        btnCFViewDiePool.setText("Die Pool Mod: " + mod);
    }
    public void setLimitMod(Integer mod){
        limitMod=mod;
        Button btnCFViewLimitMod = (Button) getActivity().findViewById(R.id.btnCFViewLimitMod);
        btnCFViewLimitMod.setText("Limit Modifier: " + mod);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_complex_forms, container, false);
        Main = (MainActivity)getActivity();


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
        final Button btnCFViewDiePool = (Button) v.findViewById(R.id.btnCFViewDiePool);
        btnCFViewDiePool.setText("Die Pool Mod: " + diePoolMod);
        btnCFViewDiePool.setOnClickListener(new View.OnClickListener() {

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
        final Button btnCFViewLimitMod = (Button) v.findViewById(R.id.btnCFViewLimitMod);
        btnCFViewLimitMod.setText("Limit Modifier: " + limitMod);
        btnCFViewLimitMod.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dplDialog.show();
            }
        });

        RelativeLayout forceLayout = new RelativeLayout(v.getContext());
        final NumberPicker forceNumberPicker = new NumberPicker(v.getContext());
        Force=Main.data.getStatValue("Resonance");
        forceNumberPicker.setMaxValue(Force*3);
        forceNumberPicker.setMinValue(1);

        forceLayout.setLayoutParams(rlparams);
        forceLayout.addView(forceNumberPicker,numPicerParams);

        AlertDialog.Builder forceDialogBuilder = new AlertDialog.Builder(v.getContext());
        forceDialogBuilder.setTitle("Pick Force for Complex Form");
        forceDialogBuilder.setView(forceLayout);
        forceDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                setForce(forceNumberPicker.getValue());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog forceDialog = forceDialogBuilder.create();
        final Button btnForce = (Button) v.findViewById(R.id.btnForce);
        btnForce.setText("Force: " + Force);
        btnForce.setOnClickListener(new View.OnClickListener() {

                                     @Override
                                     public void onClick(View v) {
                                         forceDialog.show();
                                     }
                                 });






        final Button secondChance = (Button) v.findViewById(R.id.CFSecondChance);
        final CheckBox checkEdge = (CheckBox) v.findViewById(R.id.CFCheckEdge);
        secondChance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dice dice = new Dice();
                //TODO: Store limit somewhere for second chance option
                TextView hitsText = (TextView) getActivity().findViewById(R.id.CFtextHitsResult);
                TextView diceText = (TextView) getActivity().findViewById(R.id.CFtextDiceNumber);
                //Re-roll failures
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

        final MultiSelectionSpinner spAssistance = (MultiSelectionSpinner) v.findViewById(R.id.CFspAssistance);
        List<String> activeSpriteList = new ArrayList<>();
        for (Sprite sprite : Main.data.pvSprites) {
            if((sprite.getServicesOwed()>0) && (sprite.getRegistered()==1)){
                String title = String.valueOf("Force " + sprite.getRating() + " " + sprite.getType()) + " with " + sprite.getServicesOwed() + " services";
                activeSpriteList.add(title);
            }
        }
        String[] arrayAssistance = new String[activeSpriteList.size()];
        arrayAssistance=activeSpriteList.toArray(arrayAssistance);
        spAssistance.setItems(arrayAssistance);
        spAssistance.setOnMultiSpinnerListener(
                new MultiSelectionSpinner.MultiSpinnerListener() {
                    public void onItemsSelected(List<String> selected) {
                        UpdateAssistance();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
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
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.CFtableComplexForm);
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
            btnAction.setTag("CF" + cf.getName());
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
            newRow.setTag("CFRow" + cf.getName());
            linearLayout.addView(newRow);

            rowColor=!rowColor;
        }
        UpdateDamage();
//TODO: Damage is not persisting between fragments.
//Stats is working correctly.
//CF is not.  It's saving correctly, but not loading correctly after changed in stats.
//Stats saves change no problem.
//Changes in Compile aren't saving appropriately.

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
