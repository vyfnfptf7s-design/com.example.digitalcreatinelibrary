package com.example.digitalcreatinelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class password_recovery_page extends AppCompatActivity {

    LinearLayout stepEmail, stepCode, stepPassword;
    EditText emailInput, codeInput, newPassword, confirmPassword;
    Button sendCodeBtn, verifyCodeBtn, savePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery_page);

        stepEmail      = findViewById(R.id.stepEmail);
        stepCode       = findViewById(R.id.stepCode);
        stepPassword   = findViewById(R.id.stepPassword);

        emailInput     = findViewById(R.id.emailInput);
        codeInput      = findViewById(R.id.codeInput);
        newPassword    = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);

       // sendCodeBtn     = findViewById(R.id.sendCodeBtn);
       // verifyCodeBtn   = findViewById(R.id.verifyCodeBtn);
       // savePasswordBtn = findViewById(R.id.savePasswordBtn);

        // STEP 1 → Send Code
        sendCodeBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString();

            // בדיקת אימייל
            if (email.isEmpty()) {
                emailInput.setError("נא להזין דוא״ל");
                return;
            }

            // שליחת קוד (שרת / Firebase)
            sendVerificationCode(email);

            stepEmail.setVisibility(View.GONE);
            stepCode.setVisibility(View.VISIBLE);
        });

        // STEP 2 → Verify
        verifyCodeBtn.setOnClickListener(v -> {
            String code = codeInput.getText().toString();

            if (code.isEmpty()) {
                codeInput.setError("נא להזין קוד");
                return;
            }

            // בדיקת קוד (שרת / Firebase)
            if (verifyCode(code)) {
                stepCode.setVisibility(View.GONE);
                stepPassword.setVisibility(View.VISIBLE);
            } else {
                codeInput.setError("קוד שגוי");
            }
        });

        // STEP 3 → Change Password
        savePasswordBtn.setOnClickListener(v -> {
            String pass = newPassword.getText().toString();
            String confirm = confirmPassword.getText().toString();

            if (!pass.equals(confirm)) {
                confirmPassword.setError("סיסמאות לא תואמות");
                return;
            }

            // שמירת הסיסמה (שרת / Firebase)
            saveNewPassword(pass);

            // מעבר חזרה למסך התחברות
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void sendVerificationCode(String email) {
        // קוד לשליחת אימייל
    }

    private boolean verifyCode(String code) {
        // בדיקת קוד
        return true; // לצורך הדגמה
    }

    private void saveNewPassword(String pass) {
        // שמירת סיסמה
    }
}
