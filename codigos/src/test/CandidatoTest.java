package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import model.Candidato;

//
class CandidatoTest {
	Candidato candidato = new Candidato("..\\arquivos\\lattes\\0398675521406529.xml");

	//Testes de get Candidato
	@Test
	void testGetNome() {
		assertEquals("Vânia Maria Félix Dias", candidato.getNome());
	}

	@Test
	void testGetQuantidadePremios() {
		assertEquals(1, candidato.getPremios().size());
	}
	
	@Test
	void testGetArtigosCompletos() {
		assertEquals(0, candidato.getArtigosCompletosQualisRestrito().size());
	}
	
//	void testGetPontuacao() {
//		
//	}
	
	


}
