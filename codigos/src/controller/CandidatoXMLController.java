package controller;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Artigo;
import model.AtuacaoProfissional;
import model.Candidato;
import model.Conferencia;
import model.Evento;
import model.FormacaoAcademica;
import model.Periodico;
import model.Premio;
import model.ProjetoPesquisa;
import model.QualisEnum;
import model.Vinculo;
import utils.Constantes;
import utils.XmlUtils;

/**
 * Classe para carregar um candidato a partir do arquivo XML.
 *
 */
public class CandidatoXMLController {
	
	/**
	 * Implementacao do Singleton.
	 */
	
	private static CandidatoXMLController unicaInstancia;
	
	private CandidatoXMLController() {}
	
	public static CandidatoXMLController getInstancia() {
		
		if (unicaInstancia == null) {
			unicaInstancia = new CandidatoXMLController();
		} 
		
		return unicaInstancia;
    }
	
	// Funcao para carregar o Nome do candidato com base do arquivo lattes.
	private void carregarNome(Candidato candidato, Document lattes) {
		candidato.setNome(XmlUtils.getValorAtributo(lattes, "DADOS-GERAIS", "NOME-COMPLETO"));
	}
	
	/**
	 * Numero de premios recebidos nos ultimos 10 anos.
	 * Itens na secao "PREMIOS-TITULOS" do lattes.
	 */
	public void carregarPremios(Candidato candidato, Document lattes) {
		NodeList nos = XmlUtils.getNos(lattes, "PREMIO-TITULO");
		for (int contador = 0; contador < nos.getLength(); contador++) {
			Node no = nos.item(contador);
			
			// Dados do premio:
			String nome = XmlUtils.getValorAtributo(no, "NOME-DO-PREMIO-OU-TITULO");
			String anoPremiacao = XmlUtils.getValorAtributo(no, "ANO-DA-PREMIACAO");
			
			int ano = Constantes.ANO_INICIAL_PADRAO;
			if (anoPremiacao != null) {
				ano = Integer.parseInt(anoPremiacao);
			}
			
			if (ano >= Constantes.ANO_LIMITE) {
				candidato.addPremio(new Premio(ano, nome));
			}	
		}	
	}
	
