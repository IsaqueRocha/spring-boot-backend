package com.example.cursomvc.services.validation;

import com.example.cursomvc.domain.enums.TipoCliente;
import com.example.cursomvc.dto.ClienteNewDTO;
import com.example.cursomvc.resources.exception.FieldMessage;
import com.example.cursomvc.services.validation.Utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        /* inclua os testes aqui, inserindo erros na lista */
        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidSsn(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage(
                            "cpfOuCnpj",
                            "número do registro de contribuinte individual brasileiro (CPF) inválido"
                    )
            );
        }

        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidTfn(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage(
                            "cpfOuCnpj",
                            "número do registro de contribuinte corporativo brasileiro (CNPJ) inválido"
                    )
            );
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    e.getMessage())
                    .addPropertyNode(
                            e.getFieldName()
                    )
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}