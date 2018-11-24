package controller;

import model.ArquivoCsv;

public class ConferenciaController implements ClassificacaoInterface{

	private ArquivoCsv conferencias;
	
	public ConferenciaController()
	{
		this.conferencias = new ArquivoCsv("../../../arquivos/dados/qualis_conferencia.csv", ",");

	}
	
	@Override
	public String getClassificacaoCapes(String siglaConferencia) {
		//primeiro, pegar o �ndice do elemento siglaConferencia
		int indiceLinha = conferencias.getLinhaElemento("SIGLA", siglaConferencia);
		//depois de checar se essa sigla foi encontrada, pegar o elemento da coluna QUALIS, que est� na mesma linha
		if (indiceLinha != -1)
		{
			return conferencias.getElemento("QUALIS", indiceLinha);
			
		}
		return "";
		
	}
	
}
