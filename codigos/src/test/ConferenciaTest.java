package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import controller.ConferenciaController;
import model.Conferencia;

import static org.mockito.Mockito.*;
/**
 * Classe de testes unitarios para ConferenciaController.
 */
public class ConferenciaTest {
		
	
	@Test
	/**
	 * Testa se uma sigla conhecida obtem o qualis correto.
	 */
	void testConstructorConferenciaChamaGetQualis() {
		
	}
	
	@Test
	/**
	 * Testa se uma sigla conhecida obtem o qualis correto.
	 */
	void testGetClassificacaoCapesPorSigla() {
		assertEquals("A1", ConferenciaController.getInstancia().getClassificacaoCapesPorSigla("AAAI"));
	}
	
	@Test
	/**
	 * Testa se uma sigla desconhecida obtem um qualis vazio.
	 */
	void testGetClassificacaoCapesPorSiglaDesconhecida() {
		assertEquals("", ConferenciaController.getInstancia().getClassificacaoCapesPorSigla("-"));
	}	

}
