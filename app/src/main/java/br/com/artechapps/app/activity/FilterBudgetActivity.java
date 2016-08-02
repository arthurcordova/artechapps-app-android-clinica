package br.com.artechapps.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import br.com.artechapps.app.R;

public class FilterBudgetActivity extends AppCompatActivity {

    private Switch mSwAll;
    private Switch mSwConfirmed;
    private Switch mSwCancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_budget);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filtrar orçamentos");

        mSwAll = (Switch) findViewById(R.id.sw_all);
        mSwConfirmed = (Switch) findViewById(R.id.sw_confirmed);
        mSwCancelled = (Switch) findViewById(R.id.sw_cancelled);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_budget_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_ok:

                Intent result = new Intent();
                result.putExtra("sw_all", mSwAll.isChecked());
                result.putExtra("sw_confirmed", mSwConfirmed.isChecked());
                result.putExtra("sw_cancelled", mSwCancelled.isChecked());
                setResult(RESULT_OK, result);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
