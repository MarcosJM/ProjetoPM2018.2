package controller;

import java.util.ArrayList;

import model.Artigo;
import model.Candidato;
import model.Evento;
import model.Premio;

/**
 * Classe para avaliacao dos parametros de entrada.
 *
 */
public class ComissaoBolsasController {
	
	private ArrayList<Candidato> candidatos = new ArrayList<Candidato>();
	
	/**
	 * Cria um novo candidato para ser pontuado.
	 * Comando: -a <nome-caminho-arquivo-xml> <num-semestres-sem-reprovacao>
	 * @param xmlPath - String que indica o caminho ate o arquivo, deve conter a extensao .xml
	 * @param numeroSemestreSemReprovacao - String, numero de semestres sem reprovar na pos-graduacao.
	 * @return Candidato com o lattes e outros parametros lidos. 
	 */
	public Candidato novoCandidato(String xmlPath, String numeroSemestreSemReprovacao) {
		Candidato candidato = new Candidato(xmlPath);
		
		if (numeroSemestreSemReprovacao.equals("") || numeroSemestreSemReprovacao == null) {
			numeroSemestreSemReprovacao = "0";
		}
		
		candidato.setNumeroSemestreSemReprovacao(Integer.parseInt(numeroSemestreSemReprovacao));
		return candidato;
	}
	
	
	/** 
	 * Gera a saida referente aos premios recebidos por cada candidato.
	 * Comando: -pr
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 * @return String, candidatos, sua pontuacao e os premios, opcionalmente.
	 */
	public String exibePremios(boolean verboso) {
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
		return conteudo.toString();
	}
	
	


	/** 
	 * Gera a saida referente aos artigos completos no Qualis Restrito.
	 * Conferencias e periodicos A1, A2 ou B1.
	 * Comando: -ar
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 * @return String, candidatos, sua pontuacao e os artigos, opcionalmente.
	 */
	public String exibeArtigosQualisRestrito(boolean verboso) {
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
		return conteudo.toString();
	}
	
	
		
	/** 
	 * Gera a saida referente aos artigos completos fora do Qualis Restrito.
	 * Conferencias e periodicos B2, B3, B4 e B5.
	 * Comando: -anr
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 * @return String, candidatos, sua pontuacao e os artigos, opcionalmente.
	 */
	public String exibeArtigosQualisCompleto(boolean verboso) {
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
		return conteudo.toString();
	}
	
	

	/** 
	 * Gera a saida referente a participacao em eventos classificados.
	 * Eventos A1, A2, B1, B2, B3, B4 e B5.
	 * Comando: -pe
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 * @return String, candidatos, sua pontuacao e os eventos, opcionalmente.
	 */
	public String exibeEventos(boolean verboso) {
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
		return conteudo.toString();
	}
	
	
	
	// Incompleto.
	/** 
	 * Gera a saida referente a existencia de vinculo com a UNIRIO.
	 * Comando: -vi
	 * @param verboso - boolean que indica se a saida deve ser completa ou nao. Comando: -v
	 * @return String, candidatos, sua pontuacao e os vinculos, opcionalmente.
	 
	public String exibeVinculos(boolean verboso) {
		StringBuilder conteudo = new StringBuilder("Nome	Quantidade de vinculos	Pontuacao de vinculos");
		
		for (Candidato candidato : candidatos) {
			conteudo.append(candidato.getNome() + "	");
			conteudo.append(candidato.getVinculos().size() + "	");
			conteudo.append(candidato.getPontuacaoVinculos());
			
			if (verboso && candidato.getVinculos().size() > 0) {
				// Exibe detalhes dos vinculos.
				conteudo.append("\r\nAno	Evento	Qualis"); // o ano, vínculo associado.
				for (Evento evento : candidato.getVinculos()) {
					conteudo.append(evento);
					conteudo.append("\r\n");
				}
			}
			conteudo.append("\r\n");
		}
		return conteudo.toString();
	}
	*/
	
	
	
	

}
