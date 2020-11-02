package br.com.app.kroton.sqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RepositorioTarefa repositorio;
    List<Tarefa> lista;
    Tarefa tarefa;
    ListView listView;
    TarefaAdapter adapter;
    Button btNovo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repositorio = new RepositorioTarefa(this);
        lista = repositorio.Listar();

        listView = (ListView) findViewById(R.id.listView);
        btNovo = (Button) findViewById(R.id.btNovo);

        adapter = new TarefaAdapter(this, lista);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setTextFilterEnabled(true);
        listView.setLongClickable(true);
        listView.setClickable(true);

        btNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, 100);
            }
        });


        //repositorio.Inserir("teste", 1);
        //tarefa = repositorio.Obter(12);

        //Toast.makeText(this, tarefa.Nome, Toast.LENGTH_LONG).show();

    }
    public void Editar (int id){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        Bundle b = new Bundle();
        b.putInt("id", id);
        intent.putExtras(b);
        startActivityForResult(intent, 100);
    }

    public void Excluir(int id){
        repositorio.Excluir(id);
        adapter.AtualizarLista(repositorio.Listar());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.AtualizarLista(repositorio.Listar());
    }
}