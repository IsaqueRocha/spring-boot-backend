package com.example.cursomvc.domain.enums;

public enum EstadoPagamento {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private final int codigo;
    private final String descricao;

    EstadoPagamento(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static EstadoPagamento toEnum(Integer codigo) {
        if (codigo == null) return null;

        for (EstadoPagamento x : EstadoPagamento.values()) {
            if (codigo.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("ID inválido " + codigo);
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
