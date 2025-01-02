/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDominio;

import java.io.Serializable;

/**
 *
 * @author murilocost4
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 123456789L;

    private int codUsuario;
    private String nomeUsuario;
    private String cpf;
    private String nascimento;
    private String endereco;
    private String senha;
    private String email;
    private String fone;

    public Usuario(int codUsuario, String nomeUsuario, String cpf, String nascimento, String endereco, String senha, String email, String fone) {
        this.codUsuario = codUsuario;
        this.nomeUsuario = nomeUsuario;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.endereco = endereco;
        this.senha = senha;
        this.email = email;
        this.fone = fone;
    }

    public Usuario(String nomeUsuario, String cpf, String nascimento, String endereco, String senha, String email, String fone) {
        this.nomeUsuario = nomeUsuario;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.endereco = endereco;
        this.senha = senha;
        this.email = email;
        this.fone = fone;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int codUsuario, String nomeUsuario) {
        this.codUsuario = codUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public Usuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codUsuario=" + codUsuario + ", nomeUsuario=" + nomeUsuario + ", cpf=" + cpf + ", nascimento=" + nascimento + ", endereco=" + endereco + ", email=" + email + ", fone=" + fone +'}';
    }

}