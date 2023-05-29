package com.example.user.service.constants;

/**
 * UserAuthConstants
 *
 * Contains constants for user authentication
 */
public class UserAuthConstants {
    private UserAuthConstants() {
    }

    public static final String INVALID_USERNAME = "Invalid username";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String USERNAME_IS_TAKEN = "Username is already used by another user";
    public static final String EMAIL_IS_TAKEN = "Email is already used by another user";
    public static final String EMAIL_NOT_FOUND = "Email not found";
    public static final String WRONG_PASSWORD = "Wrong password";


    public static final String MANAGER_MESSAGE = """
            Manager can:
               change other user's roles,
               monitor the list of users,
               monitor the list of dishes,
               add dishes,
               change dishes price""";
    public static final String CHEF_MESSAGE = """
            Chef can:
               monitor order list,
               get order info by id,
               change order status.""";
    public static final String CUSTOMER_MESSAGE = """
            Customer can:
               monitor menu,
               make orders,
               get order info by id.
            """;

}
