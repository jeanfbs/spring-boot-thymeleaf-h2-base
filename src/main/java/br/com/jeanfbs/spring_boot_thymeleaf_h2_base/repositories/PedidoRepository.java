package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.repositories;

import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
