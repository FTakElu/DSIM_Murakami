package teste.model.enums;

public enum StatusSinal {
    NORMAL("Normal"),
    BAIXO("Baixo"),
    ALTO("Alto"),
    CRITICO("Crítico");
    
    private final String descricao;
    
    StatusSinal(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
