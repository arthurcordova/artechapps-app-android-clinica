package br.com.artechapps.app.fragment;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.database.PersistenceMessage;
import br.com.artechapps.app.database.PersistenceSchedule;
import br.com.artechapps.app.livedata.SchedulingLiveData;
import br.com.artechapps.app.model.Schedule;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskAppointment;
import br.com.artechapps.app.utils.Constants;
import br.com.artechapps.app.utils.SessionManager;

public class DashboardFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TIME_ANIMATION = 1000;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MainMenuActivity mActivity;
    private TextView mTvProcedureName;
    private TextView mTvProcedureDate;
    private View mCardNextScheduling;
    private TextView tvNumMessage;
    private TextView tvNumSchedule;
    private View mLineMessages;
    private View mLineSchedule;
    public NavigationView mNavigation;

    private SchedulingLiveData mSchedulingLiveData;
    private User mUser;


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

        mSchedulingLiveData = ViewModelProviders.of(this).get(SchedulingLiveData.class);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvNumMessage = (TextView) view.findViewById(R.id.num_message);
        tvNumSchedule = (TextView) view.findViewById(R.id.num_schedule);
        mLineMessages = view.findViewById(R.id.line_messages);
        mLineSchedule = view.findViewById(R.id.line_schedule);
        mCardNextScheduling = view.findViewById(R.id.cvNextSchedule);
        mTvProcedureName = (TextView) view.findViewById(R.id.tv_procedure_name);
        mTvProcedureDate = (TextView) view.findViewById(R.id.tv_procedure_date);

        mCardNextScheduling.setVisibility(View.GONE);
        tvNumSchedule.setText("0");

        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.
                tvNumSchedule.setText(newName);

                PersistenceSchedule persistence = new PersistenceSchedule(getContext());
                ArrayList<Schedule> list = persistence.getRecordsOK();

                if (list.size() > 0) {
                    mCardNextScheduling.setVisibility(View.VISIBLE);
                    mTvProcedureName.setText(list.get(0).getProduct().getDescription());
                    mTvProcedureDate.setText("Data: " + list.get(0).getDate()+  " Horário: " + list.get(0).getTime());

                } else {
                    mCardNextScheduling.setVisibility(View.GONE);
                }

            }
        };
        mSchedulingLiveData.getCurrentName().observeForever(nameObserver);

        AsyncTaskAppointment task1 = new AsyncTaskAppointment("Carregando agendamentos...", getContext(), true, null, mActivity, null, null);
        if (Constants.IS_DASHBOARD) {
            task1.mLiveData = mSchedulingLiveData;
        }
        task1.execute(String.valueOf(mUser.getCode()));

        int counterM = 0;
        PersistenceMessage perMessage = null;
        try {
            perMessage = new PersistenceMessage(getContext());
            counterM = perMessage.count();
        } finally {
            perMessage.close();
            setAnimationCounter(counterM, TIME_ANIMATION, tvNumMessage);
        }

        PersistenceSchedule persistence = new PersistenceSchedule(getContext());
        ArrayList<Schedule> list = persistence.getRecordsOK();

        int counter =  persistence.count();
        tvNumSchedule.setText(String.valueOf(counter));

        persistence.close();
        if (list.size() > 0) {
            mCardNextScheduling.setVisibility(View.VISIBLE);
            mTvProcedureName.setText(list.get(0).getProduct().getDescription());
            mTvProcedureDate.setText("Data: " + list.get(0).getDate()+  " Horário: " + list.get(0).getTime());

        } else {
            mCardNextScheduling.setVisibility(View.GONE);
        }

        mLineMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigation.getMenu().findItem(R.id.nav_message).setCheckable(true);
                mNavigation.getMenu().findItem(R.id.nav_message).setChecked(true);
                getFragmentManager().beginTransaction().replace(R.id.container, new MessageFragment()).commit();
            }
        });
        mLineSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigation.getMenu().findItem(R.id.nav_event).setCheckable(true);
                mNavigation.getMenu().findItem(R.id.nav_event).setChecked(true);
                getFragmentManager().beginTransaction().replace(R.id.container, new ScheduleFragment()).commit();
            }
        });

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
            SessionManager sm = new SessionManager(getActivity());
            mUser = sm.getSessionUser();

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
