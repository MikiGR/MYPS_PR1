/*
@author Miguel Galdeano Rodríguez
@author Pablo León Vazquez
*/

/* 
PRIMER ERROR: En el método buscar(Grupo g) si el grupo g es nulo 
no está controlado.
Solución: Añadir al principio if(g==null) return -1;

SEGUNDO ERROR: Si el constructor de 2 parametros recibe como 2º
parametro el valor 1 he intento añadir 2 grupos salta la excepción 
index out of bounds (no controlado)
Solución: Añadir al principio del if(pos == -1) de anyadirActividad(Grupo g)
if (ngrupos >= grupos.length) {
    throw new ClubException("ERROR: no se pueden añadir más grupos, el club está lleno");
}

TERCER ERROR: En el método anyadirActividad(String[] datos) si el array 
datos tiene menos de 5 elementos no está controlado.
Solución: Añadir al principio del método anyadirActividad(String[] datos)
if (datos.length < 5) {
	throw new ClubException("ERROR: faltan datos");
}
*/
package clubdeportivo;

import java.util.StringJoiner;

public class ClubDeportivo {
	private String nombre;
	private int ngrupos;
	private Grupo[] grupos;
	private static final int TAM = 10;

	//Completed - 1/1
	public ClubDeportivo(String nombre) throws ClubException {
		this(nombre, TAM);
	}

	//Completed - 1/1
	public ClubDeportivo(String nombre, int n) throws ClubException {
		if (n <= 0) {
			throw new ClubException("ERROR: el club no puede crearse con un número de grupos 0 o negativo");
		}
		this.nombre = nombre;
		grupos = new Grupo[n];
	}

	private int buscar(Grupo g) {
		int i = 0;
		while (i < ngrupos && !g.equals(grupos[i])) {
			i++;
		}
		if (i == ngrupos) {
			i = -1;
		}
		return i;
	}
	// Completed - 3/3
	// DONE -> PLAZAS < 0
	// DONE -> MATRICULADOS < 0
	public void anyadirActividad(String[] datos) throws ClubException {
		try {
			int plazas = Integer.parseInt(datos[2]);
			int matriculados = Integer.parseInt(datos[3]);
			double tarifa = Double.parseDouble(datos[4]);
			Grupo g = new Grupo(datos[0], datos[1], plazas, matriculados, tarifa);
			anyadirActividad(g);
		} catch (NumberFormatException e) {
			throw new ClubException("ERROR: formato de número incorrecto");
		}
	}

	// Completed - 3/3
	public void anyadirActividad(Grupo g) throws ClubException {
		if (g==null){ // ADDME: anaydido para comprobar los grupos nulos
			throw new ClubException("ERROR: el grupo es nulo");
		}
		int pos = buscar(g);
		if (pos == -1) { // El grupo es nuevo
			grupos[ngrupos] = g;
			ngrupos++;
		} else { // El grupo ya existe --> modificamos las plazas
			grupos[pos].actualizarPlazas(g.getPlazas());
		}
	}

	// Completed - 4/4
	// Case plazaslibres = 0
	//Case plazaslibres = max plazas
	//Case actividad no existente
	// Case exito
	public int plazasLibres(String actividad) {
		int p = 0;
		int i = 0;
		while (i < ngrupos) {
			if (grupos[i].getActividad().equals(actividad)) {
				p += grupos[i].plazasLibres();
			}
			i++;
		}
		return p;
	}

	//Completed - 3/3
	// Case arg -> npersonas < 0 
	public void matricular(String actividad, int npersonas) throws ClubException {
		int plazas = plazasLibres(actividad);
		if (plazas < npersonas) {
			throw new ClubException("ERROR: no hay suficientes plazas libres para esa actividad en el club.");
		}
		int i = 0;
		while (i < ngrupos && npersonas > 0) {
			if (actividad.equals(grupos[i].getActividad())) {
				int plazasGrupo = grupos[i].plazasLibres();
				if (npersonas >= plazasGrupo) {
					grupos[i].matricular(plazasGrupo);
					npersonas -= plazasGrupo;
				} else {
					grupos[i].matricular(npersonas);
				}
			}
			i++;
		}
	}

	//  Completed - 2/2
	public double ingresos() {
		double cantidad = 0.0;
		int i = 0;
		while (i < ngrupos) {
			cantidad += grupos[i].getTarifa() * grupos[i].getMatriculados();
			i++;
		}
		return cantidad;
	}
	// Completed - 3/3
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "[ ", " ]");
		int i = 0;
		while (i < ngrupos) {
			sj.add(grupos[i].toString());
			i++;
		}
		return nombre + " --> " + sj.toString();
	}
}
