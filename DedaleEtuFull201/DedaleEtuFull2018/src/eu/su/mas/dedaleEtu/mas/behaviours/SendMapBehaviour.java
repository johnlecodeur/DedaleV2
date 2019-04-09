package eu.su.mas.dedaleEtu.mas.behaviours;

import java.io.IOException;

import eu.su.mas.dedaleEtu.mas.agents.yours.DedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class SendMapBehaviour extends SimpleBehaviour{
	
	private static final long serialVersionUID = 8567689731436787661L;

	private boolean finished = false;
	private String receiverName;
	private String[] agentsIds;
	private DedaleAgent myDedaleAgent;
	
	public SendMapBehaviour(final DedaleAgent myagent, String receiverName, String[] agentsIds) {
		super(myagent);
		this.myDedaleAgent = myagent;
		this.receiverName=receiverName;
		this.agentsIds = agentsIds;
		
	}
	
	
	
	@Override
	public void action() {
		//creation du message
		final ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setSender(this.myDedaleAgent.getAID());
		
		//ajoute tous les agents en receveur si aucun de receveur donné
		if(this.receiverName=="-1") {
			for(int i =0; i<agentsIds.length; i++) {
				if(!agentsIds[i].equals(myDedaleAgent.getLocalName())) {
					msg.addReceiver(new AID(agentsIds[i], AID.ISLOCALNAME));
				}
			}
		}
		else {
			msg.addReceiver(new AID(this.receiverName, AID.ISLOCALNAME));  
		}
		
		//ajout du contenu du message
		try {
			//met un nom de protocl pour etre gerer dans receivemessage
			msg.setProtocol("MAP");
			//envoie une paire de <liste de case> et <liste d'arete>
			msg.setContentObject(this.myDedaleAgent.getMyMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.myDedaleAgent.send(msg);
		this.finished=true; // After the execution of the action() method, this behaviour will be erased from the agent's list of triggerable behaviours.
		System.out.println(this.myDedaleAgent.getLocalName()+" ----> myMap sent to "+this.receiverName);
	}

	@Override
	public boolean done() {

		return finished;
	}

}
