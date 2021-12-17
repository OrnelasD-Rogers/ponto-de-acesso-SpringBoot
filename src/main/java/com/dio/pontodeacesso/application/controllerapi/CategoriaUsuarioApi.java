package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.CategoriaUsuarioModel;
import com.dio.pontodeacesso.application.model.input.CategoriaUsuarioInputModel;
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

@Tag(name = "Categoria-usuario", description = "A categoria de usuário do ponto de acesso")
public interface CategoriaUsuarioApi {

    ////////////// findAll


    @Operation(summary = "Lista todas as categoria-usuario",tags = {"categoria-usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoriaUsuarioModel.class))))})
    List<CategoriaUsuarioModel> findAll();

    ////////////// findById


    @Operation(summary = "Procura uma categoria-usuario pelo seu id",tags = {"categoria-usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = CategoriaUsuarioModel.class))),
            @ApiResponse(responseCode="404", description = "Categoria-usuario não encontrada")})
    CategoriaUsuarioModel findById(
            @Parameter(description = "Id da categoria-usuario. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idCategoriaUsuario);

    ////////////// insert


    @Operation(summary = "Adiciona uma nova categoria-usuario",tags = {"categoria-usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = CategoriaUsuarioModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    CategoriaUsuarioModel create(
            @Parameter(description = "Categoria-usuario a ser adicionada",
                    required = true, schema = @Schema(implementation = CategoriaUsuarioInputModel.class))
            @Valid @RequestBody CategoriaUsuarioInputModel categoriaUsuarioInputModel);


    @Operation(summary = "Atualiza uma categoria-usuario",tags = {"categoria-usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = CategoriaUsuarioModel.class))),
            @ApiResponse(responseCode="404", description = "Categoria-usuario não encontrada"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    CategoriaUsuarioModel update(
            @Parameter(description = "Id da categoria-usuario. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idCategoriaUsuario,
            @Parameter(description = "Categoria-usuario a ser atualizada",
                    required = true, schema = @Schema(implementation = CategoriaUsuarioInputModel.class))
            @Valid @RequestBody CategoriaUsuarioInputModel categoriaUsuarioInputModel);


    @Operation(summary = "Exclui uma categoria-usuario",tags = {"categoria-usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Categoria-usuario não encontrada")})
    void delete(
            @Parameter(description = "Id da categoria-usuario. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idCategoriaUsuario);




}
