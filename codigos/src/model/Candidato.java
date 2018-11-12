package model;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.XmlUtils;

public class Candidato {
	
	// Curriculo lattes carregado:
	private Document lattes;
	
	private String nome;
	private int quantidadePremios;
	private ArrayList<Artigo> artigosCompletos = new ArrayList<Artigo>();
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	private ArrayList<ProjetoPesquisa> projetosPesquisa = new ArrayList<ProjetoPesquisa>();
	private ArrayList<FormacaoAcademica> formacoesAcademicas = new ArrayList<FormacaoAcademica>();
	private ArrayList<AtuacaoProfissional> atuacoesProfissionais = new ArrayList<AtuacaoProfissional>();
	
	
	public Candidato(String xmlPath) {
		try {
			lattes = XmlUtils.lerXml(xmlPath, "CURRICULO-VITAE");
			setNome();
			setQuantidadePremios();
			setArtigosCompletos();
			setAtuacoesProfissionais();
			setEventos();
			setFormacoesAcademicas();
			setProjetosPesquisa();
		} catch (Exception e) {
			System.out.println("Erro na leitura do lattes.");
		}
	}
	
	private void setNome() {
		nome = XmlUtils.getValorAtributo(lattes, "DADOS-GERAIS", "NOME-COMPLETO");
	}
	public String getNome() {
		return nome;
	}
	
	
	// Numero de premios recebidos nos ultimos 10 anos.
	// Itens na secao "PREMIOS-TITULOS" do lattes
	public void setQuantidadePremios() {
		quantidadePremios = XmlUtils.getQuantidadeNos(lattes, "PREMIO-TITULO");
	}
	public int getQuantidadePremios() {
		return quantidadePremios;
	}
	
	
	private void setArtigosCompletos() {
		NodeList nos = XmlUtils.getNos(lattes, "ARTIGOS-PUBLICADOS");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);
			
			// Dados do artigo:
			String anoPublicacao = XmlUtils.getValorAtributo(no, "ANO-DO-ARTIGO");
			String titulo = XmlUtils.getValorAtributo(no, "TITULO-DO-ARTIGO");
			
			// Dados do periodico:
			Node detalhes = no.getFirstChild();
			String nomePeriodico = XmlUtils.getValorAtributo(detalhes, "TITULO-DO-PERIODICO-OU-REVISTA");
			String issn = XmlUtils.getValorAtributo(detalhes, "ISSN");
			Periodico periodico = new Periodico(issn, nomePeriodico);
			
			int ano = 0;
			if (anoPublicacao != null) {
				ano = Integer.parseInt(anoPublicacao);
			}
			artigosCompletos.add(new Artigo(titulo, periodico, ano));
		}
		
	}
	
	
	// Funcao auxiliar para adicionar nos do XML lattes na lista de eventos do candidato.
	private void adicionarEventosListaEventos(NodeList nos) {
		Node no;
		for (int contador = 0; contador < nos.getLength(); contador++) {
			no = nos.item(contador);
			eventos.add(new Evento(XmlUtils.getValorAtributo(no, "NOME-DO-EVENTO")));
		}
	}
	
	
	// Pode ser um congresso, simpósio, encontro ou outro.
	private void setEventos() {
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DADOS-BASICOS-DA-PARTICIPACAO-EM-SIMPOSIO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-CONGRESSO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-ENCONTRO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DE-OUTRAS-PARTICIPACOES-EM-EVENTOS-CONGRESSOS"));
	}
	
	private void setProjetosPesquisa() {}
	
	private void setFormacoesAcademicas() {}

	private void setAtuacoesProfissionais() {}
	

	// Pontuacao do candidato apos avaliacao da Comissao de Bolsas
	private float pontuacao = 0;

}
