package sistema_supermercado;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class IO_Auxiliary {

	public IO_Auxiliary() {
		// TODO Auto-generated constructor stub
	}
	
	//funções auxiliares
	
		//pega do input aberto um inteiro entre minValue e maxValue (inclusive). Imprime a mensagem message para explicar o que é pedido
		static int GetInt(Scanner input, int minValue, int maxValue, String message)
		{
			int i;
			
			while(true)
			{
				System.out.println(message);
				try 
				{
					i = Integer.parseInt(input.nextLine());
				}
				catch(NumberFormatException e)
				{
					continue;
				}
				catch(NoSuchElementException e)
				{
					continue;
				}
				
				if(minValue <= i && i <= maxValue)
				{
					return i;
				}
				//else, continue. 
				
			}
		}
		
		//pega do input aberto um float entre minValue e maxValue (inclusive). Imprime a mensagem message para explicar o que é pedido
		static float GetFloat(Scanner input, float minValue, float maxValue, String message)
		{
			float f;
			
			while(true)
			{
				System.out.println(message);
				try 
				{
					f = Float.parseFloat(input.nextLine());
				}
				catch(NumberFormatException e)
				{
					continue;
				}
				
				if(minValue <= f && f <= maxValue)
				{
					return f;
				}
			}
		}

}
