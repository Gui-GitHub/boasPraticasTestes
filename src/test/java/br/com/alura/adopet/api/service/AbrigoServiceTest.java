package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private Abrigo abrigo;

    @Mock
    private PetRepository petRepository;

    //listar()
    @Test
    void deveriaChamarListaDeTodosAbrigos(){
        //ACT
        abrigoService.listar();

        //ASSERT
        then(abrigoRepository).should().findAll();
    }

    //listarPetsDoAbrigo()
    @Test
    void deveriaChamarPetsDoAbrigoAtravesDeNome(){
        //ARRANGE
        String nome = "Miau";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //ACT
        abrigoService.listarPetsDoAbrigo(nome);

        //ASSERT
        then(petRepository).should().findByAbrigo(abrigo);
    }
}