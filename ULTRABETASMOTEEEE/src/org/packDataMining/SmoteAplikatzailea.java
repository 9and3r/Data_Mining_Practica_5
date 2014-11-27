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
		Classcounter c2 = new Classcounter();
		
		int[] valores = c2.contarClases(instanciastrain, classindex);
		
		for (int neigs = 1; neigs <= raizden; neigs++) {

			s.setNearestNeighbours(neigs);
			
			if(valores.length == 2){
				int diferencia = Math.abs((valores[0] - valores[1]));
				int valormenor = valores[0];
				if(valores[1] < valores[0]){
					valormenor = valores[1];
				}
				double porcentajedosvalores = ((double)diferencia / (double)valormenor)*100;
				System.out.println("diferencia ----> " + diferencia);
				System.out.println("valorenor ------> " + valormenor);
				System.out.println("porcentaje ---------> " + porcentajedosvalores);
				s.setSmote(porcentajedosvalores);
				porcentajemejor = porcentajedosvalores;
				
				
				
				instanciasSMOTE = s.porcentajeNumeroClases();
				instanciasSMOTE.setClassIndex(classindex);
				
				Classcounter c = new Classcounter();
				int[] porcentajesValoresClases = c.contarClases(
						instanciasSMOTE, classindex);
				for(int i = 0; i < porcentajesValoresClases.length; i++){
					System.out.println("Clase" + i + ": " + porcentajesValoresClases[i]);
				}
				
				if (porcentajesvaloresclasesmejor == null) {
					porcentajesvaloresclasesmejor = porcentajesValoresClases;
					instanciasSMOTEmejor = instanciasSMOTE;
					neigsmejor = neigs;
				} else {

					int decision = eleccionentreporcentajesvaloresclases(
							porcentajesValoresClases,
							porcentajesvaloresclasesmejor);
//					System.out.println("decision: " + decision);
					if (decision == 1) {
						porcentajesvaloresclasesmejor = porcentajesValoresClases;
						instanciasSMOTEmejor = instanciasSMOTE;
						neigsmejor = neigs;
					}

				}

							
			}
			else{
				for (double porcentaje = 50; porcentaje <= 250; porcentaje = porcentaje + 10) {
					
					s.setSmote(porcentaje);
					
					instanciasSMOTE = s.porcentajeNumeroClases();
					instanciasSMOTE.setClassIndex(classindex);
					
					// calcular porcentajes de cada clase en las instancias
					Classcounter c = new Classcounter();
					int[] porcentajesValoresClases = c.contarClases(
							instanciasSMOTE, classindex);
					for(int i = 0; i < porcentajesValoresClases.length; i++){
						System.out.println("Clase" + i + ": " + porcentajesValoresClases[i]);
					}
				
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
//						System.out.println("decision: " + decision);
						if (decision == 1) {
							porcentajesvaloresclasesmejor = porcentajesValoresClases;
							instanciasSMOTEmejor = instanciasSMOTE;
							porcentajemejor = porcentaje;
							neigsmejor = neigs;
						}

					}

				}
			}
			
			

		}
		s.setSmote(porcentajemejor);
		s.setNearestNeighbours(neigsmejor);
		for(int h = 0; h < porcentajesvaloresclasesmejor.length; h++){
			s.setPorcentajes(s.getPorcentajes() + "\nclase" + (h+1) + ": " + porcentajesvaloresclasesmejor[h] + ";\n");
			
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

