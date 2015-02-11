package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QualitiesFragment extends Fragment {

//TODO QUALITIES FRAGMENT:

//Todo: Create database to hold these records
//ID, Name, Rating
//Todo: Code to load and save info
//Todo: Add modifiers for any given die rolls to make these stats generic (MatrixMod, PerceptionMod, HealingMod, etc)
//Todo: Add code for existing die rolls
//Todo list of qualities:
//Codeslinger: +2 dice a specific matrix action
//Focused concentration 1-6: May sustain Rating force of without negative penalties
//High paint tolerance 1-3: +1 rating boxes of stun/physical before penalties are incurred
//Home ground: +2 bonus to matrix tests
//Natural Hardening: +1 biofeedback filtering
//Quick Healer: +2 dice pool on healing tests
//Toughness: +1 die damage resistance tests
//Will to live 1-3: +Rating in overflow boxes
//Bad Luck: d6 when edge used, on a 1 opposite of effect occurs, only happens once per session
//Codeblock -2 dice pool on a specific action
//Loss of Confidence:  -2 dice on a skill
//Low pain tolerance: Modifier every 2 boxes of damage, not 3
//Sensitive system: Willpower(2) test before fading tests, +2 fading if it fails
//Simsense Vertigo: -2 dice all matrix tests
//Slow Healer: -2 dice on healing tests
//Perceptive 1-2: +Rating die matrix perception test
//Spike Resistance 1-3: +rating vs. biofeedback
//Tough as Nails 1-4 Stun/Physical: +1 box of either stun or physical per rating, up to 3 on either.
//Asthma: Fatigue damage twice as often, 2 boxes fatigue damage=-1 all actions, 4 boxes=further fatigue damage only resisted by willpower, 8 boxes=additional -1 penatly all actions
//Bi-polar: Once per day at sleep time roll die, 1-2 Depressed, 3-4 Manic, 5-6Stable, Medication available.  Not taking for 12 hours results in roll
//Results: Manic: -2 logic + intuition rolls, Depressive -2 logic, intuition, Stable no penalties
//Blind: -4 perception tests
//Computer illiterate: -4 all matrix tests
//Deaf: -2 perception tests
//Dimmer bulb: -1 logic + intuition tests
//Illiterate: -2 matrix tests
//Oblivious 1-2: (1) -2 perception tests, (2) +1 threshold perception tests
//Pie Iesu Domine: High pain tolerance 1, Always has one box physical damage
//Noise reduction from equipment
//TODO Add code to Compile to handle qualities
//Todo Add onResume to pull from DB when restoring?
    MainActivity Main = (MainActivity)getActivity();


    @Override
    public void onResume() {
        super.onResume();

        Main = (MainActivity)getActivity();
        //Toast.makeText(getActivity(), "Stat.onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qualities, container, false);

        RadioGroup rgFocusedConcentration = (RadioGroup) v.findViewById(R.id.rgFocusedConcentration);
        rgFocusedConcentration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setFocusedConcentration(getValue(checkedId));

            }
        });

        RadioGroup HighPainTolerance = (RadioGroup) v.findViewById(R.id.rgHighPainTolerance);
        HighPainTolerance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setHighPainTolerance(getValue(checkedId));

            }
        });
        RadioGroup HomeGround = (RadioGroup) v.findViewById(R.id.rgHomeGround);
        HomeGround.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setHomeGround(getValue(checkedId));

            }
        });
        RadioGroup NaturalHardening = (RadioGroup) v.findViewById(R.id.rgNaturalHardening);
        NaturalHardening.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setNaturalHardening(getValue(checkedId));

            }
        });
        RadioGroup QuickHealer = (RadioGroup) v.findViewById(R.id.rgQuickHealer);
        QuickHealer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setQuickHealer(getValue(checkedId));

            }
        });
        RadioGroup Toughness = (RadioGroup) v.findViewById(R.id.rgToughness);
        Toughness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setToughness(getValue(checkedId));

            }
        });
        RadioGroup WillToLive = (RadioGroup) v.findViewById(R.id.rgWillToLive);
        WillToLive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setWillToLive(getValue(checkedId));

            }
        });
        RadioGroup BadLuck = (RadioGroup) v.findViewById(R.id.rgBadLuck);
        BadLuck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setBadLuck(getValue(checkedId));

            }
        });
        RadioGroup LowPainTolerance = (RadioGroup) v.findViewById(R.id.rgLowPainTolerance);
        LowPainTolerance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setLowPainTolerance(getValue(checkedId));

            }
        });
        RadioGroup SensitiveSystem = (RadioGroup) v.findViewById(R.id.rgSensitiveSystem);
        SensitiveSystem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSensitiveSystem(getValue(checkedId));

            }
        });
        RadioGroup SimsenseVertigo = (RadioGroup) v.findViewById(R.id.rgSimsenseVertigo);
        SimsenseVertigo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSimsenseVertigo(getValue(checkedId));

            }
        });
        RadioGroup SlowHealer = (RadioGroup) v.findViewById(R.id.rgSlowHealer);
        SlowHealer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSlowHealer(getValue(checkedId));

            }
        });
        RadioGroup Perceptive = (RadioGroup) v.findViewById(R.id.rgPerceptive);
        Perceptive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setPerceptive(getValue(checkedId));

            }
        });
        RadioGroup SpikeResistance = (RadioGroup) v.findViewById(R.id.rgSpikeResistance);
        SpikeResistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSpikeResistance(getValue(checkedId));

            }
        });
        RadioGroup ToughAsNailsPhysical = (RadioGroup) v.findViewById(R.id.rgToughAsNailsPhysical);
        ToughAsNailsPhysical.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setToughAsNailsPhysical(getValue(checkedId));

            }
        });
        RadioGroup ToughAsNailsStun = (RadioGroup) v.findViewById(R.id.rgToughAsNailsStun);
        ToughAsNailsStun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setToughAsNailsStun(getValue(checkedId));

            }
        });
        RadioGroup Asthma = (RadioGroup) v.findViewById(R.id.rgAsthma);
        Asthma.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setAsthma(getValue(checkedId));

            }
        });
        RadioGroup BiPolar = (RadioGroup) v.findViewById(R.id.rgBiPolar);
        BiPolar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setBiPolar(getValue(checkedId));

            }
        });
        RadioGroup Blind = (RadioGroup) v.findViewById(R.id.rgBlind);
        Blind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setBlind(getValue(checkedId));

            }
        });
        RadioGroup ComputerIlliterate = (RadioGroup) v.findViewById(R.id.rgComputerIlliterate);
        ComputerIlliterate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setComputerIlliterate(getValue(checkedId));

            }
        });
        RadioGroup Deaf = (RadioGroup) v.findViewById(R.id.rgDeaf);
        Deaf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setDeaf(getValue(checkedId));

            }
        });
        RadioGroup DimmerBulb = (RadioGroup) v.findViewById(R.id.rgDimmerBulb);
        DimmerBulb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setDimmerBulb(getValue(checkedId));

            }
        });
        RadioGroup Illiterate = (RadioGroup) v.findViewById(R.id.rgIlliterate);
        Illiterate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setIlliterate(getValue(checkedId));

            }
        });
        //TODO Add Radio Group ID's
        RadioGroup Oblivious = (RadioGroup) v.findViewById(R.id.rgOblivious);
        Oblivious.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setOblivious(getValue(checkedId));

            }
        });
        RadioGroup PieIesuDomine = (RadioGroup) v.findViewById(R.id.rgPieIesuDomine);
        PieIesuDomine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setPieIesuDomine(getValue(checkedId));

            }
        });

        return v;
    }

    public Integer getValue(int checkedId) {
        RadioButton rb=(RadioButton) getActivity().findViewById(checkedId);
        switch (rb.getText().toString()) {
            case "No":
                return 0;
            case "None":
                return 0;
            case "Yes":
                return 1;
            default:
                return Integer.valueOf(rb.getText().toString());
        }
    }





    public static QualitiesFragment newInstance() {

        QualitiesFragment f = new QualitiesFragment();
    /*    Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);
*/
        return f;
    }
}