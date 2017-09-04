package com.chuk3d;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;

/**
 * Created by Admin on 21/08/2017.
 */

public class GeometricShapeFragment extends Fragment {

    GridLayout gridLayout;
    ImageButton baseButton1, baseButton2, baseButton3, baseButton4, baseButton5, baseButton6, baseButton7, baseButton8, baseButton9, baseButton10, baseButton11, baseButton12, baseButton13, baseButton14, baseButton15, baseButton16, baseButton17, baseButton18, baseButton19, baseButton20, baseButton21, baseButton22, baseButton23, baseButton24, baseButton25, baseButton26, baseButton27, baseButton28, baseButton29, baseButton30, baseButton31, baseButton32, baseButton33, baseButton34, baseButton35, baseButton36;
    private FragmentItemClickCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.geometric_base_shape, container, false);

        init(rootView);

        return rootView;
    }

    public void init(View view){

        gridLayout = (GridLayout)view.findViewById(R.id.grid);
        gridLayout.setFocusable(false);


        baseButton1 = (ImageButton) view.findViewById(R.id.base1);
        baseButton2 = (ImageButton) view.findViewById(R.id.base2);
        baseButton3 = (ImageButton) view.findViewById(R.id.base3);
        baseButton4 = (ImageButton) view.findViewById(R.id.base4);
        baseButton5 = (ImageButton) view.findViewById(R.id.base5);
        baseButton6 = (ImageButton) view.findViewById(R.id.base6);
        baseButton7 = (ImageButton) view.findViewById(R.id.base7);
        baseButton8 = (ImageButton) view.findViewById(R.id.base8);
        baseButton9 = (ImageButton) view.findViewById(R.id.base9);
        baseButton10 = (ImageButton) view.findViewById(R.id.base10);
        baseButton11 = (ImageButton) view.findViewById(R.id.base11);
        baseButton12 = (ImageButton) view.findViewById(R.id.base12);
        baseButton13 = (ImageButton) view.findViewById(R.id.base13);
        baseButton14 = (ImageButton) view.findViewById(R.id.base14);
        baseButton15 = (ImageButton) view.findViewById(R.id.base15);
        baseButton16 = (ImageButton) view.findViewById(R.id.base16);
        baseButton17 = (ImageButton) view.findViewById(R.id.base17);
        baseButton18 = (ImageButton) view.findViewById(R.id.base18);
        baseButton19 = (ImageButton) view.findViewById(R.id.base19);
        baseButton20 = (ImageButton) view.findViewById(R.id.base20);
        baseButton21 = (ImageButton) view.findViewById(R.id.base21);
        baseButton22 = (ImageButton) view.findViewById(R.id.base22);
        baseButton23 = (ImageButton) view.findViewById(R.id.base23);
        baseButton24 = (ImageButton) view.findViewById(R.id.base24);
        baseButton25 = (ImageButton) view.findViewById(R.id.base25);
        baseButton26 = (ImageButton) view.findViewById(R.id.base26);
        baseButton27 = (ImageButton) view.findViewById(R.id.base27);
        baseButton28 = (ImageButton) view.findViewById(R.id.base28);
        baseButton29 = (ImageButton) view.findViewById(R.id.base29);
        baseButton30 = (ImageButton) view.findViewById(R.id.base30);
        baseButton31 = (ImageButton) view.findViewById(R.id.base31);
        baseButton32 = (ImageButton) view.findViewById(R.id.base32);
        baseButton33 = (ImageButton) view.findViewById(R.id.base33);
        baseButton34 = (ImageButton) view.findViewById(R.id.base34);
        baseButton35 = (ImageButton) view.findViewById(R.id.base35);
        baseButton36 = (ImageButton) view.findViewById(R.id.base36);

        ImageButton[]buttons = {baseButton1, baseButton2, baseButton3, baseButton4, baseButton5, baseButton6, baseButton7, baseButton8, baseButton9, baseButton10, baseButton11, baseButton12, baseButton13, baseButton14, baseButton15, baseButton16, baseButton17, baseButton18, baseButton19, baseButton20, baseButton21, baseButton22, baseButton23, baseButton24, baseButton25, baseButton26, baseButton27, baseButton28, baseButton29, baseButton30, baseButton31, baseButton32, baseButton33, baseButton34, baseButton35, baseButton36};

        for(ImageButton button: buttons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onBaseButtonClicked(v);

                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentItemClickCallback) {
            callback = (FragmentItemClickCallback) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement callback");
        }
    }


    public interface FragmentItemClickCallback {

        int onBaseButtonClicked(View view);
    }


}
