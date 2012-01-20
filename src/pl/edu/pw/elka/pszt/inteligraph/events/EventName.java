package pl.edu.pw.elka.pszt.inteligraph.events;

/**
 * Enumeracja zawierająca nazwy wszystkich zdarzeń jakie mogą być zgłaszane
 */
public enum EventName {
	
    	/**
    	 * Event startowy, pokazuje puste okno.
    	 */
    	SHOW_EMPTY_WINDOW,
	
	/**
	 * Rysuj graf wykonując obliczenia dla n-kroków.
	 */
	DRAW_GRAPH_N,
	
	/**
	 * Rysuj graf dopóki nie znaleziono najlepszego lub nie naciśnięto stop.
	 */
	DRAW_GRAPH_INF,
	
	/**
	 * Naciśnięto stop.
	 */
	DRAW_GRAPH_STOP,
	
	/**
	 * Odświeżenie grafu
	 */
	REFRESH_GRAPH,
	
	/**
	 * Odświeżanie iteracji.
	 */
	ITERATION_ALGORITHM,
	
	/**
	 * Obsługa wczytania pliku.
	 */
	CHOOSE_FILE,
	
	/**
	 * Podano złe parametry dla obliczeń algorytmu.
	 */
	WRONG_GRAPH_PARAMS,
	
	/**
	 * O programie.
	 */
	ABOUT
}
