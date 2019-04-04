package br.com.gympass.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * Operacoes com Time e LocalTime
 * 
 * @author Denis
 * 
 */
public class TempoUtils {

	final static Logger LOG = Logger.getLogger(TempoUtils.class);

	public static final String PATTERN_DURATION_mm_ss_SSS = "mm:ss.SSS";

	/***
	 * Conversao de uma String de Tempo para LocalTime
	 * 
	 * @param tempo(23:54:57.757)
	 * 
	 * @return O LocalTime ou nulo em caso de erro
	 */
	public static LocalTime getLocalTime(String tempo) {
		LOG.info("[TempoUtils.getLocalTime] - INICIO");
		try {
			if (StringUtils.isNotEmpty(tempo)) {
				LOG.info("[TempoUtils.getLocalTime] - FIM");
				return LocalTime.parse(tempo);
			}
		} catch (final Exception e) {
			LOG.error("[TempoUtils.getLocalTime] - Ocorreu um erro inesperado", e);
		}
		return null;
	}

	/***
	 * Verifica duracao pela String com a duracao
	 * 
	 * @param duracao string (1:18.097)
	 * 
	 * @return Duration
	 * 
	 */
	public static Duration getDuration(String duracao) {
		try {
			LOG.info("[TempoUtils.getDuration] - INICIO");
			if (StringUtils.isNotEmpty(duracao)) {

				duracao = StringUtils.replace(duracao, "\\:", "_");
				duracao = StringUtils.replace(duracao, "\\.", "_");

				List<String> valores = StringUtils.split(duracao, "_");

				if ((valores != null) && (!valores.isEmpty())) {

					Duration duration = Duration.ofMinutes(Long.parseLong(valores.get(0)));
					duration = duration.plusSeconds(Long.parseLong(valores.get(1)));
					duration = duration.plusMillis(Long.parseLong(valores.get(2)));
					LOG.info("[TempoUtils.getDuration] - FIM");
					return duration;
				}
			} else
				LOG.info("[TempoUtils.getDuration] - Duracao estava vazia");
		} catch (final Exception e) {
			LOG.error("[TempoUtils.getDuration] - Ocorreu um erro inesperado", e);
		}
		return null;
	}

	/**
	 * Formata uma Duracao pelo formato ( mm:ss.SSS )
	 * 
	 * @param duration Duration a ser formatado
	 * 
	 * @return String da formatacao
	 */
	public static String formatDuration(Duration duration) {
		return new SimpleDateFormat(PATTERN_DURATION_mm_ss_SSS, Locale.getDefault())
				.format(new Date(duration.toMillis() - TimeZone.getDefault().getRawOffset()));
	}

	/***
	 * Converte a velocidade para float
	 * 
	 * @param velocidade
	 * 
	 * @return velocidade em float
	 */
	public static Float converteVelocidade(String velocidade) {
		try {
			LOG.info("[TempoUtils.converteVelocidade] - INICIO");
			if (StringUtils.isNotEmpty(velocidade)) {
				return Float.parseFloat(StringUtils.replace(velocidade, ",", "."));
			} else LOG.info("[TempoUtils.converteVelocidade] - Velocidade estava vazia");
		} catch (final Exception e) {
			LOG.error("[TempoUtils.converteVelocidade] - Ocorreu um erro inesperado", e);
		}
		return null;
	}
}
