package com.UdeA.Ciclo3.controladorBack;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.serviciosBack.EmpresaServicioBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorBack {

    @Autowired
    EmpresaServicioBack empresaServicioBack;

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
}
