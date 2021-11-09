package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.JornadaTrabalhoModel;
import com.dio.pontodeacesso.application.model.input.JornadaTrabalhoInputModel;
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
@Tag(name = "Jornada de Trabalho", description = "Jornada de trabalho do usuario do ponto de acesso")
@RequestMapping("/jornada")
public interface JornadaTrabalhoApi {


////////////// findAll

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Lista todas as jornada-trabalho",tags = {"jornada-trabalho"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = JornadaTrabalhoModel.class))))})
    List<JornadaTrabalhoModel> findAll();

    ////////////// findById

    @GetMapping("/{idJornada}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Procura uma jornada-trabalho pelo seu id",tags = {"jornada-trabalho"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = JornadaTrabalhoModel.class))),
            @ApiResponse(responseCode="404", description = "Jornada não encontrada")})
    JornadaTrabalhoModel findById(
            @Parameter(description = "Id de jornada-trabalho. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idJornada
    );

    ////////////// insert

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona uma nova jornada-trabalho",tags = {"jornada-trabalho"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = JornadaTrabalhoModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    JornadaTrabalhoModel create(
            @Parameter(description = "jornada-trabalho a ser adicionada",
                    required = true, schema = @Schema(implementation = JornadaTrabalhoInputModel.class))
            @Valid @RequestBody JornadaTrabalhoInputModel jornadaTrabalhoInputModel
    );

    ////////////// update

    @PutMapping("/{idJornada}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza uma jornada-trabalho",tags = {"jornada-trabalho"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = JornadaTrabalhoModel.class))),
            @ApiResponse(responseCode="404", description = "Jornada não encontrada"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    JornadaTrabalhoModel update(
            @Parameter(description = "Id de jornada-trabalho. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idJornada,
            @Parameter(description = "jornada-trabalho a ser atualizada",
                    required = true, schema = @Schema(implementation = JornadaTrabalhoInputModel.class))
            @Valid @RequestBody JornadaTrabalhoInputModel jornadaTrabalhoInputModel
    );

////////////// delete

    @DeleteMapping("/{idJornada}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma jornada-trabalho",tags = {"jornada-trabalho"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Jornada não encontrada")})
    void delete(
            @Parameter(description = "Id de jornada-trabalho. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idJornada
    );
}
