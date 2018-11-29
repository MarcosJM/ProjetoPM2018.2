package model;

import controller.PeriodicosController;

/**
 * O Candidato pode conter uma serie de Artigos publicados, e cada
 * Artigo pode pertencer a uma Conferencia ou Periodico.
 */
public class Periodico {
	
	public String nome;
	private String issn;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.

	// Construtor.
	public Periodico(String issn, String nome) {
		this.nome = nome;
		this.issn = issn;
		setQualis();
	}
	
	
	/**
	 *  Define o qualis do Periodico. 
	 *  Nulo se nao for encontrado.
	 */
	private void setQualis() {
		String qualisSt = "";
		
		// Caso tenha ISSN dah preferencia a buscar por ele, uma vez que eh uma busca exata.
		if (issn != null & !issn.equals("")) {
			// Padroniza o ISSN do Periodico de acordo com o esperado pelo arquivo CSV.
			issn = issn.replaceAll("-", "");
			
			if (issn.length() == 8) {
				// ISSN tem o formato XXXX-XXXX e por isso eh dividido em duas substrings de tamanho 4:
				issn = issn.substring(0, 4) + "-" + issn.substring(4, issn.length());
				qualisSt = PeriodicosController.getClassificacaoCapesPorISSN(issn);
			} else {
				qualisSt = PeriodicosController.getClassificacaoCapesPorTitulo(nome);	
			}
			
		} else {
			qualisSt = PeriodicosController.getClassificacaoCapesPorTitulo(nome);	
		}
		
		
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
