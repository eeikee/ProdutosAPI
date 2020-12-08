package co.eeikee.model;

public class FornecedorDTO {

	private String nome;

	private String cnpj;


	public String getNome() {
		return nome;
	}

	public FornecedorDTO(String nome, String cnpj) {
		this.nome = nome;
		this.cnpj = cnpj;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
