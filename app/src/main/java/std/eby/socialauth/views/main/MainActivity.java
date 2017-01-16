package std.eby.socialauth.views.main;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import std.eby.socialauth.R;
import std.eby.socialauth.base.DataPassKey;
import std.eby.socialauth.utils.ProgressDialogHelper;
import std.eby.socialauth.utils.api.data.FacebookData;
import std.eby.socialauth.views.result.ResultActivity;

public class MainActivity extends AppCompatActivity implements MainView.UIInteractor {

    @BindView(R.id.btn_login)
    LoginButton fbLoginButton;

    CallbackManager callbackManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        getFacebookData();
    }

    private void getFacebookData() {
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions(Arrays.asList(("public_profile, email")));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        MainActivity.this.onLoginSuccess(response);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                showMessage("Canceled By User");
            }

            @Override
            public void onError(FacebookException error) {
                showMessage(error.toString());
                error.fillInStackTrace();
            }
        });
    }

    @Override
    public void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MainActivity.this);
        }

        ProgressDialogHelper.showDialog(progressDialog, msg);
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(GraphResponse response) {
        FacebookData fbData = new FacebookData();
        try {
            fbData.setProfileId(response.getJSONObject().getString("id"));
            fbData.setName(response.getJSONObject().getString("name"));
            fbData.setEmail(response.getJSONObject().getString("email"));

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putParcelable(DataPassKey.FACEBOOK_DATA_KEY, fbData);
            intent.putExtras(mBundle);
            startActivity(intent);
            this.finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
