package br.com.jeanfbs.repositories;

import br.com.jeanfbs.entity.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoRepository  extends JpaRepository<Credito, Long> {

}
