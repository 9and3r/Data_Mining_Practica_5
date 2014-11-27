package datuak;

import weka.core.Instances;

public class ScanParamsAlgorithm {

	public static void main(String[] args) {
		System.out.println(args.length);
		if(args.length<4){
			System.out.println("Bi path sartu behar dira. Lehenengoan entrenatzeko fitxategia, bigarrena klasifikatu behar den fitxategia, azkena klasearen posizioa. Azkena bada -1 sartu");
		}else{
			FitxategiKargatzaile f=new FitxategiKargatzaile(args[0], Integer.parseInt(args[1]));
			FitxategiKargatzaile fdev=new FitxategiKargatzaile(args[2], Integer.parseInt(args[1]));
			Instances instantziak = f.getInstantziak();
			Instances dev = fdev.getInstantziak();
			BayesNetProba oraingoa=null;
			BayesNetProba altuena=null;
			for(int i=0;i<2;i++){
				if(i==0){
					altuena=konprobatuAtributuGuztiak(instantziak, true, dev);
				}else{
					oraingoa=konprobatuAtributuGuztiak(instantziak, false, dev);
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
		}
	}
	
	private static BayesNetProba konprobatuAtributuGuztiak(Instances instantziak,boolean pMark, Instances dev){
		BayesNetProba oraingoa;
		BayesNetProba altuena;
		boolean irten=false;
		int i=1;
		altuena=new BayesNetProba(pMark, i, instantziak, dev);
		while(!irten && i<instantziak.numAttributes()){
			i++;
			oraingoa=new BayesNetProba(pMark, i, instantziak,dev);
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
