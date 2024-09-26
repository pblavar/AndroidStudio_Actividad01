package com.example.myfirstapppabv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnCalc = findViewById(R.id.calc);
        Button btnBack = findViewById(R.id.back);
        Button btnClean = findViewById(R.id.clean);
        EditText etDescription = findViewById(R.id.description);
        EditText etAmount = findViewById(R.id.amount);
        Switch swByCreditCard = findViewById(R.id.byCreditCard);

        // Vamos a asignarle un evento al botón cuando se haga click
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etDescription.getText().toString().isEmpty()){
                    Toast toastName = new Toast(SecondActivity.this);
                    toastName.setText("Description can´t be empty");
                    toastName.show();
                } else if(etAmount.getText().toString().isEmpty()){
                    Toast toastSurname = new Toast(SecondActivity.this);
                    toastSurname.setText("amount can´t be empty");
                    toastSurname.show();
                } else {
                    // Verificar si amountStr es un número válido
                    try {
                        String amountStr = etAmount.getText().toString().replace(',', '.'); // Reemplazar coma por punto
                        Double.parseDouble(amountStr); // Intenta convertir a double, en caso de error, lanza la exception

                        // Si la conversión es exitosa, crear el Intent
                        Intent intentSecondActivity = new Intent(SecondActivity.this, ThirdActivity.class);

                        // Añado las clave-valor al intent
                        intentSecondActivity.putExtra("description", etDescription.getText().toString());
                        intentSecondActivity.putExtra("amount", amountStr);
                        intentSecondActivity.putExtra("byCreditCard", swByCreditCard.isChecked());

                        // Log para ver si se está enviando bien los valores
                        Log.i("description", etDescription.getText().toString());
                        Log.i("amount", amountStr);
                        Log.i("byCreditCard", String.valueOf(swByCreditCard.isChecked()));

                        startActivity(intentSecondActivity);

                    } catch (NumberFormatException e) {
                        Toast.makeText(SecondActivity.this, "Invalid amount. Please enter a valid number. Format: (0.0).", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMainActivity = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intentMainActivity);
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDescription.setText("");
                etAmount.setText("");
                swByCreditCard.setChecked(false);
            }
        });
    }
}