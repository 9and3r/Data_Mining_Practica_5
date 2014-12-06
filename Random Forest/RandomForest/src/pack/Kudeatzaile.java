package pack;

import java.util.ArrayList;

public class Kudeatzaile {

	public static void main(String[] args) {
		if (args.length == 4) {
			modo1(args);
		} else if (args.length == 3) {
			modo2(args);
		} else {
			ayuda();
		}

	}

	private static void modo1(String[] args) {
		RanFor.getNRanFor().kargatuInstantziak(args[0], args[1], args[2]);
		RanFor.getNRanFor().parametroOptimoakLortu();
		RanFor.getNRanFor().exportMdl(args[3]);

	}

	private static void modo2(String[] args) {
		RanFor.getNRanFor().kargatuModeloa(args[0]);
		ArrayList<String> klaseak = RanFor.getNRanFor()
				.klasifikatuTest(args[1]);
		DatuKargatzailea dk = new DatuKargatzailea(null);
		dk.idatzi(klaseak, args[2]);

	}

	private static void ayuda() {

		System.out
				.println("Modo de funcionamiento:\n\nHay dos maneras de ejecutar el programa, dependiendo de nuestro objetivo.");
		System.out
				.println("\n1) Crea un clasificador RandomForest optimizando 3 parametros (numero de arboles, nivel de profundidad y numero de atributos).\nPara ejecutar esta funcion, deberas pasar 4 parametros, de la siguiente manera:\n[path del fichero train] [indice de la clase, para referirse al ultimo se podra usar -1] [path del fichero dev] [path del fichero mdl de salida, que contendra el clasificador]");
		System.out
				.println("\n2) Clasificara un conjunto de datos, con el clasificador que indiquemos.\nPara ejecutar esta funcion, deberas pasar 3 parametros, de la siguiente manera:\n[path del fichero que contiene el clasificador] [path del fichero que contiene las instancias] [path del fichero de salida en el que se desee guardar la solucion]");
	}
}
