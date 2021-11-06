package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.MovimentacaoInputModel;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public Movimentacao toDomainModel(MovimentacaoInputModel movimentacaoInputModel){
        return mapper.map(movimentacaoInputModel, Movimentacao.class);
    }

    public void copyToDomainModel(MovimentacaoInputModel movimentacaoInputModel, Movimentacao movimentacao){
        mapper.map(movimentacaoInputModel, movimentacao);
    }
}
