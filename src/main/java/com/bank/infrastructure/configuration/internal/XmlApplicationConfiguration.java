package com.bank.infrastructure.configuration.internal;

import com.bank.infrastructure.configuration.spi.ApplicationConfiguration;

public class XmlApplicationConfiguration implements ApplicationConfiguration {

    private String dbUserName;
    private String dbPassword;
    private String dbUrl;

    @Override
    public String getDBUserName() {
        return this.dbUserName;
    }

    @Override
    public String getDBPassword() {
        return this.dbPassword;
    }

    @Override
    public String getDBurl() {
        return this.dbUrl;
    }
}
