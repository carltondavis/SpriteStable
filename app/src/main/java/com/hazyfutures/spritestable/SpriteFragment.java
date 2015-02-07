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
    PersistentValues data = new PersistentValues();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sprite, container, false);
        data.RestoreFromDB(v.getContext());
        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/




        Spinner spinnerSprites = (Spinner) v.findViewById(R.id.SpriteSpinnerSprites);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSprites.setAdapter(adp1);

        final Spinner spinnerSpriteType = (Spinner) v.findViewById(R.id.SpriteSpinnerType);
        ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(v.getContext(), R.array.SpriteTypes, android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSpriteType.setAdapter(adp2);
        spinnerSpriteType.setSelection(adp2.getPosition(data.getCurrentSprite().getType()));


        final EditText editForce = (EditText) v.findViewById(R.id.editForce);
        final EditText editServices = (EditText) v.findViewById(R.id.editDamage);
        final EditText editGOD = (EditText) v.findViewById(R.id.editGod);
        final EditText editDamage = (EditText) v.findViewById(R.id.editForce);
        final CheckBox checkRegistered = (CheckBox) v.findViewById(R.id.checkBoxRegistered);

        checkRegistered.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    data.getCurrentSprite().setRegistered(1);
                } else {
                    data.getCurrentSprite().setRegistered(0);
                }
                UpdateSpriteList();
            }
        });

        spinnerSprites.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (position != data.pvActiveSpriteId) {
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
                        if ((position + 1) != data.getCurrentSprite().getSpriteType()) {
                            data.getCurrentSprite().setSpriteType(position + 1);
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
                    Log.i("Content ", "after  Force: " + s.toString());
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());


                        data.getCurrentSprite().setCondition(newValue);
                        data.UpdateSprite(data.getCurrentSprite());
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
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());

                        data.getCurrentSprite().setRating(newValue);
                        data.UpdateSprite(data.getCurrentSprite()); //Save the sprite to the DB
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
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        data.getCurrentSprite().setGODScore(newValue);
                        data.UpdateSprite(data.getCurrentSprite());
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
                    Log.i("Content ", "after  Force: " + s.toString());
                    if (!newValue.equals(data.getCurrentSprite().getRating())) {
                        Log.i("Content ", "set    Force: " + s.toString() + "/" + newValue.toString());

                        data.getCurrentSprite().setServicesOwed(newValue);
                        data.UpdateSprite(data.getCurrentSprite()); //Save the sprite to the DB
                        UpdateSpriteList();

                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
       // UpdateSpriteList();
      //  UpdateSpritePage();

        return v;
    }

    public static SpriteFragment newInstance(PersistentValues data) {

        SpriteFragment f = new SpriteFragment();
        Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    private void UpdateSpriteList() {
        data.UpdateSprite(data.getCurrentSprite());
        data.UpdateSpriteList();
        Spinner spriteSpinner = (Spinner) this.getView().findViewById(R.id.SpriteSpinnerSprites);                                             //Update the dropdown
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this.getView().getContext(), android.R.layout.simple_list_item_1, data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spriteSpinner.setAdapter(adp1);
        if (spriteSpinner.getSelectedItemPosition() != data.pvActiveSpriteId) {
            spriteSpinner.setSelection(data.pvActiveSpriteId);
        }
        //data.SaveAllToDB();Infinite loop

    }

    private void UpdateSprite(ViewGroup vg) {

        final EditText editForce = (EditText) vg.findViewById(R.id.editForce);
        final EditText editServices = (EditText) vg.findViewById(R.id.editDamage);
        //final EditText editGOD = (EditText) vg.findViewById(R.id.editGOD);
        final EditText editDamage = (EditText) vg.findViewById(R.id.editForce);
        final CheckBox checkRegistered = (CheckBox) vg.findViewById(R.id.checkBoxRegistered);
        final Spinner spinnerSpriteType = (Spinner) vg.findViewById(R.id.SpriteSpinnerType);
        editForce.setText(String.valueOf(data.getCurrentSprite().getRating()));
        editServices.setText(String.valueOf(data.getCurrentSprite().getServicesOwed()));
        //TODO editGOD.setText(String.valueOf(data.getCurrentSprite().getGODscore()));
        editDamage.setText(String.valueOf(data.getCurrentSprite().getCondition()));
        checkRegistered.setChecked(data.getCurrentSprite().getRegistered() == 1);
        spinnerSpriteType.setSelection(data.getCurrentSprite().getSpriteType() - 1);

    }

    public void UpdateSpritePage() {

        final TextView tvAttack = (TextView) this.getView().findViewById(R.id.textAttack);
        final TextView tvSleaze = (TextView) this.getView().findViewById(R.id.textSleaze);
        final TextView tvDataProcessing = (TextView) this.getView().findViewById(R.id.textDataProcessing);
        final TextView tvFirewall = (TextView) this.getView().findViewById(R.id.textFirewall);
        final TextView tvInitiative = (TextView) this.getView().findViewById(R.id.textInitiative);
        final TextView tvInitiativeDice = (TextView) this.getView().findViewById(R.id.textInitiativeDice);
        final TextView tvResonance = (TextView) this.getView().findViewById(R.id.textResonance);
        final TextView tvSkills = (TextView) this.getView().findViewById(R.id.textSkills);
        final TextView tvPowers = (TextView) this.getView().findViewById(R.id.textPowers);

        switch (data.getCurrentSprite().getSpriteType()) {
            case 1://Courier
                tvAttack.setText("Attack: " + data.getCurrentSprite().getRating());
                tvSleaze.setText("Sleaze: " + (data.getCurrentSprite().getRating() + 3));
                tvDataProcessing.setText("Data Processing: " + (data.getCurrentSprite().getRating() + 1));
                tvFirewall.setText("Firewall: " + (data.getCurrentSprite().getRating() + 2));
                tvInitiative.setText("Initiative: " + (data.getCurrentSprite().getRating() * 2 + 1));
                tvResonance.setText("Resonance: " + data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Hacking");
                tvPowers.setText("Powers: Cookie, Hash");
                break;
            case 2: //Crack
                tvAttack.setText("Attack: " + data.getCurrentSprite().getRating());
                tvSleaze.setText("Sleaze: " + (data.getCurrentSprite().getRating() + 3));
                tvDataProcessing.setText("Data Processing: " + (data.getCurrentSprite().getRating() + 2));
                tvFirewall.setText("Firewall: " + (data.getCurrentSprite().getRating() + 1));
                tvInitiative.setText("Initiative: " + (data.getCurrentSprite().getRating() * 2 + 2));
                tvResonance.setText("Resonance: " + data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Electronic Warfare, Hacking");
                tvPowers.setText("Powers: Suppression");
                break;
            case 3: //Data
                tvAttack.setText("Attack: " + (data.getCurrentSprite().getRating() - 1));
                tvSleaze.setText("Sleaze: " + data.getCurrentSprite().getRating());
                tvDataProcessing.setText("Data Processing: " + (data.getCurrentSprite().getRating() + 4));
                tvFirewall.setText("Firewall: " + (data.getCurrentSprite().getRating() + 1));
                tvInitiative.setText("Initiative: " + (data.getCurrentSprite().getRating() * 2 + 4));
                tvResonance.setText("Resonance: " + data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Electronic Warfare");
                tvPowers.setText("Powers: Camouflage, Watermark");
                break;
            case 4: //Fault
                tvAttack.setText("Attack: " + (data.getCurrentSprite().getRating() + 3));
                tvSleaze.setText("Sleaze: " + data.getCurrentSprite().getRating());
                tvDataProcessing.setText("Data Processing: " + (data.getCurrentSprite().getRating() + 1));
                tvFirewall.setText("Firewall: " + (data.getCurrentSprite().getRating() + 2));
                tvInitiative.setText("Initiative: " + (data.getCurrentSprite().getRating() * 2 + 1));
                tvResonance.setText("Resonance: " + data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Cybercombat, Hacking");
                tvPowers.setText("Powers: Electron Storm");
                break;
            case 5: //Machine
                tvAttack.setText("Attack: " + (data.getCurrentSprite().getRating() + 1));
                tvSleaze.setText("Sleaze: " + data.getCurrentSprite().getRating());
                tvDataProcessing.setText("Data Processing: " + (data.getCurrentSprite().getRating() + 3));
                tvFirewall.setText("Firewall: " + (data.getCurrentSprite().getRating() + 2));
                tvInitiative.setText("Initiative: " + (data.getCurrentSprite().getRating() * 2 + 3));
                tvResonance.setText("Resonance: " + data.getCurrentSprite().getRating());
                tvSkills.setText("Skills: Computer, Electronic Warfare, Hardware");
                tvPowers.setText("Powers: Diagnostics, Gremlins, Stability");
                break;
        }
    }
}