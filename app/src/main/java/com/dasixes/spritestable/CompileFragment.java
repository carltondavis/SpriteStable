package com.dasixes.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//TODO Drain glitches +2DV?  Maybe.  Let's wait on this.
//TODO Compile glitches  +2DV?  Maybe.  Let's wait on this.
//TODO register glitches +2DV?  Maybe.  Let's wait on this.
//TODO Drain critical glitches +2DV?  Maybe.  Let's wait on this.
//TODO Compile critical Glitches +2DV?  Maybe.  Let's wait on this.
//TODO register critical glitches +2DV?  Maybe.  Let's wait on this.
//Fancy UI:

//Todo Edge regen after 8 hours consecutive rest
//todo test consecutive rest Edge reset
//ToDo Add penalty popup warnings  Add Total Penalty display, tap it to have a toast pop up listing sources of penalties
//Todo add Toast for disabled buttons explaining why they're disabled
//Todo Someday Add statistics for rolls to include actual percentage chance of something happening to warnings
//TODO settings page: dice rolls display, Fatigue rules, fatigue warnings, Penalty popup warning, possible death warning
//ToDo Add possible Overflow/Death warnings If compiling/registering something, calculate if worst case stun/physical could max out stun or physical chart.  Warn of possibility.
//ToDo Add automated registering system: Set the amount of time to use, and it runs
//Create new class to handle all View updates?

public class CompileFragment extends Fragment {
    // PersistentValues data = new PersistentValues();
    MainActivity Main = (MainActivity)getActivity();

    Dice dice = new Dice();
    Integer secondSkillSuccesses =0;
    Integer secondSkillOpposed =0;
    Integer secondFadeSuccesses=0;
    Integer secondFadeOpposed=0;

    //Display display;// = new Display(getActivity());
    //UpdateCompile Button text   Register/Compile  Enabled
    private void UpdateCompile() {
        UpdateCompile(Main.data.getCurrentSprite().getServicesOwed() == 0, IsConscious());

    }

    private void UpdateCompile(boolean isCompile, boolean enabled) {
        Button Compile = (Button) getActivity().findViewById(R.id.Compile);
        if (isCompile) {
            Compile.setText("Compile");
        } else {
            Compile.setText("Register");
            //Disable Registration if there are more than LOGIC sprites registered.  Because there's always an unregistered sprite floating around that means the number of sprites-1== number registered
            if (((Main.data.pvSprites.size()) > Main.data.getStatValue("Logic")) && Main.data.getCurrentSprite().getRegistered() == 0) { //Also only disable if we're looking at an unregistered sprite.  We can always get more services.
                enabled = false;
            }
        }
        Compile.setClickable(enabled);
        Compile.setEnabled(enabled);
    }

    private void UpdateDisplay() {
        UpdateServices();
        UpdateDamage();
        UpdateRating();
        UpdateHours();
        UpdateTypePicker();
        UpdateCheckBoxes();

    }

    @Override
    public void onResume() {
        //Log.e("DEBUG", "onResume of CompileFragment");
        super.onResume();
        Main = (MainActivity)getActivity();
        Main.data.RestoreFromDB(Main);
        UpdateDisplay();
    }
    @Override
    public void onPause() {
        //Log.e("DEBUG", "onResume of CompileFragment");
        super.onPause();
        Main.data.SaveAllStatsToDB();
        Main.data.SaveAllSpritesToDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compile, container, false);
        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        Main = (MainActivity)getActivity();
        Button compileButton = (Button) v.findViewById(R.id.Compile);
        compileButton.setEnabled(IsConscious());
        if(Main.data.getCurrentSprite().getServicesOwed()>0){compileButton.setText("Register");}else{compileButton.setText("Compile");}

        final CheckBox checkDrainEdge = (CheckBox) v.findViewById(R.id.DrainEdge);
        checkDrainEdge.setEnabled(Main.data.getStatValue("EdgeUsed")<Main.data.getStatValue("Edge"));

        final CheckBox checkSkillEdge = (CheckBox) v.findViewById(R.id.SkillEdge);
        checkSkillEdge.setEnabled(Main.data.getStatValue("EdgeUsed")<Main.data.getStatValue("Edge"));

        final TextView valueHours = (TextView) v.findViewById(R.id.valuesHours);
        valueHours.setText(String.valueOf(Main.data.getStatValue("HoursThisSession")));

