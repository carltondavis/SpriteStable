package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class MatrixFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();
//Todo: MATRIX FRAGMENT

    //TODO:Design UI: Pick actor first or pick Action first?  Then display options below?  Checkboxes to add assistors?
    // TODO: Spinner to modify dice used (+/- 20 dice?)
    //TODO: Checkbox for pre-edge
    //TODO: Display for Total hits, Total dice rolled, Glitch status, tap to display all dice rolled as Toast.
//TODO Dynamic radio button for list of potential actors

    //TODO Programatically add Items to the fragment by pulling actions from a database list that the current actor can take
    //TODO: Button with Action Name
    // TODO: Text with total dice,
    //TODO Editable text box for limit
    //TODO Multicheckbox for Sprites that can assist
    //TODO: Button to roll assistance (grey itself and checkbox out after doing it, decrement services, add dice and limit to roll)
    //TODO: Button to roll action
    //TODO: Remember karma use when character is assisting

//TODO: One Service= An entire combat, one entire combat turn's worth of actions with a single action (job?), One use of a power
    //TODO: One service = Assist Threading = + dice pool by level


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
        Main = (MainActivity)getActivity();
        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        TableLayout tableLayout = (TableLayout) v.findViewById(R.id.tableMatrix);
        TableRow newRow = new TableRow(v.getContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        newRow.setLayoutParams(lp);

        Button newButton = new Button(v.getContext());

        newButton.setText("hi");
        newRow.addView(newButton);
        tableLayout.addView(newRow, 0);

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