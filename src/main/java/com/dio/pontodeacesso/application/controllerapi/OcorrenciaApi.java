package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.OcorrenciaModel;
import com.dio.pontodeacesso.application.model.input.OcorrenciaInputModel;
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
@Tag(name = "Ocorrencia", description = "Tipo de ocorrencia do ponto de acesso")
@RequestMapping("/ocorrencia")
public interface OcorrenciaApi {

    ////////////// findAll

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Lista todos as ocorrencias",tags = {"ocorrencia"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = OcorrenciaModel.class))))})
    List<OcorrenciaModel> findAll();

    ////////////// findById

    @GetMapping("/{idOcorrencia}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Procura uma ocorrencia pelo seu id",tags = {"ocorrencia"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = OcorrenciaModel.class))),
            @ApiResponse(responseCode="404", description = "Ocorrencia não encontrada")})
    OcorrenciaModel findById(
            @Parameter(description = "Id da ocorrencia a ser buscada. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idOcorrencia
    );

    ////////////// insert

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona uma nova ocorrencia",tags = {"ocorrencia"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = OcorrenciaModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    OcorrenciaModel create(
            @Parameter(description = "ocorrencia a ser adicionada",
                    required = true, schema = @Schema(implementation = OcorrenciaInputModel.class))
            @Valid @RequestBody OcorrenciaInputModel ocorrenciaInputModel
    );

    ////////////// update

    @PutMapping("/{idOcorrencia}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza uma ocorrencia",tags = {"ocorrencia"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = OcorrenciaModel.class))),
            @ApiResponse(responseCode="404", description = "Ocorrencia não encontrada"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    OcorrenciaModel update(
            @Parameter(description = "Id da ocorrencia a ser atualizada. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idOcorrencia,
            @Parameter(description = "ocorrencia a ser atualizada",
                    required = true, schema = @Schema(implementation = OcorrenciaInputModel.class))
            @Valid @RequestBody OcorrenciaInputModel ocorrenciaInputModel
    );

    ////////////// delete

    @DeleteMapping("/{idOcorrencia}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma ocorrencia",tags = {"ocorrencia"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Ocorrencia não encontrada")})
    void delete(
            @Parameter(description = "Id da ocorrencia a ser excluída. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idOcorrencia
    );




}
