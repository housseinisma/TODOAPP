package com.example.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TodoItem> todoItems;
    private TodoAdapter adapter;
    private EditText editText;
    private Switch urgentSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoItems = new ArrayList<>();
        adapter = new TodoAdapter(this, todoItems);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        editText = findViewById(R.id.editText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> addTodoItem());

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showDeleteDialog(position);
            return true;
        });
    }

    private void addTodoItem() {
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            boolean isUrgent = urgentSwitch.isChecked();
            todoItems.add(new TodoItem(text, isUrgent));
            adapter.notifyDataSetChanged();
            editText.setText("");
            urgentSwitch.setChecked(false);
        }
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(getString(R.string.delete_dialog_message, position))
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    todoItems.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}