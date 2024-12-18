package com.todoapp.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.server.DefaultServerFactory;

public class Config extends Configuration {

    @JsonProperty("database")
    private DatabaseConfig database;

    @JsonProperty("server")
    private DefaultServerFactory server = new DefaultServerFactory(); // Add this line

    // Getter and Setter for database config
    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    // Getter and Setter for server
    public DefaultServerFactory getServer() {
        return server;
    }

    public void setServer(DefaultServerFactory server) {
        this.server = server;
    }

    // Nested class for database config
    public static class DatabaseConfig {
        private String driverClass;
        private String user;
        private String password;
        private String url;

        // Getters and setters for database config properties
        public String getDriverClass() {
            return driverClass;
        }

        public void setDriverClass(String driverClass) {
            this.driverClass = driverClass;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
