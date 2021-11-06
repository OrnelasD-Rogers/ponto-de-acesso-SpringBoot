package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.UsuarioInputModel;
import com.dio.pontodeacesso.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public Usuario toDomainModel(UsuarioInputModel usuarioInputModel){
        return mapper.map(usuarioInputModel, Usuario.class);
    }

    public void copyToDomainModel(UsuarioInputModel usuarioInputModel, Usuario usuario){
        mapper.map(usuarioInputModel, usuario);
    }
}
