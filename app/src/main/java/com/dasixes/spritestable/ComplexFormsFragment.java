package com.dasixes.spritestable;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ComplexFormsFragment extends Fragment {
    MainActivity Main = (MainActivity)getActivity();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_matrix, container, false);
        Main = (MainActivity)getActivity();

        return v;
    }




    public static ComplexFormsFragment newInstance() {

       ComplexFormsFragment f = new ComplexFormsFragment();
       /* Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);
*/
        return f;
    }
}
