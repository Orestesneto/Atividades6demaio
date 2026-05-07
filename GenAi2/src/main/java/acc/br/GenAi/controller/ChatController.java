package acc.br.GenAi.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import acc.br.model.Perguntas;
import acc.br.repository.PerguntasRepository;

@RestController
public class ChatController {

    private final ChatClient chatClient;
    private final PerguntasRepository perguntasRepository;

    public ChatController(ChatClient.Builder builder, PerguntasRepository perguntasRepository) {
        this.chatClient = builder.build();
        this.perguntasRepository = perguntasRepository;
    }

    @GetMapping("/pergunta")
    public String perguntar(@RequestParam(defaultValue = "Qual a capital do Brasil?") String mensagem) {
        // 1. Chama a IA
        String resposta = chatClient.prompt()
                                    .user(mensagem)
                                    .call()
                                    .content();

        // 2. Desafio: Gravar no banco de dados
        Perguntas novaPergunta = new Perguntas(mensagem, resposta);
        perguntasRepository.save(novaPergunta);

        return resposta;
    }
}