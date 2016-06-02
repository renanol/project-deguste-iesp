<<<<<<< HEAD
package br.com.deguste.interfaces;

import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Funcionario;



public interface FuncionarioBusiness extends IGenericDAO<Funcionario> {

	public Funcionario salvar(Funcionario funcionario) throws Exception;
	public void verificaFuncionario(Funcionario funcionario) throws Exception;
	public List<Funcionario>getFuncionariosAtivos() throws Exception;
}
=======
package br.com.deguste.interfaces;

import java.util.List;

import br.com.deguste.dao.IGenericDAO;
import br.com.deguste.model.entity.Funcionario;



public interface FuncionarioBusiness extends IGenericDAO<Funcionario> {

	public Funcionario salvar(Funcionario funcionario) throws Exception;
	public void verificaFuncionario(Funcionario funcionario) throws Exception;
	public List<Funcionario>getFuncionariosAtivos() throws Exception;
}
>>>>>>> bafbdf018ef0117e4c6395b601a35fc901b6f766
