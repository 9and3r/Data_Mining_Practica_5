package pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Instances;

public class DatuKargatzailea {

	private String path;
	private FileReader fi;

	public DatuKargatzailea(String pPath) {
		path = pPath;
		fi = null;
	}

	private void zabalduFitx() {
		try {
			fi = new FileReader(path);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se ha podido cargar el fichero, comprueba el path: " + path);
			System.exit(0);
		}
	}

	private boolean datuEgokiak() {
		if (path == null) {
			System.out
					.println("ERROR: Ha habido un error con el fichero de los datos.");
			System.exit(0);
			return false;
		}
		return true;
	}

	private void itxiFitx() {
		try {
			fi.close();
		} catch (IOException e) {
			System.out.println("ERROR: Ha habido un error con el fichero de los datos.");
			System.exit(0);
		}
	}

	public Instances emonInstantziak() {
		if (datuEgokiak()) {
			zabalduFitx();
			Instances i1=null;
			try {
				i1 = new Instances(fi);
			} catch (IOException e) {
				System.out.println("ERROR: Ha habido un error con el fichero de los datos, comprueba su integridad y formato");
				System.exit(0);
			}
			itxiFitx();
			return i1;
		} else {
			System.out.println("ERROR: Ha habido un error con el fichero de los datos.");
			System.exit(0);
			return null;
		}
	}
	
	public void idatzi(ArrayList<String> klaseak, String arg2){
		File file = new File(arg2);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			System.out.println("ERROR: Ha habido un error a la hora de escribir en el fichero de salida");
			System.exit(0);
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
		} catch (IOException e1) {
			System.out.println("ERROR: Ha habido un error a la hora de escribir en el fichero de salida");
			System.exit(0);
		}
		System.out.println("\nInstancias clasificadas en: " +  arg2);
		for (int i = 0; i < klaseak.size(); i++) {
			try {
				writer.write("instancia numero " + i + " " + klaseak.get(i)
						+ "\n");
			} catch (IOException e) {
				System.out.println("ERROR: Ha habido un error a la hora de escribir en el fichero de salida");
				System.exit(0);
			}

		}
		
	}

}
