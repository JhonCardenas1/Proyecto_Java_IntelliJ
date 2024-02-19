package com.UdeA.Ciclo3.controladores;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.repositorio.MovimientosRepositorio;
import com.UdeA.Ciclo3.servicios.EmpleadoServicio;
import com.UdeA.Ciclo3.servicios.EmpresaServicio;
import com.UdeA.Ciclo3.servicios.MovimientosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    MovimientosRepositorio movimientosRepositorio;

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
        return "redirect:/EditarEmpresa/"+emp.getId();
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
        String passEncriptada = passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);
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
        String passEncriptada = passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);
        if(empleadoServicio.saveOrUpdateEmpleado(empl)==true){
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/verEmpleados";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarEmpleados/"+empl.getId();
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
        return "verEmpleados"; //Llamamos al html con el emplelist de los empleados filtrados
    }


    //Movimientos de dinero

    @GetMapping("/verMovimientos")
    public String verMovimientos(@RequestParam(value="pagina", required=false, defaultValue = "0") int NumeroPagina,
                                 @RequestParam(value="medida", required=false, defaultValue = "30") int medida,
                                 Model model, @ModelAttribute("mensaje") String mensaje){
        Page<MovimientoDinero> paginaMovimientos= movimientosRepositorio.findAll(PageRequest.of(NumeroPagina,medida));
        //List<MovimientoDinero> listaMovimientos = movimientosServicio.getAllMovimientos();
        model.addAttribute("movlist",paginaMovimientos.getContent());
        //model.addAttribute("movlist",listaMovimientos);
        model.addAttribute("paginas",new int[paginaMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual", NumeroPagina);
        model.addAttribute("mensaje", mensaje);
        Long sumamonto = movimientosServicio.obtenerSumaMontos();
        model.addAttribute("SumaMontos", sumamonto); //Enviamos la suma de todos los montos a a platilla HTML
        return "verMovimientos"; //LLamamos al HTML
    }


    @GetMapping("/AgregarMovimiento")
    public String nuevoMovimiento(Model model, @ModelAttribute("mensaje") String mensaje) {
        MovimientoDinero movimiento = new MovimientoDinero();
        model.addAttribute("mov", movimiento);
        model.addAttribute("mensaje",mensaje);
        List<Empleado> listaEmpleados = empleadoServicio.getAllEmpleado();
        model.addAttribute("emplelist", listaEmpleados);
        return "agregarMovimiento"; //Lamar al HTML
    }

    @PostMapping("/GuardarMovimiento")
    public String guardarMovimiento(MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosServicio.saveOrUpdateMovimiento(mov)==true){
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/verMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AgregarMovimiento";
    }

    @GetMapping("/EditarMovimiento/{id}")
    public String editarMovimiento(Model model, @PathVariable Integer id, @ModelAttribute("mensaje") String mensaje){
        MovimientoDinero mov = movimientosServicio.getMovimientoById(id);
        model.addAttribute("mov", mov);
        model.addAttribute("mensaje", mensaje);

        List<Empleado> listaEmpleados = empleadoServicio.getAllEmpleado();
        model.addAttribute("emplelist", listaEmpleados);
        return "editarMovimiento";
    }

    @PostMapping("/ActualizarMovimiento")
    public String updateMovimiento(@ModelAttribute("mov") MovimientoDinero mov, RedirectAttributes redirectAttributes){
        if(movimientosServicio.saveOrUpdateMovimiento(mov)==true){
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/verMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditarMovimiento/"+mov.getId();
    }

    @GetMapping("/EliminarMovimiento/{id}")
    public String eliminarMovimiento(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        if (movimientosServicio.deleteMovimiento(id) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/verMovimientos";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/verMovimientos";
    }

    @GetMapping("/Empleado/{id}/Movimientos") //Filtro de movimientos por empléados
    public String movimientosPorEmpleado(@PathVariable("id") Integer id, Model model){
        List<MovimientoDinero> movList = movimientosServicio.obtenerPorEmpleado(id);
        model.addAttribute("movlist",movList);
        Long sumamonto = movimientosServicio.MontosPorEmpleado(id);
        model.addAttribute("SumaMontos", sumamonto); //Enviamos la suma de todos los montos a a platilla HTML
        return "verMovimientos"; //Llamamos al HTML
    }

    @GetMapping("/Empresa/{id}/Movimientos") //Filtro para obtener movimientos por empresas
    public String movimientosPorEmpresas(@PathVariable("id") Integer id, Model model){
        List<MovimientoDinero> movList = movimientosServicio.obtenerporEmpresa(id);
        model.addAttribute("movlist",movList);
        Long sumamonto = movimientosServicio.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos", sumamonto); //Enviamos la suma de todos los montos a a platilla HTML
        return "verMovimientos";
    }

    //Metodo para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
