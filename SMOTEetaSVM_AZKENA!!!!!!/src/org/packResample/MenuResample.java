package org.packResample;

import java.io.File;
import java.io.IOException;

import klasekontatzaile.Classcounter;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class MenuResample {

	public static void main(String[] args) {
		// Auto-generated method stub
		if(args.length != 3){
		System.out.println("Error! Debe mandar los siguientes parametros:\n 1 -> path absoluto del fichero de entrada.\n 2 -> indice de la clase del fichero de entrada.\n 3 -> Path absoluto del fichero de salida.");
	}else{
		Datuak d = new Datuak(args[0]);
		Instances aux = d.getDatuak();
		int indize = Integer.parseInt(args[1]);
		if(indize<0){
			indize = aux.numAttributes()-1;
		}
		Resamplea reza = new Resamplea(indize, aux);
		try {
			reza.aplikatuResample();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		aux = reza.getDatuak();
		//inprimatu
		try {
			gorde(args[2], reza.getdatuak());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Classcounter c = new Classcounter();
		int[] emaitzak = c.contarClases(reza.getdatuak(), indize);
		System.out.println("clase1: " + emaitzak[0] + ";\nclase2: " + emaitzak[1]+";");
	}
		
		
	}

	private static void gorde(String path, Instances datu)	throws IOException {
		//TODO
	

			ArffSaver saver = new ArffSaver();
			saver.setInstances(datu);
			saver.setFile(new File(path));
		//	saver.setDestination(new File(nombreFichero)); // **not** necessary in
															// 3.5.4 and later
			saver.writeBatch();
	}	
}
