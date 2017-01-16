package std.eby.socialauth.views.result;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import std.eby.socialauth.R;
import std.eby.socialauth.base.DataPassKey;
import std.eby.socialauth.utils.api.data.FacebookData;
import std.eby.socialauth.views.main.MainActivity;

/**
 * Created by eby on 16/01/17.
 */

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.img_profile)
    ProfilePictureView profileView;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_email)
    TextView txtEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(ResultActivity.this);

        getPassingData();
    }

    private void getPassingData() {
        Bundle mBundle = getIntent().getExtras();
        FacebookData data = mBundle.getParcelable(DataPassKey.FACEBOOK_DATA_KEY);
        profileView.setProfileId(data.getProfileId());
        txtName.setText(data.getName());
        txtEmail.setText(data.getEmail());
    }

    @OnClick(R.id.btn_logout)
    void doLogout() {
        LoginManager.getInstance().logOut();

        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
