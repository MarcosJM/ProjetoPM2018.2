package view;

import utils.CommandLine.Option;
import utils.CommandLine.Parameters;

import java.util.List;

/**
 * Classe para definir os comandos (conceitualmente, tido como opcoes) aceitos pelo sistema e suas respectivas variaveis associadas. 
 * Essa classe eh implementada utilizando-se da classe CommandLine, que contem o codigo da biblioteca picocli.
 * 
 * Os comandos suportados sao:
 * 
 * 1) -a <nome-caminho-arquivo-xml> <dado-1> : indica caminho do arquivo com o currículo Lattes em XML e, nos parametros, os dados do candidato a ser considerado. Obrigatorio.
 * 2) -o <nome-caminho-arquivo-txt-saida> : indica o caminho do arquivo texto que deve conter a saída do programa. Obrigatorio.
 * 3) -l <nome-caminho-arquivo-log-errors> : indica o caminho do arquivo texto que deve conter um log/relatório de erros do programa. Opcional.
 * 4) -v : modificador que indica que a saída deve ser completa (modo verboso, ou verbose mode). Opcional.
 * 5) -c : gera a saída completa do programa. É equivalente a usar, em conjunto, os parâmetros -pr -ar -anr -pe -vi. Por padrão, eh o considerado.
 * 6) -pr : gera a saída referente aos prêmios recebidos. 
 * 7) -ar : gera a saída referente aos artigos completos no Qualis Restrito (ou seja, publicados em conferências ou periódicos classificados como A1, A2 e B1).
 * 8) -anr : gera a saída referente aos artigos completos fora do Qualis Restrito (ou seja, publicados em conferências ou periódicos classificados como B2, B3, B4 e B5).
 * 9) -pe : gera a saída referente à participação em eventos classificados (ou seja, referentes a conferências classificadas como A1, A2, B1, B2, B3, B4 e B5).
 * 10) -vi : gera a saída referente à existência de vínculo com a UNIRIO.
 * 
 * A biblioteca picocli assume qualquer ordem de comandos. Assim tambem como aceita concatenacoes (e.g. -c -f, -cf).
 *
 *
 */

public class Comandos {
	
	//"Arity" descreve que serao obrigatorios dois argumentos para esse comando
	@Option(names = "-a", arity = "2", description = "Indica o dado do candidato a ser considerado (nº de semestres sem reprovacao) e o caminho do arquivo XML.", required = true)
	private String[] DadosECaminhosXML;
	
	@Option(names = "-o", description = "Caminho do arquivo texto que contem a saida do programa.", required = true)
	private String caminhoSaida;

	@Option(names = "-l", description = "Caminho do arquivo texto que contem um log de erros do programa.")
	private String caminhoLog;
	
	@Option(names = "-v", description = "Modo detalhado. Descricao completa da execucao do codigo.")
	private boolean verboso;
	
	@Option(names = "-c", description = "Gera a saida completa do programa.")
	private boolean completo;
	
	@Option(names = "-pr", description = "Gera a saida referente aos premios recebidos.")
	private boolean premios;
	
	@Option(names = "-ar", description = "Gera a saida referente aos os artigos completos no Qualis Restrito (A1, A2 e B1).")
	private boolean artigosNoQualisRestrito;
	
	@Option(names = "-anr", description = "Gera a saida referente aos os artigos completos fora do Qualis Restrito (B2, B3, B4 e B5).")
	private boolean artigosForaQualisRestrito;
	
	@Option(names = "-pe", description = "Gera a saida referente a participacao em eventos classificados (A1, A2, B1, B2, B3, B4 e B5).")
	private boolean eventosClassificados;
	
	@Option(names = "-vi", description = "Gera a saida referente a existencia de vinculo com a UNIRIO.")
	private boolean vinculoUnirio;
	
	
	/**
	 *  Abaixo, metodos para retornar as variaveis associadas a cada opcao.
	 */
	
	public String[] getDadosECaminhosXML() {
		return DadosECaminhosXML;
	}
	
	public String getCaminhoSaida() {
		return caminhoSaida;
	}
	
	public String getCaminhoLog() {
		return caminhoLog;
	}
	
	public boolean isVerboso() {
		return verboso;
	}
	
	public boolean isCompleto() {
		return completo;
	}
	
	public boolean isPremios() {
		return premios;
	}
	
	public boolean isArtigosNoQualisRestrito() {
		return artigosNoQualisRestrito;
	}
	
	public boolean isArtigosForaQualisRestrito() {
		return artigosForaQualisRestrito;
	}
	
	public boolean isEventosClassificados() {
		return eventosClassificados;
	}
	
	public boolean isVinculoUnirio() {
		return vinculoUnirio;
	}
}