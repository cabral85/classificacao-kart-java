package br.com.gympass.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;

/**
 * Operacoes de leitura e escrita de arquivos
 * 
 * 
 * @author Denis
 * 
 */
public class ArquivoUtils {

	final static Logger LOG = Logger.getLogger(ArquivoUtils.class);

	/**
	 * Realiza a leitura de um arquivo
	 * 
	 * @param url caminho do arquivo
	 * 
	 * @return conteudo do arquivo
	 */
	public static String readFile(String url) {
		LOG.info("[ArquivoUtils.readFile] - INICIO");
		try {
			if (StringUtils.isNotEmpty(url)) {
				StringBuilder contents = new StringBuilder();
				File file = new File(url);

				BufferedReader input = new BufferedReader(new FileReader(file));

				String line;

				while ((line = input.readLine()) != null) {
					if (line != null) {
						contents.append(line);
						contents.append(System.getProperty("line.separator"));
					}
				}
				input.close();
				LOG.info("[ArquivoUtils.readFile] - FIM");
				return contents.toString();
			} else LOG.info("[ArquivoUtils.readFile] - Nao foi possivel ler o arquivo, URL > "+url);
		} catch (final Exception e) {
			LOG.info("[ArquivoUtils.readFile] - Ocorreu um problema inesperado", e);
		}
		return null;
	}
}
