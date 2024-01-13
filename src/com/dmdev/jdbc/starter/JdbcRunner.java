package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        try {
            selectScript();
        } finally {
            ConnectionManager.closePool();
        }
    }

    private static void selectScript() throws SQLException {
        String sql = """
                    SELECT *
                    FROM ticket
                """;

        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getLong("id"));
                System.out.println(resultSet.getString("passenger_no"));
                System.out.println(resultSet.getString("passenger_name"));
                System.out.println("----------");
            }
        }
    }
}
