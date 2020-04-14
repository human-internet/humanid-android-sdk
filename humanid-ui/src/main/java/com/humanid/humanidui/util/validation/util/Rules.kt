package com.humanid.humanidui.util.validation.util

import com.humanid.humanidui.util.validation.rules.CustomRule
import com.humanid.humanidui.util.validation.rules.MinMaxLengthRule
import com.humanid.humanidui.util.validation.rules.RegexRule

fun customRule(errorMessage: String, rule: () -> Boolean): CustomRule {
    return CustomRule(rule, errorMessage)
}

fun regexRule(errorMessage: String, rule: String): RegexRule {
    return RegexRule(rule, errorMessage)
}

fun notEmptyRule(errorMessage: String): RegexRule {
    return RegexRule(CommonRegex.NOT_EMPTY, errorMessage)
}

fun emailRule(errorMessage: String): RegexRule {
    return RegexRule(CommonRegex.EMAIL, errorMessage)
}

fun minMaxLengthRule(errorMessage: String, minLength: Int?, maxLength: Int?): MinMaxLengthRule {
    val min = minLength?.toString() ?: "0"
    val max = maxLength?.toString() ?: ""

    return MinMaxLengthRule(min, max, errorMessage)
}

fun maxLengthRule(errorMessage: String, maxLength: Int?): MinMaxLengthRule {
    return minMaxLengthRule(errorMessage, null, maxLength)
}

fun minLengthRule(errorMessage: String, minLength: Int?): MinMaxLengthRule {
    return minMaxLengthRule(errorMessage, minLength, null)
}

fun numberOnlyRule(errorMessage: String): RegexRule {
    return RegexRule(CommonRegex.NUMBER_ONLY, errorMessage)
}

fun alphabetOnlyRule(errorMessage: String): RegexRule {
    return RegexRule(CommonRegex.ALPHABET_ONLY, errorMessage)
}

fun alphaNumericPasswordRule(errorMessage: String): RegexRule {
    return RegexRule(CommonRegex.PASSWORD_ALPHANUMERIC, errorMessage)
}

fun passwordRule(errorMessage: String): RegexRule {
    return RegexRule(CommonRegex.PASSWORD_SPECIAL_CHAR, errorMessage)
}
