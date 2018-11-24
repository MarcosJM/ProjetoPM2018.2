package view;

import utils.XmlUtils;
import utils.CommandLine;

import static java.lang.System.out;

import java.util.Scanner;

/**
 * Interface com o usu�rio.
 *
 */

public class Principal {
	
	/**
	 * Funcao para decompor a entrada passada em argumentos.
	 * Isso eh feito ao cortar a string, utilizando espaco como separador.
	 * 
	 * @param entrada
	 * @return argumentos
	 */
	public String[] decompoeEntrada(String entrada) {
		String[] argumentos = entrada.split(" ");
		return argumentos;
	}
	
	/**
	 * Funcao que, de acordo com o comando, redireciona para a chamada de funcao adequada.
	 * 
	 * A relacao de cada comando com sua execucao esperada eh dada por:
	 * 
	 * 1) -a : leitura do arquivo XML do lattes e definicao dos dados a serem julgados.
	 * 2) -o <nome-caminho-arquivo-txt-saida> : gravacao de arquivo texto com saida do programa.
	 * 3) -l <nome-caminho-arquivo-log-errors> : gravacao de arquivo texto com erros do programa.
	 * 4) -v : modo verbose, exibe a execucao do programa.
	 * 5) -c : conteudo da saida do programa eh completo.
	 * 6) -pr : para cada candidato, deve ser exibida a quantidade de pr�mios considerados e a pontua��o obtida.
	 * 7) -ar : para cada candidato, deve ser exibida a quantidade de artigos considerados e a pontua��o obtida,
	 * 		Se o modo verbose ativado, deve ser exibida ao final, para cada candidato: o ano de publica��o, nome do artigo, confer�ncia ou peri�dico associado e o Qualis associado.
	 * 8) -anr : para cada candidato, deve ser exibida a quantidade de artigos considerados e a pontua��o obtida,
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano de publica��o, nome do artigo, confer�ncia ou peri�dico associado e o Qualis associado.
	 * 9) -pe : para cada candidato, deve ser exibida a quantidade de participa��es consideradas e a pontua��o obtida.
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano, confer�ncia e o Qualis associado.
	 * 10) -vi : para cada candidato, deve ser exibida a quantidade de v�nculos considerados e a pontua��o obtida.
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano, v�nculo associado.
	 * 
	 * @param argumentos
	 * 
	 */
	public void executaComando(String[] argumentos) {
		Comandos comandos = new Comandos();
		
		Comandos executar = CommandLine.populateCommand(comandos, argumentos);
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("$ ");
		String entrada = scanner.next();
		
	}

}
