package org.packDataMining;

import java.io.IOException;
import java.util.ArrayList;

import classcounter.Classcounter;
import weka.core.Instances;


public class MenuSmote {
	
	public static void main(String[] args) {
		
		if(args.length != 3){
		System.out.println("Error! Debe mandar los siguientes parametros:\n 1 -> path absoluto del fichero de entrada.\n 2 -> indice de la clase del fichero de entrada.\n 3 -> Path absoluto del fichero de salida.");
	}
	else{
		String ficherotrain = args[0];
		Datuak d = new Datuak(ficherotrain);
		Instances instanciasEntrada = d.getDatuak();

		int classindex = Integer.parseInt(args[1]);
		if(classindex == -1){
			classindex = instanciasEntrada.numAttributes()-1;
		}
		
		String ficheroSalida = args[2];
		
		Smote s = new Smote(instanciasEntrada, classindex);
		try {
			s.analisisParametros(instanciasEntrada, classindex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Nearest neighbours optimo: " + s.getNearestNeighbours());
		System.out.println("Porcentaje optimo: " + s.getSmote());
		System.out.println("Porcentajes de valores de clases: " + s.getPorcentajes());
		try {
			s.guardarARFF(s.getInstancias(), ficheroSalida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	}
}


