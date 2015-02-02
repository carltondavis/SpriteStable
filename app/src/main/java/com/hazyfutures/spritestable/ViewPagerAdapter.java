package com.hazyfutures.spritestable;
//TODO: Make SpriteList update happen when compile/rest/use service, etc.
//Todo: Make sure compile button is pointed at selected sprite
//TODO CHECK FOR BUGS EVERYWHERE
//TODO: Add Sprite editor page
//TODO: Add Qualities Page
//TODO: Add Matrix page

//ToDo Add Karma Usage Used Karma added to Stats
//ToDo Add Post-edge buttons for skill and drain. Set minimum number of hits desired for roll, re-roll failures and subtract edge if that number not met. Use Toast if edge used this way
//ToDo Add Karma regenerating, Track resting, 8 hours in a row regens karma
//Todo: Add skill specializations  List of Checkboxes under the skill, check for them when compiling/registering
//Todo add Insomnia A/B Checkbox to turn it on, radiobutton to pick which type, add code to check when regen karma, adds counter for failed rolls to keep karma refresh button disabled, add code to muck with healing
//ToDo Add Sleep button  Sleep button under Rest, adds 8 hours, resets fatigue, regen karma
//Todo Add Healing day available if no stun damage, makes physical damage healing test, adds 24 hours
//ToDo Add Fatigue Rules after 24 hours take 1S, then again every 3 hours, +1 each time resist with Body+Willpower
//ToDo Add warnings about fatigue/damage  Change Hours to Total Hours.  Add Hours without Sleep counter, add toast for taking fatigue damage
//ToDo Add penalty popup warnings  Add Total Penalty display, tap it to have a toast pop up listing sources of penalties
//ToDo Add possible Overflow/Death warnings If compiling/registering something, calculate if worst case stun/physical could max out stun or physical chart.  Warn of possibility.
//Todo Someday Add statistics for rolls to include actual percentage chance of something happening to warnings
//Todo add Toast for disabled buttons explaining why they're disabled
//TODO settings page: dice rolls display, Fatigue rules, fatigue warnings, Penalty popup warning, possible death warning
//TODO TabHost for system?
//Todo: Add access to persisted data on Config page side
//ToDo: Only load from DB when persisted data doesn't exist.
//Todo: Change DB writing to happen on pause/close.  Load from DB on resume
//ToDo Add automated registering system: Set the amount of time to use, and it runs
//Todo: Add page for Playing
//Todo: Add Playing hacking buttons
//Todo: Add Playing List of sprites and Sprite assistance/assist sprite radio buttons
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

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
//TODO Handle Compile, Stat, Sprite, Qualities, Matrix pages
// Depending on Position, pick a layout file, create variables, inflate the layout, set the links to objects within the layout, set the initial values of items
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
                itemView = inflater.inflate(R.layout.activity_main, container,
                        false);

                ((ViewPager) container).addView(itemView);
                break;

            case 3: //Sprite
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.activity_main, container,
                        false);

                ((ViewPager) container).addView(itemView);
                break;

            case 4: //Matrix
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.activity_main, container,
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
        data.getCurrentSprite().setServicesOwed(services);
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
        UpdateHours(data.pvHours, vg);
    }

    private void UpdateHours(int hours, ViewGroup vg){
        final TextView ValueHours = (TextView) vg.findViewById(R.id.valuesHours);
        ValueHours.setText(String.valueOf(hours));
        data.pvHours=hours;
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
    private boolean IsConscious(){
        return (data.pvStun<(8+Math.floor(data.pvWillpower/2)))&&(data.pvPhysical<(8+Math.floor(data.pvBody/2)));
    }
    private boolean IsAlive(){
        return (data.pvPhysical<(8+Math.floor(data.pvBody/2)));
    }
    //UpdateUseService Button  Enabled
    private void UpdateUseService(ViewGroup vg){
        UpdateUseService(data.getCurrentSprite().getServicesOwed()>0&&IsConscious(), vg);// Has Services, isn't unconscious, isn't dying.
    }
    private void UpdateUseService(boolean enabled, ViewGroup vg){
        Button UseService = (Button) vg.findViewById(R.id.buttonUseService);
        UpdateForcePicker(data.getCurrentSprite().getServicesOwed()==0, vg);
        UpdateTypePicker(data.getCurrentSprite().getServicesOwed()==0, vg);
        UseService.setClickable(enabled);
        UseService.setEnabled(enabled);
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
        np.setValue(data.getCurrentSprite().getRating());
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
        boolean unregisteredExists=false;
        data.pvSpriteList.clear();
        List<Sprite> Unnecessary = new ArrayList<>();
        for(Sprite sprite : data.pvSprites){

            //Keep the first unregistered sprite
            //Delete any other unregistered sprites
            //If no unregistered sprites found, create a NEW SPRITE
            //If sprite is Registered and Services==0 delete it
            //Add it to the list if it's registered and services>0 or if it's the first unregistered services>0

            if((sprite.getRegistered()==0&&unregisteredExists)||(sprite.getRegistered()==1&&sprite.getServicesOwed()==0)){ //Second or later unregistered item, or all services used
                Unnecessary.add(sprite);        //Set up to remove it from the list
                if(data.pvSprites.get(data.pvActiveSpriteId)==sprite){      //If we're deleting the active sprite then aim the sprite pointer at an existing sprite.
                    if(data.pvActiveSpriteId>0){data.pvActiveSpriteId--;}
                }
            }else{
                String title = String.valueOf("Force " + sprite.getRating() + " " + sprite.getType()) + " with " + sprite.getServicesOwed() + " services";

                if(sprite.getRegistered()==1){
                    title=title + " Registered";
                }else{
                    unregisteredExists=true;
                }
                data.pvSpriteList.add(title);
            }
        }
        for(Sprite deleteSprite : Unnecessary){
            data.DeleteSprite(deleteSprite);
        }
        data.pvSprites.removeAll(Unnecessary);

        if(!unregisteredExists){//Creating a new Sprite is an option
            Sprite newSprite= new Sprite();
            newSprite.setRating(data.getCurrentSprite().getRating());
            newSprite.setSpriteType(data.getCurrentSprite().getType());
            data.pvSprites.add(newSprite);
            //Populate Sprite List with NEW option
            String title = "NEW SPRITE";
            data.pvSpriteList.add(title);

            data.UpdateSprite(data.pvSprites.get(data.pvSpriteList.size()-1));  //Save the new sprite to the DB
        }
        Spinner spriteSpinner = (Spinner) vg.findViewById(R.id.spinnerSprite);                                             //Update the dropdown
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(vg.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);
        spriteSpinner.setSelection(data.pvActiveSpriteId);
        data.SaveAllToDB();

    }

    private void UpdateDisplay(ViewGroup vg){
        UpdateDamage(vg);
        UpdateServices(vg);
        UpdateUseService(vg);
        UpdateRest(vg);
        UpdateRating(vg);
        UpdateCompile(vg);
        UpdateHours(vg);
        UpdateTypePicker(vg);
        UpdateSpriteList(vg);







    }

    private void UpdateDamage(int stun, int physical, ViewGroup vg) {
        int MaxStun=(int) Math.floor(data.pvWillpower / 2) + 8;
        int MaxPhysical=(int) Math.floor(data.pvBody / 2) + 8;
        int _overflow=0;
        RatingBar stunDamage = (RatingBar) vg.findViewById(R.id.stunTrack);
        stunDamage.setNumStars(MaxStun);
        stunDamage.setMax(MaxStun);
        if (stun > MaxStun) {//Did we exceed the stun condition monitor?
            physical += (int) Math.floor((stun - MaxStun) / 2);  //(TotalStun - StunMax)/2 rounded down is overflow
            data.pvStun = MaxStun;
        }
        stunDamage.setRating(data.pvStun);


        RatingBar physicalDamage = (RatingBar) vg.findViewById(R.id.physicalTrack);
        physicalDamage.setNumStars(MaxPhysical);
        physicalDamage.setMax(MaxPhysical);

        RatingBar overflowDamage = (RatingBar) vg.findViewById(R.id.overflowTrack);
        overflowDamage.setNumStars(data.pvBody);
        overflowDamage.setMax(data.pvBody);

        if(physical>MaxPhysical){_overflow=physical-MaxPhysical;}
        physicalDamage.setRating(physical-_overflow);//Don't count overflow when drawing boxes of damage
        overflowDamage.setRating(_overflow);
        data.pvPhysical=physical;
        UpdateCompile(vg);
        UpdateUseService(vg);
        UpdateRest(vg);
    }

   public void PrepareCompileFragment(final ViewGroup container){
        Button restButton = (Button) container.findViewById(R.id.Rest);
        restButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Healing Roll
                data.pvStun -= Dice.rollDice(data.pvBody + data.pvWillpower, false);
                if (data.pvStun < 0) {
                    data.pvStun = 0;
                }
                //UpdateDamage();

                //Add hours
                data.pvHours += 1;
                TextView valueHours = (TextView) container.findViewById(R.id.valuesHours);
                valueHours.setText(String.valueOf(data.pvHours));
            }
        });
        data.RestoreFromDB(container.getContext());
        data.UpdateSpriteList();

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

        //Create Listeners
        npSpriteRating.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                data.UpdateSpriteList();
                Spinner sSpinner = (Spinner) container.findViewById(R.id.spinnerSprite);                                             //Update the dropdown
                ArrayAdapter<String> adp1=new ArrayAdapter<>(container.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
                adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                sSpinner.setAdapter(adp1);
                sSpinner.setSelection(data.pvActiveSpriteId);
                //data.SaveAllToDB();
            }
        });

        compileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int DamagePenalties;
                int NetHits;
                int SpriteRoll;
                //Damage penalties
                DamagePenalties = (int) (Math.floor(data.pvStun / 3) + Math.floor(data.pvPhysical / 3));

                //Make Opposed Dice Roll
                if (data.getCurrentSprite().getServicesOwed() == 0) {//New Sprite, so Compile
                    NetHits = Dice.rollDice((data.pvResonance + data.pvCompiling - DamagePenalties), checkSkillKarma.isChecked());
                    SpriteRoll = Dice.rollDice(data.getCurrentSprite().getRating(), false);
                } else {//Already has services, so Register
                    NetHits = Dice.rollDice((data.pvResonance + data.pvRegistering - DamagePenalties), checkSkillKarma.isChecked());
                    SpriteRoll = Dice.rollDice(data.getCurrentSprite().getRating() * 2, false);
                    data.pvHours += data.getCurrentSprite().getRating();  //Registering takes hours
                }

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
                        /*UpdateDamage();
                        UpdateServices();
                        UpdateUseService();
                        UpdateRest();
                        UpdateRating();
                        UpdateCompile();
                        UpdateHours();
                        UpdateTypePicker();
                        UpdateSpriteList();*/

            }
        });
    }
    public void PrepareStatsFragment(ViewGroup vg){
        data.RestoreFromDB(vg.getContext());
        data.UpdateSpriteList();

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
        final EditText editServices = (EditText) vg.findViewById(R.id.editServices);
        final EditText editGOD = (EditText) vg.findViewById(R.id.editGOD);
        final EditText editDamage = (EditText) vg.findViewById(R.id.editDamage);
        final CheckBox checkRegistered = (CheckBox) vg.findViewById(R.id.checkRegistered);

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

        checkRegistered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    data.getCurrentSprite().setRegistered(1);
                }else{
                    data.getCurrentSprite().setRegistered(0);
                }
                data.UpdateSpriteList();
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
                            data.UpdateSpriteList();
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
                        data.UpdateSpriteList();

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
                        data.UpdateSpriteList();

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
                        data.UpdateSpriteList();

                    }
                }}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }




    private void CreateListener(Integer etId, Integer value, ViewGroup vg) {
        final EditText et = (EditText) vg.findViewById(etId);
        et.setText(String.valueOf(value));
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
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });




    }


    // Will be called via the onClick attribute
    // of the buttons in main.xml

    private void UpdateSprite(ViewGroup vg) {

        final EditText editForce = (EditText) vg.findViewById(R.id.editForce);
        final EditText editServices = (EditText) vg.findViewById(R.id.editServices);
        //final EditText editGOD = (EditText) vg.findViewById(R.id.editGOD);
        final EditText editDamage = (EditText) vg.findViewById(R.id.editDamage);
        final CheckBox checkRegistered = (CheckBox) vg.findViewById(R.id.checkRegistered);
        final Spinner spinnerSpriteType = (Spinner) vg.findViewById(R.id.spinnerSpriteType);
        editForce.setText(String.valueOf(data.getCurrentSprite().getRating()));
        editServices.setText(String.valueOf(data.getCurrentSprite().getServicesOwed()));
        //TODO editGOD.setText(String.valueOf(data.getCurrentSprite().getGODscore()));
        editDamage.setText(String.valueOf(data.getCurrentSprite().getCondition()));
        checkRegistered.setChecked(data.getCurrentSprite().getRegistered() == 1);
        spinnerSpriteType.setSelection(data.getCurrentSprite().getSpriteType() - 1);

    }




}