package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.repositories;

import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Modifying
    @Query(value = "update tbl_produto set status = :status where id_produto = :id", nativeQuery = true)
    void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

    List<Produto> findByStatus(Boolean status);

    List<Produto> findByDescricaoContainingAndStatus(String descricao, Boolean status);

    Produto findByIdAndStatus(Long id, Boolean status);
}
