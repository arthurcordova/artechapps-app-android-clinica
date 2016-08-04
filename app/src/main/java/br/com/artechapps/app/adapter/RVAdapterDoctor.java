package br.com.artechapps.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Doctor;

/**
 * Created by acstapassoli on 07/12/2015.
 */
public class RVAdapterDoctor extends RecyclerView.Adapter<RVAdapterDoctor.ViewHolder> {

    private ArrayList<Doctor> mItemsData;

    public RVAdapterDoctor(ArrayList<Doctor> itemsData) {
        mItemsData = itemsData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_doctor, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Doctor model = mItemsData.get(position);
        viewHolder.tvCode.setText(String.valueOf(model.getCode()));
        viewHolder.tvName.setText(model.getName());

//        viewHolder.tvAdd.setText("R$ " + model.formatValue(model.getValue()));
//        viewHolder.lContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.getContext().startActivity(new Intent(v.getContext(), NewScheduleDoctorActivity.class));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return (mItemsData != null) ? mItemsData.size() : 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode;
        TextView tvName;
        LinearLayout lContent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvCode = (TextView) itemLayoutView.findViewById(R.id.code);
            tvName = (TextView) itemLayoutView.findViewById(R.id.name);
            lContent = (LinearLayout) itemLayoutView.findViewById(R.id.content);

        }
    }
}
