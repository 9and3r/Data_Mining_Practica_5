package SailkatzeFasea;
import java.io.FileWriter;
import java.io.PrintWriter;

import weka.classifiers.Classifier;
import weka.core.Instances;


public class SailkatzeFasea {
public static void main(String[] args) throws Exception {
	
		if(args.length>2){
			double balioAux;
			Classifier cls = (Classifier) weka.core.SerializationHelper.read(args[0]);
			FileWriter fichero = new FileWriter(args[2]);
			PrintWriter pw = new PrintWriter(fichero);
			FitxategiKargatzaileTest fTest=new FitxategiKargatzaileTest(args[1]);
			Instances inst = fTest.getInstantziak();
			if(args.length==3)inst.setClassIndex(inst.numAttributes()-1);
			if(args.length==4){
				int klasePos=Integer.parseInt(args[3]);
				inst.setClassIndex(klasePos);
			}
			for(int i=0;i<inst.numInstances();i++){
				balioAux=cls.classifyInstance(inst.instance(i));
				pw.println("Instance "+i+" classified as "+inst.classAttribute().value((int)balioAux));
			}
			pw.close();
		}
	}
}

