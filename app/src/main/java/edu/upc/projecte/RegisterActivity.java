package edu.upc.projecte;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.Toast;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameRegText;
    private EditText passwordRegText;
    private Button registerButton;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usernameRegText = findViewById(R.id.usernameText);
        passwordRegText = findViewById(R.id.passwordText);
        registerButton = findViewById(R.id.registerButton);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        registerButton.setOnClickListener(v -> {
            String username = usernameRegText.getText().toString().trim();
            String password = passwordRegText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "El nom d'usuari i la contrasenya són obligatoris", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Registre en curs...", Toast.LENGTH_SHORT).show();

            User user = new User(username, password);
            registerUser(user);
        });

    }

    private void registerUser(User user) {
        Call<Void> call = apiService.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registre correcte!", Toast.LENGTH_SHORT).show();
                    // Navegar a una altra activitat si cal
                } else {
                    Toast.makeText(RegisterActivity.this, "Error de registre " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Error de connexió", t);
            }
        });
    }
}