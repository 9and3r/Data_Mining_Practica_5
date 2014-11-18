package datuak;

public class ScanParamsOneR {
	
	public static void main(String[] args){
		if(args.length<3){
			System.out.println("Lehenengo posizioan entrenatzeko fitxategiaren path-a sartu,");
			System.out.println("bigarrenean testeatzeko fitxategia");
			System.out.println("hirugarren posizioan klasearen posizioa, azkena bada -1 jarri");
		}else{
			FitxategiKargatzaile fitxategi;
			if(args.length<2){
				fitxategi=new FitxategiKargatzaile(args[0],-1);
			}else{
				fitxategi=new FitxategiKargatzaile(args[0],Integer.parseInt(args[2]));
			}
			OneRProba altuena=new OneRProba(0, fitxategi.getInstantziak());
			OneRProba oraingoProba;
			for(int i=1;i<fitxategi.getInstantziak().numInstances();i++){
				oraingoProba= new OneRProba(i, fitxategi.getInstantziak());
				if(oraingoProba.fmeasure>altuena.fmeasure){
					altuena=oraingoProba;
				}
				System.out.println(i);
			}
			altuena.inprimatuDatuak();
			FitxategiKargatzaile fitx2= new FitxategiKargatzaile(args[1], Integer.valueOf(args[2]));
			altuena.erabakiakHartu(fitx2.getInstantziak());
		}
	}

}
