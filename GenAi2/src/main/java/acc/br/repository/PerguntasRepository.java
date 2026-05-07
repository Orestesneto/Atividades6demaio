package acc.br.repository;

import acc.br.model.Perguntas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntasRepository extends JpaRepository<Perguntas, String> {
    // O Spring Data JPA cria o método save() automaticamente aqui.
}