package controller;

import model.ArquivoCsv;


/**
 * Classe para leitura e pesquisa no arquivo com todos os
 * periodicos conhecidos.
 */
public class PeriodicosController {

	private static ArquivoCsv periodicos = new ArquivoCsv("../arquivos/dados/qualis_periodicos.csv", ";");;
	
	
	/**
	 * Encontra o qualis de um periodico por seu titulo.
	 * @param titulo - String, titulo do periodico que se deseja procurar.
	 * @return String, o qualis do periodico.
	 */
	public static String getClassificacaoCapesPorTitulo(String titulo) 
	{
		// Primeiro, pega o indice do elemento.
		int indiceLinha = periodicos.getLinhaElemento("Titulo", titulo, false);
		
		// Depois de checar se foi encontrado, pega o elemento da coluna QUALIS, que esta na mesma linha.
		if (indiceLinha != -1)
			return periodicos.getElemento("QUALIS", indiceLinha);
		return "";
	}
	
	
	/**
	 * Encontra o qualis de um periodico por seu ISSN.
	 * @param issn - String, ISSN do periodico que se deseja procurar.
	 * @return String, o qualis do periodico.
	 */
	public static String getClassificacaoCapesPorISSN(String issn) 
	{
		int indiceLinha = periodicos.getLinhaElemento("ISSN", issn, true);
		if (indiceLinha != -1)
			return periodicos.getElemento("QUALIS", indiceLinha);
		return "";	
	}
	
}
