package model;

import java.util.ArrayList;

/**
 * Classe que, a partir da leitura do lattes, calcula e guarda 
 * as informacoes pertinentes para cada Candidato.
 *
 */
public class Candidato {
		
	private String nome;
	private ArrayList<Artigo> artigosCompletos = new ArrayList<Artigo>(); // Todos os artigos.
	
	// Artigos separados por qualis.
	private ArrayList<Artigo> artigosCompletosQualisRestrito = new ArrayList<Artigo>();
	private ArrayList<Artigo> artigosCompletosQualisCompleto = new ArrayList<Artigo>();
	
	// Demais campos do lattes.
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	private ArrayList<ProjetoPesquisa> projetosPesquisa = new ArrayList<ProjetoPesquisa>();
	private ArrayList<FormacaoAcademica> formacoesAcademicas = new ArrayList<FormacaoAcademica>();
	private ArrayList<AtuacaoProfissional> atuacoesProfissionais = new ArrayList<AtuacaoProfissional>();
	private ArrayList<Vinculo> vinculos = new ArrayList<Vinculo>();
	private ArrayList<Premio> premios = new ArrayList<Premio>();
	
	private int numeroSemestreSemReprovacao; // Parametro de entrada no construtor.
	private boolean possuiVinculoInstituicao = false; // Deve ser calculado atraves de outros campos do lattes.
	
	// Pontuacao do candidato apos avaliacao da Comissao de Bolsas
	private int pontuacao = 0;
	
	
	public Candidato() {}
	
	// Construtor para receber o numero de semestres sem reprovacao
	public Candidato(int numeroSemestreSemReprovacao) {
		this.numeroSemestreSemReprovacao = numeroSemestreSemReprovacao;
	}
	
	/**
	 * Abaixo, metodos getters e setters para os atributos da classe.
	 * 
	 */
	
	public ArrayList<Artigo> getArtigosCompletos() {
		return artigosCompletos;
	}


	public void setArtigosCompletos(ArrayList<Artigo> artigosCompletos) {
		this.artigosCompletos = artigosCompletos;
	}
	
	public void addArtigoCompleto(Artigo artigo) {
		this.artigosCompletos.add(artigo);
	}


	public ArrayList<ProjetoPesquisa> getProjetosPesquisa() {
		return projetosPesquisa;
	}


	public void setProjetosPesquisa(ArrayList<ProjetoPesquisa> projetosPesquisa) {
		this.projetosPesquisa = projetosPesquisa;
	}
	
	public void addProjetoPesquisa(ProjetoPesquisa projetoPesquisa) {
		this.projetosPesquisa.add(projetoPesquisa);
	}


	public ArrayList<FormacaoAcademica> getFormacoesAcademicas() {
		return formacoesAcademicas;
	}


	public void setFormacoesAcademicas(ArrayList<FormacaoAcademica> formacoesAcademicas) {
		this.formacoesAcademicas = formacoesAcademicas;
	}
	
	public void addFormacaoAcademica(FormacaoAcademica formacaoAcademica) {
		this.formacoesAcademicas.add(formacaoAcademica);
	}


	public ArrayList<AtuacaoProfissional> getAtuacoesProfissionais() {
		return atuacoesProfissionais;
	}


	public void setAtuacoesProfissionais(ArrayList<AtuacaoProfissional> atuacoesProfissionais) {
		this.atuacoesProfissionais = atuacoesProfissionais;
	}
	
	
	public void addAtuacaoProfissional(AtuacaoProfissional atuacaoProfissional) {
		this.atuacoesProfissionais.add(atuacaoProfissional);
	}


	public int getNumeroSemestreSemReprovacao() {
		return numeroSemestreSemReprovacao;
	}


	public void setNumeroSemestreSemReprovacao(int numeroSemestreSemReprovacao) {
		this.numeroSemestreSemReprovacao = numeroSemestreSemReprovacao;
	}


	public boolean isPossuiVinculoInstituicao() {
		return possuiVinculoInstituicao;
	}


	public void setPossuiVinculoInstituicao(boolean possuiVinculoInstituicao) {
		this.possuiVinculoInstituicao = possuiVinculoInstituicao;
	}

	
	public String getNome() {
		return nome;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setArtigosCompletosQualisRestrito(ArrayList<Artigo> artigosCompletosQualisRestrito) {
		this.artigosCompletosQualisRestrito = artigosCompletosQualisRestrito;
	}
	
	
	public void addArtigoCompletoQualisRestrito(Artigo artigoCompletoQualisRestrito) {
		this.artigosCompletosQualisRestrito.add(artigoCompletoQualisRestrito);
	}


	public void setArtigosCompletosQualisCompleto(ArrayList<Artigo> artigosCompletosQualisCompleto) {
		this.artigosCompletosQualisCompleto = artigosCompletosQualisCompleto;
	}
	
	
	public void addArtigosCompletosQualisCompleto(Artigo artigoCompletoQualisCompleto) {
		this.artigosCompletosQualisCompleto.add(artigoCompletoQualisCompleto);
	}


	public ArrayList<Premio> getPremios() {
		return premios;
	}


	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}
	
	
	public void addEvento(Evento evento) {
		this.eventos.add(evento);
	}
	
	
	public ArrayList<Vinculo> getVinculos() {
		return vinculos;
	}


	public void setVinculos(ArrayList<Vinculo> vinculos) {
		this.vinculos = vinculos;
	}
	
	
	public void addVinculo(Vinculo vinculo) {
		this.vinculos.add(vinculo);
	}


	public void setPremios(ArrayList<Premio> premios) {
		this.premios = premios;
	}
	
	
	public void addPremio(Premio premio) {
		this.premios.add(premio);
	}
	
	
	public int getPontuacao() {
		return pontuacao;
	}

	
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}	
	
	public ArrayList<Artigo> getArtigosCompletosQualisRestrito() {
		return artigosCompletosQualisRestrito;
	}
	
	public ArrayList<Artigo> getArtigosCompletosQualisCompleto() {
		return artigosCompletosQualisCompleto;
	}
	
	
	public ArrayList<Evento> getEventos() {
		return eventos;
	}
		
	/**
	 * Funcao para retornar o nome do candidato e sua pontuacao.
	 */
	@Override
	public String toString() {
		return nome + "	" + pontuacao;
	}
	
}
