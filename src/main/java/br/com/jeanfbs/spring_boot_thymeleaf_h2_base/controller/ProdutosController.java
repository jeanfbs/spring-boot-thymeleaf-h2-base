package br.com.jeanfbs.spring_boot_thymeleaf_h2_base.controller;

import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.entity.Produto;
import br.com.jeanfbs.spring_boot_thymeleaf_h2_base.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cpanel/produtos")
public class ProdutosController extends BaseController{

    @Autowired
    private ProdutoService service;


    @GetMapping(value = {"/"})
    public ModelAndView produtosPage() {

        ModelAndView view = getNewView();
        view.setViewName("pages/produtos/produtos");
        view.addObject("produto", new Produto());
        view.addObject("produtos", service.findByStatus(true));
        return view;
    }


    @PostMapping(value = { "/" })
    public String save(@ModelAttribute("produto") Produto produto,
                       RedirectAttributes red,
                       @RequestHeader(value = "referer", required = true) final String referer) {

        Produto produtoSalvo = service.save(produto);

        if(produtoSalvo.getId() != null){
            red.addFlashAttribute("msg", "Produto cadastrado com sucesso");
        }else{
            red.addFlashAttribute("msg", "Erro ao cadastrar o produto, tente novamente!");
        }

        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);
        return bf.toString();
    }

    @GetMapping(value = "/consulta")
    public ModelAndView consulta(@RequestParam(value = "q") String query) {

        ModelAndView view = getNewView();
        view.setViewName("pages/produtos/produtos");
        view.addObject("produto", new Produto());
        view.addObject("produtos", service.findByDescricaoContaining(query));
        return view;
    }

    @GetMapping(value = {"/{id}"})
    public String delete(@PathVariable(required=false) Long id,
                                RedirectAttributes red,
                               @RequestHeader(value = "referer", required = true) final String referer) {

        Produto produto = service.findById(id);
        service.updateStatus(produto.getId(), false);

        StringBuffer bf = new StringBuffer("redirect:");
        bf.append(referer);
        red.addFlashAttribute("msg", "Produto excluido com sucesso");
        return bf.toString();
    }

}
