package org.Smote.v1sv0;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import klasekontatzaile.Classcounter;
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
		instanciasEntrada.setClassIndex(classindex);
		
		String ficheroSalida = args[2];
		
	
		Classcounter c = new Classcounter();
		int[] valoresiniciales = c.contarClases(instanciasEntrada, classindex);
		for(int i = 0; i < valoresiniciales.length; i++){
			System.out.println("Clase" + i + ": " + valoresiniciales[i]);
		}
//	
		Smotea s = new Smotea(instanciasEntrada, classindex);
//		FileWriter fichero2 = null;
//		PrintWriter pw = null;
//		try {
//			fichero2 = new FileWriter(ficherotrain
//					+ "RESULTADOSMOTE.txt");
//			pw = new PrintWriter(fichero2);
//
//			pw.println(resultado);
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//			try {
//
//				if (null != fichero2)
//					fichero2.close();
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		
		
		try {
			s.guardarARFF(s.getDatuak(), ficheroSalida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
}


