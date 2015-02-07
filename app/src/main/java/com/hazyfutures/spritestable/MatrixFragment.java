package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MatrixFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_matrix, container, false);

        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
        return v;
    }

    public static MatrixFragment newInstance(PersistentValues data) {

        MatrixFragment f = new MatrixFragment();
        Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}