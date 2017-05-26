package com.sample.shopapp.controllers.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.shopapp.Jiny.BusEvents;
import com.sample.shopapp.Jiny.PointerService;
import com.sample.shopapp.Jiny.UIViewsHandler;
import com.sample.shopapp.R;
import com.sample.shopapp.api.ApiClient;
import com.sample.shopapp.api.ErrorUtils;
import com.sample.shopapp.api.models.ErrorResponse;
import com.sample.shopapp.controllers.Home;
import com.sample.shopapp.events.SignupSuccessfulEvent;
import com.sample.shopapp.models.User;
import com.sample.shopapp.utils.BusProvider;
import com.sample.shopapp.utils.Common;
import com.sample.shopapp.utils.Log;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by vaibhav on 12/2/15.
 */
public class SignupFragment extends BaseFragment implements View.OnTouchListener {
    private static final String TAG = "SIGNUP";
    private Home home;
    private ImageView back, showPwd;
    private EditText email, name, password, phone;
    private TextView signup;
    private User user;
    private RelativeLayout pbContainer;
    private boolean isShown = false;

    View rootView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignupFragment.
     */
    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public String getTagText() {
        return TAG;
    }

    @Override
    public boolean onBackPressed() {
        Log.d("SignupFragment : onBackPressed");
        home.popBackStack();
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        home = (Home) getActivity();
        user = new User();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        rootView.setOnTouchListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Layout has happened here.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rootView != null)
                    UIViewsHandler.handleSignUpPageViews(getActivity(), rootView);
            }
        }, 500);
    }

    private void initUI(View view) {
        back     = (ImageView) view.findViewById(R.id.fragment_signup_tab_bar_back_img);
        email    = (EditText)  view.findViewById(R.id.fragment_signup_email_txt);
        password = (EditText)  view.findViewById(R.id.fragment_signup_password_txt);
        name     = (EditText)  view.findViewById(R.id.fragment_signup_name_txt);
        phone    = (EditText)  view.findViewById(R.id.fragment_signup_number_txt);
        signup   = (TextView)  view.findViewById(R.id.fragment_signup_btn);
        showPwd  = (ImageView) view.findViewById(R.id.fragment_signup_show_password_img);
        pbContainer = (RelativeLayout) view.findViewById(R.id.progress_bar_container);
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);


    }

    private void setListeners() {
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // change focus to next view
                    UIViewsHandler.handlePageViewFocusChanges(getActivity(), name);
                }
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // change focus to next view
                    UIViewsHandler.handlePageViewFocusChanges(getActivity(), password);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // change focus to next view
                    UIViewsHandler.handlePageViewFocusChanges(getActivity(), phone);
                }
            }
        });
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // change focus to next view
                    UIViewsHandler.handlePageViewFocusChanges(getActivity(), signup);
                }
            }
        });
        showPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShown) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isShown = true;
                }
                else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    isShown = false;
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) signupUser();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean validateForm() {
        if (TextUtils.isEmpty(email.getText()) || !Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError("Invalid Email");
            return false;
        }
        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Password cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(password.getText())) {
            password.setError("Password cannot be empty");
            return false;
        }
        return true;
    }

    private void signupUser() {
        Common.hideKeyboard(home);
        pbContainer.setVisibility(View.VISIBLE);
        user = new User(email.getText().toString(), password.getText().toString(), name.getText().toString());
        Call<User> call = ApiClient.getInstance().getApiService().signup(user);
        call.enqueue(new retrofit.Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                pbContainer.setVisibility(View.GONE);
                if (response.isSuccess()) {
                    Log.d("User signed up");
                    user = response.body();
                    BusProvider.getInstance().post(new SignupSuccessfulEvent(user));
                }
                else {
                    Log.d("User not signed up");
                    ErrorResponse errorResponse = ErrorUtils.parseError(response, retrofit);
                    if (errorResponse.getErrors().getEmail() != null && errorResponse.getErrors().getEmail().get(0).equalsIgnoreCase("has already been taken")) {
                        handleError("Email already registered");
                    }
                    if (errorResponse.getErrors().getPassword() != null && errorResponse.getErrors().getPassword().get(0).equalsIgnoreCase("is too short (minimum is 6 characters)")) {
                        handleError("Password is too short (minimum is 6 characters)");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("User signed up failure");
                handleError("User sign up failure. Pls try again");
            }
        });
    }

    private void handleError(String msg) {
        Toast.makeText(home, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PointerService.bus.post(new BusEvents.HideEvent());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
//        View view1 = AppUtils.findViewAtPosition(rootView,(int) motionEvent.getX(), (int) motionEvent.getY());
        return false;
    }
}
