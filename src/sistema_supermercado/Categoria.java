package sistema_supermercado;

import java.util.*;

import com.google.gson.annotations.Expose;


public class Categoria {

	private static List<Categoria> categorias;
	
	private String nome;
	
	//a super-categoria à qual esta categoria está atrelada
	private Categoria super_categoria;
	
	//construtor para sub-categoria
	public Categoria(String nome, Categoria super_categoria)
	{
		this.nome = nome;
		this.super_categoria = super_categoria;
				
		//checa se lista já foi inicializada
		if(categorias == null)
		{
			categorias = new ArrayList<Categoria>();
		}
		
		//se uma categoria com o mesmo nome já está criada, erro:
		for(Categoria cat : categorias)
		{
			if(cat.nome.equals(this.nome))
			{
				throw new IllegalArgumentException("Categoria de nome: \"" + nome + "\" já existe");
			}
		}
		
		categorias.add(this);
	}
	
	//construtor sem subcategoria
	public Categoria(String nome)
	{
		this(nome, null);
	}
	
	//buscar categoria por nome
	public static Categoria BuscaCategoria(String nome)
	{
		Categoria ret = null;
		if(categorias == null)
		{
			return null;
		}
		for(Categoria cat : categorias)
		{
			if(cat.nome.equals(nome))
			{
				ret = cat;
				break;
			}
		}
		//se não achou, retorna null
		return ret;
	}
	
	
	public static boolean IsEmpty() {
		//retorna true se não há categorias registradas
		if(categorias == null || categorias.size() == 0)
		{
			return true;
		}
		else return false;
		
	}
	
	public void PrintCategoria()
	{
		if(this.super_categoria != null)
		{
			super_categoria.PrintCategoria();
			System.out.print("-->");
		}
		System.out.print(this.nome);
		
	}
	
	//imprime todas as categorias
	public static void PrintTodasCategorias()
	{
		for(Categoria cat : categorias)
		{
			cat.PrintCategoria();
			System.out.println();
		}
	}
	
	
	
	//getters e setters simples
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public static List<Categoria> GetCategorias()
	{
		if(categorias == null)
		{
			categorias = new ArrayList<Categoria>();
		}
		return categorias;
	}
	
	public static void AddToCategorias(Categoria cat)
	{
		if(categorias == null)
		{
			categorias = new ArrayList<Categoria>();
		}
		categorias.add(cat);
	}
	
	public static void Clear()
	{
		if(categorias == null)
		{
			categorias = new ArrayList<Categoria>();
		}
		categorias.clear();
	}
	
}
