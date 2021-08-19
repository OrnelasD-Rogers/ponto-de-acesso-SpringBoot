package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.Calendario;
import com.dio.pontodeacesso.model.Movimentacao;
import com.dio.pontodeacesso.model.MovimentacaoId;
import com.dio.pontodeacesso.model.Ocorrencia;
import com.dio.pontodeacesso.service.CalendarioService;
import com.dio.pontodeacesso.service.MovimentacaoService;
import com.dio.pontodeacesso.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    OcorrenciaService ocorrenciaService;
    CalendarioService calendarioService;
    MovimentacaoService movimentacaoService;

    @Autowired
    public MovimentacaoController(OcorrenciaService ocorrenciaService, CalendarioService calendarioService, MovimentacaoService movimentacaoService) {
        this.ocorrenciaService = ocorrenciaService;
        this.calendarioService = calendarioService;
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping
    public ResponseEntity<Movimentacao> create(@RequestBody Movimentacao movimentacao) {
        Optional<Ocorrencia> ocorrenciaOptional = ocorrenciaService.findById(movimentacao.getOcorrencia().getId_Ocorrencia());
        if (ocorrenciaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        movimentacao.setOcorrencia(ocorrenciaOptional.get());

        Optional<Calendario> calendarioOptional = calendarioService.getCalendarioById(movimentacao.getCalendario().getId_Calendario());
        if (calendarioOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        movimentacao.setCalendario(calendarioOptional.get());

        Movimentacao movimentacaoSalva = movimentacaoService.save(movimentacao);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(movimentacaoSalva.getId_Movimento()).toUri();

        return ResponseEntity.created(location).body(movimentacaoSalva);
    }

    @PutMapping
    public ResponseEntity<Movimentacao> update(@RequestBody Movimentacao movimentacao) {

        MovimentacaoId movimentacaoId = new MovimentacaoId(
          movimentacao.getId_Movimento(),
          movimentacao.getId_Usuario()
        );
        Optional<Movimentacao> movimentacaoOptional = movimentacaoService.findById(movimentacaoId);
        if (movimentacaoOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Ocorrencia> ocorrenciaOptional = ocorrenciaService.findById(movimentacao.getOcorrencia().getId_Ocorrencia());
        if (ocorrenciaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Calendario> calendarioOptional = calendarioService.getCalendarioById(movimentacao.getCalendario().getId_Calendario());
        if (calendarioOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        movimentacao.setOcorrencia(ocorrenciaOptional.get());
        movimentacao.setCalendario(calendarioOptional.get());
        movimentacaoService.save(movimentacao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idMovimento}-{idUsuario}")
    public ResponseEntity<Movimentacao> delete(@PathVariable Long idMovimento, @PathVariable Long idUsuario) {
        MovimentacaoId movimentacaoId = new MovimentacaoId(
                idMovimento,
                idUsuario
        );
        Optional<Movimentacao> optionalMovimentacao = movimentacaoService.findById(movimentacaoId);
        if (optionalMovimentacao.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        movimentacaoService.delete(movimentacaoId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Movimentacao>> findAll() {
        return ResponseEntity.ok(movimentacaoService.findAll());
    }

    @GetMapping("/{idMovimento}-{idUsuario}")
    public ResponseEntity<Movimentacao> getById(@PathVariable Long idMovimento, @PathVariable Long idUsuario) {
        MovimentacaoId movimentacaoId = new MovimentacaoId(
                idMovimento,
                idUsuario
        );
        Optional<Movimentacao> movimentacaoOptional = movimentacaoService.findById(movimentacaoId);
        if (movimentacaoOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(movimentacaoOptional.get());
    }
}
