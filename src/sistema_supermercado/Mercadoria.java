package sistema_supermercado;

import java.util.*; 


//to do: métodos to-string(jason?)
public class Mercadoria// implements java.io.Serializable
{
	private static List<Mercadoria> catalogo;
	
	private String nome;
	private String descricao;
	private float[] dimensoes;
	private TipoDeArmazenamento tipoDeArmazenamento;
	private Categoria categoria;
	
	//só codigo de venda -> mercadoria só de venda
	//só código de armazenamento -> mercadoria só de armazenamento
	//ambos, com codNumérico identico -> mercadoria para venda e armazenamento
	private CodigoDeVenda cod_venda;
	private CodigoDeArmazenamento cod_armazenamento;
	
	
	public Mercadoria(String nome, String descricao, float[] dimensoes, TipoDeArmazenamento tipoDeArmazenamento, Categoria categoria, CodigoDeVenda codVenda, CodigoDeArmazenamento codArmazenamento)
	{
		if(nome == null || descricao == null || dimensoes == null || tipoDeArmazenamento == null || categoria == null)
		{
			throw new IllegalArgumentException("Não são aceitos argumentos nulos");
		}
		this.nome = nome;
		this.descricao = descricao;
		this.dimensoes = dimensoes;
		this.tipoDeArmazenamento = tipoDeArmazenamento;
		this.categoria = categoria;
		this.cod_venda = codVenda;
		this.cod_armazenamento = codArmazenamento;
		
		
		//checa se lista já foi inicializada
		if(catalogo == null)
		{
			catalogo = new ArrayList<Mercadoria>();
		}
		
		//se uma Mercadoria com o mesmo nome já está criada, erro:
		for(Mercadoria mer : catalogo)
		{
			if(mer.nome.equals(this.nome))
			{
				throw new IllegalArgumentException("Mercadoria de nome: \"" + nome + "\" já existe");
			}
		}
		
		if(codVenda == null && codArmazenamento == null)
		{
			throw new IllegalArgumentException("Mercadoria não pode ser criada sem codigos de barras válidos");
		}
		else if(codVenda != null && codArmazenamento != null && codVenda.GetCodigoNumerico() != codArmazenamento.GetCodigoNumerico())
		{
			throw new IllegalArgumentException("Mercadoria não pode ser criada com codigo de venda e armazenmanto ambos válidos, porém com códigos numéricos distintos");
		}
		
		
		//se dimensões não tem lenght 3, erro
		if(this.dimensoes.length != 3)
		{
			throw new IllegalArgumentException("Dimensão de uma mercadoria deve ser float[3]. Mercadoria:" + this.nome);
		}
		
		//se alguma dimensão é <= 0, erro
		if(this.dimensoes[0] <= 0 || this.dimensoes[1] <= 0 || this.dimensoes[2] <= 0)
		{
			throw new IllegalArgumentException("Dimensões de uma mercadoria devem ser positivas. Mercadoria:" + this.nome);
		}
		
		//se tudo ok, adiciona ao catálogo
		catalogo.add(this);
	}
	
	//getters e setters:
	
	public static List<Mercadoria> GetCatalogo()
	{
		if(catalogo == null)
		{
			catalogo = new ArrayList<Mercadoria>();
		}
		return catalogo;
	}
	
	public CodigoDeVenda GetCodigoDeVenda()
	{
		return cod_venda;
	}
	
	public CodigoDeArmazenamento GetCodigoDeArmazenamento()
	{
		return cod_armazenamento;
	}
	
	public TipoDeArmazenamento GetTipoDeArmazenamento()
	{
		return tipoDeArmazenamento;
	}
	
	public float[] GetDimensoes()
	{
		return dimensoes;
	}
	
	public String GetName()
	{
		return nome;
	}
	
	//end: getters e setters
	
	public void PrintMercadoria()
	{
		
		if(cod_venda != null && cod_armazenamento != null)
		{
			//dos dois:
			System.out.print("[venda e armazenamento] (" + cod_venda.GetCodigoNumerico() + ")");
		}
		else if(cod_venda != null)
		{
			//só venda
			System.out.print("[venda] (" + cod_venda.GetCodigoNumerico() + ")");
		}
		else
		{
			//só armazenamento
			System.out.print("[armazenamento] (" + cod_armazenamento.GetCodigoNumerico() + ")");
			
		}
		System.out.println(nome + ": " + descricao);
		
	}
	
	public static void PrintAllMercadorias()
	{
		if(catalogo == null)
		{
			catalogo = new ArrayList<Mercadoria>();
		}
		
		if(catalogo.size() == 0)
		{
			System.out.println("Não há mercadorias registradas");
		}
		for(Mercadoria mer : Mercadoria.catalogo)
		{
			mer.PrintMercadoria();
		}
	}
	
	public static void AddToCatalogo(Mercadoria mer)
	{
		//checa se lista já foi inicializada
		if(catalogo == null)
		{
			catalogo = new ArrayList<Mercadoria>();
		}
		if(!catalogo.contains(mer))
		{
			catalogo.add(mer);
		}
		
	}
	
	public static void Clear()
	{
		if(catalogo == null)
		{
			catalogo = new ArrayList<Mercadoria>();
		}
		catalogo.clear();
	}
	
	public static Mercadoria BuscaMercadoriaPorCodigo(int codigo)
	{
		if(catalogo != null)
		{
			for(Mercadoria mer : catalogo)
			{
				if((mer.cod_venda != null && mer.cod_venda.GetCodigoNumerico() == codigo) || (mer.cod_armazenamento != null && mer.cod_armazenamento.GetCodigoNumerico() == codigo) )
				{
					return mer;
				}
			}
		}
		// se não achou, retorna null
		return null;
	}
}
