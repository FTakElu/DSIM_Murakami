package teste.model.enums;

public enum TipoAlerta {
    OXIGENIO_BAIXO("Oxigenação Baixa"),
    OXIGENIO_ALTO("Oxigenação Alta"),
    TEMPERATURA_BAIXA("Temperatura Baixa"),
    TEMPERATURA_ALTA("Temperatura Alta"),
    BATIMENTOS_BAIXO("Batimentos Baixos"),
    BATIMENTOS_ALTO("Batimentos Altos"),
    EMERGENCIA("Emergência Médica"),
    SISTEMA("Alerta do Sistema");
    
    private final String descricao;
    
    TipoAlerta(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
