package br.com.gympass.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * 
 * Operacoes com String
 * 
 * @author Denis
 * 
 */
public class StringUtils {

	final static Logger LOG = Logger.getLogger(StringUtils.class);

	/**
	 * Verifica se a String esta vazia ou nula
	 * 
	 * @return true para valida
	 * 
	 */
	public static boolean isNotEmpty(String string) {
		return ((string != null) && (string.length() > 0));
	}

	/**
	 * Separa um texto com base no delimitador informado
	 * 
	 * @param texto
	 * @param delimitador
	 * 
	 */
	public static List<String> split(String texto, String delimitador) {
		LOG.info("[StringUtils.split] - INICIO");
		try {
			List<String> retorno = new ArrayList<String>();

			if (StringUtils.isNotEmpty(texto) && StringUtils.isNotEmpty(delimitador)) {

				if (delimitador.length() == 1) {

					StringTokenizer tokenizer = new StringTokenizer(texto, delimitador);

					while (tokenizer.hasMoreTokens()) {
						retorno.add(tokenizer.nextToken());
					}
				} else {
					String[] vet_tokens = texto.split(delimitador);
					retorno.addAll(Arrays.asList(vet_tokens));
				}
			}
			LOG.info("[StringUtils.split] - FIM");
			if (retorno.size() > 0) {
				return retorno;
			} else {
				retorno.add(texto);
				return retorno;
			}
		} catch (final Exception e) {
			LOG.error("[StringUtils.split] - Ocorreu um erro inesperado", e);
		}
		return null;
	}

	/**
	 * Realiza a substituicao de um caractere por outro
	 * 
	 * @param texto
	 * @param caractereAntigo
	 * @param caractereNovo
	 * 
	 * @return texto com o caractere substituido
	 */
	public static String replace(String texto, String caractereAntigo, String caractereNovo) {
		LOG.info("[StringUtils.replace] - INICIO");
		try {
			if (isNotEmpty(texto)) {
				LOG.info("[StringUtils.replace] - FIM");
				return texto.replaceAll(caractereAntigo, caractereNovo);
			}
		} catch (Exception e) {
			LOG.error("[StringUtils.replace] - Ocorreu um erro inesperado", e);
		}
		return null;
	}
}
