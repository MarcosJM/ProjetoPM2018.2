package controller;

import java.util.ArrayList;
import java.util.Collections;

import model.Artigo;
import model.Candidato;
import model.Evento;
import model.Premio;
import model.Vinculo;
import utils.Constantes;
import utils.TxtUtils;

/**
 * Classe para avaliacao dos parametros de entrada.
 *
 */
public class ComissaoBolsasController {
	
	private ArrayList<Candidato> candidatos = new ArrayList<Candidato>();
	
	private String arquivoSaida;
	private String arquivoLog;
	
	private StringBuilder conteudoSaida = new StringBuilder("");
	private StringBuilder conteudoLog = new StringBuilder("");
	
	
	/**
	 * Implementacao do Singleton.
	 */
	
	private static ComissaoBolsasController unicaInstancia;
	
	private ComissaoBolsasController() {}
	
	public static ComissaoBolsasController getInstancia() {
		
		if (unicaInstancia == null) {
			unicaInstancia = new ComissaoBolsasController();
		} 
		
		return unicaInstancia;
    }
	
	public ArrayList<Candidato> getCandidatos() {
		return candidatos;
	}


	public void setCandidatos(ArrayList<Candidato> candidatos) {
		this.candidatos = candidatos;
	}
	
	public void addCandidatos(Candidato candidato) {
		this.candidatos.add(candidato);
	}


	public String getArquivoSaida() {
		return arquivoSaida;
	}


	public void setArquivoSaida(String arquivoSaida) {
		this.arquivoSaida = arquivoSaida;
	}


	public String getArquivoLog() {
		return arquivoLog;
	}


	public void setArquivoLog(String arquivoLog) {
		this.arquivoLog = arquivoLog;
	}


	public StringBuilder getConteudoSaida() {
		return conteudoSaida;
	}


	public void setConteudoSaida(StringBuilder conteudoSaida) {
		this.conteudoSaida = conteudoSaida;
	}


	public StringBuilder getConteudoLog() {
		return conteudoLog;
	}


	public void setConteudoLog(StringBuilder conteudoLog) {
		this.conteudoLog = conteudoLog;
	}


	/**
	 * Cria um novo candidato para ser pontuado.
	 * Comando: -a <nome-caminho-arquivo-xml> <num-semestres-sem-reprovacao>
	 * @param caminhoXml - String que indica o caminho ate o arquivo, deve conter a extensao .xml
	 * @param numeroSemestreSemReprovacao - String, numero de semestres sem reprovar na pos-graduacao.
	 */
	public void novoCandidato(String caminhoXml, String numeroSemestreSemReprovacao) {
		
		if (numeroSemestreSemReprovacao.equals("") || numeroSemestreSemReprovacao == null) {
			numeroSemestreSemReprovacao = String.valueOf(Constantes.NUMERO_SEMESTRES_SEM_REPROVACAO_PADRAO);
		}
		
		Candidato candidato = new Candidato(Integer.parseInt(numeroSemestreSemReprovacao));
		CandidatoXMLController.getInstancia().carregarCandidatoCompleto(candidato, caminhoXml);
		
		// Candidato com o lattes e outros parametros lidos:
		candidatos.add(candidato);
		// Define pontuacao deste candidato
		definePontuacao(candidato);
	}
	
