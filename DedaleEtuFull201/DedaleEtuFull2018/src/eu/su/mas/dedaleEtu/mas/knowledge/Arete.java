package eu.su.mas.dedaleEtu.mas.knowledge;

import jade.util.leap.Serializable;

public class Arete implements Serializable{
	
	public Arete(String n1, String n2) {
		this.n1=n1;
		this.n2=n2;
	}
	
	public String getN1() {
		return n1;
	}

	public void setN1(String n1) {
		this.n1 = n1;
	}

	public String getN2() {
		return n2;
	}

	public void setN2(String n2) {
		this.n2 = n2;
	}

	private String n1;
	private String n2;
	

		
	
	

}
