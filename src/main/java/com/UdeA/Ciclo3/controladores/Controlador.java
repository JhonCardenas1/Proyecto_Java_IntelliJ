package com.UdeA.Ciclo3.controladores;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.servicios.EmpresaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/AgregarEmpresa")
    public String nuevaEmpresa(Model model) {
        Empresa emp = new Empresa();
        model.addAttribute("emp",emp);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaServicio.saveOrUpdateEmpresa(emp)==true){
            return "redirect:/verEmpresas";
        }
        return "redirect:/AgregarEmpresa";

    }





}
