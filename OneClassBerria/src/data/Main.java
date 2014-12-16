package data;


import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.OneClassClassifier;
import weka.core.Instances;

public class Main {

	public static void main(String[] args) {
		if (args.length != 4){
			System.out.println("Error en los parametros:\n1-Path fichero train\n2-Posicion de la clase (En caso de indicar -1 sera el ultimo)\n3-Path fichero dev\n4-Path donde se creara modelo");
		}else{
			DatuKargatzaile dk = new DatuKargatzaile(args[0]);
			
			Instances instantziak = dk.getInstantziak();
			DatuKargatzaile dkdev = new DatuKargatzaile(args[2]);
			Instances dev = dkdev.getInstantziak();
			for (int i=0; i<dev.numInstances();i++){
				instantziak.add(dev.get(i));
			}
			OneClassClassifier oneClass = new OneClassClassifier();
			try {
				Classcounter counter = new Classcounter();
				int[] numClass = counter.contarClases(instantziak, Integer.valueOf(args[1]));
				int classIndex = Integer.valueOf(args[1]);
				if (classIndex==-1){
					classIndex = instantziak.numAttributes()-1;
				}
				instantziak.setClassIndex(classIndex);
				dev.setClassIndex(classIndex);
				int claseMayoritariaInidice = lortuAltuena(numClass);
				String claseMayoritaria = instantziak.classAttribute().value(claseMayoritariaInidice);
				oneClass.setTargetClassLabel(claseMayoritaria);
				oneClass.buildClassifier(instantziak);
				Evaluation eval = new Evaluation(instantziak);
				eval.evaluateModel(oneClass, dev);
				System.out.println(eval.toSummaryString());
				System.out.println("F-Measure: "+eval.fMeasure(claseMayoritariaInidice));
				weka.core.SerializationHelper.write(args[3], oneClass);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static int lortuAltuena(int[] klaseak){
		int altuena = 0;
		int valor = klaseak[0];
		for(int i=0; i<klaseak.length; i++){
			if (klaseak[i] > valor){
				valor = klaseak[i];
				altuena = i;
			}
		}
		return altuena;
		
	}

}
