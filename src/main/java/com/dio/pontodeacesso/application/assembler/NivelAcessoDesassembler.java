package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.NivelAcessoInputModel;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NivelAcessoDesassembler {

    @Autowired
    ModelMapper mapper;

    public NivelAcesso toDomainModel(NivelAcessoInputModel nivelAcessoInputModel){
        return mapper.map(nivelAcessoInputModel, NivelAcesso.class);
    }

    public void copyToDomainModel(NivelAcessoInputModel nivelAcessoInputModel, NivelAcesso nivelAcesso){
        mapper.map(nivelAcessoInputModel, nivelAcesso);
    }
}
