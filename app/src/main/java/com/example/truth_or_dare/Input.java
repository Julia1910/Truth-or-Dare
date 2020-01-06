package com.example.truth_or_dare;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Input extends AppCompatActivity {


    ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        addListenerOnButton();
    }



    public void addListenerOnButton() {
        Button btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Input.this, Game.class);
                        intent.putStringArrayListExtra("KEY_ARRAY", names);
                        startActivity(intent);
                        finish();
                    }
                }
        );


        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText edit_name = (EditText) findViewById(R.id.edit_name);
                        TextView amountView = (TextView)findViewById(R.id.amountView);
                        String name = edit_name.getText().toString();
                        names.add(name);
                        amountView.setText("Додано учасників: " + names.size());
                        edit_name.getText().clear();

                    }
                }
        );
    }

}
