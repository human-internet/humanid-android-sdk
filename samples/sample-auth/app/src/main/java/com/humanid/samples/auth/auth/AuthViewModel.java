package com.humanid.samples.auth.auth;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.humanid.auth.HumanIDAuth;
import com.humanid.auth.HumanIDUser;
import com.humanid.samples.auth.BaseViewModel;
import com.humanid.samples.auth.Event;
import com.humanid.task.OnFailureListener;
import com.humanid.task.OnSuccessListener;

public class AuthViewModel extends BaseViewModel {

    private final static String TAG = AuthViewModel.class.getSimpleName();

    public enum AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,          // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    public final MutableLiveData<AuthenticationState> authenticationState = new MutableLiveData<>();

    public final MutableLiveData<String> inputCountryCode = new MutableLiveData<>();
    public final MutableLiveData<String> inputPhone = new MutableLiveData<>();
    public final MutableLiveData<String> inputVerificationCode = new MutableLiveData<>();

    private final MutableLiveData<String> errorInputCountryCode = new MutableLiveData<>();
    private final MutableLiveData<String> errorInputPhone = new MutableLiveData<>();
    private final MutableLiveData<String> errorInputVerificationCode = new MutableLiveData<>();

    private final MutableLiveData<Boolean> signByHumanID = new MutableLiveData<>();
    private final MutableLiveData<Boolean> otpRequested = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> snackBar = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authenticationState.postValue(AuthenticationState.UNAUTHENTICATED);
        signByHumanID.postValue(false);
        otpRequested.postValue(false);
        loading.postValue(false);

        checkLoggedIn();
    }

    public LiveData<AuthenticationState> getAuthenticationState() {
        return authenticationState;
    }

    public LiveData<String> getErrorInputCountryCode() {
        return errorInputCountryCode;
    }

    public LiveData<String> getErrorInputPhone() {
        return errorInputPhone;
    }

    public LiveData<String> getErrorInputVerificationCode() {
        return errorInputVerificationCode;
    }

    public LiveData<Boolean> getSignByHumanID() {
        return signByHumanID;
    }

    public LiveData<Boolean> getOtpRequested() {
        return otpRequested;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<Event<String>> getSnackBar() {
        return snackBar;
    }

    private void checkLoggedIn() {
        if (HumanIDAuth.getInstance().getCurrentUser() == null) {
            authenticationState.postValue(AuthenticationState.UNAUTHENTICATED);
        }
    }

    private boolean isValidCountryCode() {
        if (inputCountryCode.getValue() == null) {
            errorInputCountryCode.postValue("Country code required");
            return false;
        } else if (inputCountryCode.getValue().length() < 2
                || inputCountryCode.getValue().length() > 4) {
            errorInputCountryCode.postValue("Invalid country code");
            return false;
        } else {
            errorInputCountryCode.postValue(null);
            return true;
        }
    }

    private boolean isValidPhone() {
        if (inputPhone.getValue() == null) {
            errorInputPhone.postValue("Phone required");
            return false;
        } else if (inputPhone.getValue().length() < 9
                || inputPhone.getValue().length() > 15) {
            errorInputPhone.postValue("Invalid phone");
            return false;
        } else {
            errorInputPhone.postValue(null);
            return true;
        }
    }

    private boolean isValidVerificationCode() {
        if (inputVerificationCode.getValue() == null) {
            errorInputVerificationCode.postValue("Verification code required");
            return false;
        } else if (inputVerificationCode.getValue().length() != 6) {
            errorInputVerificationCode.postValue("Invalid verification code");
            return false;
        } else {
            errorInputVerificationCode.postValue(null);
            return true;
        }
    }

    private void requestOTP(String countryCode, String phone) {
        HumanIDAuth.getInstance().requestOTP(countryCode, phone)
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String message) {
                        otpRequested.postValue(true);
                        loading.postValue(false);
                        snackBar.postValue(new Event<>(message));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.postValue(false);
                        snackBar.postValue(new Event<>(e.getMessage()));
                    }
                });
    }

    private void register(String countryCode, String phone, String verificationCode) {
        HumanIDAuth.getInstance().register(countryCode, phone, verificationCode)
                .addOnSuccessListener(new OnSuccessListener<HumanIDUser>() {
                    @Override
                    public void onSuccess(HumanIDUser humanIDUser) {
                        loading.postValue(false);
                        authenticationState.postValue(AuthenticationState.AUTHENTICATED);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.postValue(false);
                        snackBar.postValue(new Event<>(e.getMessage()));
                        authenticationState.postValue(AuthenticationState.INVALID_AUTHENTICATION);
                    }
                });
    }

    public void onClickSignByHumanID() {
        signByHumanID.postValue(true);
    }

    public void onClickRequestOTP() {
        if (!isValidCountryCode() && !isValidPhone()) return;

        loading.postValue(true);

        requestOTP(inputCountryCode.getValue(), inputPhone.getValue());
    }

    public void onClickVerify() {
        if (!isValidCountryCode() && !isValidPhone() && !isValidVerificationCode()) return;

        loading.postValue(true);

        register(inputCountryCode.getValue(), inputPhone.getValue(), inputVerificationCode.getValue());
    }
}
