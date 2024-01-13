package com.example.finalproject.callback;

import com.example.finalproject.information.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface UserCallBack {
    void onUserLoginComplete(Task<AuthResult> task);
    void onUserCreateComplete(Task<AuthResult> task);
    void onUserDataSaveComplete(Task<Void> task);
    void onUserFetchComplete(User user);
}
