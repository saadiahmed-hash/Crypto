package com.example.crypto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    CardView selectFilePic;
    TextInputEditText plainTxt, keyTxt;
    TextView resultTxt;
    Button encryptBtn;

    private static final int READ_REQUEST_CODE = 42; // Any integer constant

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.mainColor));
        }

        setContentView(R.layout.activity_main);
        init();
        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // execute function of encryption or decryption
             resultTxt.setText(HillCipher.encrypt(plainTxt.getText().toString() , keyTxt.getText().toString()));
            }
        });

        selectFilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain"); // Limiting to text files

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                // Do something with the URI, like reading the file content
                handleSelectedFile(uri);
            }
        }
    }

    private void handleSelectedFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            String fileContent = stringBuilder.toString();
            plainTxt.setText(fileContent.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void init() {
        selectFilePic = findViewById(R.id.selectFilePic);
        plainTxt = findViewById(R.id.plainTxt);
        keyTxt = findViewById(R.id.keyTxt);
        resultTxt = findViewById(R.id.resultTxt);
        encryptBtn = findViewById(R.id.encryptBtn);
    }
}