package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QualitiesFragment extends Fragment {

//TODO QUALITIES FRAGMENT:

//Todo: Create database to hold these records
//Todo: UI widgets for each entry
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
//Pie Iesu Domine: High pain tolerence 1, Always has one box physical damage
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

        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        return v;
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