package sintactico;

import java.util.*;

import lexico.*;


public class AnalisadorSintatico {

	private int estado;
	public AnalisadorSintatico() {
		
	}
	
	//metodo para enviar os Simbolos
	public void enviarSimbolos(ArrayList <Token> tokens,ArrayList<Idetificador> id,ArrayList<String> operadores,ArrayList<String> constantesNumericas)
	{
		estado=0;
		Token token;
		for(int i=0;i<tokens.size();i++)
		{
			token =tokens.get(i);
			
			validarSintatico(token.getLexema(),id,operadores,constantesNumericas);
			
			
		}

	}
	public void validarSintatico(String simbolo,ArrayList<Idetificador> id,ArrayList<String> operadores,ArrayList<String> cn)
	{
		switch(estado)
		{
		
			case 0:
				if(simbolo.equals("while"))
				{
					estado=1;
					break;
				}
			break;
			case 1:
				if(simbolo.equals("("))
				{
					estado=2;
					break;
				}
				else {
					throw new RuntimeException(" erro de sintaxe while "+simbolo);
				}
			case 2:
				
				for(int i=0;i<id.size();i++)
				{
					
					if(id.get(i).getNome().equals(simbolo))
					{
						
						estado=7;
						break;
					}
					else
					{
							if(simbolo.equals(")"))
							{
								estado=3;
								break;
							}
							else
							{
								throw new RuntimeException(" erro de sintaxe simbolo "+simbolo+"nao existe");
							}
					}
				}
				break;
				
			case 3:
				if(simbolo.equals("{"))
				{
					estado=4;
					break;
				}
				else {
					throw new RuntimeException(" erro o "+simbolo+"nao existe");
				}
			case 4:
				if(simbolo.equals("while"))
				{
					estado=1;
					break;
				}
				else {
					if(simbolo.equals("}"))
					{
						estado=5;
						break;
					}
					else
						throw new RuntimeException(" erro o "+simbolo+"nao existe");
				}
			
			
			case 7:
				if(operadores.contains(simbolo))
				{
					estado=8;
					break;
				}
				else {
					throw new RuntimeException(" erro de sintaxe operador "+simbolo+"nao existe");
				}
			case 8:
				for(int i=0;i<cn.size();i++)
				{
					if(cn.contains(Character.toString(simbolo.charAt(0))))
					{
						estado=9;
						break;
					}
					else {
						if(id.contains(simbolo))
						{
							estado=10;
							break;
						}
						else {
							throw new RuntimeException(" erro o "+simbolo+"nao existe");
						}
					}
				}
			break;
			case 9:
				if(simbolo.equals(")"))
				{
					estado=3;
					break;
				}
				else {
					throw new RuntimeException(" erro , e esperado ')' ");
				}
				
		
			
		}	
	}

}
