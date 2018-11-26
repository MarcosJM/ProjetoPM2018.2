package model;

import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import controller.ProfessorController;
import utils.Constantes;
import utils.XmlUtils;

/**
 * Classe que, a partir da leitura do lattes, calcula e guarda 
 * as informacoes pertinentes para cada Candidato.
 *
 */
public class Candidato {
	
	// Curriculo lattes carregado:
	private Document lattes;
	
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
		
	// Construtor. Le o lattes e o numero de semestre sem reprovacoes, 
	// calculando os demais campos da classe. 
	public Candidato(String xmlPath, int numeroSemestreSemReprovacao) {
		try {
			lattes = XmlUtils.lerXml(xmlPath, "CURRICULO-VITAE");
			
			setNome();
			setPremios();
			setArtigosCompletos();
			setAtuacoesProfissionais();
			setEventos();
			setFormacoesAcademicas();
			setProjetosPesquisa();
			setVinculoInstituicao();
			
			this.numeroSemestreSemReprovacao = numeroSemestreSemReprovacao;
			setPontuacao();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro na leitura do lattes.");
		}
	}
	
		
	// Getters e setters.
	private void setNome() {
		nome = XmlUtils.getValorAtributo(lattes, "DADOS-GERAIS", "NOME-COMPLETO");
	}
	
	public String getNome() {
		return nome;
	}
	
	
	/**
	 * Numero de premios recebidos nos ultimos 10 anos.
	 * Itens na secao "PREMIOS-TITULOS" do lattes.
	 */
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
			
			if (ano >= Constantes.ANO_LIMITE) {
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
		
		NodeList nos = XmlUtils.getNos(lattes, "ARTIGO-PUBLICADO");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);
			
			Node dadosBasicos = no.getFirstChild();
			String natureza = XmlUtils.getValorAtributo(dadosBasicos, "NATUREZA");
			