        Spinner spriteSpinner = (Spinner) v.findViewById(R.id.CompileSpinnerSprite);
        //Set values
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, Main.data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);

        Spinner typeSpinner = (Spinner) v.findViewById(R.id.CompileSpinnerSpriteType);
        ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(v.getContext(), R.array.SpriteTypes, android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adp2);
        typeSpinner.setSelection(adp2.getPosition(Main.data.getCurrentSprite().getType()));

        final NumberPicker npSpriteRating = (NumberPicker) v.findViewById(R.id.npSpriteRating);
        npSpriteRating.setValue(Main.data.getCurrentSprite().getRating());
        npSpriteRating.setWrapSelectorWheel(false);
        npSpriteRating.setMinValue(1);
        npSpriteRating.setMaxValue(Main.data.getStatValue("Resonance") * 2);

        Button useService = (Button) v.findViewById(R.id.buttonUseService);
        useService.setEnabled((Main.data.getCurrentSprite().getServicesOwed()>0&&IsConscious()));

        final Button secondSkill = (Button) v.findViewById(R.id.SecondSkill);
        final Button secondFading = (Button) v.findViewById(R.id.SecondFading);

        secondSkill.setVisibility(View.INVISIBLE);
        secondFading.setVisibility(View.INVISIBLE);

        RatingBar stunDamage = (RatingBar) v.findViewById(R.id.stunTrack);
        stunDamage.setClickable(false);
        stunDamage.setEnabled(false);
        stunDamage.setNumStars((int) Math.floor(Main.data.getStatValue("Willpower") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Stun"));

        stunDamage.setMax((int) Math.floor(Main.data.getStatValue("Willpower") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Stun"));
        stunDamage.setRating(Main.data.getStatValue("Stun"));

        RatingBar physicalDamage = (RatingBar) v.findViewById(R.id.physicalTrack);
        physicalDamage.setClickable(false);
        physicalDamage.setEnabled(false);
        physicalDamage.setNumStars((int) Math.floor(Main.data.getStatValue("Body") / 2) + 9 +Main.data.getQualityValue("Tough as Nails Physical"));
        physicalDamage.setMax((int) Math.floor(Main.data.getStatValue("Body") / 2) + 9+Main.data.getQualityValue("Tough as Nails Physical"));

        RatingBar overflowDamage = (RatingBar) v.findViewById(R.id.overflowTrack);
        overflowDamage.setClickable(false);
        overflowDamage.setEnabled(false);
        overflowDamage.setNumStars(Main.data.getStatValue("Body")+Main.data.getQualityValue("Will to Live"));
        overflowDamage.setMax(Main.data.getStatValue("Body"));

        if (Main.data.getStatValue("Physical") > (int) Math.floor(Main.data.getStatValue("Body") / 2) + 9+Main.data.getQualityValue("Tough as Nails Physical")) {
            overflowDamage.setRating(Main.data.getStatValue("Physical") - (int) Math.floor(Main.data.getStatValue("Body") / 2) + 9+Main.data.getQualityValue("Tough as Nails Physical"));
        }
        physicalDamage.setRating(Main.data.getStatValue("Physical") - overflowDamage.getRating());//Don't count overflow when drawing boxes of damage







        //Sprite List
        spriteSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (position != Main.data.pvActiveSpriteId) {
                            Main.data.pvActiveSpriteId = position;
                            //UpdateSprite();
                            secondFading.setVisibility(View.INVISIBLE);
                            secondSkill.setVisibility(View.INVISIBLE);
                            UpdateDisplay();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

//Sprite Type Picker
        typeSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {

                        UpdateSpriteType(position + 1);
                        Main.data.SaveSpriteToDB();
                        UpdateCompileSpriteList();
                        Main.data.SaveSpriteToDB();
                        secondFading.setVisibility(View.INVISIBLE);
                        secondSkill.setVisibility(View.INVISIBLE);

                    }



                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        secondSkill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                secondChanceSkill();
            }
        });
        secondFading.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                secondChanceFading();
            }
        });


        Button restButton = (Button) v.findViewById(R.id.Rest);
        restButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                secondFading.setVisibility(View.INVISIBLE);
                secondSkill.setVisibility(View.INVISIBLE);
                //Healing Roll
                Boolean Sleepless=false;
                if(Main.data.getQualityValue("Insomnia")>0){
                    Sleepless=(4<=dice.rollDice(Main.data.getStatValue("Willpower")+Main.data.getStatValue("Intuition"),false));
                }
                if(!Sleepless||Main.data.getQualityValue("Insomnia")==1) {
                    Main.data.setStatValue("Stun",                     Main.data.getStatValue("Stun") - dice.rollDice(Main.data.getStatValue("Body") + Main.data.getStatValue("Willpower") + 2 * (Main.data.getQualityValue("Quick Healer") *Main.data.getQualityValue("Slow Healer")), false));
                }
                if (Main.data.getStatValue("Stun") < 0) {
                    Main.data.setStatValue("Stun" ,0);
                }
                if(dice.isCriticalGlitch){
                    Main.data.setStatValue("Stun",(                    Main.data.getStatValue("Stun")+dice.rollDie(3)));
                }
                UpdateDamage();

                //Add hours
                //Double time for Glitches
                int RestTime=1;
                if(Sleepless&&Main.data.getQualityValue("Insomnia")==1){
                    RestTime=RestTime*2;
                }

                if(dice.isGlitch){
                    RestTime=RestTime*2;
                }
                Main.data.addStatValue("HoursThisSession", RestTime);
                Main.data.addStatValue("HoursSinceEdgeRefresh", RestTime);
                Main.data.addStatValue("ConsecutiveRest", RestTime);

                if (Main.data.getStatValue("ConsecutiveRest") >= 8) {
                    if (Main.data.getStatValue("HoursSinceEdgeRefresh") >= 24 && Main.data.getStatValue("EdgeUsed") > 0) {
                        Main.data.setStatValue("HoursSinceEdgeRefresh", 0);
                        if(!Sleepless||Main.data.getQualityValue("Insomnia")==1){
                            Main.data.addStatValue("EdgeUsed",-1);
                            UpdateStatEdgeUsed();

                            checkDrainEdge.setEnabled(true);
                            checkSkillEdge.setEnabled(true);
                        }
                    }
                    Main.data.setStatValue("SleeplessHours", 0);
                    Main.data.setStatValue("ConsecutiveRest", 0);
                }


                valueHours.setText(String.valueOf(Main.data.getStatValue("HoursThisSession")));
            }
        });
