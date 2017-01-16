package std.eby.socialauth.utils.api.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eby on 16/01/17.
 */


public class FacebookData implements Parcelable {
    private String profileId;
    private String name;
    private String email;

    public FacebookData() {
    }

    protected FacebookData(Parcel in) {
        name = in.readString();
        email = in.readString();
        profileId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(profileId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FacebookData> CREATOR = new Creator<FacebookData>() {
        @Override
        public FacebookData createFromParcel(Parcel in) {
            return new FacebookData(in);
        }

        @Override
        public FacebookData[] newArray(int size) {
            return new FacebookData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
