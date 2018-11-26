package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Candidato;

/**
 * Classe de testes unitarios para a classe de modelo Candidato.
 */
class CandidatoTest {
	Candidato candidato = new Candidato("..\\arquivos\\lattes\\0398675521406529.xml", 8);

	// Testes de get em Candidato.
	@Test
	/**
	 * Testa para ver se obtem o nome correto.
	 */
	void testGetNome() {
		assertEquals("Vânia Maria Félix Dias", candidato.getNome());
	}

	@Test
	/**
	 * Testa para ver se obtem a quantidade certa de premios.
	 */
	void testGetQuantidadePremios() {
		assertEquals(1, candidato.getPremios().size());
	}
	
	@Test
	/**
	 * Testa para ver se obtem a quantidade correta de artigos A1, A2, B1.
	 */
	void testGetArtigosCompletos() {
		assertEquals(0, candidato.getArtigosCompletosQualisRestrito().size());
	}
	
//	void testGetPontuacao() {
//		
//	}
	
	


}
