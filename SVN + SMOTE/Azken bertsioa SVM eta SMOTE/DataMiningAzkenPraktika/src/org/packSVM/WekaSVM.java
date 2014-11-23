package org.packSVM;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import weka.classifiers.meta.GridSearch;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.rules.OneR;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.core.ChebyshevDistance;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.SelectedTag;
import weka.core.Tag;
import weka.core.Utils;
import weka.core.neighboursearch.NearestNeighbourSearch;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.instance.Normalize;

public class WekaSVM {

	private Instances data;
	private int classindex;
	private Instances datadev;

	public WekaSVM(Instances entrenamiento, int pClassindex,
			Instances dev) {

		
		data = entrenamiento;
		datadev = dev;
		classindex = pClassindex;
		

	}

	public void estimar() throws Exception {

		// 1.5. Shuffle the instances: apply Randomize filter
		Random r = new Random(10);
		data.randomize(r);

		// 1.6. Specify which attribute will be used as the class
		data.setClassIndex(classindex);
		

		double fmeasureaux = 0;
		double fmeasure = 0;
		// parametroak

		double degreeoptimoa = 1;
		String kerneloptmoa = "";
		int tagKernel = 0;
		int tagTipo = 0;
		String tipooptimoa = "";

		// entrenemos el estimador con instancas de entrenamiento
		LibSVM estimador = new LibSVM();
		estimador.buildClassifier(data);

		// pasamos las instancias de entrenamiento
		Evaluation evaluator = null;
		evaluator = new Evaluation(data);
		
		//definimos los valores de los parámetros

		String[] kernelString = { "linear: u'*v",
				"Polynomial: (gamma*u'*v + coef0)^degree",
				"radial basis function: exp(-gamma*|u-v|Â²)",
				"sigmoid: tanh(gamma*u'*v + coef0)" };
		String[] tipoString = { "CSVC", "Nu-SVC", "One-class SVM",
				"Epsilon SVR", "Nu-SVR" };
		SelectedTag[] kernelak = {
				new SelectedTag(0, estimador.TAGS_KERNELTYPE),
				new SelectedTag(1, estimador.TAGS_KERNELTYPE),
				new SelectedTag(2, estimador.TAGS_KERNELTYPE),
				new SelectedTag(3, estimador.TAGS_KERNELTYPE) };
		SelectedTag[] svmtypes = { new SelectedTag(0, estimador.TAGS_SVMTYPE),
				new SelectedTag(1, estimador.TAGS_SVMTYPE),
				new SelectedTag(2, estimador.TAGS_SVMTYPE),
				new SelectedTag(3, estimador.TAGS_SVMTYPE),
				new SelectedTag(4, estimador.TAGS_SVMTYPE) };
		
		
		for (int degreea = 0; degreea <= 4; degreea++) {
			estimador.setDegree(degreea);
			// Kernel tipoak ekortuko ditugu
			for (int kernela = 0; kernela < 4; kernela++) {
				estimador.setKernelType(kernelak[kernela]);
				// SVMType ere ekortuko dugu
				for (int tipoa = 0; tipoa < 5; tipoa++) {
					estimador.setSVMType(svmtypes[tipoa]);
					
					estimador.buildClassifier(data);
					// pasamos las instancias de entrenamiento
					evaluator = new Evaluation(data);

					
					try {
						evaluator.evaluateModel(estimador, datadev);
//						evaluator.crossValidateModel(estimador, datadev, 10,
//								new Random(1));

					} catch (Exception e2) {
						System.out
								.println("Elementu honek errorea ematen du: ");
					}

					fmeasureaux = evaluator.weightedFMeasure(); // fMeasure
																// kalkulatzen
																// du
					if (fmeasureaux > fmeasure) {
						fmeasure = fmeasureaux;
						tipooptimoa = tipoString[tipoa];
						kerneloptmoa = kernelString[kernela];
						degreeoptimoa = degreea;
						tagKernel = kernela;
						tagTipo = tipoa;
						

					}
					// TODO

				}

			}
		}
		
		String emaitza = "";
		double acc = evaluator.pctCorrect();
		double inc = evaluator.pctIncorrect();
		double kappa = evaluator.kappa();
		double mae = evaluator.meanAbsoluteError();
		double rmse = evaluator.rootMeanSquaredError();
		double rae = evaluator.relativeAbsoluteError();
		double rrse = evaluator.rootRelativeSquaredError();

		emaitza = emaitza + "Correctly Classified Instances  " + acc + "\n";
	
		emaitza = emaitza + "Incorrectly Classified Instances  " + inc + "\n";
		emaitza = emaitza + "Kappa statistic  " + kappa + "\n";
		emaitza = emaitza + "Mean absolute error  " + mae + "\n";
		emaitza = emaitza + "Root mean squared error  " + rmse + "\n";
		emaitza = emaitza + "Relative absolute error  " + rae + "\n";
		emaitza = emaitza + "Root relative squared error  " + rrse + "\n";
		
		
		emaitza = emaitza + "Confusion Matrix: \n\n\n";
		
		double[][] confusionMatrix = evaluator.confusionMatrix();
		
		int zutabeKop =  confusionMatrix.length;
		int lerroKop = confusionMatrix[0].length;
		for(int i = 0; i < zutabeKop; i++){
			for(int j = 0; j < lerroKop; j++){
				emaitza = emaitza + confusionMatrix[j][i] + "\t";
			}
			emaitza = emaitza + "\n";
		}
		
		System.out.println("EMAITZA: " + emaitza);

		System.out.println(fmeasure + ";\n" + tipooptimoa + ";\n"
				+ kerneloptmoa + "; tagKernel= " + tagKernel + "; tagTipoa= "
				+ tagTipo + "; degree= " + degreeoptimoa + ";");

	}

}
