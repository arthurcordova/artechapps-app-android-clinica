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

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.FilterMessageActivity;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.adapter.RVAdapterMessage;
import br.com.artechapps.app.database.PersistenceMessage;
import br.com.artechapps.app.model.Message;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskMessages;
import br.com.artechapps.app.utils.SessionManager;
import br.com.artechapps.app.utils.UtilsDate;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private PersistenceMessage mPersistence;
    private ArrayList<Message> mList;
    private MainMenuActivity mActivity;
    private RVAdapterMessage mAdapter;

    private RecyclerView mRvMessages;
    private FloatingActionButton mFab;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        SessionManager sm = new SessionManager(getContext());
        User user = sm.getSessionUser();

        mRvMessages = (RecyclerView) view.findViewById(R.id.rvMessages);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_filter);

        mActivity = (MainMenuActivity)getActivity();

        new AsyncTaskMessages("Carregando mensagens...", getContext(), true, mRvMessages, mActivity).execute(String.valueOf(user.getCode()));

        mPersistence = new PersistenceMessage(getContext());
        mList = mPersistence.getRecords();

        mAdapter = new RVAdapterMessage(mList, mActivity);

        mRvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvMessages.setItemAnimator(new DefaultItemAnimator());
        mRvMessages.setAdapter(mAdapter);

        mPersistence.close();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mActivity, FilterMessageActivity.class), 0);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        try {
            mPersistence = new PersistenceMessage(getContext());
            mList = mPersistence.getRecords();

            mAdapter = new RVAdapterMessage(mList, mActivity);

            mRvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
            mRvMessages.setItemAnimator(new DefaultItemAnimator());
            mRvMessages.setAdapter(mAdapter);
        } finally {

            mPersistence.close();
        }

        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            final ArrayList<Message> filterList = filter(mList, data.getExtras());
            mAdapter.setFilter(filterList);

        }
    }

    private ArrayList<Message> filter(ArrayList<Message> messages, Bundle args) {
        String startDate = args.getString("start_date");
        String endDate = args.getString("end_date");
        boolean isRead = args.getBoolean("is_read");
        boolean isNotRead = args.getBoolean("is_not_read");


        final ArrayList<Message> filteredList = new ArrayList<>();
        for (Message model : messages) {
            boolean insert = true;
            if (UtilsDate.between( UtilsDate.convert(model.getSentDate()),
                    UtilsDate.convert(startDate),
                    UtilsDate.convert(endDate))){

                if (!isRead && model.isSee()){
                    insert = false;

                }

                if (insert)
                    filteredList.add(model);
            }
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
