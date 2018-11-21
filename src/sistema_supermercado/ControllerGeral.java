package sistema_supermercado;

import java.io.*;
import java.util.Scanner;

import com.google.gson.*;


public class ControllerGeral {

	public static void main(String[] args) {

		
		//abre scanner
		Scanner input = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("O que fazer?");
			System.out.print("(1): Cadastrar nova categoria, ");
			System.out.print("(2): Cadastrar nova mercadoria, ");
			System.out.print("(3): Cadastrar nova posição em estoque, ");	
			System.out.print("(4): Salvar estado do sistema, ");
			System.out.print("(5): Carregar estado, ");
			System.out.println("(6): Printa catálogo de mercadorias registradas.");
			
			int i = IO_Auxiliary.GetInt(input, 1, 6, "");
			
			switch(i)
			{
				case 1:
					ControllerMercadorias.CadastroDeNovaCategoria(input);
					break;
				case 2:
					ControllerMercadorias.CadastroDeNovaMercadoria(input);
					break;
				case 3:
					//ControllerEstocagem.RegistroNovaPosicaoEmEstoque(input);
					ControllerEstocagem.TesteLegal();
					break;
				
				case 4:
				{
					System.out.println("Qual o nome do arquivo onde deve ser salvo o estado atual?");
					String path = input.nextLine();
					try {
						SaveState(path);
					}
					catch(IOException e)
					{
						System.out.println("Erro de abertura / escrita no arquivo");
					}
					break;
				}
				case 5:
				{	
					System.out.println("Qual o nome do arquivo de onde deve ser carregado o estado?");
				
					String path = input.nextLine();
					try {
						LoadState(path);
					}
					catch(IOException e)
					{
						System.out.println("Erro de abertura / leitura do arquivo");
					}
					
					break;
				}
				case 6:
					Mercadoria.PrintAllMercadorias();
					break;
					
				default:
					System.out.println("Opção inválida");
					break;
					
					
			}
			
		}	
				

	}
	
	public static void SaveState(String path) throws IOException
	{
		/*GsonBuilder gsonBuilder = new GsonBuilder();
		new GraphAdapterBuilder()
		    .addType(Roshambo.class)
		    .registerOn(gsonBuilder);
		Gson gson = gsonBuilder.create();*/
		
		Gson gson = new GsonBuilder().create();
		Writer writer = null;
		try
		{
			writer = new FileWriter(path);
			//grava quantidades de tudo:
			Quantities qtds = new Quantities(Mercadoria.GetCatalogo().size(), PosicaoEmEstoque.GetArmazenamento().size(), Categoria.GetCategorias().size());
			gson.toJson(qtds, writer);
			writer.write("\n");
			
			//grava mercadorias
			for(Mercadoria mer : Mercadoria.GetCatalogo())
			{
				gson.toJson(mer, writer);
				writer.write("\n");
			}
			System.out.println(path + " gravado com sucesso!");
			
			//grava posições em estoque
			for(PosicaoEmEstoque pos : PosicaoEmEstoque.GetArmazenamento())
			{
				gson.toJson(pos, writer);
				writer.write("\n");
			}
			
			//grava categorias
			for(Categoria cat : Categoria.GetCategorias())
			{
				gson.toJson(cat, writer);
				writer.write("\n");
			}
		}
		finally
		{
			if(writer != null)
			{
				writer.close();
			}
		}
		
	}
	
	//mock load state
	public static void LoadState(String path) throws IOException
	{
		
		BufferedReader buffReader = new BufferedReader(new FileReader(path));
		
		Gson gson = new Gson();
		//get quantidade de mercadorias
		Object json = gson.fromJson(buffReader.readLine(), Quantities.class);
		Quantities qtds = (Quantities) json;
		
		//clean current state
		Mercadoria.Clear();
		PosicaoEmEstoque.Clear();
		Categoria.Clear();
		
		for(int i = 0; i < qtds.QtdMercadorias; i++)
		{
			Object jmer = gson.fromJson(buffReader.readLine(), Mercadoria.class);
			Mercadoria.AddToCatalogo((Mercadoria) jmer);
		}
		
		for(int i = 0; i < qtds.QtdPosicoes; i++)
		{
			Object jpos = gson.fromJson(buffReader.readLine(), PosicaoEmEstoque.class);
			PosicaoEmEstoque.AddToArmazenamento((PosicaoEmEstoque)jpos);
		}
		
		for(int i = 0; i < qtds.QtdCategorias; i++)
		{
			Object jcat = gson.fromJson(buffReader.readLine(), Categoria.class);
			Categoria.AddToCategorias((Categoria)jcat);
		}
		
		buffReader.close();
		
		//System.out.println(json.getClass());
		//System.out.println(json.toString());
		
		System.out.println(path + " lido com sucesso!");
		
	}

}

class Quantities
{
	public int QtdMercadorias;
	public int QtdPosicoes;
	public int QtdCategorias;
	
	Quantities(int qtdMercadorias, int qtdPosicoes, int qtdCategorias)
	{
		this.QtdMercadorias = qtdMercadorias;
		this.QtdPosicoes = qtdPosicoes;
		this.QtdCategorias = qtdCategorias;
	}
}