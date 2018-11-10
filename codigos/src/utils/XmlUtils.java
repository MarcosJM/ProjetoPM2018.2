package utils;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


/**
 * Classe para leitura e escrita num arquivo XML.
 *
 * @param <T> Classe de modelo utilizada na leitura e escrita, quando necessario.
 */
public class XmlUtils<T> {


	public void lerXml(String path) {}
	public void escreverXml(String path, Document document) {}
	
	
	/**
	 * Salva o documento XML no endereco.
	 * @param documento - Document, XML a ser salvo.
	 * @param endereco - String, endereco relativo ou completo do arquivo XML, incluindo '.xml'.
	 * @throws Exception Excecoes diversas de leitura de arquivo.
	 */
	private void salvarXml(Document documento, String endereco) throws Exception {
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
	private Document readXml(String endereco, String raiz) throws Exception {
		File file = new File(endereco);
		
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document documento;
		
		// Se criou um arquivo que não existia, adiciona um no raiz e salva.
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

}
