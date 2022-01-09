package br.com.jeanfbs.controller;

import br.com.jeanfbs.auth.AuthenticationSession;
import br.com.jeanfbs.service.ParticipanteService;
import br.com.jeanfbs.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ParticipanteService participanteService;


    @Value("${application.name}")
    private String applicationName;

    public ModelAndView getNewView(){
        ModelAndView view = new ModelAndView();
        view.addObject("applicationName",applicationName);
        view.addObject("userName", AuthenticationSession.getUserLogged());
        view.addObject("totalVendas", pedidoService.findAll().stream().mapToDouble(v -> v.getTotaPedido()).sum());
        view.addObject("totalParticipantes", participanteService.count());
        Boolean [] permissoes =  getPermissoes(AuthenticationSession.getRoleLoggerd());
        view.addObject("permissoes", permissoes);
        return view;
    }


    private Boolean [] getPermissoes(String role){

        if(role.equals("ROLE_ADMIN")){
            return new Boolean[]{true, true,true, true};
        }
        else if(role.equals("ROLE_CAIXA")){
            return new Boolean[]{false, true,true, false};
        }
        else if(role.equals("ROLE_APOIO")){
            return new Boolean[]{true, false,false, false};
        }

        return new Boolean[]{false,false,false,false};
    }

}
