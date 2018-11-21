package sistema_supermercado;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CodigoDeVenda extends CodigoDeBarras {

	private CodigoDeArmazenamento cod_armazenamento;
	
	//não sei se precisa disso. Vamos ver
	//private static List<CodigoDeVenda> codigos_venda;
	
	private float QuantidadeDeVendasPorUnidadeEmArmazenamento;
	
	public CodigoDeVenda(int codigo, CodigoDeArmazenamento cod_armazenamento, float qtdPorArmazenamento) {
		super(codigo, CodigoDeVenda.class);
		
		this.codigoNumerico = codigo;
		this.cod_armazenamento = cod_armazenamento;
		this.QuantidadeDeVendasPorUnidadeEmArmazenamento = qtdPorArmazenamento;
		
	}
	
	/*public CodigoDeVenda(CodigoDeArmazenamento cod_armazenamento)
	{
		//to do: gerar novo inteiro único
		
	}*/

	
}
