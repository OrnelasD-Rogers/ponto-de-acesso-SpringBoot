package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.BancoHorasModel;
import com.dio.pontodeacesso.domain.model.BancoHoras;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BancoHorasModelAssembler {

    @Autowired
    ModelMapper mapper;

    public BancoHorasModel toModel(BancoHoras bancoHoras){
        return mapper.map(bancoHoras, BancoHorasModel.class);

    }

    public List<BancoHorasModel> toCollectionModel(List<BancoHoras> bancoHorasList){
        return bancoHorasList.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
