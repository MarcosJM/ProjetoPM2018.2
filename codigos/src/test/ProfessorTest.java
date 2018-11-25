package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import controller.ProfessorController;

public class ProfessorTest {
	
	//testa se a professora V�nia est� no excel de professores da Unirio
	@Test
	void testEhProfessorUnirioTrue() {
		ProfessorController controladorProfessor= new ProfessorController();
		assertEquals(true, controladorProfessor.ehProfessorUnirio("V�nia Maria F�lix Dias"));
	}
	
	//testa se a professora batata est� no excel de professores da Unirio
	@Test
	void testEhProfessorUnirioFalse() {
		ProfessorController controladorProfessor= new ProfessorController();
		assertEquals(false, controladorProfessor.ehProfessorUnirio("batata"));
	}
	
	
	

}
