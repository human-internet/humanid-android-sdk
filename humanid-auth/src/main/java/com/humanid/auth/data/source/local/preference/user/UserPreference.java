package com.humanid.auth.data.source.local.preference.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.humanid.auth.data.model.User;
import com.humanid.util.Preconditions;

public class UserPreference {

    /**
     *Java string with value UserPreference.
     */
    private final static String TAG = UserPreference.class.getSimpleName();

    /**
     * Java string with value com.humanid.auth.
     */
    private final static String SHARED_PREF_NAME = "com.humanid.auth";

    /**
     * Java string with value USER.
     */
    private final static String USER_KEY = "USER";

    /**
     * Static instance of a UserPreference object, used in getInstance() function.
     */
    private static volatile UserPreference INSTANCE;

    /**
     * Default android application environment.
     */
    private final Context applicationContext;

    /**
     * SharedPreferences object from android system.
     */
    private final SharedPreferences sharedPreferences;

    /**
     * Constructor.
     * @param applicationContext : Default android application environment.
     * @param sharedPreferences : SharedPreferences object from android system.
     */
    private UserPreference(@NonNull Context applicationContext,
                           @NonNull SharedPreferences sharedPreferences) {
        this.applicationContext = applicationContext;
        this.sharedPreferences = sharedPreferences;
    }

    /**
     * If INSTANCE is null, INSTANCE is set to a new UserPreference object with applicationContext = context
     * @param context :  Default android application environment.
     * @return : Returns UserPreference object INSTANCE.
     */
    public static UserPreference getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (UserPreference.class) {
                if (INSTANCE == null) {
                    Context applicationContext = context.getApplicationContext();
                    SharedPreferences sharedPreferences = applicationContext
                            .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    INSTANCE = new UserPreference(applicationContext, sharedPreferences);
                }
            }
        }

        return INSTANCE;
    }

    /**
     *
     * @return : If there is a cached User object in sharedPreferences, returns User object. Else, returns null.
     */
    @Nullable
    public User load() {
        User user = null;

        if (hasCachedUser()) {
            user = getCachedUser();
        }

        return user;
    }

    /**
     *
     * @return : LiveData object that contains User from Preferences
     */
    @NonNull
    public LiveData<User> loadLiveData() {
        MutableLiveData<User> liveData = new MutableLiveData<>();
        liveData.postValue(load());
        return liveData;
    }

    /**
     * Saves user parameter in sharedPreferences.
     * @param user : User object to save
     */
    public void save(@NonNull User user) {
        Preconditions.checkNotNull(user, "User cannot be null.");

        sharedPreferences.edit().putString(USER_KEY, new Gson().toJson(user)).apply();
    }

    /**
     * Removes stored User object in sharedPreferences.
     */
    public void clear() {
        sharedPreferences.edit().remove(USER_KEY).apply();
    }

    /**
     *
     * @return : Returns true if sharedPreferences contains USER_KEY.
     */
    private boolean hasCachedUser() {
        return sharedPreferences.contains(USER_KEY);
    }

    /**
     *
     * @return : Returns User object stored in sharedPreferences.
     */
    @Nullable
    private User getCachedUser() {
        String jsonString = sharedPreferences.getString(USER_KEY, null);

        User user = null;

        if (!TextUtils.isEmpty(jsonString)) {
            user = new Gson().fromJson(jsonString, User.class);
        }

        return user;
    }
}
