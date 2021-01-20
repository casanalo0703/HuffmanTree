package Parte1;

import org.jetbrains.annotations.NotNull;

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

	int tam;
	//ASCII tiene 256 characteres, si se quiere usar UTF-8 u otro necesita hacer mas grande el arreglo
	//ASCII is composed of 256 characters, if you want to use UTF-8 or other you need to make this array bigger
	String[] character = new String[256];
	//Bitset para el header
	BitSet guarda=new BitSet();
	
	//lee texto, solo se necesita si se quiere corroborar que el texto es propiamente leido
	//outputs the text, if you want to see if the text if being read correctly use it, otherwise is unnesesary
	void lee (String archivo) throws IOException, FileNotFoundException {
		String cadena;
		FileReader file= new FileReader(archivo);
		BufferedReader b = new BufferedReader(file);
		while((cadena=b.readLine())!=null) {
			System.out.println(cadena);
		}
		b.close();
		cuentaletras(archivo);
	}

	//busca el archivo y guarda en una tabla de int en la pos en ASCII las veces que se repite una letra
	//finds the file and saves how many times a letter has been seen using their ASCII value
	int[] cuentaletras(String archivo) throws IOException, FileNotFoundException
	{
		FileReader file= new FileReader(archivo);
		BufferedReader b = new BufferedReader(file);
		String linea;
		int[] rep=new int[256];
		//cuenta las repeticiones
		//conts the letters
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
		//imprime la tabla, no es necesaria esta parte
		//prints the table, it is not necessary
		/*for(int i=0;i<rep.length;i++)
		{
			if(rep[i]>0)
				System.out.println((char)i + "" + rep[i]);
		}*/
		return rep;
	}
	
	ArrayList<NodoH> lista(int @NotNull [] rep)
	{
		ArrayList<NodoH> lista= new ArrayList<NodoH>();
		//acomoda los elementos de la tabla y los inserta en un ArrayList
		//Sorts the elements of the table and inserts them in an ArrayList
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
		//imprime el ArrayList
		//prints the ArrayList
		/*for(int i=0;i<lista.size();i++)
		{
			System.out.println(lista.get(i).c+" "+lista.get(i).repeticion);
		}*/
		return lista;
	}
	
	NodoH creaArbol(ArrayList<NodoH> lista)
	{
		//creates the tree in the arrayList and then pops the tree out
		//crea el arbol dentro del ArrayList y al final saca el arbol de la lista
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
		//navigates through the tree, this function creates the bynary string
		//va recorriendo el arbol, el mettodo crea un String binario
		if(arbol.hoja)
		{
			S=S.concat("1");
			character[arbol.c]=S;
			return;
		}
		S=S.concat("0");

		abolabin(arbol.hijoi,S);
		S=S.substring(0, S.length()-1);
		S=S.concat("1");
		abolabin(arbol.hijod,S);
		S=S.substring(0, S.length()-1);
		//imprime el String
		//prints the String
		/*for(int i=0;i<character.length;i++)
		{
			if(character[i]!=null)
			{
				System.out.println((char)i + " "+character[i]);
			}
		}*/
	}
	
	void comprime()
	{
		//creates a byte array with the string
		//crea un byte array con el String
		int tama=(2*tam)+1;
		byte[] aux=new byte[tama];
		aux[0]=(byte)tam;
		//apuntador de x
		int j=0;
		//LLENA LOS 2N+1 BYTES
		for(int i=1;i<tama;i++)
		{
			//descarta los espacios con null
			while(character[j]==null)
			{
				if(j<character.length-1)
					j++;
			}
			aux[i]=(byte)j;
			i++;
			aux[i]=(byte)(character[j].length()-1);
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
			while(character[j]==null)
			{
				if(j<character.length)
				//recorre los null de nuevo
				j++;
			}
			char[] cod = character[j].toCharArray();
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
				char[] aux2=character[aux[i]].toCharArray();
				for(int j=0;j<aux2.length-1;j++)
				{
					guarda.set(fin, aux2[j]=='1');
					fin++;
				}
			}
			char[] aux3=character['\n'].toCharArray();
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