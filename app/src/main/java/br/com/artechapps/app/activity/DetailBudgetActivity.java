package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Budget;
import br.com.artechapps.app.model.Product;

public class DetailBudgetActivity extends AppCompatActivity {

    private Budget mModel;
    private TextView tvValue;
    private TextView tvValueDiscount;
    private TextView tvValueTotal;
    private TextView tvDateSent;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_budget);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mModel = (Budget) getIntent().getSerializableExtra("model");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orçamento: " + mModel.getCode());

        tvValue = (TextView) findViewById(R.id.tv_value);
        tvValueDiscount = (TextView) findViewById(R.id.tv_value_discount);
        tvValueTotal = (TextView) findViewById(R.id.tv_value_total);
        tvDateSent = (TextView) findViewById(R.id.tv_date_sent);
        tvStatus = (TextView) findViewById(R.id.tv_status);

        tvValue.setText("R$ " +Product.formatValue(mModel.getValueBudget()));
        tvValueDiscount.setText("R$ " +Product.formatValue(mModel.getValueDiscount()));
        tvValueTotal.setText("R$ " +Product.formatValue(mModel.getValueTotal()));
        tvDateSent.setText(mModel.getDate());
        tvStatus.setText(mModel.getStatus());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
