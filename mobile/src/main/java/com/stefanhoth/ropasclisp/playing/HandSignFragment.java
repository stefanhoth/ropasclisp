package com.stefanhoth.ropasclisp.playing;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.novoda.notils.caster.Views;
import com.stefanhoth.ropasclisp.R;

public class HandSignFragment extends Fragment implements View.OnClickListener {

    private HandSign handSign;
    private OnFragmentInteractionListener mListener;

    private static final String KEY_PARAM = "KEY_PARAM";

    public static HandSignFragment with(HandSign handSign) {

        HandSignFragment fragment = new HandSignFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PARAM, handSign.name());
        fragment.setArguments(args);
        return fragment;
    }

    public HandSignFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            handSign = HandSign.valueOf(getArguments().getString(KEY_PARAM));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hand_sign, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ImageView signView = Views.findById(view, R.id.sign);
        signView.setImageResource(handSign.getVectorResId());
        signView.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement " + OnFragmentInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onHandSignSelected(handSign);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onHandSignSelected(HandSign handSign);
    }

}
