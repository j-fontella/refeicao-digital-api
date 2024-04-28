package digital.refeicao.repositorys.login;

import digital.refeicao.dtos.response.login.UnidadeResponseDTO;
import digital.refeicao.models.login.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Query(value = "SELECT new digital.refeicao.dtos.response.login.UnidadeResponseDTO(u.prk, u.nome) FROM UsuarioUnidade us JOIN us.unidade u WHERE us.usuario.prk = :frkUsuario")
    List<UnidadeResponseDTO> findUnidadesByUsuario(Long frkUsuario);


}
