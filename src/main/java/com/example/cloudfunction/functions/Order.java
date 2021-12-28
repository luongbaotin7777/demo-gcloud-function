package com.example.cloudfunction.functions;

import com.example.cloudfunction.connection.ConnectDb;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements HttpFunction {
    private static final Logger logger = LoggerFactory.getLogger(Order.class);


    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        Map<String, List<String>> params = httpRequest.getQueryParameters();
        List<Map<String, Object>> results = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();

        ConnectDb connectDb = new ConnectDb();
        Connection con = connectDb.getConnection();
        try {
//            String DB_LOCAL = "jdbc:mysql://34.84.159.165:3306/visiondb?Unicode=true&characterEncoding=utf-8&user=root&password=123456";
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection(DB_LOCAL);

            String sql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, Long.parseLong(params.get("id").get(0)));
            logger.error(params.get("id").get(0));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultMap.put("id", resultSet.getLong("id"));
                resultMap.put("description", resultSet.getString("description"));
                resultMap.put("create_at", resultSet.getTimestamp("create_at"));
            }

            String json = new Gson().toJson(resultMap);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(json);
        } catch (Exception exception) {
            System.out.println("err db: " + exception.getMessage());
            logger.error("err db: " + exception.getMessage());
        }


    }
}
