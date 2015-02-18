package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QualitiesFragment extends Fragment {


//ID, Name, Rating
//Todo: code for drop downs

//Codeslinger: +2 dice a specific matrix action
//Codeblock -2 dice pool on a specific action
//Bad Luck: d6 when edge used, on a 1 opposite of effect occurs, only happens once per session
//Asthma: Fatigue damage twice as often, 2 boxes fatigue damage=-1 all actions, 4 boxes=further fatigue damage only resisted by willpower, 8 boxes=additional -1 penatly all actions
//Bi-polar: Once per day at sleep time roll die, 1-2 Depressed, 3-4 Manic, 5-6Stable, Medication available.  Not taking for 12 hours results in roll
//Results: Manic: -2 logic + intuition rolls, Depressive -2 logic, intuition, Stable no penalties

//Focused concentration 1-6: May sustain Rating force of without negative penalties
//High paint tolerance 1-3: +1 rating boxes of stun/physical before penalties are incurred
//Home ground: +2 bonus to matrix tests
//Natural Hardening: +1 biofeedback filtering
//Quick Healer: +2 dice pool on healing tests
//Toughness: +1 die damage resistance tests
//Will to live 1-3: +Rating in overflow boxes
//Loss of Confidence:  -2 dice on a skill
//Low pain tolerance: Modifier every 2 boxes of damage, not 3
//Sensitive system: Willpower(2) test before fading tests, +2 fading if it fails
//Simsense Vertigo: -2 dice all matrix tests
//Slow Healer: -2 dice on healing tests
//Perceptive 1-2: +Rating die matrix perception test
//Spike Resistance 1-3: +rating vs. biofeedback
//Tough as Nails 1-4 Stun/Physical: +1 box of either stun or physical per rating, up to 3 on either.
//Blind: -4 perception tests
//Computer illiterate: -4 all matrix tests
//Deaf: -2 perception tests
//Dimmer bulb: -1 logic + intuition tests
//Illiterate: -2 matrix tests
//Oblivious 1-2: (1) -2 perception tests, (2) +1 threshold perception tests
//Pie Iesu Domine: High pain tolerance 1, Always has one box physical damage
//Noise reduction from equipment
    MainActivity Main = (MainActivity)getActivity();


    @Override
    public void onResume() {
        super.onResume();

        Main = (MainActivity)getActivity();
        //Toast.makeText(getActivity(), "Stat.onResume()", Toast.LENGTH_SHORT).show();
    }

    public void UpdateRadioButtons(RadioGroup rg, final String QualityName){
        rg.check(rg.getChildAt(Main.data.getQualityValue(QualityName)).getId());
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setQualityValue(QualityName, (getValue(checkedId)));
                Main.data.SaveQualityToDB(QualityName);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qualities, container, false);
        Main = (MainActivity)getActivity();

        RadioGroup rgInsomnia = (RadioGroup) v.findViewById(R.id.rgInsomnia);
        UpdateRadioButtons(rgInsomnia, "Insomnia");

        RadioGroup rgFocusedConcentration = (RadioGroup) v.findViewById(R.id.rgFocusedConcentration);
        UpdateRadioButtons(rgFocusedConcentration, "Focused Concentration");

        RadioGroup HighPainTolerance = (RadioGroup) v.findViewById(R.id.rgHighPainTolerance);
        UpdateRadioButtons(HighPainTolerance, "High Pain Tolerance");

        RadioGroup HomeGround = (RadioGroup) v.findViewById(R.id.rgHomeGround);
        UpdateRadioButtons(HomeGround, "Home Ground");

        RadioGroup NaturalHardening = (RadioGroup) v.findViewById(R.id.rgNaturalHardening);
        UpdateRadioButtons(NaturalHardening, "Natural Hardening");

        RadioGroup QuickHealer = (RadioGroup) v.findViewById(R.id.rgQuickHealer);
        UpdateRadioButtons(QuickHealer, "Quick Healer");

        RadioGroup Toughness = (RadioGroup) v.findViewById(R.id.rgToughness);
        UpdateRadioButtons(Toughness, "Toughness");

        RadioGroup WillToLive = (RadioGroup) v.findViewById(R.id.rgWillToLive);
        UpdateRadioButtons(WillToLive, "Will to Live");

        RadioGroup BadLuck = (RadioGroup) v.findViewById(R.id.rgBadLuck);
        UpdateRadioButtons(BadLuck, "Bad Luck");

        RadioGroup LowPainTolerance = (RadioGroup) v.findViewById(R.id.rgLowPainTolerance);
        UpdateRadioButtons(LowPainTolerance, "Low Pain Tolerance");

        RadioGroup SensitiveSystem = (RadioGroup) v.findViewById(R.id.rgSensitiveSystem);
        UpdateRadioButtons(SensitiveSystem, "Sensitive System");

        RadioGroup SimsenseVertigo = (RadioGroup) v.findViewById(R.id.rgSimsenseVertigo);
        UpdateRadioButtons(SimsenseVertigo, "Simsense Vertigo");

        RadioGroup SlowHealer = (RadioGroup) v.findViewById(R.id.rgSlowHealer);
        UpdateRadioButtons(SlowHealer, "Slow Healer");

        RadioGroup Perceptive = (RadioGroup) v.findViewById(R.id.rgPerceptive);
        UpdateRadioButtons(Perceptive, "Perceptive");

        RadioGroup SpikeResistance = (RadioGroup) v.findViewById(R.id.rgSpikeResistance);
        UpdateRadioButtons(SpikeResistance, "Spike Resistance");

        RadioGroup ToughAsNailsPhysical = (RadioGroup) v.findViewById(R.id.rgToughAsNailsPhysical);
        UpdateRadioButtons(ToughAsNailsPhysical, "Tough as Nails Physical");

        RadioGroup ToughAsNailsStun = (RadioGroup) v.findViewById(R.id.rgToughAsNailsStun);
        UpdateRadioButtons(ToughAsNailsStun, "Tough as Nails Stun");

        RadioGroup Asthma = (RadioGroup) v.findViewById(R.id.rgAsthma);
        UpdateRadioButtons(Asthma, "Asthma");

        RadioGroup BiPolar = (RadioGroup) v.findViewById(R.id.rgBiPolar);
        UpdateRadioButtons(BiPolar, "BiPolar");

        RadioGroup Blind = (RadioGroup) v.findViewById(R.id.rgBlind);
        UpdateRadioButtons(Blind, "Blind");

        RadioGroup ComputerIlliterate = (RadioGroup) v.findViewById(R.id.rgComputerIlliterate);
        UpdateRadioButtons(ComputerIlliterate, "Computer Illiterate");

        RadioGroup Deaf = (RadioGroup) v.findViewById(R.id.rgDeaf);
        UpdateRadioButtons(Deaf, "Deaf");

        RadioGroup DimmerBulb = (RadioGroup) v.findViewById(R.id.rgDimmerBulb);
        UpdateRadioButtons(DimmerBulb, "Dimmer Bulb");

        RadioGroup Illiterate = (RadioGroup) v.findViewById(R.id.rgIlliterate);
        UpdateRadioButtons(Illiterate, "Illiterate");

        RadioGroup Oblivious = (RadioGroup) v.findViewById(R.id.rgOblivious);
        UpdateRadioButtons(Oblivious, "Oblivious");

        RadioGroup PieIesuDomine = (RadioGroup) v.findViewById(R.id.rgPieIesuDomine);
        UpdateRadioButtons(PieIesuDomine, "Pie Iesu Domine");


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