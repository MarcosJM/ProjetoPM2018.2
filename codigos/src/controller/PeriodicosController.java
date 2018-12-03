package controller;

import model.ArquivoCsv;
import utils.Constantes;


/**
 * Classe para leitura e pesquisa no arquivo com todos os
 * periodicos conhecidos.
 */
public class PeriodicosController {

	private ArquivoCsv periodicos = new ArquivoCsv(Constantes.ENDERECO_ARQUIVO_PERIODICOS, ";");
	
	/**
	 * Implementacao do Singleton.
	 */
	
	private static PeriodicosController unicaInstancia;
	
	private PeriodicosController() {}
	
	public static PeriodicosController getInstancia() {
		
		if (unicaInstancia == null) {
			unicaInstancia = new PeriodicosController();
		} 
		
		return unicaInstancia;
    }
	
	/**
	 * Encontra o qualis de um periodico por seu titulo.
	 * @param titulo - String, titulo do periodico que se deseja procurar.
	 * @return String, o qualis do periodico.
	 */
	public String getClassificacaoCapesPorTitulo(String titulo) 
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
	public String getClassificacaoCapesPorISSN(String issn) 
	{
		int indiceLinha = periodicos.getLinhaElemento("ISSN", issn, true);
		if (indiceLinha != -1)
			return periodicos.getElemento("QUALIS", indiceLinha);
		return "";	
	}
	
}
