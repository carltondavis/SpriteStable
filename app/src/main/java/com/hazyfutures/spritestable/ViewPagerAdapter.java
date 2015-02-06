package com.hazyfutures.spritestable;
//TODO COMPILE FRAGMENT:
//TODO Something multiplying sprites in list?  Haven't seen recently
//TODO Test update for each attribute

//Todo Heal after 24 hours consecutive rest
//Todo karma regen after 8 hours consecutive rest
//todo test consecutive rest karma reset
//ToDo Change Hours to Total Hours.
//ToDo Add penalty popup warnings  Add Total Penalty display, tap it to have a toast pop up listing sources of penalties

//TODO SPRITE FRAGMENT:
//Todo Add display of sprite stats + abilities for specific sprite listed
//ToDo Add list of important qualities
//Todo: Add skill specializations  List of Checkboxes under the skill, check for them when compiling/registering
//Todo Update Database to handle qualities
//Todo Update Compile/Register/Rest to account for qualities

//TODO QUALITIES FRAGMENT:
//Todo add Insomnia A/B Checkbox to turn it on, radiobutton to pick which type, add code to check when regen karma, adds counter for failed rolls to keep karma refresh button disabled, add code to muck with healing
//ToDo Add possible Overflow/Death warnings If compiling/registering something, calculate if worst case stun/physical could max out stun or physical chart.  Warn of possibility.
//ToDo Add automated registering system: Set the amount of time to use, and it runs

//Todo: MATRIX FRAGMENT
//Todo: Add Playing hacking buttons
//Todo: Add Playing List of sprites and Sprite assistance/assist sprite radio buttons

