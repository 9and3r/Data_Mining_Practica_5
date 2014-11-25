package Datos;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatuKargatzaile train = new DatuKargatzaile(args[0]);
		train.setKlasearenPosizioa(Integer.valueOf(args[1]));
		DatuKargatzaile dev = new DatuKargatzaile(args[2]);
		dev.setKlasearenPosizioa(Integer.valueOf(args[1]));
		NaiveBayes nb = new NaiveBayes();
		try {
			nb.buildClassifier(train.getInstantziak());
			Evaluation eval = new Evaluation(train.getInstantziak());
			eval.evaluateModel(nb, dev.getInstantziak());
			weka.core.SerializationHelper.write(args[3], nb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
