package datos;

import java.io.File;

import sun.rmi.transport.LiveRef;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class Main {

	public static void main(String[] args) {
		
		DatuKargatzaile dk = new DatuKargatzaile(args[0]);
		dk.setKlasearenPosizioa(-1);
		LibSVM libSVM = new LibSVM();
		SelectedTag selectedTag = new SelectedTag(LibSVM.SVMTYPE_ONE_CLASS_SVM, LibSVM.TAGS_SVMTYPE);
		libSVM.setSVMType(selectedTag);
		try {
			Instances instantziak = dk.getInstantziak();
			//Instances instantziak2 = new Instances(instantziak);
			RemoveWithValues remove = new RemoveWithValues();
			int[] removeValues = new int[1];
			removeValues[0] = 0;
			remove.setModifyHeader(true);
			remove.setNominalIndicesArr(removeValues);
			remove.setInputFormat(instantziak);
			
			instantziak = Filter.useFilter(instantziak, remove);
			instantziak.setClassIndex(instantziak.numAttributes()-1);
			libSVM.setNormalize(true);
			libSVM.buildClassifier(instantziak);
			for(int i=0;i<instantziak.numInstances();i++){
				System.out.println(libSVM.distributionForInstance(instantziak.get(i))[0]);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
