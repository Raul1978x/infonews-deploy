package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.entidades.Usuario;
//import com.egg.noticias.entidades.WeatherData;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.UsuarioServicio;
import com.egg.noticias.servicios.WeatherServicio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Raúl Gómez
 */
@Controller
@RequestMapping("/user")
public class UserControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");

     @Autowired
    private WeatherServicio weatherServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("")
    public String usuarioHome(HttpSession session, ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        model.addAttribute("logueado", logueado.getNombre());
        System.out.println(logueado.getNombre());
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "mostrar_user";
    }

    @GetMapping("/mostrar/{id}")
    public String noticiaLeer(@PathVariable String id,HttpSession session, ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(id);
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        model.addAttribute("logueado", logueado.getNombre());
        model.put("noticia", noticia);

        return "noticia";
    }

    private void mostrarFecha(ModelMap model) {
        String date = dateFormat.format(new Date());
        model.addAttribute("fecha", date);
    }
    
//     public void getWeatherData(ModelMap model) {
//        WeatherData weatherData = weatherServicio.getWeatherData();
//
//        double temperature = weatherData.getData().get(0).getTemp();
//        String description = weatherData.getData().get(0).getWeather().getDescription();
//        String iconUrl = weatherData.getData().get(0).getWeather().getIconUrl();
//
//        model.put("temperature", temperature);
//        model.put("description", description);
//        model.put("iconUrl", iconUrl);
//    }
}
