package org.packSVM;

import weka.core.Instances;

public class MenuSVM {
	
	public static void main(String[] args) {
		
		if(args.length != 3){
			System.out.println("parameter error");
		}
		else{
			//Cargamos el fichero de entrada
			String ficheroentrada = args[0];
			
			//Extraemos las instancias
			Datuak d = new Datuak(ficheroentrada);
			Instances instanciasentrenamiento = d.getDatuak();
			
			//Cargamos el índice de la clase
			int classindex = Integer.parseInt(args[1]);
			if(classindex < 0){
				classindex = (instanciasentrenamiento.numAttributes()-1);
			}
			
			//Cargamos el fichero de evaluación
			String ficheroEvaluacion = args[2];
			d = new Datuak(ficheroEvaluacion);
			Instances instanciasdev = d.getDatuak();
			
			//Atribuimos el índice de la clase a las instancias del train y del dev.
			
			instanciasdev.setClassIndex(classindex);
			instanciasentrenamiento.setClassIndex(classindex);
			
			
			WekaSVM s = new WekaSVM(instanciasentrenamiento, classindex, instanciasdev);
			try {
				s.estimar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
