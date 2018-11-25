package model;

import controller.ConferenciaController;

public class Conferencia {
	
	public String nome;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.

	public Conferencia(String nome) {
		this.nome = nome;
		setQualis();
	}

	// Define o qualis da conferencia. Nulo se nao for encontrado.
	private void setQualis() {
		String qualisSt = ConferenciaController.getClassificacaoCapesPorNome(nome);
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
