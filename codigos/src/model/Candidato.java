package model;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.Constantes;
import utils.XmlUtils;

public class Candidato {
	
	// Curriculo lattes carregado:
	private Document lattes;
	
	private String nome;
	private int quantidadePremios;
	private ArrayList<Artigo> artigosCompletos = new ArrayList<Artigo>();
	
	private ArrayList<Artigo> artigosCompletosQualisRestrito = new ArrayList<Artigo>();
	private ArrayList<Artigo> artigosCompletosQualisCompleto = new ArrayList<Artigo>();
	
	private ArrayList<Evento> eventos = new ArrayList<Evento>();
	private ArrayList<ProjetoPesquisa> projetosPesquisa = new ArrayList<ProjetoPesquisa>();
	private ArrayList<FormacaoAcademica> formacoesAcademicas = new ArrayList<FormacaoAcademica>();
	private ArrayList<AtuacaoProfissional> atuacoesProfissionais = new ArrayList<AtuacaoProfissional>();
	private ArrayList<Premio> premios = new ArrayList<Premio>();
	
	private int numeroSemestreSemReprovacao;
	private boolean possuiVinculoInstituicao; // Deve ser calculado atraves de outros campos do lattes.
	// Pontuacao do candidato apos avaliacao da Comissao de Bolsas
	private int pontuacao = 0;
		
	
	public Candidato(String xmlPath) {
		try {
			lattes = XmlUtils.lerXml(xmlPath, "CURRICULO-VITAE");
			setNome();
			setPremios();
			setArtigosCompletos();
			setAtuacoesProfissionais();
			setEventos();
			setFormacoesAcademicas();
			setProjetosPesquisa();
		} catch (Exception e) {
			System.out.println("Erro na leitura do lattes.");
		}
	}
	
	
	public int getNumeroSemestreSemReprovacao() {
		return numeroSemestreSemReprovacao;
	}


	public void setNumeroSemestreSemReprovacao(int numeroSemestreSemReprovacao) {
		this.numeroSemestreSemReprovacao = numeroSemestreSemReprovacao;
	}
	
	
	private void setNome() {
		nome = XmlUtils.getValorAtributo(lattes, "DADOS-GERAIS", "NOME-COMPLETO");
	}
	public String getNome() {
		return nome;
	}
	
	
	// Numero de premios recebidos nos ultimos 10 anos.
	// Itens na secao "PREMIOS-TITULOS" do lattes
	public void setPremios() {
		NodeList nos = XmlUtils.getNos(lattes, "PREMIO-TITULO");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);
			
			// Dados do premio:
			String nome = XmlUtils.getValorAtributo(no, "NOME-DO-PREMIO-OU-TITULO");
			String anoPremiacao = XmlUtils.getValorAtributo(no, "ANO-DA-PREMIACAO");
			
			int ano = 0;
			if (anoPremiacao != null) {
				ano = Integer.parseInt(anoPremiacao);
			}
			
