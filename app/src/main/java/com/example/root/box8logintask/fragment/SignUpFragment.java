package com.example.root.box8logintask.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.box8logintask.R;
import com.example.root.box8logintask.Utils;
import com.example.root.box8logintask.activity.LoginUserDetailsActivity;
import com.example.root.box8logintask.model.LoginUserModel;
import com.example.root.box8logintask.view.MyToast;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.root.box8logintask.Utils.getSavedLoginUserLists;
import static com.example.root.box8logintask.Utils.saveDataIntoFile;

public class SignUpFragment extends Fragment {
    @BindView(R.id.user_name)
    EditText etUserName;
    @BindView(R.id.user_email)
    EditText etUserEmail;
    @BindView(R.id.user_phone)
    EditText etUserPhone;
    @BindView(R.id.user_password)
    EditText etUserPassword;
    @BindView(R.id.button_sign_up)
    Button btnSignUp;
    private List<LoginUserModel> loginUserLists;
    private String userName, userEmail, userPhone, userPassword;


    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.button_sign_up)
    public void onDoneButtonClick() {
        if (isValidFields()) {
            LoginUserModel model = new LoginUserModel(); //setting the data in model
            model.setName(userName);
            model.setEmail(userEmail);
            model.setPhone(userPhone);
            model.setPassword(userPassword);
            saveDataInFile(model);
        }
    }

    @OnClick(R.id.tv_login)
    public void navigateToLoginPage() {
        Utils.nextFragmentWithLeftRightAnim(getFragmentManager(), LoginFragment.newInstance(), R.id.main_container);
    }

    private void saveDataInFile(LoginUserModel model) {
        loginUserLists = getSavedLoginUserLists(getContext());
        if(loginUserLists==null) {
            loginUserLists = new ArrayList<>();
        }
        loginUserLists.add(model);
        if (isRegisterUser(model.getPhone())) {
            MyToast.makeText(getContext(), "Seems you are already registered. Please Login.", Toast.LENGTH_LONG);
        } else {
            saveDataIntoFile(getContext(), loginUserLists);
            navigateToLoginUserDetailsActivity(model);
        }
    }

    private void navigateToLoginUserDetailsActivity(LoginUserModel model) {
        if (getFragmentManager() != null && getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Intent intent = new Intent(getActivity(), LoginUserDetailsActivity.class);
        intent.putExtra("model", new Gson().toJson(model));
        startActivity(intent);
        getActivity().finish();
    }


    private boolean isValidFields() { // for validation the edittext fields
        userName = etUserName.getText().toString();
        userEmail = etUserEmail.getText().toString();
        userPhone = etUserPhone.getText().toString();
        userPassword = etUserPassword.getText().toString();
        if (!Utils.isValidString(userName) && userName.length() <= 3) {
            MyToast.makeText(getActivity(), "Name should be greater than 3 characters", Toast.LENGTH_SHORT);
            return false;
        }

        if (!Utils.isValidEmail(userEmail)) {
            MyToast.makeText(getActivity(), "Please enter the valid email", Toast.LENGTH_SHORT);
            return false;
        }

        if (!Utils.isValidString(userPhone) && userPhone.length() != 10) {
            MyToast.makeText(getActivity(), "Please enter the valid phone number", Toast.LENGTH_SHORT);
            return false;
        }

        if (!Utils.isValidString(userPassword) && userPassword.length() <= 6) {
            MyToast.makeText(getActivity(), "Password should be greater than 6 characters", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private boolean isRegisterUser(String userPhone) {
        List<LoginUserModel> registerUserLists = Utils.getSavedLoginUserLists(getContext());
        if (registerUserLists != null && registerUserLists.size() > 0) {
            int size = registerUserLists.size();
                for (int i = 0; i < size; i++) {
                    if (userPhone.equals(registerUserLists.get(i).getPhone())) {
                        return true;
                    }
                }
                return false;
        } else {
            return false;
        }
    }
    
}
