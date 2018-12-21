package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.service;

import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity.Pedido;
import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity.Produto;
import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pedido save(Pedido pedido){
        return repository.save(pedido);
    }

    @Transactional(readOnly = true)
    public Pedido findById(Long id){

        Pedido pedido;
        try{
            pedido = repository.findById(id).get();
            pedido.setProduto(new Produto(pedido.getProduto()));

        }catch(NoSuchElementException | NullPointerException ex){
            pedido = new Pedido();
        }
        return pedido;
    }

    @Transactional(readOnly = true)
    public List<Pedido> findAll(){
        return repository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Pedido pedido){
        repository.delete(pedido);
    }
}
