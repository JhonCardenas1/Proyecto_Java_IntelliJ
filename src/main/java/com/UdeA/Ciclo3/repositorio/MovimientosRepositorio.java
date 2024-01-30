package com.UdeA.Ciclo3.repositorio;


import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MovimientosRepositorio extends JpaRepository<MovimientoDinero, Integer> {
    @Query(value = "SELECT * FROM movimientos WHERE empleado_id= ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpleado(Integer id);

    @Query(value = "SELECT * FROM movimientos WHERE empleado_id= ?1 in (SELECT id FROM WHERE empresa_id= ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);
}
