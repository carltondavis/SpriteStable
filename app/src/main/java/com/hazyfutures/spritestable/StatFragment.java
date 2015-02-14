package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StatFragment extends Fragment  {
    
    MainActivity Main = (MainActivity)getActivity();

    MultiSelectionSpinner spinnerCompiling;
    MultiSelectionSpinner spinnerRegistering;


//Todo add multiple character option
//Todo add a Spirit/Mage handler option
//Display display;// = new Display(getActivity());

    @Override
    public void onResume() {
        super.onResume();

        Main = (MainActivity)getActivity();
        //Toast.makeText(getActivity(), "Stat.onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stat, container, false);
        Main = (MainActivity)getActivity();
        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        List<String> specs = new ArrayList<String>();
        List<String> specsExists = new ArrayList<String>();
        specs=Main.data.getSpecializationList("Compiling");
        specsExists=Main.data.getSpecializationListExists("Compiling");
        String[] arrayCompiling = new String[specs.size()];
        String[] arrayCompilingExists = new String[specsExists.size()];
        arrayCompiling=specs.toArray(arrayCompiling);
        arrayCompilingExists=specsExists.toArray(arrayCompilingExists);
        spinnerCompiling = (MultiSelectionSpinner) v.findViewById(R.id.spinnerCompiling);
        spinnerCompiling.setItems(arrayCompiling);
        spinnerCompiling.setSelection(arrayCompilingExists);


        specs=Main.data.getSpecializationList("Registering");
        specsExists=Main.data.getSpecializationListExists("Registering");
        String[] arrayRegistering = new String[specs.size()];
        String[] arrayRegisteringExists = new String[specsExists.size()];
        arrayRegistering=specs.toArray(arrayRegistering);
        arrayRegisteringExists=specsExists.toArray(arrayRegisteringExists);
        spinnerRegistering = (MultiSelectionSpinner) v.findViewById(R.id.spinnerRegistering);
        spinnerRegistering.setItems(arrayRegistering);
        spinnerRegistering.setSelection(arrayRegisteringExists);

        //Main.data.setSpecialization("Registering", "Courier", true);

        spinnerCompiling.setOnMultiSpinnerListener(
                new MultiSelectionSpinner.MultiSpinnerListener() {
                    public void onItemsSelected(List<String> selected) {
                        //Toast.makeText(getActivity(), selected.toString(), Toast.LENGTH_SHORT).show();
                        //List<String> selected = spinnerCompiling.getSelectedStrings();

                        List<String> notselected = Main.data.getSpecializationList("Compiling");
                        notselected.removeAll(selected);
                        for (String notsel : notselected) {
                            Main.data.setSpecialization("Compiling", notsel, false);
                        }
                        for (String sel : selected) {
                            Main.data.setSpecialization("Compiling", sel, true);
                        }
                    }

                });
        spinnerRegistering.setOnMultiSpinnerListener(
                new MultiSelectionSpinner.MultiSpinnerListener() {
                    public void onItemsSelected(List<String> selected) {
                        List<String> notselected = Main.data.getSpecializationList("Registering");
                        notselected.removeAll(selected);
                        for (String notsel : notselected) {
                            Main.data.setSpecialization("Registering", notsel, false);
                        }
                        for (String sel : selected) {
                            Main.data.setSpecialization("Registering", sel, true);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        CreateListener(R.id.editBody, Main.data.pvBody, v);
        CreateListener(R.id.editWillpower, Main.data.pvWillpower , v);
        CreateListener(R.id.editIntuition, Main.data.pvIntuition , v);
        CreateListener(R.id.editLogic, Main.data.pvLogic, v);
        CreateListener(R.id.editCompiling, Main.data.getSkillValue("Compiling") , v);
        CreateListener(R.id.editRegistering, Main.data.getSkillValue("Registering"), v);
        CreateListener(R.id.editResonance, Main.data.pvResonance, v);
        CreateListener(R.id.editStun, Main.data.pvStun, v);
        CreateListener(R.id.editPhysical, Main.data.pvPhysical, v);
        CreateListener(R.id.editKarma, Main.data.pvKarma, v);
        CreateListener(R.id.editKarmaUsed, Main.data.pvKarmaUsed, v);
        CreateListener(R.id.editConsecutiveHoursRested, Main.data.pvConsecutiveRest, v);
        CreateListener(R.id.editHoursWithoutSleep, Main.data.pvSleeplessHours, v);
        CreateListener(R.id.editHoursThisSession, Main.data.pvHoursThisSession, v);
        CreateListener(R.id.editHoursSinceKarmaRefresh, Main.data.pvHoursSinceKarmaRefresh, v);
//        Toast.makeText(v.getContext(), "OnCreateView Stats",Toast.LENGTH_SHORT).show();


        return v;
    }
        public static StatFragment newInstance() {

        StatFragment f = new StatFragment();
     /*   Bundle b = new Bundle();
        //b.putString("msg", text);
        f.setArguments(b);
*/
        return f;
    }
    private void CreateListener(Integer etId, Integer value, View view) {



        final EditText et = (EditText) view.findViewById(etId);
        et.setText(String.valueOf(value));
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(et.getText())) {
                    UpdateStatsStats(et);


                    //UpdateCompileDisplay(vg);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }
    private void UpdateDamage() {
        RatingBar stunDamage = (RatingBar) getActivity().findViewById(R.id.stunTrack);
        if(stunDamage!=null) {
            int MaxStun = (int) Math.floor(Main.data.pvWillpower / 2) + 9 + Main.data.getToughAsNailsStun();
            int MaxPhysical = (int) Math.floor(Main.data.pvBody / 2) + 9 + Main.data.getToughAsNailsPhysical();

            int _overflow = 0;

            stunDamage.setClickable(false);
            stunDamage.setEnabled(false);
            if (stunDamage.getRating() != Main.data.pvStun || MaxStun != stunDamage.getMax()) {
                stunDamage.setNumStars(MaxStun);
                stunDamage.setMax(MaxStun);
                if (Main.data.pvStun > MaxStun) {//Did we exceed the stun condition monitor?
                    Main.data.pvPhysical += (int) Math.floor((Main.data.pvStun - MaxStun) / 2);  //(TotalStun - StunMax)/2 rounded down is overflow
                    Main.data.pvStun = MaxStun;
                }
                stunDamage.setRating(Main.data.pvStun);
            }
            RatingBar physicalDamage = (RatingBar) getActivity().findViewById(R.id.physicalTrack);
            physicalDamage.setClickable(false);
            physicalDamage.setEnabled(false);
            if (physicalDamage.getRating() != Main.data.pvPhysical || MaxPhysical != physicalDamage.getMax()) {
                physicalDamage.setNumStars(MaxPhysical);
                physicalDamage.setMax(MaxPhysical);

                RatingBar overflowDamage = (RatingBar) getActivity().findViewById(R.id.overflowTrack);
                overflowDamage.setClickable(false);
                overflowDamage.setEnabled(false);
                overflowDamage.setNumStars(Main.data.pvBody+Main.data.getToughAsNailsPhysical());
                overflowDamage.setMax(Main.data.pvBody);

                if (Main.data.pvPhysical > MaxPhysical) {
                    _overflow = Main.data.pvPhysical - MaxPhysical;
                }
                physicalDamage.setRating(Main.data.pvPhysical - _overflow);//Don't count overflow when drawing boxes of damage
                overflowDamage.setRating(_overflow);
            }
            UpdateCompile();
            UpdateUseService();
            UpdateRest();
            UpdateHeal();
        }
    }

    private void UpdateForcePicker() {
        UpdateForcePicker(Main.data.getCurrentSprite().getServicesOwed() == 0);  //Looking at an unCompiled Sprite
    }

    private void UpdateForcePicker(boolean enabled) {
        NumberPicker np = (NumberPicker) getActivity().findViewById(R.id.npSpriteRating);
        if(np!=null) {
            np.setMaxValue(Main.data.pvResonance * 2);
        }
    }

    private void UpdateCheckBoxes() {
        CheckBox checkDrain = (CheckBox) getActivity().findViewById(R.id.DrainKarma);
        if(checkDrain!=null) {
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
                if (Main.data.pvKarmaUsed < (Main.data.pvKarma)) {
                    checkDrain.setEnabled(true);
                } else {
                    checkDrain.setEnabled(false);
                }
            }
        }
    }

    private void UpdateStatsStats(View view) {
        EditText et = (EditText) getActivity().findViewById(view.getId());
        if (!et.getText().toString().isEmpty()) {
            Integer value = Integer.valueOf(et.getText().toString());

            switch (view.getId()) {
                case R.id.editBody:
                    if(Main.data.pvBody != value){
                    Main.data.pvBody = value;
                    Main.data.SaveStatToDB("Body", value);
                    //Change Physical boxes
                    //Change IsDead stuff
                    UpdateDamage();
                    }
                    break;
                case R.id.editWillpower:
                    if(Main.data.pvWillpower != value) {
                        Main.data.pvWillpower = value;
                        Main.data.SaveStatToDB("Willpower", value);
                        //Change stun boxes
                        //Change IsConscious stuff
                        UpdateDamage();
                    }
                    break;
                case R.id.editIntuition:
                    if(Main.data.pvIntuition != value) {
                        Main.data.pvIntuition = value;
                        Main.data.SaveStatToDB("Intuition", value);
                    }
                    break;
                case R.id.editLogic:
                    if(Main.data.pvLogic != value) {
                        Main.data.pvLogic = value;
                        Main.data.SaveStatToDB("Logic", value);
                        //Change Register button availability
                        UpdateCompile();
                    }
                    break;
                case R.id.editCompiling:
                    if(Main.data.getSkillValue("Compiling") != value) {
                        Main.data.SaveSkillToDB("Compiling", value);
                    }
                    break;
                case R.id.editRegistering:
                    if(Main.data.getSkillValue("Registering") != value) {
                        Main.data.SaveSkillToDB("Registering", value);
                    }
                    break;
                case R.id.editResonance:
                    if(Main.data.pvResonance != value) {
                        Main.data.pvResonance = value;
                        Main.data.SaveStatToDB("Resonance", value);
                        //Change max Force spinner
                        UpdateForcePicker();
                    }
                    break;
                case R.id.editStun:
                    if(Main.data.pvStun != value) {
                        Main.data.pvStun = value;
                        Main.data.SaveStatToDB("Stun", value);
                        //Change same as willpower + number of boxes
                        UpdateDamage();
                    }
                    break;
                case R.id.editPhysical:
                    if(Main.data.pvPhysical != value) {
                        Main.data.pvPhysical = value;

                        Main.data.SaveStatToDB("Physical", value);
                        //Change same as body + number of boxes
                        UpdateDamage();
                    }
                    break;
                case R.id.editKarma:
                    if(Main.data.pvKarma != value) {
                        Main.data.pvKarma = value;
                        Main.data.SaveStatToDB("Karma", value);
                        //Change checkboxes
                        UpdateCheckBoxes();
                    }
                    break;
                case R.id.editKarmaUsed:
                    if(Main.data.pvKarmaUsed != value){
                    Main.data.pvKarmaUsed = value;
                    Main.data.SaveStatToDB("KarmaUsed", value);
                    //Change checkboxes
                    UpdateCheckBoxes();
                    }
                    break;
                case R.id.editHoursThisSession:
                    if(Main.data.pvHoursThisSession != value) {
                        Main.data.pvHoursThisSession = value;
                        Main.data.SaveStatToDB("HoursThisSession", value);
                        //Change Time
                        TextView tv = (TextView) getActivity().findViewById(R.id.valuesHours);
                        if (tv != null) {
                            tv.setText(String.valueOf(Main.data.pvHoursThisSession));
                        }
                    }
                    break;
                case R.id.editConsecutiveHoursRested:
                    if(Main.data.pvConsecutiveRest != value) {
                        Main.data.pvConsecutiveRest = value;
                        Main.data.SaveStatToDB("ConsecutiveRest", value);
                    }
                    break;
                case R.id.editHoursWithoutSleep:
                    if(Main.data.pvSleeplessHours != value) {
                        Main.data.pvSleeplessHours = value;
                        Main.data.SaveStatToDB("SleeplessHours", value);
                    }
                    break;
                case R.id.editHoursSinceKarmaRefresh:
                    if(Main.data.pvHoursSinceKarmaRefresh != value) {
                        Main.data.pvHoursSinceKarmaRefresh = value;
                        Main.data.SaveStatToDB("HoursSinceKarmaRefresh", value);
                    }
                    break;
            }
        }


    }
    private void UpdateCompile() {
        UpdateCompile(Main.data.getCurrentSprite().getServicesOwed() == 0, IsConscious());
    }

    private void UpdateCompile(boolean isCompile, boolean enabled) {
        Button Compile = (Button) getActivity().findViewById(R.id.Compile);
        if(Compile!=null) {
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
    }
    //UpdateRest Button        Enabled
    private void UpdateRest() {
        UpdateRest((Main.data.pvStun > 0) && IsAlive());
    }

    private void UpdateRest(boolean enabled) {
        Button Rest = (Button) getActivity().findViewById(R.id.Rest);
        if(Rest!=null) {
            Rest.setClickable(enabled);
            Rest.setEnabled(enabled);
        }
    }

    //disable heal when stun, re-enable when no-stun and damage, disable when no damage
    private void UpdateHeal() {
        UpdateHeal((Main.data.pvStun == 0) && (Main.data.pvPhysical > 0) && IsAlive());
    }

    private void UpdateHeal(boolean enabled) {
        Button Heal = (Button) getActivity().findViewById(R.id.buttonHeal);
        if(Heal!=null) {
            Heal.setClickable(enabled);
            Heal.setEnabled(enabled);
        }
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
        if(UseService!=null) {
            UpdateCompile(Main.data.getCurrentSprite().getServicesOwed() == 0, IsConscious());
            UseService.setClickable(enabled);
            UseService.setEnabled(enabled);
        }
    }
}