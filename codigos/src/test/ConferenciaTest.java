package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import controller.ConferenciaController;
import model.Conferencia;
import model.QualisEnum;

public class ConferenciaTest {
	@Test
	void testGetClassificacaoCapes() {
		ConferenciaController controladorConferencia=new ConferenciaController();
		assertEquals("A1",controladorConferencia.getClassificacaoCapes("AAAI"));
	}
	
	@Test
	void testGetClassificacaoCapesErro() {
		ConferenciaController controladorConferencia=new ConferenciaController();
		assertEquals("",controladorConferencia.getClassificacaoCapes("-"));
	}	

}
