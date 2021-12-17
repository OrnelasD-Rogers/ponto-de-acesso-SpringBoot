package com.dio.pontodeacesso.controller;

import com.dio.pontodeacesso.application.assembler.CalendarioModelAssembler;
import com.dio.pontodeacesso.application.assembler.CalendarioModelDesassembler;
import com.dio.pontodeacesso.application.model.CalendarioModel;
import com.dio.pontodeacesso.application.model.input.CalendarioInputModel;
import com.dio.pontodeacesso.domain.exception.CalendarioNaoEncontradoException;
import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.model.Calendario;
import com.dio.pontodeacesso.domain.service.CalendarioService;
import com.dio.pontodeacesso.util.CalendarioCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.dio.pontodeacesso.application.exceptionhandler.ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CalendarioController.class)
public class CalendarioControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CalendarioService calendarioService;
    @MockBean
    private CalendarioModelAssembler assembler;
    @MockBean
    private CalendarioModelDesassembler desassembler;

    private final Long ID_CALENDARIO = 1L;
    private final String URL = "/calendario";
    private final String PATH = "/{idCalendario}";

    @Test
    void findAll_Returns200AndCalendarioList_WhenValidUrl() throws Exception{

        List<Calendario> calendarioList = List.of(CalendarioCreator.createValidCalendario());

        List<CalendarioModel> calendarioModelList = List.of(CalendarioCreator.createValidCalendarioModel());

        when(calendarioService.findAll()).thenReturn(calendarioList);

        when(assembler.toCollectionModel(any())).thenReturn(calendarioModelList);

        MvcResult mvcResult = mockMvc.perform(get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(calendarioModelList);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns200AndCalendario_WhenSuccessful() throws Exception {

        Calendario calendario = CalendarioCreator.createValidCalendario();

        CalendarioModel calendarioModel = CalendarioCreator.createValidCalendarioModel();

        when(calendarioService.findById(anyLong())).thenReturn(calendario);

        when(assembler.toModel(any())).thenReturn(calendarioModel);

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_CALENDARIO))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = objectMapper.writeValueAsString(calendarioModel);

        assertThat(actualResponseBody)
                .isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void findById_Returns400AndErrorMessage_WhenIdCalendarioIsNotALongType() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, "x"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).containsIgnoringCase(MSG_ERRO_GENERICA_USUARIO_FINAL);
    }

    @Test
    void findById_Returns404AndProblemDetails_WhenCalendarioIsNotFound() throws Exception {

        when(calendarioService.findById(anyLong()))
                .thenThrow(new CalendarioNaoEncontradoException(ID_CALENDARIO));

        MvcResult mvcResult = mockMvc.perform(get(URL + PATH, ID_CALENDARIO))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponseBody = String.format("NÃ£o foi encotrado um CalendÃ¡rio com o id %d", ID_CALENDARIO);

        assertThat(actualResponseBody)
                .containsIgnoringCase(expectedResponseBody);
    }

    @Test
    void create_Returns201AndCalendario_WhenSuccessful() throws Exception {

        Calendario toBeSavedCalendario = CalendarioCreator.createValidCalendario();

        Calendario savedCalendario = CalendarioCreator.createSavedCalendario();

        CalendarioModel calendarioModel = CalendarioCreator.createValidCalendarioModel();

        CalendarioInputModel calendarioInputModel = CalendarioCreator.createValidCalendarioInputModel();

        when(calendarioService.save(any(Calendario.class))).thenReturn(savedCalendario);

        when(desassembler.toDomainModel(any())).thenReturn(toBeSavedCalendario);

        when(assembler.toModel(any())).thenReturn(calendarioModel);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calendarioInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(calendarioModel);

        assertThat(actualResponseBody).containsIgnoringCase(expectedResponse);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenTipoDataIsNull() throws Exception {

        CalendarioInputModel calendarioInputModel = CalendarioCreator.createInvalidTipoDataInCalendarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calendarioInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"tipoData\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenIdTipoDataIsNull() throws Exception {

        CalendarioInputModel calendarioInputModel = CalendarioCreator.createInvalidIdTipoDataInCalendarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calendarioInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"tipoData.id_tipoData\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void create_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception {

        CalendarioInputModel calendarioInputModel = CalendarioCreator.createInvalidDescricaoInCalendarioInputModel();

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calendarioInputModel)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns200AndCalendarioModel_WhenSuccessful() throws Exception{

        CalendarioInputModel toBeUpdatedCalendarioInputModel = CalendarioCreator.createValidCalendarioInputModel();
        toBeUpdatedCalendarioInputModel.setDescricao("Atualizado");

        Calendario updatedCalendario = CalendarioCreator.createSavedCalendario();
        updatedCalendario.setDescricao("Atualizado");

        Calendario savedCalendario = CalendarioCreator.createSavedCalendario();

        CalendarioModel updatedCalendarioModel = CalendarioCreator.createValidCalendarioModel();

        when(calendarioService.findById(anyLong())).thenReturn(savedCalendario);

        when(calendarioService.save(savedCalendario)).thenReturn(updatedCalendario);

        when(assembler.toModel(any())).thenReturn(updatedCalendarioModel);

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CALENDARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedCalendarioInputModel)))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedResponse = objectMapper.writeValueAsString(updatedCalendarioModel);

        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @Test
    void update_Returns404AndProblemDetails_WhenCalendarioIsNotFound() throws Exception{

        CalendarioInputModel toBeUpdatedCalendarioInputModel = CalendarioCreator.createValidCalendarioInputModel();
        toBeUpdatedCalendarioInputModel.setDescricao("Atualizado");

        when(calendarioService.findById(anyLong()))
                .thenThrow(new CalendarioNaoEncontradoException(ID_CALENDARIO));


        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CALENDARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toBeUpdatedCalendarioInputModel)))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedError = String.format("NÃ£o foi encotrado um CalendÃ¡rio com o id %d", ID_CALENDARIO);

        assertThat(actualResponse).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenDescricaoIsNull() throws Exception{

        CalendarioInputModel invalidCalendarioInput = CalendarioCreator.createInvalidDescricaoInCalendarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CALENDARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCalendarioInput)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();

        String expectedErrors = "[{\"nome\":\"descricao\",\"mensagemUsuario\":\"must not be blank\"}]";

        assertThat(actualResponse).containsIgnoringCase(expectedErrors);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenTipoDataIsNull() throws Exception {

        CalendarioInputModel calendarioInputModel = CalendarioCreator.createInvalidTipoDataInCalendarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CALENDARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calendarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"tipoData\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void update_Returns400AndProblemDetails_WhenIdTipoDataIsNull() throws Exception {

        CalendarioInputModel calendarioInputModel = CalendarioCreator.createInvalidIdTipoDataInCalendarioInputModel();

        MvcResult mvcResult = mockMvc.perform(put(URL + PATH, ID_CALENDARIO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(calendarioInputModel)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedError = "[{\"nome\":\"tipoData.id_tipoData\",\"mensagemUsuario\":\"must not be null\"}]";

        assertThat(actualResponseBody).containsIgnoringCase(expectedError);
    }

    @Test
    void delete_Returns204_WhenSuccessful() throws Exception{

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_CALENDARIO))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void delete_Returns404AndProblemDetails_WhenCalendarioIsNotFound() throws Exception{

        doThrow(new CalendarioNaoEncontradoException(ID_CALENDARIO))
                .when(calendarioService).delete(ID_CALENDARIO);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_CALENDARIO))
                .andExpect(status().isNotFound())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("NÃ£o foi encotrado um CalendÃ¡rio com o id %d", ID_CALENDARIO);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

    @Test
    void delete_Returns409AndProblemDetails_WhenCalendarioIsInUse() throws Exception {

        doThrow(new EntidadeEmUsoException(String.format("O calendario com o código %d não pode ser removido pois está em uso", ID_CALENDARIO)))
                .when(calendarioService).delete(ID_CALENDARIO);

        MvcResult mvcResult = mockMvc.perform(delete(URL + PATH, ID_CALENDARIO))
                .andExpect(status().isConflict())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        String expectedDetails = String.format("O calendario com o cÃ³digo %d nÃ£o pode ser removido pois estÃ¡ em uso", ID_CALENDARIO);

        assertThat(actualResponseBody).containsIgnoringCase(expectedDetails);
    }

}
