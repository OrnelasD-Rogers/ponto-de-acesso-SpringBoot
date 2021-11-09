package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.BancoHorasModel;
import com.dio.pontodeacesso.application.model.input.BancoHorasInputModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Tag(name = "Banco Horas", description = "Banco de horas do usuario")
@RequestMapping("/banco-horas")
public interface BancoHorasApi {

    ////////////// findAll

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Lista todos os banco-horas",tags = {"banco-horas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = BancoHorasModel.class))))})
    List<BancoHorasModel> findAll();


    ////////////// findById

    @GetMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Procura um banco-horas pelo seu id",tags = {"banco-horas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = BancoHorasModel.class))),
            @ApiResponse(responseCode="404", description = "Banco-horas não encontrado")})
    BancoHorasModel findById(
            @Parameter(description = "Id do banco-horas que forma a chave composta a ser buscado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idBancoHoras,
            @Parameter(description = "Id do movimento que forma a chave composta a ser buscado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idMovimento,
            @Parameter(description = "Id do usuario que forma a chave composta a ser buscado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario);
    ////////////// insert

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona um novo banco-horas",tags = {"banco-horas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = BancoHorasModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    BancoHorasModel create(
            @Parameter(description = "banco-horas a ser adicionado",
                    required = true, schema = @Schema(implementation = BancoHorasInputModel.class))
            @Valid @RequestBody BancoHorasInputModel bancoHorasInputModel);

    ////////////// update

    @PutMapping("/{idBancoHoras}-{idMovimento}-{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um banco-horas",tags = {"banco-horas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = BancoHorasModel.class))),
            @ApiResponse(responseCode="404", description = "Banco-horas não encontrado"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    BancoHorasModel update(
            @Parameter(description = "Id do banco-horas que forma a chave composta a ser atualizado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idBancoHoras,
            @Parameter(description = "Id do movimento que forma a chave composta a ser atualizado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idMovimento,
            @Parameter(description = "Id do usuario que forma a chave composta a ser atualizado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario,
            @Parameter(description = "banco-horas a ser atualizado",
                    required = true, schema = @Schema(implementation = BancoHorasInputModel.class))
            @Valid @RequestBody BancoHorasInputModel bancoHorasInput);

    ////////////// delete

    @DeleteMapping("/{idTipoData}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um banco-horas",tags = {"banco-horas"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Banco-horas não encontrado")})
    void delete(
            @Parameter(description = "Id do banco-horas que forma a chave composta a ser excluído. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idBancoHoras,
            @Parameter(description = "Id do movimento que forma a chave composta a ser excluído. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idMovimento,
            @Parameter(description = "Id do usuario que forma a chave composta a ser excluído. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario);
}
