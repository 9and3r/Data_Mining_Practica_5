package EmaitzakKudeatu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import weka.core.Instances;


public class EmaitzakKudeatu {
	/**
	 * Sarrera: -arff bat emaitzekin
	 * 			- int bat kontuan hartu beharreko atributo kopuruarekin 
	 * 			- emaitzen url
	 * 			
	 * @throws IOException 
	 * 
	 *
	 * 
	 * **/
	public static void main(String[] args) throws IOException {
				if(args.length==3){
					FileWriter fichero = new FileWriter(args[2]);
		            PrintWriter pw = new PrintWriter(fichero);
					FitxategiKargatzaileTest fitxategi = new FitxategiKargatzaileTest(args[0]);
					Instances inst = fitxategi.getInstantziak();
					//Instances berria = null;
					int atribKop = Integer.parseInt(args[1]);
					int[] votacion = new int[atribKop];
					for(int k=1;k<votacion.length;k++){
						votacion[k]=0;
						}
					int ganador=0;
					int indiceGanador=0;
					
					//Recorremos todas las instancias
					for(int i=0;i<inst.numInstances();i++){
						//contamos los valores de los X ultimos atributos
						for(int j=1;j<atribKop+1;j++){
							votacion[(int)inst.instance(i).value(inst.numAttributes()-j)]++;
						}	
						//System.out.println(votacion[0]+"   "+votacion[1]);
						//Sacamos el resultado de la votacion	
						ganador=votacion[0];
						for(int k=1;k<votacion.length;k++){
							if(votacion[k]>ganador){
								ganador=votacion[k];
								indiceGanador=k;
							}
					}
						pw.println("Instancia "+ i +" resultado votacion: "+ inst.attribute(inst.numAttributes()-1).value(indiceGanador));
						for(int k=0;k<votacion.length;k++){
							votacion[k]=0;
							}
						ganador=0;
						indiceGanador=0;
						}
						
					pw.close();
				}
				else{
					System.out.println("Beharrezko parametroak:");
					System.out.println("   arg[0] = Path fichero con resultados");
					System.out.println("   args[1] = numero de atributos del fichero a tener en cuenta como resultados");
					System.out.println("   args[2] = path fichero con resultados de votacion"); 
					
				}
}
}
