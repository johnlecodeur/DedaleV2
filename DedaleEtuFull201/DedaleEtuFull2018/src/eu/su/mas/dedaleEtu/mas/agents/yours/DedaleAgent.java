package eu.su.mas.dedaleEtu.mas.agents.yours;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploMultiBehaviour;
import eu.su.mas.dedaleEtu.mas.knowledge.Arete;
import eu.su.mas.dedaleEtu.mas.knowledge.Case;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.behaviours.Behaviour;


public class DedaleAgent extends AbstractDedaleAgent{
	private static final long serialVersionUID = -6431752665590433737L;
	private MapRepresentation myMap;
	
	//recupere la representation de la map via un couple de liste de case et de liste d'arete
	
	public Couple<List<Case>,List<Arete>> getMyMap() {
		List<Case> c = new ArrayList<Case>();
		List<Arete> a = new ArrayList<Arete>();
		c=this.myMap.getMapCase();
		a=this.myMap.getMapEdge();
		Couple <List<Case>,List<Arete>> map=new Couple(c,a);
		return map;
	}
	
	
	//doit fusionner la map actuelle avec la map recu sous forme d'un couple de  list <case>, list<arete>
	public void MergeMap( Couple<List<Case>, List<Arete>> couple) {
		List<Case> c=couple.getLeft();
		List<Arete> a = couple.getRight();
		
		
	}
		
	/**
	 * This method is automatically called when "agent".start() is executed.
	 * Consider that Agent is launched for the first time. 
	 * 			1) set the agent attributes 
	 *	 		2) add the behaviours
	 *          
	 */
	protected void setup(){

		super.setup();
		

		List<Behaviour> lb=new ArrayList<Behaviour>();
		
		/************************************************
		 * 
		 * ADD the behaviours of the Dummy Moving Agent
		 * 
		 ************************************************/
		
		lb.add(new ExploMultiBehaviour(this,this.myMap));
		
		
		/***
		 * MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		 */
		
		
		addBehaviour(new startMyBehaviours(this,lb));
		
		System.out.println("the  agent "+this.getLocalName()+ " is started");

	}

}
