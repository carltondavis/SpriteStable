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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qualities, container, false);
        Main = (MainActivity)getActivity();

        RadioGroup rgInsomnia = (RadioGroup) v.findViewById(R.id.rgInsomnia);
        rgInsomnia.check(rgInsomnia.getChildAt(Main.data.getInsomnia()).getId());
        rgInsomnia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setInsomnia(getValue(checkedId));

            }
        });

        RadioGroup rgFocusedConcentration = (RadioGroup) v.findViewById(R.id.rgFocusedConcentration);
        rgFocusedConcentration.check(rgFocusedConcentration.getChildAt(Main.data.getFocusedConcentration()).getId());
        rgFocusedConcentration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setFocusedConcentration(getValue(checkedId));

            }
        });

        RadioGroup HighPainTolerance = (RadioGroup) v.findViewById(R.id.rgHighPainTolerance);
        HighPainTolerance.check(HighPainTolerance.getChildAt(Main.data.getHighPainTolerance()).getId());
        HighPainTolerance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setHighPainTolerance(getValue(checkedId));

            }
        });
        RadioGroup HomeGround = (RadioGroup) v.findViewById(R.id.rgHomeGround);
        HomeGround.check(HomeGround.getChildAt(Main.data.getHomeGround()).getId());
        HomeGround.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setHomeGround(getValue(checkedId));

            }
        });
        RadioGroup NaturalHardening = (RadioGroup) v.findViewById(R.id.rgNaturalHardening);
        NaturalHardening.check(NaturalHardening.getChildAt(Main.data.getNaturalHardening()).getId());
        NaturalHardening.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setNaturalHardening(getValue(checkedId));

            }
        });
        RadioGroup QuickHealer = (RadioGroup) v.findViewById(R.id.rgQuickHealer);
        QuickHealer.check(QuickHealer.getChildAt(Main.data.getQuickHealer()).getId());
        QuickHealer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setQuickHealer(getValue(checkedId));

            }
        });
        RadioGroup Toughness = (RadioGroup) v.findViewById(R.id.rgToughness);
        Toughness.check(Toughness.getChildAt(Main.data.getToughness()).getId());
        Toughness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setToughness(getValue(checkedId));

            }
        });
        RadioGroup WillToLive = (RadioGroup) v.findViewById(R.id.rgWillToLive);
        WillToLive.check(WillToLive.getChildAt(Main.data.getWillToLive()).getId());
        WillToLive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setWillToLive(getValue(checkedId));

            }
        });
        RadioGroup BadLuck = (RadioGroup) v.findViewById(R.id.rgBadLuck);
        BadLuck.check(BadLuck.getChildAt(Main.data.getBadLuck()).getId());
        BadLuck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setBadLuck(getValue(checkedId));

            }
        });
        RadioGroup LowPainTolerance = (RadioGroup) v.findViewById(R.id.rgLowPainTolerance);
        LowPainTolerance.check(LowPainTolerance.getChildAt(Main.data.getLowPainTolerance()).getId());
        LowPainTolerance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setLowPainTolerance(getValue(checkedId));

            }
        });
        RadioGroup SensitiveSystem = (RadioGroup) v.findViewById(R.id.rgSensitiveSystem);
        SensitiveSystem.check(SensitiveSystem.getChildAt(Main.data.getSensitiveSystem()).getId());
        SensitiveSystem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSensitiveSystem(getValue(checkedId));

            }
        });
        RadioGroup SimsenseVertigo = (RadioGroup) v.findViewById(R.id.rgSimsenseVertigo);
        SimsenseVertigo.check(SimsenseVertigo.getChildAt(Main.data.getSimsenseVertigo()).getId());
        SimsenseVertigo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSimsenseVertigo(getValue(checkedId));

            }
        });
        RadioGroup SlowHealer = (RadioGroup) v.findViewById(R.id.rgSlowHealer);
        SlowHealer.check(SlowHealer.getChildAt(Main.data.getSlowHealer()).getId());
        SlowHealer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSlowHealer(getValue(checkedId));

            }
        });
        RadioGroup Perceptive = (RadioGroup) v.findViewById(R.id.rgPerceptive);
        Perceptive.check(Perceptive.getChildAt(Main.data.getPerceptive()).getId());
        Perceptive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setPerceptive(getValue(checkedId));

            }
        });
        RadioGroup SpikeResistance = (RadioGroup) v.findViewById(R.id.rgSpikeResistance);
        SpikeResistance.check(SpikeResistance.getChildAt(Main.data.getSpikeResistance()).getId());
        SpikeResistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setSpikeResistance(getValue(checkedId));

            }
        });
        RadioGroup ToughAsNailsPhysical = (RadioGroup) v.findViewById(R.id.rgToughAsNailsPhysical);
        ToughAsNailsPhysical.check(ToughAsNailsPhysical.getChildAt(Main.data.getToughAsNailsPhysical()).getId());
        ToughAsNailsPhysical.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setToughAsNailsPhysical(getValue(checkedId));

            }
        });
        RadioGroup ToughAsNailsStun = (RadioGroup) v.findViewById(R.id.rgToughAsNailsStun);
        ToughAsNailsStun.check(ToughAsNailsStun.getChildAt(Main.data.getToughAsNailsStun()).getId());
        ToughAsNailsStun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setToughAsNailsStun(getValue(checkedId));

            }
        });
        RadioGroup Asthma = (RadioGroup) v.findViewById(R.id.rgAsthma);
        Asthma.check(Asthma.getChildAt(Main.data.getAsthma()).getId());
        Asthma.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setAsthma(getValue(checkedId));

            }
        });
        RadioGroup BiPolar = (RadioGroup) v.findViewById(R.id.rgBiPolar);
        BiPolar.check(BiPolar.getChildAt(Main.data.getBiPolar()).getId());
        BiPolar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setBiPolar(getValue(checkedId));

            }
        });
        RadioGroup Blind = (RadioGroup) v.findViewById(R.id.rgBlind);
        Blind.check(Blind.getChildAt(Main.data.getBlind()).getId());
        Blind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setBlind(getValue(checkedId));

            }
        });
        RadioGroup ComputerIlliterate = (RadioGroup) v.findViewById(R.id.rgComputerIlliterate);
        ComputerIlliterate.check(ComputerIlliterate.getChildAt(Main.data.getComputerIlliterate()).getId());
        ComputerIlliterate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setComputerIlliterate(getValue(checkedId));

            }
        });
        RadioGroup Deaf = (RadioGroup) v.findViewById(R.id.rgDeaf);
        Deaf.check(Deaf.getChildAt(Main.data.getDeaf()).getId());
        Deaf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setDeaf(getValue(checkedId));

            }
        });
        RadioGroup DimmerBulb = (RadioGroup) v.findViewById(R.id.rgDimmerBulb);
        DimmerBulb.check(DimmerBulb.getChildAt(Main.data.getDimmerBulb()).getId());
        DimmerBulb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setDimmerBulb(getValue(checkedId));

            }
        });
        RadioGroup Illiterate = (RadioGroup) v.findViewById(R.id.rgIlliterate);
        Illiterate.check(Illiterate.getChildAt(Main.data.getIlliterate()).getId());
        Illiterate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setIlliterate(getValue(checkedId));

            }
        });
        RadioGroup Oblivious = (RadioGroup) v.findViewById(R.id.rgOblivious);
        Oblivious.check(Oblivious.getChildAt(Main.data.getOblivious()).getId());
        Oblivious.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Main.data.setOblivious(getValue(checkedId));

            }
        });
        RadioGroup PieIesuDomine = (RadioGroup) v.findViewById(R.id.rgPieIesuDomine);
        PieIesuDomine.check(PieIesuDomine.getChildAt(Main.data.getPieIesuDomine()).getId());
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