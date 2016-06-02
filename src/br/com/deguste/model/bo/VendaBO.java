<<<<<<< HEAD
package br.com.deguste.model.bo;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;

import br.com.deguste.dao.GenericHibernateDAO;
import br.com.deguste.interfaces.VendaBusiness;
import br.com.deguste.model.entity.Venda;

public class VendaBO extends GenericHibernateDAO<Venda> implements VendaBusiness, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4874294880153854187L;
	
	public VendaBO(){
		super(Venda.class);
	}
	
	@Override
	public Venda salvar(Venda venda) throws Exception{
		if(venda.getId() == null)
			super.create(venda);
		else
			super.update(venda);
		
		return venda;
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> selectAll() {
		return super.em.createQuery("SELECT v FROM Venda v").getResultList();
	}
	
	public List<Venda>consultaVenda(Venda venda) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder("SELECT v FROM Venda v WHERE 1=1 AND v.ativo = true");
		
		if(venda.getDataVenda() != null){
			query.append(" AND v.dataVenda =:dataVenda");
			parametros.put("dataVenda", venda.getDataVenda());
		}
		
		if(venda.getCliente().getId() != null){
			query.append(" AND v.cliente =:cliente");
			parametros.put("cliente", venda.getCliente());
		}
		
		TypedQuery<Venda> q = em.createQuery(query.toString(), Venda.class);
		if(parametros != null && !parametros.isEmpty()){
			for(Entry<String, Object> entry : parametros.entrySet()){
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return q.getResultList();
	}

}
=======
package br.com.deguste.model.bo;


import java.io.Serializable;
import java.util.List;

import br.com.deguste.dao.GenericHibernateDAO;
import br.com.deguste.interfaces.VendaBusiness;
import br.com.deguste.model.entity.Venda;

public class VendaBO extends GenericHibernateDAO<Venda> implements VendaBusiness, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4874294880153854187L;
	
	public VendaBO(){
		super(Venda.class);
	}
	
	@Override
	public Venda salvar(Venda venda) throws Exception{
		if(venda.getId() == null)
			super.create(venda);
		else
			super.update(venda);
		
		return venda;
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Venda> selectAll() {
		return super.em.createQuery("SELECT v FROM Venda v").getResultList();
	}

}
>>>>>>> bafbdf018ef0117e4c6395b601a35fc901b6f766
