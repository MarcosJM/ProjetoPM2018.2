package utils;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.lang.reflect.Field;


/**
 * Classe para leitura e escrita num arquivo XML.
 *
 * @param <T> Classe de modelo utilizada na leitura e escrita, quando necessario.
 */
public class XmlUtils<T> {


	/**
	 * Faz leitura de um arquivo XML local.
	 * @param endereco - String, endereco relativo ou completo do arquivo XML, incluindo '.xml'.
	 * @param propriedades - ArrayList<String>, lista de propriedades a serem lidas dos nos do XML.
	 * Por exemplo, {nome, telefone} faz a leitura dessas duas tags em cada no filho.
	 * @param nomeClasse - String, nome qualificado da classe na qual serao transformados os nos lidos.
	 * Por exemplo, Model.Contato.
	 * @param nomeTagPrincipal - String, nome das tags de nos filhos da raiz. Os objetos lidos serao desta tag.
	 * @param raiz - String, raiz do documento XML, utilizada caso seja necessario cria-lo.
	 * @return ArrayList<T>, elementos da classe modelo do arquivo XML.
	 * @throws Exception Excecoes diversas de leitura de arquivo ou de reflexao.
	 */
	@SuppressWarnings("unchecked") // Impede warning ao fazer typecasting.
	public ArrayList<T> lerXml(String endereco, ArrayList<String> propriedades, 
			String nomeClasse, String nomeTagPrincipal, String raiz) throws Exception {
		ArrayList<T> objetos = new ArrayList<T>();
		
		// Leitura do arquivo XML.
		Document documento = readXml(endereco, raiz);
		
		// Reflexao da classe desejada.
		Class<?> classe = Class.forName(nomeClasse);
		
		// Loop para escrita dos elementos do XML no ArrayList.
		int numeroNos = documento.getElementsByTagName(nomeTagPrincipal).getLength();
		for (int cont = 0; cont < numeroNos; cont++) {
			Object instance = classe.newInstance();
			
			for (String propriedadeParaLeitura : propriedades) {
				// Obtem o conteudo do XML na tag desejada.
				String conteudo = documento.getElementsByTagName(propriedadeParaLeitura).item(cont).getTextContent();
				
				// Escreve o conteudo da tag no atributo do objeto.
				ReflexaoUtils.setAttribute(instance, propriedadeParaLeitura, conteudo);
		  	    
			}
			objetos.add((T) instance);
		}
		
		return objetos;
	}
	
	
	/**
	 * Adiciona um novo elemento num XML ja existente.
	 * @param endereco - String, endereco relativo ou completo do arquivo XML, incluindo '.xml'.
	 * @param objeto - T, objeto que se deseja adicionar no arquivo XML.
	 * @param nomeTagPrincipal - String, nome das tags de nos filhos da raiz. O objeto adicionado sera desta tag.
	 * @param raiz - String, raiz do documento XML, utilizada caso seja necessario cria-lo.
	 * @throws Exception Excecoes diversas de leitura de arquivo ou de reflexao.
	 */
	public void adicionaElementoXml(String endereco, T objeto, String nomeTagPrincipal, String raiz) throws Exception {
		
		// Leitura do arquivo XML.
		Document documento = readXml(endereco, raiz);
				
		// Cria um novo elemento da tag desejada.
		Node root = documento.getFirstChild();
		Element elemento = documento.createElement(nomeTagPrincipal);
		root.appendChild(elemento);
		
		// Insere os atributos no elemento de acordo com os atributos do objeto.
		Class<?> classe = objeto.getClass();
		for (Field propriedadeDoObjeto : classe.getFields()) {
			Element propriedadeDoXml = documento.createElement(propriedadeDoObjeto.getName());
			propriedadeDoXml.appendChild(documento.createTextNode(propriedadeDoObjeto.get(objeto).toString()));
			elemento.appendChild(propriedadeDoXml);
	  	    
		}
		
		// Salva o XML.
		salvarXml(documento, endereco);
		
	}
	
	
	/**
	 * Sobrescreve o conteudo de um XML existente com os elementos passados, mantendo a raiz.
	 * @param endereco - String, endereco relativo ou completo do arquivo XML, incluindo '.xml'.
	 * @param objetos - ArrayList<T>, objetos que se deseja escrever no arquivo XML.
	 * @param nomeTagPrincipal - String, nome das tags de nos filhos da raiz. Os objetos adicionados serao desta tag.
	 * @throws Exception Excecoes diversas de leitura de arquivo ou de reflexao.
	 */
	public void sobrescreveXml(String endereco, ArrayList<T> objetos, String nomeTagPrincipal, String raiz) throws Exception {
		
		// Leitura do arquivo XML.
		Document documento = readXml(endereco, raiz);
		
		// Remove os elementos anteriores.
		Node root = documento.getFirstChild();
		removeTodosFilhos(root);
		 
		// Faz reflexao com o primeiro elemento do ArrayList.
		Class<?> classe = null;
		if (objetos.size() > 0) {
			classe = objetos.get(1).getClass();
		}
		
		// Insere os objetos no documento.
		for (T objeto : objetos) {	
			Element elemento = documento.createElement(nomeTagPrincipal);
			root.appendChild(elemento);
			
			// Insere os atributos no elemento de acordo com os atributos do objeto.
			for (Field propriedadeDoObjeto : classe.getFields()) {
				Element propriedadeDoXml = documento.createElement(propriedadeDoObjeto.getName());
				propriedadeDoXml.appendChild(documento.createTextNode(propriedadeDoObjeto.get(objeto).toString()));
				elemento.appendChild(propriedadeDoXml);
		  	    
			}
			
		}
		
		// Salva o XML.
		salvarXml(documento, endereco);	
		
	}
	
	
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
	
	
	/**
	 * Remove todos os filhos de um no.
	 * @param node - Node, elemento do qual sao removidos os filhos.
	 */
	private void removeTodosFilhos(Node node) {
	    while (node.hasChildNodes())
	        node.removeChild(node.getFirstChild());
	}
	
}
