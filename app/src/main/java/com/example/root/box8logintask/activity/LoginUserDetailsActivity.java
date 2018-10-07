package com.example.root.box8logintask.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.root.box8logintask.R;
import com.example.root.box8logintask.fragment.LoginFragment;
import com.example.root.box8logintask.model.LoginUserModel;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginUserDetailsActivity extends AppCompatActivity {
    @BindView(R.id.user_name)
    TextView tvUserName;
    @BindView(R.id.user_email)
    TextView tvUserEmail;
    @BindView(R.id.user_phone)
    TextView tvUserPhone;
    @BindView(R.id.user_password)
    TextView tvUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (getIntent().getStringExtra("model") != null) {
            Gson gson = new Gson();
            LoginUserModel model = gson.fromJson(getIntent().getStringExtra("model"), LoginUserModel.class);
            if (model != null) {
                tvUserName.setText(model.getName());
                tvUserEmail.setText(model.getEmail());
                tvUserPhone.setText(model.getPhone());
                tvUserPassword.setText(model.getPassword());
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
