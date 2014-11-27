package org.packDataMining;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		instanciasEntrada.setClassIndex(classindex);
		
		String ficheroSalida = args[2];
		
		
		
//		Smote s = new Smote(instanciasEntrada, classindex);
//		try {
//			s.analisisParametros(instanciasEntrada, classindex);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("Nearest neighbours optimo: " + s.getNearestNeighbours());
//		System.out.println("Porcentaje optimo: " + s.getSmote());
//		System.out.println("Porcentajes de valores de clases: " + s.getPorcentajes());
//		try {
//			s.guardarARFF(s.getInstancias(), ficheroSalida);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
		Classcounter c = new Classcounter();
		int[] valoresiniciales = c.contarClases(instanciasEntrada, classindex);
		for(int i = 0; i < valoresiniciales.length; i++){
			System.out.println("Clase" + i + ": " + valoresiniciales[i]);
		}
		SmoteJavi s = null;
		SmoteAplikatzailea sa = new SmoteAplikatzailea();
		try {
			s = sa.analisisParametros(instanciasEntrada, classindex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Nearest neighbours optimo: " + s.getNearestNeighbours());
		System.out.println("Porcentaje optimo: " + s.getSmote());
		System.out.println("Porcentajes de valores de clases: " + s.getPorcentajes());
		
		String resultado = "Nearest neighbours optimo: " + s.getNearestNeighbours() + "\n" + "Porcentaje optimo: " + s.getSmote() + "\n" + "Porcentajes de valores de clases: " + s.getPorcentajes();
		
		
		//guardamos el fichero con los parámetros óptimos
		FileWriter fichero2 = null;
		PrintWriter pw = null;
		try {
			fichero2 = new FileWriter("ResultadosSMOTE" + ficherotrain
					+ ".txt");
			pw = new PrintWriter(fichero2);

			pw.println(resultado);

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {

				if (null != fichero2)
					fichero2.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		try {
			s.guardarARFF(s.getInstancias(), ficheroSalida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
}


