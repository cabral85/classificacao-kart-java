package br.com.gympass.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * Objeto representa os resultados da corrida
 * 
 * @author Denis
 * 
 * */
@Data
@NoArgsConstructor
public class ResultadoCorridaVO {
	
	private VoltaPistaVO melhorVolta;

	private List<ResultadoPilotoVO> participantes;
	
	public List<ResultadoPilotoVO> getParticipantes() {
		return participantes;
	}


}
