package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import model.Artigo;
import model.Conferencia;
import model.Periodico;
import model.QualisEnum;


/**
 * Classe de testes unitarios para a classe de modelo Artigo.
 */
public class ArtigoTest {
	Periodico spyPeriodico = spy(new Periodico("periodico", "03043975"));
	Artigo artigo = new Artigo("artigo 1", spyPeriodico, 2010);
	
	Conferencia spyConferencia = spy(new Conferencia("ACL - Annual Meeting of the Association for Computational Linguistics"));
	Artigo artigo2 = new Artigo("artigo 2", spyConferencia, 2010);
	

	@Test
	/**
	 * Testa se o método getQualis de artigo chama o método getQualis de Periodico
	 */
	void testArtigoGetQualisChamaPeriodico() {
		artigo.getQualis();
		verify(spyPeriodico).getQualis();
	}
	
	@Test
	/**
	 * Testa se um artigo de periodico desconhecido tem o qualis nulo.
	 */
	void testArtigoComPeriodicoDesconhecido() {
		assertEquals(null, artigo.getQualis());
	}
	
	@Test
	/**
	 * Testa se o método getQualis de artigo chama o método getQualis de conferencia
	 */
	void testArtigoGetQualisChamaConferencia() {
		artigo2.getQualis();
		verify(spyConferencia).getQualis();
	}
	
	@Test
	/**
	 * Testa se um artigo de conferencia conhecida obtem o qualis correto.
	 */
	void testArtigoComConferencia() {
		assertEquals(QualisEnum.A1, artigo2.getQualis());
	}
	
	
}
