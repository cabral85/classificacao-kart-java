package br.com.gympass.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Objeto para armazenar dados de uma volta na pista
 *
 * @author Denis
 *
 * */
@Data
@NoArgsConstructor
public class VoltaPistaVO implements Comparator<VoltaPistaVO>{
    
    private Integer codigo;
    
    private String nomePiloto;
    
    private Integer numero;
    
    private LocalTime hora;
    
    private Duration tempo;
    
    private String tempoText;
    
    private Float velocidadeMedia;
    
    @Override
    public int compare(VoltaPistaVO volta1, VoltaPistaVO volta2) {
        if((volta1.getHora() != null) && (volta2.getHora() != null))
            return volta1.getHora().compareTo(volta2.getHora());
        return 0;
    }
    
    @Override
    public String toString() {
        return "VoltaPistaVO [codigo =" + codigo + ", nomePiloto = " + nomePiloto + ", numero = " + numero + ", hora = " + hora
                + ", tempo = " + tempo + ", tempoText = " + tempoText + ", velocidadeMedia = " + velocidadeMedia + "]";
    }
}
