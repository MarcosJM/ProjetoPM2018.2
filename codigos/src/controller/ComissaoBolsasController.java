package controller;

import java.util.ArrayList;
import java.util.Collections;

import model.Artigo;
import model.Candidato;
import model.Evento;
import model.Premio;
import model.Vinculo;
import utils.TxtUtils;

/**
 * Classe para avaliacao dos parametros de entrada.
 *
 */
public class ComissaoBolsasController {
	
	private static ArrayList<Candidato> candidatos = new ArrayList<Candidato>();
	
	private static String arquivoSaida;
	private static String arquivoLog;
	
	private static StringBuilder conteudoSaida = new StringBuilder("");
	private static StringBuilder conteudoLog = new StringBuilder("");
	
	/**
	 * Cria um novo candidato para ser pontuado.
	 * Comando: -a <nome-caminho-arquivo-xml> <num-semestres-sem-reprovacao>
	 * @param caminhoXml - String que indica o caminho ate o arquivo, deve conter a extensao .xml
	 * @param numeroSemestreSemReprovacao - String, numero de semestres sem reprovar na pos-graduacao.
	 */
	public void novoCandidato(String caminhoXml, String numeroSemestreSemReprovacao) {
		Candidato candidato = new Candidato(caminhoXml);
		
		if (numeroSemestreSemReprovacao.equals("") || numeroSemestreSemReprovacao == null) {
			numeroSemestreSemReprovacao = "0";
		}
		
		candidato.setNumeroSemestreSemReprovacao(Integer.parseInt(numeroSemestreSemReprovacao));
		
		// Candidato com o lattes e outros parametros lidos:
		candidatos.add(candidato);
	}
	
	
	/** 
	 * Gera a saida referente aos premios recebidos por cada candidato.
	 * Comando: -pr
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 */
	public void geraSaidaPremios(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Nome	Quantidade de premios	Pontuacao de premios");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getNome() + "	");
			conteudo.append(candidato.getPremios().size() + "	");
			conteudo.append(candidato.getPontuacaoPremios());
			
			if (verboso && candidato.getPremios().size() > 0) {
				// Exibe a lista de premios.
				conteudo.append("\r\nAno	Nome");
				for (Premio premio : candidato.getPremios()) {
					conteudo.append(premio);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		
		// Candidatos, sua pontuacao e os premios, opcionalmente.
		conteudoSaida.append(conteudo.toString());
	}
	
	


	/** 
	 * Gera a saida referente aos artigos completos no Qualis Restrito.
	 * Conferencias e periodicos A1, A2 ou B1.
	 * Comando: -ar
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 */
	public void geraSaidaArtigosQualisRestrito(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Nome	Quantidade de artigos	Pontuacao de artigos");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getNome() + "	");
			conteudo.append(candidato.getArtigosCompletosQualisRestrito().size() + "	");
			conteudo.append(candidato.getPontuacaoQualisRestrito());
			
			if (verboso && candidato.getArtigosCompletosQualisRestrito().size() > 0) {
				// Exibe detalhes sobre os artigos.
				conteudo.append("\r\nAno	Nome	Origem	Qualis");
				for (Artigo artigo : candidato.getArtigosCompletosQualisRestrito()) {
					conteudo.append(artigo);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		
		// Candidatos, sua pontuacao e os artigos, opcionalmente.
		conteudoSaida.append(conteudo.toString());
	}
	
	
		
	/** 
	 * Gera a saida referente aos artigos completos fora do Qualis Restrito.
	 * Conferencias e periodicos B2, B3, B4 e B5.
	 * Comando: -anr
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 */
	public void geraSaidaArtigosQualisCompleto(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Nome	Quantidade de artigos	Pontuacao de artigos");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getNome() + "	");
			conteudo.append(candidato.getArtigosCompletosQualisCompleto().size() + "	");
			conteudo.append(candidato.getPontuacaoQualisCompleto());
			
			if (verboso && candidato.getArtigosCompletosQualisCompleto().size() > 0) {
				// Exibe detalhes sobre os artigos.
				conteudo.append("\r\nAno	Nome	Origem	Qualis");
				for (Artigo artigo : candidato.getArtigosCompletosQualisCompleto()) {
					conteudo.append(artigo);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		
		// Candidatos, sua pontuacao e os artigos, opcionalmente.
		conteudoSaida.append(conteudo.toString());
	}
	
	

	/** 
	 * Gera a saida referente a participacao em eventos classificados.
	 * Eventos A1, A2, B1, B2, B3, B4 e B5.
	 * Comando: -pe
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 */
	public void geraSaidaEventos(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Nome	Quantidade de eventos	Pontuacao de eventos");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getNome() + "	");
			conteudo.append(candidato.getEventos().size() + "	");
			conteudo.append(candidato.getPontuacaoEventos());
			
			if (verboso && candidato.getEventos().size() > 0) {
				// Exibe detalhes dos eventos.
				conteudo.append("\r\nAno	Evento	Qualis");
				for (Evento evento : candidato.getEventos()) {
					conteudo.append(evento);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		
		// Candidatos, sua pontuacao e os eventos, opcionalmente.
		conteudoSaida.append(conteudo.toString());
	}
	
	
	
	
	/** 
	 * Gera a saida referente a existencia de vinculo com a UNIRIO.
	 * Comando: -vi
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	*/ 
	public void geraSaidaVinculos(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Nome	Quantidade de vinculos	Pontuacao de vinculos");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getNome() + "	");
			conteudo.append(candidato.getVinculos().size() + "	");
			conteudo.append(candidato.getPontuacaoVinculos());
			
			if (verboso && candidato.getVinculos().size() > 0) {
				// Exibe detalhes dos vinculos.
				conteudo.append("\r\nAno	Vinculo");
				for (Vinculo vinculo : candidato.getVinculos()) {
					conteudo.append(vinculo);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		
		// Candidatos, sua pontuacao e os vinculos, opcionalmente.
		conteudoSaida.append(conteudo.toString());
	}
	
	
		
	/** 
	 * Gera a saida completa do programa, equivalente a usar, em conjunto, 
	 * os parametros -pr -ar -anr -pe -vi
	 * Comando: -c
	 * @param verboso - boolean que indica se as saidas devem detalhadas ou nao. Comando: -v
	 * @return String, candidatos, seus premios, artigos, eventos, vinculos 
	 * e os pontos em cada categoria.
	*/ 
	public void geraSaidaSaidaCompleta(boolean verboso) {
		
		geraSaidaPremios(verboso);
		geraSaidaArtigosQualisRestrito(verboso);
		geraSaidaArtigosQualisCompleto(verboso);
		geraSaidaEventos(verboso);
		geraSaidaVinculos(verboso);
		
	}
	

	/**
	 * Define o caminho do arquivo texto que deve conter a saida do programa.
	 * Comando: -o <nome-caminho-arquivo-txt-saida>
	 * @param caminhoTxt - String que indica o caminho ate o arquivo, deve conter a extensao .txt
	 */
	public void defineCaminhoSaida(String caminhoTxt) {
		arquivoSaida = caminhoTxt;
	}
	
	
	/**
	 * Define o caminho do arquivo texto que deve conter um log/relatorio de erros do programa.
	 * Comando: -l <nome-caminho-arquivo-log-errors>
	 * @param caminhoTxt - String que indica o caminho ate o arquivo, deve conter a extensao .txt
	 */
	public void defineCaminhoLogErro(String caminhoTxt) {
		arquivoLog = caminhoTxt;
	}
	

	/**
	 * Ordena os candidatos disponiveis por sua pontuacao.
	 */
	public void ordenaCandidatos() {
		
		Collections.sort(
			candidatos, 
			(candidato1, candidato2) -> 
				candidato1.getPontuacao() < candidato2.getPontuacao() ? -1 : 
				candidato1.getPontuacao() == candidato2.getPontuacao() ? 0 : 
				1
		);
	}
	
	
	/**
	 * Gera a saida com todos os candidatos lidos e sua pontuacao.
	 * @return String, candidatos e sua pontuacao completa.
	 */
	public void geraSaidaRanking() {
		StringBuilder conteudo = new StringBuilder("");
		ordenaCandidatos();
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato);
			conteudo.append("\r\n");
		}

		conteudoSaida.append(conteudo.toString());
	}
	
	
	/**
	 * Finaliza o programa escrevendo nos arquivos de saida e log de erros.  
	 */
	public void finalizaPrograma() {
		// Escreve o ranking final dos candidatos:
		geraSaidaRanking();
		
		try {
			TxtUtils.escreverTxt(arquivoSaida, conteudoSaida.toString());
			TxtUtils.escreverTxt(arquivoLog, conteudoLog.toString());
		} catch (Exception e) {
			// TODO: colocar o log de erros no arquivo.
			System.out.println(e.getMessage()); 
		}
		
	}
	
	
}
