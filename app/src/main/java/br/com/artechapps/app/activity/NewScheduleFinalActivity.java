package br.com.artechapps.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Appointment;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.model.Schedule;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskCancelAppointment;
import br.com.artechapps.app.task.AsyncTaskNewAppointment;
import br.com.artechapps.app.utils.SessionManager;

public class NewScheduleFinalActivity extends AppCompatActivity {

    private Product mModel;
    private Schedule mSchedule;

    private TextView mTvProcedureName;
    private TextView mTvDoctorName;
    private TextView mTvDateTime;
    private TextView mTvStatus;
    private RelativeLayout mLineDoctor;
    private Button mBtnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule_final);

        try {
            mModel = (Product) getIntent().getSerializableExtra("model");
            mSchedule = (Schedule) getIntent().getSerializableExtra("schedule");
        } catch (Exception e){
            Log.e("NOMODEL", "NO PARAMETER MODEL");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes do agendamento");

        mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        mTvProcedureName = (TextView) findViewById(R.id.tv_procedure_name);
        mTvDateTime = (TextView) findViewById(R.id.tv_date_time);
        mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        mLineDoctor = (RelativeLayout) findViewById(R.id.line_2);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mTvStatus = (TextView) findViewById(R.id.tv_status);

        mTvProcedureName.setText(mModel.getDescription());

        if (mModel.getDoctor() != null) {
            mLineDoctor.setVisibility(View.VISIBLE);
            mTvDoctorName.setText(mModel.getDoctor().getName());
        }

        String dateFormat = "";
        if (mModel.getDate() != null) {
            dateFormat = mModel.getDate().replace("-", "/");
        }

        if (mModel.getTime() != null) {
            mTvDateTime.setText(dateFormat + "  " + mModel.getTime());
        } else {
            // From Detail, user can cancel the appointment
            mTvDateTime.setText(mModel.getDateTimeFormatted());
            mBtnConfirm.setText("Cancelar");
        }

        SessionManager sm = new SessionManager(this);
        final User user = sm.getSessionUser();


        if (mSchedule != null){
            if (mSchedule.getStatus().equalsIgnoreCase("CANCELADO")){
                mBtnConfirm.setVisibility(View.GONE);
            } else {
                mBtnConfirm.setVisibility(View.VISIBLE);
            }
            mTvStatus.setText(mSchedule.getStatus());
        }

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mModel.getDateTimeFormatted() != null) { //CANCEL
//                    http://www2.beautyclinic.com.br/clinwebservice2/servidor/cancelaragendamento/
                    String json = "{\"codAgenda\":"+ mModel.getCodeSchedule() +"}";
                    new AsyncTaskCancelAppointment("Cancelando agendamento...", NewScheduleFinalActivity.this, false, NewScheduleFinalActivity.this).execute(json, String.valueOf(mModel.getCodeSchedule()));

                } else {// CONFIRM
                    Appointment model = new Appointment();
                    model.setCodCliente(user.getCode());
                    model.setCodFilial(user.getCodFilial());
                    model.setCodProcedimento(mModel.getId());
                    model.setData(mModel.getDate());
                    model.setHorario(mModel.getTime());

                    new AsyncTaskNewAppointment("Salvando agendamento...", v.getContext(), true, model, NewScheduleFinalActivity.this).execute();
                }
            }
        });
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
