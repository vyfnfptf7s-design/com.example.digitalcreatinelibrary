package com.example.digitalcreatinelibrary;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class singup_page extends AppCompatActivity {

    private EditText fullNameEditText, phoneEditText, emailEditText, passwordEditText;
    private ImageButton signupButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page); // שמו של קובץ ה-XML שלך

        // חיבור ל־Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // חיבור ל־שדות ה־XML
        fullNameEditText = findViewById(R.id.fullNameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // בדיקות תקינות
        if(fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "אנא הכנס אימייל חוקי", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(this, "הסיסמה חייבת להיות לפחות 6 תווים", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!phone.matches("\\d{9,15}")){
            Toast.makeText(this, "אנא הכנס מספר טלפון חוקי (9-15 ספרות)", Toast.LENGTH_SHORT).show();
            return;
        }

        // יצירת משתמש ב־Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        // שמירת נתונים נוספים ב-Firestore
                        String uid = mAuth.getCurrentUser().getUid();

                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("fullName", fullName);
                        userMap.put("phone", phone);
                        userMap.put("email", email);

                        db.collection("users").document(uid)
                                .set(userMap)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        Toast.makeText(singup_page.this, "הרשמה בוצעה בהצלחה!", Toast.LENGTH_SHORT).show();
                                        // כאן אפשר לעבור למסך הבא
                                    } else {
                                        Toast.makeText(singup_page.this, "שגיאה בשמירת הנתונים: " + task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });

                    } else {
                        Toast.makeText(singup_page.this, "שגיאה בהרשמה: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
