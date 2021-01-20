package Parte1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

public class Main {

	public static void main(String[] args) throws IOException {
		//makes an instance of the class the makes the compression
		Leetxt leer=new Leetxt();
		//makes an instance of the class the makes the decompression
		Descod descomp= new Descod();


		ArrayList<NodoH> lista;
		NodoH arbol;
		String str="";
		BitSet compreso;
		String ruta="C:\\copy.txt"; //write the route where you want the .txt converted from .bin
		String ruta2="C:\\copy.bin"; //write the route where you want the .bin converted from .txt
		
		//leer.lee("The raven.txt");
		int[] repeticiones=leer.cuentaletras("hola.txt");//write the route of where the .txt is
		lista=leer.lista(repeticiones);
		arbol=leer.creaArbol(lista);
		leer.abolabin(arbol, str);
		compreso=leer.textacod("hola.txt");
		leer.guardaBit(compreso, ruta2);
		BitSet compreso2=descomp.binABit(ruta2);
		descomp.convArbol(compreso2);
		descomp.creaTxt(ruta, compreso2);
	}
}
