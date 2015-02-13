package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
//TODO: Sprite multiplying again.  Damnit.
//ToDo Add Post-edge buttons for skill and drain. Set minimum number of hits desired for roll, re-roll failures and subtract edge if that number not met. Use Toast if edge used this way
//Fancy UI:

//Todo karma regen after 8 hours consecutive rest
//todo test consecutive rest karma reset
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
            if (((Main.data.pvSprites.size()) > Main.data.pvLogic) && Main.data.getCurrentSprite().getRegistered() == 0) { //Also only disable if we're looking at an unregistered sprite.  We can always get more services.
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
        Log.e("DEBUG", "onResume of CompileFragment");
        super.onResume();
        Main = (MainActivity)getActivity();
        UpdateDisplay();
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

        final CheckBox checkDrainKarma = (CheckBox) v.findViewById(R.id.DrainKarma);
        checkDrainKarma.setEnabled(Main.data.pvKarmaUsed<Main.data.pvKarma);

        final CheckBox checkSkillKarma = (CheckBox) v.findViewById(R.id.SkillKarma);
        checkSkillKarma.setEnabled(Main.data.pvKarmaUsed<Main.data.pvKarma);

        final TextView valueHours = (TextView) v.findViewById(R.id.valuesHours);
        valueHours.setText(String.valueOf(Main.data.pvHoursThisSession));

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
        npSpriteRating.setMaxValue(Main.data.pvResonance * 2);

        Button useService = (Button) v.findViewById(R.id.buttonUseService);
        useService.setEnabled((Main.data.getCurrentSprite().getServicesOwed()>0&&IsConscious()));


        RatingBar stunDamage = (RatingBar) v.findViewById(R.id.stunTrack);
        stunDamage.setClickable(false);
        stunDamage.setEnabled(false);
        stunDamage.setNumStars((int) Math.floor(Main.data.pvWillpower / 2) + 9 + Main.data.getToughAsNailsStun());
        stunDamage.setMax((int) Math.floor(Main.data.pvWillpower / 2) + 9 + Main.data.getToughAsNailsStun());
        stunDamage.setRating(Main.data.pvStun);

        RatingBar physicalDamage = (RatingBar) v.findViewById(R.id.physicalTrack);
        physicalDamage.setClickable(false);
        physicalDamage.setEnabled(false);
        physicalDamage.setNumStars((int) Math.floor(Main.data.pvBody / 2) + 9 +Main.data.getToughAsNailsPhysical());
        physicalDamage.setMax((int) Math.floor(Main.data.pvBody / 2) + 9+Main.data.getToughAsNailsPhysical());

        RatingBar overflowDamage = (RatingBar) v.findViewById(R.id.overflowTrack);
        overflowDamage.setClickable(false);
        overflowDamage.setEnabled(false);
        overflowDamage.setNumStars(Main.data.pvBody+Main.data.getWillToLive());
        overflowDamage.setMax(Main.data.pvBody);

        if (Main.data.pvPhysical > (int) Math.floor(Main.data.pvBody / 2) + 9+Main.data.getToughAsNailsPhysical()) {
           overflowDamage.setRating(Main.data.pvPhysical - (int) Math.floor(Main.data.pvBody / 2) + 9+Main.data.getToughAsNailsPhysical());
        }
        physicalDamage.setRating(Main.data.pvPhysical - overflowDamage.getRating());//Don't count overflow when drawing boxes of damage







        //Sprite List
        spriteSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (position != Main.data.pvActiveSpriteId) {
                            Main.data.pvActiveSpriteId = position;
                            //UpdateSprite();
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

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        Button restButton = (Button) v.findViewById(R.id.Rest);
        restButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Healing Roll
                Boolean Sleepless=false;
                if(Main.data.getInsomnia()>0){
                    Sleepless=(4<=dice.rollDice(Main.data.pvWillpower+Main.data.pvIntuition,false));
                }
                if(!Sleepless||Main.data.getInsomnia()==1) {
                    Main.data.pvStun -= dice.rollDice(Main.data.pvBody + Main.data.pvWillpower + 2 * (Main.data.getSlowHealer() + Main.data.getQuickHealer()), false);
                }
                if (Main.data.pvStun < 0) {
                    Main.data.pvStun = 0;
                }
                if(dice.isCriticalGlitch){
                    Main.data.pvStun+=dice.rollDie(3);
                }
                UpdateDamage();

                //Add hours
                //Double time for Glitches
                int RestTime=1;
                if(Sleepless&&Main.data.getInsomnia()==1){
                    RestTime=RestTime*2;
                }

                if(dice.isGlitch){
                    RestTime=RestTime*2;
                }
                    Main.data.pvHoursThisSession += RestTime;
                    Main.data.pvHoursSinceKarmaRefresh += RestTime;
                    Main.data.pvConsecutiveRest += RestTime;

                if (Main.data.pvConsecutiveRest >= 8) {
                    if (Main.data.pvHoursSinceKarmaRefresh >= 24 && Main.data.pvKarmaUsed > 0) {
                        Main.data.pvHoursSinceKarmaRefresh = 0;
                        if(!Sleepless||Main.data.getInsomnia()==1){
                            Main.data.pvKarmaUsed--;
                            UpdateStatKarmaUsed();

                            checkDrainKarma.setEnabled(true);
                            checkSkillKarma.setEnabled(true);
                        }
                    }
                    Main.data.pvSleeplessHours = 0;
                    Main.data.pvConsecutiveRest = 0;
                }


                valueHours.setText(String.valueOf(Main.data.pvHoursThisSession));
            }
        });
