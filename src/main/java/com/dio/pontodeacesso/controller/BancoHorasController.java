package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.model.BancoHoras;
import com.dio.pontodeacesso.model.BancoHorasId;
import com.dio.pontodeacesso.model.JornadaTrabalho;
import com.dio.pontodeacesso.service.BancoHorasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Retorna dados como um JSON
@RequestMapping("/banco-horas") //Define um EndPoint para este controlador
public class BancoHorasController {

    @Autowired
    BancoHorasService bancoHorasService;

    @PostMapping
    public BancoHoras createBancoHoras(@RequestBody BancoHoras bancoHoras){
        return bancoHorasService.saveBancoHoras(bancoHoras);
    }

    @GetMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public Optional<BancoHoras> getBancoHorasById(@PathVariable("idBancoHoras") long idBancoHoras,
                                                  @PathVariable("idMovimento") long idMovimento,
                                                  @PathVariable("idUsuario") long idUsuario){
        return bancoHorasService.getById(new BancoHorasId(idBancoHoras,idMovimento,idUsuario));
    }

    @GetMapping
    public List<BancoHoras> getBancoHorasList(){
        return bancoHorasService.findAll();
    }

    @PutMapping
    public ResponseEntity<BancoHoras> updateBancoHoras(@RequestBody BancoHoras bancoHoras){
        try{
            BancoHorasId bancoHorasId = new BancoHorasId(
                    bancoHoras.getIdBancoHoras()
                    ,bancoHoras.getIdMovimento()
                    ,bancoHoras.getIdUsuario()
            );
            Optional<BancoHoras> bh = bancoHorasService.getById(bancoHorasId);
            if(bh.isPresent()){
                bancoHorasService.update(bancoHoras);
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @DeleteMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    public ResponseEntity<BancoHoras> deleteById(@PathVariable("idBancoHoras") long idBancoHoras,
                                                      @PathVariable("idMovimento") long idMovimento,
                                                      @PathVariable("idUsuario") long idUsuario){

        try {
            bancoHorasService.deleteBancoHoras(new BancoHorasId(idBancoHoras, idMovimento, idUsuario));
        }catch (Exception e){
            System.out.printf(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

}
