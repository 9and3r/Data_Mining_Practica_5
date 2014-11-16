package klaseBalioakAtera;

public class Nagusia {
	public static void main(String[] args){
		if(args.length == 2){
			FitxategiKargatzaile fitxategia = new FitxategiKargatzaile(args[0],Integer.parseInt(args[1]));
			//Creo un array de ints, una casilla para cada valor de la clase
			int[] kont = new int[fitxategia.getInstantziak().classAttribute().numValues()];
			//Por cada instancia incremento el contador de ese valor de clase
			for(int i = 0;i<fitxategia.getInstantziak().numInstances();i++)
				kont[fitxategia.getInstantziak().classAttribute().indexOfValue(fitxategia.getInstantziak().instance(i).stringValue(fitxategia.getInstantziak().classIndex()))]++;
				
				//System.out.println(fitxategia.getInstantziak().classAttribute().indexOfValue(fitxategia.getInstantziak().instance(i).stringValue(fitxategia.getInstantziak().classIndex())));
				//System.out.println(fitxategia.getInstantziak().instance(i).stringValue(Integer.parseInt(args[1])));
			for(int i = 0;i<fitxategia.getInstantziak().classAttribute().numValues();i++){
				System.out.println("Number of instances with value '"+fitxategia.getInstantziak().classAttribute().value(i)+"':");
				System.out.println(kont[i]+"    "+((double)kont[i]/fitxategia.getInstantziak().numInstances()*100)+"% of the total");
			
			}
		}else{
			System.out.println("Bi parametro sartu behar dituzu: ");
			System.out.println("      1. Fitxategiaren path-a");
			System.out.println("      2. Klasearen posizioa");
		}
}
}