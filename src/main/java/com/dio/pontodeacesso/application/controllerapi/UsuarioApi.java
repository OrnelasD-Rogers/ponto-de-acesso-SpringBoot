package com.dio.pontodeacesso.application.controllerapi;

import com.dio.pontodeacesso.application.model.UsuarioModel;
import com.dio.pontodeacesso.application.model.input.UsuarioInputModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Tag(name = "Usuario", description = "Usuário do ponto de acesso")
public interface UsuarioApi {

    ////////////// findAll


    @Operation(summary = "Lista todos os usuários",tags = {"usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioModel.class))))})
    List<UsuarioModel> findAll();

    ////////////// findById


    @Operation(summary = "Procura um usuario pelo seu id",tags = {"usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Operação realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = UsuarioModel.class))),
            @ApiResponse(responseCode="404", description = "Usuário não encontrado")})
    UsuarioModel findById(
            @Parameter(description = "Id do usuário a ser buscado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario
    );

    ////////////// insert


    @Operation(summary = "Adiciona um novo usuario",tags = {"usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Inserção realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = UsuarioModel.class))),
            @ApiResponse(responseCode="400", description = "Entrada de dados inválida")})
    UsuarioModel create(@Parameter(description = "Usuario a ser adicionado",
            required = true, schema = @Schema(implementation = UsuarioInputModel.class))
                               @Valid @RequestBody UsuarioInputModel usuarioInputModel
    );

    ////////////// update


    @Operation(summary = "Atualiza um usuário",tags = {"usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description = "Atualização realizada com sucesso"
                    , content = @Content(schema = @Schema(implementation = UsuarioModel.class))),
            @ApiResponse(responseCode="404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode="400", description = "Dados inválidos")})
    UsuarioModel update(
            @Parameter(description = "Id do usuário a ser atualizado. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario,
            @Parameter(description = "Usuario a ser atualizado",
                    required = true, schema = @Schema(implementation = UsuarioInputModel.class))
            @Valid @RequestBody UsuarioInputModel usuarioInputModel
    );

    ////////////// delete


    @Operation(summary = "Exclui um usuário",tags = {"usuario"})
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode="404", description = "Usuário não encontrado")})
    void delete(
            @Parameter(description = "Id do usuário a ser excluído. Não pode ser nulo", required = true)
            @PathVariable @NotNull Long idUsuario
    );

}
