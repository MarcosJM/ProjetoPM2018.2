package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import model.Candidato;

class CandidatoTest {

	@Test
	void testGetNome() {
		Candidato candidato = new Candidato("..\\arquivos\\lattes\\0398675521406529.xml");
		assertEquals("Vânia Maria Félix Dias", candidato.getNome());
	}

	@Test
	void testGetQuantidadePremios() {
		Candidato candidato = new Candidato("..\\arquivos\\lattes\\0398675521406529.xml");
		assertEquals(1, candidato.getQuantidadePremios());
	}

}
