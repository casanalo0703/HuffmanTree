package Parte1;

public class NodoH implements Comparable<NodoH>{
	char c;
	boolean hoja;
	int repeticion;
	NodoH hijoi;
	NodoH hijod;
	
	@Override
	public int compareTo(NodoH x)
    {
        return repeticion-x.repeticion;
    }
}
