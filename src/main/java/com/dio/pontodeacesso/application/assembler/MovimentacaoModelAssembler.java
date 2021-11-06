package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.MovimentacaoModel;
import com.dio.pontodeacesso.domain.model.Movimentacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovimentacaoModelAssembler {

    @Autowired
    ModelMapper mapper;

    public MovimentacaoModel toModel(Movimentacao movimentacao){
        return mapper.map(movimentacao, MovimentacaoModel.class);
    }

    public List<MovimentacaoModel> toCollectionModel(List<Movimentacao> movimentacoes){
        return movimentacoes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
