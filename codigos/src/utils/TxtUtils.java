package utils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Classe que realiza a escrita em arquivos TXT, 
 * por padrao escreve em UTF-8.
 */
public class TxtUtils {
	private TxtUtils logErros = new TxtUtils();
	
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
	
	public void appendDataTxt(String caminhoTxt, String conteudo) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
		    fw = new FileWriter(caminhoTxt, true);
		    bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    out.println(conteudo);
		    out.close();
		} catch (IOException e) {
			logErros.appendDataTxt("../errorLog.txt", e.getMessage());
			logErros.appendDataTxt("../errorLog.txt", "-----------------------------------------------------");

		    //exception handling left as an exercise for the reader
		}
		finally {
		    if(out != null)
			    out.close();
		    try {
		        if(bw != null)
		            bw.close();
		    } catch (IOException e) {
				logErros.appendDataTxt("../errorLog.txt", e.getMessage());
				logErros.appendDataTxt("../errorLog.txt", "-----------------------------------------------------");

		        //exception handling left as an exercise for the reader
		    }
		    try {
		        if(fw != null)
		            fw.close();
		    } catch (IOException e) {
				logErros.appendDataTxt("../errorLog.txt", e.getMessage());
				logErros.appendDataTxt("../errorLog.txt", "-----------------------------------------------------");
		        //exception handling left as an exercise for the reader
		    }
		}
	}
	
}