//Fancy UI:
//ToDo Add Post-edge buttons for skill and drain. Set minimum number of hits desired for roll, re-roll failures and subtract edge if that number not met. Use Toast if edge used this way
//Todo add Toast for disabled buttons explaining why they're disabled
//Todo Someday Add statistics for rolls to include actual percentage chance of something happening to warnings
//TODO settings page: dice rolls display, Fatigue rules, fatigue warnings, Penalty popup warning, possible death warning
//ToDo: Only load from DB when persisted data doesn't exist.
//Todo: Change DB writing to happen on pause/close.  Load from DB on resume
//Todo add multiple character option
//Todo add a Spirit/Mage handler option
//Todo: Add Playing Spells buttons


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    //TODO Declare local variables for persistant data
    Context context;
    PersistentValues data=new PersistentValues();

    private Dice Dice = new Dice();
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context) {
        //TODO set link to local variables
        this.context = context;




    }


    @Override
    public int getCount() {
        //TODO Increase number as new layouts are added
        return 2;//5 pages Stats, Qualities, SpriteEditor, SpriteCompiler, MatrixActions
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
    public void onPageSelected (int position){

    }


    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
//TODO Handle Compile, Stat, Sprite, Qualities, Matrix pages
// Depending on Position, pick a layout file, create variables, inflate the layout, set the links to objects within the layout, set the initial values of items
        ViewPager viewPager = (ViewPager) container.findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        //TODO pass persistant data object
        ViewPagerAdapter adapter = new ViewPagerAdapter(container.getContext());
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

                if(i==0) {
                    data.RestoreFromDB(container.getContext());
                    UpdateDisplay(container);
                    //UpdateDamage(data.pvStun, data.pvPhysical, container);
                }else{
                    if(i==1){
                        EditText et = (EditText) container.findViewById(R.id.editStun);
                        et.setText(String.valueOf(data.pvStun));
                        et = (EditText) container.findViewById(R.id.editPhysical);
                        et.setText(String.valueOf(data.pvPhysical));
                        et = (EditText) container.findViewById(R.id.editKarmaUsed);
                        et.setText(String.valueOf(data.pvKarmaUsed));
                        et = (EditText) container.findViewById(R.id.editConsecutiveHoursRested);
                        et.setText(String.valueOf(data.pvConsecutiveRest));
                        et = (EditText) container.findViewById(R.id.editHoursWithoutSleep);
                        et.setText(String.valueOf(data.pvSleeplessHours));
                        et = (EditText) container.findViewById(R.id.editHoursThisSession);
                        et.setText(String.valueOf(data.pvHoursThisSession));
                        et = (EditText) container.findViewById(R.id.editHoursSinceKarmaRefresh);
                        et.setText(String.valueOf(data.pvHoursSinceKarmaRefresh));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        View itemView;
        //TODO Configure 5 layouts
        switch (position) {
            case 0:  //Compile
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.fragment_compile, container,
                        false);


                ((ViewPager) container).addView(itemView);
                final ViewGroup vg1 =container;
                PrepareCompileFragment(vg1);

                break;
            case 1: //Stats
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.fragment_stat, container,
                        false);
                ((ViewPager) container).addView(itemView);
                ViewGroup vg2=container;
                PrepareStatsFragment(vg2);
                break;

            case 2: //Qualities
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.fragment_qualities, container,
                        false);

                ((ViewPager) container).addView(itemView);
                break;

            case 3: //Sprite
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.fragment_sprite, container,
                        false);

                ((ViewPager) container).addView(itemView);
                break;

            case 4: //Matrix
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.fragment_matrix, container,
                        false);

                ((ViewPager) container).addView(itemView);
                break;
            default:
                inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.viewpager_item, container,
                    false);
            break;
        }


        // Add viewpager_item.xml to ViewPager

        // Add viewpager_item.xml to ViewPager

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    private void UpdateServices(ViewGroup vg){
        UpdateServices(data.getCurrentSprite().getServicesOwed(), vg);
    }
    private void UpdateServices(int services, ViewGroup vg){
        data.getCurrentSprite().setServicesOwed(services);
        UpdateSpriteList(vg);
        UpdateCompile(vg);
        UpdateUseService(vg);

    }
    //UpdateSpriteType
    //  private void UpdateSpriteType(){UpdateSpriteType(data.getCurrentSprite().getSpriteType());}
    private void UpdateSpriteType(int spriteType, ViewGroup vg){
        data.getCurrentSprite().setSpriteType(spriteType);
        final Spinner spinnerSpriteType = (Spinner) vg.findViewById(R.id.spinnerSpriteType);
        spinnerSpriteType.setEnabled(data.getCurrentSprite().getServicesOwed() == 0);
    }

    // /UpdateRating
    private void UpdateRating(ViewGroup vg){
        UpdateRating(data.getCurrentSprite().getRating(), vg);
    }

    private void UpdateRating(int rating, ViewGroup vg){
        data.getCurrentSprite().setRating(rating);
        UpdateForcePicker(vg);
    }

    //UpdateHours
    private void UpdateHours(ViewGroup vg){
        UpdateHours(data.pvHoursThisSession, vg);
    }

    private void UpdateHours(int hours, ViewGroup vg){
        final TextView ValueHours = (TextView) vg.findViewById(R.id.valuesHours);
        ValueHours.setText(String.valueOf(hours));
        data.pvHoursThisSession =hours;
    }

    //UpdateDamage             Compile Button, Rest, Use Service
    private void UpdateDamage(ViewGroup vg){
        UpdateDamage(data.pvStun,data.pvPhysical, vg);
    }

    //UpdateCompile Button text   Register/Compile  Enabled
    private void UpdateCompile(ViewGroup vg){
        UpdateCompile(data.getCurrentSprite().getServicesOwed()==0,IsConscious(), vg);
    }
    private void UpdateCompile(boolean isCompile, boolean enabled, ViewGroup vg){
        Button Compile = (Button) vg.findViewById(R.id.Compile);
        if(isCompile){
            Compile.setText("Compile");
        }else{
            Compile.setText("Register");
            //Disable Registration if there are more than LOGIC sprites registered.  Because there's always an unregistered sprite floating around that means the number of sprites-1== number registered
            if(((data.pvSprites.size())>data.pvLogic)&&data.getCurrentSprite().getRegistered()==0){ //Also only disable if we're looking at an unregistered sprite.  We can always get more services.
                enabled =false;
            }
        }
        Compile.setClickable(enabled);
        Compile.setEnabled(enabled);
    }

    //UpdateRest Button        Enabled
    private void UpdateRest(ViewGroup vg){
        UpdateRest((data.pvStun>0)&&IsAlive(), vg);
    }
    private void UpdateRest(boolean enabled, ViewGroup vg){
        Button Rest = (Button) vg.findViewById(R.id.Rest);
        Rest.setClickable(enabled);
        Rest.setEnabled(enabled);
    }

    //disable heal when stun, re-enable when no-stun and damage, disable when no damage
    private void UpdateHeal(ViewGroup vg){
        UpdateHeal((data.pvStun==0)&&(data.pvPhysical>0)&&IsAlive(), vg);
    }
    private void UpdateHeal(boolean enabled, ViewGroup vg){
        Button Heal = (Button) vg.findViewById(R.id.buttonHeal);
        Heal.setClickable(enabled);
        Heal.setEnabled(enabled);
    }

    private boolean IsConscious(){
        return (data.pvStun<(9+Math.floor(data.pvWillpower/2)))&&(data.pvPhysical<(9+Math.floor(data.pvBody/2)));
    }
    private boolean IsAlive(){
        return (data.pvPhysical<(9+Math.floor(data.pvBody/2)));
    }
    //UpdateUseService Button  Enabled
    private void UpdateUseService(ViewGroup vg){
        UpdateUseService(data.getCurrentSprite().getServicesOwed()>0&&IsConscious(), vg);// Has Services, isn't unconscious, isn't dying.
    }
    private void UpdateUseService(boolean enabled, ViewGroup vg){
        Button UseService = (Button) vg.findViewById(R.id.buttonUseService);
        UpdateForcePicker(data.getCurrentSprite().getServicesOwed()==0, vg);
        UpdateTypePicker(data.getCurrentSprite().getServicesOwed()==0, vg);
        UpdateCompile(data.getCurrentSprite().getServicesOwed()==0, IsConscious(), vg);
        UseService.setClickable(enabled);
        UseService.setEnabled(enabled);
        UpdateSpriteList(vg);
    }
    //Update ForcePicker Enabled
    private void UpdateForcePicker(ViewGroup vg){
        UpdateForcePicker(data.getCurrentSprite().getServicesOwed()==0, vg);  //Looking at an unCompiled Sprite
    }
    private void UpdateForcePicker(boolean enabled, ViewGroup vg){
        NumberPicker np = (NumberPicker) vg.findViewById(R.id.npSpriteRating);
        np.setEnabled(enabled);
        np.setClickable(enabled);
        np.setValue(data.getCurrentSprite().getRating());
        np.setMaxValue(data.pvResonance * 2);
    }

    //Update TypePicker Enabled
    private void UpdateTypePicker(ViewGroup vg){
        UpdateTypePicker(data.getCurrentSprite().getServicesOwed()==0, vg);  //Looking at an unCompiled Sprite
    }
    private void UpdateTypePicker(boolean enabled, ViewGroup vg){
        Spinner tp = (Spinner) vg.findViewById(R.id.spinnerSpriteType);
        tp.setEnabled(enabled);
        tp.setClickable(enabled);
        tp.setSelection(data.getCurrentSprite().getSpriteType()-1);
    }
    private void UpdateSpriteList(ViewGroup vg){
        data.UpdateSprite(data.getCurrentSprite());
        data.UpdateSpriteList();
        Spinner spriteSpinner = (Spinner) vg.findViewById(R.id.spinnerSprite);                                             //Update the dropdown
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(vg.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);
        if(spriteSpinner.getSelectedItemPosition()!=data.pvActiveSpriteId) {
            spriteSpinner.setSelection(data.pvActiveSpriteId);
       }
        //data.SaveAllToDB();Infinite loop

    }

    private void UpdateDisplay(ViewGroup vg){
        UpdateServices(vg);
        UpdateDamage(vg);
        //UpdateUseService(vg);in Services
        //UpdateRest(vg); in damage
        UpdateRating(vg);
        //UpdateCompile(vg);in Services
        UpdateHours(vg);
        UpdateTypePicker(vg);
        //UpdateSpriteList(vg); in Services
        UpdateCheckBoxes(vg);

    }

    private void UpdateCheckBoxes(ViewGroup vg){
        CheckBox checkDrain = (CheckBox) vg.findViewById(R.id.DrainKarma);
        CheckBox checkSkill = (CheckBox) vg.findViewById(R.id.SkillKarma);
        if(checkDrain.isChecked()){
            if(data.pvKarmaUsed<(data.pvKarma-1)){
                checkSkill.setEnabled(true);
            }else{
                checkSkill.setEnabled(false);
            }
        }else{
            if(data.pvKarmaUsed<(data.pvKarma)){
                checkSkill.setEnabled(true);
            }else{
                checkSkill.setEnabled(false);
            }
        }
        if(checkSkill.isChecked()){
            if(data.pvKarmaUsed<(data.pvKarma-1)){
                checkDrain.setEnabled(true);
            }else{
                checkDrain.setEnabled(false);
            }
        }else{
            if(data.pvKarmaUsed<(data.pvKarma)){
                checkDrain.setEnabled(true);
            }else{
                checkDrain.setEnabled(false);
            }
        }
    }
    private void UpdateDamage(int stun, int physical, ViewGroup vg) {
        int MaxStun=(int) Math.floor(data.pvWillpower / 2) + 9;
        int MaxPhysical=(int) Math.floor(data.pvBody / 2) + 9;
        int _overflow=0;
        RatingBar stunDamage = (RatingBar) vg.findViewById(R.id.stunTrack);
        if(stunDamage.getRating()!=stun||MaxStun!=stunDamage.getMax()) {
            stunDamage.setNumStars(MaxStun);
            stunDamage.setMax(MaxStun);
            if (stun > MaxStun) {//Did we exceed the stun condition monitor?
                physical += (int) Math.floor((stun - MaxStun) / 2);  //(TotalStun - StunMax)/2 rounded down is overflow
                data.pvStun = MaxStun;
            }
            stunDamage.setRating(data.pvStun);
        }
        RatingBar physicalDamage = (RatingBar) vg.findViewById(R.id.physicalTrack);
        if(physicalDamage.getRating()!=physical||MaxPhysical!=physicalDamage.getMax()) {
            physicalDamage.setNumStars(MaxPhysical);
            physicalDamage.setMax(MaxPhysical);

            RatingBar overflowDamage = (RatingBar) vg.findViewById(R.id.overflowTrack);
            overflowDamage.setNumStars(data.pvBody);
            overflowDamage.setMax(data.pvBody);

            if (physical > MaxPhysical) {
                _overflow = physical - MaxPhysical;
            }
            physicalDamage.setRating(physical - _overflow);//Don't count overflow when drawing boxes of damage
            overflowDamage.setRating(_overflow);
            data.pvPhysical = physical;
        }
        UpdateCompile(vg);
        UpdateUseService(vg);
        UpdateRest(vg);
        UpdateHeal(vg);
    }

   public void PrepareCompileFragment(final ViewGroup container){
       //Toast.makeText(container.getContext(), "Compile", Toast.LENGTH_SHORT).show();

       data.RestoreFromDB(container.getContext());


        Button compileButton = (Button) container.findViewById(R.id.Compile);
        final CheckBox checkDrainKarma = (CheckBox) container.findViewById(R.id.DrainKarma);
        final CheckBox checkSkillKarma = (CheckBox) container.findViewById(R.id.SkillKarma);
        Spinner spriteSpinner = (Spinner) container.findViewById(R.id.spinnerSprite);
        Spinner typeSpinner = (Spinner) container.findViewById(R.id.spinnerSpriteType);
        final NumberPicker npSpriteRating = (NumberPicker) container.findViewById(R.id.npSpriteRating);
        Button useService = (Button) container.findViewById(R.id.buttonUseService);


        //Set values
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);

        ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(container.getContext(), R.array.SpriteTypes, android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adp2);
        typeSpinner.setSelection(adp2.getPosition(data.getCurrentSprite().getType()));

        npSpriteRating.setWrapSelectorWheel(false);
        npSpriteRating.setMinValue(1);
        npSpriteRating.setMaxValue(data.pvResonance*2);
        npSpriteRating.setValue(data.getCurrentSprite().getRating());






       //Sprite List
       spriteSpinner.setOnItemSelectedListener(
               new AdapterView.OnItemSelectedListener() {
                   public void onItemSelected(
                           AdapterView<?> parent, View view, int position, long id) {
                       if(position!=data.pvActiveSpriteId) {
                            data.pvActiveSpriteId= position;
                       //UpdateSprite();
                           UpdateDisplay(container);
                       }else{
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

                       UpdateSpriteType(position + 1, container);
                       UpdateSpriteList(container);
                       data.SaveSpriteToDB();

                   }

                   public void onNothingSelected(AdapterView<?> parent) {
                   }
               });

       Button restButton = (Button) container.findViewById(R.id.Rest);
       restButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               //Healing Roll
               data.pvStun -= Dice.rollDice(data.pvBody + data.pvWillpower, false);
               if (data.pvStun < 0) {
                   data.pvStun = 0;
               }
               UpdateDamage(container);

               //Add hours
               data.pvHoursThisSession += 1;
               data.pvHoursSinceKarmaRefresh+=1;
               data.pvConsecutiveRest+=1;
               if(data.pvConsecutiveRest>=8){
                   if(data.pvHoursSinceKarmaRefresh>=24&&data.pvKarmaUsed>0){
                       data.pvHoursSinceKarmaRefresh=0;
                       data.pvKarmaUsed--;
                       checkDrainKarma.setEnabled(true);
                       checkSkillKarma.setEnabled(true);
                   }
                   data.pvSleeplessHours=0;
                   data.pvConsecutiveRest=0;
               }

               TextView valueHours = (TextView) container.findViewById(R.id.valuesHours);
               valueHours.setText(String.valueOf(data.pvHoursThisSession));
           }
       });
