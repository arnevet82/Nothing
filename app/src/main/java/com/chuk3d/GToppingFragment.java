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

public class GToppingFragment extends Fragment {
    ImageButton topping1, topping2, topping3, topping4, topping5, topping6, topping7, topping8, topping9, topping10, topping11, topping12, topping13, topping14, topping15, topping16, topping17, topping18, topping19, topping20, topping21, topping22, topping23, topping24, topping25, topping26, topping27, topping28, topping29, topping30, topping31, topping32, topping33, topping34, topping35, topping36;
    private ToppinfFragmentItemClickCallback callback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.g_frag_topping, container, false);
        init(rootView);

        return rootView;
    }

    public void init(View view){

        topping1 = (ImageButton) view.findViewById(R.id.topping1);
        topping2 = (ImageButton) view.findViewById(R.id.topping2);
        topping3 = (ImageButton) view.findViewById(R.id.topping3);
        topping4 = (ImageButton) view.findViewById(R.id.topping4);
        topping5 = (ImageButton) view.findViewById(R.id.topping5);
        topping6 = (ImageButton) view.findViewById(R.id.topping6);
        topping7 = (ImageButton) view.findViewById(R.id.topping7);
        topping8 = (ImageButton) view.findViewById(R.id.topping8);
        topping9 = (ImageButton) view.findViewById(R.id.topping9);
        topping10 = (ImageButton) view.findViewById(R.id.topping10);
        topping11 = (ImageButton) view.findViewById(R.id.topping11);
        topping12 = (ImageButton) view.findViewById(R.id.topping12);
        topping13 = (ImageButton) view.findViewById(R.id.topping13);
        topping14 = (ImageButton) view.findViewById(R.id.topping14);
        topping15 = (ImageButton) view.findViewById(R.id.topping15);
        topping16 = (ImageButton) view.findViewById(R.id.topping16);
        topping17 = (ImageButton) view.findViewById(R.id.topping17);
        topping18 = (ImageButton) view.findViewById(R.id.topping18);
        topping19 = (ImageButton) view.findViewById(R.id.topping19);
        topping20 = (ImageButton) view.findViewById(R.id.topping20);
        topping21 = (ImageButton) view.findViewById(R.id.topping21);
        topping22 = (ImageButton) view.findViewById(R.id.topping22);
        topping23 = (ImageButton) view.findViewById(R.id.topping23);
        topping24 = (ImageButton) view.findViewById(R.id.topping24);
        topping25 = (ImageButton) view.findViewById(R.id.topping25);
        topping26 = (ImageButton) view.findViewById(R.id.topping26);
        topping27 = (ImageButton) view.findViewById(R.id.topping27);
        topping28 = (ImageButton) view.findViewById(R.id.topping28);
        topping29 = (ImageButton) view.findViewById(R.id.topping29);
        topping30 = (ImageButton) view.findViewById(R.id.topping30);
        topping31 = (ImageButton) view.findViewById(R.id.topping31);
        topping32 = (ImageButton) view.findViewById(R.id.topping32);
        topping33 = (ImageButton) view.findViewById(R.id.topping33);
        topping34 = (ImageButton) view.findViewById(R.id.topping34);
        topping35 = (ImageButton) view.findViewById(R.id.topping35);
        topping36 = (ImageButton) view.findViewById(R.id.topping36);

        ImageButton[]buttons = {topping1, topping2, topping3, topping4, topping5, topping6, topping7, topping8, topping9, topping10, topping11, topping12, topping13, topping14, topping15, topping16, topping17, topping18, topping19, topping20, topping21, topping22, topping23, topping24, topping25, topping26, topping27, topping28, topping29, topping30, topping31, topping32, topping33, topping34, topping35, topping36};

        for(ImageButton button: buttons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onAddButtonClicked(v, "tShape");

                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToppinfFragmentItemClickCallback) {
            callback = (ToppinfFragmentItemClickCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement callback");
        }
    }


    public interface ToppinfFragmentItemClickCallback {

        void onAddButtonClicked(View view, String tag);
    }
}
