package acc.br.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Perguntas {

    @Id
    private String pergunta;
    private String resposta;

    // Construtor padrão obrigatório para o JPA
    public Perguntas() {}

    // Construtor usado no JOptionPane
    public Perguntas(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
    }

    // Getters e Setters
    public String getPergunta() { return pergunta; }
    public void setPergunta(String pergunta) { this.pergunta = pergunta; }
    public String getResposta() { return resposta; }
    public void setResposta(String resposta) { this.resposta = resposta; }
}