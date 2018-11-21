package sistema_supermercado;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PosicaoEmEstoque {

	private static List<PosicaoEmEstoque> armazenamento;
	
	private int ID_Posicao;
	private String coordenadas;
	private float[] dimensoes;
	private TipoDeArmazenamento tipoDeArmazenamento;
	private float capacidade;
	private float usoAtual;
	private List<MercadoriaArmazenada> estocados;
	
	
	public PosicaoEmEstoque(int id, String coords, float[] dim, TipoDeArmazenamento tipo, float capacidade) 
	{
		this.ID_Posicao = id;
		this.coordenadas = coords;
		this.dimensoes = dim;
		this.tipoDeArmazenamento = tipo;
		this.capacidade = capacidade;
		this.usoAtual = 0.0f;
		this.estocados = new LinkedList<MercadoriaArmazenada>();
		
		if(armazenamento == null)
		{
			armazenamento = new ArrayList<PosicaoEmEstoque>();
		}
		
		//checa unicidade da id
		for(PosicaoEmEstoque pos : armazenamento)
		{
			if(pos.ID_Posicao == id)
			{
				throw new IllegalArgumentException("Posição em estoque com id  " + id + " já existe");
			}
		}
		
		//possivelmente checar formatação das coordenadas (String)
		
		//checa validade das dimensões
		//se dimensões não tem lenght 3, erro
		if(this.dimensoes.length != 3)
		{
			throw new IllegalArgumentException("Dimensão de uma posição em estoque deve ser float[3]. PosicaoEmEstoque:" + id);
		}
		
		//se alguma dimensão é <= 0, erro
		if(this.dimensoes[0] <= 0 || this.dimensoes[1] <= 0 || this.dimensoes[2] <= 0)
		{
			throw new IllegalArgumentException("Dimensões de uma posição em estoque devem ser positivas. PosicaoEmEstoque:" + id);
		}
		
		//capacidade deve ser > 0
		if(capacidade <= 0)
		{
			throw new IllegalArgumentException("Capacidade de uma posição em estoque deve ser positiva. PosicaoEmEstoque:" + id);
		}
		
		//se tudo ok, adiciona ao armazenamento
		armazenamento.add(this);
	}
	
	public float CapacidadeLivre()
	{
		return capacidade - usoAtual;
	}
	
	//pequena função auxiliar que eliminar ifs em outras classes
	public void FazerMovimentacao(Mercadoria mer, float quantidade, TipoDeMovimentacao tipo)
	{
		switch(tipo)
		{
			case DEPOSITO:
				Depositar(mer, quantidade);
				break;
			case REMOCAO:
				Remover(mer, quantidade);
		}
	}
	
	public void Depositar(Mercadoria mer, float quantidade)
	{
		float[] mer_dim = mer.GetDimensoes();
		
		if(quantidade > CapacidadeLivre())
		{
			//impossível depositar essa quantidade
			throw new IllegalArgumentException("PosicaoEmEstoque " + this.ID_Posicao + " não possue" + quantidade + "de capacidade livre");
		}
		else if(mer.GetTipoDeArmazenamento() != this.tipoDeArmazenamento)
		{
			throw new IllegalArgumentException("Tipo de armazenamento da PosicaoEmEstoque("  +this.tipoDeArmazenamento + ") é incompatível com o da mercadoria(" + mer.GetTipoDeArmazenamento() + ")");
		}
		else if(mer_dim[0] > dimensoes[0] || mer_dim[1] > dimensoes[1] || mer_dim[2] > dimensoes[2])
		{
			throw new IllegalArgumentException("Dimensões da mercadoria excedem as suportadas pela posição em estoque");
		}
		
		// se tá tudo ok, deposita
		else
		//implícito no código abaixo está uma resposta afirmativa à pergunta: é possível armazenar mercadorias diferentes numa mesma posição em estoque?
		{
			//percorre lista pra ver se alguma mercadoria dessa já está depositada
			for(MercadoriaArmazenada merAr : estocados)
			{
				if(merAr.mercadoria.GetName() == mer.GetName())
				{
					//adiciona mais da mercadoria que já tem:
					merAr.quantidadeArmazenada += quantidade;
					//adiciona ao uso atual
					usoAtual += quantidade;
					return;
				}
			}
			//se não achou, adiciona a nova mercadoria:
			estocados.add(new MercadoriaArmazenada(mer, quantidade));
			usoAtual += quantidade;
			return;
			
		}
		
	}
	
	public void Remover(Mercadoria mer, float quantidade)
	{
		//verifica se a posição possui no mínimo a tal quantidade da tal mercadoria
		//checa todas mercadorias nessa posição,
		for(MercadoriaArmazenada merAr : estocados)
		{
			//se achar esta mercadoria nesta posição,
			if(merAr.mercadoria.GetName() == mer.GetName())
			{
				//se tiver armazenada quantidade suficiente, remove
				if(merAr.quantidadeArmazenada >= quantidade)
				{
					merAr.quantidadeArmazenada -= quantidade;
					usoAtual -= quantidade;
					return;
				}
				//senão, erro
				else
				{
					throw new IllegalArgumentException("A Posição em Estoque não possui esta quantidade(" + quantidade + ") desta mercadoria(" + mer.GetName() + ")");
				}
			}
		}
		//se não achar a mercadoria, erro
		throw new IllegalArgumentException("Não há mercadorias " + mer.GetName() + " nesta posição em estoque");
	}

	public static List<PosicaoEmEstoque> GetArmazenamento()
	{
		if(armazenamento == null)
		{
			armazenamento = new ArrayList<PosicaoEmEstoque>();
		}
		return armazenamento;
	}
	
	public static void AddToArmazenamento(PosicaoEmEstoque pos)
	{
		if(armazenamento == null)
		{
			armazenamento = new ArrayList<PosicaoEmEstoque>();
		}
		armazenamento.add(pos);
	}
	
	public static void Clear()
	{
		if(armazenamento == null)
		{
			armazenamento = new ArrayList<PosicaoEmEstoque>();
		}
		armazenamento.clear();
	}
	
}

//todo: struct armazenado que relaciona mercadoria com qtd armazenada
class MercadoriaArmazenada
{
	public Mercadoria mercadoria;
	public float quantidadeArmazenada;
	
	public MercadoriaArmazenada(Mercadoria mer, float quantidade)
	{
		this.mercadoria = mer;
		quantidadeArmazenada = quantidade;
	}
	
}

