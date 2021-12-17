package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.LocalidadeModel;
import com.dio.pontodeacesso.application.model.input.LocalidadeInputModel;
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

@Tag(name = "Localidade", description = "Localidade do ponto de acesso")
public interface LocalidadeApi {

    ////////////// findAll


    @Operation(summary = "Lista todos as localidades",tags = {"localidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = LocalidadeModel.class))))})
    List<LocalidadeModel> findAll();

    ////////////// findById

    @Operation(summary = "Procura uma localidade pelo seu id",tags = {"localidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = LocalidadeModel.class))),
            @ApiResponse(responseCode="404", description = "Localidade não encontrada")})
    LocalidadeModel findById(
            @Parameter(description = "Id de localidade que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idLocalidade,
            @Parameter(description = "Id de nivelAcesso que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idNivelAcesso
    );

    ////////////// insert


    @Operation(summary = "Adiciona uma nova localidade",tags = {"localidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = LocalidadeModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    LocalidadeModel create(
            @Parameter(description = "localidade a ser adicionada",
                    required = true, schema = @Schema(implementation = LocalidadeInputModel.class))
            @Valid @RequestBody LocalidadeInputModel localidadeInputModel
    );

    ////////////// update


    @Operation(summary = "Atualiza uma movimentacao",tags = {"localidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = LocalidadeModel.class))),
            @ApiResponse(responseCode="404", description = "Localidade não encontrada"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    LocalidadeModel update(
            @Parameter(description = "Id de localidade que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idLocalidade,
            @Parameter(description = "Id de nivelAcesso que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idNivelAcesso,
            @Parameter(description = "localidade a ser atualizada",
                    required = true, schema = @Schema(implementation = LocalidadeInputModel.class))
            @Valid @RequestBody LocalidadeInputModel localidadeInputModel
    );

    ////////////// delete


    @Operation(summary = "Exclui uma localidade",tags = {"localidade"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Localidade não encontrada")})
    void delete(
            @Parameter(description = "Id de localidade que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idLocalidade,
            @Parameter(description = "Id de nivelAcesso que forma a chave composta da entidade. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idNivelAcesso
    );
}
