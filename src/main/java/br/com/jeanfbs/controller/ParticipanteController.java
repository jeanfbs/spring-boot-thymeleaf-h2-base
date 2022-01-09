package br.com.jeanfbs.controller;

import br.com.jeanfbs.entity.Credito;
import br.com.jeanfbs.entity.Participante;
import br.com.jeanfbs.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;

@Controller
@RequestMapping("/cpanel")
public class ParticipanteController extends BaseController{

    @Autowired
    private ParticipanteService service;

    @GetMapping("/participante")
    public ModelAndView participantePage() {

        ModelAndView view = getNewView();
        view.setViewName("pages/participante/participante");
        view.addObject("participante", new Participante());
        view.addObject("participantes", service.findByStatus(true));
        return view;
    }


    @PostMapping(value = { "/participante" })
    public String save(@ModelAttribute("participante") Participante participante,
                       RedirectAttributes red,
                       @RequestHeader(value = "referer", required = true) final String referer) {

        Participante participanteSalvo = service.save(participante);

        if(participanteSalvo.getId() != null){
            red.addFlashAttribute("msg", "Participante cadastrado com sucesso");
        }else{
            red.addFlashAttribute("msg", "Erro ao cadastrar o participante, tente novamente!");
        }

        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);
        return bf.toString();
    }

    @GetMapping(value = "/participante/consulta")
    public ModelAndView consulta(@RequestParam(value = "q") String query) {

        ModelAndView view = getNewView();
        view.setViewName("pages/participante/participante");
        view.addObject("participante", new Participante());
        view.addObject("participantes", service.findByDescricaoContaining(query));
        return view;
    }

    @GetMapping(value = {"/participante/{id}"})
    public String delete(@PathVariable(required=false) Long id,
                         RedirectAttributes red,
                         @RequestHeader(value = "referer", required = true) final String referer) {

        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);

        Participante participante = service.findById(id);
        if(participante.getCreditos().size() > 0){
            red.addFlashAttribute("msg", String.format("Não é possível excluir esse participante, " +
                    "o mesmo possui R$ %.02f de créditos disponível para uso.", participante.totalCreditos()));
            return bf.toString();
        }
        service.updateStatus(participante.getId(), false);


        red.addFlashAttribute("msg", "Participante excluido com sucesso");
        return bf.toString();
    }



    @GetMapping("/credito")
    public ModelAndView creditoPage() {

        ModelAndView view = getNewView();
        view.setViewName("pages/credito/credito");
        view.addObject("participante", new Participante());
        view.addObject("credito", new Credito());
        return view;
    }


    @GetMapping(value = "/credito/consulta")
    public ModelAndView consultaCredito(@RequestParam(value = "id") Long id) throws ParseException {

        ModelAndView view = getNewView();
        view.setViewName("pages/credito/credito");

        Participante findParticipante = service.findById(id);
        if(findParticipante.getId() == null){
            view.addObject("msg","Nenhum participante foi encontrado.");
        }
        view.addObject("participante", findParticipante);
        view.addObject("credito", new Credito());
        return view;
    }


    @PostMapping(value = { "/credito" })
    public String saveCredito(
                @RequestParam("id") Long id,
                @RequestParam("valor") Double valor,
                @RequestParam("data") String data,
                RedirectAttributes red,
                @RequestHeader(value = "referer", required = true) final String referer) {

        Participante participante = service.findById(id);
        Credito credito = service.adicionarCredito(new Credito(valor, data, participante));

        if(credito.getId() != null){
            red.addFlashAttribute("msg", String.format("R$ %.02f de crédito foram adicionados.", credito.getValor()));
        }else{
            red.addFlashAttribute("msg", "Ocorreu um erro ao adicionar o credito a conta.");
        }

        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);
        return bf.toString();
    }


    @GetMapping(value = {"/credito/{id}"})
    public String deleteCredito(@PathVariable(required=false) Long id,
                         RedirectAttributes red,
                         @RequestHeader(value = "referer", required = true) final String referer) {

        Credito credito = service.getCredito(id);
        service.deleteCredito(credito);
        red.addFlashAttribute("msg", String.format("R$ %.02f de crédito foram retirados da conta.", credito.getValor()));
        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);

        return bf.toString();
    }

}
