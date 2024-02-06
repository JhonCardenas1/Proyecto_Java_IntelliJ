package com.UdeA.Ciclo3.controladores;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.servicios.EmpleadoServicio;
import com.UdeA.Ciclo3.servicios.EmpresaServicio;
import com.UdeA.Ciclo3.servicios.MovimientosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class Controlador {

    @Autowired
    EmpresaServicio empresaServicio;
    @Autowired
    EmpleadoServicio empleadoServicio;
    @Autowired
    MovimientosServicio movimientosServicio;

    //EMPRESAS

    @GetMapping({"/","/verEmpresas"})
    public String verEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> listaEmpresas=empresaServicio.getAllEmpresas();
        model.addAttribute("emplist", listaEmpresas);
        model.addAttribute("mensaje", mensaje);
        return "verEmpresas"; //Lamarmos al HTML
    }
    @GetMapping("/AgregarEmpresa")
    public String nuevaEmpresa(Model model, @ModelAttribute("mensaje") String mensaje) {
        Empresa emp = new Empresa();
        model.addAttribute("emp", emp);
        model.addAttribute("mensaje",mensaje);
        return "agregarEmpresa";
    }
    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaServicio.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/verEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarEmpresa";

    }
    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empresa emp = empresaServicio.getEmpresaById(id);
        model.addAttribute("emp", emp);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }
    @PostMapping("/ActualizarEmpresa")
    public String updateEmpresa(@ModelAttribute("emp") Empresa emp, RedirectAttributes redirectAttributes){
        if(empresaServicio.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/verEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarEmpresa";
    }
    @GetMapping("/EliminarEmpresa/{id}")
        public String eliminarEmpresa(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        if (empresaServicio.deleteEmpresa(id) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/verEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/verEmpresas";

        /*try{
            empresaServicio.deleteEmpresa(id);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteError");
            return "redirect:/verEmpresas";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
        return "redirect:/verEmpresas";*/
    }


    //EMPLEADOS

    @GetMapping("/verEmpleados")
    public String verEmpleados(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empleado> listaEmpleados=empleadoServicio.getAllEmpleado();
        model.addAttribute("emplelist", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "verEmpleados"; //Lamarmos al HTML
    }

    @GetMapping("/AgregarEmpleado")
    public String nuevoEmpleado(Model model, @ModelAttribute("mensaje") String mensaje) {
        Empleado empl = new Empleado();
        model.addAttribute("empl", empl);
        model.addAttribute("mensaje",mensaje);
        List<Empresa> listaEmpresas = empresaServicio.getAllEmpresas();
        model.addAttribute("emprelist", listaEmpresas);
        return "agregarEmpleado"; //Lamar al HTML
    }

    @PostMapping("/GuardarEmpleado")
    public String guardarEmpleado(Empleado empl, RedirectAttributes redirectAttributes){
        if(empleadoServicio.saveOrUpdateEmpleado(empl)==true){
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/verEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarEmpleado";

    }

    @GetMapping("/EditarEmpleado/{id}")
    public String editarEmpleado(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        Empleado empl = empleadoServicio.getEmpleadoById(id).get();
        model.addAttribute("empl", empl);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas = empresaServicio.getAllEmpresas();
        model.addAttribute("emprelist", listaEmpresas);
        return "editarEmpleado";
    }

    @PostMapping("/ActualizarEmpleado")
    public String updateEmpleado(@ModelAttribute("empl") Empleado empl, RedirectAttributes redirectAttributes){
        if(empleadoServicio.saveOrUpdateEmpleado(empl)==true){
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/verEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarEmpleados";
    }

    @GetMapping("/EliminarEmpleado/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        if (empleadoServicio.deleteEmpleado(id) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/verEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/verEmpleados";
    }

    @GetMapping("/Empresa/{id}/Empleados") //Filtrar las empleados por empresa
    public String verEmpleadosPorEmpresas(@PathVariable("id") Integer id, Model model){
        List<Empleado> listaEmpleados =empleadoServicio.obtenerPorEmpresa(id);
        model.addAttribute("emplelist", listaEmpleados);
        return "verEmpleados"; //Llmamos al html con el emplelist de los empleados filtrados
    }


    //Movimientos de dinero

    @GetMapping("/verMovimientos")
    public String verMovimientos(Model model, @ModelAttribute("mensaje") String mensaje){
        List<MovimientoDinero> listaMovimientos=movimientosServicio.getAllMovimientos();
        model.addAttribute("movlist", listaMovimientos);
        model.addAttribute("mensaje", mensaje);
        return "verMovimientos"; //LLamamos al HTML
    }



}
