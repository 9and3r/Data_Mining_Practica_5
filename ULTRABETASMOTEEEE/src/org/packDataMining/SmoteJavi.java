package org.packDataMining;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import classcounter.Classcounter;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

public class SmoteJavi {

	private int nearestNeighbours;
	private double smote;
	

	private Instances instancias;
	private String porcentajes;
	// private String nombreFicheroDestino;
	private int classindex;

	public SmoteJavi(int pClassIndex, int pNearestNeighbours, double pSmote,
			Instances pInstancias) {
		porcentajes = "";
		nearestNeighbours = pNearestNeighbours;
		smote = pSmote;
		instancias = pInstancias;
		// nombreFicheroDestino = pNFDestino;
		classindex = pClassIndex;
		instancias.setClassIndex(classindex);
	}

	public SmoteJavi(Instances instanciasEntrada, int classindex2) {
		porcentajes = "";
		instancias = instanciasEntrada;
		classindex = classindex2;
		instancias.setClassIndex(classindex);
		// TODO Auto-generated constructor stub
	}

	/*
	 * calculara el smote con los parametros specificados, mostrando por
	 * pantalla el resultado
	 */
	public Instances porcentajeNumeroClases() throws Exception {

		instancias.setClassIndex(classindex);

//		int indiceclaseminoritaria = 0;
//		Classcounter c = new Classcounter();
//		int[] vals = c.contarClases(instancias, classindex);
//		int aux = 0;
//		for(int i = 0; i < vals.length; i++){
//			if(vals[i] <= aux){
//				aux = vals[i];
//				indiceclaseminoritaria = i;
//			}
//		}
		
		
		SMOTE s = new SMOTE();
//		s.setClassValue(""+indiceclaseminoritaria);
//		System.out.println("------------------------------------------  " + indiceclaseminoritaria);
		s.setNearestNeighbors(nearestNeighbours);
		s.setPercentage(smote);
		s.setInputFormat(instancias);
		//s.batchFinished();
		
		//s.doSMOTE();		
		
//		System.out.println("porcentaje: " + s.getPercentage());
//		System.out.println("neigs: " + s.getNearestNeighbors());		
		
		Instances instanciasNuevas = Filter.useFilter(instancias, s);
//		System.out.println("instancias: " + instanciasNuevas.numInstances());
		
//		Instances instanciasNuevas = s.getOutputFormat();
		instanciasNuevas.setClassIndex(classindex);
		//instancias = instanciasNuevas;
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

//	public void analisisParametros(Instances instanciastrain, int classindex)
//			throws Exception {
//		// bateria de esperimentos:
//		// nearestneighbiurs -> [1, raiz de n]
//		// porcentaje -> [50, 100] n saltos de 10
//
//		Instances instanciasSMOTE = null;
//		Instances instanciasSMOTEmejor = null;
//		double porcentajemejor = 50;
//		int neigsmejor = 1;
//		Smote s = new Smote(instanciastrain, classindex);
//		int[] porcentajesvaloresclasesmejor = null;
//
//		int raizden = (int) Math.sqrt(instanciastrain.numInstances());
//		for (int neigs = 1; neigs <= raizden; neigs++) {
//
//			for (double porcentaje = 50; porcentaje <= 100; porcentaje = porcentaje + 10) {
//				
//				s.smote = porcentaje;
//				s.nearestNeighbours = neigs;
//				
//				instanciasSMOTE = s.porcentajeNumeroClases();
//				instanciasSMOTE.setClassIndex(classindex);
//				
//				// calcular porcentajes de cada clase en las instancias
//				Classcounter c = new Classcounter();
//				int[] porcentajesValoresClases = c.contarClases(
//						instanciasSMOTE, classindex);
//				System.out.println(porcentajesValoresClases[0]);
//				System.out.println(porcentajesValoresClases[1]);
//				System.out.println(porcentajesValoresClases[2]);
//				System.out.println(porcentajesValoresClases[3]);
//				System.out.println(porcentajesValoresClases[4]);
//				System.out.println(porcentajesValoresClases[5]);
//				System.out.println(porcentajesValoresClases[6]);
//				System.out.println("\n\n\n");
//
//
//				// comparar para saber cual es el mejor
//				if (porcentajesvaloresclasesmejor == null) {
//					porcentajesvaloresclasesmejor = porcentajesValoresClases;
//					instanciasSMOTEmejor = instanciasSMOTE;
//					porcentajemejor = porcentaje;
//					neigsmejor = neigs;
//				} else {
//
//					int decision = eleccionentreporcentajesvaloresclases(
//							porcentajesValoresClases,
//							porcentajesvaloresclasesmejor);
//					System.out.println("decision: " + decision);
//					if (decision == 1) {
//						porcentajesvaloresclasesmejor = porcentajesValoresClases;
//						instanciasSMOTEmejor = instanciasSMOTE;
//					}
//
//				}
//
//			}
//
//		}
//		this.smote = porcentajemejor;
//		this.nearestNeighbours = neigsmejor;
//		for(int h = 0; h < porcentajesvaloresclasesmejor.length; h++){
//			porcentajes = porcentajes + "clase" + (h+1) + ": " + porcentajesvaloresclasesmejor[h] + ";\n";
//			
//		}
//		this.instancias = instanciasSMOTEmejor;
//		
//	}
//
//	private int eleccionentreporcentajesvaloresclases(int[] pvc1, int[] pvc2) {
//
//		double resultado1 = ponderacion(pvc1);
//		double resultado2 = ponderacion(pvc2);
//		System.out.println("resultado1: " + resultado1);
//		System.out.println("resultado2: " + resultado2);
//		if (resultado1 < resultado2) {
//			return 1;
//		} else {
//			return 2;
//		}
//
//	}
//
//	private double ponderacion(int[] pvc1) {
//		double resultado = 0;
//		int acumulado = 0;
//
//		for (int i = 0; i < pvc1.length; i++) {
//			int v = pvc1[i];
//			for (int j = 0; j < pvc1.length; j++) {
//				if (j != i) {
//					acumulado = acumulado + Math.abs((v - pvc1[j]));
//				}
//
//			}
//
//		}
//		resultado = (double) acumulado / pvc1.length;
//		return resultado;
//	}

	
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

	public int getClassindex() {
		return classindex;
	}

	public void setClassindex(int classindex) {
		this.classindex = classindex;
	}

	public void setNearestNeighbours(int nearestNeighbours) {
		this.nearestNeighbours = nearestNeighbours;
	}

	public void setSmote(double smote) {
		this.smote = smote;
	}

	public void setInstancias(Instances instancias) {
		this.instancias = instancias;
	}

	public void setPorcentajes(String porcentajes) {
		this.porcentajes = porcentajes;
	}
	


	
	
	
}
