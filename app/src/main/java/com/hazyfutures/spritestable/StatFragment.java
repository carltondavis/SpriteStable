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

//TODO Add Load Chummer File Button
    //TODO: Pick file
    //TODO: Clear out existing Character PV values
    //TODO: Parse data object into PV object
    //TODO: Convert Quality Build Points into my levels.

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

        CreateListener(R.id.editBody, Main.data.getStatValue("Body"), v);
        CreateListener(R.id.editWillpower, Main.data.getStatValue("Willpower") , v);
        CreateListener(R.id.editIntuition, Main.data.getStatValue("Intuition") , v);
        CreateListener(R.id.editLogic, Main.data.getStatValue("Logic"), v);
        CreateListener(R.id.editCompiling, Main.data.getSkillValue("Compiling") , v);
        CreateListener(R.id.editRegistering, Main.data.getSkillValue("Registering"), v);
        CreateListener(R.id.editResonance, Main.data.getStatValue("Resonance"), v);
        CreateListener(R.id.editStun, Main.data.getStatValue("Stun"), v);
        CreateListener(R.id.editPhysical, Main.data.getStatValue("Physical"), v);
        CreateListener(R.id.editKarma, Main.data.getStatValue("Karma"), v);
        CreateListener(R.id.editKarmaUsed, Main.data.getStatValue("KarmaUsed"), v);
        CreateListener(R.id.editConsecutiveHoursRested, Main.data.getStatValue("ConsecutiveRest"), v);
        CreateListener(R.id.editHoursWithoutSleep, Main.data.getStatValue("SleeplessHours"), v);
        CreateListener(R.id.editHoursThisSession, Main.data.getStatValue("HoursThisSession"), v);
        CreateListener(R.id.editHoursSinceKarmaRefresh, Main.data.getStatValue("HoursSinceKarmaRefresh"), v);
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
            int MaxStun = (int) Math.floor(Main.data.getStatValue("Willpower") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Stun");
            int MaxPhysical = (int) Math.floor(Main.data.getStatValue("Body") / 2) + 9 + Main.data.getQualityValue("Tough as Nails Physical");

            int _overflow = 0;

            stunDamage.setClickable(false);
            stunDamage.setEnabled(false);
            if (stunDamage.getRating() != Main.data.getStatValue("Stun") || MaxStun != stunDamage.getMax()) {
                stunDamage.setNumStars(MaxStun);
                stunDamage.setMax(MaxStun);
                if (Main.data.getStatValue("Stun") > MaxStun) {//Did we exceed the stun condition monitor?
                    Main.data.addStatValue("Physical" ,(int) Math.floor((Main.data.getStatValue("Stun") - MaxStun) / 2));  //(TotalStun - StunMax)/2 rounded down is overflow
                    Main.data.setStatValue("Stun", MaxStun);
                }
                stunDamage.setRating(Main.data.getStatValue("Stun"));
            }
            RatingBar physicalDamage = (RatingBar) getActivity().findViewById(R.id.physicalTrack);
            physicalDamage.setClickable(false);
            physicalDamage.setEnabled(false);
            if (physicalDamage.getRating() != Main.data.getStatValue("Physical") || MaxPhysical != physicalDamage.getMax()) {
                physicalDamage.setNumStars(MaxPhysical);
                physicalDamage.setMax(MaxPhysical);

                RatingBar overflowDamage = (RatingBar) getActivity().findViewById(R.id.overflowTrack);
                overflowDamage.setClickable(false);
                overflowDamage.setEnabled(false);
                overflowDamage.setNumStars(Main.data.getStatValue("Body")+Main.data.getQualityValue("Tough as Nails Physical"));
                overflowDamage.setMax(Main.data.getStatValue("Body"));

                if (Main.data.getStatValue("Physical") > MaxPhysical) {
                    _overflow = Main.data.getStatValue("Physical") - MaxPhysical;
                }
                physicalDamage.setRating(Main.data.getStatValue("Physical") - _overflow);//Don't count overflow when drawing boxes of damage
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
            np.setMaxValue(Main.data.getStatValue("Resonance") * 2);
        }
    }

    private void UpdateCheckBoxes() {
        CheckBox checkDrain = (CheckBox) getActivity().findViewById(R.id.DrainKarma);
        if(checkDrain!=null) {
            CheckBox checkSkill = (CheckBox) getActivity().findViewById(R.id.SkillKarma);
            if (checkDrain.isChecked()) {
                if (Main.data.getStatValue("KarmaUsed") < (Main.data.getStatValue("Karma") - 1)) {
                    checkSkill.setEnabled(true);
                } else {
                    checkSkill.setEnabled(false);
                }
            } else {
                if (Main.data.getStatValue("KarmaUsed") < Main.data.getStatValue("Karma")) {
                    checkSkill.setEnabled(true);
                } else {
                    checkSkill.setEnabled(false);
                }
            }
            if (checkSkill.isChecked()) {
                if (Main.data.getStatValue("KarmaUsed") < (Main.data.getStatValue("Karma") - 1)) {
                    checkDrain.setEnabled(true);
                } else {
                    checkDrain.setEnabled(false);
                }
            } else {
                if (Main.data.getStatValue("KarmaUsed") < (Main.data.getStatValue("Karma"))) {
                    checkDrain.setEnabled(true);
                } else {
                    checkDrain.setEnabled(false);
                }
            }
        }
    }
    private void UpdateStat(String name, Integer value) {
        if (Main.data.getStatValue(name) != value) {
            Main.data.setStatValue(name, value);
            Main.data.SaveStatToDB(name, value);
        }
    }
    private void UpdateStatsStats(View view) {
        EditText et = (EditText) getActivity().findViewById(view.getId());
        if (!et.getText().toString().isEmpty()) {
            Integer value = Integer.valueOf(et.getText().toString());

            switch (view.getId()) {
                case R.id.editBody:
                    UpdateStat("Body", value);
                    //Change Physical boxes
                    //Change IsDead stuff
                    UpdateDamage();

                    break;
                case R.id.editWillpower:
                    UpdateStat("Willpower", value);
                        //Change stun boxes
                        //Change IsConscious stuff
                        UpdateDamage();

                    break;
                case R.id.editIntuition:
        UpdateStat("Intuition", value);
                    break;
                case R.id.editLogic:
        UpdateStat("Logic", value);
        UpdateCompile();
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
        UpdateStat("Resonance", value);
                        //Change max Force spinner
                        UpdateForcePicker();

                    break;
                case R.id.editStun:
        UpdateStat("Stun", value);
                        //Change same as willpower + number of boxes
                        UpdateDamage();

                    break;
                case R.id.editPhysical:
        UpdateStat("Physical", value);
                        //Change same as body + number of boxes
                        UpdateDamage();

                    break;
                case R.id.editKarma:
        UpdateStat("Karma", value);
                        //Change checkboxes
                        UpdateCheckBoxes();

                    break;
                case R.id.editKarmaUsed:
        UpdateStat("KarmaUsed", value);
                    //Change checkboxes
                    UpdateCheckBoxes();

                    break;
                case R.id.editHoursThisSession:
        UpdateStat("HoursThisSession", value);
                        //Change Time
                        TextView tv = (TextView) getActivity().findViewById(R.id.valuesHours);
                        if (tv != null) {
                            tv.setText(String.valueOf(Main.data.getStatValue("HoursThisSession")));
                        }

                    break;
                case R.id.editConsecutiveHoursRested:
        UpdateStat("ConsecutiveHoursRested", value);
                    break;
                case R.id.editHoursWithoutSleep:
        UpdateStat("HoursWithoutSleep", value);
                    break;
                case R.id.editHoursSinceKarmaRefresh:
        UpdateStat("HoursSinceKarmaRefresh", value);
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
                if (((Main.data.pvSprites.size()) > Main.data.getStatValue("Logic")) && Main.data.getCurrentSprite().getRegistered() == 0) { //Also only disable if we're looking at an unregistered sprite.  We can always get more services.
                    enabled = false;
                }
            }
            Compile.setClickable(enabled);
            Compile.setEnabled(enabled);
        }
    }
    //UpdateRest Button        Enabled
    private void UpdateRest() {
        UpdateRest((Main.data.getStatValue("Stun") > 0) && IsAlive());
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
        UpdateHeal((Main.data.getStatValue("Stun") == 0) && (Main.data.getStatValue("Physical") > 0) && IsAlive());
    }

    private void UpdateHeal(boolean enabled) {
        Button Heal = (Button) getActivity().findViewById(R.id.buttonHeal);
        if(Heal!=null) {
            Heal.setClickable(enabled);
            Heal.setEnabled(enabled);
        }
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
        if(UseService!=null) {
            UpdateCompile(Main.data.getCurrentSprite().getServicesOwed() == 0, IsConscious());
            UseService.setClickable(enabled);
            UseService.setEnabled(enabled);
        }
    }
}