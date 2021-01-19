package Parte1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;


@SuppressWarnings("ALL")
public class Leetxt {
	
	//tamano del Arraylist
	int tam;
	//guarda las direcciones de los nodos en el arbol
	String[] x=new String[256];
	//Bitset para el header
	BitSet guarda=new BitSet();
	
	//lee texto
	/*void lee (String archivo) throws IOException, FileNotFoundException {
		String cadena;
		FileReader file= new FileReader(archivo);
		BufferedReader b = new BufferedReader(file);
		while((cadena=b.readLine())!=null) {
			System.out.println(cadena);
		}
		b.close();
		cuentaletras(archivo);
	}*/
	
	int[] cuentaletras(String archivo) throws IOException, FileNotFoundException
	{
		FileReader file= new FileReader(archivo);
		BufferedReader b = new BufferedReader(file);
		String linea;
		int[] rep=new int[256];
		//cuenta las repeticiones
		while((linea=b.readLine())!=null)
		{
			char[] ch= linea.toCharArray();
			for(int i=0;i<ch.length;i++)
			{
				rep[ch[i]]++;
			}
			rep['\n']++;
		}
		b.close();
		//imprime las rep
		/*for(int i=0;i<rep.length;i++)
		{
			if(rep[i]>0)
				System.out.println((char)i + "" + rep[i]);
		}*/
		return rep;
	}
	
	ArrayList<NodoH> lista(int[] rep)
	{
		ArrayList<NodoH> lista= new ArrayList<NodoH>();
		//crea el Array List acomodado
		for(int i=0;i<rep.length;i++)
		{
			if(rep[i]>0)
			{
				NodoH aux=new NodoH();
				aux.c=(char)i;
				aux.repeticion=rep[i];
				aux.hoja=true;
				lista.add(aux);
			}
		}
		lista.sort(null);
		tam=lista.size();
	//imprime	
		/*for(int i=0;i<lista.size();i++)
		{
			System.out.println(lista.get(i).c+" "+lista.get(i).repeticion);
		}*/
		return lista;
	}
	
	NodoH creaArbol(ArrayList<NodoH> lista)
	{
		//crea los nodos y los acomoda en la lista
		while(lista.size()>1)
		{
			NodoH aux= new NodoH();
			aux.hoja=false;
			aux.hijoi=lista.remove(0);
			aux.hijod=lista.remove(0);
			aux.repeticion=aux.hijod.repeticion+aux.hijoi.repeticion;
			lista.add(aux);
			lista.sort(null);
		}
		return lista.remove(0);
	}
	
	void abolabin(NodoH arbol,String S)
	{
		if(arbol==null)
		{
			return;
		}
		//va recorriendo el arbol y aconodando 1's y 0's
		if(arbol.hoja==true)
		{
			S=S.concat("1");
			x[arbol.c]=S;
			return;
		}
		S=S.concat("0");
		abolabin(arbol.hijoi,S);
		S=S.substring(0, S.length()-1);
		S=S.concat("1");
		abolabin(arbol.hijod,S);
		S=S.substring(0, S.length()-1);
		//imprime el arreglo con las direcciones
		/*for(int i=0;i<x.length;i++)
		{
			if(x[i]!=null)
			{
				System.out.println((char)i + " "+x[i]);
			}
		}*/
	}
	
	void comprime()
	{
		int tama=(2*tam)+1;
		byte[] aux=new byte[tama];
		aux[0]=(byte)tam;
		//apuntador de x
		int j=0;
		//LLENA LOS 2N+1 BYTES
		for(int i=1;i<tama;i++)
		{
			//descarta los espacios con null
			while(x[j]==null)
			{
				if(j<x.length-1)
					j++;
			}
			aux[i]=(byte)j;
			i++;
			aux[i]=(byte)(x[j].length()-1);
			j++;
		}
		guarda=BitSet.valueOf(aux);
		//guarda la posicion despues de los 2n+1 bytes
		int despues=((2*tam)+1)*8;
		//reinicio apuntador
		j=0;
		//escribe las dirrecciones de 1 y 0
		for(int i=0;i<tam;i++)
		{
			while(x[j]==null)
			{
				if(j<x.length)
				//recorre los null de nuevo
				j++;
			}
			char[] cod = x[j].toCharArray();
			for(int a=0;a<cod.length-1;a++)
			{
				guarda.set(despues, cod[a]=='1');
				despues++;
			}
			j++;
		}
		//garda un uno en la ultima pos para no perder los ceros
		guarda.set(despues, true);
		//System.out.println("Sali de comprime");
	}
	
	BitSet textacod(String dir) throws IOException, FileNotFoundException
	{
		//ptimero comprime el abol y luego el texto
		comprime();
		String cadena;
		FileReader file= new FileReader(dir);
		BufferedReader b= new BufferedReader(file);
		//removemos el ultimo 1
		int fin=guarda.length()-1;
		while((cadena=b.readLine())!=null)
		{
			char[] aux=cadena.toCharArray();
			for(int i=0;i<aux.length;i++)
			{
				char[] aux2=x[aux[i]].toCharArray();
				for(int j=0;j<aux2.length-1;j++)
				{
					guarda.set(fin, aux2[j]=='1');
					fin++;
				}
			}
			char[] aux3=x['\n'].toCharArray();
			for(int i=0;i<aux3.length-1;i++)
			{
				guarda.set(fin, aux3[i]=='1');
				fin++;
			}
		}
		guarda.set(fin, true);
		b.close();
		//System.out.println("sali de textacod");
		return guarda;
	}
	
	void guardaBit(BitSet header,String dir) throws IOException
	{
		FileOutputStream fos=new FileOutputStream(dir);
		DataOutputStream salida = new DataOutputStream(fos);
		salida.write(header.toByteArray());
		salida.close();
	}
}
