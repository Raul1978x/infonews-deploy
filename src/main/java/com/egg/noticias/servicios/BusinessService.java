package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.repositorios.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Raúl Gómez
 */
@Service
public class BusinessService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    public void sendEmail(String email, String titulo, String textarea, String id) {
        Usuario usuario = usuarioRepositorio.findById(id).get();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getEmail());
        message.setSubject(titulo);
        message.setText(textarea);
        mailSender.send(message); //método Send(envio), propio de Java Mail Sender.

    }
}
