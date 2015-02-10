//TODO SPRITE FRAGMENT:
//Todo Add display of sprite stats + abilities for specific sprite listed
//ToDo Add list of important qualities
//Todo: Add skill specializations  List of Checkboxes under the skill, check for them when compiling/registering
//Todo Update Database to handle qualities
//Todo Update Compile/Register/Rest to account for qualities

package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SpriteFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();


    @Override
    public void onResume() {
        super.onResume();

        Main = (MainActivity)getActivity();
        UpdateSpritePage();
        //Toast.makeText(getActivity(), "Stat.onResume()", Toast.LENGTH_SHORT).show();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sprite, container, false);
        Main = (MainActivity)getActivity();

        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/




        Spinner spinnerSprites = (Spinner) v.findViewById(R.id.SpriteSpinnerSprites);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, Main.data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSprites.setAdapter(adp1);

        final Spinner spinnerSpriteType = (Spinner) v.findViewById(R.id.SpriteSpinnerType);
        ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(v.getContext(), R.array.SpriteTypes, android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSpriteType.setAdapter(adp2);
        spinnerSpriteType.setSelection(adp2.getPosition(Main.data.getCurrentSprite().getType()));


        final EditText editForce = (EditText) v.findViewById(R.id.editForce);
        editForce.setText(String.valueOf(Main.data.getCurrentSprite().getRating()));
        final EditText editServices = (EditText) v.findViewById(R.id.editServices);
        editServices.setText(String.valueOf(Main.data.getCurrentSprite().getServicesOwed()));
        final EditText editGOD = (EditText) v.findViewById(R.id.editGod);
        editGOD.setText(String.valueOf(Main.data.getCurrentSprite().getGODScore()));
        final EditText editDamage = (EditText) v.findViewById(R.id.editDamage);
        editDamage.setText(String.valueOf(Main.data.getCurrentSprite().getCondition()));
        final CheckBox checkRegistered = (CheckBox) v.findViewById(R.id.checkBoxRegistered);
        checkRegistered.setChecked(Main.data.getCurrentSprite().getRegistered()==1);

        // UpdateSpriteList();


        checkRegistered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Main.data.getCurrentSprite().setRegistered(1);
                } else {
                    Main.data.getCurrentSprite().setRegistered(0);
                }
                UpdateSpriteList();
            }
        });

        spinnerSprites.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (position != Main.data.pvActiveSpriteId) {
                            Main.data.pvActiveSpriteId = position;
                            editForce.setText(String.valueOf(Main.data.getCurrentSprite().getRating()));
                            editServices.setText(String.valueOf(Main.data.getCurrentSprite().getServicesOwed()));
                            editGOD.setText(String.valueOf(Main.data.getCurrentSprite().getGODScore()));
                            editDamage.setText(String.valueOf(Main.data.getCurrentSprite().getCondition()));
                            spinnerSpriteType.setSelection(Main.data.getCurrentSprite().getSpriteType() - 1);
                            checkRegistered.setChecked(Main.data.getCurrentSprite().getRegistered() == 1);
                            // UpdateSpriteList();
                            UpdateSpritePage();

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
                        if ((position + 1) != Main.data.getCurrentSprite().getSpriteType()) {
                            Main.data.getCurrentSprite().setSpriteType(position + 1);
                            UpdateSpriteList();
                        }
                        //UpdateCompileSpriteList(); Causing infinite loop
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        editDamage.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    if (!newValue.equals(Main.data.getCurrentSprite().getCondition())) {
                        Main.data.getCurrentSprite().setCondition(newValue);
                        Main.data.SaveSpriteToDB();
                        UpdateSpriteList();

                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editForce.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    Log.i("Content ", "after  Force: " + s.toString());
                    if (!newValue.equals(Main.data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());

                        Main.data.getCurrentSprite().setRating(newValue);
                        Main.data.SaveSpriteToDB(); //Save the sprite to the DB
                        UpdateSpriteList();
                        UpdateSpritePage();
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

                if (!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    if (!newValue.equals(Main.data.getCurrentSprite().getGODScore())) {
                        Main.data.getCurrentSprite().setGODScore(newValue);
                        Main.data.SaveSpriteToDB();
                        UpdateSpriteList();

                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editServices.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    Integer newValue = Integer.parseInt(s.toString());
                    if (!newValue.equals(Main.data.getCurrentSprite().getServicesOwed())) {
                        Main.data.getCurrentSprite().setServicesOwed(newValue);
                        Main.data.SaveSpriteToDB(); //Save the sprite to the DB
                        UpdateSpriteList();

                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return v;
    }

    public static SpriteFragment newInstance() {

        SpriteFragment f = new SpriteFragment();
    /*    Bundle b = new Bundle();
        //b.putString("msg", text);


        f.setArguments(b);
*/
        return f;
    }
    private void UpdateSpriteList() {
        Main.data.UpdateSpriteList();
        Spinner spriteSpinner = (Spinner) getActivity().findViewById(R.id.SpriteSpinnerSprites);                                             //Update the dropdown
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Main.data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);
        if (spriteSpinner.getSelectedItemPosition() != Main.data.pvActiveSpriteId) {
            spriteSpinner.setSelection(Main.data.pvActiveSpriteId);
        }
        //data.SaveAllToDB();Infinite loop

    }

    private void UpdateSprite(ViewGroup vg) {

        final EditText editForce = (EditText) vg.findViewById(R.id.editForce);
        final EditText editServices = (EditText) vg.findViewById(R.id.editServices);
        final EditText editGOD = (EditText) vg.findViewById(R.id.editGod);
        final EditText editDamage = (EditText) vg.findViewById(R.id.editDamage);
        final CheckBox checkRegistered = (CheckBox) vg.findViewById(R.id.checkBoxRegistered);
        final Spinner spinnerSpriteType = (Spinner) vg.findViewById(R.id.SpriteSpinnerType);
        editForce.setText(String.valueOf(Main.data.getCurrentSprite().getRating()));
        editServices.setText(String.valueOf(Main.data.getCurrentSprite().getServicesOwed()));
        editGOD.setText(String.valueOf(Main.data.getCurrentSprite().getGODScore()));
        editDamage.setText(String.valueOf(Main.data.getCurrentSprite().getCondition()));
        checkRegistered.setChecked(Main.data.getCurrentSprite().getRegistered() == 1);
        spinnerSpriteType.setSelection(Main.data.getCurrentSprite().getSpriteType() - 1);

    }

    public void UpdateSpritePage() {

        final TextView tvAttack = (TextView) getActivity().findViewById(R.id.textAttack);
        final TextView tvSleaze = (TextView) getActivity().findViewById(R.id.textSleaze);
        final TextView tvDataProcessing = (TextView) getActivity().findViewById(R.id.textDataProcessing);
        final TextView tvFirewall = (TextView) getActivity().findViewById(R.id.textFirewall);
        final TextView tvInitiative = (TextView) getActivity().findViewById(R.id.textInitiative);
        final TextView tvInitiativeDice = (TextView) getActivity().findViewById(R.id.textInitiativeDice);
        final TextView tvResonance = (TextView) getActivity().findViewById(R.id.textResonance);
        final TextView tvSkills = (TextView) getActivity().findViewById(R.id.textSkills);
        final TextView tvPowers = (TextView) getActivity().findViewById(R.id.textPowers);
        tvInitiativeDice.setText("4D6");
        switch (Main.data.getCurrentSprite().getSpriteType()) {
            case 1://Courier
                tvAttack.setText("Attack: " + Main.data.getCurrentSprite().getRating());
                tvSleaze.setText("Sleaze: " + (Main.data.getCurrentSprite().getRating() + 3));
                tvDataProcessing.setText("Data Processing: " + (Main.data.getCurrentSprite().getRating() + 1));
                tvFirewall.setText("Firewall: " + (Main.data.getCurrentSprite().getRating() + 2));
                tvInitiative.setText("Initiative: " + (Main.data.getCurrentSprite().getRating() * 2 + 1));
                tvResonance.setText("Resonance: " + Main.data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Hacking");
                tvPowers.setText("Powers: Cookie, Hash");
                break;
            case 2: //Crack
                tvAttack.setText("Attack: " + Main.data.getCurrentSprite().getRating());
                tvSleaze.setText("Sleaze: " + (Main.data.getCurrentSprite().getRating() + 3));
                tvDataProcessing.setText("Data Processing: " + (Main.data.getCurrentSprite().getRating() + 2));
                tvFirewall.setText("Firewall: " + (Main.data.getCurrentSprite().getRating() + 1));
                tvInitiative.setText("Initiative: " + (Main.data.getCurrentSprite().getRating() * 2 + 2));
                tvResonance.setText("Resonance: " + Main.data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Electronic Warfare, Hacking");
                tvPowers.setText("Powers: Suppression");
                break;
            case 3: //Data
                tvAttack.setText("Attack: " + (Main.data.getCurrentSprite().getRating() - 1));
                tvSleaze.setText("Sleaze: " + Main.data.getCurrentSprite().getRating());
                tvDataProcessing.setText("Data Processing: " + (Main.data.getCurrentSprite().getRating() + 4));
                tvFirewall.setText("Firewall: " + (Main.data.getCurrentSprite().getRating() + 1));
                tvInitiative.setText("Initiative: " + (Main.data.getCurrentSprite().getRating() * 2 + 4));
                tvResonance.setText("Resonance: " + Main.data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Electronic Warfare");
                tvPowers.setText("Powers: Camouflage, Watermark");
                break;
            case 4: //Fault
                tvAttack.setText("Attack: " + (Main.data.getCurrentSprite().getRating() + 3));
                tvSleaze.setText("Sleaze: " + Main.data.getCurrentSprite().getRating());
                tvDataProcessing.setText("Data Processing: " + (Main.data.getCurrentSprite().getRating() + 1));
                tvFirewall.setText("Firewall: " + (Main.data.getCurrentSprite().getRating() + 2));
                tvInitiative.setText("Initiative: " + (Main.data.getCurrentSprite().getRating() * 2 + 1));
                tvResonance.setText("Resonance: " + Main.data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Cybercombat, Hacking");
                tvPowers.setText("Powers: Electron Storm");
                break;
            case 5: //Machine
                tvAttack.setText("Attack: " + (Main.data.getCurrentSprite().getRating() + 1));
                tvSleaze.setText("Sleaze: " + Main.data.getCurrentSprite().getRating());
                tvDataProcessing.setText("Data Processing: " + (Main.data.getCurrentSprite().getRating() + 3));
                tvFirewall.setText("Firewall: " + (Main.data.getCurrentSprite().getRating() + 2));
                tvInitiative.setText("Initiative: " + (Main.data.getCurrentSprite().getRating() * 2 + 3));
                tvResonance.setText("Resonance: " + Main.data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Electronic Warfare, Hardware");
                tvPowers.setText("Powers: Diagnostics, Gremlins, Stability");
                break;
        }
    }
}