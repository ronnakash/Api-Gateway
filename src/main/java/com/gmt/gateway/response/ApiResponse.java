package com.gmt.gateway.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
//@JsonSerialize(using = ApiResponseSerializer.class)
public class ApiResponse<T> {

    final T result;
    final Error[] errors;
    final String errorMessage;
    final ErrorCode errorCode;

    @JsonCreator
    public ApiResponse(
            @JsonProperty("result") T result,
            @JsonProperty("errors") Error[] errors,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("errorCode") String errorCode
    ) {
        this(result, errors, errorMessage, Objects.equals(errorCode, "") ? null : ErrorCode.valueOf(errorCode)); //TODO: make sure valueOf doesn't throw
    }

    public static <T> ApiResponse<T> ok(T t){
        return new ApiResponse<>(t, new Error[0], "", "");
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(null, new Error[0], "", errorCode);
    }

//    public ApiResponse() {
//        this(null, new Error[0], "", null);
//    }
//
//    public ApiResponse(final T result) {
//        this(result, new Error[0], "", null);
//    }
//
//    public ApiResponse(final ErrorCode errorCode) {
//        this(null, new Error[0], "", errorCode);
//    }
//
//    public ApiResponse(final Error[] errors) {
//        this(null, errors, "", ErrorCode.ERROR);
//    }
//
//    public ApiResponse(final Error[] errors, final ErrorCode errorCode) {
//        this(null, errors, "", errorCode);
//    }

    public ApiResponse(final T result, final Error[] errors, final String errorMessage, final ErrorCode errorCode) {
        this.result = result;
        this.errors = errors;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public enum ErrorCode {
        ERROR,
        CORRESPONDENT_EXCEPTION,
        SMS_EXPIRED,
        SMS_WRONG,
        ALREADY_HAVE_CARD_NUMBER,
        BENEFICIARY_NOT_FOUND,
        BENEFICIARY_NOT_FOUND_FOR_CUSTOMER,
        KYC_EXCEPTION,
        GRAILS_VALIDATION_ERROR,
        API_EXCEPTION,
        COMPANY_IDENTIFICATION,
        CAPTCHA_NOT_VERIFIED,
        CUSTOMER_SESSION_NOT_FOUND,
        CUSTOMER_DENIED,
        CUSTOMER_BLOCKED,
        CUSTOMER_DAILY_LIMIT_EXCEEDED,
        REGISTRATION_TOKEN_EXPIRED,
        REGISTRATION_TOKEN_NOT_FOUND,
        CASH_BACK_AMOUNT_NOT_VALID,
        CREDIT_CARD_COMMIT_ERROR,
        CREDIT_CARD_SIGN_ERROR,
        INVALID_SESSION_INFO,
        BAD_REQUEST,
        NOT_FOUND,
        UNAUTHORIZED,
        NEW_CUSTOMER_SESSION_LIMIT_EXCEEDED,
        NEW_CUSTOMER_SESSION_SUM_LIMIT_EXCEEDED,
        NEW_BENEFICIARY_LIMIT_EXCEEDED,
        NEW_BENEFICIARY_AMOUNT_EXCEEDED,
        CUSTOMER_MOBILE_NOT_MATCH,
        WALLET_FRIEND_NOT_UNIQUE,
        WALLET_FRIEND_NOT_REGISTERED,
        BENEFICIARY_CARD_BLOCKED,
        BIOMETRIC_LOGIN_FAILED,
        DOCUMENT_NOT_FOUND,
        FILE_NOT_ALLOWED,
        CARD_WRONG_DETAILS,
        BENEFICIARY_INVALID,
        INVALID_CARD,
        CARD_REJECTED,
        AMOUNT_LIMITED,
        CONTACT_DENIED,
        CARD_DENIED,
        CONTACT_GENERAL,
        TEMPORARY_ERROR,
        MAX_EXCEPTION,
        BLACKLIST,
        BLACKLIST_CARD,
        CORRESPONDENT_BLOCKED;
    }

    public record Error(String field, String code, String message) {
    }

}
