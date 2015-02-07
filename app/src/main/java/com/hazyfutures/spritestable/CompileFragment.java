package com.hazyfutures.spritestable;

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
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CompileFragment extends Fragment {
    PersistentValues data = new PersistentValues();
    Dice dice = new Dice();

    //UpdateCompile Button text   Register/Compile  Enabled
    private void UpdateCompile() {
        UpdateCompile(data.getCurrentSprite().getServicesOwed() == 0, IsConscious());
    }

    private void UpdateCompile(boolean isCompile, boolean enabled) {
        Button Compile = (Button) this.getView().findViewById(R.id.Compile);
        if (isCompile) {
            Compile.setText("Compile");
        } else {
            Compile.setText("Register");
            //Disable Registration if there are more than LOGIC sprites registered.  Because there's always an unregistered sprite floating around that means the number of sprites-1== number registered
            if (((data.pvSprites.size()) > data.pvLogic) && data.getCurrentSprite().getRegistered() == 0) { //Also only disable if we're looking at an unregistered sprite.  We can always get more services.
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compile, container, false);
        data.RestoreFromDB(v.getContext());
        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        Button compileButton = (Button) v.findViewById(R.id.Compile);
        final CheckBox checkDrainKarma = (CheckBox) v.findViewById(R.id.DrainKarma);
        final CheckBox checkSkillKarma = (CheckBox) v.findViewById(R.id.SkillKarma);
        final TextView valueHours = (TextView) v.findViewById(R.id.valuesHours);
        Spinner spriteSpinner = (Spinner) v.findViewById(R.id.CompileSpinnerSprite);
        Spinner typeSpinner = (Spinner) v.findViewById(R.id.CompileSpinnerSpriteType);
        final NumberPicker npSpriteRating = (NumberPicker) v.findViewById(R.id.npSpriteRating);
        Button useService = (Button) v.findViewById(R.id.buttonUseService);
        //Set values
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);

        ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(v.getContext(), R.array.SpriteTypes, android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adp2);
        typeSpinner.setSelection(adp2.getPosition(data.getCurrentSprite().getType()));

        npSpriteRating.setWrapSelectorWheel(false);
        npSpriteRating.setMinValue(1);
        npSpriteRating.setMaxValue(data.pvResonance * 2);
        npSpriteRating.setValue(data.getCurrentSprite().getRating());


        //Sprite List
        spriteSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (position != data.pvActiveSpriteId) {
                            data.pvActiveSpriteId = position;
                            //UpdateSprite();
                            UpdateDisplay();
                        } else {
                            // UpdateUseService(container);
                            // UpdateCompile(container);

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
                        UpdateCompileSpriteList();
                        data.SaveSpriteToDB();

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        Button restButton = (Button) v.findViewById(R.id.Rest);
        restButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Healing Roll
                data.pvStun -= dice.rollDice(data.pvBody + data.pvWillpower, false);
                if (data.pvStun < 0) {
                    data.pvStun = 0;
                }
                UpdateDamage();

                //Add hours
                data.pvHoursThisSession += 1;
                data.pvHoursSinceKarmaRefresh += 1;
                data.pvConsecutiveRest += 1;
                if (data.pvConsecutiveRest >= 8) {
                    if (data.pvHoursSinceKarmaRefresh >= 24 && data.pvKarmaUsed > 0) {
                        data.pvHoursSinceKarmaRefresh = 0;
                        data.pvKarmaUsed--;
                        checkDrainKarma.setEnabled(true);
                        checkSkillKarma.setEnabled(true);
                    }
                    data.pvSleeplessHours = 0;
                    data.pvConsecutiveRest = 0;
                }


                valueHours.setText(String.valueOf(data.pvHoursThisSession));
            }
        });
