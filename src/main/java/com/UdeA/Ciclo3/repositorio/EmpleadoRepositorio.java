package com.UdeA.Ciclo3.repositorio;


import com.UdeA.Ciclo3.modelos.Empleado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepositorio extends CrudRepository <Empleado, Integer> {
}