			if (ano >= Constantes.DATA_LIMITE) {
				premios.add(new Premio(ano, nome));
			}
			
		}
		
	}
	
	
	public ArrayList<Premio> getPremios() {
		return premios;
	}
	
	
	/**
	 * Adiciona artigos publicados em periodicos ao ArrayList<Artigo> artigos.
	 */
	private void adicionarArtigosPeriodicos() {
		
		NodeList nos = XmlUtils.getNos(lattes, "ARTIGOS-PUBLICADOS");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);
			
			Node dadosBasicos = no.getFirstChild();
			String natureza = XmlUtils.getValorAtributo(dadosBasicos, "NATUREZA");
			
			if (natureza.equals("COMPLETO")) {
				// Dados do artigo:
				String anoPublicacao = XmlUtils.getValorAtributo(dadosBasicos, "ANO-DO-ARTIGO");
				String titulo = XmlUtils.getValorAtributo(dadosBasicos, "TITULO-DO-ARTIGO");
				
				// Dados do periodico:
				Node detalhes = dadosBasicos.getNextSibling();
				String nomePeriodico = XmlUtils.getValorAtributo(detalhes, "TITULO-DO-PERIODICO-OU-REVISTA");
				String issn = XmlUtils.getValorAtributo(detalhes, "ISSN");
				Periodico periodico = new Periodico(issn, nomePeriodico);
				
				int ano = 0;
				if (anoPublicacao != null) {
					ano = Integer.parseInt(anoPublicacao);
				}
				if (ano >= Constantes.DATA_LIMITE) {
					artigosCompletos.add(new Artigo(titulo, periodico, ano));
				}
				
			}
			
		}
	}
	
	
	/**
	 * Adiciona artigos publicados em conferencias ao ArrayList<Artigo> artigos.
	 */
	private void adicionarArtigosConferencias() {
		NodeList nos = XmlUtils.getNos(lattes, "TRABALHOS-EM-EVENTOS");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);
			
			Node dadosBasicos = no.getFirstChild();
			String natureza = XmlUtils.getValorAtributo(dadosBasicos, "NATUREZA");
			
			if (natureza.equals("COMPLETO")) {
				// Dados do artigo:
				String anoPublicacao = XmlUtils.getValorAtributo(dadosBasicos, "ANO-DO-TRABALHO");
				String titulo = XmlUtils.getValorAtributo(dadosBasicos, "TITULO-DO-TRABALHO");
				
				// Dados da conferencia:
				Node detalhes = dadosBasicos.getNextSibling();
				String nomeEvento = XmlUtils.getValorAtributo(detalhes, "NOME-DO-EVENTO");
				Conferencia conferencia = new Conferencia(nomeEvento);
				
				int ano = 0;
				if (anoPublicacao != null) {
					ano = Integer.parseInt(anoPublicacao);
				}
				if (ano >= Constantes.DATA_LIMITE) {
					artigosCompletos.add(new Artigo(titulo, conferencia, ano));
				}
				
			}
			
		}
	}
	
	
	/**
	 * Captura todos os artigos do lattes e divide entre os que sao qualis restrito e qualis completo.
	 */
	private void setArtigosCompletos() {
		adicionarArtigosPeriodicos();
		adicionarArtigosConferencias();
	
		// Divide em qualis restrito e completo:
		for (Artigo artigo : artigosCompletos) {
			QualisEnum qualis = artigo.getQualis();
			
			if (qualis == QualisEnum.A1 || 
				qualis == QualisEnum.A2 || 
				qualis == QualisEnum.B1) {
				artigosCompletosQualisRestrito.add(artigo);
			}
			
			if (qualis == QualisEnum.B2 || 
				qualis == QualisEnum.B3 || 
				qualis == QualisEnum.B4 || 
				qualis == QualisEnum.B5) {
				artigosCompletosQualisCompleto.add(artigo);
			}
		}
	
	}
	
	public ArrayList<Artigo> getArtigosCompletosQualisRestrito() {
		return artigosCompletosQualisRestrito;
	}
	
	public ArrayList<Artigo> getArtigosCompletosQualisCompleto() {
		return artigosCompletosQualisCompleto;
	}
	
	// Funcao auxiliar para adicionar nos do XML lattes na lista de eventos do candidato.
	private void adicionarEventosListaEventos(NodeList nos) {
		Node no;
		for (int contador = 0; contador < nos.getLength(); contador++) {
			no = nos.item(contador);
			eventos.add(new Evento(XmlUtils.getValorAtributo(no, "NOME-DO-EVENTO")));
		}
	}
	
	
	// Pode ser um congresso, simposio, encontro ou outro.
	private void setEventos() {
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DADOS-BASICOS-DA-PARTICIPACAO-EM-SIMPOSIO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-CONGRESSO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-ENCONTRO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DE-OUTRAS-PARTICIPACOES-EM-EVENTOS-CONGRESSOS"));
	}
	
	
	
	private void setProjetosPesquisa() {
		
		NodeList nos = XmlUtils.getNos(lattes, "PROJETO-DE-PESQUISA");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);

			String anoVinculo = XmlUtils.getValorAtributo(no, "ANO-INICIO");
			String titulo = XmlUtils.getValorAtributo(no, "NOME-DO-PROJETO");
			String coordenadorProjeto = "";
			
			NodeList integrantes = no.getFirstChild().getChildNodes(); // EQUIPE-DO-PROJETO -> INTEGRANTES-DO-PROJETO
			for (int contador2 = 0; contador2 < integrantes.getLength(); contador2++) {
				Node integrante = integrantes.item(contador2);
				String nome = XmlUtils.getValorAtributo(integrante, "NOME-COMPLETO");
				String ehCoordenador = XmlUtils.getValorAtributo(integrante, "FLAG-RESPONSAVEL");
				if (ehCoordenador.equals("SIM")) {
					coordenadorProjeto = nome;
				}
			}
			
			if (anoVinculo == null || anoVinculo == "") {
				anoVinculo = "0";
			}
			projetosPesquisa.add(new ProjetoPesquisa(Integer.parseInt(anoVinculo), titulo, coordenadorProjeto));
		}
		
	}
	
	
	private void setFormacoesAcademicas() {
		NodeList nos = XmlUtils.getNos(lattes, "FORMACAO-ACADEMICA-TITULACAO");
		
		if (nos.getLength() > 0) {
			NodeList formacoes = nos.item(0).getChildNodes();
			
			for (int contador = 0; contador < formacoes.getLength(); contador++) {
				Node no = formacoes.item(contador);
				
				String dataFormacao = XmlUtils.getValorAtributo(no, "ANO-DE-CONCLUSAO");
				String nomeUniversidade = XmlUtils.getValorAtributo(no, "NOME-INSTITUICAO");
				String titulo = no.getNodeName();
				
				if (dataFormacao == null || dataFormacao == "") {
					dataFormacao = "0";
				}
				
				formacoesAcademicas.add(new FormacaoAcademica(Integer.parseInt(dataFormacao), nomeUniversidade, titulo));
				
			}

		}
		
	}
	
	

	private void setAtuacoesProfissionais() {
		
		NodeList nos = XmlUtils.getNos(lattes, "VINCULOS"); // ATUACAO-PROFISSIONAL -> VINCULOS
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);

			String anoInicio = XmlUtils.getValorAtributo(no, "ANO-INICIO");
			String anoFim = XmlUtils.getValorAtributo(no, "ANO-FIM");
			String localAtuacao = XmlUtils.getValorAtributo(no.getParentNode(), "NOME-INSTITUICAO");
			String vinculo = XmlUtils.getValorAtributo(no, "TIPO-DE-VINCULO");
			
			if (anoInicio == null || anoInicio == "") {
				anoInicio = "0";
			}
			
			if (anoFim == null || anoFim == "") {
				anoFim = "0";
			}
			
			atuacoesProfissionais.add(new AtuacaoProfissional(Integer.parseInt(anoInicio), Integer.parseInt(anoFim), localAtuacao, vinculo));
		}	
		
	}
	

	public int getPontuacao() {
		pontuacao = 0;
		
		// RN1 - O candidato recebe um ponto por cada semestre cursado sem reprovacao no curso que a bolsa esta sendo pleiteada
		pontuacao += numeroSemestreSemReprovacao;
		
		// Premios:
		pontuacao += getPontuacaoPremios();
		
		// Artigos:
		pontuacao += getPontuacaoQualisRestrito();
		pontuacao += getPontuacaoQualisCompleto();
				
		
		// RN5 - O candidato recebe um ponto por cada evento participado. O maximo de pontos por esse requisito sao cinco.
		
		// RN6 - O candidato recebe um ponto se houver registro de vinculo com a UNIRIO nos ultimos 10 anos, seja por participacao 
		// em projetos, bolsas de pesquisa, representacao discente ou similar.
		
		return pontuacao;
		
	}

	
	/**
	 * RN2 - O candidato recebe um ponto por cada premio recebido nos ultimos 10 anos.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoPremios() {
		return premios.size();
	}
	
	
	/**
	 * RN3 - O candidato recebe tres pontos por cada artigo pontuado como A1, A2 ou B1 
	 * na Qualis da Computacao nos ultimos 10 anos.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoQualisRestrito() {
		return artigosCompletosQualisRestrito.size() * 3;
	
	}
	
	
	/**
	 * RN4 - O candidato recebe um ponto por cada artigo pontuado como B2, B3, B4 ou B5 
	 * na Qualis da Computacao nos ultimos 10 anos.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoQualisCompleto() {
		return artigosCompletosQualisCompleto.size();
	}
	

}
