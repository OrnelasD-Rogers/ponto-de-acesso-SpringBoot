package com.dio.pontodeacesso.application.assembler;

import com.dio.pontodeacesso.application.model.input.JornadaTrabalhoInputModel;
import com.dio.pontodeacesso.domain.model.JornadaTrabalho;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JornadaTrabalhoDesassembler {

    @Autowired
    ModelMapper mapper;

    public JornadaTrabalho toDomainModel(JornadaTrabalhoInputModel jornadaTrabalhoInputModel){
        return mapper.map(jornadaTrabalhoInputModel, JornadaTrabalho.class);
    }

    public void copyToDomainModel(JornadaTrabalhoInputModel jornadaTrabalhoInputModel,
                                  JornadaTrabalho jornadaTrabalho){

        mapper.map(jornadaTrabalhoInputModel, jornadaTrabalho);
    }
}
