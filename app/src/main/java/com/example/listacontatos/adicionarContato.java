package com.example.listacontatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class adicionarContato extends AppCompatActivity {
    private Button Salvar, Cancelar;
    private EditText Nome, Endereco, Telefone1, Telefone2;
    int posicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_contato);

        Salvar = (Button) findViewById(R.id.btSalvar);
        Cancelar = (Button) findViewById(R.id.btCancelar);
        Nome = (EditText) findViewById(R.id.etNome);
        Endereco = (EditText) findViewById(R.id.etEndereco);
        Telefone1 = (EditText) findViewById(R.id.etTelefone1);
        Telefone2 = (EditText) findViewById(R.id.etTelefone2);

        Intent intent = getIntent();
        if(intent.hasExtra("posicao")) {
            posicao = intent.getIntExtra("posicao", 0);
            Nome.setText(intent.getStringExtra("nome"));
            Endereco.setText(intent.getStringExtra("endereco"));
            Telefone1.setText(intent.getStringExtra("telefone1"));
            Telefone2.setText(intent.getStringExtra("telefone2"));
        }



        Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if(posicao > 0){
                    bundle.putInt("posicao", posicao);
                }
                bundle.putString("nome",Nome.getText().toString());
                bundle.putString("endereco",Endereco.getText().toString());
                bundle.putString("telefone1",Telefone1.getText().toString());
                bundle.putString("telefone2",Telefone2.getText().toString());

                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}