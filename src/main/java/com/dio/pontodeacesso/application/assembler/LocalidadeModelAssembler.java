package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.LocalidadeModel;
import com.dio.pontodeacesso.domain.model.Localidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocalidadeModelAssembler {

    @Autowired
    ModelMapper mapper;

    public LocalidadeModel toModel(Localidade localidade){
        return mapper.map(localidade,LocalidadeModel.class);
    }

    public List<LocalidadeModel> toCollectionModel(List<Localidade> localidadeList){
        return localidadeList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
