package com.gruptd.medicPet;

//spring-boot.run.jvmArguments=-Xdebug -Xrunjdwp:transport=dt_socket,server=n,adsress=${jpda.address} jpda.listen=true
import com.gruptd.medicPet.models.Rol;
import com.gruptd.medicPet.models.Usuari;
import com.gruptd.medicPet.services.RolServices;
import com.gruptd.medicPet.services.UsuariServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mllanas
 */
//sobre @Slf4j: https://stackabuse.com/guide-to-logging-in-spring-boot/
/*Anotació @RestController fa refèrencia a la classe RestController que accepta les
peticions HTTP

*que es fan des del navegador.
 */
@Controller
@Slf4j  // Anotació que permet utilitzar l'API de Login
public class ControladorInici {

    @Autowired
    private UsuariServices usuariService;

    @Autowired
    private RolServices rolService;

    @GetMapping("/login")
    public String inici(@RequestParam(value = "message", required = false) String message, Model model) {
        if ("logout".equals(message)) {
            model.addAttribute("logout", true);
        }

        return "login";
    }

    @GetMapping("/")
    public String arrel() {
        return "redirect:/medicpet/clients";
    }

    @GetMapping("/registre")
    public String registre(Usuari usuari) {
        log.info("Executant el controlador de registre");
        return "registre";
    }

    @PostMapping("/nou-usuari")
    public String guardar(Usuari usuari) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String testPasswordEncoded = passwordEncoder.encode(usuari.getContrasenya());
        usuari.setContrasenya(testPasswordEncoded);
        Rol rol = rolService.getOne(usuari.getRol_id().getId());
        usuari.setRol_id(rol);
        usuariService.save(usuari);
        return "redirect:/login";
    }

    @GetMapping("/error/error403")
    public String noAutoritzat() {
        return "error403";
    }
}
