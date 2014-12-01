package data;

import java.io.File;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.OneClassClassifier;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class Main {

	public static void main(String[] args) {
		
		DatuKargatzaile dk = new DatuKargatzaile(args[0]);
		
		Instances instantziak = dk.getInstantziak();

		OneClassClassifier oneClass = new OneClassClassifier();
		try {
			instantziak.setClassIndex(instantziak.numAttributes()-1);
			dk.setKlasearenPosizioa(-1);
			oneClass.setTargetClassLabel("V1");
			oneClass.buildClassifier(instantziak);
			Evaluation eval = new Evaluation(instantziak);
			eval.evaluateModel(oneClass, instantziak);
			System.out.println(eval.toSummaryString());
			System.out.println(eval.fMeasure(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
