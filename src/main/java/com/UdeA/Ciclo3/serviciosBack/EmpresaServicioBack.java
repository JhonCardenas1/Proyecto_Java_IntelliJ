package com.UdeA.Ciclo3.serviciosBack;

import com.UdeA.Ciclo3.modelos.Empresa;
import com.UdeA.Ciclo3.repositorio.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaServicioBack {

    @Autowired// Conectamos esta clase con el repositorio de empresa
    EmpresaRepositorio empresaRepositorio; //Creamos un objeto tipo empresa Repositorio para poder usar los metodos que dicha clase hereda

    //Metodo que retornara la lista de empresas usando metodos heredados del JPARepository
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        empresaRepositorio.findAll().forEach(empresa -> empresaList.add(empresa)); //Recordemos el iterable que regresa el medtodo findall del Jpa y se guarda en una lista
        return empresaList;
    }

    //Metodo que trae un objeto de tipo Empresa cuando cuento con el id de la misma
    public Empresa getEmpresaById(Integer id){
        return empresaRepositorio.findById(id).get();
    }

    //Metodo para guardar o actualizar objeto de tipo empresa
    public Empresa  saveOrUpdateEmpresa(Empresa empresa){
        return empresaRepositorio.save(empresa);
    }

    //Metodo para eliminar empresas registradas teniendo el ID
    public boolean deleteEmpresa(Integer id){
        empresaRepositorio.deleteById(id);

        if (empresaRepositorio.findById(id)!=null){ // Verificacion del servicio eliminacion
            return true;
        }
        return false;
    }


}
