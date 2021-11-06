package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.CategoriaUsuarioModel;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaUsuarioAssembler {

    @Autowired
    ModelMapper mapper;

    public CategoriaUsuarioModel toModel(CategoriaUsuario categoriaUsuario){
        return mapper.map(categoriaUsuario, CategoriaUsuarioModel.class);
    }

    public List<CategoriaUsuarioModel> toCollectionModel(List<CategoriaUsuario> categoriaUsuarioList){
        return categoriaUsuarioList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
