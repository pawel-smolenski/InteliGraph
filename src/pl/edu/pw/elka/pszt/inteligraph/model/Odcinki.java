package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;

public class Odcinki
{
		 
	//  Ta funkcja sprawdza, czy punkt z należy do odcinka |xy|
private static int przynaleznosc(int xx, int xy, int yx, int yy, int zx, int zy)    
	{
		int det; //wyznacznik macierzy
		 
		det = xx*yy + yx*zy + zx*xy - zx*yy - xx*zy - yx*xy;
		if (det!=0)
		return 0 ;
		else {
		if ((Math.min(xx, yx) <= zx) && (zx <= Math.max(xx, yx)) &&
		(Math.min(xy, yy) <= zy) && (zy <= Math.max(xy, yy)))
		return 1;
		else
		return 0;
		}
	}
	 
	//  wyznacznik macierzy
private static int det_matrix(int xx, int xy, int yx, int yy, int zx, int zy)
	{
	return (xx*yy + yx*zy + zx*xy - zx*yy - xx*zy - yx*xy);
	}
	 
public  void main(Point beg_1, Point end_1, Point beg_2, Point end_2) 
	{
		//int i,det; //wyznacznik macierzy
		int x[] = new int[4]; //tablica wspolrzednych x punktow
		int y[] = new int[4]; //tablica wspolrzednych y punktow

		x[0] =  beg_1.x;
		y[0] =  beg_1.y;
		
		x[1] = end_1.x;
		y[1] = end_1.y;
		
		x[2] = beg_2.x;
		y[2] = beg_2.y;
		
		x[3] = end_2.x;
		y[3] = end_2.y;
		 
		//      Sprawdzanie, czy jakiś punkt należy do drugiego odcinka
		if (przynaleznosc(x[0], y[0], x[1], y[1], x[2], y[2])==1) System.out.println("Odcinki sie przecinaja- przynaleznosc"); else
		if (przynaleznosc(x[0], y[0], x[1], y[1], x[3], y[3])==1) System.out.println("Odcinki sie przecinaja- przynaleznosc"); else
		if (przynaleznosc(x[2], y[2], x[3], y[3], x[0], y[0])==1) System.out.println("Odcinki sie przecinaja- przynaleznosc"); else
		if (przynaleznosc(x[2], y[2], x[3], y[3], x[1], y[1])==1) System.out.println("Odcinki sie przecinaja- przynaleznosc"); else
		 
		//      zaden punkt nie nalezy do drugego odcinka
		if ((det_matrix(x[0], y[0], x[1], y[1], x[2], y[2]))*(det_matrix(x[0], y[0], x[1], y[1], x[3], y[3]))>=0)
		System.out.println("Odcinki sie NIE przecinaja"); else
		if ((det_matrix(x[2], y[2], x[3], y[3], x[0], y[0]))*(det_matrix(x[2], y[2], x[3], y[3], x[1], y[1]))>=0)
		System.out.println("Odcinki sie NIE przecinaja");
		else //znaki wyznaczników sa równe
		System.out.println("Odcinki sie przecinaja- punkty leza po przeciwnych stronach");
	}
}
