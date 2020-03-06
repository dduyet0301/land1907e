package com.t3h.chatt3h.register;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.t3h.basemodule.base.ActivityBase;
import com.t3h.chatt3h.R;
import com.t3h.chatt3h.databinding.ActivityRegisterBinding;
import com.t3h.chatt3h.model.Event;

public class RegisterActivity extends ActivityBase<ActivityRegisterBinding> implements RegisterItemListener, Observer<Event> {
    public static final String EXTRA_EMAIL = "extra.email";
    public static final String EXTRA_PASSWORD = "extra.password";

    private RegisterViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        binding.setListener(this);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        viewModel.getEvent().observe(this, this);
    }

    @Override
    public void onRegisterClick() {
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        String name = binding.edtName.getText().toString();
        viewModel.register(email,password,name);
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_EMAIL, email);
//        intent.putExtra(EXTRA_PASSWORD, password);
//        setResult(RESULT_OK, intent);
//        finish();
    }

    @Override
    public void onChanged(Event event) {
        if (event.isLoading()){
            //show progress dialog
            return;
        }
        //dismiss dialog
        if (event.isSuccess()){
            finish();
            return;
        }
        if (event.isFail()){
            Toast.makeText(this, "Register Fail", Toast.LENGTH_LONG).show();
        }
    }
}
