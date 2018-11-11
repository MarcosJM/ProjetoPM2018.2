package model;

import java.util.ArrayList;

import org.w3c.dom.Document;

import utils.XmlUtils;

public class Candidato {
	
	// Curriculo lattes carregado:
	private Document lattes;
	
	public Candidato(String xmlPath) {
		try {
			lattes = XmlUtils.lerXml(xmlPath, "CURRICULO-VITAE");
		} catch (Exception e) {
			System.out.println("Erro na leitura do lattes.");
		}
	}
	
	public String getNome() {
		return XmlUtils.getValorAtributo(lattes, "DADOS-GERAIS", "NOME-COMPLETO");
	}
	
	
	// Numero de premios recebidos nos ultimos 10 anos.
	// Itens na secao "PREMIOS-TITULOS" do lattes
	public int getQuantidadePremios() {
		return XmlUtils.getQuantidadeNos(lattes, "PREMIO-TITULO");
	}
	
	private ArrayList<Artigo> artigosCompletos = new ArrayList<Artigo>();
	
	private ArrayList<Conferencia> conferencias = new ArrayList<Conferencia>();

	private ArrayList<ProjetoPesquisa> projetosPesquisa = new ArrayList<ProjetoPesquisa>();

	private ArrayList<FormacaoAcademica> formacoesAcademicas = new ArrayList<FormacaoAcademica>();

	private ArrayList<AtuacaoProfissional> atuacoesProfissionais = new ArrayList<AtuacaoProfissional>();
	
	public void getPeriodicos() {
		
		
	}

	// Pontuacao do candidato apos avaliacao da Comissao de Bolsas
	private float pontuacao = 0;

}
