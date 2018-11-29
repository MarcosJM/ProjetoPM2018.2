package utils;

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
	public static final int ANO_LIMITE = Calendar.getInstance().YEAR - 10; // ultimos 10 anos.
	public static final int ANO_ATUAL = Calendar.getInstance().YEAR;
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
}
