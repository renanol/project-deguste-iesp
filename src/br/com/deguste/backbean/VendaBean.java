<<<<<<< HEAD
package br.com.deguste.backbean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.deguste.model.bo.ClienteBO;
import br.com.deguste.model.bo.PedidoBO;
import br.com.deguste.model.bo.ProdutoBO;
import br.com.deguste.model.bo.VendaBO;
import br.com.deguste.model.entity.Categoria;
import br.com.deguste.model.entity.Cliente;
import br.com.deguste.model.entity.Pedido;
import br.com.deguste.model.entity.Produto;
import br.com.deguste.model.entity.Venda;
import br.com.deguste.util.FacesUtil;
import br.com.deguste.util.SessionControl;

@Named
@ViewScoped
public class VendaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 569047074730471751L;

	private Venda venda;

	@Inject
	private VendaBO vendaBO;

	private Cliente cliente;

	@Inject
	private ClienteBO clienteBO;

	@Inject
	private SessionControl session;

	private Produto produtoSelecionado;

	@Inject
	private ProdutoBO produtoBO;

	private Pedido pedido;

	@Inject
	private PedidoBO pedidoBO;

	private List<Pedido> carrinho;
	private List<Venda> listaVendas;

	private String param;
	
	//Rendereds da pagina
	
	private boolean statusRegister;
	private boolean dtRender;
	private boolean cadastroRendered;
	private boolean pesquisaRendered;
	private boolean botaoFecharRendered; 
	private boolean botaoApagarRendered;
	
	
	
	public VendaBean(){
		this.botaoApagarRendered = false;
		this.setCadastroRendered(false);
		this.setPesquisaRendered(true);
	}
	

	@PostConstruct
	public void inicialization() {
		if (venda == null) {
			venda = new Venda();
		}
		if(pedido == null){
			pedido = new Pedido();
		}

	}
	
	public void acaoPesquisar(){
		this.limpaBean();
		this.alterStatusRendered();
		this.dtRender = false;
	}
	
	public void alterStatusRendered() {
		if (cadastroRendered) {
			setCadastroRendered(false);
			setPesquisaRendered(true);
		} else if (pesquisaRendered) {
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}
	
	public void acaoCadastrar(){
		this.venda = new Venda();
		this.pedido = new Pedido();
		if(statusRegister == false){
			this.statusRegister = true;
		}
		this.alterStatusRendered();
	}
	
	public void desabilitaVenda(Venda venda){
		
		try {
			venda.setAtivo(false);
			vendaBO.salvar(venda);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Essa venda foi exluída", null);
		FacesContext.getCurrentInstance().addMessage(null, msg);
		venda = new Venda();
		this.listaVendas = vendaBO.consultaVenda(venda);
		
	}
	
	public void limpar(){
		this.venda = new Venda();
		this.pedido = new Pedido();
		this.statusRegister = true;
		this.botaoApagarRendered = false;
	}

	public void limpaBean() {
		carrinho = new ArrayList<Pedido>();
		listaVendas = new ArrayList<Venda>();
		produtoSelecionado = new Produto();
		pedido = new Pedido();
		venda = new Venda();
	}
	
	public void acaoAlterar(Venda venda) {
		this.venda = venda; 
		if (this.venda.getId() != null) {
			this.setStatusRegister(false);
			botaoApagarRendered = true;
			setCadastroRendered(true);
			setPesquisaRendered(false);
		}
	}

	public void procurarVendas() throws Exception{
		this.listaVendas = vendaBO.consultaVenda(venda);
		this.dtRender = true;
	}

	public void cadastroVendas() {
		Calendar dataVenda = new GregorianCalendar();
		BigDecimal valorDaVenda = BigDecimal.ZERO;
		try {
			if(session.getUsuarioSession() != null){
				venda.setUsuario(session.getUsuarioSession());
			}
			if(carrinho.size() > 0 ){
				for(Pedido pedido : carrinho){
					valorDaVenda = valorDaVenda.add(pedido.getProduto().getPreco());
				}
				if(valorDaVenda.compareTo(BigDecimal.ZERO) > 0){
					venda.setValorVenda(valorDaVenda);
				}
			}
			venda.setDataVenda(dataVenda.getTime());
			venda.setAtivo(true);
			vendaBO.salvar(venda);

			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Venda concluï¿½da", "."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
		}
	}

	public List<SelectItem> listaClientesItem() throws Exception {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(null, "Selecione"));
		for (Cliente cliente : clienteBO.getClientesAtivos()) {
			items.add(new SelectItem(cliente, cliente.getNome()));
		}

		return items;
	}

	public List<Produto> completeProduto(String str) {
		return produtoBO.autoComplete(str);
	}

	public void addProduto() {
		if (this.carrinho == null)
			carrinho = new ArrayList<Pedido>();

		if (this.produtoSelecionado != null && pedido.getQuantidade() > 0 && this.carrinho != null) {
			for (Pedido c : carrinho) {
				if (produtoSelecionado.equals(c.getProduto())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O produto jï¿½ estï¿½ na lista de compras.", ""));
					return;
				}
			}

			Pedido pedidoAtual = new Pedido();
			pedidoAtual.setProduto(produtoSelecionado);
			pedidoAtual.setQuantidade(pedido.getQuantidade());
			carrinho.add(pedidoAtual);
		}
	}

	public void removerPedido(Pedido pedidoSelecionado){
		if(carrinho != null && carrinho.size() > 0){
			carrinho.remove(pedidoSelecionado);
		}
	}

	public BigDecimal getTotalCarrinho(){
		BigDecimal totalBruto = BigDecimal.ZERO;
		if(carrinho != null && carrinho.size() > 0){
			for(Pedido pedido : carrinho )
				totalBruto = totalBruto.add(pedido.getProduto().getPreco());
		}
		return totalBruto;
	}


	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public VendaBO getVendaBO() {
		return vendaBO;
	}

	public void setVendaBO(VendaBO vendaBO) {
		this.vendaBO = vendaBO;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) throws Exception {
		try {
			this.param = param;
			if (param != null && !param.isEmpty())
				this.venda = vendaBO.selectById(Long.valueOf(param));

		} catch (NumberFormatException e) {
			throw new Exception("Formato de parametro ilegal");
		} catch (IllegalArgumentException e) {
			throw new Exception("Argumento ilegal");
		} catch (Exception e) {
			throw new Exception("Erro inesperado!");
		}
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public ProdutoBO getProdutoBO() {
		return produtoBO;
	}

	public void setProdutoBO(ProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PedidoBO getPedidoBO() {
		return pedidoBO;
	}

	public void setPedidoBO(PedidoBO pedidoBO) {
		this.pedidoBO = pedidoBO;
	}

	public List<Pedido> getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(List<Pedido> carrinho) {
		this.carrinho = carrinho;
	}

	public SessionControl getSession() {
		return session;
	}

	public void setSession(SessionControl session) {
		this.session = session;
	}


	public List<Venda> getListaVendas() {
		return listaVendas;
	}


	public void setListaVendas(List<Venda> listaVendas) {
		this.listaVendas = listaVendas;
	}


	public boolean isStatusRegister() {
		return statusRegister;
	}


	public void setStatusRegister(boolean statusRegister) {
		this.statusRegister = statusRegister;
	}


	public boolean isDtRender() {
		return dtRender;
	}


	public void setDtRender(boolean dtRender) {
		this.dtRender = dtRender;
	}


	public boolean isCadastroRendered() {
		return cadastroRendered;
	}


	public void setCadastroRendered(boolean cadastroRendered) {
		this.cadastroRendered = cadastroRendered;
	}


	public boolean isPesquisaRendered() {
		return pesquisaRendered;
	}


	public void setPesquisaRendered(boolean pesquisaRendered) {
		this.pesquisaRendered = pesquisaRendered;
	}


	public boolean isBotaoFecharRendered() {
		return botaoFecharRendered;
	}


	public void setBotaoFecharRendered(boolean botaoFecharRendered) {
		this.botaoFecharRendered = botaoFecharRendered;
	}


	public boolean isBotaoApagarRendered() {
		return botaoApagarRendered;
	}


	public void setBotaoApagarRendered(boolean botaoApagarRendered) {
		this.botaoApagarRendered = botaoApagarRendered;
	}
	

}
=======
package br.com.deguste.backbean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.deguste.model.bo.ClienteBO;
import br.com.deguste.model.bo.PedidoBO;
import br.com.deguste.model.bo.ProdutoBO;
import br.com.deguste.model.bo.VendaBO;
import br.com.deguste.model.entity.Cliente;
import br.com.deguste.model.entity.Pedido;
import br.com.deguste.model.entity.Produto;
import br.com.deguste.model.entity.Venda;
import br.com.deguste.util.FacesUtil;
import br.com.deguste.util.SessionControl;

@Named
@ViewScoped
public class VendaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 569047074730471751L;

	private Venda venda;

	@Inject
	private VendaBO vendaBO;

	private Cliente cliente;

	@Inject
	private ClienteBO clienteBO;

	@Inject
	private SessionControl session;

	private Produto produtoSelecionado;

	@Inject
	private ProdutoBO produtoBO;

	private Pedido pedido;

	@Inject
	private PedidoBO pedidoBO;

	private List<Pedido> carrinho;

	private String param;

	@PostConstruct
	public void inicialization() {
		if (this.venda == null) {
			venda = new Venda();
			pedido = new Pedido();
		}

	}

	public void limpaBean() {
		carrinho = new ArrayList<Pedido>();
		produtoSelecionado = new Produto();
		pedido = new Pedido();
		venda = new Venda();
	}

	public void buttonCanc() {
		venda = new Venda();
		FacesUtil.redirectTo("consultaVendas");
	}

	public void cadastroVendas() {
		Calendar dataVenda = new GregorianCalendar();
		BigDecimal valorDaVenda = BigDecimal.ZERO;
		try {
			if(session.getUsuarioSession() != null){
				venda.setUsuario(session.getUsuarioSession());
			}
			if(carrinho.size() > 0 ){
				for(Pedido pedido : carrinho){
					valorDaVenda = valorDaVenda.add(pedido.getProduto().getPreco());
				}
				if(valorDaVenda.compareTo(BigDecimal.ZERO) > 0){
					venda.setValorVenda(valorDaVenda);
				}
			}
			venda.setDataVenda(dataVenda.getTime());
			vendaBO.salvar(venda);

			this.limpaBean();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Venda concluï¿½da", "."));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), null));
		}
	}

	public List<SelectItem> listaClientesItem() throws Exception {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem(null, "Selecione"));
		for (Cliente cliente : clienteBO.getClientesAtivos()) {
			items.add(new SelectItem(cliente, cliente.getNome()));
		}

		return items;
	}

	public List<Produto> completeProduto(String str) {
		return produtoBO.autoComplete(str);
	}

	public void addProduto() {
		if (this.carrinho == null)
			carrinho = new ArrayList<Pedido>();

		if (this.produtoSelecionado != null && pedido.getQuantidade() > 0 && this.carrinho != null) {
			for (Pedido c : carrinho) {
				if (produtoSelecionado.equals(c.getProduto())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O produto jï¿½ estï¿½ na lista de compras.", ""));
					return;
				}
			}

			Pedido pedidoAtual = new Pedido();
			pedidoAtual.setProduto(produtoSelecionado);
			pedidoAtual.setQuantidade(pedido.getQuantidade());
			carrinho.add(pedidoAtual);
		}
	}

	public void removerPedido(Pedido pedidoSelecionado){
		if(carrinho != null && carrinho.size() > 0){
			carrinho.remove(pedidoSelecionado);
		}
	}

	public BigDecimal getTotalCarrinho(){
		BigDecimal totalBruto = BigDecimal.ZERO;
		if(carrinho != null && carrinho.size() > 0){
			for(Pedido pedido : carrinho )
				totalBruto = totalBruto.add(pedido.getProduto().getPreco());
		}
		return totalBruto;
	}


	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public VendaBO getVendaBO() {
		return vendaBO;
	}

	public void setVendaBO(VendaBO vendaBO) {
		this.vendaBO = vendaBO;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteBO getClienteBO() {
		return clienteBO;
	}

	public void setClienteBO(ClienteBO clienteBO) {
		this.clienteBO = clienteBO;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) throws Exception {
		try {
			this.param = param;
			if (param != null && !param.isEmpty())
				this.venda = vendaBO.selectById(Long.valueOf(param));

		} catch (NumberFormatException e) {
			throw new Exception("Formato de parametro ilegal");
		} catch (IllegalArgumentException e) {
			throw new Exception("Argumento ilegal");
		} catch (Exception e) {
			throw new Exception("Erro inesperado!");
		}
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public ProdutoBO getProdutoBO() {
		return produtoBO;
	}

	public void setProdutoBO(ProdutoBO produtoBO) {
		this.produtoBO = produtoBO;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PedidoBO getPedidoBO() {
		return pedidoBO;
	}

	public void setPedidoBO(PedidoBO pedidoBO) {
		this.pedidoBO = pedidoBO;
	}

	public List<Pedido> getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(List<Pedido> carrinho) {
		this.carrinho = carrinho;
	}

	public SessionControl getSession() {
		return session;
	}

	public void setSession(SessionControl session) {
		this.session = session;
	}

}
>>>>>>> bafbdf018ef0117e4c6395b601a35fc901b6f766
