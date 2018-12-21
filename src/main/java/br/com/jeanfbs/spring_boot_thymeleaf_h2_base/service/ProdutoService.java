package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.service;

import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity.Produto;
import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Produto save(Produto produto){
        return repository.save(produto);
    }

    @Transactional(readOnly = true)
    public Produto findById(Long id){
        Produto produto;
        try{
            produto = repository.findByIdAndStatus(id, true);
        }catch(NoSuchElementException | NullPointerException ex){
            produto = new Produto();
        }
        return produto;
    }



    @Transactional(readOnly = true)
    public List<Produto> findByStatus(Boolean status){
        return repository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Produto> findByDescricaoContaining(String descricao){
        return repository.findByDescricaoContainingAndStatus(descricao, true);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(Long id, Boolean status){
        repository.updateStatus(id, status);
    }
}
