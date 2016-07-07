package br.com.artechapps.app.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.artechapps.app.R;

/**
 * A login screen that offers login via cpf/password.
 */
public class LoginActivity extends AppCompatActivity {

//    private UserLoginTask mAuthTask = null;
    private String a;
    private int mKeyDel;
    private boolean mCPFValidated = false;
    private boolean mPasswordValidated = false;

    private EditText mCPF;
    private EditText mPassword;
    private Button mSignIn;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCPF = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mSignIn = (Button) findViewById(R.id.sign_in_button);

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, NewUserActivity.class));
            }
        });

        mCPF.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean flag = true;
                String eachBlock[] = mCPF.getText().toString().split(".");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 3) {
                        flag = false;
                    }
                }
                if (flag) {

                    mCPF.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                mKeyDel = 1;
                            return false;
                        }
                    });

                    if (mKeyDel == 0) {

                        if (((mCPF.getText().length() + 1) % 4) == 0) {

                            if (mCPF.getText().toString().split(".").length < 3) {
                                if(mCPF.getText().length() == 11){
                                    mCPF.setText(mCPF.getText() + "-");
                                } else {
                                    mCPF.setText(mCPF.getText() + ".");
                                }
                                mCPF.setSelection(mCPF.getText().length());
                            }
                        }
                        a = mCPF.getText().toString();
                    } else {
                        a = mCPF.getText().toString();
                        mKeyDel = 0;
                    }

                } else {
                    mCPF.setText(a);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mCPF.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String cpf = mCPF.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid cpf address.
        if (TextUtils.isEmpty(cpf)) {
            mCPF.setError(getString(R.string.error_field_required));
            focusView = mCPF;
            cancel = true;
        } else if (!isEmailValid(cpf)) {
            mCPF.setError(getString(R.string.error_invalid_cpf));
            focusView = mCPF;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            //execute
        }
    }

    private void enableButton(){
        mSignIn.setAlpha(1);
        mSignIn.setClickable(true);
    }

    private void disableButton(){
        mSignIn.setAlpha(0.12F);
        mSignIn.setClickable(false);
    }

    private boolean isEmailValid(String cpf) {
        //TODO: Replace this with your own logic
        return cpf.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

