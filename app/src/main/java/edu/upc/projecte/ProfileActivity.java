package edu.upc.projecte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText usernameText, passwordText, fullNameText, emailText, ageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        fullNameText = findViewById(R.id.fullNameText);
        emailText = findViewById(R.id.emailText);
        ageText = findViewById(R.id.ageText);

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateUserDataInDatabase()) {
                    Toast.makeText(ProfileActivity.this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Error, no se ha podido actualizar el perfil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean updateUserDataInDatabase() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:8080/dsajuego", "usuario", "contraseña");

            // Create the SQL query to update the user data
            String sql = "UPDATE user SET username = ?, password = ?, fullName = ?, email = ?, age = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usernameText.getText().toString());
            preparedStatement.setString(2, passwordText.getText().toString());
            preparedStatement.setString(3, fullNameText.getText().toString());
            preparedStatement.setString(4, emailText.getText().toString());
            preparedStatement.setInt(5, Integer.parseInt(ageText.getText().toString()));
            preparedStatement.setString(6, getUserId()); // Assume you have a method to get the user ID

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getUserId() {
        // Implement your logic to get the user ID
        // For example, you can retrieve it from SharedPreferences or pass it as an Intent extra
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("userId", "defaultUserId");
    }
}