package com.chuk3d;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Admin on 21/08/2017.
 */

public class OtherPunchFragment extends Fragment {
    ImageButton punch1, punch2, punch3, punch4, punch5, punch6, punch7, punch8, punch9, punch10, punch11, punch12, punch13, punch14, punch15, punch16, punch17, punch18, punch19, punch20, punch21, punch22, punch23, punch24, punch25, punch26, punch27, punch28, punch29, punch30, punch31, punch32, punch33, punch34, punch35, punch36;
    private PunchFragmentItemClickCallback callback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.other_frag_punch, container, false);
        init(rootView);

        return rootView;
    }

    public void init(View view){

        punch1 = (ImageButton) view.findViewById(R.id.punch1);
        punch2 = (ImageButton) view.findViewById(R.id.punch2);
        punch3 = (ImageButton) view.findViewById(R.id.punch3);
        punch4 = (ImageButton) view.findViewById(R.id.punch4);
        punch5 = (ImageButton) view.findViewById(R.id.punch5);
        punch6 = (ImageButton) view.findViewById(R.id.punch6);
        punch7 = (ImageButton) view.findViewById(R.id.punch7);
        punch8 = (ImageButton) view.findViewById(R.id.punch8);
        punch9 = (ImageButton) view.findViewById(R.id.punch9);
        punch10 = (ImageButton) view.findViewById(R.id.punch10);
        punch11 = (ImageButton) view.findViewById(R.id.punch11);
        punch12 = (ImageButton) view.findViewById(R.id.punch12);
        punch13 = (ImageButton) view.findViewById(R.id.punch13);
        punch14 = (ImageButton) view.findViewById(R.id.punch14);
        punch15 = (ImageButton) view.findViewById(R.id.punch15);
        punch16 = (ImageButton) view.findViewById(R.id.punch16);
        punch17 = (ImageButton) view.findViewById(R.id.punch17);
        punch18 = (ImageButton) view.findViewById(R.id.punch18);
        punch19 = (ImageButton) view.findViewById(R.id.punch19);
        punch20 = (ImageButton) view.findViewById(R.id.punch20);
        punch21 = (ImageButton) view.findViewById(R.id.punch21);
        punch22 = (ImageButton) view.findViewById(R.id.punch22);
        punch23 = (ImageButton) view.findViewById(R.id.punch23);
        punch24 = (ImageButton) view.findViewById(R.id.punch24);
        punch25 = (ImageButton) view.findViewById(R.id.punch25);
        punch26 = (ImageButton) view.findViewById(R.id.punch26);
        punch27 = (ImageButton) view.findViewById(R.id.punch27);
        punch28 = (ImageButton) view.findViewById(R.id.punch28);
        punch29 = (ImageButton) view.findViewById(R.id.punch29);
        punch30 = (ImageButton) view.findViewById(R.id.punch30);
        punch31 = (ImageButton) view.findViewById(R.id.punch31);
        punch32 = (ImageButton) view.findViewById(R.id.punch32);
        punch33 = (ImageButton) view.findViewById(R.id.punch33);
        punch34 = (ImageButton) view.findViewById(R.id.punch34);
        punch35 = (ImageButton) view.findViewById(R.id.punch35);
        punch36 = (ImageButton) view.findViewById(R.id.punch36);

        ImageButton[]buttons = {punch1, punch2, punch3, punch4, punch5, punch6, punch7, punch8, punch9, punch10, punch11, punch12, punch13, punch14, punch15, punch16, punch17, punch18, punch19, punch20, punch21, punch22, punch23, punch24, punch25, punch26, punch27, punch28, punch29, punch30, punch31, punch32, punch33, punch34, punch35, punch36};

        for(ImageButton button: buttons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onPunchButtonClicked(v);

                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PunchFragmentItemClickCallback) {
            callback = (PunchFragmentItemClickCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement callback");
        }
    }


    public interface PunchFragmentItemClickCallback {

        void onPunchButtonClicked(View view);
    }
}
