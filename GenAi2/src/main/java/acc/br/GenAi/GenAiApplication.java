package acc.br.GenAi;

import javax.swing.JOptionPane;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import acc.br.model.Perguntas;
import acc.br.repository.PerguntasRepository;



@SpringBootApplication
@EnableJpaRepositories("acc.br.repository")
@EntityScan("acc.br.model")
public class GenAiApplication {

    public static void main(String[] args) {
        // Inicia o contexto do Spring
        ConfigurableApplicationContext context = SpringApplication.run(GenAiApplication.class, args);

        // Recupera os serviços (Beans) do Spring
        ChatClient.Builder builder = context.getBean(ChatClient.Builder.class);
        PerguntasRepository repository = context.getBean(PerguntasRepository.class);
        
        ChatClient chatClient = builder.build();

        
        while (true) {
            String promptTeclado = JOptionPane.showInputDialog("Digite sua pergunta para a IA (ou 'sair' para encerrar):");

            if (promptTeclado == null || promptTeclado.equalsIgnoreCase("sair")) {
                break;
            }

            try {
                // Chama a IA
                String respostaIA = chatClient.prompt()
                                              .user(promptTeclado)
                                              .call()
                                              .content();

                // Desafio: Gravar no banco de dados
                Perguntas novaInteracao = new Perguntas(promptTeclado, respostaIA);
                repository.save(novaInteracao);

                // Exibe a resposta
                JOptionPane.showMessageDialog(null, "Resposta da IA:\n" + respostaIA);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro na comunicação: " + e.getMessage());
            }
        }
        
        // Encerra a aplicação ao sair do loop
        System.exit(SpringApplication.exit(context));
    }
}