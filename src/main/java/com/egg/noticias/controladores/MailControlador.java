package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.servicios.BusinessService;
import com.egg.noticias.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Raúl Gómez
 */
@Controller
public class MailControlador {

//    @Autowired
//    private BusinessService emailService;
//
//    @Autowired
//    private UsuarioServicio usuarioServicio;
//
//    @GetMapping("/enviarEmail/{id}")
//    public String sendEmail( @PathVariable String id, ModelMap modelo) {
//            Usuario usuario = usuarioServicio.getOne(id);
//            modelo.put("usuario", usuario);
//        return "email";
//    }
//
//    @PostMapping("/envioEmail/{id}")
//    public String sendEmail2(@RequestParam(required = false) String email, @RequestParam String titulo, @RequestParam String textarea, @PathVariable(required = false) String id, ModelMap modelo) {
//        try {
//            emailService.sendEmail(email, titulo, textarea, id);
//            Usuario usuario = usuarioServicio.getOne(id);
//            modelo.put("usuario", usuario);
//            System.out.println(usuario.toString());
//            modelo.put("exito", "Mail enviado correctamente");
//            return "redirect:/admin/usuarios";
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//            return "redirect:/enviarEmail/{id}";
//        }
//    }

}