//Sleep Button
//Adds 8 hours, heal stun, regen karma, reset consecutive hours without sleep
        Button sleepButton = (Button) v.findViewById(R.id.buttonSleep);
        sleepButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Healing Roll 8 times
                for (int i = 1; i <= 8; i++) {
                    if (data.pvStun > 0) {
                        data.pvStun -= dice.rollDice(data.pvBody + data.pvWillpower, false);
                        if (data.pvStun < 0) {
                            data.pvStun = 0;
                        }
                    }
                }
                UpdateDamage();

                //Add hours
                data.pvHoursThisSession += 8;
                //If it's been at least 24 hours, refresh karma.
                if (data.pvHoursSinceKarmaRefresh >= 24 && data.pvKarmaUsed > 0) {
                    data.pvHoursSinceKarmaRefresh = 0;
                    data.pvKarmaUsed--;
                    checkDrainKarma.setEnabled(true);
                    checkSkillKarma.setEnabled(true);
                }
                data.pvSleeplessHours = 0;
                data.pvHoursSinceKarmaRefresh += 8;
                data.pvConsecutiveRest = 0;
                valueHours.setText(String.valueOf(data.pvHoursThisSession));
            }
        });

//Heal Button
//Adds 24 hours, heal stun or physical, regen karma, reset consecutive hours without sleep
        Button healButton = (Button) v.findViewById(R.id.buttonHeal);
        healButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (data.pvStun > 0) {
                    //Healing Roll 24 times
                    for (int i = 1; i <= 24; i++) {
                        if (data.pvStun > 0) {
                            data.pvStun -= dice.rollDice(data.pvBody + data.pvWillpower, false);
                            if (data.pvStun < 0) {
                                data.pvStun = 0;
                            }
                        }
                    }
                } else {
                    if (data.pvPhysical > 0) {
                        data.pvPhysical -= dice.rollDice(data.pvBody * 2, false);
                        if (data.pvPhysical < 0) {
                            data.pvPhysical = 0;
                        }
                    }
                }


                //Add hours
                data.pvHoursThisSession += 24;
                //If it's been at least 24 hours, refresh karma.
                if (data.pvHoursSinceKarmaRefresh >= 24 && data.pvKarmaUsed > 0) {
                    data.pvHoursSinceKarmaRefresh = 0;
                    data.pvKarmaUsed--;
                    checkDrainKarma.setEnabled(true);
                    checkSkillKarma.setEnabled(true);
                }
                if (data.pvKarmaUsed > 0) {
                    data.pvHoursSinceKarmaRefresh = 0;
                    data.pvKarmaUsed--;
                    checkDrainKarma.setEnabled(true);
                    checkSkillKarma.setEnabled(true);
                }
                data.pvSleeplessHours = 0;
                data.pvConsecutiveRest = 0;
                valueHours.setText(String.valueOf(data.pvHoursThisSession));
                UpdateDamage();
            }
        });

        useService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateServices((data.getCurrentSprite().getServicesOwed() - 1));
            }
        });

        //Create Listeners
        npSpriteRating.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                data.getCurrentSprite().setRating(newVal);
                UpdateCompileSpriteList();
                data.SaveSpriteToDB();
            }
        });

        checkDrainKarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (data.pvKarmaUsed < (data.pvKarma - 1)) {
                        checkSkillKarma.setEnabled(true);
                    } else {
                        checkSkillKarma.setEnabled(false);
                    }
                } else {
                    if (data.pvKarmaUsed < (data.pvKarma)) {
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
                    if (data.pvKarmaUsed < (data.pvKarma - 1)) {
                        checkDrainKarma.setEnabled(true);
                    } else {
                        checkDrainKarma.setEnabled(false);
                    }
                } else {
                    if (data.pvKarmaUsed < (data.pvKarma)) {
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
                data.pvConsecutiveRest = 0;
                //Damage penalties
                DamagePenalties = (int) (Math.floor(data.pvStun / 3) + Math.floor(data.pvPhysical / 3));

                //Make Opposed Dice Roll
                if (data.getCurrentSprite().getServicesOwed() == 0) {//New Sprite, so Compile
                    NetHits = dice.rollDice((data.pvResonance + data.pvCompiling - DamagePenalties), checkSkillKarma.isChecked());
                    SpriteRoll = dice.rollDice(data.getCurrentSprite().getRating(), false);
                } else {//Already has services, so Register
                    data.pvHoursThisSession += data.getCurrentSprite().getRating();  //Registering takes hours
                    data.pvHoursSinceKarmaRefresh += data.getCurrentSprite().getRating();
                    //TODO Check that this is actually working correctly
                    //Check for fatigue before making the roll, no fair registering your sprite before you pass out from sleep exhaustion.
                    if (data.pvSleeplessHours + data.getCurrentSprite().getRating() >= 24) {//If you've been awake 24 hours you start taking stun.  24, 27, 30, etc hours
                        int sleepydamage = 1;
                        int sleepyresist = 0;
                        for (int sleepycounter = data.pvSleeplessHours + 1; sleepycounter <= data.pvSleeplessHours + data.getCurrentSprite().getRating(); sleepycounter++) {
                            double actualsleepy = ((float) sleepycounter - 24) / 3;
                            double floorsleepy = Math.floor(((float) sleepycounter - 24) / 3);
                            if (actualsleepy == floorsleepy) {
                                sleepydamage = (int) Math.floor((sleepycounter - 24) / 3) + 1;
                                sleepyresist = dice.rollDice(data.pvBody + data.pvWillpower, false);
                                Toast.makeText(v.getContext(), "Resisting " + sleepydamage + "S from fatigue.", Toast.LENGTH_SHORT).show();
                                if (sleepydamage > sleepyresist) {
                                    data.pvStun += (sleepydamage - sleepyresist);
                                    UpdateDamage();
                                    DamagePenalties = (int) (Math.floor(data.pvStun / 3) + Math.floor(data.pvPhysical / 3));
                                }
                            }
                        }
                    }
                    data.pvSleeplessHours += data.getCurrentSprite().getRating();

                    NetHits = dice.rollDice((data.pvResonance + data.pvRegistering - DamagePenalties), checkSkillKarma.isChecked());
                    SpriteRoll = dice.rollDice(data.getCurrentSprite().getRating() * 2, false);

                }
                if (data.pvStun < (9 + Math.floor(data.pvWillpower / 2))) {//Only do stuff if they didn't pass out from sleep deprivation before they could finish.  Nothing happens if they did.
                    //TODO Add a toast here to alert the user that they fell asleep.
                    //Add net successes
                    NetHits -= SpriteRoll;
                    if (NetHits <= 0) {
                        NetHits = 0;
                        if (data.getCurrentSprite().getRegistered()==1) {
                            NetHits--;
                        } //Failed attempts to Register already registered sprites still cost a services.
                    } else {//Positive number of net hits, so the sprite is registered
                        if (data.getCurrentSprite().getRegistered()==1) {
                            NetHits--;
                        } //Successful attempts to Register already registered sprites still cost a service.
                        if (data.getCurrentSprite().getServicesOwed() > 0) {//Already had hits, now has more, so it's registered.
                            data.getCurrentSprite().setRegistered(1);
                            data.getCurrentSprite().setGODScore(0);
                        }
                    }
                    data.getCurrentSprite().setServicesOwed(data.getCurrentSprite().getServicesOwed() + NetHits);
                    if (data.getCurrentSprite().getServicesOwed() == 0) {
                        data.getCurrentSprite().setRegistered(0);
                    }
                    //Drain Resistance
                    if (SpriteRoll < 1) {
                        SpriteRoll = 1;
                    }//Minimum drain, this number is doubled in the next step.
                    SpriteRoll = 2 * SpriteRoll - dice.rollDice(data.pvResonance + data.pvWillpower, checkDrainKarma.isChecked());
                    if (SpriteRoll < 0) {
                        SpriteRoll = 0;
                    }
                    if (data.getCurrentSprite().getRating() > data.pvResonance) {//Big sprite, physical damage
                        data.pvPhysical += SpriteRoll;
                    } else {//Small Sprite: Stun
                        data.pvStun += SpriteRoll;

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

                }
                if (checkDrainKarma.isChecked()) {
                    data.pvKarmaUsed++;
                    checkDrainKarma.setChecked(false);
                }
                if (checkSkillKarma.isChecked()) {
                    data.pvKarmaUsed++;
                    checkSkillKarma.setChecked(false);
                }

                UpdateDisplay();

            }
        });
        return v;
    }

    public static CompileFragment newInstance(PersistentValues data) {
        CompileFragment f = new CompileFragment();
        Bundle b = new Bundle();
        b.putString("msg",String.valueOf(data.pvCompiling));

        f.setArguments(b);
        return f;
    }


    private void UpdateSpriteType(int spriteType) {
        data.getCurrentSprite().setSpriteType(spriteType);
        Spinner typeSpinner = (Spinner) this.getView().findViewById(R.id.CompileSpinnerSpriteType);
        typeSpinner.setEnabled(data.getCurrentSprite().getServicesOwed() == 0);
    }
    private void UpdateServices() {
        UpdateServices(data.getCurrentSprite().getServicesOwed());
    }

    private void UpdateServices(int services) {
        data.getCurrentSprite().setServicesOwed(services);
        UpdateCompileSpriteList();
        UpdateCompile();
        UpdateUseService();
    }

    // /UpdateRating
    private void UpdateRating() {
        UpdateRating(data.getCurrentSprite().getRating());
    }

    private void UpdateRating(int rating) {
        data.getCurrentSprite().setRating(rating);
        UpdateForcePicker();
    }

    //UpdateHours
    private void UpdateHours() {
        UpdateHours(data.pvHoursThisSession);
    }

    private void UpdateHours(int hours) {
        TextView valueHours = (TextView) this.getView().findViewById(R.id.valuesHours);
        valueHours.setText(String.valueOf(hours));
        data.pvHoursThisSession = hours;
    }

    //UpdateDamage             Compile Button, Rest, Use Service
    private void UpdateDamage() {
        UpdateDamage(data.pvStun, data.pvPhysical);
    }



    //UpdateRest Button        Enabled
    private void UpdateRest() {
        UpdateRest((data.pvStun > 0) && IsAlive());
    }

    private void UpdateRest(boolean enabled) {
        Button Rest = (Button) this.getView().findViewById(R.id.Rest);
        Rest.setClickable(enabled);
        Rest.setEnabled(enabled);
    }

    //disable heal when stun, re-enable when no-stun and damage, disable when no damage
    private void UpdateHeal() {
        UpdateHeal((data.pvStun == 0) && (data.pvPhysical > 0) && IsAlive());
    }

    private void UpdateHeal(boolean enabled) {
        Button Heal = (Button) this.getView().findViewById(R.id.buttonHeal);
        Heal.setClickable(enabled);
        Heal.setEnabled(enabled);
    }

    private boolean IsConscious() {
        return (data.pvStun < (9 + Math.floor(data.pvWillpower / 2))) && (data.pvPhysical < (9 + Math.floor(data.pvBody / 2)));
    }

    private boolean IsAlive() {
        return (data.pvPhysical < (9 + Math.floor(data.pvBody / 2)));
    }

    //UpdateUseService Button  Enabled
    private void UpdateUseService() {
        UpdateUseService(data.getCurrentSprite().getServicesOwed() > 0 && IsConscious());// Has Services, isn't unconscious, isn't dying.
    }

    private void UpdateUseService(boolean enabled) {
        Button UseService = (Button) this.getView().findViewById(R.id.buttonUseService);
        UpdateForcePicker(data.getCurrentSprite().getServicesOwed() == 0);
        UpdateTypePicker(data.getCurrentSprite().getServicesOwed() == 0);
        UpdateCompile(data.getCurrentSprite().getServicesOwed() == 0, IsConscious());
        UseService.setClickable(enabled);
        UseService.setEnabled(enabled);
        UpdateCompileSpriteList();
    }

    //Update ForcePicker Enabled
    private void UpdateForcePicker() {
        UpdateForcePicker(data.getCurrentSprite().getServicesOwed() == 0);  //Looking at an unCompiled Sprite
    }

    private void UpdateForcePicker(boolean enabled) {
        NumberPicker np = (NumberPicker) this.getView().findViewById(R.id.npSpriteRating);
        np.setEnabled(enabled);
        np.setClickable(enabled);
        np.setValue(data.getCurrentSprite().getRating());
        np.setMaxValue(data.pvResonance * 2);
    }

    //Update TypePicker Enabled
    private void UpdateTypePicker() {
        UpdateTypePicker(data.getCurrentSprite().getServicesOwed() == 0);  //Looking at an unCompiled Sprite
    }

    private void UpdateTypePicker(boolean enabled) {
        Spinner tp = (Spinner) this.getView().findViewById(R.id.CompileSpinnerSpriteType);
        tp.setEnabled(enabled);
        tp.setClickable(enabled);
        tp.setSelection(data.getCurrentSprite().getSpriteType() - 1);
    }

    private void UpdateCompileSpriteList() {
        data.UpdateSprite(data.getCurrentSprite());
        data.UpdateSpriteList();
        Spinner spriteSpinner = (Spinner) this.getView().findViewById(R.id.CompileSpinnerSprite);                                             //Update the dropdown
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this.getView().getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);
        if (spriteSpinner.getSelectedItemPosition() != data.pvActiveSpriteId) {
            spriteSpinner.setSelection(data.pvActiveSpriteId);
        }
        //data.SaveAllToDB();Infinite loop

    }



    private void UpdateCheckBoxes() {
        CheckBox checkDrain = (CheckBox) this.getView().findViewById(R.id.DrainKarma);
        CheckBox checkSkill = (CheckBox) this.getView().findViewById(R.id.SkillKarma);
        if (checkDrain.isChecked()) {
            if (data.pvKarmaUsed < (data.pvKarma - 1)) {
                checkSkill.setEnabled(true);
            } else {
                checkSkill.setEnabled(false);
            }
        } else {
            if (data.pvKarmaUsed < (data.pvKarma)) {
                checkSkill.setEnabled(true);
            } else {
                checkSkill.setEnabled(false);
            }
        }
        if (checkSkill.isChecked()) {
            if (data.pvKarmaUsed < (data.pvKarma - 1)) {
                checkDrain.setEnabled(true);
            } else {
                checkDrain.setEnabled(false);
            }
        } else {
            if (data.pvKarmaUsed < (data.pvKarma)) {
                checkDrain.setEnabled(true);
            } else {
                checkDrain.setEnabled(false);
            }
        }
    }

    private void UpdateDamage(int stun, int physical) {
        int MaxStun = (int) Math.floor(data.pvWillpower / 2) + 9;
        int MaxPhysical = (int) Math.floor(data.pvBody / 2) + 9;
        int _overflow = 0;
        RatingBar stunDamage = (RatingBar) this.getView().findViewById(R.id.stunTrack);
        stunDamage.setClickable(false);
        stunDamage.setEnabled(false);
        if (stunDamage.getRating() != stun || MaxStun != stunDamage.getMax()) {
            stunDamage.setNumStars(MaxStun);
            stunDamage.setMax(MaxStun);
            if (stun > MaxStun) {//Did we exceed the stun condition monitor?
                physical += (int) Math.floor((stun - MaxStun) / 2);  //(TotalStun - StunMax)/2 rounded down is overflow
                data.pvStun = MaxStun;
            }
            stunDamage.setRating(data.pvStun);
        }
        RatingBar physicalDamage = (RatingBar) this.getView().findViewById(R.id.physicalTrack);
        physicalDamage.setClickable(false);
        physicalDamage.setEnabled(false);
        if (physicalDamage.getRating() != physical || MaxPhysical != physicalDamage.getMax()) {
            physicalDamage.setNumStars(MaxPhysical);
            physicalDamage.setMax(MaxPhysical);

            RatingBar overflowDamage = (RatingBar) this.getView().findViewById(R.id.overflowTrack);
            overflowDamage.setClickable(false);
            overflowDamage.setEnabled(false);
            overflowDamage.setNumStars(data.pvBody);
            overflowDamage.setMax(data.pvBody);

            if (physical > MaxPhysical) {
                _overflow = physical - MaxPhysical;
            }
            physicalDamage.setRating(physical - _overflow);//Don't count overflow when drawing boxes of damage
            overflowDamage.setRating(_overflow);
            data.pvPhysical = physical;
        }
        UpdateCompile();
        UpdateUseService();
        UpdateRest();
        UpdateHeal();
    }
}