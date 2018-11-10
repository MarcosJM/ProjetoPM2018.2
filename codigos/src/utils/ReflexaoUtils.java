package utils;

import java.lang.reflect.Field;

/**
 * Classe com metodos uteis para reflexao.
 *
 */
public class ReflexaoUtils {

	/**
	 * Coloca um valor especifico num atributo de um objeto com reflexao.
	 * Exemplo: setAttribute(new Contato(), "nome", "Cass")
	 * @param objeto - Object, objeto de uma classe qualquer.
	 * @param nomeAtributo - String, nome do atributo no qual se quer atribuir um valor.
	 * @param valor - Object, valor atribuido ao campo desejado.
	 * @return boolean - true se conseguiu atribuir o valor, false se não.
	 */
	public static boolean setAttribute(Object objeto, String nomeAtributo, Object valor) {
	    Class<?> classe = objeto.getClass();
	    if (classe != null) {
	        try {
	            Field atributo = classe.getDeclaredField(nomeAtributo);
	            atributo.setAccessible(true);
	            atributo.set(objeto, valor);
	            return true;
	        } catch (Exception e) {return false;}
	    }
	    return false;
	}
}
