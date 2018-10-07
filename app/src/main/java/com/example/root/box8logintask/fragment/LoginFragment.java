package com.example.root.box8logintask.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.root.box8logintask.OnEventClickListener;
import com.example.root.box8logintask.R;
import com.example.root.box8logintask.Utils;
import com.example.root.box8logintask.activity.LoginUserDetailsActivity;
import com.example.root.box8logintask.adapter.ViewPagerAdapter;
import com.example.root.box8logintask.model.LoginUserModel;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment implements OnEventClickListener, RadioGroup.OnCheckedChangeListener
, ViewPager.OnPageChangeListener{
    @BindView(R.id.view_pager_login)
    ViewPager mLoginPager;
    @BindView(R.id.page_radio_group)
    RadioGroup mPageRadioGroup;
    private ViewPagerAdapter mAdapter;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        setupViewPager();
        mPageRadioGroup.setOnCheckedChangeListener(this);
    }

    private void initialiseListener() {
        LoginPagerFragment fragment = (LoginPagerFragment) mAdapter.getItem(0);
        if (fragment != null) {
            fragment.setOnEventClickListener(this);
        }

        LoginPagerFragment fragmentOne = (LoginPagerFragment) mAdapter.getItem(1);
        if (fragmentOne != null) {
            fragmentOne.setOnEventClickListener(this);
        }
    }

    private void setupViewPager() {
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(LoginPagerFragment.newInstance(true));
        mAdapter.addFragment(LoginPagerFragment.newInstance(false));
        mLoginPager.setAdapter(mAdapter);
        mLoginPager.setOnPageChangeListener(this);
        initialiseListener();
    }

    @Override
    public void onEventClick() {
        if (getFragmentManager() != null && getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        Utils.nextFragmentWithLeftRightAnim(getFragmentManager(), SignUpFragment.newInstance(), R.id.main_container);
    }

    @Override
    public void onLoginEvent(LoginUserModel model) {
        Intent intent = new Intent(getActivity(), LoginUserDetailsActivity.class);
        intent.putExtra("model", new Gson().toJson(model));
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) { // on clicking the radio button it will set the respective page
        RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(i);
        int index = radioGroup.indexOfChild(checkedRadioButton);
        mLoginPager.setCurrentItem(index);
    }

    @Override
    public void onPageSelected(int position) {
        int radioButtonId = mPageRadioGroup.getChildAt(position).getId(); // when current page change -> update radio button state
        mPageRadioGroup.check(radioButtonId);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
