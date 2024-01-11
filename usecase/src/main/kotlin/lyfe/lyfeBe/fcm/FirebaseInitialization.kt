package lyfe.lyfeBe.fcm

import org.springframework.stereotype.Service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import jakarta.annotation.PostConstruct
import org.springframework.core.io.ClassPathResource
import java.io.IOException

@Service
class FirebaseInitialization {

    @PostConstruct
    fun initialize() {
        try {
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(ClassPathResource("serviceAccountKey.json").inputStream))
                .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: IOException) {
            throw IllegalStateException("Firebase initialization failed.", e)
        }
    }
}