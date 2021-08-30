package com.bank.policy.account;

public enum AccountStatus {
    ACTIVE(1),
    BLOCKED(2);

    private Integer statusCode;
    AccountStatus(Integer statusCode){
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
