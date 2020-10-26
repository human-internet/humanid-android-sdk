package com.humanid.auth.data.source.remote.api.user;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.auth.data.source.remote.api.HttpClient;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.user.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.user.revoke.RevokeAccessRequest;
import com.humanid.auth.data.source.remote.api.user.revoke.RevokeAccessResponse;
import com.humanid.util.Preconditions;
import java.util.HashMap;
import java.util.Map;

public class UserAPI implements UserAPIService {

    /**
     Java string with value “UserAPI”.
     */
    private final static String TAG = UserAPI.class.getSimpleName();

    /**
     * Static instance of a UserAPI object, used in getInstance().
     */
    private static volatile UserAPI INSTANCE;

    /**
     * Default android application environment.
     */
    private final Context applicationContext;

    /**
     * Interface for UserAPI

     */
    private final UserAPIService service;

    /**
     * Constructor.
     * @param applicationContext : Default android application environment.
     * @param userAPIService : Interface for UserAPI
     */
    private UserAPI(@NonNull Context applicationContext,
                    @NonNull UserAPIService userAPIService) {
        this.applicationContext = applicationContext;
        this.service = userAPIService;
    }

    /**
     *
     * @param context : Application context.
     * @return : If INSTANCE is null, sets INSTANCE to return a new UserAPI object with given context.

     */
    @NonNull
    public static UserAPI getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (UserAPI.class) {
                if (INSTANCE == null) {
                    Context applicationContext = context.getApplicationContext();
                    UserAPIService userAPIService = HttpClient.createService(UserAPIService.class);

                    INSTANCE = new UserAPI(applicationContext, userAPIService);
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Checks to make sure parameters are not null.
     * @param countryCode : String countryCode.
     * @param phone : String phone number.
     * @return : Returns LiveData object retrieved from service.requestOTP().
     */
    @NonNull
    @Override
    public LiveData<APIResponse<OTPResponse>> requestOTP(final String countryCode, final String phone) {
        Preconditions.checkNotNull(countryCode, "Country Code cannot be null.");
        Preconditions.checkNotNull(phone, "Phone cannot be null.");
        return service.requestOTP(countryCode, phone);
    }

    /**
     *  Checks to make sure parameters are not null.
     * @param params : Login Map data.
     * @return : Returns LiveData object retrieved from service.login().
     */
    @NonNull
    @Override
    public LiveData<APIResponse<LoginResponse>> login(final Map<String, String> params) {
        Preconditions.checkNotNull(params, "Params cannot be null.");
        return service.login(params);
    }

    /**
     *  Checks to make sure parameters are not null.
     * @param request : RevokeAccessRequest data.
     * @return : Returns LiveData object retrieved from service.revokeAccess().
     */
    @NonNull
    @Override
    public LiveData<APIResponse<RevokeAccessResponse>> revokeAccess(final RevokeAccessRequest request) {
        Preconditions.checkNotNull(request, "RevokeAccessRequest cannot be null.");

        return service.revokeAccess(request);
    }
}
