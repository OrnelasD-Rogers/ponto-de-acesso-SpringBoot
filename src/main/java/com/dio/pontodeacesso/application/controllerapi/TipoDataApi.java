package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.TipoDataModel;
import com.dio.pontodeacesso.application.model.input.TipoDataInputModel;
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
@Tag(name = "Tipo Data", description = "Tipo de data do ponto de acesso")
@RequestMapping("/tipo-data")
public interface TipoDataApi {

    ////////////// findAll

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Lista todos os tipo-data",tags = {"tipo-data"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoDataModel.class))))})
    List<TipoDataModel> findAll();

    ////////////// findById

    @GetMapping("/{idTipoData}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Procura um tipo-data pelo seu id",tags = {"tipo-data"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = TipoDataModel.class))),
            @ApiResponse(responseCode="404", description = "tipo-data não encontrado")})
    TipoDataModel findById(
            @Parameter(description = "Id do tipo-data a ser buscado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idTipoData
    );

    ////////////// insert

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona um novo tipo-data",tags = {"tipo-data"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = TipoDataModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    TipoDataModel create(
            @Parameter(description = "tipo-data a ser adicionado",
            required = true, schema = @Schema(implementation = TipoDataInputModel.class))
            @Valid @RequestBody TipoDataInputModel tipoData
    );

    ////////////// update

    @PutMapping("/{idTipoData}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um tipo-data",tags = {"tipo-data"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = TipoDataModel.class))),
            @ApiResponse(responseCode="404", description = "tipo-data não encontrado"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    TipoDataModel update(
            @Parameter(description = "Id do tipo-data a ser atualizado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idTipoData,
            @Parameter(description = "tipo-data a ser atualizado",
                    required = true, schema = @Schema(implementation = TipoDataInputModel.class))
            @Valid @RequestBody TipoDataInputModel tipoDataInputModel
    );

    ////////////// delete

    @DeleteMapping("/{idTipoData}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um tipo-data",tags = {"tipo-data"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "tipo-data não encontrado")})
    void delete(
            @Parameter(description = "Id do tipo-data a ser excluído. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idTipoData
    );


}