//Sleep Button
//Adds 8 hours, heal stun, regen karma, reset consecutive hours without sleep
        Button sleepButton = (Button) v.findViewById(R.id.buttonSleep);
        sleepButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean Sleepless=false;
                if(Main.data.getInsomnia()>0){
                    Sleepless=(4<=dice.rollDice(Main.data.pvWillpower+Main.data.pvIntuition,false));
                }
                //Healing Roll 8 times
                for (int i = 1; i <= 8; i++) {
                    if (Main.data.pvStun > 0) {
                        if(!Sleepless||Main.data.getInsomnia()==1){
                            Main.data.pvStun -= dice.rollDice(Main.data.pvBody + Main.data.pvWillpower+2*(Main.data.getSlowHealer()+Main.data.getQuickHealer()), false);
                            if(Sleepless){i++;}//Sleeping takes twice as long
                        }
                        if (Main.data.pvStun < 0) {
                            Main.data.pvStun = 0;
                        }

                        if(dice.isGlitch) {
                            i++;//Lose an hour of rest, since it takes two hours to heal this batch of damage
                            if (dice.isCriticalGlitch) {
                                Main.data.pvStun += dice.rollDie(3);
                            }
                        }
                    }
                }
                UpdateDamage();

                //Add hours
                Main.data.pvHoursThisSession += 8;
                //If it's been at least 24 hours, refresh karma.
                if (Main.data.pvHoursSinceKarmaRefresh >= 24 && Main.data.pvKarmaUsed > 0) {
                    Main.data.pvHoursSinceKarmaRefresh = 0;
                    if(!Sleepless||Main.data.getInsomnia()==1) {
                        Main.data.pvKarmaUsed--;
                        UpdateStatKarmaUsed();
                        checkDrainKarma.setEnabled(true);
                        checkSkillKarma.setEnabled(true);
                    }
                }
                Main.data.pvSleeplessHours = 0;
                Main.data.pvHoursSinceKarmaRefresh += 8;
                Main.data.pvConsecutiveRest = 0;
                valueHours.setText(String.valueOf(Main.data.pvHoursThisSession));
            }
        });

