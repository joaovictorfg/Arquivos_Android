package com.example.joovictorfirmino.exercicio_1;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtNome = (EditText) findViewById(R.id.edtNome);
        final EditText edtConteudo = (EditText) findViewById(R.id.edtConteudo);
        Button btnDeletar = (Button) findViewById(R.id.btnDeletar);
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        Button btnCriar = (Button) findViewById(R.id.btnCriar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream arquivo = openFileOutput(edtNome.getText().toString(), MODE_PRIVATE);
                    String arquivoDados = edtConteudo.getText().toString();
                    arquivo.write(arquivoDados.getBytes());
                    arquivo.close();
                    mensagem("Arquivo gravado!", "Parabéns!");

                } catch (FileNotFoundException erro){
                    mensagem("Arquivo não encontrado!", "" + erro);
                } catch (IOException erro){
                    mensagem("Erro!", "" + erro);
                }
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File lerArquivo = getFileStreamPath(edtNome.getText().toString());
                    if (lerArquivo.exists()) {
                        FileInputStream arquivoBuscar = openFileInput(edtNome.getText().toString());
                        int tamanhoArquivo = arquivoBuscar.available();
                        byte dadosLidos[] = new byte[tamanhoArquivo];
                        arquivoBuscar.read(dadosLidos);
                        String lidoTexto = new String(dadosLidos);
                        edtConteudo.setText(lidoTexto);
                    }else {
                        mensagem("Descupe!", "Arquivo não encontrado");
                    }

                } catch (FileNotFoundException erro){
                    mensagem("Arquivo não encontrado!", "" + erro);
                } catch (IOException erro){
                    mensagem("Erro!", "" + erro);
                }
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean excluir = deleteFile(edtNome.getText().toString());
                if (excluir) {
                    mensagem("Atenção!", "Arquivo excluido com sucesso!");
                }else {
                    mensagem("Atenção!", "Erro ao excluri o arquivo!");
                }
            }
        });
    }

    public void mensagem(String titulo, String texto){
        AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
        msg.setTitle(titulo);
        msg.setMessage(texto);
        msg.setNeutralButton("OK", null);
        msg.show();
    }
}
