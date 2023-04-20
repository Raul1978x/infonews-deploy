package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Usuario;
//import com.egg.noticias.entidades.WeatherData;
import com.egg.noticias.excepciones.MiExcepcion;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class InicioControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");

    @Autowired
    private UsuarioServicio usuarioServicio;

     @Autowired
    private WeatherServicio weatherServicio;
    
    @GetMapping("")
    public String home(HttpSession session, ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        return "mostrar";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam MultipartFile archivo, 
            @RequestParam String nombre, @RequestParam String email, 
            @RequestParam String password, @RequestParam String password2, 
            ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        try {
            usuarioServicio.registrar(archivo, nombre, email, password, password2);
            model.put("exito", "Usuario Registrado");

            return "redirect:/";

        } catch (MiExcepcion ex) {
            model.put("error", ex.getMessage());
            model.put("nombre", nombre);
            model.put("email", email);

            return "registro";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        if (error  != null) {
            model.put("error", "Usuario o contraseña incorrecto!!");
        }
        return "login";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap model, HttpSession session){
//        getWeatherData(model);
                
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        model.put("usuario", usuario);
        return "usuarioModificar";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id, 
            @PathVariable String nombre,@PathVariable String email, 
            @PathVariable String password, @PathVariable String password2, 
            ModelMap model){
//        getWeatherData(model);
                
        try {
            usuarioServicio.actualizar(archivo, password2, nombre, email, password, password2);
            model.put("exito", "Usuario actualizado correctamente!");
            return "mostrar";
        } catch (MiExcepcion e) {
            model.put("error", e.getMessage());
            model.put("nombre", nombre);
            model.put("email", email);
        return "usuarioModificar";
        }
    }
    
    private void mostrarFecha(ModelMap model){
        String date = dateFormat.format(new Date());
        model.addAttribute("fecha", date);
    }
    
//    public void getWeatherData(ModelMap model) {
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
