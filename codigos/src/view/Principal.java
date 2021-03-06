package view;

import utils.CommandLine;

import controller.ComissaoBolsasController;

/**
 * Interface com o usu�rio.
 *
 */

public class Principal {
	
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
	 * 7) -ar : para cada candidato, deve ser exibida a quantidade de artigos no Qualis Restrito considerados e a pontua��o obtida,
	 * 		Se o modo verbose ativado, deve ser exibida ao final, para cada candidato: o ano de publica��o, nome do artigo, confer�ncia ou peri�dico associado e o Qualis associado.
	 * 8) -anr : para cada candidato, deve ser exibida a quantidade de artigos fora do Qualis Restrito considerados e a pontua��o obtida,
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano de publica��o, nome do artigo, confer�ncia ou peri�dico associado e o Qualis associado.
	 * 9) -pe : para cada candidato, deve ser exibida a quantidade de participa��es em eventos classificados consideradas e a pontua��o obtida.
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano, confer�ncia e o Qualis associado.
	 * 10) -vi : para cada candidato, deve ser exibida a quantidade de v�nculos com a Unirio considerados e a pontua��o obtida.
	 * 		Se o modo verboso estiver ativado, deve ser exibida ao final, para cada candidato: o ano, v�nculo associado.
	 * 
	 * @param argumentos
	 * 
	 */
	
	public static void executaComandos(String[] argumentos) {	
		Comandos comandos = Comandos.getInstancia();
		
		try {
			comandos = CommandLine.populateCommand(comandos, argumentos); //Metodo populateCommand, da biblioteca picocli, ira popular o comando instanciado com as opcoes e suas variaveis passadas
			
			String[] dadosECaminhosXML = comandos.getDadosECaminhosXML();
			ComissaoBolsasController.getInstancia().defineCaminhoSaida(comandos.getCaminhoSaida());
			if(comandos.getCaminhoLog() != null) { ComissaoBolsasController.getInstancia().defineCaminhoLogErro(comandos.getCaminhoLog()); } //Associa o caminho do log de erros apenas se o comando foi utilizado
			boolean verboso = comandos.isVerboso();
			
			//Percorre o vetor de dadosECaminhosXML passo 2, pois este vetor esta estruturado como: [caminho XML1, dado1, caminho XML2, dado2, ...]
			for(int i = 0; i < dadosECaminhosXML.length; i += 2) {
				ComissaoBolsasController.getInstancia().novoCandidato(dadosECaminhosXML[i], dadosECaminhosXML[i+1]);
			}
			
			//Gera a saida do sistema de acordo com os parametros passados
			if (comandos.isCompleto()) {
				ComissaoBolsasController.getInstancia().geraSaidaSaidaCompleta(verboso);
			} else {
				if (comandos.isPremios()) {
					ComissaoBolsasController.getInstancia().geraSaidaPremios(verboso);
				}
				if (comandos.isArtigosNoQualisRestrito()) {
					ComissaoBolsasController.getInstancia().geraSaidaArtigosQualisRestrito(verboso);
				}
				if (comandos.isArtigosForaQualisRestrito()) {
					ComissaoBolsasController.getInstancia().geraSaidaArtigosQualisCompleto(verboso);
				}
				if (comandos.isEventosClassificados()) {
					ComissaoBolsasController.getInstancia().geraSaidaEventos(verboso);
				}
				if (comandos.isVinculoUnirio()) {
					ComissaoBolsasController.getInstancia().geraSaidaVinculos(verboso);
				}
				//Nesse caso, nenhum argumento relacionado a saida foi passado
				if (!comandos.isPremios() && !comandos.isArtigosNoQualisRestrito() && !comandos.isArtigosForaQualisRestrito() && !comandos.isEventosClassificados() && !comandos.isVinculoUnirio()) {
					ComissaoBolsasController.getInstancia().geraSaidaSaidaCompleta(verboso); //Por padrao, ira gerar a saida completa
				}
			}
			
			//Finaliza o programa, associando o ranking final dos candidatos ao arquivo de saida
			ComissaoBolsasController.getInstancia().finalizaPrograma();
						
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/**
	 * Ponto de entrada na aplica��o.
	 * @param args 
	 */
	
	public static void main(String[] args) {
			
		executaComandos(args);
		
	}
}
