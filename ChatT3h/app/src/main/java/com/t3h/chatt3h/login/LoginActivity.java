package com.t3h.chatt3h.login;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.t3h.basemodule.base.ActivityBase;
import com.t3h.chatt3h.MainActivity;
import com.t3h.chatt3h.R;
import com.t3h.chatt3h.databinding.ActivityLoginBinding;
import com.t3h.chatt3h.register.RegisterActivity;

public class LoginActivity extends ActivityBase<ActivityLoginBinding> implements LoginItemListener {

    private static final int REQUEST_CODE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
       binding.setListener(this);
    }

    @Override
    public void onLoginClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onRegisterClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(RegisterActivity.EXTRA_EMAIL);
                String password = data.getStringExtra(RegisterActivity.EXTRA_PASSWORD);
                binding.edtEmail.setText(email);
                binding.edtPassword.setText(password);
            } else {
                Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
