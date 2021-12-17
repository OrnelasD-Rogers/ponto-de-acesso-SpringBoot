package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.EmpresaModel;
import com.dio.pontodeacesso.application.model.input.EmpresaInputModel;
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

@Tag(name = "Empresa", description = "Empresa do ponto de acesso")
public interface EmpresaApi {

    ////////////// findAll


    @Operation(summary = "Lista todas as empresas",tags = {"empresa"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaModel.class))))})
    List<EmpresaModel> findAll();

    ////////////// findById


    @Operation(summary = "Procura uma empresa pelo seu id",tags = {"empresa"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = EmpresaModel.class))),
            @ApiResponse(responseCode="404", description = "Empresa não encontrada")})
    EmpresaModel findById(
            @Parameter(description = "Id da empresa. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idEmpresa);

    ////////////// insert


    @Operation(summary = "Adiciona uma nova empresa",tags = {"empresa"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = EmpresaModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    EmpresaModel create(
            @Parameter(description = "Empresa a ser adicionada",
                    required = true, schema = @Schema(implementation = EmpresaInputModel.class))
            @Valid @RequestBody EmpresaInputModel empresaInputModel
    );



    @Operation(summary = "Atualiza uma empresa",tags = {"empresa"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = EmpresaModel.class))),
            @ApiResponse(responseCode="404", description = "Empresa não encontrada"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    EmpresaModel update(
            @Parameter(description = "Id da empresa. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idEmpresa,
            @Parameter(description = "Empresa a ser atualizada",
                    required = true, schema = @Schema(implementation = EmpresaInputModel.class))
            @Valid @RequestBody EmpresaInputModel empresaInputModel
    );


    @Operation(summary = "Exclui uma empresa",tags = {"empresa"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Empresa não encontrada")})
    void delete(
            @Parameter(description = "Id da empresa. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idEmpresa);




}
