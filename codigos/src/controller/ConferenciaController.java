package controller;

import model.ArquivoCsv;

public class ConferenciaController {

	private static ArquivoCsv conferencias = new ArquivoCsv("../../../arquivos/dados/qualis_conferencia.csv", ",");;
	
	/**
	 * Encontra o qualis de uma conferencia por sua sigla.
	 * @param sigla - String, sigla da conferencia que se deseja procurar.
	 * @return String, o qualis da conferencia.
	 */
	public static String getClassificacaoCapesPorSigla(String sigla) 
	{
		// Primeiro, pega o indice do elemento.
		int indiceLinha = conferencias.getLinhaElemento("SIGLA", sigla);
		
		// Depois de checar se foi encontrado, pega o elemento da coluna QUALIS, que esta na mesma linha.
		if (indiceLinha != -1)
			return conferencias.getElemento("QUALIS", indiceLinha);
		return "";
	}
	
	/**
	 * Encontra o qualis de uma conferencia por seu nome.
	 * @param nome - String, nome da conferencia que se deseja procurar.
	 * @return String, o qualis da conferencia.
	 */
	public static String getClassificacaoCapesPorNome(String nome) {
		// Primeiro, pega o indice do elemento.
		int indiceLinha = conferencias.getLinhaElemento("NOME DA CONFERENCIA", nome);
		
		// Depois de checar se foi encontrado, pega o elemento da coluna QUALIS, que esta na mesma linha.
		if (indiceLinha != -1)
			return conferencias.getElemento("QUALIS", indiceLinha);
		return "";
		
	}
	
}
