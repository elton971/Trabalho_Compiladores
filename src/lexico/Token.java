package lexico;

public class Token {
	
	private String lexema;
	

	public Token( String lexema) {
		super();
		this.lexema = lexema;
	}
	public Token() {
		super();
	}
	
	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	@Override
	public String toString() {
		return "Token [lexema: " + lexema + "]";
	}

	

}
