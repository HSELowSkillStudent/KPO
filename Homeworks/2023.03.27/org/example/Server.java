package org.example;

import lombok.Data;
import org.json.JSONObject;
import org.sql.Database;

@Data
public class Server {
    private Database database;
    private String tableName;

    public Server(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
        database.createTableIfNotExist(this.tableName);
    }

    public boolean isUserExist(String name) {
        return database.getFromTable(this.tableName, name).getJSONObject("users").length() > 0;
    }

    public boolean isPasswordCorrect(String name, String password) {
        JSONObject userData = database.getFromTable(this.tableName, name).getJSONObject("users");
        return userData.getJSONObject(name).getString("password").equals(password);
    }

    public User registerUser(JSONObject userData) {
        if (!database.insertIntoTable(this.tableName, userData.getString("name"), userData.getString("password"))) {
            return null;
        }
        JSONObject result = database.getFromTable(this.tableName, userData.getString("name"));
        User user = new User(result.getJSONObject("users").getJSONObject(userData.getString("name")).getInt("id"), userData.getString("name"), userData.getString("password"));
        return user;
    }

    public User getUser(String name) {
        JSONObject userData = database.getFromTable(this.tableName, name).getJSONObject("users").getJSONObject(name);
        return new User(userData.getInt("id"), userData.getString("name"), userData.getString("password"));
    }
}
