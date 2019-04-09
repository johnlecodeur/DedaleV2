package eu.su.mas.dedaleEtu.mas.behaviours;

import java.util.List;

import eu.su.mas.dedaleEtu.mas.agents.yours.DedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.Arete;
import eu.su.mas.dedaleEtu.mas.knowledge.Case;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import dataStructures.tuple.Couple;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMessageBehaviour extends SimpleBehaviour{
	
	private static final long serialVersionUID = 9088209402507795289L;
	private boolean finished=false;
	private DedaleAgent myDedaleAgent;
	
	public ReceiveMessageBehaviour(final DedaleAgent myagent) {
		super(myagent);
		myDedaleAgent = myagent;
	}
	
	@Override
	public void action() {
		
		//1) receive the message
		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		final ACLMessage msg = this.myDedaleAgent.receive(msgTemplate);
		System.out.println(myDedaleAgent.getLocalName() + " ----> ouvrerture messages");
		if (msg != null) {

		try {
			switch(msg.getProtocol()) {
			
			case "MAP":
				System.out.println(myDedaleAgent.getLocalName() + " ----> map recue");
				//recupere un couple liste case et liste edge
				this.myDedaleAgent.MergeMap((Couple<List<Case>,List<Arete>>)msg.getContentObject());
				
				
				break;
			}
			}
		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
		}
		
	}

	@Override
	public boolean done() {
		return finished;
	}

}

