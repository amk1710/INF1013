package sistema_supermercado;

import java.util.*;

public class ControllerMercadorias {
	
	//input já aberto, é mantido aberto
	static public Mercadoria CadastroDeNovaMercadoria(Scanner input)
	{
		int i;
		CodigoDeVenda codVenda = null;
		CodigoDeArmazenamento codArm = null;
		
		/*CodigoDeArmazenamento cod_arm = null;
		CodigoDeVenda cod_venda = null;*/
		
		System.out.println("Iniciando cadastro de uma nova mercadoria");
		
		if(Categoria.IsEmpty())
		{
			System.out.println("Não há nenhuma categoria registrada. Cadastro de mercadoria não é possível.");
			return null;
		}
		
		i = IO_Auxiliary.GetInt(input, 1, 3, "Mercadoria será para venda(1), armazenamento(2), ou ambos(3)?");
				
		switch(i)
		{
			case 1:
			{
				System.out.println("Só Venda");
				//perguntar aqui: gerar um código para a mercadoria?
				Mercadoria mer_arm = null;
				do
				{
					int cod_arm = IO_Auxiliary.GetInt(input, -1, Integer.MAX_VALUE, "Digite o codigo da mercadoria em armazenamento a que esta mercadoria a ser vendida deve ser atribuída. \n Ou insira -1 para abortar registro");
					if(cod_arm == -1)
					{
						//aborta registro
						System.out.println("Registro abortado");
						return null;
					}
					mer_arm = Mercadoria.BuscaMercadoriaPorCodigo(cod_arm);
					if(mer_arm == null)
					{
						System.out.println("Mercadoria com codigo " + cod_arm +  " não foi encontrada");
					}
					else if(mer_arm.GetCodigoDeArmazenamento() == null || mer_arm.GetCodigoDeVenda() != null)
					{
						System.out.println("Mercadoria com codigo dado foi encontrada, mas ela não é exclusiva para armazenamento");
						mer_arm = null;
					}
				} while(mer_arm == null);
				System.out.println("Mercadoria em armazenamento encontrada:");
				mer_arm.PrintMercadoria();
				
				//codigo de armazenamento sendo atrelado ao código de venda a ser criado agora
				CodigoDeArmazenamento codAtrelado = mer_arm.GetCodigoDeArmazenamento();
				

				float qtd = IO_Auxiliary.GetFloat(input, Float.MIN_NORMAL, Float.MAX_VALUE, "Digite a quantidade de vendas a serem obtidas de uma mercadoria em armazenamento");
				
				int cod = IO_Auxiliary.GetInt(input, -1, Integer.MAX_VALUE, "Digite o codigo de barras da mercadoria para venda, ou -1 para gerar um código de venda novo");
				if(cod == -1)
				{
					//gera código de venda novo
					cod = CodigoDeBarras.GetCodigoNovo();
					System.out.println("Novo código gerado: " + cod);
				}
				
				codVenda = new CodigoDeVenda(cod, codAtrelado, 1.0f);
				codArm = null;
			

				break;
			}
			case 2:
			{	System.out.println("Só Armazenamento");
				int cod = IO_Auxiliary.GetInt(input, 0, Integer.MAX_VALUE, "Digite o codigo de barras da mercadoria");
				codArm = new CodigoDeArmazenamento(cod);
				codVenda = null;
				break;
			}
			case 3:
			{	System.out.println("Venda e Armazenamento");
				int cod = IO_Auxiliary.GetInt(input, 0, Integer.MAX_VALUE, "Digite o codigo de barras da mercadoria");
				//cria dois codigos, um de cada tipo
				//o de armazenamento tem que vir primeiro
				codArm = new CodigoDeArmazenamento(cod);
				codVenda = new CodigoDeVenda(cod, codArm, 1.0f);
				break;
			}
		}
		
		System.out.println("Digite o nome da mercadoria:");
		String nome = input.nextLine();
		
		System.out.println("Digite a descrição da mercadoria:");
		String desc = input.nextLine();
		
		System.out.println("Digite as dimensões da mercadoria:");
		float x = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "x:");
		float y = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "y:");
		float z = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "z:");
		
		int t = IO_Auxiliary.GetInt(input, 0, 1, "Digite o tipo de armazenamento: 0 para regular, 1 para refrigerado");
		TipoDeArmazenamento tipo = TipoDeArmazenamento.values()[t];
		
		System.out.println("Digite o nome da categoria da mercadoria");
		Categoria cat;
		while(true)
		{
			String nome_c = input.nextLine();
			cat = Categoria.BuscaCategoria(nome_c);
			if(cat != null)
			{
				break;
			}
			else
			{
				System.out.println("Categoria " + nome_c + "não encontrada. Digite o nome de uma categoria existente");
			}
		}
		
		try
		{
			Mercadoria mer = new Mercadoria(nome, desc, new float[] {x, y, z}, tipo, cat, codVenda, codArm);
			System.out.println("Mercadoria criada com sucesso");
			return mer;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		
		
		
	}
	
	public static Categoria CadastroDeNovaCategoria(Scanner input)
	{
		//dúvida: pode ser criada uma mercadoria que pertence a uma categoria que tem subcategorias?
		System.out.println("Iniciando cadastro de uma nova categoria");
		
		int i = IO_Auxiliary.GetInt(input, 1, 2, "Deseja que a nova categoria seja uma subcategoria? NÃO(1), SIM(2)");
		Categoria superCategoria = null;
		if(i == 2)
		{
			String super_name;
			
			do
			{
				System.out.println("Digite o nome da categoria a qual será adicionada uma sub-categoria:");
				super_name = input.nextLine();
				superCategoria = Categoria.BuscaCategoria(super_name);
				
			}while(superCategoria == null);
		}
		
		Categoria nova = null;
		do
		{
			System.out.println("Digite o nome da nova categoria:");
			String name = input.nextLine();
			try
			{
				nova = new Categoria(name, superCategoria);
			}
			catch(IllegalArgumentException e)
			{
				System.out.println(e.getMessage());
			}
			
		} while(nova == null);
		
		return nova;
				
	}
	
	public static Mercadoria IdentificarMercadoria(int codigo)
	{
		Mercadoria mer = Mercadoria.BuscaMercadoriaPorCodigo(codigo);
		if(mer == null)
		{
			System.out.println("Mercadoria não encontrada");
		}
		else
		{
			mer.PrintMercadoria();
		}
		return mer;
	}
	
}
