package org.packSVM;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class Datuak {
	
	private Instances datuak;

	
	public Datuak (String fitxategia){
		
		kargatu(fitxategia);
	}
	public void kargatu(String path){
		FileReader fi = null;
		// Fitxategia irakurri
		try {
			fi = new FileReader(path);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Berikusi fitxategiaren path-a:" + path);
		}
		// Instantziak hartu
		Instances data = null;
		try {
			data = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: Berrikusi fitxategiaren edukia: " + path);
		}
		// Fitxategia itxi
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		this.datuak = data;
	}
	
	public Instances getDatuak(){
		return this.datuak;
	}
}
