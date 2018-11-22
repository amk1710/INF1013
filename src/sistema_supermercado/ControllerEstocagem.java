package sistema_supermercado;

import java.util.Date;
import java.util.Scanner;

public class ControllerEstocagem {

	public static PosicaoEmEstoque RegistroNovaPosicaoEmEstoque(Scanner input)
	{
		int id = IO_Auxiliary.GetInt(input, 0, Integer.MAX_VALUE, "Insira o ID da nova posição em estoque:");
		
		System.out.println("Insira o identificador de localização para a nova posição:");
		String coords = input.nextLine();
		
		System.out.println("Digite as dimensões da posição:");
		float x = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "x:");
		float y = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "y:");
		float z = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "z:");
		
		int t = IO_Auxiliary.GetInt(input, 0, 1, "Digite o tipo de armazenamento: 0 para regular, 1 para refrigerado");
		TipoDeArmazenamento tipo = TipoDeArmazenamento.values()[t];
		
		float capacidade = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "Digite a capacidade máxima da nova posição em estoque");
		
		PosicaoEmEstoque pos = new PosicaoEmEstoque(id, coords, new float[] {x, y, z}, tipo, capacidade);
		return pos;
	}
	
	public static Estocagem RegistroNovaEstocagem(Scanner input)
	{
		System.out.println("Registro de nova movimentação no estoque");
		
		PosicaoEmEstoque pos = null;
		do
		{
			int id = IO_Auxiliary.GetInt(input, -1, Integer.MAX_VALUE, "Insira o id de uma posição em estoque já registrada, ou -1 para abortar");
			if(id == -1)
			{
				System.out.println("Registro abortado");
				return null;
			}
			pos = PosicaoEmEstoque.BuscaPosicao(id);
			if(pos == null)
			{
				System.out.println("Posição não encontrada");
			}
			
			
		} while (pos == null);
		
		Mercadoria mer = null;
		do
		{
			int cod = IO_Auxiliary.GetInt(input, -1, Integer.MAX_VALUE, "Insira o código de uma mercadoria já registrada, ou -1 para abortar");
			if(cod == -1)
			{
				System.out.println("Registro abortado");
				return null;
			}
			mer = Mercadoria.BuscaMercadoriaPorCodigo(cod);
			if(mer == null)
			{
				System.out.println("Mercadoria não encontrada");
			}
		}while(mer == null);
		
		//data é a data de agora
		Date data = new Date();
		
		int t = IO_Auxiliary.GetInt(input, 0, 1, "Digite o tipo de movimentação: 0 para depósito, 1 para remoção");
		TipoDeMovimentacao tipo = TipoDeMovimentacao.values()[t];
		
		float qtd = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "Digite a quantidade de mercadoria movimentada");
		
		System.out.println("Insira o nome do funcionario responsável");
		String funcionario = input.nextLine();
		
		
		try
		{
			Estocagem es = new Estocagem(pos, mer, data, tipo, qtd, funcionario);
			System.out.println("Movimentação registrada com sucesso");
			return es;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println("Registro abortado");
			return null;
		}
		
	}
	
}
