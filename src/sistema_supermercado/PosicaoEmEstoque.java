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
				throw new IllegalArgumentException("Posi��o em estoque com id  " + id + " j� existe");
			}
		}
		
		//possivelmente checar formata��o das coordenadas (String)
		
		//checa validade das dimens�es
		//se dimens�es n�o tem lenght 3, erro
		if(this.dimensoes.length != 3)
		{
			throw new IllegalArgumentException("Dimens�o de uma posi��o em estoque deve ser float[3]. PosicaoEmEstoque:" + id);
		}
		
		//se alguma dimens�o � <= 0, erro
		if(this.dimensoes[0] <= 0 || this.dimensoes[1] <= 0 || this.dimensoes[2] <= 0)
		{
			throw new IllegalArgumentException("Dimens�es de uma posi��o em estoque devem ser positivas. PosicaoEmEstoque:" + id);
		}
		
		//capacidade deve ser > 0
		if(capacidade <= 0)
		{
			throw new IllegalArgumentException("Capacidade de uma posi��o em estoque deve ser positiva. PosicaoEmEstoque:" + id);
		}
		
		//se tudo ok, adiciona ao armazenamento
		armazenamento.add(this);
	}
	
	public float CapacidadeLivre()
	{
		return capacidade - usoAtual;
	}
	
	//pequena fun��o auxiliar que eliminar ifs em outras classes
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
			//imposs�vel depositar essa quantidade
			throw new IllegalArgumentException("PosicaoEmEstoque " + this.ID_Posicao + " n�o possue" + quantidade + "de capacidade livre");
		}
		else if(mer.GetTipoDeArmazenamento() != this.tipoDeArmazenamento)
		{
			throw new IllegalArgumentException("Tipo de armazenamento da PosicaoEmEstoque("  +this.tipoDeArmazenamento + ") � incompat�vel com o da mercadoria(" + mer.GetTipoDeArmazenamento() + ")");
		}
		else if(mer_dim[0] > dimensoes[0] || mer_dim[1] > dimensoes[1] || mer_dim[2] > dimensoes[2])
		{
			throw new IllegalArgumentException("Dimens�es da mercadoria excedem as suportadas pela posi��o em estoque");
		}
		
		// se t� tudo ok, deposita
		else
		//impl�cito no c�digo abaixo est� uma resposta afirmativa � pergunta: � poss�vel armazenar mercadorias diferentes numa mesma posi��o em estoque?
		{
			//percorre lista pra ver se alguma mercadoria dessa j� est� depositada
			for(MercadoriaArmazenada merAr : estocados)
			{
				if(merAr.mercadoria.GetName() == mer.GetName())
				{
					//adiciona mais da mercadoria que j� tem:
					merAr.quantidadeArmazenada += quantidade;
					//adiciona ao uso atual
					usoAtual += quantidade;
					return;
				}
			}
			//se n�o achou, adiciona a nova mercadoria:
			estocados.add(new MercadoriaArmazenada(mer, quantidade));
			usoAtual += quantidade;
			return;
			
		}
		
	}
	
	public void Remover(Mercadoria mer, float quantidade)
	{
		//verifica se a posi��o possui no m�nimo a tal quantidade da tal mercadoria
		//checa todas mercadorias nessa posi��o,
		for(MercadoriaArmazenada merAr : estocados)
		{
			//se achar esta mercadoria nesta posi��o,
			if(merAr.mercadoria.GetName() == mer.GetName())
			{
				//se tiver armazenada quantidade suficiente, remove
				if(merAr.quantidadeArmazenada >= quantidade)
				{
					merAr.quantidadeArmazenada -= quantidade;
					usoAtual -= quantidade;
					return;
				}
				//sen�o, erro
				else
				{
					throw new IllegalArgumentException("A Posi��o em Estoque n�o possui esta quantidade(" + quantidade + ") desta mercadoria(" + mer.GetName() + ")");
				}
			}
		}
		//se n�o achar a mercadoria, erro
		throw new IllegalArgumentException("N�o h� mercadorias " + mer.GetName() + " nesta posi��o em estoque");
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

