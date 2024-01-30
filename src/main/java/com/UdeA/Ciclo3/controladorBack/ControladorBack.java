package com.UdeA.Ciclo3.controladorBack;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.serviciosBack.EmpleadoServicioBack;
import com.UdeA.Ciclo3.serviciosBack.EmpresaServicioBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ControladorBack {

    //EMPRESAS

    @Autowired
    EmpresaServicioBack empresaServicioBack;
    @Autowired
    EmpleadoServicioBack empleadoServicioBack;

    @GetMapping("/enterprises") // Ver json de todas las empresas
    public List<Empresa> verEmpresas(){
        return empresaServicioBack.getAllEmpresas();
    }

    @PostMapping("/enterprises") //Guardar el json del body como una nueva empresa
    public Empresa guardarEmpresa(@RequestBody Empresa emp){
        return  this.empresaServicioBack.saveOrUpdateEmpresa(emp);
    }

    @GetMapping(path = "enterprises/{id}")
    public Empresa empresaPorID(@PathVariable("id") Integer id){
        return this.empresaServicioBack.getEmpresaById(id);
    }

    @PatchMapping("/enterprises/{id}")
    public Empresa actualizarEmpresaID(@PathVariable("id") Integer id, @RequestBody Empresa empresa){
        Empresa emp = empresaServicioBack.getEmpresaById(id);
        emp.setNombre(empresa.getNombre());
        emp.setDireccion(empresa.getDireccion());
        emp.setTelefono(empresa.getTelefono());
        emp.setNIT(empresa.getNIT());

        return empresaServicioBack.saveOrUpdateEmpresa(emp);

    }

    @DeleteMapping(path = "enterprises/{id}")
    public String eliminarEmpresaID(@PathVariable("id") Integer id){
        boolean respuesta = this.empresaServicioBack.deleteEmpresa(id);
        if(respuesta == true){
            return "Se elimino la empresa con id"+ id;
        }
        else {
            return "No elimino la empresa con id"+ id;
        }
    }


    //EMPLEADOS

    @GetMapping("/empleados") // Ver json de todos los empleados
    public List<Empleado> verEmpleado(){
        return empleadoServicioBack.getAllEmpleado();
    }
    @PostMapping("/empleados")
    public Optional<Empleado> guardarEmpleado(@RequestBody Empleado empl){
        return Optional.ofNullable(this.empleadoServicioBack.saveOrUpdateEmpleado(empl));
    }

    @GetMapping(path ="empleados/{id}")
    public Optional<Empleado> empleadoPorID(@PathVariable Integer id){
        return this.empleadoServicioBack.getEmpleadoById(id);
    }

    @GetMapping("/enterprises/{id}/empleados") //Consultar empleados por empresas
    public ArrayList<Empleado> EmpleadoPorEmpresa(@PathVariable("id") Integer id){
        return this.empleadoServicioBack.obtenerPorEmpresa(id);
    }

    @PatchMapping("/empleados/{id}")
    public Empleado actualizarEmpleado(@PathVariable("id") Integer id, @RequestBody Empleado empleado){
        Empleado empl = empleadoServicioBack.getEmpleadoById(id).get();
        empl.setNombre(empleado.getNombre());
        empl.setCorreo(empleado.getCorreo());
        empl.setEmpresa(empleado.getEmpresa());
        empl.setRol(empleado.getRol());

        return empleadoServicioBack.saveOrUpdateEmpleado(empl);
    }

    @DeleteMapping("/empleados/{id}") //Mentodo para eliminar empleado por id
    public String eliminarEmpleadoID(@PathVariable("id") Integer id){
        boolean respuesta = empleadoServicioBack.deleteEmpleado(id); //Eliminamos usando el servicio del nuestro service
        if(respuesta){ //Si la respuesta booleana es true, si se elimino
            return "Se pudo eliminar correctamente el empleado con el id "+id;
        }
        //Si la respuesta boolenana es falsa, no se elimino
        return "No se pudo eliminar correctamente el empleado con id "+id;
    }
}
