package br.com.gympass.kart;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.gympass.model.ResultadoPilotoVO;
import br.com.gympass.model.VoltaPistaVO;
import br.com.gympass.utils.StringUtils;
import br.com.gympass.utils.TempoUtils;

/**
 * Operacoes para processamento do Log da corrida de Kart
 * 
 * 
 * @author Denis
 * 
 */
public class KartUtils {

	final static Logger LOG = Logger.getLogger(KartUtils.class);

	/***
	 * Realiza a conversao da linha do arquivo em objeto VoltaPistaVO
	 * 
	 * @param string conteudo
	 * 
	 * @return Lista com VoltaPistaVO
	 */
	public static List<VoltaPistaVO> processarArquivoLog(String log) {

		List<VoltaPistaVO> retorno = new ArrayList<VoltaPistaVO>();

		LOG.info("[KartUtils.processarArquivoLog] - INICIO");
		try {
			List<String> linhas = StringUtils.split(log, "\n");

			if ((linhas != null) && (linhas.size() > 0)) {

				for (int posicao = 1; posicao < linhas.size(); posicao++) {

					String linha = linhas.get(posicao);

					linha = StringUtils.replace(linha, " ", "_");
					linha = StringUtils.replace(linha, "\t", "_");

					List<String> resultado = StringUtils.split(linha, "_");

					if (resultado != null && resultado.size() > 0) {
						VoltaPistaVO volta = new VoltaPistaVO();
						volta.setHora(TempoUtils.getLocalTime(resultado.get(0)));
						volta.setCodigo(Integer.parseInt(resultado.get(1)));
						volta.setNomePiloto(resultado.get(3));
						volta.setNumero(Integer.parseInt(resultado.get(4)));
						volta.setTempo(TempoUtils.getDuration(resultado.get(5)));
						volta.setTempoText(TempoUtils.formatDuration(TempoUtils.getDuration(resultado.get(5))));
						volta.setVelocidadeMedia(TempoUtils.converteVelocidade(resultado.get(6)));
						LOG.info("[KartUtils.processarArquivoLog] - Populando volta: " + volta.toString());
						retorno.add(volta);
					}
				}
			}
		} catch (final Exception e) {
			LOG.error("[KartUtils.processarArquivoLog] - Ocorreu um erro inesperado", e);
		}
		LOG.info("[KartUtils.processarArquivoLog] - FIM");
		return retorno;
	}

	/**
	 * Retorna a melhor volta da corrida
	 * 
	 * @param lista com VoltaPistaVO
	 * @return melhor volta VoltaPistaVO
	 * 
	 * @author Denis
	 * 
	 */
	public static VoltaPistaVO obterMelhorVolta(List<VoltaPistaVO> listaVoltas) {
		LOG.info("[KartUtils.obterMelhorVolta] - INICIO");
		VoltaPistaVO melhorVolta = null;
		try {
			Duration melhorTempo = listaVoltas.get(0).getTempo();

			for (VoltaPistaVO volta : listaVoltas) {
				if (volta.getTempo().compareTo(melhorTempo) < 0) {
					melhorTempo = volta.getTempo();
					melhorVolta = volta;
				}
			}
		} catch (final Exception e) {
			LOG.error("[KartUtils.obterMelhorVolta] - Ocorreu um erro inesperado", e);
		}
		LOG.info("[KartUtils.obterMelhorVolta] - FIM");
		return melhorVolta;
	}

	/**
	 * Obtem a o resultado por piloto
	 * 
	 * @author Denis
	 * 
	 */
	public static List<ResultadoPilotoVO> obterDadosPiloto(List<VoltaPistaVO> voltas) {
		
		List<ResultadoPilotoVO> pilotos = new ArrayList<ResultadoPilotoVO>();
		
		LOG.info("[KartUtils.obterDadosPiloto] - INICIO");
		try {
			for (VoltaPistaVO volta : voltas) {
				ResultadoPilotoVO piloto = new ResultadoPilotoVO();
				piloto.setVoltas(new ArrayList<VoltaPistaVO>());

				if (pilotos.isEmpty()) {
					piloto.setCodigoPiloto(volta.getCodigo());
					piloto.setNomePiloto(volta.getNomePiloto());
					piloto.getVoltas().add(volta);
					pilotos.add(piloto);
				} else {
					boolean encontrado = false;
					for (ResultadoPilotoVO listaPilotos : pilotos) {
						if (listaPilotos.getCodigoPiloto().equals(volta.getCodigo())) {
							listaPilotos.getVoltas().add(volta);
							encontrado = true;
							break;
						}
					}
					if (!encontrado) {
						piloto.setCodigoPiloto(volta.getCodigo());
						piloto.setNomePiloto(volta.getNomePiloto());
						piloto.getVoltas().add(volta);
						pilotos.add(piloto);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("[KartUtils.obterDadosPiloto] - Ocorreu um erro inesperado", e);
		}
		LOG.info("[KartUtils.obterDadosPiloto] - FIM");
		return pilotos;
	}

	/**
	 * Obtem o tempo total da corrida
	 * 
	 * @author Denis
	 * 
	 */
	public static Duration obterTempoTotalProva(ResultadoPilotoVO piloto) {
		Duration total = Duration.ZERO;
		LOG.info("[KartUtils.obterTempoTotalProva] - INICIO");
		try {
			for (VoltaPistaVO volta : piloto.getVoltas()) {
				total = total.plus(volta.getTempo());
			}
			piloto.setTempoTotalProva(total);
			piloto.setTempoTotalProvaText(TempoUtils.formatDuration(total));
		} catch (final Exception e) {
			LOG.info("[KartUtils.obterTempoTotalProva] - Ocorreu um erro inesperado", e);
		}
		LOG.info("[KartUtils.obterTempoTotalProva] - FIM");
		return total;
	}

	/**
	 * Obtem a posicao de chegada do piloto com base da duracao total da corrida
	 * 
	 * @author Denis
	 * 
	 */
	public static List<ResultadoPilotoVO> populaPosicaoPiloto(List<ResultadoPilotoVO> pilotos) {
		
		List<Duration> duracoes = new ArrayList<Duration>();
		
		LOG.info("[KartUtils.populaPosicaoPiloto] - INICIO");
		try {
			for (ResultadoPilotoVO piloto : pilotos) {
				duracoes.add(KartUtils.obterTempoTotalProva(piloto));
			}

			Collections.sort(duracoes);
			int posicao = 1;
			for (Duration tempo : duracoes) {
				for (ResultadoPilotoVO piloto : pilotos) {
					if (piloto.getTempoTotalProva().equals(tempo)) {
						piloto.setPosicaoChegada(posicao);
						break;
					}
				}
				posicao++;
			}
		} catch (final Exception e) {
			LOG.error("[KartUtils.populaPosicaoPiloto] - Ocorreu um erro inesperado", e);
		}
		LOG.info("[KartUtils.populaPosicaoPiloto] - FIM");
		return pilotos;
	}
}