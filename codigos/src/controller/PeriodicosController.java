package controller;

import model.ArquivoCsv;

public class PeriodicosController implements ClassificacaoInterface{

	private ArquivoCsv periodicos;
	
	public PeriodicosController()
	{
		this.periodicos = new ArquivoCsv("../../../arquivos/dados/qualis_conferencia.csv", ",");

	}
	@Override
	public String getClassificacaoCapes(String ISSNPeriodico) 
	{
		// primeiro, pegar o indice do elemento siglaConferencia
		int indiceLinha = periodicos.getLinhaElemento("ISSN", ISSNPeriodico);
		// depois de checar se essa sigla foi encontrada, pegar o elemento da coluna QUALIS, que esta na mesma linha
		if (indiceLinha != -1)
		{
			return periodicos.getElemento("QUALIS", indiceLinha);
			
		}
		return "";
		
	}
}
