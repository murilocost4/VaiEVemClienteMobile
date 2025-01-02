/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelDominio;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author murilocost4
 */
public class StatusPassageiro implements Serializable {
    private static final long serialVersionUID = 123456789L;

    private int idStatusPassageiro;
    private int idViagem;
    private Passageiro passageiro;
    private int status;
    private Timestamp horaAtualizacao;

    public StatusPassageiro(int idStatusPassageiro, int idViagem, Passageiro passageiro, int status, Timestamp horaAtualizacao) {
        this.idStatusPassageiro = idStatusPassageiro;
        this.idViagem = idViagem;
        this.passageiro = passageiro;
        this.status = status;
        this.horaAtualizacao = horaAtualizacao;
    }

    public StatusPassageiro(int idViagem, Passageiro passageiro, int status, Timestamp horaAtualizacao) {
        this.idViagem = idViagem;
        this.passageiro = passageiro;
        this.status = status;
        this.horaAtualizacao = horaAtualizacao;
    }

    public StatusPassageiro(int idStatusPassageiro) {
        this.idStatusPassageiro = idStatusPassageiro;
    }

    public int getIdStatusPassageiro() {
        return idStatusPassageiro;
    }

    public void setIdStatusPassageiro(int idStatusPassageiro) {
        this.idStatusPassageiro = idStatusPassageiro;
    }

    public int getViagem() {
        return idViagem;
    }

    public void setViagem(int idViagem) {
        this.idViagem = idViagem;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public void setPassageiro(Passageiro passageiro) {
        this.passageiro = passageiro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getHoraAtualizacao() {
        return horaAtualizacao;
    }

    public void setHoraAtualizacao(Timestamp horaAtualizacao) {
        this.horaAtualizacao = horaAtualizacao;
    }


}
