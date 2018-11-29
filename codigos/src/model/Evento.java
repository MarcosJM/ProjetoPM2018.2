package model;

import controller.ConferenciaController;

/**
 * O Candidato pode ter participado em uma serie de Eventos, que
 * na pratica sao como Conferencia.
 */
public class Evento {
	
	private String nome;
	private int ano;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.
	
	// Construtor.
	public Evento(int ano, String nome) {
		this.ano = ano;
		this.nome = nome;
		setQualis();
	}
	
	
	/**
	 *  Define o qualis do Evento. 
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

	@Override
	public String toString() {
		return ano + "	" + qualis + "	" + nome;
	}

}
