package br.com.gympass.service;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.gympass.kart.KartUtils;
import br.com.gympass.model.ResultadoCorridaVO;
import br.com.gympass.model.ResultadoPilotoVO;
import br.com.gympass.model.VoltaPistaVO;
import br.com.gympass.utils.ArquivoUtils;
import br.com.gympass.utils.StringUtils;

@RestController
@RequestMapping("/gympass")
public class KartService {

	final static Logger LOG = Logger.getLogger(KartService.class);

	/**
	 * CONSULTAR ULTIMA CORRIDA KART
	 * 
	 * @return
	 */
	@RequestMapping(value = "/corrida", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResultadoCorridaVO consultarResultados() {
		LOG.info("[ KartService > /corrida ] - INICIO");
		try {
			String log = ArquivoUtils.readFile("./KartLog.txt");
			
			if (StringUtils.isNotEmpty(log)) {

				List<VoltaPistaVO> listaCorredores = KartUtils.processarArquivoLog(log);

				if (listaCorredores != null) {
					/* Melhor volta */
					VoltaPistaVO melhorVolta = KartUtils.obterMelhorVolta(listaCorredores);
					/* Ordena a lista */
					Collections.sort(listaCorredores, new VoltaPistaVO());
					/* Obtem resultado por piloto */
					List<ResultadoPilotoVO> resultadoPiloto = KartUtils.obterDadosPiloto(listaCorredores);
					/* Popula com base no tempo total a posicao */
					resultadoPiloto = KartUtils.populaPosicaoPiloto(resultadoPiloto);
					Collections.sort(resultadoPiloto);
					/* Popula objeto de retorno */
					ResultadoCorridaVO resultado = new ResultadoCorridaVO();
					resultado.setParticipantes(resultadoPiloto);
					resultado.setMelhorVolta(melhorVolta);
					LOG.info("[ KartService > /corrida ] - FIM - Processo concluido");
					return resultado;
				}
			}
		} catch (final Exception e) {
			LOG.error("[ KartService > /corrida ] - Ocorreu um erro inesperado na chamada do servico", e);
		}
		return null;
	}
}