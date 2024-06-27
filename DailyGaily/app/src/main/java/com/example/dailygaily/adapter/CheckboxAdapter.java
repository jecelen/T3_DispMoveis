package com.example.dailygaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.dailygaily.R;
import com.example.dailygaily.views.TelaTarefa;

import java.util.Date;
import java.util.List;

public class CheckboxAdapter extends BaseAdapter {
    private Context context;
    private List<String> tarefas;
    private LayoutInflater inflater;
    private OnEditTaskListener onEditTaskListener;

    public CheckboxAdapter(Context context, List<String> tarefas, OnEditTaskListener onEditTaskListener) {
        this.context = context;
        this.tarefas = tarefas;
        this.inflater = LayoutInflater.from(context);
        this.onEditTaskListener = onEditTaskListener;
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_checkbox, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkBoxItem);
        checkBox.setText(tarefas.get(position));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditTaskListener != null) {
                    onEditTaskListener.onEditTask(position);

                }
            }
        });

        return convertView;
    }

    public void addTarefa(String tarefa) {
        tarefas.add(tarefa);
        notifyDataSetChanged();
    }

    public void updateTarefa(int position, String newTarefa) {
        tarefas.set(position, newTarefa);
        notifyDataSetChanged();
    }

    public void removeTarefa(int position) {
        tarefas.remove(position);
        notifyDataSetChanged();
    }

    public interface OnEditTaskListener {
        void onEditTask(int position);
    }

}
