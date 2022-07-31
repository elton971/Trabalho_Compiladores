package lexico;

public class Simbolos {
	private String  nome,Classificacao;

	public Simbolos(String nome,String Classificacao) {
		this.nome = nome;
		this.Classificacao = Classificacao;

	}
	public Simbolos()
	{

	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getClassificacao() {
		return Classificacao;
	}
	public void setClassificacao(String Classificacao) {
		this.Classificacao = Classificacao;
	}


}
