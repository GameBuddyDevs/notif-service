package com.back2261.notifservice.domain.service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

public class FCMInitializer {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream serviceAccount = classLoader.getResourceAsStream(firebaseConfigPath);
            if (serviceAccount == null) {
                throw new IllegalArgumentException("Firebase config file not found");
            }
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase application has been initialized");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
