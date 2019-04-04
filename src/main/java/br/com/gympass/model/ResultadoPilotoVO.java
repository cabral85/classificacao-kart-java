package br.com.gympass.model;

import java.time.Duration;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto representa o resultado do piloto na prova
 *
 *
 * @author Denis
 *
 * */
@Data
@NoArgsConstructor
public class ResultadoPilotoVO implements Comparable<ResultadoPilotoVO> {
    
    private Integer codigoPiloto;
    
    private Integer posicaoChegada;
    
    private String nomePiloto;
    
    private Duration tempoTotalProva;
    
    private String tempoTotalProvaText;
    
    private Integer totalVoltas;
    
    List<VoltaPistaVO> voltas;
    
    @Override
    public int compareTo(ResultadoPilotoVO piloto) {
        if (this.tempoTotalProva.compareTo(piloto.getTempoTotalProva()) < 1) {
            return -1;
        }
        if (this.tempoTotalProva.compareTo(piloto.getTempoTotalProva()) > 1) {
            return 1;
        }
        return 0;
    }
}
