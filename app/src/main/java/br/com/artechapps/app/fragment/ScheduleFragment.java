package br.com.artechapps.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.activity.NewScheduleActivity;
import br.com.artechapps.app.adapter.RVAdapterSchedule;
import br.com.artechapps.app.database.PersistenceSchedule;
import br.com.artechapps.app.model.Schedule;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskAppointment;
import br.com.artechapps.app.utils.SessionManager;

public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PersistenceSchedule mPersistence;
    private ArrayList<Schedule> mList;
    private MainMenuActivity mActivity;
    private RecyclerView mRvSchedules;
    private FloatingActionButton mFab;
    private SwipeRefreshLayout mSrl;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        SessionManager sm = new SessionManager(getContext());
        final User user = sm.getSessionUser();

        mRvSchedules = (RecyclerView) view.findViewById(R.id.rvSchedules);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_new_schedule);
        final TextView mTvNullList = (TextView) view.findViewById(R.id.tv_null_list);

        mActivity = (MainMenuActivity)getActivity();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, NewScheduleActivity.class));
            }
        });

        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTaskAppointment("Carregando agendamentos...", getContext(), true, mRvSchedules, mActivity, mSrl, mTvNullList).execute(String.valueOf(user.getCode()));
            }
        });


        new AsyncTaskAppointment("Carregando agendamentos...", getContext(), true, mRvSchedules, mActivity, mSrl, mTvNullList).execute(String.valueOf(user.getCode()));

        mPersistence = new PersistenceSchedule(getContext());
        mList = mPersistence.getRecords();

        RVAdapterSchedule adapter = new RVAdapterSchedule(mList, mActivity);

        mRvSchedules.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvSchedules.setItemAnimator(new DefaultItemAnimator());
        mRvSchedules.setAdapter(adapter);

        mPersistence.close();

        return view;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