	/**
	 * Adiciona artigos publicados em periodicos ao ArrayList<Artigo> artigos.
	 */
	private void adicionarArtigosPeriodicos(Candidato candidato, Document lattes) {
		
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
				
				int ano = Constantes.ANO_INICIAL_PADRAO;
				if (anoPublicacao != null) {
					ano = Integer.parseInt(anoPublicacao);
				}
				if (ano >= Constantes.ANO_LIMITE) {
					candidato.addArtigoCompleto(new Artigo(titulo, periodico, ano));
				}	
			}
		}
	}
	
	/**
	 * Adiciona artigos publicados em conferencias ao ArrayList<Artigo> artigos.
	 */
	private void adicionarArtigosConferencias(Candidato candidato, Document lattes) {
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
				
				int ano = Constantes.ANO_INICIAL_PADRAO;
				if (anoPublicacao != null) {
					ano = Integer.parseInt(anoPublicacao);
				}
				if (ano >= Constantes.ANO_LIMITE) {
					candidato.addArtigoCompleto(new Artigo(titulo, conferencia, ano));
				}
				
			}
			
		}
	}
	
	/**
	 * Captura todos os artigos do lattes e divide entre os 
	 * que sao qualis restrito e qualis completo.
	 */
	private void carregarArtigosCompletos(Candidato candidato, Document lattes) {
		adicionarArtigosPeriodicos(candidato, lattes);
		adicionarArtigosConferencias(candidato, lattes);
	
		// Divide em qualis restrito e completo e
		// separa nos ArrayLists especificos:
		for (Artigo artigo : candidato.getArtigosCompletos()) {
			QualisEnum qualis = artigo.getQualis();
			
			if (qualis == QualisEnum.A1 || 
				qualis == QualisEnum.A2 || 
				qualis == QualisEnum.B1) {
				candidato.addArtigoCompletoQualisRestrito(artigo);
			}
			
			if (qualis == QualisEnum.B2 || 
				qualis == QualisEnum.B3 || 
				qualis == QualisEnum.B4 || 
				qualis == QualisEnum.B5) {
				candidato.addArtigosCompletosQualisCompleto(artigo);
			}
		}
	}
	
	
	/**
	 * Funcao auxiliar para adicionar nos do XML lattes no ArrayList<Evento> do candidato.
	 * @param nos - NodeList, nos contendo eventos de varios tipos.
	 */
	private void adicionarEventosListaEventos(Candidato candidato, NodeList nos) {
		Node no;
		for (int contador = 0; contador < nos.getLength(); contador++) {
			no = nos.item(contador);
			Node dadosBasicos = no.getPreviousSibling();
			
			// Qualquer tipo de Evento tem ano e nome.
			String ano = XmlUtils.getValorAtributo(dadosBasicos, "ANO");
			String nome = XmlUtils.getValorAtributo(no, "NOME-DO-EVENTO");
			
			if (ano == null || ano == "") {
				ano = String.valueOf(Constantes.ANO_INICIAL_PADRAO);
			}
			
			Evento evento = new Evento(Integer.parseInt(ano), nome);
			QualisEnum qualis = evento.getQualis();
			
			// Tem classificacao de A1 a B5.
			if (qualis != null) {
				candidato.addEvento(evento);
			}
		}
	}
	
	/**
	 * Adiciona Eventos no ArrayList<Evento>, separando e adicionando
	 * os Eventos por tipo: congresso, simposio, encontro ou outro.
	 */
	private void carregarEventos(Candidato candidato, Document lattes) {
		adicionarEventosListaEventos(candidato, XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-SIMPOSIO"));
		adicionarEventosListaEventos(candidato, XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-CONGRESSO"));
		adicionarEventosListaEventos(candidato, XmlUtils.getNos(lattes, "DETALHAMENTO-DA-PARTICIPACAO-EM-ENCONTRO"));
		adicionarEventosListaEventos(candidato, XmlUtils.getNos(lattes, "DETALHAMENTO-DE-OUTRAS-PARTICIPACOES-EM-EVENTOS-CONGRESSOS"));
	}
	
	/**
	 * Projetos dos ultimos 10 anos.
	 */
	private void carregarProjetosPesquisa(Candidato candidato, Document lattes) {
		
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
				anoVinculo = String.valueOf(Constantes.ANO_INICIAL_PADRAO);
			}
			
			int ano = Integer.parseInt(anoVinculo);
			if (ano >= Constantes.ANO_LIMITE) {
				candidato.addProjetoPesquisa(new ProjetoPesquisa(ano, titulo, coordenadorProjeto));
			}	
		}
	}
	
	/**
	 * Ano de formacao nos ultimos 10 anos.
	 */
	private void carregarFormacoesAcademicas(Candidato candidato, Document lattes) {
		NodeList nos = XmlUtils.getNos(lattes, "FORMACAO-ACADEMICA-TITULACAO");
		
		if (nos.getLength() > 0) {
			NodeList formacoes = nos.item(0).getChildNodes();
			
			for (int contador = 0; contador < formacoes.getLength(); contador++) {
				Node no = formacoes.item(contador);
				
				String dataFormacao = XmlUtils.getValorAtributo(no, "ANO-DE-CONCLUSAO");
				String nomeUniversidade = XmlUtils.getValorAtributo(no, "NOME-INSTITUICAO");
				String titulo = no.getNodeName();
				
				if (dataFormacao == null || dataFormacao == "") {
					dataFormacao = String.valueOf(Constantes.ANO_FINAL_PADRAO);
				}
				
				int ano = Integer.parseInt(dataFormacao);
				if (ano >= Constantes.ANO_LIMITE) {
					candidato.addFormacaoAcademica(new FormacaoAcademica(ano, nomeUniversidade, titulo));
				}			
			}
		}
	}
	
	/**
	 * Atuacoes nos ultimos 10 anos, ou atuais.
	 */
	private void carregarAtuacoesProfissionais(Candidato candidato, Document lattes) {
		
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
				anoInicio = String.valueOf(Constantes.ANO_INICIAL_PADRAO);
			}
			
			// Pode ser uma atuacao vigente, sem ano final.
			if (anoFim == null || anoFim == "") {
				anoFim = String.valueOf(Constantes.ANO_FINAL_PADRAO);
			}
			
			int anoFinal = Integer.parseInt(anoFim);
			if (anoFinal >= Constantes.ANO_LIMITE) {
				candidato.addAtuacaoProfissional(new AtuacaoProfissional(Integer.parseInt(anoInicio), anoFinal, localAtuacao, descricaoVinculo));
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
		private void carregarVinculoInstituicao(Candidato candidato) {
			// Projetos:
			for (ProjetoPesquisa projeto : candidato.getProjetosPesquisa()) {
				if (ProfessorController.getInstancia().ehProfessorUnirio(projeto.getCoordenadorProjeto())) {
					candidato.addVinculo(new Vinculo(projeto.getAnoVinculo(), projeto.getTitulo(), "Projeto de Pesquisa"));
				}
			}
			
			
			// Formacao academica:
			for (FormacaoAcademica formacao : candidato.getFormacoesAcademicas()) {
				if (formacao.getNomeUniversidade().contains("Universidade Federal do Estado do Rio de Janeiro")) {
					candidato.addVinculo(new Vinculo(formacao.getDataFormacao(), formacao.getTitulo(), "Formacao academica"));
				}
			}
			
			
			// Atuacao profissional:
			for (AtuacaoProfissional atuacao : candidato.getAtuacoesProfissionais()) {
				if (atuacao.getLocalAtuacao().contains("Universidade Federal do Estado do Rio de Janeiro")) {
					candidato.addVinculo(new Vinculo(atuacao.getAnoFim(), atuacao.getDescricaoVinculo(), "Atuacao profissional"));
				}
			}
			
			
			if (candidato.getVinculos().size() > 0) {
				candidato.setPossuiVinculoInstituicao(true);
			}
		}
		
		// Construtor. Le o lattes e o numero de semestre sem reprovacoes, 
		// calculando os demais campos da classe. 
		public Candidato carregarCandidatoCompleto(Candidato candidato, String caminhoXml) {
			try {
				Document lattes = XmlUtils.lerXml(caminhoXml, "CURRICULO-VITAE");
				
				carregarNome(candidato, lattes);
				carregarPremios(candidato, lattes);
				carregarArtigosCompletos(candidato, lattes);
				carregarAtuacoesProfissionais(candidato, lattes);
				carregarEventos(candidato, lattes);
				carregarFormacoesAcademicas(candidato, lattes);
				carregarProjetosPesquisa(candidato, lattes);
				carregarVinculoInstituicao(candidato);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro na leitura do lattes.");
			}
			
			return candidato;
		}
		
}

