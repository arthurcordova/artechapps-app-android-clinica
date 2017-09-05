package br.com.artechapps.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import br.com.artechapps.app.R;
import br.com.artechapps.app.model.Appointment;
import br.com.artechapps.app.model.Product;
import br.com.artechapps.app.model.Schedule;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskCancelAppointment;
import br.com.artechapps.app.task.AsyncTaskNewAppointment;
import br.com.artechapps.app.utils.DialogQRCode;
import br.com.artechapps.app.utils.EndPoints;
import br.com.artechapps.app.utils.InputStreamVolleyRequest;
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
    private View mQrCode;
    private ImageView mImageQrcode;

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
        mQrCode = findViewById(R.id.button_qrcode);
        mImageQrcode = (ImageView) findViewById(R.id.image_qrcode);

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

        mQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUrl= EndPoints.QRCODE.concat(String.valueOf(mModel.getCodeSchedule()));

                InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                        new Response.Listener<byte[]>() {
                            @Override
                            public void onResponse(byte[] response) {
                                // TODO handle the response
                                try {
                                    if (response!=null) {


                                        File file = new File(Environment.getExternalStorageDirectory() + "/qrcode.png");
                                        FileOutputStream fOut = new FileOutputStream(file);
//                                        fOut = openFileOutput(name, Context.MODE_PRIVATE);
                                        fOut.write(response);
                                        fOut.close();

                                        Bitmap image = BitmapFactory.decodeStream( new FileInputStream( file ) );

                                        fOut.close();

//                                        mImageQrcode.setImageBitmap(image);

                                        DialogQRCode dialog =new DialogQRCode(NewScheduleFinalActivity.this, image);
                                        dialog.show();

                                        Toast.makeText(NewScheduleFinalActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                                    e.printStackTrace();
                                }
                            }
                        } ,new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO handle the error
                        error.printStackTrace();
                    }
                }, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
                mRequestQueue.add(request);
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
