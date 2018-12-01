package controller;

import java.io.InputStream;

import model.ArquivoCsv;


/**
 * Classe para leitura e pesquisa no arquivo com todos os 
 * professores conhecidos.
 */
public class ProfessorController {
	
	private static final InputStream ENDERECO_ARQUIVO_PROFESSORES = PeriodicosController.class.getResourceAsStream("/resources/qualis_periodicos.csv");
	
	private static ArquivoCsv professoresPPGI = new ArquivoCsv(ENDERECO_ARQUIVO_PROFESSORES, ",");
	
	/**
	 * Retorna se um determinado professor esta no corpo docente da UNIRIO.
	 * @param professor - String, nome do professor a verificar.
	 * @return true se for docente, false se nao.
	 */
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
