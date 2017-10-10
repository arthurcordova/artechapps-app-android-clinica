package br.com.artechapps.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.artechapps.app.R;
import br.com.artechapps.app.task.AsyncTaskNewUser;
import br.com.artechapps.app.utils.UtilsCPF;

public class NewUserActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mEtName;
    private EditText mEtCpf;
    private EditText mEtPassword;
    private EditText mEtPasswordConfirm;
    private EditText mEtEmail;

    private String a;
    private int mKeyDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButton = (Button) findViewById(R.id.new_user_button);
        mEtName = (EditText) findViewById(R.id.new_name);
        mEtEmail = (EditText) findViewById(R.id.new_email);
        mEtCpf = (EditText) findViewById(R.id.new_cpf);
        mEtPassword = (EditText) findViewById(R.id.new_password);
        mEtPasswordConfirm = (EditText) findViewById(R.id.new_password_confirm);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin()) {
                    new AsyncTaskNewUser("Criando novo usuário...", NewUserActivity.this, true).execute(
                            mEtName.getText().toString(),
                            mEtCpf.getText().toString(),
                            mEtPassword.getText().toString(),
                            mEtEmail.getText().toString()
                    );
                }
            }
        });

        mEtCpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                boolean flag = true;
                String eachBlock[] = mEtCpf.getText().toString().split(".");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 3) {
                        flag = false;
                    }
                }
                if (flag) {

                    mEtCpf.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                mKeyDel = 1;
                            return false;
                        }
                    });

                    if (mKeyDel == 0) {

                        if (((mEtCpf.getText().length() + 1) % 4) == 0) {

                            if (mEtCpf.getText().toString().split(".").length < 3) {
                                if (mEtCpf.getText().length() == 11) {
                                    mEtCpf.setText(mEtCpf.getText() + "-");
                                } else {
                                    mEtCpf.setText(mEtCpf.getText() + ".");
                                }
                                mEtCpf.setSelection(mEtCpf.getText().length());
                            }
                        }
                        a = mEtCpf.getText().toString();
                    } else {
                        a = mEtCpf.getText().toString();
                        mKeyDel = 0;
                    }

                } else {
                    mEtCpf.setText(a);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean attemptLogin() {
        mEtCpf.setError(null);
        mEtPassword.setError(null);
        mEtPasswordConfirm.setError(null);
        mEtName.setError(null);
        mEtEmail.setError(null);

        // Store values at the time of the login attempt.
        String cpf = mEtCpf.getText().toString();
        String password = mEtPassword.getText().toString();
        String passwordConfirmed = mEtPasswordConfirm.getText().toString();
        String name = mEtName.getText().toString();
        String email = mEtEmail.getText().toString();

        boolean forward = false;
        View focusView = null;


        if (TextUtils.isEmpty(name)) {
            mEtName.setError(getString(R.string.error_field_required));
            focusView = mEtName;

        } else if (!isNameValid(name)) {
            mEtName.setError(getString(R.string.error_invalid_name));
            focusView = mEtName;

        } else if (TextUtils.isEmpty(cpf)) {
            mEtCpf.setError(getString(R.string.error_field_required));
            focusView = mEtCpf;

        } else if (!isCPFValid(cpf)) {
            mEtCpf.setError(getString(R.string.error_invalid_cpf));
            focusView = mEtCpf;

        } else if (TextUtils.isEmpty(email)) {
            mEtCpf.setError(getString(R.string.error_field_required));
            focusView = mEtCpf;

        } else if (!isEmailValid(email)) {
            mEtEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEtEmail;

        } else if (TextUtils.isEmpty(password)) {
            mEtPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEtPassword;

        } else if (!isPasswordValid(password)) {
            mEtPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEtPassword;

        } else if (TextUtils.isEmpty(passwordConfirmed)) {
            mEtPasswordConfirm.setError(getString(R.string.error_field_required));
            focusView = mEtPasswordConfirm;

        } else if (!isPasswordConfirmed(password, passwordConfirmed)) {
            mEtPasswordConfirm.setError(getString(R.string.error_invalid_password));
            focusView = mEtPasswordConfirm;


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

    private boolean isPasswordConfirmed(String password, String passwordConfirmed) {
        return password.equals(passwordConfirmed);
    }

    private boolean isNameValid(String name) {
        return name.length() > 3;
    }

    private boolean isEmailValid(String email) {
        Pattern p = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        Matcher m = p.matcher(email);
        return m.find();
    }


}
