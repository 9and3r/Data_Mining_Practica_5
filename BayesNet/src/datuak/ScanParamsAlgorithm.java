package datuak;

import weka.core.Instances;

public class ScanParamsAlgorithm {

	public static void main(String[] args) {
		if(args.length<3){
			System.out.println("Bi path sartu behar da. Lehenengoan entrenatzeko fitxategia, bigarrena klasifikatu behar den fitxategia, azkena klasearen posizioa. Azkena bada -1 sartu");
		}else{
			FitxategiKargatzaile f=new FitxategiKargatzaile(args[0], Integer.parseInt(args[2]));
			Instances instantziak = f.getInstantziak();
			BayesNetProba oraingoa=null;
			BayesNetProba altuena=null;
			for(int i=0;i<2;i++){
				if(i==0){
					altuena=konprobatuAtributuGuztiak(instantziak, true);
				}else{
					oraingoa=konprobatuAtributuGuztiak(instantziak, false);
				}
			}	
			if(oraingoa.getFmeasure()>altuena.getFmeasure()){
				altuena=oraingoa;
			}
			altuena.imprimatuEzZintzoa();
			altuena.imprimatuZintzoa();
			FitxategiKargatzaile f2=new FitxategiKargatzaile(args[1], Integer.valueOf(args[2]));
			altuena.erabakiakHartu(f2.getInstantziak());
		}
	}
	
	private static BayesNetProba konprobatuAtributuGuztiak(Instances instantziak,boolean pMark){
		BayesNetProba oraingoa;
		BayesNetProba altuena;
		boolean irten=false;
		int i=1;
		altuena=new BayesNetProba(pMark, i, instantziak);
		while(!irten && i<instantziak.numAttributes()){
			i++;
			oraingoa=new BayesNetProba(pMark, i, instantziak);
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
