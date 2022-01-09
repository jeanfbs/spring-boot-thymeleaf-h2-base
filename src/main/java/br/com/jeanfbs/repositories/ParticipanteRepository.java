package br.com.jeanfbs.repositories;

import br.com.jeanfbs.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {


    Participante findByIdAndStatus(Long id, Boolean status);

    List<Participante> findByStatus(Boolean status);

    List<Participante> findByNomeContainingAndStatus(String descricao, Boolean b);

    @Modifying
    @Query(value = "update tbl_participante set status = :status where id_participante = :id", nativeQuery = true)
    void updateStatus(Long id, Boolean status);
}
