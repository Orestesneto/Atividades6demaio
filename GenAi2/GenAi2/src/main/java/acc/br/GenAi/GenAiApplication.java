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
        System.setProperty("java.awt.headless", "false");
        ConfigurableApplicationContext context = SpringApplication.run(GenAiApplication.class, args);

        ChatClient.Builder builder = context.getBean(ChatClient.Builder.class);
        PerguntasRepository repository = context.getBean(PerguntasRepository.class);
        ChatClient chatClient = builder.build();

        while (true) {
            String promptTeclado = JOptionPane.showInputDialog("Digite sua pergunta (ou 'sair'):");

            if (promptTeclado == null || promptTeclado.equalsIgnoreCase("sair")) {
                break;
            }

            try {
                String respostaIA = chatClient.prompt()
                                              .user(promptTeclado)
                                              .call()
                                              .content();

                // SALVANDO NO BANCO
                repository.save(new Perguntas(promptTeclado, respostaIA));

                JOptionPane.showMessageDialog(null, "IA: " + respostaIA);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
    }
}