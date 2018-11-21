package sistema_supermercado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Estocagem {

	private static List<Estocagem> estocagens;
	
	private Date data;
	private TipoDeMovimentacao tipo;
	//to-do: funcionário responsável
	
	private float QuantidadeTransferida;
	
	private PosicaoEmEstoque posicao;
	private Mercadoria mercadoria;
	
	public Estocagem(PosicaoEmEstoque posicao, Mercadoria mercadoria, Date data, TipoDeMovimentacao tipo, float qtdTransferida) 
	{
		this.posicao = posicao;
		this.mercadoria = mercadoria;
		this.data = data;
		this.tipo = tipo;
		this.QuantidadeTransferida = qtdTransferida;
		
		if(posicao == null)
		{
			throw new IllegalArgumentException("Argumento posicao deve ser uma PosicaoEmEstoque valida");
		}
		
		if(mercadoria == null) 
		{
			throw new IllegalArgumentException("Argumento mercadoria deve ser uma Mercadoria valida");
		}
		
		//quantidade deve ser maior que 0
		if(qtdTransferida <= 0)
		{
			throw new IllegalArgumentException("Quantidade transferida deve ser superior a 0.");
		}
		
		if(data == null || tipo == null)
		{
			throw new IllegalArgumentException("Argumentos nulos não são válidos");
		}
		
		//faz a movimentação
		posicao.FazerMovimentacao(mercadoria, qtdTransferida, tipo);
		
		//se tudo certo, adiciona à lista:
		if(estocagens == null)
		{
			estocagens = new ArrayList<Estocagem>();
		}
		estocagens.add(this);
		
	}

}
