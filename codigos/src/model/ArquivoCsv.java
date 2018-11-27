package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import info.debatty.java.stringsimilarity.JaroWinkler;
import utils.Constantes;
import utils.CsvUtils;

/**
 *  Classe que realiza o processamento e busca em arquivos CSV.
 */
public class ArquivoCsv {
	private ArrayList<String[]> dadosCsv = new ArrayList<String[]>();
	private int quantidadeLinhas;
	private int quantidadeColunas;
	
	// Biblioteca para pesquisa de String por similaridade.
	// Sao usados dois metodos para maior exatidao.
	// Documentacao em https://github.com/tdebatty/java-string-similarity/blob/master/README.md
	private static JaroWinkler jw = new JaroWinkler();
	private static info.debatty.java.stringsimilarity.MetricLCS lcs2 = 
            new info.debatty.java.stringsimilarity.MetricLCS();
	
	
	/**
	 * Construtor - Cria uma instancia de um arquivo CSV.
	 * @param endereco - String que indica o caminho ate o arquivo
	 * @param separador - String que indica o tipo de separador utilizado no arquivo csv
	 */
	public ArquivoCsv(String endereco, String separador)
	{
		this.dadosCsv = CsvUtils.lerCsv(endereco, separador);
		this.quantidadeLinhas = dadosCsv.size();
		this.quantidadeColunas = dadosCsv.get(0).length;
	}

	
	/**
	 * Encontra um elemento dado o nome da coluna e a linha na qual esta.
	 * @param nomeColuna - String, nome da coluna conforme no CSV.
	 * @param locLinha - int, inteiro positivo maior ou igual a zero, linha 
	 * do elemento.
	 * @return String, conteudo da celula com o elemento.
	 */
	public String getElemento(String nomeColuna, int locLinha)
	// TODO: padronizar os nomes loc, indice e posicao pois se referem a mesma coisa
	{
		if (locLinha < this.quantidadeLinhas && locLinha >= 0)
		{
			String[] linha = dadosCsv.get(locLinha);
			int indiceColuna = this.getPosicaoColuna(nomeColuna);
			if (indiceColuna != -1)
			{
				return linha[indiceColuna];
			}	
		} else {
			System.err.println("O endereco da linha esta fora dos limites.");
		}
		return "";
	}
	
	
	/**
	 * Retorna o elemento contido na localizacao indicada.
	 * @param locLinha - int, indice da linha.
	 * @param locColuna - int, indice da coluna.
	 * @return String, o elemento encontrado, ou "".
	 */
	public String getElemento(int locLinha, int locColuna)
	{
		if (locLinha < this.quantidadeLinhas)
		{
			String[] linha = dadosCsv.get(locLinha);
			
			if (locColuna < this.quantidadeColunas)
			{
				return linha[locColuna];
			}else {
				System.err.println("O endereco da coluna esta fora dos limites.");
			}
		}else {
			System.err.println("O endereco da linha esta fora dos limites.");
		}
		return "";	
	}
	
	
	
