package com.gruptd.medicPet.controllers;

import com.gruptd.medicPet.models.CanviContrasenya;
import com.gruptd.medicPet.models.Usuari;
import com.gruptd.medicPet.services.UsuariServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * *
 * Controlador per la vista del control d'Usuaris
 *
 *
 * @author izan
 */
@Controller
@Slf4j
public class UsuariController {

    @Autowired
    private UsuariServices usuariServices;

    /**
     * *
     * Controlador per la gestió del perfil de l'usuari
     *
     * @param model
     * @param incorrecta
     * @param novaInvalida
     * @param correcta
     * @param imatge
     * @return
     */
    @GetMapping("/medicpet/perfil")
    public String perfilUsuari(Model model, @RequestParam(name = "incorrecta", required = false) Boolean incorrecta,
            @RequestParam(name = "novaInvalida", required = false) Boolean novaInvalida,
            @RequestParam(name = "correcta", required = false) Boolean correcta,
            @RequestParam(name = "imatge", required = false) Boolean imatge) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuari usuari = usuariServices.getByUsername(username);
        // Accedir als atributs
        String nomUsuariComplert = usuari.getNom();
        String rol = usuari.getRol_id().getNom();
        CanviContrasenya password = new CanviContrasenya();
        model.addAttribute("userName", username);
        model.addAttribute("treballador", usuari);
        model.addAttribute("nomUsuariComplert", nomUsuariComplert);
        model.addAttribute("rol", rol);
        model.addAttribute("contrasenya", password);

        if (incorrecta != null && incorrecta == true) {
            model.addAttribute("incorrecta", incorrecta);
        }
        if (novaInvalida != null && novaInvalida == true) {
            model.addAttribute("novaInvalida", novaInvalida);
        }
        if (correcta != null && correcta == true) {
            model.addAttribute("correcta", correcta);
        }
        if (imatge != null && imatge == true) {
            model.addAttribute("imatge", imatge);
        }

        return "perfil";
    }

    /**
     * *
     * Actualitza l'usuari a la BBDD
     *
     * @param usuari
     * @return
     */
    @PostMapping("/medicpet/perfil/guardar")
    public String modificarUsuari(Usuari usuari) {
        usuariServices.update(usuari);

        return "redirect:/medicpet/perfil";
    }

    /***
     * Controlador per actualizar la contrasenya de l'usuaria la BBDD
     * @param password
     * @param redirectAtr
     * @return 
     */
    @PostMapping("/medicpet/perfical/canviarContrasenya")
    public String modificarContrasenya(CanviContrasenya password, RedirectAttributes redirectAtr) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuari usuari = usuariServices.getByUsername(username);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String contrasenyaActualSend = password.getContraActual();
        String contrasenyaActual = usuari.getContrasenya();
        System.out.println(contrasenyaActual);
        System.out.println(contrasenyaActualSend);
        if (passwordEncoder.matches(contrasenyaActualSend, contrasenyaActual)) {
            if (password.getContraNova().equals(password.getContraNovaConf())) {
                String contrasenyaNova = passwordEncoder.encode(password.getContraNova());
                usuari.setContrasenya(contrasenyaNova);
                usuariServices.update(usuari);
                redirectAtr.addAttribute("correcta", true);
            } else {
                redirectAtr.addAttribute("novaInvalida", true);
            }
        } else {
            redirectAtr.addAttribute("incorrecta", true);
        }

        return "redirect:/medicpet/perfil";
    }
}
