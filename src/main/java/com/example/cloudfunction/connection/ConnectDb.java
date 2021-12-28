package com.example.cloudfunction.connection;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDb {
    @SneakyThrows
    public Connection getConnection() {
        String DB_LOCAL = "jdbc:mysql://34.84.159.165:3306/visiondb?Unicode=true&characterEncoding=utf-8&user=root&password=123456";
        String DB_URL = "jdbc:mysql://google/visiondb?socketFactory=com.google.cloud.sql.mysql.SocketFactory&cloudSqlInstance=fluid-tangent-320302:asia-northeast1:vision-ai-dev&user=root&password=123456";
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(DB_URL);
    }
}