	/**
	 * Recebe o nome de uma coluna e retorna a sua posicao no array.
	 * @param nomeColuna - String, nome da coluna desejada.
	 * @return int que eh a posicao da coluna desejada, ou -1 se nada for encontrado.
	 */
	private int getPosicaoColuna(String nomeColuna)
	{
		String[] colunas = this.dadosCsv.get(0);
		for (int iterator = 0; iterator < this.quantidadeColunas; iterator ++)
		{
			if (nomeColuna.equals(colunas[iterator]))
			{
				return iterator;
			}
		}
		return -1;
	}
	
	
	/**
	 * Retorna o indice da linha de um elemento dado o proprio e o nome de sua coluna.
	 * @param nomeColuna - String, nome da coluna desejada.
	 * @param elemento - String, elemento cujo indice deseja-se saber.
	 * @return int correspondente ao indice da linha do elemento, ou -1 se o indice nao for encontrado.
	 */
	public int getLinhaElemento(String nomeColuna, String elemento, boolean exato)
	{
		int posicaoColuna = this.getPosicaoColuna(nomeColuna);
		if (posicaoColuna != -1)
		{
			elemento = elemento.toUpperCase();
			if (exato) {
				
				return procuraMatchExato(posicaoColuna, elemento);
			} else {
				return procuraMatchNaoExato(posicaoColuna, elemento);
			}
			
			// TODO: mensagem para informar quando o elemento nao foi encontrado.
		}
		return -1;
	}
	
	
	/**
	 * Procura um elemento numa coluna, busca pelo mais similar.
	 * @param posicaoColuna - int, coluna na qual se deve procurar, começando com 0.
	 * @param elemento - String, nome que se deseja pesquisar.
	 * @return int correspondente ao indice da linha do elemento, ou -1 se o indice nao for encontrado.
	 */
	private int procuraMatchNaoExato(int posicaoColuna, String elemento) {
		
		Map<Integer, Double> mapaScoreJw = new HashMap<Integer, Double>();
		Map<Integer, Double> mapaScoreLcs2 = new HashMap<Integer, Double>();

		// Comparacao dos elementos:
		for (int iterator = 1; iterator < quantidadeLinhas; iterator ++)
		{
			String valorElemento = this.dadosCsv.get(iterator)[posicaoColuna].toUpperCase();
			
			if (valorElemento.equals(elemento)) {
				return iterator;
			} else {
				mapaScoreJw.put(iterator, jw.distance(valorElemento, elemento));
				mapaScoreLcs2.put(iterator, lcs2.distance(valorElemento, elemento));
			}
		}
		
		// Encontra o elemento com a maior similaridade.
		
		// Ordena os mapas de score para ter o melhor match como primeiro elemento.
		Stream<Entry<Integer, Double>> sortedJw = mapaScoreJw.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
		Stream<Entry<Integer, Double>> sortedLcs2 = mapaScoreLcs2.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
		
		Entry<Integer, Double> melhorMatchJw = sortedJw.findFirst().get();
		Entry<Integer, Double> melhorMatchLcs2 = sortedLcs2.findFirst().get();

		if (melhorMatchJw.getValue() >= 0.985) { // similaridade >= 98.5%
			return melhorMatchJw.getKey();
		}
		
		if (melhorMatchLcs2.getValue() >= 0.985) { // similaridade >= 98.5%
			return melhorMatchLcs2.getKey();
		}
		
		return -1;
		
	}

	
	/**
	 * Procura um elemento numa coluna, busca exata.
	 * @param posicaoColuna - int, coluna na qual se deve procurar, começando com 0.
	 * @param elemento - String, nome que se deseja pesquisar.
	 * @return int correspondente ao indice da linha do elemento, ou -1 se o indice nao for encontrado.
	 */
	private int procuraMatchExato(int posicaoColuna, String elemento) {

		for (int iterator = 1; iterator < quantidadeLinhas; iterator ++)
		{
			String valorElemento = this.dadosCsv.get(iterator)[posicaoColuna];
			if (valorElemento.equals(elemento)) {
				return iterator;
			}
		}
		
		return -1;	
	}

	
	/**
	 * Retorna todos os elementos de uma linha.
	 * @param indiceLinha - int, indice da linha da qual deseja-se obter os elementos.
	 * @return Array de String com os elementos da linha, ou um array vazio 
	 * se a linha for inexistente.
	 */
	public String[] getElementosLinha(int indiceLinha)
	{
		if (indiceLinha < this.quantidadeLinhas)
		{
			int quantidadeColunas = this.quantidadeColunas;
			String[] elementosLinha = new String[quantidadeColunas];
			for (int iterator = 0; iterator < quantidadeColunas; iterator ++)
			{
				elementosLinha[iterator] = dadosCsv.get(iterator)[indiceLinha];
			}
			return elementosLinha;
		}else {
			System.err.println("Linha nao existente.");
			return Constantes.ARRAY_VAZIO;
		}
	}
	
	
	
	/**
	 * Retorna todos os elementos de determinada coluna.
	 * @param nomeColuna - String, nome da coluna desejada.
	 * @return Array de String com cada elemento da coluna desejada, 
	 * ou um array vazio se a coluna for inexistente.
	 */
	public String[] getElementosColuna(String nomeColuna)
	{
		int indiceColuna = this.getPosicaoColuna(nomeColuna);
		if (indiceColuna != -1)
		{
			String[] elementosColuna = new String[this.quantidadeLinhas - 1];
			for (int iterator = 1; iterator < this.quantidadeLinhas; iterator ++)
			{
				elementosColuna[iterator - 1] = this.dadosCsv.get(iterator)[indiceColuna];
			}
			return elementosColuna;
		}else {
			System.err.println("Coluna nao existente.");
		}
		
		return Constantes.ARRAY_VAZIO;
		
	}
}
