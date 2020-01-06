package com.example.truth_or_dare;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Game extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        addListenerOnButton();
        setName();
        setPlayers();
    }

    private void setPlayers(){
        ArrayList<String> names = (ArrayList<String>) getIntent().getStringArrayListExtra("KEY_ARRAY");
        TextView playersView = (TextView)findViewById(R.id.playersView);
        playersView.setText("        Гравці: \n");
        for (String i:names){
            playersView.append(i + "\n");
        }

      // ListView playersView = (ListView)findViewById(R.id.amountView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
        //, names);
       // playersView.setAdapter(adapter);



    }

    private void setName(){
        ArrayList<String> names = (ArrayList<String>) getIntent().getStringArrayListExtra("KEY_ARRAY");
        TextView nameView = (TextView)findViewById(R.id.nameView);
        nameView.setText(names.get(random(names.size())));
            //nameView.setText(names.get(i));
            //i++;
    }

    private int random(int size){
        int i = (int) (Math.random()*size);
        return i;
    }

    private void getTruthData(int id) {
        final TextView questionView = (TextView) findViewById(R.id.questionView);
        db.collection("truth").document("question" + id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name;
                        name = documentSnapshot.getString("title");
                        Log.d("NAMETEST:", name);
                        questionView.setText(name);
                    }
                });

    }

    private void getDareData(int id){
        final TextView questionView = (TextView)findViewById(R.id.questionView);
        db.collection("dare").document("question"+id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name;
                        name = documentSnapshot.getString("title");
                        questionView.setText(name);
                    }
                });
    }

    private void addListenerOnButton() {
        final Button btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Game.this);
                        a_builder.setMessage("Вийти з гри?").setCancelable(false)
                                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();

                                    }
                                })
                                .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Завершення");
                        alert.show();
                    }

                }
        );

        Button btn_back = (Button) findViewById(R.id.btn_previous);
        btn_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Game.this);
                        a_builder.setMessage("Повернутися до попереднього меню? \nВнесені зміни буде втрачено")
                                .setCancelable(false)
                                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent("android.intent.action.Input");
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Ні", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = a_builder.create();
                        alertDialog.show();
                    }
                }
        );

        final Button btn_truth = (Button) findViewById(R.id.btn_truth);
        final Button btn_dare = (Button) findViewById(R.id.btn_dare);
        final Button btn_done = (Button) findViewById(R.id.btn_done);
        final TextView questionView = (TextView)findViewById(R.id.questionView);
        btn_done.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_truth.setVisibility(View.VISIBLE);
                        btn_dare.setVisibility(View.VISIBLE);
                        btn_done.setVisibility(View.INVISIBLE);
                        questionView.setText("");
                        setName();
                    }
                }
        );
        btn_truth.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_truth.setVisibility(View.INVISIBLE);
                        btn_dare.setVisibility(View.INVISIBLE);
                        btn_done.setVisibility(View.VISIBLE);
                        getTruthData(random(40));



                    }
                }
        );
        btn_dare.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_truth.setVisibility(View.INVISIBLE);
                        btn_dare.setVisibility(View.INVISIBLE);
                        btn_done.setVisibility(View.VISIBLE);
                        getDareData(random(23));
                    }
                }
        );
    }
}
