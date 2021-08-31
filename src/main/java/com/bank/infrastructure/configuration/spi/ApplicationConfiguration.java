package com.bank.infrastructure.configuration.spi;

public interface ApplicationConfiguration {

    String getDBUserName();
    String getDBPassword();
    String getDBurl();
}
