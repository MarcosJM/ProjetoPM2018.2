package model;

import java.util.ArrayList;

import utils.Constantes;
import utils.CsvUtils;

/**
 * 
 *  Classe que realiza o processamento de arquivos CSV
 */
public class ArquivoCsv {
	private ArrayList<String[]> dadosCsv = new ArrayList<String[]>();
	private int quantidadeLinhas;
	private int quantidadeColunas;
	
	/**
	 * Construtor - Cria uma instancia de um arquivo CSV.
	 * @param endereco - String que indica o caminho ate o arquivo
	 * @param separador - String que indica o tipo de separador utilizado no arquivo csv
	 */
	public ArquivoCsv(String endereco, String separador)
	{
		CsvUtils inicializadorCsv = new CsvUtils();
		this.dadosCsv = inicializadorCsv.lerCsv(endereco, separador);
		this.quantidadeLinhas = dadosCsv.size();
		this.quantidadeColunas = dadosCsv.get(0).length;
	}
	
	/**
	 * 
	 * @return int - retorna a quantidade de linhas na tabela representada por CSV.
	 */
	public int getQuantidadeLinhas()
	{
		return this.quantidadeLinhas;
	}
	
	/**
	 * 
	 * @return int - retorna a quantidade de colunas na tabela representada por CSV.
	 */
	public int getQuantidadeColunas()
	{
		return this.quantidadeColunas;
	}
	
	
	public String getElemento(String nomeColuna, int locLinha)
	// Para um futuro proximo: padronizar os nomes loc, indice e posicao pois se referem a mesma coisa
	{
		if (locLinha < this.getQuantidadeLinhas())
		{
			String[] linha = dadosCsv.get(locLinha);
			int indiceColuna = this.getPosicaoColuna(nomeColuna);
			if (indiceColuna != -1)
			{
				return linha[indiceColuna];
			}	
		}else {
			System.err.println("O endereco da linha esta fora dos limites.");
		}
		return "";
	}
	
	/**
	 * retorna o elemento contido na localizacao indicada
	 * @param locLinha, int - indice da linha
	 * @param locColuna, int - indice da coluna
	 * @return retorna o elemento encontrado, ou entao uma string vazia
	 */
	public String getElemento(int locLinha, int locColuna)
	{
		if (locLinha < this.getQuantidadeLinhas())
		{
			String[] linha = dadosCsv.get(locLinha);
			
			if (locColuna < this.getQuantidadeColunas())
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
	 * recebe o nome de uma coluna e retora a sua posicao no array
	 * @param nomeColuna, String - nome da coluna desejada
	 * @return retorna a posicao da coluna desejada, ou -1 se nada for encontrado
	 */
	private int getPosicaoColuna(String nomeColuna)
	{
		String[] colunas = this.dadosCsv.get(0);
		for (int iterator = 0; iterator < this.getQuantidadeColunas(); iterator ++)
		{
			if (nomeColuna.equals(colunas[iterator]))
			{
				return iterator;
			}
		}
		return -1;
	}
	
	
	/**
	 * retorna o indice da linha de um elemento dado o proprio e o nome de sua coluna
	 * @param nomeColuna, String - nome da coluna desejada
	 * @param elemento, String - elemento cujo indice deseja-se saber
	 * @return int correspondente ao indice da linha do elemento, ou -1 se o indice nao for encontrado.
	 */
	public int getLinhaElemento(String nomeColuna, String elemento)
	{
		int posicaoColuna = this.getPosicaoColuna(nomeColuna);
		if (posicaoColuna != -1)
		{
			int quantidadeLinhas = this.getQuantidadeLinhas();
			for (int iterator = 0; iterator < quantidadeLinhas; iterator ++)
			{
				if (this.dadosCsv.get(iterator)[posicaoColuna].equals(elemento))
				{
					//essa forma de retorno leva em conta que cada elemento na tabela eh unico.
					return iterator;
				}
			}
			//faltando: mensagem para informar quando o elemento nao foi encontrado.
		}
		return -1;
	}
	
	/**
	 * retorna todos os elementos de uma linha
	 * @param indiceLinha, int - indice da linha da qual deseja-se obter os elementos
	 * @return Array de Strings com os elementos da linha, ou um array vazio se a linha for inexistente
	 */
	public String[] getElementosLinha(int indiceLinha)
	{
		if (indiceLinha < this.getQuantidadeLinhas())
		{
			int quantidadeColunas = this.getQuantidadeColunas();
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
	 * retorna todos os elementos de determinada coluna
	 * @param nomeColuna, String - nome da coluna desejada
	 * @return Array de Strrings com cada elemento da coluna desejada, ou um array vazio se a coluna for inexistente.
	 */
	public String[] getElementosColuna(String nomeColuna)
	{
		int indiceColuna = this.getPosicaoColuna(nomeColuna);
		if (indiceColuna != -1)
		{
			String[] elementosColuna = new String[this.getQuantidadeLinhas() - 1];
			for (int iterator = 1; iterator < this.getQuantidadeLinhas(); iterator ++)
			{
				elementosColuna[iterator - 1] = this.dadosCsv.get(iterator)[indiceColuna];
			}
		}else {
			System.err.println("Coluna nao existente.");
		}
		
		return Constantes.ARRAY_VAZIO;
		
	}
}
