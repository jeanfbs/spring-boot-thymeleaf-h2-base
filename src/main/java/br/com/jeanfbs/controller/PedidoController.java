package br.com.jeanfbs.controller;

import br.com.jeanfbs.entity.Participante;
import br.com.jeanfbs.entity.Pedido;
import br.com.jeanfbs.entity.Produto;
import br.com.jeanfbs.service.ParticipanteService;
import br.com.jeanfbs.service.PedidoService;
import br.com.jeanfbs.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;

@Controller
@RequestMapping("/cpanel")
public class PedidoController extends BaseController {


    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private PedidoService pedidoService;


    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/bar")
    public ModelAndView barPage() {

        ModelAndView view = getNewView();
        view.setViewName("pages/pedido/pedido");
        view.addObject("participante", new Participante());
        view.addObject("pedido", new Pedido());
        view.addObject("produtos", produtoService.findByStatus(true));
        return view;
    }


    @GetMapping(value = "/bar/consulta")
    public ModelAndView consultaBar(@RequestParam(value = "id") Long id) throws ParseException {

        ModelAndView view = getNewView();
        view.setViewName("pages/pedido/pedido");

        Participante findParticipante = participanteService.findById(id);
        if(findParticipante.getId() == null){
            view.addObject("msg","Nenhum participante foi encontrado.");
        }
        if(findParticipante.isBelowAge()){
            view.addObject("alert","Atenção: O Participante é menor de idade. A venda de " +
                    "bebidas alcóolicas para menores de idade é proibida por lei!");
        }
        view.addObject("participante", findParticipante);
        view.addObject("pedido", new Pedido());
        view.addObject("produtos", produtoService.findByStatus(true));
        return view;
    }


    @PostMapping(value = { "/pedido" })
    public String saveCredito(
            @RequestParam("idParticipante") Long idParticipante,
            @RequestParam("idProduto") Long idProduto,
            @RequestParam("quantidade") Integer quantidade,
            RedirectAttributes red,
            @RequestHeader(value = "referer", required = true) final String referer) {

        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);

        Participante participante = participanteService.findById(idParticipante);
        Produto produto = produtoService.findById(idProduto);
        Double valorPedido = quantidade * produto.getPreco();

        if(participante.totalCreditos() < valorPedido){
            red.addFlashAttribute("msg", "O participante não possui saldo suficiente " +
                    "para esse pedido");
            return bf.toString();
        }

        Pedido pedido = pedidoService.save(new Pedido(quantidade, valorPedido, participante, produto));

        if(pedido.getId() != null){
            red.addFlashAttribute("msg", String.format("Um novo pedido no valor de R$ %.2f foi criado.", pedido.getValor()));
        }else{
            red.addFlashAttribute("msg", "Ocorreu um erro ao criar o pedido a conta.");
        }
        return bf.toString();
    }

    @GetMapping(value = {"/pedido/{id}"})
    public String deletePedido(@PathVariable(required=false) Long id,
                                RedirectAttributes red,
                                @RequestHeader(value = "referer", required = true) final String referer) {

        Pedido pedido = pedidoService.findById(id);
        pedidoService.delete(pedido);
        red.addFlashAttribute("msg", String.format("Pedido ID %d foi removido.", pedido.getId()));

        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);

        return bf.toString();
    }
}
