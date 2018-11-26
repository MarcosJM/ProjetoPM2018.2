package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import controller.ProfessorController;

/**
 * Classe de testes unitarios para ProfessorController.
 */
public class ProfessorTest {
	
	@Test
	/**
	 * Testa se a professora Vania esta no excel de professores da UNIRIO.
	 */
	void testEhProfessorUnirioTrue() {
		assertEquals(true, ProfessorController.ehProfessorUnirio("V�nia Maria F�lix Dias"));
	}
	
	@Test
	/**
	 * Testa se a professora 'batata' esta no excel de professores da UNIRIO.
	 */
	void testEhProfessorUnirioFalse() {
		assertEquals(false, ProfessorController.ehProfessorUnirio("batata"));
	}

}
