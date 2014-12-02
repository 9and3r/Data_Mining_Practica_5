package SailkatzeFasea;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.ProtectedProperties;
import weka.core.converters.ArffSaver;


public class SailkatzeFasea {
public static void main(String[] args) throws Exception {
	/***************************************
	 Argumentos
		arg[0] = Path cls (clasificador)
		args[1] = Path fichero test
		args[2] = Path fichero salida
		args[3] = nombre nuevo atributo
		args[4] = klase posizioa
	 ****************************************/
		if(args.length>3){
			double balioAux;
			Classifier cls = (Classifier) weka.core.SerializationHelper.read(args[0]);
			//FileWriter fichero = new FileWriter(args[2]);
			//PrintWriter pw = new PrintWriter(fichero);
			FitxategiKargatzaileTest fTest=new FitxategiKargatzaileTest(args[1]);
			FitxategiKargatzaileTest fAcumulado=new FitxategiKargatzaileTest(args[2]);
			Instances inst = fTest.getInstantziak();
			Instances instAcum = fAcumulado.getInstantziak();
			if(args.length==4){
				inst.setClassIndex(inst.numAttributes()-1);
				instAcum.setClassIndex(inst.numAttributes()-1);
				
			}
			if(args.length==5){
				int klasePos=Integer.parseInt(args[4]);
				inst.setClassIndex(klasePos);
				instAcum.setClassIndex(klasePos);
			}
			////////ARFF SORTU
			Enumeration balioak = instAcum.classAttribute().enumerateValues();
			List<String> list = Collections.list(balioak);
			FastVector vector = new FastVector();
			Iterator<String> ite = list.iterator();
			while(ite.hasNext())
				vector.addElement(ite.next());
			Attribute berria = new Attribute(args[3], vector);
			inst.insertAttributeAt(berria, inst.numAttributes());
			for(int i=0;i<inst.numInstances();i++){
				balioAux=cls.classifyInstance(inst.instance(i));
				instAcum.instance(i).setValue(inst.numAttributes()-1, inst.classAttribute().value((int)balioAux));
			}
			ArffSaver saver = new ArffSaver();
			saver.setInstances(instAcum);
			saver.setFile(new File(args[2]));			
			saver.writeBatch();
		}else{
			System.out.println("Parametroak falta dira");
		}
	}
}

