package com.example.myfirstapppabv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class ThirdActivity extends AppCompatActivity {

    // Variables para guardar los totales
    private double totalAmount = 0.0;
    private double totalAmountCredit = 0.0;
    private double totalAmountCash = 0.0;

    private static final String FILE_NAME = "totals.txt"; // Nombre del archivo
    private DecimalFormat df = new DecimalFormat("#.##"); // Para formatear los decimales

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Inicializar los TextViews y botones
        TextView tvAmount = findViewById(R.id.totalAmount);
        TextView tvAmountCredit = findViewById(R.id.totalAmountCredit);
        TextView tvAmountCash = findViewById(R.id.totalAmountCash);
        Button btnSave = findViewById(R.id.save);
        Button btnHome = findViewById(R.id.home);
        Button btnDeleteAll = findViewById(R.id.deleteAll);

        // Leer los totales desde el archivo
        leerTotales();

        // Obtener los datos que llegaron desde la SecondActivity
        Intent intent = getIntent();
        String amountStr = intent.getStringExtra("amount");
        boolean isByCreditCard = intent.getBooleanExtra("byCreditCard", false);

        // Si hay una cantidad, convertirla a double
        if (amountStr != null) {
            double amount = Double.parseDouble(amountStr);

            // Sumar al total general
            totalAmount += amount;

            // Verificar si fue con tarjeta o en metálico
            if (isByCreditCard) {
                totalAmountCredit += amount;
            } else {
                totalAmountCash += amount;
            }

            // Mostrar los totales en los TextViews
            tvAmount.setText(df.format(totalAmount) + "€");
            tvAmountCredit.setText(df.format(totalAmountCredit) + "€");
            tvAmountCash.setText(df.format(totalAmountCash) + "€");

            // Configurar el evento del botón Guardar
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarTotales();
                }
            });

            // Configurar el evento del botón inicio
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentMainActivity = new Intent(ThirdActivity.this, MainActivity.class);
                    startActivity(intentMainActivity);
                }
            });
        }

        // Configurar el evento del botón Borrar Datos
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarDatos();
                // Actualizar los TextViews después de borrar los datos
                tvAmount.setText(df.format(totalAmount) + "€");
                tvAmountCredit.setText(df.format(totalAmountCredit) + "€");
                tvAmountCash.setText(df.format(totalAmountCash) + "€");
            }
        });
    }

    // Función para guardar los totales en un archivo
    private void guardarTotales() {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            String datos = totalAmount + "," + totalAmountCredit + "," + totalAmountCash;
            fos.write(datos.getBytes());
            fos.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Función para leer los totales desde el archivo
    private void leerTotales() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            int c;
            StringBuilder temp = new StringBuilder();

            while ((c = fis.read()) != -1) {
                temp.append((char) c);
            }
            fis.close();

            // Dividir los datos por comas y asignar a las variables
            String[] totales = temp.toString().split(",");
            totalAmount = Double.parseDouble(totales[0]);
            totalAmountCredit = Double.parseDouble(totales[1]);
            totalAmountCash = Double.parseDouble(totales[2]);

        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Función para borrar los datos y resetear los totales
    private void borrarDatos() {
        deleteFile(FILE_NAME);
        totalAmount = 0.0;
        totalAmountCredit = 0.0;
        totalAmountCash = 0.0;
    }
}