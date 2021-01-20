package Parte1;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

public class Descod
{
	//tenemos el nodo que sera el que apunte al inicio del arbol
	NodoH r=new NodoH();
	//apuntador para los 2*n+1 posiciones
	int apunta;
	
	BitSet binABit(String dir) throws IOException
	{
		BitSet aux;
		byte[] auxBytes;
		FileInputStream fis=new FileInputStream(dir);
		DataInputStream dis=new DataInputStream(fis);
		auxBytes=dis.readAllBytes();
		aux=BitSet.valueOf(auxBytes);
		dis.close();
		return aux;
		
	}
	
	void convArbol(BitSet cod)
	{
		byte[] bin=cod.toByteArray();
		//obtenemos el tamano del BitSet
		int n=bin[0];
		//creamos apuntador para el arbol
		apunta=8*(2*n+1);
		//creamos el arbol
		for(int i=1;i<(2*n)+1;i++)
		{
			NodoH aux=r;
			//guarda el ASCII del char de la hoja
			int c=bin[i];
			i++;
			int tama=bin[i];
			for(int j=0;j<tama;j++)
			{
				//recorremos por la derecha
				if(cod.get(apunta))
				{
					if(aux.hijod==null)
					{
						aux.hijod=new NodoH();
					}
					aux=aux.hijod;
				}
				//recorremos por la izquierda
				else
				{
					if(aux.hijoi==null)
					{
						aux.hijoi=new NodoH();
					}
					aux=aux.hijoi;
				}
				apunta++;
			}
			//si hemos llegado al final aqui va una hoja y guardamos el char
			if(aux!=r)
			{
				aux.c=(char)c;
				aux.hoja=true;
			}
		}
	}
	
	void creaTxt(String ruta,BitSet cod) throws IOException
	{
		//decimos la ruta a escribir
		File disco=new File(ruta);
		//se agrega esta funcion porque mi programa tenia problemas al sobreescribir
		//if(!disco.exists())
		//{
			//disco.createNewFile();
		//}
		FileWriter escribe= new FileWriter(disco);
		//nos posicionamos en la primera posicion
		NodoH aux=r;
		//recorremos el arbol de acuerdo a el bitset
		for(int i=apunta;i<cod.length()-1;i++)
		{
			//si es true es 1 y es derecha, si es false es izq y es 0
			if(cod.get(i))
			{
				aux=aux.hijod;
			}
			else
			{
				aux=aux.hijoi;
			}
			//si es hoja escribimos el char
			if(aux.hoja)
			{
				escribe.append(aux.c);
				//reseteamos aux al inicio del arbol
				aux=r;
			}
		}
		escribe.close();
	}
}
