package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Classe que realiza a leitura de arquivos CSV.
 */
public class CsvUtils 
{
	
	/**
	 * 
	 * @param endereco - String que indica o caminho ate o arquivo
	 * @param separador - String que indica o tipo de separador utilizado no arquivo csv
	 * @return ArrayList<String[]> contendo os dados do arquivo
	 * @throws FileNotFound exception caso o arquivo nao seja encontrado
	 * @throws IOException caso nao seja mais possivel acessar o arquivo
	 */
	public static ArrayList<String[]> lerCsv(InputStream in, String separador)
	{
			
        BufferedReader leitor = null;
        String linha = "";
        ArrayList<String[]> dadosDoArquivo = new ArrayList<String[]>();
		try {
			leitor = new BufferedReader(new InputStreamReader(in));
			while ((linha = leitor.readLine()) != null) 
			{
				//para cada linha do arquivo, realiza um split pelo separador (virgula, ponto e virgula, etc) e adiciona a arrayList
                dadosDoArquivo.add(linha.split(separador));

            }
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (leitor != null) {
                try {
                    leitor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return dadosDoArquivo;
	}
	
}
 