
package com.dasixes.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SpriteFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();


    @Override
    public void onResume() {
        super.onResume();
//TODO: Whichever sprite is selected gets set to the stats of whatever sprite was previously selected on this page.
        Main = (MainActivity)getActivity();
    UpdateSpritePage();
    }

@Override
public void onPause() {
    super.onPause();
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sprite, container, false);
         Main = (MainActivity)getActivity();

        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        final Spinner spinnerSprites = (Spinner) v.findViewById(R.id.SpriteSpinnerSprites);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, Main.data.pvSpriteList);
        adp1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSprites.setAdapter(adp1);


           spinnerSprites.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (position != Main.data.pvActiveSpriteId) {
                            Main.data.pvActiveSpriteId = position;
                            // UpdateSpriteList();
                            UpdateSpritePage();

                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        UpdateSpritePage();
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


    public void UpdateSpritePage() {
        //Log.i("Sprite", " Update Sprite Page");
   /*     final EditText evForce = (EditText) getActivity().findViewById(R.id.editForce);
        final EditText evGod = (EditText) getActivity().findViewById(R.id.editGod);
        final EditText evService = (EditText) getActivity().findViewById(R.id.editServices);
        final EditText evDamage = (EditText) getActivity().findViewById(R.id.editDamage);
        final CheckBox evRegistered = (CheckBox) getActivity().findViewById(R.id.checkBoxRegistered);
        if(evForce!=null) {
          //  Log.i("Sprite", " Set Values");
            evForce.setText(String.valueOf(Main.data.getCurrentSprite().getRating()));
            evGod.setText(String.valueOf(Main.data.getCurrentSprite().getGODScore()));
            evService.setText(String.valueOf(Main.data.getCurrentSprite().getServicesOwed()));
            evDamage.setText(String.valueOf(Main.data.getCurrentSprite().getCondition()));
            evRegistered.setChecked(Main.data.getCurrentSprite().getRegistered() == 1);
        }
*/
        final TextView tvAttack = (TextView) getActivity().findViewById(R.id.textAttack);
        final TextView tvSleaze = (TextView) getActivity().findViewById(R.id.textSleaze);
        final TextView tvDataProcessing = (TextView) getActivity().findViewById(R.id.textDataProcessing);
        final TextView tvFirewall = (TextView) getActivity().findViewById(R.id.textFirewall);
        final TextView tvInitiative = (TextView) getActivity().findViewById(R.id.textInitiative);
        final TextView tvInitiativeDice = (TextView) getActivity().findViewById(R.id.textInitiativeDice);
        final TextView tvResonance = (TextView) getActivity().findViewById(R.id.textSpriteResonance);
        final TextView tvSkills = (TextView) getActivity().findViewById(R.id.textSkills);
        final TextView tvPowers = (TextView) getActivity().findViewById(R.id.textPowers);
        final TextView tvGOD = (TextView) getActivity().findViewById(R.id.textGOD);
        final TextView tvServices = (TextView) getActivity().findViewById(R.id.textServices);
        final TextView tvDamage = (TextView) getActivity().findViewById(R.id.textDamage);
        if(tvInitiativeDice!=null){
            tvInitiativeDice.setText("+4D6");
            tvGOD.setText("GOD Score: " + Main.data.getCurrentSprite().getGODScore());
            tvServices.setText("Services: " + (Main.data.getCurrentSprite().getServicesOwed()));
            tvDamage.setText("Damage: " + (Main.data.getCurrentSprite().getCondition()));

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
    }}
}