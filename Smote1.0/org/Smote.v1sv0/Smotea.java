package org.Smote.v1sv0;

import java.io.File;
import java.io.IOException;

import klasekontatzaile.Classcounter;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

public class Smotea {
private Instances datu;
private int indizea;
	
	public Smotea(Instances datuak, int classIndex){
		datu=datuak;
		indizea=classIndex;
		datu.setClassIndex(indizea);
	}
	
	public void aplikatuSmote() throws Exception{
		SMOTE s =  new SMOTE();
		s.setInputFormat(datu);
		Classcounter c = new Classcounter();
		int valores[]=c.contarClases(datu, indizea);
		
		int dif= Math.abs(valores[0]-valores[1]);
		int min=valores[1];
		if(valores[0]<valores[1]){
			min = valores[0];
		}
		double porc = ((double)dif*100)/(double)min;
		s.setPercentage(porc);
		
		Instances result = SMOTE.useFilter(datu, s);
		datu = result;
		
	}
	public Instances getDatuak(){
		return datu;
	}

	public void guardarARFF(Instances datuak, String ficheroSalida)throws IOException {
		// TODO Auto-generated method stub
		ArffSaver save = new ArffSaver();
		save.setInstances(datuak);
		save.setFile(new File(ficheroSalida));
		
		save.writeBatch();
		}
	}
}
