package com.humanid.auth.data.source.remote.api.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.auth.data.source.remote.api.user.login.check.CheckLoginResponse;
import com.humanid.auth.data.source.remote.api.user.otp.OTPRequest;
import com.humanid.auth.data.source.remote.api.user.otp.OTPResponse;
import com.humanid.auth.data.source.remote.api.user.register.RegisterRequest;
import com.humanid.auth.data.source.remote.api.user.register.RegisterResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPIService {

    @NonNull
    @POST("users/verifyPhone")
    LiveData<APIResponse<OTPResponse>> requestOTP(@Body OTPRequest request);

    @NonNull
    @POST("users/register")
    LiveData<APIResponse<RegisterResponse>> register(@Body RegisterRequest request);

    @NonNull
    @GET("users/login")
    LiveData<APIResponse<CheckLoginResponse>> checkLogin(
            @Query("hash") String userHash, @Query("appId") String applicationID,
            @Query("appSecret") String applicationSecret);
}
