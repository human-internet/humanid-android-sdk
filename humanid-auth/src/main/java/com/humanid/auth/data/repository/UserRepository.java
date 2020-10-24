package com.humanid.auth.data.repository;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.humanid.auth.data.model.User;
import com.humanid.auth.data.source.local.preference.user.UserPreference;
import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.auth.data.source.remote.api.user.UserAPI;
import com.humanid.auth.data.source.remote.api.user.login.LoginRequest;
import com.humanid.auth.data.source.remote.api.user.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.user.revoke.RevokeAccessRequest;
import com.humanid.auth.data.source.remote.api.user.revoke.RevokeAccessResponse;
import com.humanid.auth.util.livedata.NetworkBoundResource;
import com.humanid.auth.util.livedata.vo.Resource;
import com.humanid.util.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserRepository {

    /**
     * Java string with value “UserRepository”.
     */
    private final static String TAG = UserRepository.class.getSimpleName();

    /**
     * Static instance of a UserRepository object, used in getInstance() function.
     */
    private static volatile UserRepository INSTANCE;

    /**
     * Default android application environment.
     */
    private final Context applicationContext;

    /**
     User object storing information about current user.

     */
    private User currentUser;

    /**
     * UserPreference object.
     */
    private final UserPreference userPreference;

    /**
     * UserAPI object.
     */
    private final UserAPI userAPI;

    /**
     * Constructor.
     * @param applicationContext : Default android application environment.
     * @param userPreference : UserPreference object.
     * @param userAPI : UserAPI object.
     */
    private UserRepository(@NonNull Context applicationContext,
            @NonNull UserPreference userPreference,
            @NonNull UserAPI userAPI) {
        this.applicationContext = applicationContext;
        this.userPreference = userPreference;
        this.userAPI = userAPI;
    }

    /**
     *If INSTANCE is null, INSTANCE is set to a UserRepository object with applicationContext = context.
     * @param context: Default android application environment.
     * @return : Returns INSTANCE.
     */
    @NonNull
    public static UserRepository getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (UserRepository.class) {
                if (INSTANCE == null) {
                    Context applicationContext = context.getApplicationContext();
                    UserPreference userPreference = UserPreference.getInstance(applicationContext);
                    UserAPI userAPI = UserAPI.getInstance(applicationContext);

                    INSTANCE = new UserRepository(applicationContext, userPreference, userAPI);
                }
            }
        }

        return INSTANCE;
    }

    /**
     *
     * @return : Returns currentUser after calling loadCurrentUser().
     */
    @Nullable
    public User getCurrentUser() {
        loadCurrentUser();
        return currentUser;
    }

    /**
     * Calls clear() on userPreference.
     */
    public void clearUserPreference() {
        userPreference.clear();
    }

    /**
     * <p>Gets User object from userPreference.load(). If value is not null, sets currentUser to value.</p>
     */
    private void loadCurrentUser() {
        User user = userPreference.load();

        if (user == null) {
            return;
        }

        setCurrentUser(user, false);
    }

    /**
     Calls setCurrentUser(currentUser, true) using the input as the parameter.
     * @param currentUser : User object.
     */
    private void setCurrentUser(@Nullable User currentUser) {
        setCurrentUser(currentUser, true);
    }

    /**
     * <p>Sets currentUser (member variable) equal to currentUser(parameter). If saveToCache is true, currentUser is saved
     * in a cache using userPreference.save().</p>
     * @param currentUser : User object
     * @param saveToCache : Bool true if saved to cache.
     */
    private void setCurrentUser(@Nullable User currentUser, boolean saveToCache) {
        this.currentUser = currentUser;

        if (!saveToCache) {
            return;
        }

        if (currentUser != null) {
            userPreference.save(currentUser);
        } else {
            userPreference.clear();
        }
    }

    /**
     *<p>The function creates a NetworkBoundResource object and calls (new NetworkBoundResource object).asLiveData() to
     * convert it to a LiveData object. This object’s createCall() creates a request object and calls userAPI.revokeAccess(request). </p>
     * @param countryCode : String for countryCode.
     * @param phone : String for phone number.
     * @return : A NetWorkBoundResource object.
     */
    @NonNull
    public LiveData<Resource<String>> requestOTP(@NonNull String countryCode, @NonNull String phone) {
        Preconditions.checkArgument(!TextUtils.isEmpty(countryCode), "countryCode");
        Preconditions.checkArgument(!TextUtils.isEmpty(phone), "phone");

        return new NetworkBoundResource<String, OTPResponse>() {

            private String result;

            @NonNull
            @Override
            protected LiveData<String> loadFromLocal() {
                return new LiveData<String>() {
                    private AtomicBoolean started = new AtomicBoolean(false);

                    @Override
                    protected void onActive() {
                        super.onActive();

                        if (started.compareAndSet(false, true)) {
                            postValue("");
                        }
                    }
                };
            }

            @Override
            protected boolean shouldFetch(@Nullable String data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<APIResponse<OTPResponse>> createCall() {
                OTPRequest request = new OTPRequest(countryCode, phone);
                return userAPI.requestOTP(request.getCountryCode(), request.getPhone());
            }

            @Override
            protected void saveCallResult(@NonNull OTPResponse item) {
                result = "Request OTP Succeed";
            }
        }.asLiveData();
    }

    /**
     *<p>If phone, countryCode, and verificationCode are not empty, then the function returns a LiveData object.
     * The function creates a NetworkBoundResource object and calls (new NetworkBoundResource object).asLiveData() to convert it to a LiveData object.
     * This object’s createCall() calls userAPI.login(params). This object’s saveCallResult() calls setCurrentUser(user).</p>
     * @param countryCode
     * @param phone
     * @param verificationCode
     * @param deviceID
     * @return : A NetWorkBoundResource object.
     */
    @NonNull
    public LiveData<Resource<User>> login(
            @NonNull String countryCode,
            @NonNull String phone,
            @NonNull String verificationCode,
            @NonNull String deviceID) {
        Preconditions.checkArgument(!TextUtils.isEmpty(countryCode), "countryCode");
        Preconditions.checkArgument(!TextUtils.isEmpty(phone), "phone");
        Preconditions.checkArgument(!TextUtils.isEmpty(verificationCode), "verificationCode");

        return new NetworkBoundResource<User, LoginResponse>() {
            @NonNull
            @Override
            protected LiveData<User> loadFromLocal() {
                return userPreference.loadLiveData();
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<APIResponse<LoginResponse>> createCall() {
                LoginRequest request = new LoginRequest(countryCode, phone, verificationCode, deviceID);

                Map<String, String> params = new HashMap<>();
                params.put("countryCode", countryCode);
                params.put("phone", phone);
                params.put("deviceId", deviceID);
                params.put("verificationCode", verificationCode);

                return userAPI.login(params);
            }

            @Override
            protected void saveCallResult(@NonNull final LoginResponse item) {
                User user = new User(item.getLoginItem().getUserHash(), item.getLoginItem().getExchangeToken());
                setCurrentUser(user);
            }
        }.asLiveData();
    }

    /**
     * Sets currentUser to null by calling setCurrentUser(null).
     */
    public void logout() {
        setCurrentUser(null);
    }

    /**
     *<p>If the userHash in userPreference isn’t empty, then the function returns a LiveData object. The function creates a NetworkBoundResource
     * object and calls (new NetworkBoundResource object).asLiveData() to convert it to a LiveData object.
     * This object’s createCall() creates a request object and calls </p>
     * @param applicationID
     * @param applicationSecret
     * @return : A NetWorkBoundResource object.
     */
    @NonNull
    public LiveData<Resource<String>> revokeAccess(
            @NonNull String applicationID,
            @NonNull String applicationSecret) {

        Preconditions.checkArgument(!TextUtils.isEmpty(userPreference.load().getUserHash()), "userHash");

        return new NetworkBoundResource<String, RevokeAccessResponse>() {

            private String result;

            @NonNull
            @Override
            protected LiveData<String> loadFromLocal() {
                return new LiveData<String>() {
                    private AtomicBoolean started = new AtomicBoolean(false);

                    @Override
                    protected void onActive() {
                        super.onActive();

                        if (started.compareAndSet(false, true)) {
                            postValue("");
                        }
                    }
                };
            }

            @Override
            protected boolean shouldFetch(@Nullable String data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<APIResponse<RevokeAccessResponse>> createCall() {
                RevokeAccessRequest request = new RevokeAccessRequest(applicationID,
                        applicationSecret, userPreference.load().getUserHash());
                return userAPI.revokeAccess(request);
            }

            @Override
            protected void saveCallResult(@NonNull RevokeAccessResponse item) {
                result = "Revoke Access Succeed";
            }
        }.asLiveData();
    }
}
