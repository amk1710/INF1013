package sistema_supermercado;

import java.util.*; 


//to do: m�todos to-string(jason?)
public class Mercadoria// implements java.io.Serializable
{
	private static List<Mercadoria> catalogo;
	
	private String nome;
	private String descricao;
	private float[] dimensoes;
	private TipoDeArmazenamento tipoDeArmazenamento;
	private Categoria categoria;
	
	//s� codigo de venda -> mercadoria s� de venda
	//s� c�digo de armazenamento -> mercadoria s� de armazenamento
	//ambos, com codNum�rico identico -> mercadoria para venda e armazenamento
	private CodigoDeVenda cod_venda;
	private CodigoDeArmazenamento cod_armazenamento;
	
	
	public Mercadoria(String nome, String descricao, float[] dimensoes, TipoDeArmazenamento tipoDeArmazenamento, Categoria categoria, CodigoDeVenda codVenda, CodigoDeArmazenamento codArmazenamento)
	{
		if(nome == null || descricao == null || dimensoes == null || tipoDeArmazenamento == null || categoria == null)
		{
			throw new IllegalArgumentException("N�o s�o aceitos argumentos nulos");
		}
		this.nome = nome;
		this.descricao = descricao;
		this.dimensoes = dimensoes;
		this.tipoDeArmazenamento = tipoDeArmazenamento;
		this.categoria = categoria;
		this.cod_venda = codVenda;
		this.cod_armazenamento = codArmazenamento;
		
		
		//checa se lista j� foi inicializada
		if(catalogo == null)
		{
			catalogo = new ArrayList<Mercadoria>();
		}
		
		//se uma Mercadoria com o mesmo nome j� est� criada, erro:
		for(Mercadoria mer : catalogo)
		{
			if(mer.nome.equals(this.nome))
			{
				throw new IllegalArgumentException("Mercadoria de nome: \"" + nome + "\" j� existe");
			}
		}
		
		if(codVenda == null && codArmazenamento == null)
		{
			throw new IllegalArgumentException("Mercadoria n�o pode ser criada sem codigos de barras v�lidos");
		}
		else if(codVenda != null && codArmazenamento != null && codVenda.GetCodigoNumerico() != codArmazenamento.GetCodigoNumerico())
		{
			throw new IllegalArgumentException("Mercadoria n�o pode ser criada com codigo de venda e armazenmanto ambos v�lidos, por�m com c�digos num�ricos distintos");
		}
		
		
		//se dimens�es n�o tem lenght 3, erro
		if(this.dimensoes.length != 3)
		{
			throw new IllegalArgumentException("Dimens�o de uma mercadoria deve ser float[3]. Mercadoria:" + this.nome);
		}
		
		//se alguma dimens�o � <= 0, erro
		if(this.dimensoes[0] <= 0 || this.dimensoes[1] <= 0 || this.dimensoes[2] <= 0)
		{
			throw new IllegalArgumentException("Dimens�es de uma mercadoria devem ser positivas. Mercadoria:" + this.nome);
		}
		
		//se tudo ok, adiciona ao cat�logo
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
			//s� venda
			System.out.print("[venda] (" + cod_venda.GetCodigoNumerico() + ")");
		}
		else
		{
			//s� armazenamento
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
			System.out.println("N�o h� mercadorias registradas");
		}
		for(Mercadoria mer : Mercadoria.catalogo)
		{
			mer.PrintMercadoria();
		}
	}
	
	public static void AddToCatalogo(Mercadoria mer)
	{
		//checa se lista j� foi inicializada
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
		// se n�o achou, retorna null
		return null;
	}
}
