package Parte1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

public class Main {

	public static void main(String[] args) throws IOException {
		Leetxt leer=new Leetxt();
		ArrayList<NodoH> lista;
		NodoH arbol=new NodoH();
		String str="";
		BitSet compreso=new BitSet();
		String ruta="C:\\Users\\carlo\\OneDrive\\Escritorio\\holacopia.txt";
		String ruta2="C:\\Users\\carlo\\OneDrive\\Escritorio\\holabin.bin";
		Descod descomp= new Descod();
		
		//leer.lee("The raven.txt");
		int[] repeticiones=leer.cuentaletras("hola.txt");
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
