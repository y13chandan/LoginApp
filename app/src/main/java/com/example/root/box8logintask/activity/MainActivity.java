package com.example.root.box8logintask.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.root.box8logintask.R;
import com.example.root.box8logintask.fragment.LoginFragment;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigateToLoginFragment();
    }

    private void navigateToLoginFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        ft.add(R.id.main_container, loginFragment);
        ft.addToBackStack(loginFragment.toString());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (fm != null) {
            int count = fm.getBackStackEntryCount();
            if (count != 1) {
                fm.popBackStack();
            } else {
                finish();
            }
        }
    }
}
