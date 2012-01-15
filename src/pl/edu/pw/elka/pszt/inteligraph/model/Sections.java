package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * Klasa zapewniająca obsługę, rozpoznawania problemu przecinania się krawędzi.
 */
public class Sections
{
	/**
	 * Sprawdza czy dwie krawędzie sie przecinają, zakłada że krawędzie które maja wspólne początki lub końce nie przecinają się.
	 * @return  1 - jeżeli przecinają się, 0 - nie przecinają sie
	 */
	public static int isCrossing(Point beg1, Point end1, Point beg2, Point end2) {
	    int[] xs = {beg2.x, end2.x};
	    int[] ys = {beg2.y, end2.y};
	    //sprawdzanie czy 
	    for (int i = 0 ; i < 2 ; ++i) {
		if (beg1.x == xs[i]) {
		    if (beg1.y == ys[i])
			return 0;
		}
		if (end1.x == xs[i]) {
		    if (end1.y == ys[i])
			return 0;
		}
	    }
	    if(Line2D.linesIntersect(beg1.x, beg1.y, end1.x, end1.y, beg2.x, beg2.y, end2.x, end2.y)) {
		return 1;
	    }
	    return 0;
	}
	/**
	 * Sprawdza czy odcinki się przecinają.
	 * @param beg1
	 * @param end1
	 * @param beg2
	 * @param end2
	 * @return 1 jak się przecinają, 0 jak się nie przecinają.
	 */
	public int crossing(Point beg1, Point end1, Point beg2, Point end2) {
	    double a1, a2;
	    double b1, b2;
	    a1 = ((double)end1.y - (double)beg1.y)/((double)end1.x-(double)beg1.x);
	    b1 = (double)end1.y - (a1)*(double)end1.x;
	    
	    a2 = ((double)end2.y - (double)beg2.y)/((double)end2.x-(double)beg2.x);
	    b2 = (double)end2.y - (a2)*(double)end2.x;
	    
	    double w, wX, wY, x, y;
	    w = a1*(-1.0) - a2*(-1.0);
	    wX = b1*(-1.0) - b2*(-1.0);
	    wY = a1*b2 - a2*b1;
	    
	    x = -wX/w; y = -wY/w;
//	    System.out.println("" + beg1 + end1 + beg2 + end2);
//	    System.out.println("y1="+a1+"x"+"-"+b1+" y2="+a2+"x"+"-"+b2);
	    System.out.println("["+ x + ", " + y + "]");
	    System.out.println(beg1.distance(x, y) + " + " + end1.distance(x, y) +" < "+ beg1.distance(end1));
	    if ((beg1.distance(x, y) + end1.distance(x, y) < beg1.distance(end1)))
		return 1;    
	    return 0;	    
	}
}
