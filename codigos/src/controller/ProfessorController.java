package controller;

import model.ArquivoCsv;

public class ProfessorController {
	private static ArquivoCsv professoresPPGI = new ArquivoCsv("../arquivos/dados/professores_bsi_ppgi.csv", ",");;
	
	public static boolean ehProfessorUnirio(String professor)
	{
		String[] professores = professoresPPGI.getElementosColuna("Nome professor");
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