	/**
	 * RN2 - O candidato recebe um ponto por cada premio recebido nos ultimos 10 anos.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoPremios(ArrayList<Premio> premios) {
		return premios.size() * Constantes.PONTOS_PREMIOS_MULTIPLICADOR;
	}
	
	
	/**
	 * RN3 - O candidato recebe tres pontos por cada artigo pontuado como A1, A2 ou B1 
	 * na Qualis da Computacao nos ultimos 10 anos.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoQualisRestrito(ArrayList<Artigo> artigosCompletosQualisRestrito) {
		return artigosCompletosQualisRestrito.size() * Constantes.PONTOS_QUALIS_RESTRITO_MULTIPLICADOR;
	
	}
	
	
	/**
	 * RN4 - O candidato recebe um ponto por cada artigo pontuado como B2, B3, B4 ou B5 
	 * na Qualis da Computacao nos ultimos 10 anos.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoQualisCompleto(ArrayList<Artigo> artigosCompletosQualisCompleto) {
		return artigosCompletosQualisCompleto.size() * Constantes.PONTOS_QUALIS_COMPLETO_MULTIPLICADOR;
	}
	
	
	/**
	 * RN5 - O candidato recebe um ponto por cada evento participado. 
	 * O maximo de pontos por esse requisito sao cinco.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoEventos(ArrayList<Evento> eventos) {
		if (eventos.size() > Constantes.PONTOS_EVENTOS) {
			return Constantes.PONTOS_EVENTOS;
		} else {
			return eventos.size();
		}
	}
	
	
	/**
	 * RN6 - O candidato recebe um ponto se houver registro de vinculo com a UNIRIO 
	 * nos ultimos 10 anos, seja por participacao em projetos, 
	 * bolsas de pesquisa, representacao discente ou similar.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoVinculos(boolean possuiVinculoInstituicao) {
		if (possuiVinculoInstituicao) {
			return Constantes.PONTOS_VINCULOS;
		} else {
			return 0;
		}
	}
	
	/**
	 * Metodo para calculo da pontuacao geral.
	 * Usa metodos que calculam a pontuacao por categoria.
	 */
	private void definePontuacao(Candidato candidato) {
		
		int pontuacao = 0;
		
		// RN1 - O candidato recebe um ponto por cada semestre cursado sem reprovacao no curso que a bolsa esta sendo pleiteada
		pontuacao += candidato.getNumeroSemestreSemReprovacao();
		
		// Premios:
		pontuacao += getPontuacaoPremios(candidato.getPremios());
		
		// Artigos:
		pontuacao += getPontuacaoQualisRestrito(candidato.getArtigosCompletosQualisRestrito());
		pontuacao += getPontuacaoQualisCompleto(candidato.getArtigosCompletosQualisCompleto());
				
		// Eventos:
		pontuacao += getPontuacaoEventos(candidato.getEventos());
		
		// Vinculos:
		pontuacao += getPontuacaoVinculos(candidato.isPossuiVinculoInstituicao());
		
		candidato.setPontuacao(pontuacao);

	}
	
	
	/** 
	 * Gera a saida referente aos premios recebidos por cada candidato.
	 * Comando: -pr
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 */
	public void geraSaidaPremios(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Quantidade de premios\t\tPontuacao de premios\t\tNome\r\n");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getPremios().size() + "\t\t\t\t");
			conteudo.append(getPontuacaoPremios(candidato.getPremios()) + "\t\t\t\t");
			conteudo.append(candidato.getNome());
			
			if (verboso && candidato.getPremios().size() > 0) {
				// Exibe a lista de premios.
				conteudo.append("\r\n\tAno\tNome\r\n");
				for (Premio premio : candidato.getPremios()) {
					conteudo.append("\t" + premio);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		conteudo.append("\r\n");
		
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
		StringBuilder conteudo = new StringBuilder("Quantidade de artigos\t\tPontuacao de artigos\t\tNome\r\n");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getArtigosCompletosQualisRestrito().size() + "\t\t\t\t");
			conteudo.append(getPontuacaoQualisRestrito(candidato.getArtigosCompletosQualisRestrito()) + "\t\t\t\t");
			conteudo.append(candidato.getNome());
			
			if (verboso && candidato.getArtigosCompletosQualisRestrito().size() > 0) {
				// Exibe detalhes sobre os artigos.
				conteudo.append("\r\n\tAno\tQualis\tNome e origem\r\n");
				for (Artigo artigo : candidato.getArtigosCompletosQualisRestrito()) {
					conteudo.append("\t" + artigo);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		conteudo.append("\r\n");
		
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
		StringBuilder conteudo = new StringBuilder("Quantidade de artigos\t\tPontuacao de artigos\t\tNome\r\n");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getArtigosCompletosQualisCompleto().size() + "\t\t\t\t");
			conteudo.append(getPontuacaoQualisCompleto(candidato.getArtigosCompletosQualisCompleto()) + "\t\t\t\t");
			conteudo.append(candidato.getNome());
			
			if (verboso && candidato.getArtigosCompletosQualisCompleto().size() > 0) {
				// Exibe detalhes sobre os artigos.
				conteudo.append("\r\n\tAno\tQualis\tNome e origem\r\n");
				for (Artigo artigo : candidato.getArtigosCompletosQualisCompleto()) {
					conteudo.append("\t" + artigo);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		conteudo.append("\r\n");
		
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
		StringBuilder conteudo = new StringBuilder("Quantidade de eventos\t\tPontuacao de eventos\t\tNome\r\n");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getEventos().size() + "\t\t\t\t");
			conteudo.append(getPontuacaoEventos(candidato.getEventos()) + "\t\t\t\t");
			conteudo.append(candidato.getNome());
			
			if (verboso && candidato.getEventos().size() > 0) {
				// Exibe detalhes dos eventos.
				conteudo.append("\r\n\tAno\tQualis\tEvento\r\n");
				for (Evento evento : candidato.getEventos()) {
					conteudo.append("\t" + evento);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		conteudo.append("\r\n");
		
		// Candidatos, sua pontuacao e os eventos, opcionalmente.
		conteudoSaida.append(conteudo.toString());
	}
	
	
	
	
	/** 
	 * Gera a saida referente a existencia de vinculo com a UNIRIO.
	 * Comando: -vi
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	*/ 
	public void geraSaidaVinculos(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Quantidade de vinculos\t\tPontuacao de vinculos\t\tNome\r\n");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getVinculos().size() + "\t\t\t\t");
			conteudo.append(getPontuacaoVinculos(candidato.isPossuiVinculoInstituicao()) + "\t\t\t\t");
			conteudo.append(candidato.getNome());
			
			if (verboso && candidato.getVinculos().size() > 0) {
				// Exibe detalhes dos vinculos.
				conteudo.append("\r\n\tAno\tVinculo\r\n");
				for (Vinculo vinculo : candidato.getVinculos()) {
					conteudo.append("\t" + vinculo);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		conteudo.append("\r\n");
		
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
	 * Ordena os candidatos disponiveis por sua pontuacao,
	 * do maior para o menor (decrescente).
	 */
	public void ordenaCandidatos() {
		
		Collections.sort(
			candidatos, 
			(candidato1, candidato2) -> 
				((Integer)candidato2.getPontuacao()).compareTo(candidato1.getPontuacao())
		);
	}
	
	
	/**
	 * Gera a saida com todos os candidatos lidos e sua pontuacao.
	 * @return String, candidatos e sua pontuacao completa.
	 */
	public void geraSaidaRanking() {
		StringBuilder conteudo = new StringBuilder("Pontos\t\tNome\r\n");
		ordenaCandidatos();
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getPontuacao() + "\t\t");
			conteudo.append(candidato.getNome());
			conteudo.append("\r\n");
		}

		conteudoSaida.append(conteudo.toString());
	}
	
	
	/**
	 * Finaliza o programa escrevendo nos arquivos de saida e log de erros.  
	 */
	public void finalizaPrograma() {
		// Escreve o ranking final dos candidatos:
		conteudoSaida.append("\r\nRanking final\r\n");
		geraSaidaRanking();
		
		try {
			TxtUtils.escreverTxt(arquivoSaida, conteudoSaida.toString());
			//TxtUtils.escreverTxt(arquivoLog, conteudoLog.toString());
		} catch (Exception e) {
			// TODO: colocar o log de erros no arquivo.
			System.out.println(e.getMessage()); 
		}
		
	}
	
	
}
