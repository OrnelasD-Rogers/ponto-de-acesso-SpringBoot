package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.NivelAcessoModel;
import com.dio.pontodeacesso.application.model.input.NivelAcessoInputModel;
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

@Tag(name = "Nivel Acesso", description = "Nível de acesso do ponto")
public interface NivelAcessoApi {

    ////////////// findAll


    @Operation(summary = "Lista todos os níveis de acesso",tags = {"nivel-acesso"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = NivelAcessoModel.class))))})
    List<NivelAcessoModel> findAll();

    ////////////// findById


    @Operation(summary = "Procura um nível de acesso pelo seu id",tags = {"nivel-acesso"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = NivelAcessoModel.class))),
            @ApiResponse(responseCode="404", description = "nivel-acesso não encontrado")})
    NivelAcessoModel findById(
            @Parameter(description = "Id do nivel-acesso a ser buscado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idNivelAcesso
    );

    ////////////// insert


    @Operation(summary = "Adiciona um novo nível de acesso",tags = {"nivel-acesso"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = NivelAcessoModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    NivelAcessoModel create(
            @Parameter(description = "nivel-acesso a ser adicionado",
                    required = true, schema = @Schema(implementation = NivelAcessoInputModel.class))
            @Valid @RequestBody NivelAcessoInputModel nivelAcessoInputModel
    );

    ////////////// update


    @Operation(summary = "Atualiza um nível de acesso",tags = {"nivel-acesso"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = NivelAcessoModel.class))),
            @ApiResponse(responseCode="404", description = "nivel-acesso não encontrado"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    NivelAcessoModel update(
            @Parameter(description = "Id do nivel-acesso a ser atualizado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idNivelAcesso,
            @Parameter(description = "nivel-acesso a ser atualizado",
                    required = true, schema = @Schema(implementation = NivelAcessoInputModel.class))
            @Valid @RequestBody NivelAcessoInputModel nivelAcessoInputModel
    );

    ////////////// delete


    @Operation(summary = "Exclui um nível de acesso",tags = {"nivel-acesso"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "nivel-acesso não encontrado")})
    void delete(
            @Parameter(description = "Id do nivel-acesso a ser excluído. Não pode ser nulo", required = true)
            @PathVariable Long idNivelAcesso
    );
}
