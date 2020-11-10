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

    private final static String TAG = UserAPI.class.getSimpleName();

    private static volatile UserAPI INSTANCE;

    private final Context applicationContext;
    private final UserAPIService service;

    private UserAPI(@NonNull Context applicationContext,
                    @NonNull UserAPIService userAPIService) {
        this.applicationContext = applicationContext;
        this.service = userAPIService;
    }

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

    @NonNull
    @Override
    public LiveData<APIResponse<OTPResponse>> requestOTP(final String countryCode, final String phone) {
        Preconditions.checkNotNull(countryCode, "Country Code cannot be null.");
        Preconditions.checkNotNull(phone, "Phone cannot be null.");
        return service.requestOTP(countryCode, phone);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<LoginResponse>> login(final Map<String, String> params) {
        Preconditions.checkNotNull(params, "Params cannot be null.");
        return service.login(params);
    }

    @NonNull
    @Override
    public LiveData<APIResponse<RevokeAccessResponse>> revokeAccess(final RevokeAccessRequest request) {
        Preconditions.checkNotNull(request, "RevokeAccessRequest cannot be null.");

        return service.revokeAccess(request);
    }
}
