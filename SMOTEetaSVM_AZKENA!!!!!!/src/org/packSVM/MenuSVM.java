package org.packSVM;


import org.packDataMining.SmoteAplikatzailea;
import org.packDataMining.SmoteJavi;
import org.packResample.Resamplea;

import weka.core.Instances;

public class MenuSVM {
	//Using -h 0 may be faster
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
			Resamplea ra = new Resamplea(classindex, instanciasentrenamiento);
		    try {
				ra.aplikatuResample();
				Instances instanciasra = ra.getdatuak();
				instanciasra.setClassIndex(classindex);
				SmoteAplikatzailea sa = new SmoteAplikatzailea();
				SmoteJavi sj = sa.analisisParametros(instanciasra, classindex);
				Instances instanciasbetapro = sj.getInstancias();
				instanciasdev.setClassIndex(classindex);
				instanciasentrenamiento.setClassIndex(classindex);
				instanciasbetapro.setClassIndex(classindex);
				
				
				WekaSVM s = new WekaSVM(instanciasbetapro, classindex, instanciasdev);
				try {
					s.estimar();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

}
