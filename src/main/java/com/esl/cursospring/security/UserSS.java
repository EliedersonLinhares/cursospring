package com.esl.cursospring.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.esl.cursospring.domain.enums.Perfil;

public class UserSS implements UserDetails{//Classe que defini metdos para autenticação de usuario

	private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

	
   public UserSS() {
	   
   }
    
    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		
		/*
		 * Construtor irá gerar a lista de authorities a partir da lista dos perfis
		 */
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}


	public Integer getId() {
    	return id;
    }
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
