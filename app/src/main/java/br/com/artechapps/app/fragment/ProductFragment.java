package br.com.artechapps.app.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.activity.MainMenuActivity;
import br.com.artechapps.app.adapter.RVAdapterMessage;
import br.com.artechapps.app.adapter.RVAdapterProduct;
import br.com.artechapps.app.database.Persistence;
import br.com.artechapps.app.database.PersistenceProduct;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.task.AsyncTaskProduct;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private PersistenceProduct mPersistence;
    private ArrayList<Product> mList;
    private MainMenuActivity mActivity;

    private RecyclerView mRvProduct;
    private SearchView mSearchItem;


    public void setSearchView(SearchView searchItem){
        mSearchItem = searchItem;
    }

    public ProductFragment() {
        // Required empty public constructor

    }

//    public ProductFragment(SearchView searchItem) {
//        mSearchItem = searchItem;
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        mRvProduct = (RecyclerView) view.findViewById(R.id.rvProducts);
        TextView mTvNullList = (TextView) view.findViewById(R.id.tv_null_list);
        mActivity = (MainMenuActivity)getActivity();

        new AsyncTaskProduct("Carregando produtos...",getContext(),true, mRvProduct, mActivity).execute(String.valueOf(BuildConfig.FILIAL));

        mPersistence = new PersistenceProduct(getContext());
        mList = mPersistence.getProduct();

        final RVAdapterProduct mAdapter = new RVAdapterProduct(mList, mActivity);
        if (mList.size() > 0) {
            mTvNullList.setVisibility(View.INVISIBLE);

            mRvProduct.setLayoutManager(new LinearLayoutManager(getContext()));
            mRvProduct.setItemAnimator(new DefaultItemAnimator());
            mRvProduct.setAdapter(mAdapter);
        } else {
            mTvNullList.setVisibility(View.VISIBLE);
        }
        mPersistence.close();

        mSearchItem.setIconified(true);
        mSearchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 1) {
                    ArrayList<Product> list = filter(mList, query);
                    if (list != null && list.size() > 0) {

                        mAdapter.setFilter(list);
                        loadListProviders(mAdapter);

                    } else {
                        mAdapter.setFilter(list);
                        loadListProviders(mAdapter);

                    }
                }
                if (query.equals("")){
                    mPersistence = new PersistenceProduct(getContext());
                    mAdapter.setFilter(mPersistence.getProduct());
                    loadListProviders(mAdapter);
                }
                return true;
            }
        });

//        mSearchItem.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                mAdapter.setFilter(mPersistence.getProduct());
//                loadListProviders(mAdapter);
//                return true;
//            }
//        });

        return view;
    }

    private void loadListProviders(RVAdapterProduct adapterProduct) {
        mRvProduct.setLayoutManager(new LinearLayoutManager(mRvProduct.getContext()));
        mRvProduct.setHasFixedSize(true);
        mRvProduct.setAdapter(adapterProduct);

    }

    private ArrayList<Product> filter(ArrayList<Product> list, String query) {
        final ArrayList<Product> filteredModelList = new ArrayList<>();
        for (Product model : list) {
            if (model.getDescription().toUpperCase().contains(query.toUpperCase())) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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
