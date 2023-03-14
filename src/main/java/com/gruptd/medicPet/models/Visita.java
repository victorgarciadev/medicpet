/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gruptd.medicPet.models;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author izan
 */
@Data
@Entity
@Table(name = "Visita")
public class Visita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnostic;
    private String medicacio;
    private String data_visita;
    
    @OneToOne
    @JoinColumn(name = "treballador_id")
    private Treballador treballador_id;
    
    @OneToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota_id;

}
