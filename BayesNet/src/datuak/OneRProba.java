package datuak;


import java.io.PrintWriter;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.rules.OneR;
import weka.core.Instance;
import weka.core.Instances;

public class OneRProba {
	
	private int bucket;
	private OneR oneR;
	double fmeasure;
	private Evaluation ebaluazioEzZintzoa;
	Instances instantziak;
	private Evaluation evaluator;
	
	public OneRProba(int pBucket, Instances pInstantziak){
		this.bucket=pBucket;
		this.oneR=new OneR();
		this.oneR.setMinBucketSize(bucket);
		instantziak=pInstantziak;
		try {
			oneR.buildClassifier(instantziak);
			evaluator = new Evaluation(instantziak);
			evaluator.crossValidateModel(oneR, instantziak, 5, new Random(1));
			fmeasure=evaluator.weightedFMeasure();
			this.ebaluazioEzZintzoa();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getBucket() {
		return bucket;
	}

	public double getFmeasure() {
		return fmeasure;
	}
	
	private void ebaluazioEzZintzoa(){
		try {
			ebaluazioEzZintzoa=new Evaluation(instantziak);
			ebaluazioEzZintzoa.evaluateModel(oneR, instantziak);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inprimatuDatuak(){
		System.out.println("Ebaluazio zintzoa One R bidez lortutako emaitzak:");
		System.out.println("F-measure: "+this.fmeasure);
		System.out.println("Bucket: "+this.bucket);
		System.out.println("Precission: "+evaluator.weightedPrecision());
		System.out.println("Recall: "+evaluator.weightedRecall());
		this.imprimatuMatrizea(evaluator);
		System.out.println("----------------------------------------------");
		System.out.println("Ebaluazio ez zintzoa One R bidez lortutako emaitzak:");
		System.out.println("F-measure: "+ebaluazioEzZintzoa.weightedFMeasure());
		System.out.println("Bucket: "+this.bucket);
		System.out.println("Precission: "+ebaluazioEzZintzoa.weightedPrecision());
		System.out.println("Recall: "+ebaluazioEzZintzoa.weightedRecall());
		this.imprimatuMatrizea(ebaluazioEzZintzoa);
	}
	
	public void erabakiakHartu(Instances erabakitzeko){
		try {
			PrintWriter writer = new PrintWriter("emaitzaOneR.txt", "UTF-8");
			for(int i=0;i<erabakitzeko.numInstances();i++){
				Instance instantzia=erabakitzeko.instance(i);
				double emaitza=oneR.classifyInstance(instantzia);
				writer.println(erabakitzeko.classAttribute().value((int) emaitza));
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimatuMatrizea(Evaluation e){
		String lerroa;
		double[][] confMatrix = e.confusionMatrix();
		for (int i=0; i<confMatrix.length;i++){
			lerroa = "";
			for (int j=0;j<confMatrix[i].length;j++){
				lerroa= lerroa +" | "+String.valueOf(confMatrix[i][j]);
			}
			System.out.println(lerroa);
		}
	}
	

}
