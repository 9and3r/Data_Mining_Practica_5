package smotetester;

import org.packDataMining.MenuSmote;

public class SmoteTester {
	
	public static void main(String[] args) {
		
		if (args.length!=3){
			System.out.println("Los parámetros deben ser:\n1) Path absoluto del fichero de entrada\n2)Índice de la clase;\n3)Path absoluto del fichero salida");
			System.out.println("SE DEBEN TENER 5 FICHEROS EN EL PATH ABSOLUTO QUE SE INDIQUE, QUE SE LLAMEN 1, 2, 3, 4 y 5");
		}
		else{
			String ficheroentrada = args[0];
			String ficherosalida = args[2];
			for(int i = 1; i <=5; i++){
				ficheroentrada = ""+i;
				ficherosalida = "salidaSMOTE"+i;
				String[] args2 = {ficheroentrada, args[1], ficherosalida};
				MenuSmote.main(args);
			}
		}
	}

}
