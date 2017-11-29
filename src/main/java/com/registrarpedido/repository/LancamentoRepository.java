package com.registrarpedido.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.registrarpedido.model.Lancamento;

public class LancamentoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Lancamento porId(Long id) {
		return manager.find(Lancamento.class, id);
	}
	
	public List<String> descricoesQueContem(String descricao) {
		TypedQuery<String> query = manager.createQuery(
				"select distinct descricao from Lancamento "
				+ "where upper(descricao) like upper(:descricao)", 
				String.class);
		query.setParameter("descricao", "%" + descricao + "%");
		return query.getResultList();
	}
	
	public List<Lancamento> todos() {
		TypedQuery<Lancamento> query = manager.createQuery(
				"from Lancamento", Lancamento.class);
		return query.getResultList();
	}

	public void adicionar(Lancamento lancamento) {
		this.manager.persist(lancamento);
	}
	
	public Lancamento guardar(Lancamento lancamento) {
		return this.manager.merge(lancamento);
	}
	
	public void remover(Lancamento lancamento) {
		this.manager.remove(lancamento);
	}

}