package com.foodme.application.infrastructure;

public class ConnectionInfo {
    private final String connectionString;
    private final String user;
    private final String password;

    public ConnectionInfo(String connectionString, String user, String password) {

        this.connectionString = connectionString;
        this.user = user;
        this.password = password;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
