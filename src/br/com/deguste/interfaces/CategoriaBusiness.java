<<<<<<< HEAD
package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Categoria;

public interface CategoriaBusiness extends IGenericDAO<Categoria>{
	
	public Categoria salvar(Categoria user)throws Exception;
	public List<Categoria> selectFornecedores();
}
=======
package br.com.deguste.interfaces;


import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Categoria;

public interface CategoriaBusiness extends IGenericDAO<Categoria>{
	
	public Categoria salvar(Categoria user)throws Exception;
	public List<Categoria> selectFornecedores();
}
>>>>>>> bafbdf018ef0117e4c6395b601a35fc901b6f766
