package sistema_supermercado;

import java.util.Date;
import java.util.Scanner;

public class ControllerEstocagem {

	public static PosicaoEmEstoque RegistroNovaPosicaoEmEstoque(Scanner input)
	{
		int id = IO_Auxiliary.GetInt(input, 0, Integer.MAX_VALUE, "Insira o ID da nova posi��o em estoque:");
		
		System.out.println("Insira o identificador de localiza��o para a nova posi��o:");
		String coords = input.nextLine();
		
		System.out.println("Digite as dimens�es da posi��o:");
		float x = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "x:");
		float y = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "y:");
		float z = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "z:");
		
		int t = IO_Auxiliary.GetInt(input, 0, 1, "Digite o tipo de armazenamento: 0 para regular, 1 para refrigerado");
		TipoDeArmazenamento tipo = TipoDeArmazenamento.values()[t];
		
		float capacidade = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "Digite a capacidade m�xima da nova posi��o em estoque");
		
		PosicaoEmEstoque pos = new PosicaoEmEstoque(id, coords, new float[] {x, y, z}, tipo, capacidade);
		return pos;
	}
	
	public static Estocagem RegistroNovaEstocagem(Scanner input)
	{
		System.out.println("Registro de nova movimenta��o no estoque");
		
		PosicaoEmEstoque pos = null;
		do
		{
			int id = IO_Auxiliary.GetInt(input, -1, Integer.MAX_VALUE, "Insira o id de uma posi��o em estoque j� registrada, ou -1 para abortar");
			if(id == -1)
			{
				System.out.println("Registro abortado");
				return null;
			}
			pos = PosicaoEmEstoque.BuscaPosicao(id);
			if(pos == null)
			{
				System.out.println("Posi��o n�o encontrada");
			}
			
			
		} while (pos == null);
		
		Mercadoria mer = null;
		do
		{
			int cod = IO_Auxiliary.GetInt(input, -1, Integer.MAX_VALUE, "Insira o c�digo de uma mercadoria j� registrada, ou -1 para abortar");
			if(cod == -1)
			{
				System.out.println("Registro abortado");
				return null;
			}
			mer = Mercadoria.BuscaMercadoriaPorCodigo(cod);
			if(mer == null)
			{
				System.out.println("Mercadoria n�o encontrada");
			}
		}while(mer == null);
		
		//data � a data de agora
		Date data = new Date();
		
		int t = IO_Auxiliary.GetInt(input, 0, 1, "Digite o tipo de movimenta��o: 0 para dep�sito, 1 para remo��o");
		TipoDeMovimentacao tipo = TipoDeMovimentacao.values()[t];
		
		float qtd = IO_Auxiliary.GetFloat(input, Float.MIN_VALUE, Float.MAX_VALUE, "Digite a quantidade de mercadoria movimentada");
		
		System.out.println("Insira o nome do funcionario respons�vel");
		String funcionario = input.nextLine();
		
		
		try
		{
			Estocagem es = new Estocagem(pos, mer, data, tipo, qtd, funcionario);
			System.out.println("Movimenta��o registrada com sucesso");
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
