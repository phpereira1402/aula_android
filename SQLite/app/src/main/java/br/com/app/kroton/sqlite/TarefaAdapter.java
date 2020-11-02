package br.com.app.kroton.sqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TarefaAdapter extends BaseAdapter {

    private Context context;
    private List<Tarefa> tarefas;
    private ItemViewHolder itemViewHolder;
    private LayoutInflater inflater;

    public TarefaAdapter(Context ctx, List<Tarefa> t){
        this.context = ctx;
        this.tarefas = t;
        inflater = LayoutInflater.from(ctx);
    }

    public static class ItemViewHolder{
        public int  Id;
        public TextView Nome;
        public CheckBox Finalizao;
    }

    public void AtualizarLista(List<Tarefa> lista){
        this.tarefas = lista;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Tarefa getItem(int position) {
        return this.tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.tarefas.get(position).Id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item, null );
            itemViewHolder = new ItemViewHolder();
            itemViewHolder.Nome = (TextView) convertView.findViewById(R.id.tvNome);
            itemViewHolder.Finalizao  = (CheckBox) convertView.findViewById(R.id.cbFinalizado);

            convertView.setTag(itemViewHolder);

        } else
            itemViewHolder = (ItemViewHolder) convertView.getTag();

        if (tarefas.size() > 0){
            Tarefa p = tarefas.get(position);
            itemViewHolder.Nome.setText(p.Nome);
            //itemViewHolder.Id.setText(String.valueOf(p.Id));
            itemViewHolder.Finalizao.setChecked(p.Finalizado == 1);
            itemViewHolder.Id = p.Id;

            itemViewHolder.Nome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemViewHolder item =  (ItemViewHolder) ((View)v.getParent()).getTag();
                    ((MainActivity) context).Editar(item.Id);
                }
            });
            itemViewHolder.Nome.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ItemViewHolder item =  (ItemViewHolder) ((View)v.getParent()).getTag();
                    ((MainActivity) context).Excluir(item.Id);
                    return true;
                }
            });
        }

        return convertView;
    }
}
