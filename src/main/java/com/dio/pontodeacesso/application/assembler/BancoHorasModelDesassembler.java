package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.BancoHorasInputModel;
import com.dio.pontodeacesso.domain.model.BancoHoras;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BancoHorasModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public BancoHoras toDomainModel(BancoHorasInputModel bancoHorasInputModel){
        return mapper.map(bancoHorasInputModel, BancoHoras.class);
    }

    public void copyToDomainModel(BancoHorasInputModel bancoHorasInputModel, BancoHoras bancoHoras){
        mapper.map(bancoHorasInputModel, bancoHoras);
    }

}
