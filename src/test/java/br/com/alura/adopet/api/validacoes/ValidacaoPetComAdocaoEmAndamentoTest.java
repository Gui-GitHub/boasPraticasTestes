package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @Mock
    private AdocaoRepository adocaoRepository;

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacao;

    @Mock
    private SolicitacaoAdocaoDto dto;

    //validar()
    @Test
    void deveriaPermitirSolicitacaoDeAdocaoPet() {

        // ARRANGE
        given(adocaoRepository.existsByPetIdAndStatus(
                dto.idPet(),
                StatusAdocao.AGUARDANDO_AVALIACAO
        )).willReturn(false);

        //ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {

        // ARRANGE
        given(adocaoRepository.existsByPetIdAndStatus(
                dto.idPet(),
                StatusAdocao.AGUARDANDO_AVALIACAO
        )).willReturn(true);

        //ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}