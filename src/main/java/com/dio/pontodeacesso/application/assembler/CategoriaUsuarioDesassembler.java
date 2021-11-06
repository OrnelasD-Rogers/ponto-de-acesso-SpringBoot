package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.CategoriaUsuarioInputModel;
import com.dio.pontodeacesso.domain.model.CategoriaUsuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaUsuarioDesassembler {

    @Autowired
    ModelMapper mapper;

    public CategoriaUsuario toDomainModel(CategoriaUsuarioInputModel categoriaUsuarioInputModel){
        return mapper.map(categoriaUsuarioInputModel, CategoriaUsuario.class);
    }

    public void copyToDomainModel(CategoriaUsuarioInputModel categoriaUsuarioInputModel,
                                  CategoriaUsuario categoriaUsuario){

        mapper.map(categoriaUsuarioInputModel,categoriaUsuario);
    }
}
