package pack;

import java.util.ArrayList;

import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class RanFor {
	private static RanFor nRanFor;
	private Instances train;
	private Instances dev;
	private RandomForest RFKlasifikatzailea;
	private static int[] parametroak; // depth, tree, feature

	private RanFor() {
		train = null;
		dev = null;
		RFKlasifikatzailea = null;
	}

	public static RanFor getNRanFor() {
		if (nRanFor == null) {
			nRanFor = new RanFor();
			parametroak = new int[3];
		}
		return nRanFor;
	}

	public void kargatuInstantziak(String pathTrain, String pClassIndex,
			String pathDev) {
		DatuKargatzailea dK = new DatuKargatzailea(pathTrain);
		train = dK.emonInstantziak();
		System.out.println("Train cargado: " + train.numInstances()
				+ " instancias");
		dK = new DatuKargatzailea(pathDev);
		dev = dK.emonInstantziak();
		dK = null;
		System.out
				.println("Dev cargado: " + dev.numInstances() + " instancias");
		if (pClassIndex.equalsIgnoreCase("-1")) {
			train.setClassIndex(train.numAttributes() - 1);
			dev.setClassIndex(train.numAttributes() - 1);

		} else {
			train.setClassIndex(Integer.parseInt(pClassIndex));
			dev.setClassIndex(Integer.parseInt(pClassIndex));
		}

	}

	public void parametroOptimoakLortu() {
		System.out.println("Numero de atributos: " + train.numAttributes()
				+ "\n");
		System.out.println("Calculando los parametros optimos para:");
		System.out.println(" -Nivel de profundidad:");
		int depthOpt = depthOpt();
		System.out.println(" -cantidad de arboles:");
		int numTreeOpt = numTreeOpt(depthOpt);
		System.out.println(" -cantidad de features:");
		int numFeatOpt = numFeatOpt(depthOpt, numTreeOpt);
		parametroak[0] = depthOpt;
		parametroak[1] = numTreeOpt;
		parametroak[2] = numFeatOpt;

	}

	private int depthOpt() {
		double errorTxikiena = 1;
		double errorEgungoa = 1;
		int depthEgungoa = 1;
		int depthOptimoa = 0;
		boolean dif = false;
		boolean lehengoBuelta = true;
		while (depthEgungoa < 10 && !dif) {
			errorEgungoa = klasifikatzaileaEraiki(10, depthEgungoa, 0);
			System.out.print(".");
			if (errorEgungoa < errorTxikiena) {
				errorTxikiena = errorEgungoa;
				depthOptimoa = depthEgungoa;
			}
			if (((errorEgungoa - errorTxikiena) < 0.0001) && !lehengoBuelta) {
				dif = true;
			}
			depthEgungoa = depthEgungoa + 1;
			lehengoBuelta = false;

		}
		System.out.println("   -->valor optimo:: " + depthOptimoa);
		return depthOptimoa;
	}

	private int numTreeOpt(int depthOpt) {
		double errorTxikiena = 1;
		double errorEgungoa = 1;
		int numTrees = 0;
		int numTreesOptimoa = 0;
		boolean dif = false;
		while (numTrees < 50 && !dif) {
			errorEgungoa = klasifikatzaileaEraiki(numTrees, depthOpt, 0);
			System.out.print(".");
			if (errorEgungoa < errorTxikiena) {
				errorTxikiena = errorEgungoa;
				numTreesOptimoa = numTrees;
			} else {
				if ((errorEgungoa - errorTxikiena) < 0.0001) {
					dif = true;
				}
			}
			numTrees = numTrees + 5;
		}
		numTreesOptimoa = numTreeOptPro(depthOpt, numTreesOptimoa - 5);
		System.out.println("   -->valor optimo:: " + numTreesOptimoa);
		return numTreesOptimoa;
	}

	private int numTreeOptPro(int depthOpt, int numTreesIni) {
		double errorTxikiena = 1;
		double errorEgungoa = 1;
		int numTrees = numTreesIni;
		int numTreesOptimoa = 0;
		boolean dif = false;
		while (numTrees < (numTreesIni + 10) && !dif) {
			errorEgungoa = klasifikatzaileaEraiki(numTrees, depthOpt, 0);
			System.out.print(".");
			if (errorEgungoa < errorTxikiena) {
				errorTxikiena = errorEgungoa;
				numTreesOptimoa = numTrees;
			} else {
				if ((errorEgungoa - errorTxikiena) < 0.0001) {
					dif = true;
				}
			}
			numTrees++;
		}
		return numTreesOptimoa;
	}

	private int numFeatOpt(int depthOpt, int numTrees) {
		double errorTxikiena = 1;
		double errorEgungoa = 1;
		int numFeatOptimoa = -2;
		int numFeatEgungo = 1;
		boolean dif = false;
		while (numFeatEgungo < (train.numAttributes()) && !dif) {
			errorEgungoa = klasifikatzaileaEraiki(numTrees, depthOpt,
					numFeatEgungo);
			System.out.print(".");
			if (errorEgungoa < errorTxikiena) {
				errorTxikiena = errorEgungoa;
				numFeatOptimoa = numFeatEgungo;
			} else {
				if ((errorEgungoa - errorTxikiena) < 0.000000001) {
					dif = true;
				}
			}
			numFeatEgungo++;
		}
		System.out.println("  -->valor optimo: " + numFeatOptimoa);
		return numFeatOptimoa;
	}

	private double klasifikatzaileaEraiki(int numTrees, int depth, int features) {
		RFKlasifikatzailea = new RandomForest();
		RFKlasifikatzailea.setNumTrees(numTrees);
		RFKlasifikatzailea.setMaxDepth(depth);
		RFKlasifikatzailea.setNumFeatures(features);
		try {
			RFKlasifikatzailea.buildClassifier(train);
		} catch (Exception e) {
			System.out.println("ERROR: klasifikatzailea sortzean");
			e.printStackTrace();
		}
		return RFKlasifikatzailea.measureOutOfBagError();
	}

	private RandomForest KlasifikatzaileOptimoaEraiki() {
		RFKlasifikatzailea = new RandomForest();
		RFKlasifikatzailea.setMaxDepth(parametroak[0]);
		RFKlasifikatzailea.setNumTrees(parametroak[1]);
		RFKlasifikatzailea.setNumFeatures(parametroak[2]);
		try {
			RFKlasifikatzailea.buildClassifier(trainDev());
		} catch (Exception e) {
			System.out
					.println("ERROR: No se ha podido crear el clasificador optimo");
			e.printStackTrace();
		}
		return RFKlasifikatzailea;

	}

	private Instances trainDev() {
		Instances aux = train;
		for (int i = 0; i < dev.numInstances(); i++) {
			aux.add(dev.instance(i));
		}
		return aux;
	}

	public void exportMdl(String path) {
		System.out
				.println("Creando Clasificador RandomForest de las instancias Train y dev, con los parametros optimos anteriormente calculados.");
		System.out
				.println("Numero de Instancias usadas para el entrenamiento del modelo: "
						+ trainDev().numInstances());
		System.out
				.println("Numero de Atributos usados para el entrenamiento del modelo: "
						+ train.numAttributes());
		RandomForest rf = KlasifikatzaileOptimoaEraiki();
		try {
			weka.core.SerializationHelper.write(path, rf);
		} catch (Exception e) {
			System.out
					.println("ERROR: no se ha logrado exportar el clasificador");
			e.printStackTrace();
		}

		System.out
				.println("\n\n Hecho, clasificador exportado con formato mdl en: "
						+ path);
	}

	public void kargatuModeloa(String arg) {
		try {
			RFKlasifikatzailea = (RandomForest) weka.core.SerializationHelper
					.read(arg);
		} catch (Exception e) {
			System.out
					.println("ERROR: no se ha podido cargar el cls, comprueba si el path esta bien escrito: "
							+ arg);
			System.exit(0);
		}
	}

	public ArrayList<String> klasifikatuTest(String pathTest) {
		DatuKargatzailea dK = new DatuKargatzailea(pathTest);
		Instances unlabeled = dK.emonInstantziak();
		unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
		ArrayList<String> klaseak = new ArrayList<>();
		for (int i = 0; i < unlabeled.numInstances(); i++) {
			double clas = 0.0;
			try {
				clas = RFKlasifikatzailea.classifyInstance(unlabeled
						.instance(i));
			} catch (Exception e) {
				System.out
						.println("ERROR: no se han podido clasificar las instancias");
				e.printStackTrace();
			}
			klaseak.add(i, String.valueOf(clas));
		}
		return klaseak;
	}

}
