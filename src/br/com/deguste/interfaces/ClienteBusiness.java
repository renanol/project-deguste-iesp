<<<<<<< HEAD
package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Cliente;

public interface ClienteBusiness extends IGenericDAO<Cliente> {
	
	public List<Cliente>consultaClienteAtivo(Cliente cliente);
	public Cliente salvar(Cliente cliente) throws Exception;
	public List<Cliente> selectFornecedores();

}
=======
package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Cliente;

public interface ClienteBusiness extends IGenericDAO<Cliente> {
	
	public List<Cliente>consultaClienteAtivo(Cliente cliente);
	public Cliente salvar(Cliente cliente) throws Exception;
	public List<Cliente> selectFornecedores();

}
>>>>>>> bafbdf018ef0117e4c6395b601a35fc901b6f766
