package sintactico;

import java.util.*;

import lexico.*;


public class AnalisadorSintatico {

	private int estado;
	public AnalisadorSintatico() {
		
	}
	
	//metodo para enviar os Simbolos
	public void enviarSimbolos(ArrayList <Token> tokens,ArrayList<Idetificador> id)
	{
		estado=0;
		Token token;
		for(int i=0;i<tokens.size();i++)
		{
			token =tokens.get(i);
			validarSintatico(token.getLexema(),id);
			
		}

	}
	public void validarSintatico(String simbolo,ArrayList<Idetificador> id)
	{
		switch(estado)
		{
			case 0:
				if(simbolo.equals("while"))
				{
					estado=6;
				}
				else{
					estado=0;
				}
			break;
			case 6:
				if(simbolo.equals("("))
				{
					estado=7;
				}
				else{
					estado=6;
					//throw new RuntimeException(" Erro  depois deo while vem (  e nao :"+simbolo);
				}
			break;
			case 7:
				if(simbolo.equals(")"))
				{
					estado=8;
				}
				else{
					estado=7;
					//throw new RuntimeException(" Erro  depois deo while vem )  e nao :"+simbolo);
				}
			break;
			case 8:
				if(simbolo.equals("{"))
				{
					estado=9;
					break;
				}
				else{
					estado=0;
					throw new RuntimeException(" Erro  depois deo while vem {  e nao :"+simbolo);
				}
			
			case 9:
				if(simbolo.equals("}"))
				{
					estado=10;
					break;
				}
				else{
					estado=0;
					throw new RuntimeException(" Erro  depois deo while vem }  e nao :"+simbolo);
				}
			
		}	
	}

}
