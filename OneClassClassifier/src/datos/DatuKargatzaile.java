package datos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class DatuKargatzaile {
	private FileReader fitxategia;
	private boolean ondoKargatu;
	private Instances instantziak;

	/**
	 * Fitxategia hartu eta instantziak kargatuko ditu
	 * @param path ARFF fitxategiaren path-a
	 */
	public DatuKargatzaile(String path){
		ondoKargatu=false;
		try {
			this.fitxategia=new FileReader(path);
			this.instantziak=new Instances(this.fitxategia);
			this.fitxategia.close();
			ondoKargatu=true;
		} catch (FileNotFoundException e) {
			System.out.println("Fitxategia ez da aurkitu: " + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Klasea ezartzen du
	 * @param pKlasePos Klasearen indizea
	 */
	public void setKlasearenPosizioa(int pKlasePos){
		if(pKlasePos < 0){
			pKlasePos = this.instantziak.numAttributes()-1;
		}
		this.instantziak.setClassIndex(pKlasePos);
	}
	
	
	/**
	 * 
	 * @return fitxategia ondo kargatu den
	 */
	public boolean isOndoKargatu() {
		return ondoKargatu;
	}
	/**
	 * 
	 * @return Kargatutako instantziak
	 */
	public Instances getInstantziak(){
		return this.instantziak;
	}
}
