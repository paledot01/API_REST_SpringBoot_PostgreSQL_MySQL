package com.cibertec.shoesformen_api.service;

import com.cibertec.shoesformen_api.exception.EntidadNotFoundException;
import com.cibertec.shoesformen_api.exception.ListEmptyException;
import com.cibertec.shoesformen_api.model.Distrito;
import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.Estado;
import com.cibertec.shoesformen_api.model.Rol;
import com.cibertec.shoesformen_api.model.dto.EmpleadoDTO;
import com.cibertec.shoesformen_api.repository.DistritoRepository;
import com.cibertec.shoesformen_api.repository.EmpleadoRepository;
import com.cibertec.shoesformen_api.repository.EstadoRepository;
import com.cibertec.shoesformen_api.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepo;
    @Autowired
    private DistritoRepository distritoRepo;
    @Autowired
    private EstadoRepository estadoRepo;
    @Autowired
    private RolRepository rolRepo;


    @Override
    public List<Empleado> listar() throws ListEmptyException {
        List<Empleado> lista = empleadoRepo.findAll();
        if(lista.isEmpty()){
            throw new ListEmptyException("lista Empleado vacio");
        }
        return lista;
    }

    @Override
    public void eliminar(Empleado empleado) {
        empleadoRepo.delete(empleado);
    }

    @Override
    public void eliminarByCodigo(String codigo) {
        empleadoRepo.deleteById(codigo);
    }

    @Override
    public Empleado guardar(Empleado empleado) throws IllegalArgumentException, EntidadNotFoundException{
        return empleadoRepo.save(empleado);
    }

    @Override
    public Optional<Empleado> getEmpleadoByCodigo(String codigo) throws EntidadNotFoundException {
        return empleadoRepo.findById(codigo);
    }

    @Override
    public String createNewCodigo() {
        String codigo_ultimo = empleadoRepo.getUltimoCodigo();
        String codigo_nuevo = "EM10001";
        if(codigo_ultimo != null){
            return codigo_ultimo;
        }
        return codigo_nuevo;
    }

    @Override
    public Empleado buildEmpleado(EmpleadoDTO dto) throws IllegalArgumentException, EntidadNotFoundException {

        // 1. que todos los datos sean validados -> @Valid
        // 1.5. que los ID no sean NULL -> IllegalArgumentException --> NO PASA
        // 2. que los codigos permitan encontrar ah las entidades -> EntidadNotFoundException
        // 3. que los campos en la BD no se repitan -> SQL
        // 4. que Xodo salga correcto -> Controller OK 201.

        Optional<Distrito> dis = distritoRepo.findById(dto.getCodDistrito());
        Optional<Estado> est = estadoRepo.findById(dto.getCodEstado());
        Optional<Rol> rol = rolRepo.findById("RL02");
        if(dis.isEmpty() || est.isEmpty() || rol.isEmpty()){
            throw new EntidadNotFoundException("Codigos invalidos");
        }

        Empleado empleado = new Empleado(
                //this.getUltimoCodigo(),
                dis.get(),
                est.get(),
                dto.getNombre(),
                dto.getApellidos(),
                dto.getDni(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getEmail(),
                dto.getUsuario(),
                dto.getContrasena(),
                Arrays.asList(rol.get()));
        return empleado;
    }

}
