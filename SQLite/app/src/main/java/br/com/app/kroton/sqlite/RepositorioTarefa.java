package br.com.app.kroton.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTarefa {
    private SQLiteDatabase db;
    private SQLiteHelper helper;

    public RepositorioTarefa(Context context){
        helper = new SQLiteHelper(context);
    }

    public boolean Inserir(String nome, int finalizado){
        ContentValues contentValues;
        long resultado;

        db = helper.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(helper.NOME_TAREFA, nome);
        contentValues.put(helper.FINALIZADO_TAREFA, finalizado);
        resultado = db.insert(helper.TABELA_TAREFA, null, contentValues);
        db.close();

        return resultado > 0;
    }

    public boolean Alterar(int id, String nome, int finalizado){
        ContentValues contentValues;
        long resultado;

        String condicao = helper.ID_TAREFA + "=" + id;

        db = helper.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(helper.NOME_TAREFA, nome);
        contentValues.put(helper.FINALIZADO_TAREFA, finalizado);
        resultado = db.update(helper.TABELA_TAREFA, contentValues, condicao,null);
        db.close();

        return resultado > 0;
    }

    public List<Tarefa> Listar(){
        Cursor cursor;
        List<Tarefa> lista = new ArrayList<>();

        String[] campos  = {helper.ID_TAREFA,  helper.NOME_TAREFA, helper.FINALIZADO_TAREFA};
        db = helper.getReadableDatabase();
        cursor = db.query(helper.TABELA_TAREFA, campos, null, null, null, null, null);
        if (cursor != null){
            while(cursor.moveToNext()){
                Tarefa tarefa = new Tarefa();
                tarefa.Id =  cursor.getInt(cursor.getColumnIndexOrThrow(helper.ID_TAREFA));
                tarefa.Nome  =  cursor.getString(cursor.getColumnIndexOrThrow(helper.NOME_TAREFA));
                tarefa.Finalizado =  cursor.getInt(cursor.getColumnIndexOrThrow(helper.FINALIZADO_TAREFA));
                lista.add(tarefa);
            }
        }
        db.close();
        return lista;
    }

    public Tarefa Obter(int id){
        Cursor cursor;
        Tarefa tarefa = new Tarefa();

        String[] campos  = {helper.ID_TAREFA,  helper.NOME_TAREFA, helper.FINALIZADO_TAREFA};
        String condicao = helper.ID_TAREFA + "=" + id;

        db = helper.getReadableDatabase();
        cursor = db.query(helper.TABELA_TAREFA, campos, condicao, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            tarefa.Id =  cursor.getInt(cursor.getColumnIndexOrThrow(helper.ID_TAREFA));
            tarefa.Nome  =  cursor.getString(cursor.getColumnIndexOrThrow(helper.NOME_TAREFA));
            tarefa.Finalizado =  cursor.getInt(cursor.getColumnIndexOrThrow(helper.FINALIZADO_TAREFA));
        }
        db.close();
        return tarefa;
    }

    public void Excluir(int id){
        String condicao = helper.ID_TAREFA + "=" + id;
        db = helper.getWritableDatabase();
        db.delete(helper.TABELA_TAREFA, condicao, null);
        db.close();
    }

}
