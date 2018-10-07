package com.example.root.box8logintask.fragment;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.box8logintask.OnEventClickListener;
import com.example.root.box8logintask.R;
import com.example.root.box8logintask.Utils;
import com.example.root.box8logintask.model.LoginUserModel;
import com.example.root.box8logintask.view.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginPagerFragment extends Fragment {
    @BindView(R.id.user_email)
    EditText etUserEmail;
    @BindView(R.id.icon_email)
    ImageView imgIconEmail;
    @BindView(R.id.user_phone)
    EditText etUserPhone;
    @BindView(R.id.icon_phone)
    ImageView imgIconPhone;
    @BindView(R.id.user_password)
    EditText etUserPassword;
    @BindView(R.id.heading)
    TextView tvHeading;
    private boolean isLoginByPhone;
    private LoginUserModel loggedInUSerModel = new LoginUserModel();
    private String userEmail, userPhone, userPassword;
    private OnEventClickListener mListener;

    public static LoginPagerFragment newInstance(boolean isLoginByPhone) {
        LoginPagerFragment fragment = new LoginPagerFragment();
        Bundle args = new Bundle();
        args.putBoolean("is_phone_login", isLoginByPhone);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnEventClickListener(OnEventClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_pager, container, false);
        ButterKnife.bind(this, view);
        assert getArguments() != null;
        isLoginByPhone = getArguments().getBoolean("is_phone_login");
        initView();
        return view;
    }

    private void initView() {
        etUserEmail.setVisibility(View.GONE);
        etUserPhone.setVisibility(View.GONE);
        if (isLoginByPhone) {
            tvHeading.setText(R.string.login_by_phone);
            imgIconPhone.setVisibility(View.VISIBLE);
            etUserPhone.setVisibility(View.VISIBLE);
        } else {
            tvHeading.setText(R.string.login_by_email);
            imgIconEmail.setVisibility(View.VISIBLE);
            etUserEmail.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.button_login)
    public void onLoginButtonClick(){
        if (isValidFields()) {
            if (isRegisterUser()) {
                mListener.onLoginEvent(loggedInUSerModel);
                MyToast.makeText(getContext(), "You are successfully logged in", Toast.LENGTH_LONG);
            } else {
                MyToast.makeText(getContext(), "Please Enter the correct details", Toast.LENGTH_SHORT);
            }
        }
    }

    @OnClick(R.id.tv_signup)
    public void onSignUpClick() {
        mListener.onEventClick();
    }

    private boolean isValidFields() {
        userEmail = etUserEmail.getText().toString();
        userPhone = etUserPhone.getText().toString();
        userPassword = etUserPassword.getText().toString();
        if (isLoginByPhone) {
            if (!Utils.isValidString(userPhone) && userPhone.length() != 10) {
                MyToast.makeText(getActivity(), "Please enter the valid phone number", Toast.LENGTH_SHORT);
                return false;
            }
        } else {
            if (!Utils.isValidEmail(userEmail)) {
                MyToast.makeText(getActivity(), "Please enter the valid email", Toast.LENGTH_SHORT);
                return false;
            }
        }

        if (!Utils.isValidString(userPassword) && userPassword.length() <= 6) {
            MyToast.makeText(getActivity(), "Password should be greater than 6 characters", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private boolean isRegisterUser() {
        List<LoginUserModel> registerUserLists = Utils.getSavedLoginUserLists(getContext());
        if (registerUserLists != null && registerUserLists.size() > 0) {
            int size = registerUserLists.size();
            boolean isPhoneValid = false, isPasswordValid = false, isEmailValid = false;
            if (isLoginByPhone) {
                for (int i = 0; i < size; i++) {
                    if (userPhone.equals(registerUserLists.get(i).getPhone())) {
                        isPhoneValid = true;
                    }
                    if (isPhoneValid) {
                        if (userPassword.equals(registerUserLists.get(i).getPassword())) {
                            setLoggedInUserModel(registerUserLists.get(i));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            } else {
                for (int j = 0; j < size; j++) {
                    if (userEmail.equals(registerUserLists.get(j).getEmail())) {
                        isEmailValid = true;
                    }

                    if (isEmailValid) {
                        if (userPassword.equals(registerUserLists.get(j).getPassword())) {
                            setLoggedInUserModel(registerUserLists.get(j));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
            }
        } else {
            return false;
        }
    }

    private void setLoggedInUserModel (LoginUserModel model) {
        loggedInUSerModel.setPassword(model.getPassword());
        loggedInUSerModel.setName(model.getName());
        loggedInUSerModel.setEmail(model.getEmail());
        loggedInUSerModel.setPhone(model.getPhone());
    }


}
