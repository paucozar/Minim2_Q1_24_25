package edu.upc.projecte;

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



public class MainActivity extends AppCompatActivity {

    private Button login_button;
    private Button register_button;
    private ApiService apiService;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login_button = findViewById(R.id.loginButton);
        register_button = findViewById(R.id.registerButton);
        usernameEditText = findViewById(R.id.usernameText);
        passwordEditText = findViewById(R.id.passwordText);
        apiService = RetrofitClient.getClient().create(ApiService.class);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obrir la AddTrackActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el botó d'inici de sessió
        login_button.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "El nom d'usuari i la contrasenya són obligatoris", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(username, password);
            loginUser(user);
        });

    }

    private void loginUser(User user) {
        Call<Void> call = apiService.loginUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Inici de sessió correcte!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    // Navegar a una altra activitat si cal
                } else {
                    Toast.makeText(MainActivity.this, "Error d'inici de sessió: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Error de connexió", t);
            }
        });
    }


}