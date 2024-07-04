package com.example.chat.SessionManager;
import android.content.Context;
import android.content.SharedPreferences;
public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "ChatAppPref";
    private static final String KEY_USER_ID = "userId";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setCurrentUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public int getCurrentUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1); // Default -1 if not found
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
