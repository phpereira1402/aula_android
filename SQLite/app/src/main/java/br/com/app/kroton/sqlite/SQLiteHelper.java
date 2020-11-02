package br.com.app.kroton.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaCasException;

import androidx.annotation.Nullable;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco.db";
    private static final int VERSAO_BANCO = 1;

    public static final String TABELA_TAREFA = "tarefas";
    public static final String ID_TAREFA = "_id";
    public static final String NOME_TAREFA = "nome";
    public static final String FINALIZADO_TAREFA = "finalizado";

    private static final String CREATE_TAREFA = "CREATE TABLE " + TABELA_TAREFA + "(" +
            ID_TAREFA + " integer primary key autoincrement, " +
            NOME_TAREFA + " text, " +
            FINALIZADO_TAREFA + " text ) ";

    private static final String DROP_TAREFA = "DROP TABLE IF EXISTS " + TABELA_TAREFA;

    public SQLiteHelper(@Nullable Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TAREFA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TAREFA);
        onCreate(db);
    }
}
