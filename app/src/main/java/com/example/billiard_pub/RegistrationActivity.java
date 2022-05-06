package com.example.billiard_pub;

import static javax.crypto.Cipher.SECRET_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.crypto.SecretKey;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();



    EditText userNameEditText;
    EditText userEmailEditText;
    EditText userNamePasswordEditText;
    EditText userNamePasswordAgainEditText;
    EditText phoneNumberEditText;

    Button notify_btn;


    private SharedPreferences preferences;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

       if (secret_key != 99) {
            finish();
        }

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        userNamePasswordEditText = findViewById(R.id.passwordEditText);
        userNamePasswordAgainEditText = findViewById(R.id.passwordAgainEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        SharedPreferences preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String password = preferences.getString("password", "");

        userNameEditText.setText(username);
        userNamePasswordEditText.setText(password);



        Log.i(LOG_TAG, "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart");
    }

    public void register(View view) {
        String username = userNameEditText.getText().toString();
        String email = userEmailEditText.getText().toString();
        String password = userNamePasswordEditText.getText().toString();
        String passwordconfirm = userNamePasswordAgainEditText.getText().toString();
        String phone = phoneNumberEditText.getText().toString();

        if (!password.equals(passwordconfirm)) {
            Log.e(LOG_TAG, "Nem egyezik a jelszó és a megerősítése!");
            return;
        }

        Log.i(LOG_TAG, "Regisztrált: " + username + ", e-mail: " + email);
        gotoBooking();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "Felhasznalo letrehozasa sikerult.");
                    gotoBooking();
                } else {
                    Log.d(LOG_TAG, "Nem sikerult letrehozni a felhasznalot:", task.getException());
                    Toast.makeText(RegistrationActivity.this, "Nem sikerult letrehozni a felhasznalot: ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void gotoBooking() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);

    }

    public void cancel(View view) {
        finish();
    }
}