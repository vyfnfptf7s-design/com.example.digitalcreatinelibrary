package com.example.digitalcreatinelibrary;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // מעבר למסך Sing_up
        Intent intent = new Intent(MainActivity.this, singup_page.class);
        startActivity(intent);

        // אתחול Firebase
        FirebaseApp.initializeApp(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // התחברות אנונימית (בדיקה)
        auth.signInAnonymously().addOnSuccessListener(result -> {

            Map<String, Object> note = new HashMap<>();
            note.put("title", "שלום Firestore!");

            db.collection("notes").add(note);

        }).addOnFailureListener(e -> {
            // טיפול בשגיאה כאן
        });
    }
}
