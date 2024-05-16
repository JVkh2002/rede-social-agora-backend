package dio.springbootweb.repository;

import dio.springbootweb.model.Credenciais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredenciaisRepository extends JpaRepository<Credenciais, Integer> {

}
