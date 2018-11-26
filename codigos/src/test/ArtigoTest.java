package test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import model.Artigo;
import model.Conferencia;
import model.Periodico;

public class ArtigoTest {
	Artigo artigo = new Artigo("artigo 1", new Periodico("periodico", "03043975"), 2010);
	Conferencia conferencia=new Conferencia("ACL - Annual Meeting of the Association for Computational Linguistics");
	Artigo artigo2 = new Artigo("artigo 2", conferencia, 2010);
	

	@Test
	void testArtigoComPeriodico() {
		assertEquals(null, artigo.getQualis());
	}
	
	@Test
	void testArtigoSemPeriodico() {
		assertEquals("A1", artigo2.getQualis());
	}
	
	
}
