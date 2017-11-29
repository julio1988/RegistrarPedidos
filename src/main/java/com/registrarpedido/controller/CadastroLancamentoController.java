package com.registrarpedido.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.registrarpedido.model.Lancamento;
import com.registrarpedido.model.TipoLancamento;
import com.registrarpedido.repository.PessoaRepository;
import com.registrarpedido.service.CadastroLancamentoService;
import com.registrarpedido.service.NegocioException;
import com.registrarpedido.model.Pessoa;
import com.registrarpedido.repository.LancamentoRepository;

@Named
@javax.faces.view.ViewScoped
public class CadastroLancamentoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroLancamentoService cadastro;
	
	@Inject
	private PessoaRepository pessoas;
	
	@Inject
	private LancamentoRepository lancamentos;
	
	private Lancamento lancamento;
	private List<Pessoa> todasPessoas;

	public void prepararCadastro() {
		this.todasPessoas = this.pessoas.todas();
		
		if (this.lancamento == null) {
			this.lancamento = new Lancamento();
		}
	}
	
	public List<String> pesquisarDescricoes(String descricao) {
		return this.lancamentos.descricoesQueContem(descricao);
	}
	
	public void dataVencimentoAlterada(AjaxBehaviorEvent event) {
		if (this.lancamento.getDataPagamento() == null) {
			this.lancamento.setDataPagamento(this.lancamento.getDataVencimento());
		}
	}
	
	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			this.cadastro.salvar(this.lancamento);
			
			this.lancamento = new Lancamento();
			context.addMessage(null, new FacesMessage("Lançamento salvo com sucesso!"));
		} catch (NegocioException e) {
			
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}
	}
	
	public List<Pessoa> getTodasPessoas() {
		return this.todasPessoas;
	}
	
	public TipoLancamento[] getTiposLancamentos() {
		return TipoLancamento.values();
	}
	
	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	
}
