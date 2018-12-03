package controller;

import model.ArquivoCsv;
import utils.Constantes;


/**
 * Classe para leitura e pesquisa no arquivo com todos os 
 * professores conhecidos.
 */
public class ProfessorController {
	
	private ArquivoCsv professoresPPGI = new ArquivoCsv(Constantes.ENDERECO_ARQUIVO_PROFESSORES, ",");
	
	/**
	 * Implementacao do Singleton.
	 */
	
	private static ProfessorController unicaInstancia;
	
	private ProfessorController() {}
	
	public static ProfessorController getInstancia() {
		
		if (unicaInstancia == null) {
			unicaInstancia = new ProfessorController();
		} 
		
		return unicaInstancia;
    }
	
	/**
	 * Retorna se um determinado professor esta no corpo docente da UNIRIO.
	 * @param professor - String, nome do professor a verificar.
	 * @return true se for docente, false se nao.
	 */
	public boolean ehProfessorUnirio(String professor)
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
