package datos;

import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.SelectedTag;
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
			RemoveWithValues remove = new RemoveWithValues();
			int[] removeValues = new int[1];
			removeValues[0] = 1;
			remove.setModifyHeader(true);
			remove.setNominalIndicesArr(removeValues);
			remove.setInputFormat(instantziak);
			Filter.useFilter(instantziak, remove);
			System.out.println(instantziak.attribute(instantziak.classIndex()).numValues());
			libSVM.buildClassifier(instantziak);
			for(int i=0;i<instantziak.numInstances();i++){
				System.out.println(libSVM.classifyInstance(instantziak.instance(i)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
