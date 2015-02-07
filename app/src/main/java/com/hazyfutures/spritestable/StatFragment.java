package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class StatFragment extends Fragment {
    PersistentValues data = new PersistentValues();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stat, container, false);
        data.RestoreFromDB(v.getContext());
        /*TextView tv = (TextView) v.findViewById(R.id.tvFragFirst);
        tv.setText(getArguments().getString("msg"));
*/
/*
        CreateListener(R.id.editBody, data.pvBody);
        CreateListener(R.id.editWillpower, data.pvWillpower );
        CreateListener(R.id.editIntuition, data.pvIntuition);
        CreateListener(R.id.editLogic, data.pvLogic);
        CreateListener(R.id.editCharisma, data.pvCharisma);
        CreateListener(R.id.editCompiling, data.pvCompiling);
        CreateListener(R.id.editRegistering, data.pvRegistering);
        CreateListener(R.id.editResonance, data.pvResonance);
        CreateListener(R.id.editStun, data.pvStun);
        CreateListener(R.id.editPhysical, data.pvPhysical);
        CreateListener(R.id.editKarma, data.pvKarma);
        CreateListener(R.id.editKarmaUsed, data.pvKarmaUsed);
        CreateListener(R.id.editConsecutiveHoursRested, data.pvConsecutiveRest);
        CreateListener(R.id.editHoursWithoutSleep, data.pvSleeplessHours);
        CreateListener(R.id.editHoursThisSession, data.pvHoursThisSession);
        CreateListener(R.id.editHoursSinceKarmaRefresh, data.pvHoursSinceKarmaRefresh);
*/
        return v;
    }

    public static StatFragment newInstance(PersistentValues data) {

        StatFragment f = new StatFragment();
        Bundle b = new Bundle();
        //b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    private void CreateListener(Integer etId, Integer value) {
        final EditText et = (EditText) this.getView().findViewById(etId);
        et.setText(String.valueOf(value));
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(et.getText())) {
                    UpdateStatsStats(et);
                    //UpdateCompileDisplay(vg);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void UpdateStatsStats(View view) {
        EditText et = (EditText) this.getView().findViewById(view.getId());
        if (!et.getText().toString().isEmpty()) {
            Integer value = Integer.valueOf(et.getText().toString());

            switch (view.getId()) {
                case R.id.editBody:
                    data.pvBody = value;
                    data.SaveStatToDB("Body", value);
                    break;
                case R.id.editWillpower:
                    data.pvWillpower = value;
                    data.SaveStatToDB("Willpower", value);
                    break;
                case R.id.editIntuition:
                    data.pvIntuition = value;
                    data.SaveStatToDB("Intuition", value);
                    break;
                case R.id.editLogic:
                    data.pvLogic = value;
                    data.SaveStatToDB("Logic", value);
                    break;
                case R.id.editCharisma:
                    data.pvCharisma = value;
                    data.SaveStatToDB("Charisma", value);
                    break;
                case R.id.editCompiling:
                    data.pvCompiling = value;
                    data.SaveStatToDB("Compiling", value);
                    break;
                case R.id.editRegistering:
                    data.pvRegistering = value;
                    data.SaveStatToDB("Registering", value);
                    break;
                case R.id.editResonance:
                    data.pvResonance = value;
                    data.SaveStatToDB("Resonance", value);
                    break;
                case R.id.editStun:
                    data.pvStun = value;
                    data.SaveStatToDB("Stun", value);
                    break;
                case R.id.editPhysical:
                    data.pvPhysical = value;

                    data.SaveStatToDB("Physical", value);
                    break;
                case R.id.editKarma:
                    data.pvKarma = value;
                    data.SaveStatToDB("Karma", value);
                    break;
            }
        }


    }
}