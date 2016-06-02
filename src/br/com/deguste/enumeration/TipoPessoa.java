<<<<<<< HEAD
package br.com.deguste.enumeration;

public enum TipoPessoa {
	
	PESSOA_FISICA(1,"Pessoa Física"),
	PESSOA_JURIDICA(2,"Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	private TipoPessoa(int codigo, String descricao) {
		this.codigo = codigo;
		this.setDescricao(descricao);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
=======
package br.com.deguste.enumeration;

public enum TipoPessoa {
	
	PESSOA_FISICA(1,"Pessoa Física"),
	PESSOA_JURIDICA(2,"Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	private TipoPessoa(int codigo, String descricao) {
		this.codigo = codigo;
		this.setDescricao(descricao);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
>>>>>>> bafbdf018ef0117e4c6395b601a35fc901b6f766
