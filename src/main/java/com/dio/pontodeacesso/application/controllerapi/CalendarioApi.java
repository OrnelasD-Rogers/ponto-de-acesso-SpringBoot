package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.CalendarioModel;
import com.dio.pontodeacesso.application.model.input.CalendarioInputModel;
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
import java.util.List;

@Tag(name = "Calendario", description = "Um tipo de calendario")
public interface CalendarioApi {

    ////////////// findAll


    @Operation(summary = "Lista todos os calendario",tags = {"calendario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = CalendarioModel.class))))})
    List<CalendarioModel> findAll();

    ////////////// findById


    @Operation(summary = "Procura um calendario pelo seu id",tags = {"calendario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = CalendarioModel.class))),
            @ApiResponse(responseCode="404", description = "Calendario não encontrado")})
    CalendarioModel findById(
            @Parameter(description = "Id do calendario a ser buscado. Não pode ser nulo", required = true)
            @PathVariable Long idCalendario);

    ////////////// insert


    @Operation(summary = "Adiciona um novo calendario",tags = {"calendario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = CalendarioModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    CalendarioModel create(
            @Parameter(description = "calendario a ser adicionado",
                    required = true, schema = @Schema(implementation = CalendarioInputModel.class))
            @Valid @RequestBody CalendarioInputModel calendarioInput);

    ////////////// update


    @Operation(summary = "Atualiza um calendario",tags = {"calendario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = CalendarioModel.class))),
            @ApiResponse(responseCode="404", description = "Calendario não encontrado"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    CalendarioModel update(
            @Parameter(description = "Id do calendario a ser atualizado. Não pode ser nulo", required = true)
            @PathVariable Long idCalendario,
            @Parameter(description = "calendario a ser atualizado",
                    required = true, schema = @Schema(implementation = CalendarioInputModel.class))
            @Valid @RequestBody CalendarioInputModel calendarioInput);

    ////////////// delete


    @Operation(summary = "Exclui um calendario",tags = {"calendario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Calendario não encontrado")})
    void delete(
            @Parameter(description = "Id do calendario a ser excluído. Não pode ser nulo", required = true)
            @PathVariable Long idCalendario);
}
