package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelAssembler {

    @Autowired
    ModelMapper mapper;

    public UsuarioModel toModel(Usuario usuario){
        return mapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> toCollectionModel(List<Usuario> usuarioList){
        return usuarioList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
