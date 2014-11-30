package org.packResample;

import java.util.Random;

import klasekontatzaile.Classcounter;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;

public class Resamplea {
	public Instances datuak;
	public int classIndex = 0;

	public Resamplea(int indize, Instances datu) {
		classIndex = indize;
		datuak = datu;
		datuak.setClassIndex(indize);

	}

	public void aplikatuResample() throws Exception {

		Resample reza = new Resample();
		Random r = new Random();

		double port = 0;
		Instances auxinst = null;
		Classcounter c = new Classcounter();
		// System.out.println(datuak.classIndex());
		int diferenciamejor = datuak.numInstances();
		for (int i = 10; i <= 100; i = i + 10) {
			reza.setSampleSizePercent(i);
			reza.setInputFormat(datuak);

			Instances instanciasnuevas = Filter.useFilter(datuak, reza);
			instanciasnuevas.setClassIndex(classIndex);
			// System.out.println(instanciasnuevas.classIndex());

			int[] numerovaloresclase = c.contarClases(instanciasnuevas,
					classIndex);
//			System.out.println(numerovaloresclase);
			int diferencia = numerovaloresclase[1] - numerovaloresclase[0];
			diferencia = Math.abs(diferencia);
			if (diferencia <= diferenciamejor) {
				diferenciamejor = diferencia;
				port = i;
				auxinst = instanciasnuevas;
			}

		}
		datuak = auxinst;

	}

	public Instances getdatuak() {
		// TODO Auto-generated method stub
		return datuak;
	}

}
