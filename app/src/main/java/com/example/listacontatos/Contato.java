package com.example.listacontatos;

public class Contato {
    private int id;
    private String nome;
    private String endereco;
    private String telefone1;
    private String telefone2;

    public Contato(int id, String nome,String endereco, String telefone1, String telefone2){

        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

}
