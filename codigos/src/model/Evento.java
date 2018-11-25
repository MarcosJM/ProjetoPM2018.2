package model;

import controller.ConferenciaController;

public class Evento {
	
	private String nome;
	private int ano;
	private QualisEnum qualis; // Obtido atraves de consulta ao site Qualis ou de outra forma.
	
	public Evento(int ano, String nome) {
		this.ano = ano;
		this.nome = nome;
	}
	
	// Define o qualis do evento. Nulo se nao for encontrado.
	public void setQualis() {
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

	@Override
	public String toString() {
		return ano + "	" + qualis + "	" + nome;
	}

}
