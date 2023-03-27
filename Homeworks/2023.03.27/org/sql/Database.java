package org.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.User;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


@Data
@AllArgsConstructor
public class Database {
    private Connection connection;

    public boolean createTableIfNotExist(String tableName) {
        connection = ConnectionFactory.getConnection();
        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id SERIAL PRIMARY KEY, name VARCHAR(255), password VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean dropTableIfExists(String tableName) {
        connection = ConnectionFactory.getConnection();
        try {
            connection.createStatement().execute("DROP TABLE IF EXISTS " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public JSONObject getFromTable(String tableName, String name) {
        connection = ConnectionFactory.getConnection();
        Statement statement;
        JSONObject result = new JSONObject().put("users", new JSONObject());
        JSONObject users = result.getJSONObject("users");
        try {
//            connection.createStatement().execute("SELECT * FROM " + tableName);
            statement = connection.createStatement();
            statement.executeQuery("""
                    SELECT * FROM %s WHERE name = '%s'
                    """.formatted(tableName, name));
            while (statement.getResultSet().next()) {
                int userId = statement.getResultSet().getInt("id");
                users.put(String.valueOf(name), new JSONObject()
                        .put("name", statement.getResultSet().getString("name"))
                        .put("password", statement.getResultSet().getString("password"))
                        .put("id", userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;

    }

    public boolean insertIntoTable(String tableName, String name, String password) {
        connection = ConnectionFactory.getConnection();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            connection.createStatement().execute(
                    "INSERT INTO " + tableName + " (name, password) VALUES ('" + name + "', '" +
                            password + "')");
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
