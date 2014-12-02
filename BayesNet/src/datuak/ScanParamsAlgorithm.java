package datuak;
import java.io.FileWriter;
import java.io.PrintWriter;

import classcounter.*;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class ScanParamsAlgorithm {

	public static void main(String[] args) throws Exception {
		System.out.println(args.length);
		if(args.length<3){
			System.out.println("Bi path sartu behar dira. Lehenengoan entrenatzeko fitxategia, bigarrena klasifikatu behar den fitxategia, azkena klasearen posizioa. Azkena bada -1 sartu");
		}else{
			//CASO 1
			if(args.length==4){
			FitxategiKargatzaile f=new FitxategiKargatzaile(args[0], Integer.parseInt(args[1]));
			FitxategiKargatzaile fdev=new FitxategiKargatzaile(args[2], Integer.parseInt(args[1]));
			Instances instantziak = f.getInstantziak();
			Instances dev = fdev.getInstantziak();
			BayesNetProba oraingoa=null;
			BayesNetProba altuena=null;			
			//klase minoritarioa lortzeko kodea, klase honen fmeasure-a optimizatu nahi dugu

			Classcounter classcounter = new Classcounter();
			int[] emaitza = classcounter.contarClases(instantziak,	Integer.parseInt(args[1]) );
			int klaseMinoritarioa=0;
			for (int i=1; i<emaitza.length; i++){
				if(emaitza[i]<emaitza[klaseMinoritarioa])
					klaseMinoritarioa=i;
			}
			////////////////////////////////////
			
			for(int i=0;i<2;i++){
				if(i==0){
					altuena=konprobatuAtributuGuztiak(instantziak, true, dev, klaseMinoritarioa);
				}else{
					oraingoa=konprobatuAtributuGuztiak(instantziak, false, dev,klaseMinoritarioa);
				}
			}	
			if(oraingoa.getFmeasure()>altuena.getFmeasure()){
				altuena=oraingoa;
			}
			altuena.imprimatuEzZintzoa();
			altuena.imprimatuZintzoa();
			FitxategiKargatzaile f2=new FitxategiKargatzaile(args[2], Integer.valueOf(args[1]));
			altuena.erabakiakHartu(f2.getInstantziak()); 
			altuena.sailkatzaileaGorde(args[3]);
			
			/*
			//Importatu sortutako modeloa eta proba egin (probisionala)
			Classifier cls = (Classifier) weka.core.SerializationHelper.read(args[3]);
			Evaluation evaluator = new Evaluation(instantziak);
			evaluator.evaluateModel(cls, fdev.getInstantziak());
			System.out.println(evaluator.fMeasure(klaseMinoritarioa));
			*/}
			
			}
	}
	
	private static BayesNetProba konprobatuAtributuGuztiak(Instances instantziak,boolean pMark, Instances dev, int klaseMin){
		BayesNetProba oraingoa;
		BayesNetProba altuena;
		boolean irten=false;
		int i=1;
		altuena=new BayesNetProba(pMark, i, instantziak, dev, klaseMin);
		while(!irten && i<instantziak.numAttributes()){
			i++;
			oraingoa=new BayesNetProba(pMark, i, instantziak,dev,klaseMin);
			if(oraingoa.getFmeasure()>altuena.getFmeasure()){
				if(oraingoa.getFmeasure()-altuena.getFmeasure()<0.001){
					irten=true;
				}
				altuena=oraingoa;
			}else{
				irten=true;
			}
		}
		return altuena;
	}

}
