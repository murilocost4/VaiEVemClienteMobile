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
public class Condutor extends Usuario implements Serializable {
    private static final long serialVersionUID = 123456789L;

    public Condutor(int codUsuario, String nomeUsuario, String cpf, String nascimento, String endereco, String senha, String email, String fone) {
        super(codUsuario, nomeUsuario, cpf, nascimento, endereco, senha, email, fone);
    }

    public Condutor(String nomeUsuario, String cpf, String nascimento, String endereco, String senha, String email, String fone) {
        super(nomeUsuario, cpf, nascimento, endereco, senha, email, fone);
    }

    public Condutor(String email, String senha) {
        super(email, senha);
    }

    public Condutor(int codUsuario) {
        super(codUsuario);
    }
}