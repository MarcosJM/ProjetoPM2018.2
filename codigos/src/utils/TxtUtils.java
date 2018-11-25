package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Classe que realiza a escrita em arquivos TXT.
 */
public class TxtUtils {
	
	/**
	 * Escreve conteudo no arquivo TXT informado.
	 * @param caminhoTxt - String que indica o caminho ate o arquivo, deve conter a extensao .txt
	 * @param conteudo - String que eh o conteudo a ser escrito.
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception Excecoes diversas de leitura de arquivo.
	 */
	public static void escreverTxt(String caminhoTxt, String conteudo) throws Exception
	{
		Writer writer = new BufferedWriter(
				new OutputStreamWriter(
					new FileOutputStream(caminhoTxt), "utf-8"));
		writer.write(conteudo);
		writer.close();
	}
	
}