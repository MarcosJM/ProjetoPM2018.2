package controller;

import java.util.ArrayList;

import model.Candidato;
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
	 * @return String, candidatos, sua pontuacao e os premios.
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
	
	
	// Incompleto
	public String exibeArtigosQualisRestrito(boolean verboso) {
		return "";
	}
	// Incompleto
	public String exibeArtigosQualisCompleto(boolean verboso) {
		return "";
	}

}
