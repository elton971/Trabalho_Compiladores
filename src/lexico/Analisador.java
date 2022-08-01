package lexico;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;

import sintactico.AnalisadorSintatico;
public class Analisador {

	
	private char [] carracteres;
	private int estado;
	private int posicao;

	//arraylist para armazenar todos os Token
	ArrayList <Token> tokens = new ArrayList<Token>();

	//arraylist para armzaenas as classifcacoes e os simbolos
	Simbolos[] simbolos ;



	//
	private ArrayList<String> palavrasReservadas = new ArrayList<String>(); // arrayList palavras reservadas
    private ArrayList<String> tipos = new ArrayList<String>();// arrayList tipos
    private ArrayList<String> operadores = new ArrayList<String>();// arrayList operadores
    private ArrayList<String> delimitadores = new ArrayList<String>();// arrayList delimitadores
	private ArrayList<String> constantesNumericas = new ArrayList<String>();// arrayList constantes NUMERICOS

	//vai armazenar os Idetificadores
	private ArrayList<Idetificador> id=new ArrayList<Idetificador>();

	private String tipoPrimitivo,valor,memoria;//armazena o tipo da variavel e armzena o valor da variavel
	
	private AnalisadorSintatico a;
	
	public Analisador() {
		preecherArrayTipo();
        preecherArrayOperador();
        preecherArrayDelimitador();
        preecherArrayPalavrasReservadas();
        preecherConstantesNumericas();
        lerFicheiro("programa.txt");
        Token token=null;
		a=new AnalisadorSintatico();
		do{
			token=proximoToken();
			
			
			
		}while(token!=null);
		classificar();
		a.enviarSimbolos(tokens, id);
		
		
		
			
	}

	//metodo para ler o filheiro
	public void  lerFicheiro(String nomeFicheiro) {
		
		try{
			String conteudo;//ira armazenar o conteudo do ficheiro
			conteudo = new String(Files.readAllBytes(Paths.get(nomeFicheiro)),StandardCharsets.UTF_8);
		
			System.out.println("Detalhes do ficheiro:----------------------------------------------------");
			System.out.println(conteudo);

			carracteres = conteudo.toCharArray();
			
			
			posicao=0;
		}catch(IOException e){
			System.out.println("Erro ao ler o ficheiro");
			e.printStackTrace();
		}
	}

	//metodo para validar o caracter digito
	private boolean isDigito(char c)	{
		return c >= '0' && c <= '9';
	}

	//metodo para validar o caracter letra
	private boolean isLetra(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}
	
	//metodo para validar o caracter operador
	private boolean isOperador(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '=' || c=='!' || c=='<' || c=='>' || c=='&' || c=='|';
	}
	
	//metodo para validar o caracter delimitador
	private boolean isDelimitador(char c) {
		return c == ' ' || c == '\n' || c == '\t' || c == '\r' ;
	}

	private boolean isDelimitadoAceite( char c)
	{
		return c==';' || c=='(' || c==')' || c=='{' || c=='}' || c==',' || c=='[' || c==']';	
	}


	//metodo para avancar o estado atual
	private char proximoChar() {
		
		if(posicao<carracteres.length)
		{
			
			return carracteres[posicao]; 
			
		}

		return  ' ';
		
	}
	
	//metodo para retormar o estado atual
	private void retrocederChar() {

		posicao--;
	}

	//metodo para verificar se estamos no fim do arquivo
	private boolean fimAcquivo() {
		
		return posicao == carracteres.length;
	}

	private boolean existente(String c)
	{
		for(int i=0;i<id.size();i++)
		{
			if(id.get(i).getNome().equals(c))
			{
				return false;
			}
	
		}
		return true;
	}

	
	public Token proximoToken()
	{
		char c;
		String lexema = "";
		Token token;
		if(fimAcquivo())
		{	
			return null;
		}
		estado=0;
		while(true)
		{
			 
			c=proximoChar();
			// escolha de um estado dependendo do character
			// cada destado ira criar um automato de estados
			switch(estado)
			{
				case 0:
					if(isLetra(c))
					{
						estado=1;//vai para o estdo 1
						lexema+=c;

						break;
					}
					else 
					{
						if(isDigito(c))
						{
							estado=3;//vai para o estado 3
							lexema+=c;
							break;
						}
						else{
							if(isDelimitador(c))
							{ 
								
								estado=0;//vai para o estado 0
							}
							else{
								if(isOperador(c))
								{
									estado=5;//vai para o estado 5
									lexema+=c;
									break;
								}
								else{
									if(isDelimitadoAceite(c))
									{
										estado=8;//vai para o estado 8
										lexema+=c;
										break;
									}
									else
										throw new RuntimeException(" caracter invalido");
								}
							}
						}
					}
				break;
				case 1:
					if(isLetra(c) || isDigito(c))
					{
						estado=1;//vai para o estado 1
						lexema+=c;
						break;
					
					}
					else
					{
						if(isDelimitador(c) || isOperador(c))
						{
							estado=2;//vai para o estado 2	
						}
						else 
						{
							if(isDelimitadoAceite(c))
							{
								estado=2;//vai para o estado 8
								
							}
							else
								throw new RuntimeException(" caracter invalido");
						}
					}
				break;
				case 2:
					retrocederChar();
					token=new Token();
					token.setLexema(lexema);
					tokens.add(token);
					tokens.trimToSize();
					return token;
				case 3:
					if(isDigito(c))
					{
						estado=3;//vai para o estado 3
						lexema+=c;
						break;
					}
					else
					{
						if(!isLetra(c))
						{
							estado=4;//vai para o estado 4
							
						}
						else{
							throw new RuntimeException(" Numero invalido "+lexema+c);
						}
					}
				break;
				case 4:
					retrocederChar();
					token=new Token();
					token.setLexema(lexema);
					tokens.add(token);
					tokens.trimToSize();
					return token;
				
				case 5:
					if(isOperador(c))
					{
						estado=7;//vai para o estado 5
						lexema+=c;
					}
					else
					{
						if(isDigito(c))
						{
							estado=6;//vai para o estado 6
						}
						else
						{ 
							if(isDelimitador(c))
							{
								estado=7;//vai para o estado 7
							}
							else
							{
								throw new RuntimeException(" caracter invalido");
							}
						}
					}
				break;
				case 6:
					retrocederChar();	
					token=new Token();
					token.setLexema(lexema);
					tokens.add(token);
					tokens.trimToSize();
					return token;
				case 7:	
					token=new Token();
					token.setLexema(lexema);
					tokens.add(token);
					tokens.trimToSize();
					return token;

				case 8:
					token=new Token();
					token.setLexema(lexema);
					tokens.add(token);
					tokens.trimToSize();
					return token;
			}
			posicao++; 
		}
		
	}
	