//Sleep Button
//Adds 8 hours, heal stun, regen Edge, reset consecutive hours without sleep
        Button sleepButton = (Button) v.findViewById(R.id.buttonSleep);
        sleepButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean Sleepless=false;
                secondFading.setVisibility(View.INVISIBLE);
                secondSkill.setVisibility(View.INVISIBLE);

                if(Main.data.getQualityValue("Insomnia")>0){
                    Sleepless=(4<=dice.rollDice(Main.data.getStatValue("Willpower")+Main.data.getStatValue("Intuition"),false));
                }
                //Healing Roll 8 times
                for (int i = 1; i <= 8; i++) {
                    if (Main.data.getStatValue("Stun") > 0) {
                        if(!Sleepless||Main.data.getQualityValue("Insomnia")==1){
                            Main.data.addStatValue("Stun", -1*(dice.rollDice(Main.data.getStatValue("Body") + Main.data.getStatValue("Willpower")+2*(Main.data.getQualityValue("Quick Healer")-Main.data.getQualityValue("Slow Healer")), false)));
                            if(Sleepless){i++;}//Sleeping takes twice as long
                        }
                        if (Main.data.getStatValue("Stun") < 0) {
                            Main.data.setStatValue("Stun", 0);
                        }

                        if(dice.isGlitch) {
                            i++;//Lose an hour of rest, since it takes two hours to heal this batch of damage
                            if (dice.isCriticalGlitch) {
                                Main.data.addStatValue("Stun",  dice.rollDie(3));
                            }
                        }
                    }
                }
                UpdateDamage();

                //Add hours
                Main.data.addStatValue("HoursThisSession", 8);
                //If it's been at least 24 hours, refresh Edge.
                if (Main.data.getStatValue("HoursSinceEdgeRefresh") >= 24 && Main.data.getStatValue("EdgeUsed") > 0) {
                    Main.data.setStatValue("HoursSinceEdgeRefresh", 0);
                    if(!Sleepless||Main.data.getQualityValue("Insomnia")==1) {
                        Main.data.addStatValue("EdgeUsed",-1);
                        UpdateStatEdgeUsed();
                        checkDrainEdge.setEnabled(true);
                        checkSkillEdge.setEnabled(true);
                    }
                }
                Main.data.setStatValue("SleeplessHours" , 0);
                Main.data.addStatValue("HoursSinceEdgeRefresh", 8);
                Main.data.setStatValue("ConsecutiveRest" , 0);
                valueHours.setText(String.valueOf(Main.data.getStatValue("HoursThisSession")));
            }
        });

