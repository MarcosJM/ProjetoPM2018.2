package utils;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Classe para leitura e escrita num arquivo XML.
 *
 */
public class XmlUtils {
		
	/**
	 * Salva o documento XML no endereco.
	 * @param documento - Document, XML a ser salvo.
	 * @param endereco - String, endereco relativo ou completo do arquivo XML, incluindo '.xml'.
	 * @throws Exception Excecoes diversas de leitura de arquivo.
	 */
	private static void salvarXml(Document documento, String endereco) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(documento);
		StreamResult streamResult = new StreamResult(new File(endereco));
		
		transformer.transform(domSource, streamResult);
		
	}
	
	
	/**
	 * Le o documento XML do endereco.
	 * @param endereco - String, endereco relativo ou completo do arquivo XML, incluindo '.xml'.
	 * @param raiz - String, raiz do documento XML, utilizada caso seja necessario cria-lo.
	 * @return Document, arquivo XML.
	 * @throws Exception Excecoes diversas de leitura de arquivo.
	 */
	public static Document lerXml(String endereco, String raiz) throws Exception {
		File file = new File(endereco);
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document documento;
		
		// Se criou um arquivo que nao existia, adiciona um no raiz e salva.
		if (file.createNewFile()) {
			documento = documentBuilder.newDocument();
			
			Element root = documento.createElement(raiz);
			documento.appendChild(root);
			salvarXml(documento, endereco);

		} else {
			documento = (Document) documentBuilder.parse(file);
		}
		
		return documento;
	}

	
	/**
	 * Retorna o valor do atributo associado ao primeiro no encontrado da tag.
	 * @param documento - Document, XML qualquer.
	 * @param tag - String, tag que eh o nome de um no.
	 * @param atributo - String, nome do atributo a ser pesquisado na tag.
	 * @return String, valor do atributo.
	 */
	public static String getValorAtributo(Document documento, String tag, String atributo) {
		NodeList listaNos = documento.getElementsByTagName(tag);
		if (listaNos.getLength() > 0) {
			return listaNos.item(0).getAttributes().getNamedItem(atributo).getNodeValue();
		} else {
			return "";
		}
		
	}


	/**
	 * Retorna a quantidade de nos de uma determinada tag presentes no documento.
	 * @param documento - Document, XML qualquer.
	 * @param tag - String, tag que eh o nome de um no.
	 * @return int, quantidade de nos da tag.
	 */
	public static int getQuantidadeNos(Document documento, String tag) {
		return documento.getElementsByTagName(tag).getLength();
	}
	
}
