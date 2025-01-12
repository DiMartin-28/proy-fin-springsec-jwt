package com.dimartin.springsecurity21.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dimartin.springsecurity21.dto.AuthLoginRequestDTO;
import com.dimartin.springsecurity21.dto.AuthResponseDTO;
import com.dimartin.springsecurity21.model.UserSec;
import com.dimartin.springsecurity21.repository.IUserRepository;
import com.dimartin.springsecurity21.utils.JwtUtils;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserSec userSec = userRepository.findUserEntityByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + "no fue encontrado"));
		
		//con GrantedAuthority Spring Security maneja permisos
        List<GrantedAuthority> authorityList = new ArrayList<>();

        //Programación funcional a full
        //tomamos roles y los convertimos en SimpleGrantedAuthority para poder agregarlos a la authorityList
        userSec.getRolesList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));


        //ahora tenemos que agregar los permisos
        userSec.getRolesList().stream()
                .flatMap(role -> role.getPermissionsList().stream()) //acá recorro los permisos de los roles
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermission_name())));
		
		
		return new User(
				userSec.getUsername(),
				userSec.getPassword(),
				userSec.isEnabled(),
				userSec.isAccountNotExpired(),
				userSec.isCredentialNotExpired(),
				userSec.isAccountNotLocked(),
				authorityList
				);
	}
	
	 public AuthResponseDTO loginUser (AuthLoginRequestDTO authLoginRequest){

	        //recuperamos nombre de usuario y contraseña
	        String username = authLoginRequest.username();
	        String password = authLoginRequest.password();

	        Authentication authentication = this.authenticate (username, password);
	        //si todo sale bien
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String accessToken =jwtUtils.createToken(authentication);
	        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "login ok", accessToken, true);
	        return authResponseDTO;

	    }

	    public Authentication authenticate (String username, String password) {
	        //con esto debo buscar el usuario
	        UserDetails userDetails = this.loadUserByUsername(username);

	        if (userDetails==null) {
	            throw new BadCredentialsException("Ivalid username or password");
	        }
	        // si no es igual
	        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
	            throw new BadCredentialsException("Invalid password");
	        }
	        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
	    }
}


