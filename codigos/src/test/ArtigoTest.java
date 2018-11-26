package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import model.Artigo;
import model.Conferencia;
import model.Periodico;

/**
 * Classe de testes unitarios para a classe de modelo Artigo.
 */
public class ArtigoTest {
	Artigo artigo = new Artigo("artigo 1", new Periodico("periodico", "03043975"), 2010);
	Conferencia conferencia = new Conferencia("ACL - Annual Meeting of the Association for Computational Linguistics");
	Artigo artigo2 = new Artigo("artigo 2", conferencia, 2010);
	

	@Test
	/**
	 * Testa se um artigo de periodico desconhecido tem o qualis nulo.
	 */
	void testArtigoComPeriodicoDesconhecido() {
		assertEquals(null, artigo.getQualis());
	}
	
	@Test
	/**
	 * Testa se um artigo de conferencia conhecida obtem o qualis correto.
	 */
	void testArtigoComConferencia() {
		assertEquals("A1", artigo2.getQualis());
	}
	
	
}
