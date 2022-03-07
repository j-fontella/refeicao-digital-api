package digital.refeicao.repositorys.login;

import digital.refeicao.models.login.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    @Query(value = "select * from login.token where frk_usuario = :prkUsuario", nativeQuery = true)
    Optional<Token> findByFrkUsuario(Long prkUsuario);

    @Modifying
    @Transactional
    @Query(value = "delete from login.token where frk_usuario = :prkUsuario", nativeQuery = true)
    void limpar(Long prkUsuario);
}
