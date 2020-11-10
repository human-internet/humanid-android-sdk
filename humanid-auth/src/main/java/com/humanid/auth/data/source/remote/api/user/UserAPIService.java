package com.humanid.auth.data.source.remote.api.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.auth.data.source.remote.api.user.login.LoginResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.user.revoke.RevokeAccessRequest;
import com.humanid.auth.data.source.remote.api.user.revoke.RevokeAccessResponse;
import java.util.Map;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserAPIService {

    @NonNull
    @FormUrlEncoded
    @POST("users/login/request-otp")
    LiveData<APIResponse<OTPResponse>> requestOTP(@Field("countryCode") String countryCode, @Field("phone") String phone);

    @NonNull
    @FormUrlEncoded
    @POST("users/login")
    LiveData<APIResponse<LoginResponse>> login(@FieldMap Map<String, String> params);

    @NonNull
    @PUT("users/revokeAccess")
    LiveData<APIResponse<RevokeAccessResponse>> revokeAccess(@Body RevokeAccessRequest request);
}
