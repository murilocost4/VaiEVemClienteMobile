package modelDominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Viagem implements Serializable{

    private static final long serialVersionUID = 123456789L;

    private int trip_id;
    private String origem;
    private String destino;
    private String data;
    private String saida;
    private String retorno;
    private int status_viagem;
    private int codCondutor;
    private List<StatusPassageiro> statusPassageiro;

    // usado por selects e updates.
    public Viagem(int trip_id, String origem, String destino, String data, String saida, String retorno, int status_viagem, int codCondutor, List<StatusPassageiro> statusPassageiro) {
        this.trip_id = trip_id;
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.saida = saida;
        this.retorno = retorno;
        this.status_viagem = status_viagem;
        this.codCondutor = codCondutor;
        this.statusPassageiro = statusPassageiro != null ? statusPassageiro : new ArrayList<>();
    }


    // INSERTS
    public Viagem(String origem, String destino, String data, String saida, String retorno, int status_viagem, int codCondutor) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.saida = saida;
        this.retorno = retorno;
        this.status_viagem = status_viagem;
        this.codCondutor = codCondutor;
        new ArrayList<>();
    }


    // usado para DELETE
    public Viagem(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public int getStatus_viagem() {
        return status_viagem;
    }

    public void setStatus_viagem(int status_viagem) {
        this.status_viagem = status_viagem;
    }

    public int getCodCondutor() {
        return codCondutor;
    }

    public void setCodCondutor(int codCondutor) {
        this.codCondutor = codCondutor;
    }

    public List<StatusPassageiro> getStatusPassageiros() {
        return statusPassageiro;
    }

    public void setStatusPassageiros(List<StatusPassageiro> statusPassageiro) {
        this.statusPassageiro = statusPassageiro;
    }

    @Override
    public String toString() {
        return "Viagem{" + "trip_id=" + trip_id + ", origem=" + origem +  ", destino=" + destino + '}';
    }
}