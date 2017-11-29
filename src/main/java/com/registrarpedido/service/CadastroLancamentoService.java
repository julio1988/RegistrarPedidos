package com.registrarpedido.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.registrarpedido.model.Lancamento;
import com.registrarpedido.repository.LancamentoRepository;
import com.registrarpedido.util.Transactional;

public class CadastroLancamentoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentoRepository lancamentos;
	
	@Transactional
	public void salvar(Lancamento lancamento) throws NegocioException {
		if (lancamento.getDataPagamento() != null &&
				lancamento.getDataPagamento().after(new Date())) {
			throw new NegocioException(
					"Data de pagamento não pode ser uma data futura.");
		}
		
		this.lancamentos.guardar(lancamento);
	}
	
	@Transactional
	public void excluir(Lancamento lancamento) throws NegocioException {
		lancamento = this.lancamentos.porId(lancamento.getId());
		
		if (lancamento.getDataPagamento() != null) {
			throw new NegocioException("Não é possível excluir um lançamento pago!");
		}
		
		this.lancamentos.remover(lancamento);
	}
	
}