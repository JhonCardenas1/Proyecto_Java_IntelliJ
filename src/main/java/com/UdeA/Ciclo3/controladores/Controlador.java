package com.UdeA.Ciclo3.controladores;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.servicios.EmpresaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class Controlador {

    @Autowired
    EmpresaServicio empresaServicio;

    @GetMapping({"/","/verEmpresas"})
    public String verEmpresas(Model model){
        List<Empresa> listaEmpresas=empresaServicio.getAllEmpresas();
        model.addAttribute("emplist", listaEmpresas);
        return "verEmpresas";
    }



}
