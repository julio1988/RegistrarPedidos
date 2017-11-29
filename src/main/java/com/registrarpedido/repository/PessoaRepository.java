package com.registrarpedido.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.registrarpedido.model.Pessoa;

public class PessoaRepository implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Pessoa porId(Long id) {
		return manager.find(Pessoa.class, id);
	}
	
	public List<Pessoa> todas() {
		TypedQuery<Pessoa> query = manager.createQuery(
				"from Pessoa", Pessoa.class);
		return query.getResultList();
	}

}