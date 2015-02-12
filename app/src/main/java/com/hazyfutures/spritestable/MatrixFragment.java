package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MatrixFragment extends Fragment {
//Todo: MATRIX FRAGMENT
    //Read Sprite rules for assisting
    //Design UI: Pick actor first or pick Action first?  Then display options below?  Checkboxes to add assistors?
//Todo: Add Noise rating distance spinner
//Todo: Add Noise rating static spinner
//Todo: Add Playing hacking buttons, including manual die pool modifier
//Todo: Design convenient widget for picking sprites to assist or vice versa (Dropdown, Player Action/Sprite Action)
//Todo: Add code to calculate dice pools for each button including penalties, and
//Todo: add code to calculate dice pools for assistance rolls
//Todo: Roll dice button, determine final pool button (assistance), and list manual die rolls
//Todo: Calculate limits for rolls
//Todo: Drain for matrix spells
//ToDo Add Post-edge buttons for skill and drain. Set minimum number of hits desired for roll, re-roll failures and subtract edge if that number not met. Use Toast if edge used this way
//Todo: Update Stats for  karma
    //Todo: Update Stats for   damage
    //Todo: Update Stats for time
    //Todo: add onResume to refresh from DB
    //TODO: Add option to enable/disable Threads known

    //Todo: Add Playing Spells buttons
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_matrix, container, false);

        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        return v;
    }

    public static MatrixFragment newInstance() {

        MatrixFragment f = new MatrixFragment();
       /* Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);
*/
        return f;
    }
}