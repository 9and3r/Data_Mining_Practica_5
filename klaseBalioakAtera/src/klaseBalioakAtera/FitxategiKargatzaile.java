package klaseBalioakAtera;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import weka.core.Instances;

public class FitxategiKargatzaile {
	
	private FileReader fitxategia;
	private boolean ondoKargatu;
	private Instances instantziak;

	public FitxategiKargatzaile(String path, int pKlasePos){
		ondoKargatu=false;
		try {
			this.fitxategia=new FileReader(path);
			this.instantziak=new Instances(this.fitxategia);
			// 1.6. Specify which attribute will be used as the class: the last one, in this case
			if(pKlasePos!=-1){
				this.instantziak.setClassIndex(pKlasePos);
			}else{
				this.instantziak.setClassIndex(this.instantziak.numAttributes()-1);
			}
			this.fitxategia.close();
			ondoKargatu=true;
		} catch (FileNotFoundException e) {
			System.out.println("Fitxategia ez da aurkitu: ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isOndoKargatu() {
		return ondoKargatu;
	}
	
	public Instances getInstantziak(){
		return this.instantziak;
	}
	
	
	
}

