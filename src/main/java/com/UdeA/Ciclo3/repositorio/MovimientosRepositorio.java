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

    //Metodo para filtrar movimientps por empresa
    @Query(value = "SELECT * FROM movimientos WHERE empleado_id in (SELECT id FROM empleado WHERE empresa_id= ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);

    //Metodo para ver la suma de todos los movimientos
    @Query(value = "SELECT SUM(monto) FROM movimientos", nativeQuery = true)
    public abstract Long SumarMonto();

    //Metodo para ver la suma de todos los montos por empleado
    @Query(value = "SELECT SUM(monto) from movimientos WHERE empleado_id=?1 ", nativeQuery = true)
    public abstract Long MontosPorEmpleado(Integer id); //Id del empleado

    //Metodo para ver la suma de todos los montos por empresa
    @Query(value = "SELECT SUM(monto) FROM movimientos WHERE empleado_id in (SELECT id FROM empleado WHERE empresa_id= ?1)", nativeQuery = true)
    public abstract Long MontosPorEmpresa(Integer id); //ID de la empresa

    //Metodo que se trae el id de un usuario cuando tengo su correo
    @Query(value = "SELECT id FROM empleado WHERE correo = ?1", nativeQuery = true)
    public abstract Integer IdPorCorreo(String correo);


}
