package br.com.artechapps.app.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Message;

public class DetailMessageActivity extends AppCompatActivity {

    private Message mModel;
    private TextView tvMsgTitle;
    private TextView tvMsgContent;
    private TextView tvDateSent;
    private TextView tvDateSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mModel = (Message) getIntent().getSerializableExtra("model");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mensagem");

        tvMsgTitle = (TextView) findViewById(R.id.tv_msg_title);
        tvMsgContent = (TextView) findViewById(R.id.tv_msg_content);
        tvDateSent = (TextView) findViewById(R.id.tv_date_sent);
        tvDateSee = (TextView) findViewById(R.id.tv_date_see);

        tvMsgTitle.setText(mModel.getTitle());
        tvMsgContent.setText(mModel.getMessage());
        tvDateSent.setText("Enviada em " + mModel.getSentDate());
        tvDateSee.setText(mModel.getSeeDate() != null ? "Visualizada em  " + mModel.getSeeDate() : "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Deseja excluir a mensagem?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // FIRE ZE MISSILES!
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail_message, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
