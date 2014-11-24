package org.packDataMining;

import weka.core.Instances;
import classcounter.Classcounter;

public class SmoteAplikatzailea {
	
	public SmoteAplikatzailea(){
		
	}
	
	
	public SmoteJavi analisisParametros(Instances instanciastrain, int classindex)
			throws Exception {
		// bateria de esperimentos:
		// nearestneighbiurs -> [1, raiz de n]
		// porcentaje -> [50, 100] n saltos de 10

		Instances instanciasSMOTE = null;
		Instances instanciasSMOTEmejor = null;
		double porcentajemejor = 50;
		int neigsmejor = 1;
		SmoteJavi s = new SmoteJavi(instanciastrain, classindex);
		int[] porcentajesvaloresclasesmejor = null;

		int raizden = (int) Math.sqrt(instanciastrain.numInstances());
		for (int neigs = 1; neigs <= raizden; neigs++) {

			for (double porcentaje = 50; porcentaje <= 100; porcentaje = porcentaje + 10) {
				
				s.setSmote(porcentaje);
				s.setNearestNeighbours(neigs);
				
				instanciasSMOTE = s.porcentajeNumeroClases();
				instanciasSMOTE.setClassIndex(classindex);
				
				// calcular porcentajes de cada clase en las instancias
				Classcounter c = new Classcounter();
				int[] porcentajesValoresClases = c.contarClases(
						instanciasSMOTE, classindex);
				System.out.println(porcentajesValoresClases[0]);
				System.out.println(porcentajesValoresClases[1]);
				System.out.println(porcentajesValoresClases[2]);
				System.out.println(porcentajesValoresClases[3]);
				System.out.println(porcentajesValoresClases[4]);
				System.out.println(porcentajesValoresClases[5]);
				System.out.println(porcentajesValoresClases[6]);
				System.out.println("\n\n\n");


				// comparar para saber cual es el mejor
				if (porcentajesvaloresclasesmejor == null) {
					porcentajesvaloresclasesmejor = porcentajesValoresClases;
					instanciasSMOTEmejor = instanciasSMOTE;
					porcentajemejor = porcentaje;
					neigsmejor = neigs;
				} else {

					int decision = eleccionentreporcentajesvaloresclases(
							porcentajesValoresClases,
							porcentajesvaloresclasesmejor);
					System.out.println("decision: " + decision);
					if (decision == 1) {
						porcentajesvaloresclasesmejor = porcentajesValoresClases;
						instanciasSMOTEmejor = instanciasSMOTE;
					}

				}

			}

		}
		s.setSmote(porcentajemejor);
		s.setNearestNeighbours(neigsmejor);
		for(int h = 0; h < porcentajesvaloresclasesmejor.length; h++){
			s.setPorcentajes(s.getPorcentajes() + "clase" + (h+1) + ": " + porcentajesvaloresclasesmejor[h] + ";\n");
			
		}
		s.setInstancias(instanciasSMOTEmejor);
		return s;
		
	}

	private int eleccionentreporcentajesvaloresclases(int[] pvc1, int[] pvc2) {

		double resultado1 = ponderacion(pvc1);
		double resultado2 = ponderacion(pvc2);
		System.out.println("resultado1: " + resultado1);
		System.out.println("resultado2: " + resultado2);
		if (resultado1 < resultado2) {
			return 1;
		} else {
			return 2;
		}

	}

	private double ponderacion(int[] pvc1) {
		double resultado = 0;
		int acumulado = 0;

		for (int i = 0; i < pvc1.length; i++) {
			int v = pvc1[i];
			for (int j = 0; j < pvc1.length; j++) {
				if (j != i) {
					acumulado = acumulado + Math.abs((v - pvc1[j]));
				}

			}

		}
		resultado = (double) acumulado / pvc1.length;
		return resultado;
	}


}

