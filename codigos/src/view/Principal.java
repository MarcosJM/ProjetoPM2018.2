package view;

import utils.CommandLine;
import controller.ComissaoBolsasController;
import java.util.Scanner;

/**
 * Interface com o usuário.
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
	public static String[] decompoeEntrada(String entrada) {
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
	 * 6) -pr : para cada candidato, deve ser exibida a quantidade de prêmios considerados e a pontuação obtida.
	 * 7) -ar : para cada candidato, deve ser exibida a quantidade de artigos no Qualis Restrito considerados e a pontuação obtida,
	 * 		Se o modo verbose ativado, deve ser exibida ao final, para cada candidato: o ano de publicação, nome do artigo, conferência ou periódico associado e o Qualis associado.
	 * 8) -anr : para cada candidato, deve ser exibida a quantidade de artigos fora do Qualis Restrito considerados e a pontuação obtida,
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano de publicação, nome do artigo, conferência ou periódico associado e o Qualis associado.
	 * 9) -pe : para cada candidato, deve ser exibida a quantidade de participações em eventos classificados consideradas e a pontuação obtida.
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano, conferência e o Qualis associado.
	 * 10) -vi : para cada candidato, deve ser exibida a quantidade de vínculos com a Unirio considerados e a pontuação obtida.
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano, vínculo associado.
	 * 
	 * @param argumentos
	 * 
	 */
	
	public static void executaComandos(String[] argumentos) {
		Comandos comandos = new Comandos();
		
		try {
			comandos = CommandLine.populateCommand(comandos, argumentos); //Metodo populateCommand, da biblioteca picocli, ira popular o comando instanciado com as opcoes e suas variaveis passadas
			
			String[] dadosECaminhosXML = comandos.getDadosECaminhosXML();
			ComissaoBolsasController.defineCaminhoSaida(comandos.getCaminhoSaida());
			if(comandos.getCaminhoLog() != null) { ComissaoBolsasController.defineCaminhoLogErro(comandos.getCaminhoLog()); } //Associa o caminho do log de erros apenas se o comando foi utilizado
			boolean verboso = comandos.isVerboso();
			
			//Percorre o vetor de dadosECaminhosXML passo 2, pois este vetor esta estruturado como: [caminho XML1, dado1, caminho XML2, dado2, ...]
			for(int i = 0; i < dadosECaminhosXML.length; i += 2) {
				ComissaoBolsasController.novoCandidato(dadosECaminhosXML[i], dadosECaminhosXML[i+1]);
			}
			
			//Gera a saida do sistema de acordo com os parametros passados
			if (comandos.isCompleto()) {
				ComissaoBolsasController.geraSaidaSaidaCompleta(verboso);
			} else {
				if (comandos.isPremios()) {
					ComissaoBolsasController.geraSaidaPremios(verboso);
				}
				if (comandos.isArtigosNoQualisRestrito()) {
					ComissaoBolsasController.geraSaidaArtigosQualisRestrito(verboso);
				}
				if (comandos.isArtigosForaQualisRestrito()) {
					ComissaoBolsasController.geraSaidaArtigosQualisCompleto(verboso);
				}
				if (comandos.isEventosClassificados()) {
					ComissaoBolsasController.geraSaidaEventos(verboso);
				}
				if (comandos.isVinculoUnirio()) {
					ComissaoBolsasController.geraSaidaVinculos(verboso);
				}
			}
			
			//Finaliza o programa, associando o ranking final dos candidatos ao arquivo de saida
			ComissaoBolsasController.finalizaPrograma();
						
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/**
	 * Ponto de entrada na aplicação.
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("$ ");
		String entrada = scanner.nextLine();
		String[] argumentos = decompoeEntrada(entrada);
		scanner.close();
		
		executaComandos(argumentos);

	}
}
