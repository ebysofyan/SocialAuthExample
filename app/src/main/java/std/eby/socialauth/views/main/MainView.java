package std.eby.socialauth.views.main;

import com.facebook.GraphResponse;

/**
 * Created by eby on 16/01/17.
 */

public interface MainView {
    interface UIInteractor {
        void showProgressDialog(String msg);

        void hideProgressDialog();

        void showMessage(String msg);

        void onLoginSuccess(GraphResponse response);
    }
}
