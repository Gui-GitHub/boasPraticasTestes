package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    private PetDto petDto;

    //listar()
    @Test
    void deveriaDevolverCodigoOk200ParaListarAbrigos() throws Exception {
        //ARRANGE

        //ACT
        var response = mvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    //cadastro()
    @Test
    void deveriaDevolverCodigoErro400ParaCadastroDeAbrigos() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigoOk200ParaCadastroDeAbrigos() throws Exception {
        //ARRANGE
        String json =  """
                {
                    "nome": "Abrigo xpto",
                    "telefone": "61977777777",
                    "email": "abrigoxpto@email.com.br"
                }
            """;

        //ACT
        var response = mvc.perform(
                post("/abrigos")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    //listarPets
    @Test
    void deveriaDevolverNotFoundQuandoAbrigoNaoEncontrado() throws Exception {
        // Arrange
        String idOuNome = "exemploIdOuNome";
        when(abrigoService.listarPetsDoAbrigo(idOuNome))
                .thenThrow(new ValidacaoException("Abrigo não encontrado"));

        // Act / Assert
        mvc.perform(MockMvcRequestBuilders.get("/{idOuNome}/pets", idOuNome)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}