package utils;

import java.io.InputStream;
import java.util.Calendar;

/**
 * Classe para lidar com as constantes e configuracoes padroes 
 * do programa.
 */
public class Constantes {
	// Leitura de CSV.
	public static final String[] ARRAY_VAZIO = {};
	public static final Double SIMILARIDADE_DESEJADA = 98.5;
	
	// Datas.
	public static final int ANO_LIMITE = Calendar.YEAR - 10; // ultimos 10 anos.
	public static final int ANO_ATUAL = Calendar.YEAR;
	public static final int ANO_FINAL_PADRAO = 9999;
	public static final int ANO_INICIAL_PADRAO = 0;
	
	// Pontuacao do lattes.
	public static final int PONTOS_QUALIS_RESTRITO_MULTIPLICADOR = 3;
	public static final int PONTOS_QUALIS_COMPLETO_MULTIPLICADOR = 1;
	public static final int PONTOS_EVENTOS = 5;
	public static final int PONTOS_PREMIOS_MULTIPLICADOR = 1;
	public static final int PONTOS_VINCULOS = 1;
	
	// Comissao de bolsas.
	public static final int NUMERO_SEMESTRES_SEM_REPROVACAO_PADRAO = 0;
	
	// Endereco dos arquivos CSV de busca.
	public static final InputStream ENDERECO_ARQUIVO_CONFERENCIA = Constantes.class.getResourceAsStream("/resources/qualis_conferencia.csv");
	public static final InputStream ENDERECO_ARQUIVO_PERIODICOS = Constantes.class.getResourceAsStream("/resources/qualis_periodicos.csv");
	public static final InputStream ENDERECO_ARQUIVO_PROFESSORES = Constantes.class.getResourceAsStream("/resources/professores_bsi_ppgi.csv");
	
	// erros comuns
	public static final String ERRO_ENDERECO_COLUNA_INALCANCAVEL = "O endereco da coluna esta fora dos limites.";
	public static final String ERRO_ENDERECO_LINHA_INALCANCAVEL = "O endereco da linha esta fora dos limites.";

}
