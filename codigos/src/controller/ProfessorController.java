package controller;

import model.ArquivoCsv;

public class ProfessorController {
	private static ArquivoCsv professoresPPGI;
	
	public ProfessorController()
	{
		this.professoresPPGI = new ArquivoCsv("../../../arquivos/dados/professores_bsi_ppgi.csv", ",");
	} 
	public static boolean ehProfessorUnirio(String professor)
	{
		String[] professores = professoresPPGI.getElementosColuna("Nome Professor");
		int quantidadeProfessores = professores.length;
		for (int iterator = 0; iterator < quantidadeProfessores; iterator ++)
		{
			if (professores[iterator].equals(professor))
			{
				return true;
			}
		}
		return false;
	}
}
