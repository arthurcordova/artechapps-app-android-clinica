package br.com.artechapps.app.fragment;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.database.PersistenceSchedule;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.SessionManager;

public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TIME_ANIMATION = 1000;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MainMenuActivity mActivity;
    private TextView tvNumScore;
    private TextView tvNumDiscount;
    private TextView tvNumMessage;
    private TextView tvNumSchedule;


    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvNumScore = (TextView) view.findViewById(R.id.num_score);
        tvNumDiscount = (TextView) view.findViewById(R.id.num_discount);
        tvNumMessage = (TextView) view.findViewById(R.id.num_message);
        tvNumSchedule = (TextView) view.findViewById(R.id.num_schedule);

        SessionManager sm = new SessionManager(getActivity());
        User user = sm.getSessionUser();

        setAnimationCounter(user.getScore(), TIME_ANIMATION, tvNumScore);
        setAnimationCounter(user.getDiscount(), TIME_ANIMATION, tvNumDiscount);
        setAnimationCounter(user.getMessages(), TIME_ANIMATION, tvNumMessage);
        setAnimationCounter(user.getScore(), TIME_ANIMATION, tvNumScore);

        int counter=0;
        PersistenceSchedule per = null;
        try {
            per = new PersistenceSchedule(getContext());
            counter = per.count();
        } finally {
            per.close();
            setAnimationCounter(counter, TIME_ANIMATION, tvNumSchedule);
        }



//        setAnimationCounter(10, TIME_ANIMATION, tvNumScore);
//        setAnimationCounter(55, TIME_ANIMATION, tvNumDiscount);
//        setAnimationCounter(12, TIME_ANIMATION, tvNumMessage);
//        setAnimationCounter(33, TIME_ANIMATION, tvNumSchedule);

        return view;
    }


    public void setAnimationCounter(int value, int duration, final TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, value);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(duration);
        animator.start();

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
