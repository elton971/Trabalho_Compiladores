package lexico;

public class Idetificador {

	private String memoria;
	private String valor;
	private String tipo;
	private String nome;
	
	 public Idetificador(){}

	    public Idetificador(String memoria, String valor, String tipo, String nome) {
	        this.memoria = memoria;
	        this.tipo = tipo;
	        this.valor = valor;
	    }

		public String getMemoria() {
			return memoria;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public void setMemoria(String memoria) {
			this.memoria = memoria;
		}

		public String getValor() {
			return valor;
		}

		public void setValor(String valor) {
			this.valor = valor;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
	    
}