			if (natureza.equals("COMPLETO")) {
				// Dados do Artigo:
				String anoPublicacao = XmlUtils.getValorAtributo(dadosBasicos, "ANO-DO-ARTIGO");
				String titulo = XmlUtils.getValorAtributo(dadosBasicos, "TITULO-DO-ARTIGO");
				
				// Dados do Periodico:
				Node detalhes = dadosBasicos.getNextSibling();
				String nomePeriodico = XmlUtils.getValorAtributo(detalhes, "TITULO-DO-PERIODICO-OU-REVISTA");
				String issn = XmlUtils.getValorAtributo(detalhes, "ISSN");
				Periodico periodico = new Periodico(issn, nomePeriodico);
				
				int ano = 0;
				if (anoPublicacao != null) {
					ano = Integer.parseInt(anoPublicacao);
				}
				if (ano >= Constantes.ANO_LIMITE) {
					artigosCompletos.add(new Artigo(titulo, periodico, ano));
				}	
			}
		}
	}
	
	
	/**
	 * Adiciona artigos publicados em conferencias ao ArrayList<Artigo> artigos.
	 */
	private void adicionarArtigosConferencias() {
		NodeList nos = XmlUtils.getNos(lattes, "TRABALHO-EM-EVENTOS");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);
			
			Node dadosBasicos = no.getFirstChild();
			String natureza = XmlUtils.getValorAtributo(dadosBasicos, "NATUREZA");
			
			if (natureza.equals("COMPLETO")) {
				// Dados do Artigo:
				String anoPublicacao = XmlUtils.getValorAtributo(dadosBasicos, "ANO-DO-TRABALHO");
				String titulo = XmlUtils.getValorAtributo(dadosBasicos, "TITULO-DO-TRABALHO");
				
				// Dados da Conferencia:
				Node detalhes = dadosBasicos.getNextSibling();
				String nomeEvento = XmlUtils.getValorAtributo(detalhes, "NOME-DO-EVENTO");
				Conferencia conferencia = new Conferencia(nomeEvento);
				
				int ano = 0;
				if (anoPublicacao != null) {
					ano = Integer.parseInt(anoPublicacao);
				}
				if (ano >= Constantes.ANO_LIMITE) {
					artigosCompletos.add(new Artigo(titulo, conferencia, ano));
				}
				
			}
			
		}
	}
	
	
	/**
	 * Captura todos os artigos do lattes e divide entre os 
	 * que sao qualis restrito e qualis completo.
	 */
	private void setArtigosCompletos() {
		adicionarArtigosPeriodicos();
		adicionarArtigosConferencias();
	
		// Divide em qualis restrito e completo e
		// separa nos ArrayLists especificos:
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
	
	
	/**
	 * Funcao auxiliar para adicionar nos do XML lattes no ArrayList<Evento> do candidato.
	 * @param nos - NodeList, nos contendo eventos de varios tipos.
	 */
	private void adicionarEventosListaEventos(NodeList nos) {
		Node no;
		for (int contador = 0; contador < nos.getLength(); contador++) {
			no = nos.item(contador);
			Node dadosBasicos = no.getPreviousSibling();
			
			// Qualquer tipo de Evento tem ano e nome.
			String ano = XmlUtils.getValorAtributo(dadosBasicos, "ANO");
			String nome = XmlUtils.getValorAtributo(no, "NOME-DO-EVENTO");
			
			if (ano == null || ano == "") {
				ano = "0";
			}
			
			Evento evento = new Evento(Integer.parseInt(ano), nome);
			QualisEnum qualis = evento.getQualis();
			
			// Tem classificacao de A1 a B5.
			if (qualis != null) {
				eventos.add(evento);
			}
		}
	}

	
	
	/**
	 * Adiciona Eventos no ArrayList<Evento>, separando e adicionando
	 * os Eventos por tipo: congresso, simposio, encontro ou outro.
	 */
	private void setEventos() {
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-SIMPOSIO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-CONGRESSO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-ENCONTRO"));
		adicionarEventosListaEventos(XmlUtils.getNos(lattes, "DETALHAMENTO-DE-OUTRAS-PARTICIPACOES-EM-EVENTOS-CONGRESSOS"));
	}
	
	public ArrayList<Evento> getEventos() {
		return eventos;
	}
	
	
	/**
	 * Projetos dos ultimos 10 anos.
	 */
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
			
			int ano = Integer.parseInt(anoVinculo);
			if (ano >= Constantes.ANO_LIMITE) {
				projetosPesquisa.add(new ProjetoPesquisa(ano, titulo, coordenadorProjeto));
			}	
		}
	}
	
	
	/**
	 * Ano de formacao nos ultimos 10 anos.
	 */
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
					dataFormacao = String.valueOf(Constantes.SEM_ANO);
				}
				
				int ano = Integer.parseInt(dataFormacao);
				if (ano >= Constantes.ANO_LIMITE) {
					formacoesAcademicas.add(new FormacaoAcademica(ano, nomeUniversidade, titulo));
				}			
			}
		}
	}
	
	
	/**
	 * Atuacoes nos ultimos 10 anos, ou atuais.
	 */
	private void setAtuacoesProfissionais() {
		
		NodeList nos = XmlUtils.getNos(lattes, "VINCULOS"); // ATUACAO-PROFISSIONAL -> VINCULOS
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);

			String anoInicio = XmlUtils.getValorAtributo(no, "ANO-INICIO");
			String anoFim = XmlUtils.getValorAtributo(no, "ANO-FIM");
			String localAtuacao = XmlUtils.getValorAtributo(no.getParentNode(), "NOME-INSTITUICAO");
			
			String descricaoVinculo = 
					XmlUtils.getValorAtributo(no, "TIPO-DE-VINCULO") + " - " +
					XmlUtils.getValorAtributo(no, "OUTRO-VINCULO-INFORMADO") + " - " +
					XmlUtils.getValorAtributo(no, "OUTRO-ENQUADRAMENTO-FUNCIONAL-INFORMADO");
			
			
			if (anoInicio == null || anoInicio == "") {
				anoInicio = "0";
			}
			
			// Pode ser uma atuacao vigente, sem ano final.
			if (anoFim == null || anoFim == "") {
				anoFim = String.valueOf(Constantes.SEM_ANO);
			}
			
			int anoFinal = Integer.parseInt(anoFim);
			if (anoFinal >= Constantes.ANO_LIMITE) {
				atuacoesProfissionais.add(new AtuacaoProfissional(Integer.parseInt(anoInicio), anoFinal, localAtuacao, descricaoVinculo));
			}	
		}	
	}
	
		
	/**
	 * Altera a variavel possuiVinculoInstituicao para true se nos ultimos 10 anos o candidato:
	 * 1. Participou em projetos cujo coordenador eh um professor da UNIRIO.
	 * 2. Tem formacao academica/bolsas cuja instituicao eh a UNIRIO.
	 * 3. Possui alguma atuacao profissional cuja instituicao eh a UNIRIO (aqui estao
	 * inclusas representacao discente ou outras).
	 */
	private void setVinculoInstituicao() {
		// Projetos:
		for (ProjetoPesquisa projeto : projetosPesquisa) {
			if (ProfessorController.ehProfessorUnirio(projeto.getCoordenadorProjeto())) {
				vinculos.add(new Vinculo(projeto.getAnoVinculo(), projeto.getTitulo(), "Projeto de Pesquisa"));
			}
		}
		
		
		// Formacao academica:
		for (FormacaoAcademica formacao : formacoesAcademicas) {
			if (formacao.getNomeUniversidade().contains("Universidade Federal do Estado do Rio de Janeiro")) {
				vinculos.add(new Vinculo(formacao.getDataFormacao(), formacao.getTitulo(), "Formacao academica"));
			}
		}
		
		
		// Atuacao profissional:
		for (AtuacaoProfissional atuacao : atuacoesProfissionais) {
			if (atuacao.getLocalAtuacao().contains("Universidade Federal do Estado do Rio de Janeiro")) {
				vinculos.add(new Vinculo(atuacao.getAnoFim(), atuacao.getDescricaoVinculo(), "Atuacao profissional"));
			}
		}
		
		
		if (vinculos.size() > 0) {
			possuiVinculoInstituicao = true;
		}
	}
	
	public ArrayList<Vinculo> getVinculos() {
		return vinculos;
	}

	
	/**
	 * Metodo para calculo da pontuacao geral.
	 * Usa metodos que calculam a pontuacao por categoria.
	 */
	private void setPontuacao() {
		pontuacao = 0;
		
		// RN1 - O candidato recebe um ponto por cada semestre cursado sem reprovacao no curso que a bolsa esta sendo pleiteada
		pontuacao += numeroSemestreSemReprovacao;
		
		// Premios:
		pontuacao += getPontuacaoPremios();
		
		// Artigos:
		pontuacao += getPontuacaoQualisRestrito();
		pontuacao += getPontuacaoQualisCompleto();
				
		// Eventos:
		pontuacao += getPontuacaoEventos();
		
		// Vinculos:
		pontuacao += getPontuacaoVinculos();
	}

	public int getPontuacao() {
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
	
	
	/**
	 * RN5 - O candidato recebe um ponto por cada evento participado. 
	 * O maximo de pontos por esse requisito sao cinco.
	 * @return int, quantidade de pontos dessa categoria.
	 */
	public int getPontuacaoEventos() {
		if (eventos.size() > 5) {
			return 5;
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
	public int getPontuacaoVinculos() {
		if (possuiVinculoInstituicao) {
			return 1;
		} else {
			return 0;
		}
	}


	@Override
	public String toString() {
		return nome + "	" + pontuacao;
	}
	
}
