package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import controller.ProfessorController;

public class ProfessorTest {
	
	//testa se a professora Vânia está no excel de professores da Unirio
	@Test
	void testEhProfessorUnirioTrue() {
		ProfessorController controladorProfessor= new ProfessorController();
		assertEquals(true, controladorProfessor.ehProfessorUnirio("Vânia Maria Félix Dias"));
	}
	
	//testa se a professora batata está no excel de professores da Unirio
	@Test
	void testEhProfessorUnirioFalse() {
		ProfessorController controladorProfessor= new ProfessorController();
		assertEquals(false, controladorProfessor.ehProfessorUnirio("batata"));
	}
	
	
	

}