	//metodo para  classificar e criar a tabela de simbolos

	public void classificar()
	{
		Token token;
		
		simbolos= new Simbolos[tokens.size()];
		for(int i=0;i<tokens.size();i++)
		{
			Simbolos s=new Simbolos();
			token =tokens.get(i);
			
			
			if(tipos.contains(token.getLexema()))
			{
				
				s.setNome(token.getLexema());
				s.setClassificacao("Tipo primitivo");
				tipoPrimitivo=token.getLexema();
				memoria="Primitivo";
				simbolos[i]=s;

				
			}
			else{
				if(operadores.contains(token.getLexema()))
				{
					
					
					s.setNome(token.getLexema());
					s.setClassificacao("Operador");
					simbolos[i]=s;
					
				}
				else{
					if(delimitadores.contains(token.getLexema()))
					{
						
						s.setNome(token.getLexema());
						s.setClassificacao("Delimitadores");
						simbolos[i]=s;
						
					
					}
					else{
						if(palavrasReservadas.contains(token.getLexema()))
						{
							
							s.setNome(token.getLexema());
							s.setClassificacao("Palavra reservada");
							simbolos[i]=s;
						
						}
						else
						{
							
							if(constantesNumericas.contains(Character.toString(token.getLexema().charAt(0))))
							{
								s.setNome(token.getLexema());
								s.setClassificacao("Constante numerica"	);
								simbolos[i]=s;
								
							}
							else {
								s.setNome(token.getLexema());
								s.setClassificacao("Idetificadores"	);
								if(Character.isDigit(tokens.get(i+2).getLexema().charAt(0)))
								{
									valor =tokens.get(i+2).getLexema();
								}
								else{
									valor="---";
								}
								
								simbolos[i]=s;
								Idetificador ids=new Idetificador();

								if(existente(token.getLexema()))
								{
									
									ids.setNome(token.getLexema());
									ids.setTipo(tipoPrimitivo);
									ids.setMemoria(memoria);
									ids.setValor(valor);
									id.add(ids);
									
								}
								
								
								//ids.setMemoria(memoria);

							}
						}
						
					}
					
				}
			}
		}
		verTabelaDeSimbolos();
		
	}


	public void preecherArrayTipo()
    {
        tipos.add("float");
        tipos.add("double");
    }

    public void preecherArrayOperador()
    {
        operadores.add("+");
        operadores.add("-");
        operadores.add("*");
        operadores.add("/");
        operadores.add("%");
        operadores.add("=");
        operadores.add("==");
        operadores.add("!=");
        operadores.add("<");
        operadores.add("<=");
        operadores.add(">");
        operadores.add(">=");
        operadores.add("&&");
        operadores.add("||");
        operadores.add("!");
        operadores.add("?");
       
    }

    public void preecherArrayDelimitador()
    {
        delimitadores.add("{");
        delimitadores.add("}");
        delimitadores.add("(");
        delimitadores.add(")");
        delimitadores.add("[");
        delimitadores.add("]");
        delimitadores.add(";");
        delimitadores.add(",");
    }

	private void preecherConstantesNumericas()
	{
		constantesNumericas.add("0");
		constantesNumericas.add("1");
		constantesNumericas.add("2");
		constantesNumericas.add("3");
		constantesNumericas.add("4");
		constantesNumericas.add("5");
		constantesNumericas.add("6");
		constantesNumericas.add("7");
		constantesNumericas.add("8");
		constantesNumericas.add("9");
	}

    public void preecherArrayPalavrasReservadas()
    {
        palavrasReservadas.add("while");
    }

	public void  verTabelaDeSimbolos()
	{
		
		System.out.println("==========================Tabela de Simbolos=============================");
		System.out.println("|             Nome                |             Classificacao           |");
		System.out.println("==========================================================================");
		for(int i=0;i<simbolos.length;i++)
		{
			
			System.out.printf("|%33s|%37s|\n",simbolos[i].getNome(),simbolos[i].getClassificacao());
			
		}
		System.out.println("==========================================================================");

		System.out.println();
		System.out.println("===============================Tabela de Idetificadores=============================");
		System.out.println("|         Nome        |         Tipo        |         Valor      |     Memoria     |");
		System.out.println("====================================================================================");
		for(int i=0;i<id.size();i++)
		{
			
			System.out.printf("|%21s|%21s|%20s|%17s|\n",id.get(i).getNome(),id.get(i).getTipo(),id.get(i).getValor(),id.get(i).getMemoria());
			
		}
		System.out.println("===================================================================================|");
	}
}
