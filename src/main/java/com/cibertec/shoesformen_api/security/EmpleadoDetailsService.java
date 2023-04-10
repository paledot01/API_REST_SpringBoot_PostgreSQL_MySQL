package com.cibertec.shoesformen_api.security;

import com.cibertec.shoesformen_api.model.Empleado;
import com.cibertec.shoesformen_api.model.Rol;
import com.cibertec.shoesformen_api.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado emp = empleadoRepo.findByUsuario(username);
        List<GrantedAuthority> autorizacion = new ArrayList<>();

        if(emp == null) throw new UsernameNotFoundException("Usuario o password inválidos GAAAA");

        for(Rol rol : emp.getRoles() ) {
            autorizacion.add(new SimpleGrantedAuthority(rol.getNombreRol()));
        }
        System.out.println(emp);
        System.out.println(autorizacion);
        return new User(emp.getUsuario(), emp.getContrasena(), true, true, true, true, autorizacion); // user, pass, roles
    }

}
