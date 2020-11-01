package br.com.app.kroton.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btLogin;
    EditText etEmail;
    EditText etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        etSenha = (EditText) findViewById(R.id.etSenha);
        btLogin = (Button) findViewById(R.id.btLogin);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setText(sharedPreferences.getString("email", ""));
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("email", etEmail.getText().toString());
                editor.commit();
            }
        });
    }
}