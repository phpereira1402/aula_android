package br.com.app.kroton.persistencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
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

    Button btCarregarMemoriaExterna;
    Button btGravarMemoriaExterna;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file = getFileStreamPath(FILENAME);
        etId = (EditText) findViewById(R.id.etId);
        etNome = (EditText) findViewById(R.id.etNome);
        btCarregar = (Button) findViewById(R.id.btCarregar);
        btGravar = (Button) findViewById(R.id.btGravar);
        btCarregarMemoriaExterna = (Button) findViewById(R.id.btCarregarMemoriaExterna);
        btGravarMemoriaExterna = (Button) findViewById(R.id.btGravarMemoriaExterna);

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

        btCarregarMemoriaExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pessoa p = LerMemoriaExterna();
                etId.setText(String.valueOf(p.id));
                etNome.setText(p.nome);
            }
        });

        btGravarMemoriaExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pessoa p = new Pessoa(Integer.parseInt(etId.getText().toString()), etNome.getText().toString());
                GravarMemoriaExterna(p);
            }
        });

        verifyStoragePermissions(this);

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


    private boolean GravarMemoriaExterna(Pessoa pessoa){

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File arquivo = new File(diretorio, "pessoa.obj");
            try {
                FileOutputStream fos = new FileOutputStream(arquivo);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(pessoa);
                fos.close();
                oos.close();
            } catch (Exception ex){
                return false;
            }

            return true;
        }
        return false;
    }

    private Pessoa LerMemoriaExterna(){
        String state = Environment.getExternalStorageState();
        Pessoa p = null;
        if (Environment.MEDIA_MOUNTED.equals(state)){
            File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File arquivo = new File(diretorio, "pessoa.obj");
            try {
                FileInputStream fis = new FileInputStream(arquivo);
                ObjectInputStream ois = new ObjectInputStream(fis);
                p = (Pessoa) ois.readObject();
                fis.close();
                ois.close();
            } catch (Exception ex){
                System.out.println(ex.toString());
                return new Pessoa(0, "Pessoa não cadastrada");

            }
        }
        return p;
    }




    public static class Pessoa implements Serializable {

        public int id;
        public String nome;

        Pessoa(int id, String nome){
            this.id = id;
            this.nome = nome;
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}