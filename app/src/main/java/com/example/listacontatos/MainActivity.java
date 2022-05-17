package com.example.listacontatos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Criação das variaveis
    private Button btAdicionar;
    private ArrayList<Contato> listaDinamica;
    private ContatoAdapter adptador;
    private ListView listView;
    public static final int NOVO_CONTATO = 321;
    public static final int ALTERAR_CONTATO = 123;

    private SQLiteDatabase db;
    private AlertDialog adAlterarExcluir;
    private Notification notification;
    private NotificationManager manager;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialização das variaveis
        btAdicionar = (Button) findViewById(R.id.btAdicionar);


        db = openOrCreateDatabase("Agenda", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS CONTATOS (" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NOME VARCHAR(200),"+
                " ENDERECO VARCHAR(200)," +
                " TELEFONE1 VARCHAR(200),"+
                " TELEFONE2 VARCHAR(200))");
        listaDinamica = new ArrayList<Contato>();

        Cursor cursor = db.rawQuery("SELECT * FROM CONTATOS", null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                listaDinamica.add(new Contato(
                        cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("NOME")),
                        cursor.getString(cursor.getColumnIndex("ENDERECO")),
                        cursor.getString(cursor.getColumnIndex("TELEFONE1")),
                        cursor.getString(cursor.getColumnIndex("TELEFONE2"))

                ));

                } while (cursor.moveToNext());
        }
        //listaDinamica.add(new Contato("Ricardo",
                                    //"Rua das Flores, 542",
                                   // "41234354",
                                   // "4499875634"));

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Novo site do integrado")
                .setContentText("Veja o novo site da faculdade")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_baseline_web_24);

        Uri webpage = Uri.parse("https://grupointegrado.br");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                webIntent,
                0);

        builder.setContentIntent(pendingIntent);

        notification = builder.build();

        manager.notify(R.drawable.ic_launcher_background, notification);

        adptador = new ContatoAdapter(this, 0, listaDinamica);
        listView = (ListView) findViewById(R.id.Dinamico);
        listView.setAdapter(adptador);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                LayoutInflater layoutInflater = getLayoutInflater();
                View alterarExcluir = layoutInflater.inflate(R.layout.alterar_excluir, null);
                alterarExcluir.findViewById(R.id.imFundo);
                TextView text = alterarExcluir.findViewById(R.id.tvTitulo);

                text.setText("cLIEUQ EM ALETRAR PARA MODIFICAR");
                alterarExcluir.findViewById(R.id.btAlterar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, adicionarContato.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("posicao", i);
                        bundle.putString("nome", listaDinamica.get(i).getNome());
                        bundle.putString("endereco", listaDinamica.get(i).getEndereco());
                        bundle.putString("telefone1", listaDinamica.get(i).getTelefone1());
                        bundle.putString("telefone2", listaDinamica.get(i).getTelefone2());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, ALTERAR_CONTATO);
                        adAlterarExcluir.dismiss();

                    }
                });
                alterarExcluir.findViewById(R.id.btExcluir).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!listaDinamica.isEmpty()){
                            int idx = listaDinamica.get(i).getId();
                            db.delete("CONTATOS", "ID = ?", new String[]{String.valueOf(idx)});
                            listaDinamica.remove(i);
                            adptador.notifyDataSetChanged();
                            adAlterarExcluir.dismiss();
                        }
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Alterar ou Excluir?");
                builder.setView(alterarExcluir);
                adAlterarExcluir = builder.create();
                adAlterarExcluir.show();
                return false;
                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alterar ou Excluir")
                .setMessage("Clique em alterar para modificar os dados ou excluir para remomer o contato")
                        .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int arg2) {
                                    Intent intent = new Intent(MainActivity.this, adicionarContato.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("posicao", i);
                                    bundle.putString("nome", listaDinamica.get(i).getNome());
                                    bundle.putString("endereco", listaDinamica.get(i).getEndereco());
                                    bundle.putString("telefone1", listaDinamica.get(i).getTelefone1());
                                    bundle.putString("telefone2", listaDinamica.get(i).getTelefone2());
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, ALTERAR_CONTATO);
                            }
                        })
                        .setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int arg2) {
                                if(!listaDinamica.isEmpty()){
                                    int idx = listaDinamica.get(i).getId();
                                    db.delete("CONTATOS", "ID = ?", new String[]{String.valueOf(idx)});
                                    listaDinamica.remove(i);
                                    adptador.notifyDataSetChanged();
                                }
                            }
                        });




                adAlterarExcluir = builder.create();
                adAlterarExcluir.show();

                return false;
                 */

            }
        });





        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adicionarContato.class);
                startActivityForResult(intent,NOVO_CONTATO);
            }
        });
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == ALTERAR_CONTATO)&&(resultCode == RESULT_OK)) {
            ContentValues c1 = new ContentValues();
            int id = listaDinamica.get(data.getIntExtra("posicao", 0)).getId();
            c1.put("NOME", data.getStringExtra("nome"));
            c1.put("ENDERECO", data.getStringExtra("endereco"));
            c1.put("TELEFONE1", data.getStringExtra("telefone1"));
            c1.put("TELEFONE2", data.getStringExtra("telefone2"));
            db.update("CONTATOS", c1, "ID = ?", new String[]{String.valueOf(id)});
            listaDinamica.set(data.getIntExtra("posicao", 0),new Contato(id,
                    data.getStringExtra("nome"),
                    data.getStringExtra("endereco"),
                    data.getStringExtra("telefone1"),
                    data.getStringExtra("telefone2")));

            adptador.notifyDataSetChanged();
        }



        if((requestCode == NOVO_CONTATO)&&(resultCode == RESULT_OK)){
            ContentValues c1 = new ContentValues();
            c1.put("NOME", data.getStringExtra("nome"));
            c1.put("ENDERECO", data.getStringExtra("endereco"));
            c1.put("TELEFONE1", data.getStringExtra("telefone1"));
            c1.put("TELEFONE2", data.getStringExtra("telefone2"));

            db.insert( "CONTATOS", null, c1);

            int idx = 0;

            Cursor cursor = db.rawQuery("SELECT MAX(id) IDMAIOR FROM CONTATOS", null);
            cursor.moveToFirst();
            idx = cursor.getInt(cursor.getColumnIndex("IDMAIOR"));

            listaDinamica.add(new Contato(idx,
                                          data.getStringExtra("nome"),
                                          data.getStringExtra("endereco"),
                                          data.getStringExtra("telefone1"),
                                          data.getStringExtra("telefone2")));
            adptador.notifyDataSetChanged();

        }
    }
}