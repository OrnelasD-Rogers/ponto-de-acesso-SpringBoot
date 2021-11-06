package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.NivelAcessoModel;
import com.dio.pontodeacesso.domain.model.NivelAcesso;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NivelAcessoAssembler {

    @Autowired
    ModelMapper mapper;

    public NivelAcessoModel toModel(NivelAcesso nivelAcesso){
        return mapper.map(nivelAcesso, NivelAcessoModel.class);
    }

    public List<NivelAcessoModel> toCollectionModel(List<NivelAcesso> nivelAcessoList){
        return nivelAcessoList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
