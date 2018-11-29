package model;

import controller.ConferenciaController;

/**
 * O Candidato pode conter uma serie de Artigos publicados, e cada
 * Artigo pode pertencer a uma Conferencia ou Periodico.
 */
public class Conferencia {
	
	public String nome;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.

	// Construtor.
	public Conferencia(String nome) {
		this.nome = nome;
		setQualis();
	}

	
	/**
	 *  Define o qualis da Conferencia. 
	 *  Nulo se nao for encontrado.
	 */
	private void setQualis() {
		String qualisSt = ConferenciaController.getClassificacaoCapesPorNome(nome);
		if (qualisSt.equals("")) {
			qualis = null;
		} else {
			try {
				qualis = QualisEnum.valueOf(qualisSt);
			} catch(Exception e) {
				// Nao era uma String esperada para o enum.
				qualis = null;
			}
		}
	}
	
	// Getter.
	public QualisEnum getQualis() {
		return qualis;
	}
}