//Sleep Button
//Adds 8 hours, heal stun, regen karma, reset consecutive hours without sleep
       Button sleepButton = (Button) container.findViewById(R.id.buttonSleep);
       sleepButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               //Healing Roll 8 times
               for(int i=1;i<=8;i++){
                   if(data.pvStun>0){
                       data.pvStun -= Dice.rollDice(data.pvBody + data.pvWillpower, false);
                       if (data.pvStun < 0) {
                           data.pvStun = 0;
               }}}
               UpdateDamage(container);

               //Add hours
               data.pvHoursThisSession += 8;
               //If it's been at least 24 hours, refresh karma.
               if(data.pvHoursSinceKarmaRefresh>=24&&data.pvKarmaUsed>0){
                   data.pvHoursSinceKarmaRefresh=0;
                   data.pvKarmaUsed--;
                   checkDrainKarma.setEnabled(true);
                   checkSkillKarma.setEnabled(true);
               }
               data.pvSleeplessHours=0;
               data.pvHoursSinceKarmaRefresh+=8;
               data.pvConsecutiveRest=0;
               TextView valueHours = (TextView) container.findViewById(R.id.valuesHours);
               valueHours.setText(String.valueOf(data.pvHoursThisSession));
           }
       });

//Heal Button
//Adds 24 hours, heal stun or physical, regen karma, reset consecutive hours without sleep
       Button healButton = (Button) container.findViewById(R.id.buttonHeal);
       healButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
                if(data.pvStun>0) {
                    //Healing Roll 24 times
                    for (int i = 1; i <= 24; i++) {
                        if (data.pvStun > 0) {
                            data.pvStun -= Dice.rollDice(data.pvBody + data.pvWillpower, false);
                            if (data.pvStun < 0) {
                                data.pvStun = 0;
                            }
                        }
                    }
                }else{
                    if(data.pvPhysical>0){
                        data.pvPhysical-=Dice.rollDice(data.pvBody*2,false);
                        if (data.pvPhysical < 0) {
                            data.pvPhysical = 0;
                        }
                    }
                }


               //Add hours
               data.pvHoursThisSession += 24;
               //If it's been at least 24 hours, refresh karma.
               if(data.pvHoursSinceKarmaRefresh>=24&&data.pvKarmaUsed>0){
                   data.pvHoursSinceKarmaRefresh=0;
                   data.pvKarmaUsed--;
                   checkDrainKarma.setEnabled(true);
                   checkSkillKarma.setEnabled(true);
               }
               if(data.pvKarmaUsed>0){
                   data.pvHoursSinceKarmaRefresh=0;
                   data.pvKarmaUsed--;
                   checkDrainKarma.setEnabled(true);
                   checkSkillKarma.setEnabled(true);
               }
               data.pvSleeplessHours=0;
               data.pvConsecutiveRest=0;
               TextView valueHours = (TextView) container.findViewById(R.id.valuesHours);
               valueHours.setText(String.valueOf(data.pvHoursThisSession));
               UpdateDamage(container);
           }
       });

       useService.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
            UpdateServices((data.getCurrentSprite().getServicesOwed()-1), container);
           }
       });

        //Create Listeners
        npSpriteRating.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                data.getCurrentSprite().setRating(newVal);
                UpdateSpriteList(container);
                /*Spinner sSpinner = (Spinner) container.findViewById(R.id.spinnerSprite);                                             //Update the dropdown
                ArrayAdapter<String> adp1=new ArrayAdapter<>(container.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
                adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                sSpinner.setAdapter(adp1);
                sSpinner.setSelection(data.pvActiveSpriteId);*/
                data.SaveSpriteToDB();
            }
        });

       checkDrainKarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   if(data.pvKarmaUsed<(data.pvKarma-1)){
                       checkSkillKarma.setEnabled(true);
                   }else{
                       checkSkillKarma.setEnabled(false);
                   }
               }else{
                   if(data.pvKarmaUsed<(data.pvKarma)){
                       checkSkillKarma.setEnabled(true);
                   }else{
                       checkSkillKarma.setEnabled(false);
                   }
               }
           }
       });

       checkSkillKarma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   if(data.pvKarmaUsed<(data.pvKarma-1)){
                       checkDrainKarma.setEnabled(true);
                   }else{
                       checkDrainKarma.setEnabled(false);
                   }
               }else{
                   if(data.pvKarmaUsed<(data.pvKarma)){
                       checkDrainKarma.setEnabled(true);
                   }else{
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
                data.pvConsecutiveRest=0;
                //Damage penalties
                DamagePenalties = (int) (Math.floor(data.pvStun / 3) + Math.floor(data.pvPhysical / 3));

                //Make Opposed Dice Roll
                if (data.getCurrentSprite().getServicesOwed() == 0) {//New Sprite, so Compile
                    NetHits = Dice.rollDice((data.pvResonance + data.pvCompiling - DamagePenalties), checkSkillKarma.isChecked());
                    SpriteRoll = Dice.rollDice(data.getCurrentSprite().getRating(), false);
                } else {//Already has services, so Register
                    data.pvHoursThisSession += data.getCurrentSprite().getRating();  //Registering takes hours
                    data.pvHoursSinceKarmaRefresh+=data.getCurrentSprite().getRating();
                    //TODO Check that this is actually working correctly
                    //Check for fatigue before making the roll, no fair registering your sprite before you pass out from sleep exhaustion.
                    if(data.pvSleeplessHours+data.getCurrentSprite().getRating()>=24){//If you've been awake 24 hours you start taking stun.  24, 27, 30, etc hours
                        int sleepydamage=1;
                        int sleepyresist=0;
                        for(int sleepycounter=data.pvSleeplessHours+1;sleepycounter<=data.pvSleeplessHours+data.getCurrentSprite().getRating();sleepycounter++){
                            double actualsleepy =((float) sleepycounter-24)/3;
                            double floorsleepy=Math.floor(((float) sleepycounter-24)/3);
                            if(actualsleepy==floorsleepy){
                                sleepydamage=(int) Math.floor((sleepycounter-24)/3)+1;
                                sleepyresist=Dice.rollDice(data.pvBody+data.pvWillpower, false);
                                Toast.makeText(container.getContext(),"Resisting " + sleepydamage + "S from fatigue.", Toast.LENGTH_SHORT).show();
                                if(sleepydamage>sleepyresist){
                                    data.pvStun+=(sleepydamage-sleepyresist);
                                    UpdateDamage(container);
                                    DamagePenalties = (int) (Math.floor(data.pvStun / 3) + Math.floor(data.pvPhysical / 3));
                                }
                            }
                        }
                    }
                    data.pvSleeplessHours+=data.getCurrentSprite().getRating();

                    NetHits = Dice.rollDice((data.pvResonance + data.pvRegistering - DamagePenalties), checkSkillKarma.isChecked());
                    SpriteRoll = Dice.rollDice(data.getCurrentSprite().getRating() * 2, false);

                }
            if(data.pvStun<(9+Math.floor(data.pvWillpower/2))) {//Only do stuff if they didn't pass out from sleep deprivation before they could finish.  Nothing happens if they did.
                //TODO Add a toast here to alert the user that they fell asleep.
                //Add net successes
                NetHits -= SpriteRoll;
                if (NetHits <= 0) {
                    NetHits = 0;
                    if (data.getCurrentSprite().getServicesOwed() > 0) {
                        NetHits--;
                    } //Failed attempts to Register still cost services.
                } else {//Positive number of net hits, so the sprite is registered
                    if (data.getCurrentSprite().getServicesOwed() > 0) {//Already had hits, now has more, so it's registered.
                        data.getCurrentSprite().setRegistered(1);
                        data.getCurrentSprite().setOverwatchScore(0);
                        NetHits--; //Pay for the attempt to register
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
                SpriteRoll = 2 * SpriteRoll - Dice.rollDice(data.pvResonance + data.pvWillpower, checkDrainKarma.isChecked());
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
                        // UpdateSpriteList(container); Handled in UpdateServices

            }
                if(checkDrainKarma.isChecked()){
                    data.pvKarmaUsed++;
                    checkDrainKarma.setChecked(false);
                }
                if(checkSkillKarma.isChecked()){
                    data.pvKarmaUsed++;
                    checkSkillKarma.setChecked(false);
                }

                UpdateDisplay(container);

            }});
    UpdateDisplay(container);
    }

    public void PrepareSpriteFragment(final ViewGroup vg){
        UpdateSpriteList(vg);
        UpdateDisplay(vg);

        Spinner spinnerSprites = (Spinner) vg.findViewById(R.id.spinnerSprites);
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(vg.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSprites.setAdapter(adp1);

        final Spinner spinnerSpriteType = (Spinner) vg.findViewById(R.id.spinnerSpriteType);
        ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(vg.getContext(), R.array.SpriteTypes, android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSpriteType.setAdapter(adp2);
        spinnerSpriteType.setSelection(adp2.getPosition(data.getCurrentSprite().getType()));



        final EditText editForce = (EditText) vg.findViewById(R.id.editForce);
        final EditText editServices = (EditText) vg.findViewById(R.id.editDamage);
        final EditText editGOD = (EditText) vg.findViewById(R.id.editGod);
        final EditText editDamage = (EditText) vg.findViewById(R.id.editForce);
        final CheckBox checkRegistered = (CheckBox) vg.findViewById(R.id.checkBoxRegistered);

        checkRegistered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    data.getCurrentSprite().setRegistered(1);
                }else{
                    data.getCurrentSprite().setRegistered(0);
                }
                UpdateSpriteList(vg);
            }
        });

        spinnerSprites.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if(position!=data.pvActiveSpriteId) {
                            data.pvActiveSpriteId = position;
                            editForce.setText(String.valueOf(data.getCurrentSprite().getRating()));
                            editServices.setText(String.valueOf(data.getCurrentSprite().getServicesOwed()));
                            //TODO editGOD.setText(String.valueOf(data.getCurrentSprite().getGODscore()));
                            editDamage.setText(String.valueOf(data.getCurrentSprite().getCondition()));
                            spinnerSpriteType.setSelection(data.getCurrentSprite().getSpriteType() - 1);
                            checkRegistered.setChecked(data.getCurrentSprite().getRegistered() == 1);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

//Sprite Type Picker
        spinnerSpriteType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if((position+1)!=data.getCurrentSprite().getSpriteType()) {
                            data.getCurrentSprite().setSpriteType(position + 1);
                            UpdateSpriteList(vg);
                        }
                        //UpdateSpriteList(); Causing infinite loop
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        editDamage.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    Log.i("Content ", "after  Force: " + s.toString());
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());


                        data.getCurrentSprite().setCondition(newValue);
                        data.UpdateSprite(data.getCurrentSprite());
                        UpdateSpriteList(vg);

                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editForce.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s){
                if(!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    Log.i("Content ", "after  Force: " + s.toString());
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());

                        data.getCurrentSprite().setRating(newValue);
                        data.UpdateSprite(data.getCurrentSprite()); //Save the sprite to the DB
                        UpdateSpriteList(vg);

                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.i("Content ", "before Force: " + s.toString() +"/" +String.valueOf(start)+"/" +String.valueOf(after));
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //   Log.i("Content ", "on     Force: " + s.toString() +"/" +String.valueOf(start)+"/" +String.valueOf(before));
            }
        });

        editGOD.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                /*
                if(!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    Log.i("Content ", "after  Force: " + s.toString());
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());

                        data.getCurrentSprite().setGOD(newValue);
                        datasource.updateSprite(data.getCurrentSprite());  //Save the sprite to the DB
                        UpdateSpriteList();

                    }
                }*/
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editServices.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    Log.i("Content ", "after  Force: " + s.toString());
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());

                        data.getCurrentSprite().setServicesOwed(newValue);
                        data.UpdateSprite(data.getCurrentSprite()); //Save the sprite to the DB
                        UpdateSpriteList(vg);

                    }
                }}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }
    public void PrepareStatsFragment(final ViewGroup vg){
        //Toast.makeText(vg.getContext(), "Stats", Toast.LENGTH_SHORT).show();

        data.RestoreFromDB(vg.getContext());


                    CreateListener(R.id.editBody, data.pvBody, vg);
                    CreateListener(R.id.editWillpower, data.pvWillpower, vg);
                    CreateListener(R.id.editIntuition, data.pvIntuition, vg);
                    CreateListener(R.id.editLogic, data.pvLogic, vg);
                    CreateListener(R.id.editCharisma, data.pvCharisma, vg);
                    CreateListener(R.id.editCompiling, data.pvCompiling, vg);
                    CreateListener(R.id.editRegistering, data.pvRegistering, vg);
                    CreateListener(R.id.editResonance, data.pvResonance, vg);
                    CreateListener(R.id.editStun, data.pvStun, vg);
                    CreateListener(R.id.editPhysical, data.pvPhysical, vg);
                    CreateListener(R.id.editKarma, data.pvKarma, vg);



    }




   /* private void CreateListener(Integer etId, Integer value, ViewGroup vg) {
        final EditText et = (EditText) vg.findViewById(etId);
        //et.setText(String.valueOf(value));
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!et.getText().toString().isEmpty()) {
                    Integer value = Integer.valueOf(et.getText().toString());
                    switch (et.getId()) {
                        case R.id.editBody:
                            data.pvBody=value;
                            break;
                        case R.id.editWillpower:
                            data.pvWillpower= value;
                            break;
                        case R.id.editIntuition:
                            data.pvIntuition=value;
                            break;
                        case R.id.editLogic:
                            data.pvLogic=value;
                            break;
                        case R.id.editCharisma:
                            data.pvCharisma= value;
                            break;
                        case R.id.editCompiling:
                            data.pvCompiling= value;
                            break;
                        case R.id.editRegistering:
                            data.pvRegistering= value;
                            break;
                        case R.id.editResonance:
                            data.pvResonance= value;
                            break;
                        case R.id.editStun:
                            data.pvStun= value;
                            break;
                        case R.id.editPhysical:
                            data.pvPhysical=value;
                            break;
                        case R.id.editKarma:
                            data.pvKarma=value;
                            break;
                    }
                    data.SaveAllToDB();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
*/
   private void CreateListener(Integer etId, Integer value,final ViewGroup vg) {
       final EditText et = (EditText) vg.findViewById(etId);
       et.setText(String.valueOf(value));
       et.addTextChangedListener(new TextWatcher() {
           public void afterTextChanged(Editable s) {
               if(!s.toString().equals(et.getText())) {
                   UpdateStats(et, vg);
                   UpdateDisplay(vg);
               }
           }

           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           public void onTextChanged(CharSequence s, int start, int before, int count) {
           }
       });
   }
       private void UpdateStats(View view, ViewGroup vg){
           EditText et = (EditText) vg.findViewById(view.getId());
           if (!et.getText().toString().isEmpty()) {
               Integer value = Integer.valueOf(et.getText().toString());

               switch (view.getId()) {
                   case R.id.editBody:
                        data.pvBody=value;
                       data.SaveStatToDB("Body", value);
                       break;
                   case R.id.editWillpower:
                       data.pvWillpower=value;
                       data.SaveStatToDB("Willpower", value);
                       break;
                   case R.id.editIntuition:
                       data.pvIntuition=value;
                       data.SaveStatToDB("Intuition", value);
                       break;
                   case R.id.editLogic:
                       data.pvLogic=value;
                       data.SaveStatToDB("Logic", value);
                       break;
                   case R.id.editCharisma:
                       data.pvCharisma=value;
                       data.SaveStatToDB("Charisma", value);
                       break;
                   case R.id.editCompiling:
                       data.pvCompiling=value;
                       data.SaveStatToDB("Compiling", value);
                       break;
                   case R.id.editRegistering:
                       data.pvRegistering=value;
                       data.SaveStatToDB("Registering", value);
                       break;
                   case R.id.editResonance:
                       data.pvResonance=value;
                       data.SaveStatToDB("Resonance", value);
                       break;
                   case R.id.editStun:
                       data.pvStun=value;
                       data.SaveStatToDB("Stun", value);
                       break;
                   case R.id.editPhysical:
                       data.pvPhysical=value;

                       data.SaveStatToDB("Physical", value);
                       break;
                   case R.id.editKarma:
                       data.pvKarma=value;
                       data.SaveStatToDB("Karma", value);
                       break;
               }
           }


    }


    // Will be called via the onClick attribute
    // of the buttons in main.xml

    private void UpdateSprite(ViewGroup vg) {

        final EditText editForce = (EditText) vg.findViewById(R.id.editForce);
        final EditText editServices = (EditText) vg.findViewById(R.id.editDamage);
        //final EditText editGOD = (EditText) vg.findViewById(R.id.editGOD);
        final EditText editDamage = (EditText) vg.findViewById(R.id.editForce);
        final CheckBox checkRegistered = (CheckBox) vg.findViewById(R.id.checkBoxRegistered);
        final Spinner spinnerSpriteType = (Spinner) vg.findViewById(R.id.spinnerSpriteType);
        editForce.setText(String.valueOf(data.getCurrentSprite().getRating()));
        editServices.setText(String.valueOf(data.getCurrentSprite().getServicesOwed()));
        //TODO editGOD.setText(String.valueOf(data.getCurrentSprite().getGODscore()));
        editDamage.setText(String.valueOf(data.getCurrentSprite().getCondition()));
        checkRegistered.setChecked(data.getCurrentSprite().getRegistered() == 1);
        spinnerSpriteType.setSelection(data.getCurrentSprite().getSpriteType() - 1);

    }




}