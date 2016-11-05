package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import br.com.artechapps.app.R;
import br.com.artechapps.app.adapter.RVAdapterCart;
import br.com.artechapps.app.database.PersistenceShop;
import br.com.artechapps.app.model.BudgetConfirm;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.model.Shop;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskConfirmCart;
import br.com.artechapps.app.utils.SessionManager;
import br.com.artechapps.app.utils.UtilsDate;

public class CartActivity extends AppCompatActivity {

    private RecyclerView mRvCart;
    private PersistenceShop mPersistence;
    private ArrayList<Shop> mList;
    private Button mButton;
    private TextView mTvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButton = (Button) findViewById(R.id.btn_confirm);
        mRvCart = (RecyclerView) findViewById(R.id.rv_cart_detail);
        mTvTotal = (TextView) findViewById(R.id.total_value);
        mPersistence = new PersistenceShop(this);
        mList = mPersistence.getRecords();

        RVAdapterCart mAdapter = new RVAdapterCart(mList, this);
        mAdapter.notifyDataSetChanged();

        mRvCart.setLayoutManager(new LinearLayoutManager(this));
        mRvCart.setItemAnimator(new DefaultItemAnimator());
        mRvCart.setAdapter(mAdapter);

        updateTotalValue();

        SessionManager sm = new SessionManager(this);
        final User user = sm.getSessionUser();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTotalValue();

                double value = format(mTvTotal.getText().toString());
                if (value > 0){
                    BudgetConfirm budgetConfirm = new BudgetConfirm();
                    budgetConfirm.setCodCliente(user.getCode());
                    budgetConfirm.setValorOrcamento(value);
                    budgetConfirm.setValorTotal(value);
                    budgetConfirm.setDataOrcamento(UtilsDate.getCurrentDate());

                    new AsyncTaskConfirmCart("Gerando orçamento...",v.getContext(),true, budgetConfirm).execute();
                } else {
                    Toast.makeText(CartActivity.this, "Nenhum produto no carrinho.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void updateTotalValue(){

        PersistenceShop persistence = new PersistenceShop(this);
        ArrayList<Shop> list = persistence.getRecords();
        persistence.close();
        double totalValue = 0d;
        for (Shop model : list){
            totalValue += (model.getProduct().getValue() * model.getAmount()) ;
        }
        mTvTotal.setText(String.valueOf(Product.formatValue(totalValue)));
    }

    public static double format(String value) {
        String formated = value.replace(".","").replace(",",".");
        return Double.parseDouble(formated);
    }


}
