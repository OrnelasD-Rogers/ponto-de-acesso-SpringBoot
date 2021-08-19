package com.dio.pontodeacesso.controller;


import com.dio.pontodeacesso.model.Calendario;
import com.dio.pontodeacesso.model.TipoData;
import com.dio.pontodeacesso.service.CalendarioService;
import com.dio.pontodeacesso.service.TipoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calendario")
public class CalendarioController {

    @Autowired
    CalendarioService calendarioService;
    @Autowired
    TipoDataService tipoDataService;

    @PostMapping
    public ResponseEntity<Calendario> createCalendario(@RequestBody Calendario calendario){
        Optional<TipoData> optionalTipoData = tipoDataService.getTipoDataById(calendario.getTipoData().getId_tipoData());
        if (optionalTipoData.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        calendario.setTipoData(optionalTipoData.get());
        Calendario calendarioSalvo = calendarioService.save(calendario);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(calendarioSalvo.getId_Calendario()).toUri();

        return ResponseEntity.created(location).body(calendarioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Calendario>> getCalendarioList(){
        return ResponseEntity.ok(calendarioService.getCalendarioList());
    }

    @GetMapping("/{idCalendario}")
    public ResponseEntity<Calendario> getCalendarioById(@PathVariable Long idCalendario)throws Exception{
        Optional<Calendario> optionalCalendario = calendarioService.getCalendarioById(idCalendario);
        if (optionalCalendario.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalCalendario.get());
    }

    @PutMapping
    public ResponseEntity<Calendario> updateCalendario(@RequestBody Calendario calendario){
            Optional<TipoData> OptionalTipoData = tipoDataService.getTipoDataById(calendario.getTipoData().getId_tipoData());
            if (OptionalTipoData.isEmpty()) {
                return ResponseEntity.unprocessableEntity().build();
            }

            Optional<Calendario> Optionalcalendario = calendarioService.getCalendarioById(calendario.getId_Calendario());
            if (Optionalcalendario.isEmpty()) {
                return ResponseEntity.unprocessableEntity().build();
            }

            calendario.setTipoData(Optionalcalendario.get().getTipoData());
            calendario.setId_Calendario(Optionalcalendario.get().getId_Calendario());
            calendarioService.save(calendario);

            return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Calendario> deleteCalendario(@PathVariable Long id){

        Optional<Calendario> optionalCalendario = calendarioService.getCalendarioById(id);
        if (optionalCalendario.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        calendarioService.deleteCalendario(optionalCalendario.get().getId_Calendario());

        return ResponseEntity.noContent().build();
    }
}
