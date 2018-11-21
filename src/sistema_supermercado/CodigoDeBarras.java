package sistema_supermercado;

import java.util.*;

public class CodigoDeBarras {
	
	protected static List<CodigoDeBarras> codigos;
	
	protected int codigoNumerico;
	
	private static int lastNewCode = 0;
	
	protected CodigoDeBarras(int codigo)
	{
		
		if(codigos == null)
		{
			codigos = new ArrayList<CodigoDeBarras>();
		}
		
		//verificar unicidade do codigo
		for(CodigoDeBarras cod : codigos)
		{
			if(cod.codigoNumerico == codigo)
			{
				throw new IllegalArgumentException("Codigo de barras com o codigo numerico " + codigo + " já existe");
			}
		}
		
		this.codigoNumerico = codigo;
		
		codigos.add(this);
	}
	
	//a mesma coisa, mas verifica a unicidade do código considerando somente a subclasse especificada
	protected CodigoDeBarras(int codigo, Class subclass)
	{
		if(codigos == null)
		{
			codigos = new ArrayList<CodigoDeBarras>();
		}
		
		//verificar unicidade do codigo, MAS só na subclasse especificada
		for(CodigoDeBarras cod : codigos)
		{
			if(cod.codigoNumerico == codigo && cod.getClass() == subclass)
			{
				throw new IllegalArgumentException(subclass + " com o codigo numerico " + codigo + " já existe");
			}
		}
		
		this.codigoNumerico = codigo;
		
		codigos.add(this);
	}
	
	public void PrintCodigo()
	{
		System.out.println(codigoNumerico);
	}
	
	//getters and setters
	public int GetCodigoNumerico()
	{
		return codigoNumerico;
	}
	
	//gera novo codigo que ainda não está em uso
	public static int GetCodigoNovo()
	{
		outer:
		while(true)
		{
			lastNewCode++;
			//se codigo já está em uso, pula
			//senão, usa este
			for(CodigoDeBarras c : codigos)
			{
				if(c.codigoNumerico == lastNewCode)
				{
					continue outer;
				}
			}
			return lastNewCode;
		}
	}
	
}
