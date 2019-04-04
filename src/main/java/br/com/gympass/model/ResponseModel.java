package br.com.gympass.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class ResponseModel {
    
    @Id
    private int codigo;
    private String mensagem;
    
    public ResponseModel(int codigo, String mensagem) {
        this.codigo   = codigo;
        this.mensagem =  mensagem;
    }
    
}
