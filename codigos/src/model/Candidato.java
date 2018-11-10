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
	
		
	private ArrayList<Periodico> periodicos = new ArrayList<Periodico>();
	
	private ArrayList<Conferencia> conferencias = new ArrayList<Conferencia>();
	
	
	
	public void getPeriodicos() {
		
		
	}

	
	
}
