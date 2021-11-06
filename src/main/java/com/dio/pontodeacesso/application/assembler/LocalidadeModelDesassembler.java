package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.LocalidadeInputModel;
import com.dio.pontodeacesso.domain.model.Localidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LocalidadeModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public Localidade toDomainModel(LocalidadeInputModel localidadeInputModel){
        return mapper.map(localidadeInputModel, Localidade.class);
    }

    public void copyToDomainModel(LocalidadeInputModel localidadeInputModel, Localidade localidade){
        mapper.map(localidadeInputModel, localidade);
    }


}
