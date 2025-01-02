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
public class Passageiro extends Usuario implements Serializable {
    private static final long serialVersionUID = 123456789L;

    public Passageiro(int codUsuario, String nomeUsuario, String cpf, String nascimento, String endereco, String senha, String email, String fone) {
        super(codUsuario, nomeUsuario, cpf, nascimento, endereco, senha, email, fone);
    }

    public Passageiro(String nomeUsuario, String cpf, String nascimento, String endereco, String senha, String email, String fone) {
        super(nomeUsuario, cpf, nascimento, endereco, senha, email, fone);
    }

    public Passageiro(String email, String senha) {
        super(email, senha);
    }

    public Passageiro(int codUsuario) {
        super(codUsuario);
    }

    public Passageiro(int codUsuario, String nomeUsuario) {
        super(codUsuario, nomeUsuario);
    }


}