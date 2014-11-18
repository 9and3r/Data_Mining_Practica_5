package classcounter;


public class Probaclasscounter {
	
	public static void main(String[] args){
			if(args.length == 2){
				FitxategiKargatzaile fitxategia = new FitxategiKargatzaile(args[0],Integer.parseInt(args[1]));
				Classcounter klasekont = new Classcounter();
				int[] kont=klasekont.contarClases(fitxategia.getInstantziak(), Integer.parseInt(args[1]));
				for(int i=0;i<kont.length;i++){
					System.out.println(kont[i]);
				}
			}
	}
}
