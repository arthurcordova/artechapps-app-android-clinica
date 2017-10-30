package br.com.artechapps.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.task.AsyncTaskLogin;
import br.com.artechapps.app.utils.CPFMask;
import br.com.artechapps.app.utils.Constants;
import br.com.artechapps.app.utils.UtilsCPF;

/**
 * A login screen that offers login via cpf/password.
 */
public class LoginActivity extends AppCompatActivity {

    private String a;
    private int mKeyDel;
    private final String USER_LOGIN = "user_login";

    private EditText mCPF;
    private EditText mPassword;
    private Button mSignIn;
    private Button mFab;
    private SharedPreferences mSharedPreferences;
    private View mTvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCPF = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mFab = (Button) findViewById(R.id.fab);
        mSignIn = (Button) findViewById(R.id.sign_in_button);
        mTvForgot = findViewById(R.id.tv_forgot_password);

        mSharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        String user = mSharedPreferences.getString(USER_LOGIN, null);

        mTvForgot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

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

        if (user!=null && !user.equals("")){
            mCPF.setText(user);
            mPassword.requestFocus();
        }

        mCPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(USER_LOGIN, mCPF.getText().toString());
                    editor.commit();
                }
            }
        });


        mCPF.addTextChangedListener(CPFMask.insert(mCPF));

//        mCPF.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                boolean flag = true;
//                String eachBlock[] = mCPF.getText().toString().split(".");
//                for (int i = 0; i < eachBlock.length; i++) {
//                    if (eachBlock[i].length() > 3) {
//                        flag = false;
//                    }
//                }
//                if (flag) {
//
//                    mCPF.setOnKeyListener(new View.OnKeyListener() {
//
//                        @Override
//                        public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                            if (keyCode == KeyEvent.KEYCODE_DEL)
//                                mKeyDel = 1;
//                            return false;
//                        }
//                    });
//
//                    if (mKeyDel == 0) {
//
//                        if (((mCPF.getText().length() + 1) % 4) == 0) {
//
//                            if (mCPF.getText().toString().split(".").length < 3) {
//                                if(mCPF.getText().length() == 11){
//                                    mCPF.setText(mCPF.getText() + "-");
//                                } else {
//                                    mCPF.setText(mCPF.getText() + ".");
//                                }
//                                mCPF.setSelection(mCPF.getText().length());
//                            }
//                        }
//                        a = mCPF.getText().toString();
//                    } else {
//                        a = mCPF.getText().toString();
//                        mKeyDel = 0;
//                    }
//
//                } else {
//                    mCPF.setText(a);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        mSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin())
                    Constants.IS_DASHBOARD = true;
                    new AsyncTaskLogin(getString(R.string.hint_execute_login),LoginActivity.this, true).execute(mCPF.getText().toString(), mPassword.getText().toString());
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean attemptLogin() {
        mCPF.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String cpf = mCPF.getText().toString();
        String password = mPassword.getText().toString();

        boolean forward = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
        } else if (!isPasswordValid(password)){
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
        } else if (TextUtils.isEmpty(cpf)) {
            mCPF.setError(getString(R.string.error_field_required));
            focusView = mCPF;
        } else if (!isCPFValid(cpf)) {
            mCPF.setError(getString(R.string.error_invalid_cpf));
            focusView = mCPF;
        } else {
            forward = true;
        }
        if (!forward) {
            focusView.requestFocus();
        }
        return forward;
    }

    private boolean isCPFValid(String cpf) {
        return UtilsCPF.validateCPF(cpf);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

}

