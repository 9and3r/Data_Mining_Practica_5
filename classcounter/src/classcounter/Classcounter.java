package classcounter;

import weka.core.Instances;

public class Classcounter {
	public Classcounter(){}
	
	public int[] contarClases(Instances instantziak, int klasePos){
		if(klasePos==-1)
			instantziak.setClassIndex(instantziak.numAttributes()-1);
		else
			instantziak.setClassIndex(klasePos);
		
		//Creo un array de ints, una casilla para cada valor de la clase
		int[] kont = new int[instantziak.classAttribute().numValues()];
		//Por cada instancia incremento el contador de ese valor de clase
		for(int i = 0;i<instantziak.numInstances();i++)
			kont[(instantziak.instance(i).stringValue(klasePos))]++;
		//kont[instantziak.classAttribute().indexOfValue(instantziak.instance(i).stringValue(instantziak.classIndex()))]++;
		
		for(int i = 0;i<instantziak.classAttribute().numValues();i++){
			System.out.println("Number of instances with value '"+instantziak.classAttribute().value(i)+"':");
			System.out.println(kont[i]+"    "+((double)kont[i]/instantziak.numInstances()*100)+"% of the total");
		}
		return kont;
}
}
