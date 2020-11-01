package br.com.app.kroton.persistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    String FILENAME = "memoria_interna";
    File file ;
    EditText etId;
    EditText etNome;
    Button btCarregar;
    Button btGravar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file = getFileStreamPath(FILENAME);
        etId = (EditText) findViewById(R.id.etId);
        etNome = (EditText) findViewById(R.id.etNome);
        btCarregar = (Button) findViewById(R.id.btCarregar);
        btGravar = (Button) findViewById(R.id.btGravar);

        btCarregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pessoa p = LerMemoriaInterna();
                etId.setText(String.valueOf(p.id));
                etNome.setText(p.nome);
            }
        });

        btGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pessoa p = new Pessoa(Integer.parseInt(etId.getText().toString()), etNome.getText().toString());
                GravarMemoriaInterna(p);

            }
        });



        Pessoa p = new Pessoa(1, "Aluno");

        GravarMemoriaInterna(p);

    }

    private Pessoa LerMemoriaInterna(){
        Pessoa p = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            p = (Pessoa) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception ex){
            Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show();

        }

        return p;


    }

    private void GravarMemoriaInterna(Pessoa pessoa){

        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos  = new ObjectOutputStream(fos);
            oos.writeObject(pessoa);
            oos.close();
            fos.close();


        } catch (Exception ex){
            Toast.makeText(this, "tsete", Toast.LENGTH_LONG).show();
        }
    }


    public static class Pessoa implements Serializable {

        public int id;
        public String nome;

        Pessoa(int id, String nome){
            this.id = id;
            this.nome = nome;
        }
    }

}