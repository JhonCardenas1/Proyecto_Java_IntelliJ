package com.UdeA.Ciclo3.repositorio;

import com.UdeA.Ciclo3.modelos.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Anotación que le dice a Spring que  esta clase es un repositorio
public interface EmpresaRepositorio extends JpaRepository<Empresa,Integer> {

}
