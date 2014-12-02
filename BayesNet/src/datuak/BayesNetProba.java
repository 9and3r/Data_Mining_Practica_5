package datuak;


import java.io.PrintWriter;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.estimate.SimpleEstimator;
import weka.classifiers.bayes.net.search.local.HillClimber;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ManhattanDistance;

public class BayesNetProba {
	
	private BayesNet bayesNet;
	private HillClimber searchAlgo;
	private boolean marklov;
	private int parents;
	private double fmeasure;
	private Evaluation evaluator;
	private Evaluation ebaluazioEzZintzoa;
	private Instances instantziak;
	int klaseMinoritarioa;
	

	public BayesNetProba(boolean pMarklov, int pParents,Instances train, Instances dev, int klaseMin){
		this.instantziak=train;
		this.bayesNet=new BayesNet();
		this.searchAlgo=new HillClimber();
		this.marklov=pMarklov;
		this.parents=pParents;
		this.searchAlgo.setMaxNrOfParents(parents);
		this.searchAlgo.setMarkovBlanketClassifier(marklov);
		this.bayesNet.setSearchAlgorithm(searchAlgo);
		this.bayesNet.setEstimator(new SimpleEstimator());
		this.klaseMinoritarioa=klaseMin;
		
		try {
			bayesNet.buildClassifier(instantziak);
			evaluator = new Evaluation(train);
			evaluator.evaluateModel(bayesNet, dev);
			fmeasure=evaluator.fMeasure(klaseMinoritarioa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double getFmeasure() {
		return fmeasure;
	}
	
	public void imprimatuZintzoa(){
		System.out.println("Ebaluazio Zintzoa:");
		this.imprimatu(evaluator);
	}
	
	public void imprimatuEzZintzoa(){
		try {
			System.out.println("Ebaluazio Ez Zintzoa:");
			this.ebaluazioEzZintzoa();
			this.imprimatu(ebaluazioEzZintzoa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void imprimatu(Evaluation e){
		System.out.println("F-Measure: " + e.fMeasure(klaseMinoritarioa));
		System.out.println("Parents: "+this.parents);
		System.out.println("Markov: "+this.marklov);
		System.out.println("Precission: "+e.weightedPrecision());
		System.out.println("Recall: "+e.weightedRecall());
		this.imprimatuMatrizea(e);
		System.out.println("________________________________");
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
	
	
	
	private void ebaluazioEzZintzoa(){
		try {
			ebaluazioEzZintzoa=new Evaluation(instantziak);
			ebaluazioEzZintzoa.evaluateModel(bayesNet, instantziak);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void erabakiakHartu(Instances erabakitzeko){
		try {
			PrintWriter writer = new PrintWriter("emaitzaBayesNet", "UTF-8");
			for(int i=0;i<erabakitzeko.numInstances();i++){
				Instance instantzia=erabakitzeko.instance(i);
				double emaitza=bayesNet.classifyInstance(instantzia);
				String emaitzaString=erabakitzeko.classAttribute().value((int) emaitza);
				writer.println(emaitzaString);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sailkatzaileaGorde(String fitxIzena){
		try {
			weka.core.SerializationHelper.write(fitxIzena, bayesNet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
