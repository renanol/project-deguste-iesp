<<<<<<< HEAD
package br.com.deguste.enumeration;


public enum PerfilAcesso {
	SECRETARIO("Secret�rio"),
	CHEFE("Chefe"),
	FUNCIONARIO("Funcion�rio");
	
	private String descricao;
	
	PerfilAcesso(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
}
=======
package br.com.deguste.enumeration;


public enum PerfilAcesso {
	SECRETARIO("Secret�rio"),
	CHEFE("Chefe"),
	FUNCIONARIO("Funcion�rio");
	
	private String descricao;
	
	PerfilAcesso(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
}
>>>>>>> bafbdf018ef0117e4c6395b601a35fc901b6f766
