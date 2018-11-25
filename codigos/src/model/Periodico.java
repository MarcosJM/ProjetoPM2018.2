package model;

import controller.ConferenciaController;
import controller.PeriodicosController;

public class Periodico {
	
	public String nome;
	private String issn;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.

	public Periodico(String issn, String nome) {
		this.nome = nome;
		this.issn = issn;
	}
	
	// Define o qualis do periodico. Nulo se nao for encontrado.
	public void setQualis() {
		String qualisSt = PeriodicosController.getClassificacaoCapesPorTitulo(nome);
		if (qualisSt.equals("")) {
			qualis = null;
		} else {
			qualis = QualisEnum.valueOf(qualisSt);
		}
	}
	
	public QualisEnum getQualis() {
		return qualis;
	}

}
