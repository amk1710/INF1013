package sistema_supermercado;

import java.util.Date;
import java.util.Scanner;

public class ControllerEstocagem {

	public static PosicaoEmEstoque RegistroNovaPosicaoEmEstoque(Scanner input)
	{
		int id = IO_Auxiliary.GetInt(input, 0, Integer.MAX_VALUE, "Insira o ID da nova posição em estoque:");
		
		System.out.println("Insira o identificador de localização para a nova posição:");
		String coords = input.nextLine();
		
		System.out.println("Digite as dimensões da mercadoria:");
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
		//Estocagem(PosicaoEmEstoque posicao, Mercadoria mercadoria, Date data, TipoDeMovimentacao tipo, float qtdTransferida)
		return null;
	}
	
	
	public static void TesteLegal()
	{
		System.out.println("Teste legal");
		Categoria cat = new Categoria("cat1");
		Mercadoria mer = new Mercadoria("asdasd", "asdasdasdasd", new float[] {1.0f, 1.0f, 1.0f}, TipoDeArmazenamento.REGULAR, cat, null, new CodigoDeArmazenamento(1001));
		Mercadoria mer2 = new Mercadoria("hdfghdfghdfh", "asdasdasdasd", new float[] {1.0f, 1.0f, 1.0f}, TipoDeArmazenamento.REGULAR, cat, null, new CodigoDeArmazenamento(1000));
		
		PosicaoEmEstoque pos = new PosicaoEmEstoque(12, "dasdasd", new float[] {1.0f, 1.0f, 1.0f}, TipoDeArmazenamento.REGULAR, 100);
		
		try {
			System.out.println(pos.CapacidadeLivre());
			pos.Depositar(mer, 30);
			pos.Depositar(mer, 30);
			pos.Depositar(mer, 30);
			pos.Remover(mer2, 40);
			
			System.out.println(pos.CapacidadeLivre());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
