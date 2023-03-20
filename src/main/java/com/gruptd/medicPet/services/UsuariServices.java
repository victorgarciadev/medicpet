/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gruptd.medicPet.services;

import com.gruptd.medicPet.dao.UsuariDAO;
import com.gruptd.medicPet.models.Usuari;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aquesta clase crea totes les funcions del CRUD amb la BBDD sobre la clase
 * Usuari. S'ha d'importar i es pot utilitzar per fer peticions i modificacions
 * a la BBDD a la taula de Usuari
 *
 * @author izan
 */
@Service
public class UsuariServices implements ServicesInterface<Usuari> {

    @Autowired
    private UsuariDAO usuariDao;

    @Transactional(readOnly = true)
    @Override
    public Iterable<Usuari> findAll() {
        return usuariDao.findAll();
    }

    @Transactional
    @Override
    public void save(Usuari u) {
        usuariDao.save(u);
    }

    @Transactional
    @Override
    public void delete(Usuari u) {
        usuariDao.delete(u);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuari getOne(Long id) {
        try {
            return usuariDao.findById(id).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    @Override
    public void update(Usuari u) {
        Usuari usuariBD = getOne(u.getId());
        if (usuariBD != null) {
            usuariBD.setNom(u.getNom());

            save(usuariBD);
        } else {
            System.out.println("L'usuari no existeix.");
        }
    }
}
