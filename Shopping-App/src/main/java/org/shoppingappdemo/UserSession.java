package org.shoppingappdemo;

public class UserSession {
    private static String loggedInUsername;

    UserSession(String username) {
        loggedInUsername = username;
    }

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static boolean isLoggedIn() {
        return loggedInUsername != null;
    }

    public static void clearSession() {
        loggedInUsername = null;
    }
}
