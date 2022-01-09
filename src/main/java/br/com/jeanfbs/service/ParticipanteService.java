package br.com.jeanfbs.service;


import br.com.jeanfbs.entity.Credito;
import br.com.jeanfbs.entity.Participante;
import br.com.jeanfbs.repositories.CreditoRepository;
import br.com.jeanfbs.repositories.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ParticipanteService {


    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private CreditoRepository creditorepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Participante save(Participante produto){
        return participanteRepository.save(produto);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Credito adicionarCredito(Credito credito){
        return creditorepository.save(credito);
    }

    @Transactional(readOnly = true)
    public Participante findById(Long id){
        Participante participante;
        try{
            participante = participanteRepository.findByIdAndStatus(id, true);
            participante.setCreditos(new ArrayList<>(participante.getCreditos()));
            participante.setPedidos(new ArrayList<>(participante.getPedidos()));
        }catch(NoSuchElementException | NullPointerException ex){
            participante = new Participante();
        }
        return participante;
    }

    @Transactional(readOnly = true)
    public List<Participante> findByStatus(Boolean status){
        return participanteRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Participante> findByDescricaoContaining(String descricao){
        return participanteRepository.findByNomeContainingAndStatus(descricao, true);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(Long id, Boolean status){
        participanteRepository.updateStatus(id, status);
    }


    @Transactional(readOnly = true)
    public Credito getCredito(Long id){
        return creditorepository.findById(id).get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteCredito(Credito credito){
        creditorepository.delete(credito);
    }

    public Long count() {
        return participanteRepository.count();
    }
}
