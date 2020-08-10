package com.example.cursomvc.domain.enums;

public enum TipoCliente {
    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private final int codigo;
    private final String descricao;

    TipoCliente(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoCliente toEnum(Integer codigo) {
        if (codigo == null) return null;

        for (TipoCliente x : TipoCliente.values()) {
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
