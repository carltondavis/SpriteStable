package com.hazyfutures.spritestable;
//TODO COMPILE FRAGMENT:

//TODO SPRITE FRAGMENT:
//Todo Add display of sprite stats + abilities for specific sprite listed
//ToDo Add list of important qualities
//Todo: Add skill specializations  List of Checkboxes under the skill, check for them when compiling/registering
//Todo Update Database to handle qualities
//Todo Update Compile/Register/Rest to account for qualities

//TODO QUALITIES FRAGMENT:
//Todo list of qualities:
//Todo: Create database to hold these records
//Todo: UI widgets for each entry
//Todo: Code to load and save info
//Todo: Add modifiers for any given die rolls to make these stats generic (MatrixMod, PerceptionMod, HealingMod, etc)
//Todo: Add code for existing die rolls

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


//ToDo Add possible Overflow/Death warnings If compiling/registering something, calculate if worst case stun/physical could max out stun or physical chart.  Warn of possibility.
//ToDo Add automated registering system: Set the amount of time to use, and it runs

//Todo: MATRIX FRAGMENT
//Todo: Add Noise rating distance spinner
//Todo: Add Noise rating static spinner
//Todo: Add Playing hacking buttons, including manual die pool modifier
//Todo: Design convenient widget for picking sprites to assist or vice versa (Dropdown, Player Action/Sprite Action)
//Todo: Add code to calculate dice pools for each button including penalties, and
//Todo: add code to calculate dice pools for assistance rolls
//Todo: Roll dice button, determine final pool button (assistance), and list manual die rolls
//Todo: Calculate limits for rolls
//Todo: Drain for matrix spells

//Fancy UI:
//TODO Something multiplying sprites in list?  Haven't seen recently
//TODO Test update for each attribute
//Todo Heal after 24 hours consecutive rest
//Todo karma regen after 8 hours consecutive rest
//todo test consecutive rest karma reset
//ToDo Change Hours to Total Hours.
//ToDo Add penalty popup warnings  Add Total Penalty display, tap it to have a toast pop up listing sources of penalties
//ToDo Add Post-edge buttons for skill and drain. Set minimum number of hits desired for roll, re-roll failures and subtract edge if that number not met. Use Toast if edge used this way
//Todo add Toast for disabled buttons explaining why they're disabled
//Todo Someday Add statistics for rolls to include actual percentage chance of something happening to warnings
//TODO settings page: dice rolls display, Fatigue rules, fatigue warnings, Penalty popup warning, possible death warning
//ToDo: Only load from DB when persisted data doesn't exist.
//Todo: Change DB writing to happen on pause/close.  Load from DB on resume
//Todo add multiple character option
//Todo add a Spirit/Mage handler option
//Todo: Add Playing Spells buttons


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter{
    // Declare Variables
    //TODO Declare local variables for persistant data
    PersistentValues data = new PersistentValues();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0: return CompileFragment.newInstance(data);
            case 1: return StatFragment.newInstance(data);
            case 2: return SpriteFragment.newInstance(data);
            case 3: return QualitiesFragment.newInstance(data);
            case 4: return MatrixFragment.newInstance(data);
            default: return CompileFragment.newInstance(data);
        }

    }

    @Override
    public int getCount() {
        //TODO Increase number as new layouts are added
        return 5;//5 pages SpriteCompiler 1, Stats 2, Sprite 3 Qualities 4, MatrixActions 5
    }


  /*  @Override
    public boolean isViewFromObject(View view, Object object) {
        if(object != null) {
            return view == ((RelativeLayout) object);
        }else{
            return false;
        }

    }

    public void onPageSelected(int position) {

    }
*/

}