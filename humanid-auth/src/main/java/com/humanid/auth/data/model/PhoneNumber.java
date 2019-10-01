package com.humanid.auth.data.model;

import android.text.TextUtils;

public class PhoneNumber {

    private static final PhoneNumber EMPTY_PHONE_NUMBER = new PhoneNumber("", "", "");

    private final String mPhoneNumber;
    private final String mCountryIso;
    private final String mCountryCode;

    public PhoneNumber(String phoneNumber, String countryIso, String countryCode) {
        mPhoneNumber = phoneNumber;
        mCountryIso = countryIso;
        mCountryCode = countryCode;
    }

    /**
     * Returns an empty instance of this class
     */
    public static PhoneNumber emptyPhone() {
        return EMPTY_PHONE_NUMBER;
    }

    public static boolean isValid(PhoneNumber phoneNumber) {
        return phoneNumber != null
                && !EMPTY_PHONE_NUMBER.equals(phoneNumber)
                && !TextUtils.isEmpty(phoneNumber.getPhoneNumber())
                && !TextUtils.isEmpty(phoneNumber.getCountryCode())
                && !TextUtils.isEmpty(phoneNumber.getCountryIso());
    }

    public static boolean isCountryValid(PhoneNumber phoneNumber) {
        return phoneNumber != null
                && !EMPTY_PHONE_NUMBER.equals(phoneNumber)
                && !TextUtils.isEmpty(phoneNumber.getCountryCode())
                && !TextUtils.isEmpty(phoneNumber.getCountryIso());
    }

    /**
     * Returns country code
     */
    public String getCountryCode() {
        return mCountryCode;
    }

    /**
     * Returns phone number without country code
     */
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    /**
     * Returns 2 char country ISO
     */
    public String getCountryIso() {
        return mCountryIso;
    }
}
