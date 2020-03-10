package com.software.acuity.splick.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SignUpActivity;
import com.software.acuity.splick.interfaces.IChangeViewPagerItem;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SocialReachFragment extends Fragment {


    // INTERFACE
    IChangeViewPagerItem iChangeViewPagerItem;

    public static CallbackManager callbackManager;

    // PERMISSIONS
    private static final String EMAIL = "email";
    private static final String INSTAGRAM_BASIC = "email, public_profile, instagram_graph_user_profile, instagram_graph_user_media";
    private static final String INSTAGRAM_CONTENT_PUBLISH = "instagram_content_publish";

    // VIEWS
    @BindView(R.id.btnSocialNext)
    Button btnSocialNext;

    @BindView(R.id.login_button)
    LoginButton loginButton;

    //UTILS
    SharedPreferenceClass sharedPreferenceClass;

    public SocialReachFragment(Context context, SignUpActivity activity) {
        iChangeViewPagerItem = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_social_reach, container, false);

        //Butter-knife initialization
        ButterKnife.bind(this, fragmentView);

        //FACEBOOK Integration Initialization
        facebookInitialization();

        //UTILS INITIALIZATIONS
        sharedPreferenceClass = SharedPreferenceClass.getInstance(getActivity());

        btnSocialNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChangeViewPagerItem.changeViewPagerItem("portfolio");
            }
        });

        return fragmentView;
    }

    private void facebookInitialization() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList());
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("facebook_login_result", loginResult.getAccessToken().toString());
                accessProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSkipTagFragment:
                iChangeViewPagerItem.changeViewPagerItem("portfolio");
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tags_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void accessProfile(AccessToken accessToken) {
        //Node Request
        GraphRequest request = GraphRequest.newGraphPathRequest(accessToken, "/me", new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.d("fb_res", response.toString() + "");
                try {
                    JSONObject profileObject = new JSONObject(response.toString());
                    sharedPreferenceClass.setValues(Constants.FB_PROFILE_KEY, profileObject.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            accessProfile(AccessToken.getCurrentAccessToken());
        }
    }
}

//        loginButton.setLoginBehavior(LoginBehavior.WEB_ONLY);
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//        @Override
//        public void onSuccess(LoginResult loginResult) {
//            // App code
//            Log.e("fb_login", loginResult.getAccessToken() + "");
//        }
//
//        @Override
//        public void onCancel() {
//            // App code
//        }
//
//        @Override
//        public void onError(FacebookException exception) {
//            // App code
//        }
//    });
//
////        loginButton.setPermissions(Arrays.asList(INSTAGRAM_BASIC));
//
////        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));

//}
