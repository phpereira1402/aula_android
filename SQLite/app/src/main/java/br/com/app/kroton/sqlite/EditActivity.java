package br.com.app.kroton.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etNome;
    CheckBox cbFinalizado;
    Button btGravar;
    RepositorioTarefa repositorio;
    Tarefa tarefa;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        repositorio = new RepositorioTarefa(getBaseContext());

        etNome = (EditText) findViewById(R.id.etNome);
        cbFinalizado = (CheckBox) findViewById(R.id.cbFinalizado);
        btGravar = (Button) findViewById(R.id.btGravar);

        btGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == 0)
                    repositorio.Inserir(etNome.getText().toString(), cbFinalizado.isChecked() ? 1 : 0);
                else
                    repositorio.Alterar(id, etNome.getText().toString(), cbFinalizado.isChecked() ? 1 : 0);
                finish();
            }
        });

        Bundle b = getIntent().getExtras();
        if (b != null)
            id = b.getInt("id");

        tarefa = repositorio.Obter(id);
        if (tarefa != null){
            etNome.setText(tarefa.Nome);
            cbFinalizado.setChecked(tarefa.Finalizado == 1);
        }




    }
}