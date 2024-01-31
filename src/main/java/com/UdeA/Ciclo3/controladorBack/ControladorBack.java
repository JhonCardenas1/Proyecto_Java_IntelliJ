package com.UdeA.Ciclo3.controladorBack;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.serviciosBack.EmpleadoServicioBack;
import com.UdeA.Ciclo3.serviciosBack.EmpresaServicioBack;
import com.UdeA.Ciclo3.serviciosBack.MovimientosServicioBack;
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

    @Autowired
    MovimientosServicioBack movimientosServicioBack;

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

    //MOVIMIENTOS DE DINERO

    @GetMapping("/movimientos") //Consultar todos los movimientos
        public List<MovimientoDinero> verMovimientos() {
        return movimientosServicioBack.getAllMovimientos();
    }

    @PostMapping("/movimientos")
    public MovimientoDinero guardarmovimientos(@RequestBody MovimientoDinero movimiento){
        return movimientosServicioBack.saveOrUpdateMovimiento(movimiento);

    }

    @GetMapping("/movimientos/{id}")
    public MovimientoDinero movimientoPorId(@PathVariable("id") Integer id){
        return movimientosServicioBack.getMovimientoById(id);
    }

    @PatchMapping("/movimientos/{id}")
    public MovimientoDinero actualizarMovimientoPorId(@PathVariable("id") Integer id, @RequestBody MovimientoDinero movimiento){
        MovimientoDinero mov = movimientosServicioBack.getMovimientoById(id);
        mov.setConcepto(movimiento.getConcepto());
        mov.setMonto(movimiento.getMonto());
        mov.setUsuario(movimiento.getUsuario());
        return movimientosServicioBack.saveOrUpdateMovimiento(mov);

    }

    @DeleteMapping("/movimientos/{id}") //Metodo para eliminar movimientos por id
    public String eliminarMovimientoId(@PathVariable("id") Integer id){
        boolean respuesta = movimientosServicioBack.deleteMovimiento(id); //Eliminamos usando el servicio del nuestro service
        if(respuesta){ //Si la respuesta booleana es true, si se elimino
            return "Se pudo eliminar correctamente el movimientos con el id "+id;
        }
        //Si la respuesta boolenana es falsa, no se elimino
        return "No se pudo eliminar correctamente el movimiento con id "+id;
    }

    @GetMapping("/empleados/{id}/movimientos") //Consultar movimientos por el id del empleado
    public ArrayList<MovimientoDinero> movimientosPorEmpleados(@PathVariable("id") Integer id){
        return movimientosServicioBack.obtenerPorEmpleado(id);

    }

    @GetMapping("/enterprises/{id}/movimientos")//Consultar movimientos que pertenecesn a una empresa por el id de la empresa
    public ArrayList<MovimientoDinero> movimientosPorEmpresa(@PathVariable("id") Integer id){
        return movimientosServicioBack.obtenerporEmpresa(id);
    }



}
