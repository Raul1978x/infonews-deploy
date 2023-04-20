package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.entidades.Usuario;
//import com.egg.noticias.entidades.WeatherData;
import com.egg.noticias.excepciones.MiExcepcion;
import com.egg.noticias.servicios.BusinessService;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.UsuarioServicio;
import com.egg.noticias.servicios.WeatherServicio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    private static final Logger logger = LoggerFactory.getLogger(AdminControlador.class);

    @Autowired
    private NoticiaServicio noticiaServicio;
    DateFormat dateFormat = new SimpleDateFormat("EEEEEEEEEE, d MMM yyyy");
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private WeatherServicio weatherServicio;

    @GetMapping("/dashboard")
    public String homeAdmin(HttpSession session, ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        model.addAttribute("noticias", noticiaServicio.listarNoticias());
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        model.addAttribute("logueado", logueado.getNombre());
        return "mostrar_admin";
    }

    @GetMapping("/cargar")
    public String noticia(ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        return "noticiaForm";
    }

    @PostMapping("/carga")
    public String cargarNoticia(@RequestParam MultipartFile archivo,
            @RequestParam String titulo, @RequestParam String cuerpo,
            @RequestParam String bajada, ModelMap model)
            throws MiExcepcion {
        mostrarFecha(model);
//        getWeatherData(model);
        try {
            List<Noticia> noticias = noticiaServicio.listarNoticias();
            model.put("noticias", noticias);
            noticiaServicio.crearNoticia(archivo, titulo, cuerpo, bajada);
            model.put("exito", "La Noticia fue cargada exitosamente");

        } catch (MiExcepcion e) {
            model.put("error", e.getMessage());

            return "noticiaForm";
        }
        return "redirect:/admin/dashboard";

    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap model) {
//        getWeatherData(model);
        mostrarFecha(model);
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(id);
        model.put("noticia", noticia);
        return "noticiaEditar";
    }

    @PostMapping("/edita/{id}")
    public String editaNoticia(@RequestParam String id, @RequestParam String titulo, 
            @RequestParam String bajada, @RequestParam String cuerpo, ModelMap modelo, 
            MultipartFile archivo) throws MiExcepcion {
//        getWeatherData(modelo);
        mostrarFecha(modelo);
        try {
            noticiaServicio.actualizar(archivo, id, titulo, cuerpo, bajada);
            modelo.put("exito", "la noticia se actualizo bien");
        } catch (Exception e) {

            modelo.put("error", e.getMessage());
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPorId(@PathVariable String id, ModelMap model) {
//        getWeatherData(model);
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        model.put("noticias", noticias);
        noticiaServicio.eliminarPorId(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/mostrar/{id}")
    public String modificar(@PathVariable String id, HttpSession session, ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        Noticia noticia = noticiaServicio.buscarNoticiaPorId(id);
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        model.addAttribute("logueado", logueado.getNombre());
        model.put("noticia", noticia);

        return "noticia_admin";
    }

    @GetMapping("/usuarios")
    public String mostrarUsuarios(ModelMap model) {
        mostrarFecha(model);
//        getWeatherData(model);
        List<Usuario> usuarios = usuarioServicio.usuarios();
        model.put("usuarios", usuarios);

        return "listaUsuarios";
    }

    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable String id, ModelMap model) throws MiExcepcion {
//        getWeatherData(model);
        Usuario usuario = usuarioServicio.getOne(id);
        model.put("usuario", usuario);
        return "registro_editar";
    }

    @PostMapping("/editaUsuario/{id}")
    public String editaUsuario(@RequestParam MultipartFile archivo, @PathVariable String id,
            @RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2,
            ModelMap model, HttpSession session) throws MiExcepcion {
//        getWeatherData(model);
        usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);
        model.put("exito", "el usuario se actualizo correctamente!!");
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/cambiarRol/{id}")
    public String cambiarRol(@PathVariable String id) {

        usuarioServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";

    }

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable String id) {

        usuarioServicio.eliminarUsuario(id);

        return "redirect:/admin/usuarios";

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

    private void mostrarFecha(ModelMap model) {
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

//    Envio de Email
    @Autowired
    private BusinessService emailService;

    @GetMapping("/enviarEmail/{id}")
    public String sendEmail(@PathVariable String id, HttpSession session,ModelMap modelo) {
        mostrarFecha(modelo);
//        getWeatherData(modelo);
        Usuario usuario = usuarioServicio.getOne(id);
        modelo.put("usuario", usuario);
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("logueado", logueado.getNombre());
        return "email";
    }

    @PostMapping("/envioEmail/{id}")
    public String sendEmail2(@RequestParam(required = false) String email, @RequestParam String titulo, @RequestParam String textarea, @PathVariable(required = false) String id, ModelMap modelo) {
        mostrarFecha(modelo);
//        getWeatherData(modelo);
        try {
            emailService.sendEmail(email, titulo, textarea, id);
            Usuario usuario = usuarioServicio.getOne(id);
            modelo.put("usuario", usuario);
            System.out.println(usuario.toString());
            modelo.put("exito", "Mail enviado correctamente");
            return "redirect:/admin/usuarios";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/admin/enviarEmail/{id}";
        }
    }
}
