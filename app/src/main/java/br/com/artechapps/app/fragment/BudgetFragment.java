package br.com.artechapps.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.FilterBudgetActivity;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.adapter.RVAdapterBudget;
import br.com.artechapps.app.database.PersistenceBudget;
import br.com.artechapps.app.model.Budget;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskBudget;
import br.com.artechapps.app.utils.SessionManager;

public class BudgetFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRvMessages;
    private MainMenuActivity mActivity;
    private PersistenceBudget mPersistence;
    private ArrayList<Budget> mList;
    private FloatingActionButton mFab;
    private FloatingActionButton mFabAdd;
    private RVAdapterBudget mAdapter;


    public BudgetFragment() {
        // Required empty public constructor
    }

    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        SessionManager sm = new SessionManager(getActivity());
        User user = sm.getSessionUser();

        mRvMessages = (RecyclerView) view.findViewById(R.id.rvBudget);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_filter);
        mFabAdd = (FloatingActionButton) view.findViewById(R.id.fab_add);
        TextView mTvNullList = (TextView) view.findViewById(R.id.tv_null_list);

        mActivity = (MainMenuActivity) getActivity();

        try {
            mPersistence = new PersistenceBudget(mActivity);
            mList = mPersistence.getRecords();

            if (mList.size() > 0) {
                mTvNullList.setVisibility(View.INVISIBLE);
            } else {
                mTvNullList.setVisibility(View.VISIBLE);
            }
            mAdapter = new RVAdapterBudget(mList, mActivity);

            mRvMessages.setLayoutManager(new LinearLayoutManager(mActivity));
            mRvMessages.setItemAnimator(new DefaultItemAnimator());
            mRvMessages.setAdapter(mAdapter);

        } finally {
            mPersistence.close();

        }

        new AsyncTaskBudget("Carregando orçamentos...",getContext(),true, mRvMessages, mActivity).execute(String.valueOf(user.getCode()));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mActivity, FilterBudgetActivity.class), 0);
            }
        });
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
                Intent it = new Intent(mActivity, MainMenuActivity.class);
                it.putExtra("origin", BudgetFragment.class.getName());
                startActivityForResult(it, 0);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            final ArrayList<Budget> filterList = filter(mList, data.getExtras());
            mAdapter.setFilter(filterList);
            mRvMessages.setLayoutManager(new LinearLayoutManager(mRvMessages.getContext()));
            mRvMessages.setHasFixedSize(true);
            mRvMessages.setAdapter(mAdapter);
        }
    }

    private ArrayList<Budget> filter(ArrayList<Budget> budgets, Bundle args) {
        boolean isAll = args.getBoolean("sw_all");
        boolean isConfirmed = args.getBoolean("sw_confirmed");
        boolean isCancelled = args.getBoolean("sw_cancelled");
        ArrayList<Budget> filteredList = new ArrayList<>();

        if (!isAll) {
            for (Budget model : budgets) {
                if (isConfirmed){
                    if (model.getStatus().equalsIgnoreCase("CONFIRMADO")){
                        filteredList.add(model);
                    }
                }
                if (isCancelled){
                    if (model.getStatus().equalsIgnoreCase("CANCELADO")) {
                        filteredList.add(model);
                    }
                }
            }
        } else {
            filteredList.addAll(budgets);
        }
        return filteredList;
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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
