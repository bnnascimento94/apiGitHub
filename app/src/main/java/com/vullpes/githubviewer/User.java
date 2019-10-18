package com.vullpes.githubviewer;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


public class User implements Serializable {
    private String nome;
    private byte[] picByte;
    private  List<HashMap<String,String>> repositorios;
    public User(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<HashMap<String, String>> getRepositorios() {
        return repositorios;
    }

    public void setRepositorios(List<HashMap<String, String>> repositorios) {
        this.repositorios = repositorios;
    }


    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}
