package com.esl.cursospring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esl.cursospring.domain.Estado;
import com.esl.cursospring.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;	
	
	public List<Estado> findAll(){
		return repo.findAllByOrderByNome();
	}
}
