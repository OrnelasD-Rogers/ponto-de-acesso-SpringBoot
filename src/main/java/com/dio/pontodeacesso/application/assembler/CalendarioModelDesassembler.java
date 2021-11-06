package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.CalendarioInputModel;
import com.dio.pontodeacesso.domain.model.Calendario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarioModelDesassembler {

    @Autowired
    ModelMapper mapper;

    public Calendario toDomainModel(CalendarioInputModel calendarioInputModel){
        return mapper.map(calendarioInputModel, Calendario.class);
    }

    public void copyToDomainModel(CalendarioInputModel calendarioInputModel, Calendario calendario){
        mapper.map(calendarioInputModel, calendario);
    }
}