//Heal Button
//Adds 24 hours, heal stun or physical, regen karma, reset consecutive hours without sleep
        Button healButton = (Button) v.findViewById(R.id.buttonHeal);
        healButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean Sleepless = false;
                if (Main.data.getInsomnia() > 0) {
                    Sleepless = (4 <= dice.rollDice(Main.data.pvWillpower + Main.data.pvIntuition, false));
                }

                if (Main.data.pvStun > 0) {
                    //Healing Roll 24 times

                    for (int i = 1; i <= 24; i++) {
                        if (Main.data.pvStun > 0) {
                            if (!Sleepless || Main.data.getInsomnia() == 1) {
                                Main.data.pvStun -= dice.rollDice(Main.data.pvBody + Main.data.pvWillpower + 2 * (Main.data.getSlowHealer() + Main.data.getQuickHealer()), false);
                                if (Sleepless) {
                                    Main.data.pvHoursThisSession += 24;
                                }//Takes twice as long to heal
                            }
                            if (Main.data.pvStun < 0) {
                                Main.data.pvStun = 0;
                            }
                            if (dice.isGlitch) {
                                i++;//Lose an hour of rest, since it takes two hours to heal this batch of damage
                                Main.data.pvHoursThisSession += 1;
                                if (dice.isCriticalGlitch) {
                                    Main.data.pvStun += dice.rollDie(3);
                                }
                            }

                        }
                        Main.data.pvHoursThisSession += 1;
                    }
                } else {
                    if (Main.data.pvPhysical > 0) {
                        if (!Sleepless || Main.data.getInsomnia() == 1) {
                            Main.data.pvPhysical -= dice.rollDice(Main.data.pvBody * 2 + 2 * (Main.data.getSlowHealer() + Main.data.getQuickHealer()), false);
                            if (Sleepless) {
                                Main.data.pvHoursThisSession += 24;//Takes twice as long to heal
                            }
                            if (Main.data.pvPhysical <= 0) {
                                Main.data.pvPhysical = 0 + Main.data.getPieIesuDomine();
                            }
                            if (dice.isCriticalGlitch) {
                                Main.data.pvPhysical += dice.rollDie(3);
                            }
                        }
                        if (dice.isGlitch) {
                            Main.data.pvHoursThisSession += 24;
                        }
                        Main.data.pvHoursThisSession += 24;
                    }


                    //Add hours

                    //If it's been at least 24 hours, refresh karma.
                    if (Main.data.pvHoursSinceKarmaRefresh >= 24 && Main.data.pvKarmaUsed > 0) {
                        Main.data.pvHoursSinceKarmaRefresh = 0;
                        if (!Sleepless || Main.data.getInsomnia() == 1) {
                            Main.data.pvKarmaUsed--;
                            UpdateStatKarmaUsed();
                            checkDrainKarma.setEnabled(true);
                            checkSkillKarma.setEnabled(true);
                        }
                    }
                    if (Main.data.pvKarmaUsed > 0) {
                        Main.data.pvHoursSinceKarmaRefresh = 0;
                        if (!Sleepless || (Main.data.getInsomnia() == 1)) {
                            Main.data.pvKarmaUsed--;
                            UpdateStatKarmaUsed();
                            checkDrainKarma.setEnabled(true);
                            checkSkillKarma.setEnabled(true);
                        }
                    }
                    Main.data.pvSleeplessHours = 0;
                    Main.data.pvConsecutiveRest = 0;
                    valueHours.setText(String.valueOf(Main.data.pvHoursThisSession));
                    UpdateDamage();
                }
            }
        });

        useService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateServices((Main.data.getCurrentSprite().getServicesOwed() - 1));
            }
        });

        //Create Listeners
        npSpriteRating.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Main.data.getCurrentSprite().setRating(newVal);
                Main.data.SaveSpriteToDB();
                UpdateCompileSpriteList();
                Main.data.SaveSpriteToDB();
            }
        });

        checkDrainKarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Main.data.pvKarmaUsed < (Main.data.pvKarma - 1)) {
                        checkSkillKarma.setEnabled(true);
                    } else {
                        checkSkillKarma.setEnabled(false);
                    }
                } else {
                    if (Main.data.pvKarmaUsed < Main.data.pvKarma) {
                        checkSkillKarma.setEnabled(true);
                    } else {
                        checkSkillKarma.setEnabled(false);
                    }
                }
            }
        });

        checkSkillKarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Main.data.pvKarmaUsed < (Main.data.pvKarma - 1)) {
                        checkDrainKarma.setEnabled(true);
                    } else {
                        checkDrainKarma.setEnabled(false);
                    }
                } else {
                    if (Main.data.pvKarmaUsed < Main.data.pvKarma) {
                        checkDrainKarma.setEnabled(true);
                    } else {
                        checkDrainKarma.setEnabled(false);
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
                Main.data.pvConsecutiveRest = 0;
                //Damage penalties
                int temppenalties = (int) (Math.floor((Main.data.pvStun-Main.data.getHighPainTolerance()) / (3-Main.data.getLowPainTolerance())));
                if(temppenalties<0){temppenalties=0;}
                DamagePenalties = (int) Math.floor((Main.data.pvPhysical-Main.data.getHighPainTolerance()) / (3-Main.data.getLowPainTolerance()));
                if(DamagePenalties<0){DamagePenalties=0;}
                DamagePenalties+=temppenalties;


                //Make Opposed Dice Roll
                if (Main.data.getCurrentSprite().getServicesOwed() == 0) {//New Sprite, so Compile
                    NetHits = dice.rollDice((Main.data.pvResonance + Main.data.getSkillValue("Compiling", Main.data.getCurrentSprite().getType()) - DamagePenalties), checkSkillKarma.isChecked(), Main.data.getCurrentSprite().getRating());
                    SpriteRoll = dice.rollDice(Main.data.getCurrentSprite().getRating(), false);
                } else {//Already has services, so Register
                    Main.data.pvHoursThisSession += Main.data.getCurrentSprite().getRating();  //Registering takes hours
                    Main.data.pvHoursSinceKarmaRefresh += Main.data.getCurrentSprite().getRating();
                    //TODO Check that this is actually working correctly
                    //Check for fatigue before making the roll, no fair registering your sprite before you pass out from sleep exhaustion.
                    if (Main.data.pvSleeplessHours + Main.data.getCurrentSprite().getRating() >= 24) {//If you've been awake 24 hours you start taking stun.  24, 27, 30, etc hours
                        int sleepydamage = 1;
                        int sleepyresist = 0;
                        for (int sleepycounter = Main.data.pvSleeplessHours + 1; sleepycounter <= Main.data.pvSleeplessHours + Main.data.getCurrentSprite().getRating(); sleepycounter++) {
                            double actualsleepy = ((float) sleepycounter - 24) / 3;
                            double floorsleepy = Math.floor(((float) sleepycounter - 24) / 3);
                            if (actualsleepy == floorsleepy) {
                                sleepydamage = (int) Math.floor((sleepycounter - 24) / 3) + 1;
                                sleepyresist = dice.rollDice(Main.data.pvBody + Main.data.pvWillpower+2*(Main.data.getSlowHealer()+Main.data.getQuickHealer())+Main.data.getToughness(), false);
                                Toast.makeText(v.getContext(), "Resisting " + sleepydamage + "S from fatigue.", Toast.LENGTH_SHORT).show();
                                if (sleepydamage > sleepyresist) {
                                    Main.data.pvStun += (sleepydamage - sleepyresist);
                                    UpdateDamage();
                                    DamagePenalties = (int) (Math.floor(Main.data.pvStun / 3) + Math.floor(Main.data.pvPhysical / 3));
                                }
                            }
                        }
                    }
                    Main.data.pvSleeplessHours += Main.data.getCurrentSprite().getRating();

                    NetHits = dice.rollDice((Main.data.pvResonance + Main.data.getSkillValue("Registering", Main.data.getCurrentSprite().getType()) - DamagePenalties), checkSkillKarma.isChecked(), Main.data.getCurrentSprite().getRating());
                    SpriteRoll = dice.rollDice(Main.data.getCurrentSprite().getRating() * 2, false);

                }
                if (Main.data.pvStun < (9 + Math.floor(Main.data.pvWillpower / 2))) {//Only do stuff if they didn't pass out from sleep deprivation before they could finish.  Nothing happens if they did.
                    //Add net successes
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
                    if(Main.data.getSensitiveSystem()==1){
                        if(dice.rollDice(Main.data.pvWillpower,false)<2){SpriteRoll++;}
                    }
                    SpriteRoll = 2 * SpriteRoll - dice.rollDice(Main.data.pvResonance + Main.data.pvWillpower, checkDrainKarma.isChecked());
                    if (SpriteRoll < 0) {
                        SpriteRoll = 0;
                    }
                    if (Main.data.getCurrentSprite().getRating() > Main.data.pvResonance) {//Big sprite, physical damage
                        Main.data.pvPhysical += SpriteRoll;
                    } else {//Small Sprite: Stun
                        Main.data.pvStun += SpriteRoll;

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
                if (checkDrainKarma.isChecked()) {
                    //TODo:Badluck And ADD karma dice for pre-edge!
                    Main.data.pvKarmaUsed++;
                    UpdateStatKarmaUsed();
                    checkDrainKarma.setChecked(false);
                }
                if (checkSkillKarma.isChecked()) {
                    //TODo:Badluck  And ADD karma dice for pre-edge!
                    Main.data.pvKarmaUsed++;
                    UpdateStatKarmaUsed();
                    checkSkillKarma.setChecked(false);
                }

                UpdateDisplay();

            }
        });
        return v;
    }

    private void UpdateStatKarmaUsed(){
        EditText ku = (EditText) getActivity().findViewById(R.id.editKarmaUsed);
        if(ku!=null){
            ku.setText(String.valueOf(Main.data.pvKarmaUsed));
        }
    }

    private void UpdateStatTime(){
        EditText chr = (EditText) getActivity().findViewById(R.id.editConsecutiveHoursRested);
        if(chr!=null){
            EditText hws = (EditText) getActivity().findViewById(R.id.editHoursWithoutSleep);
            EditText hts = (EditText) getActivity().findViewById(R.id.editHoursThisSession);
            EditText hskr = (EditText) getActivity().findViewById(R.id.editHoursSinceKarmaRefresh);
            chr.setText(String.valueOf(Main.data.pvConsecutiveRest));
            hws.setText(String.valueOf(Main.data.pvSleeplessHours));
            hts.setText(String.valueOf(Main.data.pvHoursThisSession));
            hskr.setText(String.valueOf(Main.data.pvHoursSinceKarmaRefresh));
        }
    }

    public static CompileFragment newInstance() {
        CompileFragment f = new CompileFragment();
/*        Bundle b = new Bundle();
        b.putString("msg",String.valueOf(data.pvCompiling));

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
        UpdateHours(Main.data.pvHoursThisSession);
    }

    private void UpdateHours(int hours) {
        TextView valueHours = (TextView) getActivity().findViewById(R.id.valuesHours);
        if(valueHours.getText().toString()!=String.valueOf(hours)) {
            valueHours.setText(String.valueOf(hours));
            Main.data.pvHoursThisSession = hours;
            UpdateStatTime();
        }
    }

    //UpdateDamage             Compile Button, Rest, Use Service
    private void UpdateDamage() {
        UpdateDamage(Main.data.pvStun, Main.data.pvPhysical);
    }



    //UpdateRest Button        Enabled
    private void UpdateRest() {
        UpdateRest((Main.data.pvStun > 0) && IsAlive());
    }

    private void UpdateRest(boolean enabled) {
        Button Rest = (Button) getActivity().findViewById(R.id.Rest);
        Rest.setClickable(enabled);
        Rest.setEnabled(enabled);
    }

    //disable heal when stun, re-enable when no-stun and damage, disable when no damage
    private void UpdateHeal() {
        UpdateHeal((Main.data.pvStun == 0) && (Main.data.pvPhysical > 0) && IsAlive());
    }

    private void UpdateHeal(boolean enabled) {
        Button Heal = (Button) getActivity().findViewById(R.id.buttonHeal);
        Heal.setClickable(enabled);
        Heal.setEnabled(enabled);
    }

    private boolean IsConscious() {
        return (Main.data.pvStun < (Main.data.getToughAsNailsStun()+ 9 + Math.floor(Main.data.pvWillpower / 2))) && (Main.data.pvPhysical < (Main.data.getToughAsNailsPhysical() + 9 + Math.floor(Main.data.pvBody / 2)));
    }

    private boolean IsAlive() {
        return (Main.data.pvPhysical < (Main.data.getToughAsNailsPhysical() + 9 + Math.floor(Main.data.pvBody / 2)));
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
        np.setMaxValue(Main.data.pvResonance * 2);
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
        CheckBox checkDrain = (CheckBox) getActivity().findViewById(R.id.DrainKarma);
        CheckBox checkSkill = (CheckBox) getActivity().findViewById(R.id.SkillKarma);
        if (checkDrain.isChecked()) {
            if (Main.data.pvKarmaUsed < (Main.data.pvKarma - 1)) {
                checkSkill.setEnabled(true);
            } else {
                checkSkill.setEnabled(false);
            }
        } else {
            if (Main.data.pvKarmaUsed < Main.data.pvKarma) {
                checkSkill.setEnabled(true);
            } else {
                checkSkill.setEnabled(false);
            }
        }
        if (checkSkill.isChecked()) {
            if (Main.data.pvKarmaUsed < (Main.data.pvKarma - 1)) {
                checkDrain.setEnabled(true);
            } else {
                checkDrain.setEnabled(false);
            }
        } else {
            if (Main.data.pvKarmaUsed < Main.data.pvKarma) {
                checkDrain.setEnabled(true);
            } else {
                checkDrain.setEnabled(false);
            }
        }
    }

    private void UpdateDamage(int stun, int physical) {
        int MaxStun = (int) Math.floor(Main.data.pvWillpower / 2) + 9 + Main.data.getToughAsNailsStun();
        int MaxPhysical = (int) Math.floor(Main.data.pvBody / 2) + 9 + Main.data.getToughAsNailsPhysical();
        int _overflow = 0;
        RatingBar stunDamage = (RatingBar) getActivity().findViewById(R.id.stunTrack);
        stunDamage.setClickable(false);
        stunDamage.setEnabled(false);
        if (stunDamage.getRating() != stun || MaxStun != stunDamage.getMax()) {
            stunDamage.setNumStars(MaxStun);
            stunDamage.setMax(MaxStun);
            if (stun > MaxStun) {//Did we exceed the stun condition monitor?
                physical += (int) Math.floor((stun - MaxStun) / 2);  //(TotalStun - StunMax)/2 rounded down is overflow
                Main.data.pvStun = MaxStun;
            }
            stunDamage.setRating(Main.data.pvStun);
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
            overflowDamage.setNumStars(Main.data.pvBody);
            overflowDamage.setMax(Main.data.pvBody);

            if (physical > MaxPhysical) {
                _overflow = physical - MaxPhysical;
            }
            physicalDamage.setRating(physical - _overflow);//Don't count overflow when drawing boxes of damage
            overflowDamage.setRating(_overflow);
            Main.data.pvPhysical = physical;
        }
        UpdateCompile();
        UpdateUseService();
        UpdateRest();
        UpdateHeal();
        EditText stuntext = (EditText) getActivity().findViewById(R.id.editStun);
        if(stuntext!=null){
            stuntext.setText(String.valueOf(Main.data.pvStun));
            EditText physicaltext = (EditText) getActivity().findViewById(R.id.editPhysical);
            physicaltext.setText(String.valueOf(Main.data.pvPhysical));
        }
    }
}