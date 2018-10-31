package com.saraj.prep;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        initializeAdminDatabase();
    }

    private static void initializeAdminDatabase() throws IOException {
        try (InputStream serviceAccount =
                    ClassLoader.getSystemClassLoader().getResourceAsStream("private_key.json")) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://trial-by-fire-6fb1d.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }
}
