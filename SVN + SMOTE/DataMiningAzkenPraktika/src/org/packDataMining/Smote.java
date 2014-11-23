package org.packDataMining;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import classcounter.Classcounter;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

public class Smote {

	private int nearestNeighbours;
	private double smote;
	

	private Instances instancias;
	private String porcentajes;
	// private String nombreFicheroDestino;
	private int classindex;

	public Smote(int pClassIndex, int pNearestNeighbours, double pSmote,
			Instances pInstancias) {
		porcentajes = "";
		nearestNeighbours = pNearestNeighbours;
		smote = pSmote;
		instancias = pInstancias;
		// nombreFicheroDestino = pNFDestino;
		classindex = pClassIndex;
	}

	public Smote(Instances instanciasEntrada, int classindex2) {
		instancias = instanciasEntrada;
		classindex = classindex2;
		// TODO Auto-generated constructor stub
	}

	/*
	 * calculara el smote con los parametros specificados, mostrando por
	 * pantalla el resultado
	 */
	public Instances porcentajeNumeroClases() throws Exception {

		instancias.setClassIndex(classindex);

		SMOTE s = new SMOTE();
		s.setNearestNeighbors(nearestNeighbours);
		s.setPercentage(smote);
		s.setInputFormat(instancias);
		Instances instanciasNuevas = Filter.useFilter(instancias, s);
		// guardarARFF(instanciasNuevas, nombreFicheroDestino);
		return instanciasNuevas;
	}

	public void guardarARFF(Instances instanciasNuevas, String nombreFichero)
			throws IOException {

		ArffSaver saver = new ArffSaver();
		saver.setInstances(instancias);
		saver.setFile(new File(nombreFichero));
		saver.setDestination(new File(nombreFichero)); // **not** necessary in
														// 3.5.4 and later
		saver.writeBatch();

	}

	public void analisisParametros(Instances instanciastrain, int classindex)
			throws Exception {
		// bateria de esperimentos:
		// nearestneighbiurs -> [1, raiz de n]
		// porcentaje -> [50, 100] n saltos de 10

		Instances instanciasSMOTE = null;
		Instances instanciasSMOTEmejor = null;
		double porcentajemejor = 50;
		int neigsmejor = 1;
		Smote s = null;
		int[] porcentajesvaloresclasesmejor = null;

		int raizden = (int) Math.sqrt(instanciastrain.numInstances());
		for (int neigs = 1; neigs <= raizden; neigs++) {

			for (double porcentaje = 50; porcentaje <= 100; porcentaje = porcentaje + 10) {

				s = new Smote(classindex, neigs, porcentaje, instanciastrain);

				instanciasSMOTE = s.porcentajeNumeroClases();

				// calcular porcentajes de cada clase en las instancias
				Classcounter c = new Classcounter();
				int[] porcentajesValoresClases = c.contarClases(
						instanciasSMOTE, classindex);

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
					if (decision == 1) {
						porcentajesvaloresclasesmejor = porcentajesValoresClases;
						instanciasSMOTEmejor = instanciasSMOTE;
					}

				}

			}

		}
		this.smote = porcentajemejor;
		this.nearestNeighbours = neigsmejor;
		for(int h = 0; h < porcentajesvaloresclasesmejor.length; h++){
			porcentajes = porcentajes + "clase" + (h+1) + ": " + porcentajesvaloresclasesmejor[h] + ";\n";
			
		}
		this.instancias = instanciasSMOTEmejor;
		
	}

	private int eleccionentreporcentajesvaloresclases(int[] pvc1, int[] pvc2) {

		double resultado1 = ponderacion(pvc1);
		double resultado2 = ponderacion(pvc2);
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

	
	public int getNearestNeighbours() {
		return nearestNeighbours;
	}

	
	public double getSmote() {
		return smote;
	}

	

	public Instances getInstancias() {
		return instancias;
	}



	public String getPorcentajes() {
		return porcentajes;
	}

	
	
	
}
