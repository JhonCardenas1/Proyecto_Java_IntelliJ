package com.UdeA.Ciclo3.serviciosBack;

import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.repositorio.MovimientosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosServicioBack {

    @Autowired
    MovimientosRepositorio movimientosRepositorio;

    //Metodo que retornara la lista de movimientos usando metodos heredados del JPARepository
    public List<MovimientoDinero> getAllMovimientos(){
        List<MovimientoDinero> movimientosList = new ArrayList<>();
        movimientosRepositorio.findAll().forEach(movimiento -> movimientosList.add(movimiento)); //Recordemos el iterable que regresa el medtodo findall del Jpa y se guarda en una lista
        return movimientosList;
    }

    //Metodo que trae un objeto de tipo movimientos cuando cuento con el id de la misma
    public MovimientoDinero getMovimientoById(Integer id){
        return movimientosRepositorio.findById(id).get();
    }

    //Metodo para guardar o actualizar objeto de tipo empresa
    public MovimientoDinero  saveOrUpdateMovimiento(MovimientoDinero movimientoDinero){
        MovimientoDinero mov = movimientosRepositorio.save(movimientoDinero);
        return mov;
    }

    //Metodo para eliminar un registro de un empleado por id
    public boolean deleteMovimiento(Integer id) {
        movimientosRepositorio.deleteById(id);
        if (this.movimientosRepositorio.findById(id).isPresent()) {
            return false;
        }
        return true; //Si al buscar el movimiento no lo encontramos, si se elimino
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpleado(Integer id){ // Obtener teniendo en cuentael id del empleado
        return movimientosRepositorio.findByEmpleado(id);
    }

    public ArrayList<MovimientoDinero>obtenerporEmpresa(Integer id){//Obtener movimientos teniendo en cuenta en id de la empresa a la que pertenecen los empeleados registrados
        return  movimientosRepositorio.findByEmpresa(id);
    }

}
