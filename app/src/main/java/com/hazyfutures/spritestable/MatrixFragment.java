package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MatrixFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();
//Todo: MATRIX FRAGMENT

//TODO:Design UI: Pick actor first or pick Action first?  Then display options below?  Checkboxes to add assistors?
// TODO: Spinner to modify dice used (+/- 20 dice?)
//TODO: Checkbox for pre-edge

//TODO: Display for Total hits, Total dice rolled, Glitch status, tap to display all dice rolled as Toast.
//TODO Dynamic radio button for list of potential actors
//TODO Programmatically add Items to the fragment by pulling actions from a database list that the current actor can take
//TODO: Button with Action Name

    // Die modifier spinner, Limit Modifier Spinner

    // Action Name, Total Dice, Total/Total/Total assisting dice, Limit(Assisted Limit),
    // Opposed dice, Marks Needed, Action Type,
    // Dropdown of lead actor default to character Multi-dropdown of valid assistors
    //Roll Assist button
    //Auto calculate Total dice and Assistant dice based on totals/limits
    //Expend services checkbox, default to checked

    //Two rows per action?
    //Spinner to pick action?




    //Todo: Add Playing Spells buttons
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_matrix, container, false);
        Main = (MainActivity)getActivity();
        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        //TODO: Finish Header:
        //TODO Spinner for active person
        //TODO: pop-up for NumberPicker selection to edit value field?
        //TODO: Perhaps reuse this on all number entry options?
        //TODO Pre-edge checkbox
        //TODO Post-edge button
        //TODO: Remember karma use when character is assisting

//TODO: One Service= An entire combat, one entire combat turn's worth of actions with a single action (job?), One use of a power
        //TODO: One service = Assist Threading = + dice pool by level


//Todo: Design convenient widget for picking sprites to assist or vice versa (Dropdown, Player Action/Sprite Action)
//Todo: Add code to calculate dice pools for each button including penalties, and
//Todo: add code to calculate dice pools for assistance rolls
//Todo: Roll dice button, determine final pool button (assistance), and list manual die rolls
//Todo: Calculate limits for rolls
//Todo: Drain for matrix spells
//ToDo Add Post-edge buttons for skill and drain. Set minimum number of hits desired for roll, re-roll failures and subtract edge if that number not met. Use Toast if edge used this way
//Todo: Update Stats for  karma
        //Todo: Update Stats for damage
        //Todo: Update Stats for time
        //Todo: add onResume to refresh from DB
        //TODO: Add option to enable/disable Threads known
        int rowCounter=0;
        for(MatrixActions ma: Main.data.pvMatrixActions){
            TableLayout tableLayout = (TableLayout) v.findViewById(R.id.tableMatrix);
            TableRow newRow = new TableRow(v.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            lp.span=5;
            newRow.setLayoutParams(lp);


            //TODO: Button to roll action  Needs listener to call dice rolling function
            Button btnAction = new Button(v.getContext(), null, android.R.attr.buttonStyleSmall);
            btnAction.setText(ma.getActionName());
            btnAction.setHeight(75);
            btnAction.setWidth(150);
            btnAction.setTextSize(10);
            btnAction.setMaxLines(2);

//todo text for number of dice

            //TODO: Button to roll assistance (grey itself and checkbox out after doing it, decrement services, add dice and limit to roll)
            Button btnAssist= new Button(v.getContext());
            btnAssist.setText("Roll Assist");
//after hit, roll dice, add hits to dice, bump limit, remove button


            //TODO Multicheckbox for Sprites that can assist, list all assistance options
            MultiSelectionSpinner msSprites = new MultiSelectionSpinner(v.getContext());
//add sum of assistance dice to assist button
//todo function to grab possible assisting sprites/character
            int assistdice=0;
            btnAssist.setText("Roll "+ assistdice +" to Assist");
            //todo text for opposing dice
            TextView oppdice = new TextView(v.getContext());
            oppdice.setText(ma.getOpposedAttribute() + "+" + ma.getOpposedSkill());
            newRow.addView(btnAction);
            newRow.addView(msSprites);
            newRow.addView(btnAssist);
            newRow.addView(oppdice);

            tableLayout.addView(newRow, rowCounter);
            //btnAction.setLayoutParams(new TableRow.LayoutParams(150, 75));
            rowCounter++;
        }



        return v;
    }

    //TODO: Dice rolling function that accepts MatrixAction object + Sprite object list for assistance

    public static MatrixFragment newInstance() {

        MatrixFragment f = new MatrixFragment();
       /* Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);
*/
        return f;
    }
}
