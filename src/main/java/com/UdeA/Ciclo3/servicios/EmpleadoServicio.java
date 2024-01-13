package com.UdeA.Ciclo3.servicios;


import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EmpleadoServicio {

    @Autowired // Conectamos esta clase con el repositorio de empleado
    EmpleadoRepositorio empleadoRepositorio;

    //Metodo que retornara la lista de empleados usando metodos heredados del CrudRepository
    public List<Empleado> getAllEmpleado(){
        List<Empleado> empleadoList = new ArrayList<>();
        empleadoRepositorio.findAll().forEach(empleado -> empleadoList.add(empleado));
        return empleadoList;
    }

    //Metodo que trae un objeto de tipo Empleado cuando cuento con el id de la mismo
    public Optional <Empleado> getEmpleadoById(Integer id){
        return empleadoRepositorio.findById(id);
    }

    //Metodo para guardar o actualizar registros de empeleados
    public Empleado saveOrUpdateEmpleado(Empleado empleado){
        return empleadoRepositorio.save(empleado);
    }

    //Metodo para eliminar un registro de un empleado por id
    public boolean deleteEmpleado(Integer id){
        empleadoRepositorio.deleteById(id);
        if(empleadoRepositorio.findById(id).isPresent()){
            return false;
        }
        return true;
        /*if(empleadoRepositorio.findById(id) != null){
            return true;
        }
        return false;*/

    }
}