//Heal Button
//Adds 24 hours, heal stun or physical, regen Edge, reset consecutive hours without sleep
        Button healButton = (Button) v.findViewById(R.id.buttonHeal);
        healButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                secondFading.setVisibility(View.INVISIBLE);
                secondSkill.setVisibility(View.INVISIBLE);
                Boolean Sleepless = false;
                if (Main.data.getQualityValue("Insomnia") > 0) {
                    Sleepless = (4 <= dice.rollDice(Main.data.getStatValue("Willpower") + Main.data.getStatValue("Intuition"), false));
                }

                if (Main.data.getStatValue("Stun") > 0) {
                    //Healing Roll 24 times

                    for (int i = 1; i <= 24; i++) {
                        if (Main.data.getStatValue("Stun") > 0) {
                            if (!Sleepless || Main.data.getQualityValue("Insomnia") == 1) {
                                Main.data.addStatValue("Stun", -1*( dice.rollDice(Main.data.getStatValue("Body") + Main.data.getStatValue("Willpower") + 2 * (Main.data.getQualityValue("Slow Healer") + Main.data.getQualityValue("Quick Healer")), false)));
                                if (Sleepless) {
                                    Main.data.addStatValue("HoursThisSession", 24);
                                }//Takes twice as long to heal
                            }
                            if (Main.data.getStatValue("Stun") < 0) {
                                Main.data.setStatValue("Stun", 0);
                            }
                            if (dice.isGlitch) {
                                i++;//Lose an hour of rest, since it takes two hours to heal this batch of damage
                                Main.data.addStatValue("HoursThisSession", 1);
                                if (dice.isCriticalGlitch) {
                                    Main.data.addStatValue("Stun", dice.rollDie(3));
                                }
                            }

                        }
                        Main.data.addStatValue("HoursThisSession", 1);
                    }
                } else {
                    if (Main.data.getStatValue("Physical") > 0) {
                        if (!Sleepless || Main.data.getQualityValue("Insomnia") == 1) {
                            Main.data.addStatValue("Physical",-1* dice.rollDice(Main.data.getStatValue("Body") * 2 + 2 * (Main.data.getQualityValue("Quick Healer") - Main.data.getQualityValue("Slow Healer")), false));
                            if (Sleepless) {
                                Main.data.addStatValue("HoursThisSession", 24);//Takes twice as long to heal
                            }
                            if (Main.data.getStatValue("Physical") <= 0) {
                                Main.data.setStatValue("Physical", 0 + Main.data.getQualityValue("Pie Iesu Domine"));
                            }
                            if (dice.isCriticalGlitch) {
                                Main.data.addStatValue("Physical", dice.rollDie(3));
                            }
                        }
                        if (dice.isGlitch) {
                            Main.data.addStatValue("HoursThisSession", 24);
                        }
                        Main.data.addStatValue("HoursThisSession", 24);
                    }


                    //Add hours

                    //If it's been at least 24 hours, refresh Edge.
                    if (Main.data.getStatValue("HoursSinceEdgeRefresh") >= 24 && Main.data.getStatValue("EdgeUsed") > 0) {
                        Main.data.setStatValue("HoursSinceEdgeRefresh", 0);
                        if (!Sleepless || Main.data.getQualityValue("Insomnia") == 1) {
                            Main.data.addStatValue("EdgeUsed", -1);
                            UpdateStatEdgeUsed();
                            checkDrainEdge.setEnabled(true);
                            checkSkillEdge.setEnabled(true);
                        }
                    }
                    if (Main.data.getStatValue("EdgeUsed") > 0) {
                        Main.data.setStatValue("HoursSinceEdgeRefresh", 0);
                        if (!Sleepless || (Main.data.getQualityValue("Insomnia") == 1)) {
                            Main.data.addStatValue("EdgeUsed", -1);
                            UpdateStatEdgeUsed();
                            checkDrainEdge.setEnabled(true);
                            checkSkillEdge.setEnabled(true);
                        }
                    }
                    Main.data.setStatValue("SleeplessHours", 0);
                    Main.data.setStatValue("ConsecutiveRest", 0);
                    valueHours.setText(String.valueOf(Main.data.getStatValue("HoursThisSession")));
                    UpdateDamage();
                }
            }
        });

        useService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                secondFading.setVisibility(View.INVISIBLE);
                secondSkill.setVisibility(View.INVISIBLE);
                UpdateServices((Main.data.getCurrentSprite().getServicesOwed() - 1));
            }
        });

        //Create Listeners
        npSpriteRating.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                secondFading.setVisibility(View.INVISIBLE);
                secondSkill.setVisibility(View.INVISIBLE);
                Main.data.getCurrentSprite().setRating(newVal);
                Main.data.SaveSpriteToDB();
                UpdateCompileSpriteList();
                Main.data.SaveSpriteToDB();
            }
        });

        checkDrainEdge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Main.data.getStatValue("EdgeUsed") < (Main.data.getStatValue("Edge") - 1)) {
                        checkSkillEdge.setEnabled(true);
                    } else {
                        checkSkillEdge.setEnabled(false);
                    }
                } else {
                    if (Main.data.getStatValue("EdgeUsed") < Main.data.getStatValue("Edge")) {
                        checkSkillEdge.setEnabled(true);
                    } else {
                        checkSkillEdge.setEnabled(false);
                    }
                }
            }
        });

        checkSkillEdge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Main.data.getStatValue("EdgeUsed") < (Main.data.getStatValue("Edge") - 1)) {
                        checkDrainEdge.setEnabled(true);
                    } else {
                        checkDrainEdge.setEnabled(false);
                    }
                } else {
                    if (Main.data.getStatValue("EdgeUsed") < Main.data.getStatValue("Edge")) {
                        checkDrainEdge.setEnabled(true);
                    } else {
                        checkDrainEdge.setEnabled(false);
                    }
                }
            }
        });
        compileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int DamagePenalties;
                int NetHits;
                int SpriteRoll;
                Main.data.setStatValue("ConsecutiveRest", 0);
                //Damage penalties
                int temppenalties = (int) (Math.floor((Main.data.getStatValue("Stun")-Main.data.getQualityValue("High Pain Tolerance")) / (3-Main.data.getQualityValue("Low Pain Tolerance"))));
                if(temppenalties<0){temppenalties=0;}
                DamagePenalties = (int) Math.floor((Main.data.getStatValue("Physical")-Main.data.getQualityValue("High Pain Tolerance")) / (3-Main.data.getQualityValue("Low Pain Tolerance")));
                if(DamagePenalties<0){DamagePenalties=0;}
                DamagePenalties+=temppenalties;

                if (checkDrainEdge.isChecked()) {
                    Main.data.addStatValue("EdgeUsed", 1);
                    UpdateStatEdgeUsed();
                }
                if (checkSkillEdge.isChecked()) {
                    Main.data.addStatValue("EdgeUsed", 1);
                    UpdateStatEdgeUsed();
                }

                //Make Opposed Dice Roll
                if (Main.data.getCurrentSprite().getServicesOwed() == 0) {//New Sprite, so Compile

                    if(Main.data.DoIHaveBadLuck()&&checkSkillEdge.isChecked()){//BADLUCK
                        NetHits = dice.rollDice((Main.data.getStatValue("Resonance") + Main.data.getSkillValue("Compiling", Main.data.getCurrentSprite().getType()) - DamagePenalties -Main.data.getStatValue("Edge")), false, Main.data.getCurrentSprite().getRating());
                        //Disable secondChance skill button
                        secondSkill.setVisibility(View.INVISIBLE);
                    }else{//NORMAL
                        if(checkDrainEdge.isChecked()){
                            //Disable secondChance skill button
                            secondSkill.setVisibility(View.INVISIBLE);
                            NetHits = dice.rollDice((Main.data.getStatValue("Resonance") + Main.data.getSkillValue("Compiling", Main.data.getCurrentSprite().getType()) - DamagePenalties +Main.data.getStatValue("Edge")), true, Main.data.getCurrentSprite().getRating());
                        }else{
                            //Enable secondChance skill button
                            if(Main.data.getStatValue("Edge")>Main.data.getStatValue("EdgeUsed")){
                                secondSkill.setVisibility(View.VISIBLE);
                            }else{
                                secondSkill.setVisibility(View.INVISIBLE);
                            }

                            NetHits = dice.rollDice((Main.data.getStatValue("Resonance") + Main.data.getSkillValue("Compiling", Main.data.getCurrentSprite().getType()) - DamagePenalties), false, Main.data.getCurrentSprite().getRating());
                        }

                    }

                    SpriteRoll = dice.rollDice(Main.data.getCurrentSprite().getRating(), false);
                    secondSkillOpposed =SpriteRoll;

                } else {//Already has services, so Register
                    Main.data.addStatValue("HoursThisSession", Main.data.getCurrentSprite().getRating());  //Registering takes hours
                    Main.data.addStatValue("HoursSinceEdgeRefresh", Main.data.getCurrentSprite().getRating());
                    //TODO Check that this is actually working correctly
                    //Check for fatigue before making the roll, no fair registering your sprite before you pass out from sleep exhaustion.
                    if (Main.data.getStatValue("SleeplessHours") + Main.data.getCurrentSprite().getRating() >= 24) {//If you've been awake 24 hours you start taking stun.  24, 27, 30, etc hours
                        int sleepydamage = 1;
                        int sleepyresist = 0;
                        for (int sleepycounter = Main.data.getStatValue("SleeplessHours") + 1; sleepycounter <= Main.data.getStatValue("SleeplessHours") + Main.data.getCurrentSprite().getRating(); sleepycounter++) {
                            double actualsleepy = ((float) sleepycounter - 24) / 3;
                            double floorsleepy = Math.floor(((float) sleepycounter - 24) / 3);
                            if (actualsleepy == floorsleepy) {
                                sleepydamage = (int) Math.floor((sleepycounter - 24) / 3) + 1;
                                sleepyresist = dice.rollDice(Main.data.getStatValue("Body") + Main.data.getStatValue("Willpower")+2*(Main.data.getQualityValue("Quick Healer")-Main.data.getQualityValue("Slow Healer"))+Main.data.getQualityValue("Toughness"), false);
                                Toast.makeText(v.getContext(), "Resisting " + sleepydamage + "S from fatigue.", Toast.LENGTH_SHORT).show();
                                if (sleepydamage > sleepyresist) {
                                    Main.data.addStatValue("Stun", (sleepydamage - sleepyresist));
                                    UpdateDamage();
                                    DamagePenalties = (int) (Math.floor(Main.data.getStatValue("Stun") / 3) + Math.floor(Main.data.getStatValue("Physical") / 3));
                                }
                            }
                        }
                    }
                    Main.data.addStatValue("SleeplessHours", Main.data.getCurrentSprite().getRating());

                    if(Main.data.DoIHaveBadLuck()&&checkSkillEdge.isChecked()){
                        NetHits = dice.rollDice((Main.data.getStatValue("Resonance") + Main.data.getSkillValue("Registering", Main.data.getCurrentSprite().getType()) - DamagePenalties -Main.data.getStatValue("Edge")), false, Main.data.getCurrentSprite().getRating());
                        secondSkill.setVisibility(View.INVISIBLE);
                    }else{
                        if(checkSkillEdge.isChecked()){
                            //disable secondchance fading
                            secondSkill.setVisibility(View.INVISIBLE);
                            NetHits = dice.rollDice((Main.data.getStatValue("Resonance") + Main.data.getSkillValue("Registering", Main.data.getCurrentSprite().getType()) - DamagePenalties +Main.data.getStatValue("Edge")), true, Main.data.getCurrentSprite().getRating());
                        }else{
                            //enable secondchance fading
                            if(Main.data.getStatValue("Edge")>Main.data.getStatValue("EdgeUsed")){
                                secondSkill.setVisibility(View.VISIBLE);
                            }else{
                                secondSkill.setVisibility(View.INVISIBLE);
                            }

                            NetHits = dice.rollDice((Main.data.getStatValue("Resonance") + Main.data.getSkillValue("Registering", Main.data.getCurrentSprite().getType()) - DamagePenalties), false, Main.data.getCurrentSprite().getRating());
                        }

                    }
                    SpriteRoll = dice.rollDice(Main.data.getCurrentSprite().getRating() * 2, false);
                    secondSkillOpposed =SpriteRoll;
                }
                if (Main.data.getStatValue("Stun") < (9 + Math.floor(Main.data.getStatValue("Willpower") / 2))) {//Only do stuff if they didn't pass out from sleep deprivation before they could finish.  Nothing happens if they did.
                    //Add net successes
                    secondSkillSuccesses =NetHits;
                    NetHits -= SpriteRoll;
                    if (NetHits <= 0) {
                        NetHits = 0;
                        if (Main.data.getCurrentSprite().getRegistered()==1) {
                            NetHits--;
                        } //Failed attempts to Register already registered sprites still cost a services.
                    } else {//Positive number of net hits, so the sprite is registered
                        if (Main.data.getCurrentSprite().getRegistered()==1) {
                            NetHits--;
                        } //Successful attempts to Register already registered sprites still cost a service.
                        if (Main.data.getCurrentSprite().getServicesOwed() > 0) {//Already had hits, now has more, so it's registered.
                            Main.data.getCurrentSprite().setRegistered(1);
                            Main.data.getCurrentSprite().setGODScore(0);
                        }
                    }
                    Main.data.getCurrentSprite().setServicesOwed(Main.data.getCurrentSprite().getServicesOwed() + NetHits);
                    if (Main.data.getCurrentSprite().getServicesOwed() == 0) {
                        Main.data.getCurrentSprite().setRegistered(0);
                    }
                    Main.data.SaveSpriteToDB();
                    //Drain Resistance
                    if (SpriteRoll < 1) {
                        SpriteRoll = 1;
                    }//Minimum drain, this number is doubled in the next step.
                    if(Main.data.getQualityValue("Sensitive System")==1){
                        if(dice.rollDice(Main.data.getStatValue("Willpower"),false)<2){SpriteRoll++;}
                    }
                    if(Main.data.DoIHaveBadLuck()&&checkDrainEdge.isChecked()){
                        secondFading.setVisibility(View.INVISIBLE);

                        SpriteRoll = 2 * SpriteRoll - dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower") - Main.data.getStatValue("Edge"), false);
                    }else{
                        if(checkDrainEdge.isChecked()){
                            SpriteRoll = 2 * SpriteRoll - dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower") + Main.data.getStatValue("Edge"), true);
                            //disable second chance fading
                            secondFading.setVisibility(View.INVISIBLE);

                        }else{
                            //enable second chance fading
                            if(Main.data.getStatValue("Edge")>Main.data.getStatValue("EdgeUsed")){
                                secondFading.setVisibility(View.VISIBLE);
                            }else{
                                secondFading.setVisibility(View.INVISIBLE);
                            }
                            secondFadeSuccesses= dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower"), false);
                            secondFadeOpposed=SpriteRoll*2;
                            SpriteRoll = secondFadeOpposed - secondFadeSuccesses;

                        }

                    }
                    if (SpriteRoll < 0) {
                        SpriteRoll = 0;
                    }
                    if (Main.data.getCurrentSprite().getRating() > Main.data.getStatValue("Resonance")) {//Big sprite, physical damage
                        Main.data.addStatValue("Physical", SpriteRoll);
                    } else {//Small Sprite: Stun
                        Main.data.addStatValue("Stun", SpriteRoll);

                    }
                    //UpdateDamage(container);
                    //UpdateServices(container);
                    //UpdateRest(container);
                    //UpdateRating(container);
                    //UpdateHours(container);
                    //UpdateTypePicker(container);


                    //UpdateUseService(container); Handled in UpdateServices
                    //UpdateCompile(container); Handled in UpdateServices
                    // UpdateCompileSpriteList(container); Handled in UpdateServices

                }else{
                    Toast.makeText(getActivity(),"You passed out due to exhaustion!", Toast.LENGTH_SHORT).show();
                }

                checkDrainEdge.setChecked(false);
                checkSkillEdge.setChecked(false);

                UpdateDisplay();

            }
        });
        return v;
    }

    //Post-edge skill
    public void secondChanceFading() {
        //Get failed roll count
        //Roll those dice again

        //Decrement edge
        Main.data.addStatValue("EdgeUsed",1);
        UpdateStatEdgeUsed();
        //Make this button invisible, check on the other second chance button
        Button secondSkill = (Button) getActivity().findViewById(R.id.SecondSkill);
        Button secondFading = (Button) getActivity().findViewById(R.id.SecondFading);

        if(Main.data.getStatValue("Edge")<=Main.data.getStatValue("EdgeUsed")){
            secondSkill.setVisibility(View.INVISIBLE);
        }
        secondFading.setVisibility(View.INVISIBLE);


        int results=dice.rollDice(Main.data.getStatValue("Resonance") + Main.data.getStatValue("Willpower")-secondFadeSuccesses, false, secondFadeOpposed-secondFadeSuccesses);
        //Determine if fading was physical or stun, remove newfade from that track
        if(Main.data.getCurrentSprite().getRating()>Main.data.getStatValue("Resonance")){//physical
            UpdateDamage(Main.data.getStatValue("Stun"), Main.data.getStatValue("Physical")-results);
        }else {//   stun
            UpdateDamage(Main.data.getStatValue("Stun")-results, Main.data.getStatValue("Physical"));
        }

    }
    //Post-edge drain
    public void secondChanceSkill(){
        //Damage penalties
        //Determine if fading was physical or stun, remove most recent fading from that track when calculating penalties
        int stunmod=0;
        int physmod=0;
        if(Main.data.getCurrentSprite().getRating()>Main.data.getStatValue("Resonance")){//physical
            physmod=secondFadeOpposed;
        }else {//   stun
            stunmod=secondFadeOpposed;
        }
        int temppenalties = (int) (Math.floor((Main.data.getStatValue("Stun")-stunmod-Main.data.getQualityValue("High Pain Tolerance")) / (3-Main.data.getQualityValue("Low Pain Tolerance"))));
        if(temppenalties<0){temppenalties=0;}
        int DamagePenalties = (int) Math.floor((Main.data.getStatValue("Physical")-physmod-Main.data.getQualityValue("High Pain Tolerance")) / (3-Main.data.getQualityValue("Low Pain Tolerance")));
        if(DamagePenalties<0){DamagePenalties=0;}
        DamagePenalties+=temppenalties;

        //Roll those dice again
        int results=dice.rollDice((Main.data.getStatValue("Resonance") + Main.data.getSkillValue("Compiling", Main.data.getCurrentSprite().getType()) - DamagePenalties -secondSkillSuccesses), false, Main.data.getCurrentSprite().getRating()-secondSkillSuccesses);
        //Add results to services
        Main.data.getCurrentSprite().setServicesOwed(results+Main.data.getCurrentSprite().getServicesOwed());
        //Decrement edge
        Main.data.addStatValue("EdgeUsed",1);
        UpdateStatEdgeUsed();
        //Make this button invisible, check on the other second chance button
        Button secondSkill = (Button) getActivity().findViewById(R.id.SecondSkill);
        Button secondFading = (Button) getActivity().findViewById(R.id.SecondFading);

        if(Main.data.getStatValue("Edge")<=Main.data.getStatValue("EdgeUsed")){
            secondFading.setVisibility(View.INVISIBLE);
        }
        secondSkill.setVisibility(View.INVISIBLE);
        UpdateServices();
        UpdateCompileSpriteList();
    }

    private void UpdateStatEdgeUsed(){
        EditText ku = (EditText) getActivity().findViewById(R.id.editEdgeUsed);
        if(ku!=null){
            ku.setText(String.valueOf(Main.data.getStatValue("EdgeUsed")));
        }
    }

    private void UpdateStatTime(){
        EditText chr = (EditText) getActivity().findViewById(R.id.editConsecutiveHoursRested);
        if(chr!=null){
            EditText hws = (EditText) getActivity().findViewById(R.id.editHoursWithoutSleep);
            EditText hts = (EditText) getActivity().findViewById(R.id.editHoursThisSession);
            EditText hskr = (EditText) getActivity().findViewById(R.id.editHoursSinceEdgeRefresh);
            chr.setText(String.valueOf(Main.data.getStatValue("ConsecutiveRest")));
            hws.setText(String.valueOf(Main.data.getStatValue("SleeplessHours")));
            hts.setText(String.valueOf(Main.data.getStatValue("HoursThisSession")));
            hskr.setText(String.valueOf(Main.data.getStatValue("HoursSinceEdgeRefresh")));
        }
    }

    public static CompileFragment newInstance() {
        CompileFragment f = new CompileFragment();
/*        Bundle b = new Bundle();
        b.putString("msg",String.valueOf(data.getStatValue("Compiling));

        f.setArguments(b);
      */
        return f;

    }


    private void UpdateSpriteType(int spriteType) {
        Main.data.getCurrentSprite().setSpriteType(spriteType);
        Spinner typeSpinner = (Spinner) getActivity().findViewById(R.id.CompileSpinnerSpriteType);
        typeSpinner.setEnabled(Main.data.getCurrentSprite().getServicesOwed() == 0);
    }
    private void UpdateServices() {
        UpdateServices(Main.data.getCurrentSprite().getServicesOwed());
    }

    private void UpdateServices(int services) {
        if(services!=Main.data.getCurrentSprite().getServicesOwed()) {
            Main.data.getCurrentSprite().setServicesOwed(services);
            Main.data.SaveSpriteToDB();
            UpdateCompileSpriteList();
            UpdateCompile();
            UpdateUseService();
        }
    }

    // /UpdateRating
    private void UpdateRating() {
        UpdateRating(Main.data.getCurrentSprite().getRating());
    }

    private void UpdateRating(int rating) {
        if(rating!=Main.data.getCurrentSprite().getRating()) {
            Main.data.getCurrentSprite().setRating(rating);
            UpdateForcePicker();
        }
    }

    //UpdateHours
    private void UpdateHours() {
        UpdateHours(Main.data.getStatValue("HoursThisSession"));
    }

    private void UpdateHours(int hours) {
        TextView valueHours = (TextView) getActivity().findViewById(R.id.valuesHours);
        if(!valueHours.getText().toString().equals(String.valueOf(hours))) {
            valueHours.setText(String.valueOf(hours));
            Main.data.setStatValue("HoursThisSession", hours);
            UpdateStatTime();
        }
    }

    //UpdateDamage             Compile Button, Rest, Use Service
    private void UpdateDamage() {
        UpdateDamage(Main.data.getStatValue("Stun"), Main.data.getStatValue("Physical"));
    }



    //UpdateRest Button        Enabled
    private void UpdateRest() {
        UpdateRest((Main.data.getStatValue("Stun") > 0) && IsAlive());
    }

    private void UpdateRest(boolean enabled) {
        Button Rest = (Button) getActivity().findViewById(R.id.Rest);
        Rest.setClickable(enabled);
        Rest.setEnabled(enabled);
    }

    private void UpdateSleep() {
        UpdateSleep(IsAlive());
    }

    private void UpdateSleep(boolean enabled) {
        Button Sleep = (Button) getActivity().findViewById(R.id.buttonSleep);
        Sleep.setClickable(enabled);
        Sleep.setEnabled(enabled);
    }

    //disable heal when stun, re-enable when no-stun and damage, disable when no damage
    private void UpdateHeal() {
        UpdateHeal((Main.data.getStatValue("Stun") == 0) && (Main.data.getStatValue("Physical") > 0) && IsAlive());
    }

    private void UpdateHeal(boolean enabled) {
        Button Heal = (Button) getActivity().findViewById(R.id.buttonHeal);
        Heal.setClickable(enabled);
        Heal.setEnabled(enabled);
    }

    private boolean IsConscious() {
        return (Main.data.getStatValue("Stun") < (Main.data.getQualityValue("Tough as Nails Stun")+ 9 + Math.floor(Main.data.getStatValue("Willpower") / 2))) && (Main.data.getStatValue("Physical") < (Main.data.getQualityValue("Tough as Nails Physical") + 9 + Math.floor(Main.data.getStatValue("Body") / 2)));
    }

    private boolean IsAlive() {
        return (Main.data.getStatValue("Physical") < (Main.data.getQualityValue("Tough as Nails Physical") + 9 + Math.floor(Main.data.getStatValue("Body") / 2)));
    }

    //UpdateUseService Button  Enabled
    private void UpdateUseService() {
        UpdateUseService(Main.data.getCurrentSprite().getServicesOwed() > 0 && IsConscious());// Has Services, isn't unconscious, isn't dying.
    }

    private void UpdateUseService(boolean enabled) {
        Button UseService = (Button) getActivity().findViewById(R.id.buttonUseService);
        UpdateForcePicker(Main.data.getCurrentSprite().getServicesOwed() == 0);
        UpdateTypePicker(Main.data.getCurrentSprite().getServicesOwed() == 0);
        UpdateCompile(Main.data.getCurrentSprite().getServicesOwed() == 0, IsConscious());
        UseService.setClickable(enabled);
        UseService.setEnabled(enabled);
        UpdateCompileSpriteList();
    }

    //Update ForcePicker Enabled
    private void UpdateForcePicker() {
        UpdateForcePicker(Main.data.getCurrentSprite().getServicesOwed() == 0);  //Looking at an unCompiled Sprite
    }

    private void UpdateForcePicker(boolean enabled) {
        NumberPicker np = (NumberPicker) getActivity().findViewById(R.id.npSpriteRating);
        np.setEnabled(enabled);
        np.setClickable(enabled);
        np.setValue(Main.data.getCurrentSprite().getRating());
        np.setMaxValue(Main.data.getStatValue("Resonance") * 2);



    }

    //Update TypePicker Enabled
    private void UpdateTypePicker() {
        UpdateTypePicker(Main.data.getCurrentSprite().getServicesOwed() == 0);  //Looking at an unCompiled Sprite
    }

    private void UpdateTypePicker(boolean enabled) {
        Spinner tp = (Spinner) getActivity().findViewById(R.id.CompileSpinnerSpriteType);
        tp.setEnabled(enabled);
        tp.setClickable(enabled);
        tp.setSelection(Main.data.getCurrentSprite().getSpriteType() - 1);
    }

    private void UpdateCompileSpriteList() {
        //Main.data.SaveSpriteToDB(); Should always be up to date
        Main.data.UpdateSpriteList();
        Spinner spriteSpinner = (Spinner) getActivity().findViewById(R.id.CompileSpinnerSprite);                                             //Update the dropdown
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Main.data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);
        if (spriteSpinner.getSelectedItemPosition() != Main.data.pvActiveSpriteId) {
            spriteSpinner.setSelection(Main.data.pvActiveSpriteId);
        }
        Spinner sspriteSpinner = (Spinner) getActivity().findViewById(R.id.SpriteSpinnerSprites);                                             //Update the dropdown
        if(sspriteSpinner!=null) {
            sspriteSpinner.setAdapter(adp1);
            if (sspriteSpinner.getSelectedItemPosition() != Main.data.pvActiveSpriteId) {
                sspriteSpinner.setSelection(Main.data.pvActiveSpriteId);
            }
        }
        //data.SaveAllToDB();Infinite loop

    }



    private void UpdateCheckBoxes() {
        CheckBox checkDrain = (CheckBox) getActivity().findViewById(R.id.DrainEdge);
        CheckBox checkSkill = (CheckBox) getActivity().findViewById(R.id.SkillEdge);
        if (checkDrain.isChecked()) {
            if (Main.data.getStatValue("EdgeUsed") < (Main.data.getStatValue("Edge") - 1)) {
                checkSkill.setEnabled(true);
            } else {
                checkSkill.setEnabled(false);
            }
        } else {
            if (Main.data.getStatValue("EdgeUsed") < Main.data.getStatValue("Edge")) {
                checkSkill.setEnabled(true);
            } else {
                checkSkill.setEnabled(false);
            }
        }
        if (checkSkill.isChecked()) {
            if (Main.data.getStatValue("EdgeUsed") < (Main.data.getStatValue("Edge") - 1)) {
                checkDrain.setEnabled(true);
            } else {
                checkDrain.setEnabled(false);
            }
        } else {
            if (Main.data.getStatValue("EdgeUsed") < Main.data.getStatValue("Edge")) {
                checkDrain.setEnabled(true);
            } else {
                checkDrain.setEnabled(false);
            }
        }
    }

    private void UpdateDamage(int stun, int physical) {
        Main.data.setStatValue("Stun", stun);
        Main.data.setStatValue("Physical", physical);
        int MaxStun = (int) Math.floor(Main.data.getStatValue("Willpower") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Stun");
        int MaxPhysical = (int) Math.floor(Main.data.getStatValue("Body") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Physical");
        int _overflow = 0;
        RatingBar stunDamage = (RatingBar) getActivity().findViewById(R.id.stunTrack);
        stunDamage.setClickable(false);
        stunDamage.setEnabled(false);
        if (stunDamage.getRating() != stun || MaxStun != stunDamage.getMax()) {
            stunDamage.setNumStars(MaxStun);
            stunDamage.setMax(MaxStun);
            if (stun > MaxStun) {//Did we exceed the stun condition monitor?
                physical += (int) Math.floor((stun - MaxStun) / 2);  //(TotalStun - StunMax)/2 rounded down is overflow
                Main.data.setStatValue("Stun", MaxStun);
            }
            stunDamage.setRating(stun);
        }
        RatingBar physicalDamage = (RatingBar) getActivity().findViewById(R.id.physicalTrack);
        physicalDamage.setClickable(false);
        physicalDamage.setEnabled(false);
        if (physicalDamage.getRating() != physical || MaxPhysical != physicalDamage.getMax()) {
            physicalDamage.setNumStars(MaxPhysical);
            physicalDamage.setMax(MaxPhysical);

            RatingBar overflowDamage = (RatingBar) getActivity().findViewById(R.id.overflowTrack);
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
        UpdateCompile();
        UpdateUseService();
        UpdateRest();
        UpdateSleep();
        UpdateHeal();
        EditText stuntext = (EditText) getActivity().findViewById(R.id.editStun);
        if(stuntext!=null){
            stuntext.setText(String.valueOf(Main.data.getStatValue("Stun")));
            EditText physicaltext = (EditText) getActivity().findViewById(R.id.editPhysical);
            physicaltext.setText(String.valueOf(Main.data.getStatValue("Physical")));
        }
        Main.data.SaveAllStatsToDB();
    }
}