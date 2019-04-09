package eu.su.mas.dedaleEtu.mas.knowledge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

/**
 * This simple topology representation only deals with the graph, not its content.</br>
 * The knowledge representation is not well written (at all), it is just given as a minimal example.</br>
 * The viewer methods are not independent of the data structure, and the dijkstra is recomputed every-time.
 * 
 * @author hc
 */
public class MapRepresentation implements Serializable {

	public enum MapAttribute {
		agent,open
	}

	private static final long serialVersionUID = -1333959882640838272L;

	private Graph g; //data structure
	private Viewer viewer; //ref to the display
	private Integer nbEdges;//used to generate the edges ids
	
	/*********************************
	 * Parameters for graph rendering
	 ********************************/
	
	private String defaultNodeStyle= "node {"+"fill-color: black;"+" size-mode:fit;text-alignment:under; text-size:14;text-color:white;text-background-mode:rounded-box;text-background-color:black;}";
	private String nodeStyle_open = "node.agent {"+"fill-color: forestgreen;"+"}";
	private String nodeStyle_agent = "node.open {"+"fill-color: blue;"+"}";
	private String nodeStyle=defaultNodeStyle+nodeStyle_agent+nodeStyle_open;

	
	public MapRepresentation() {
		System.setProperty("org.graphstream.ui.renderer","org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		this.g= new SingleGraph("My world vision");
		this.g.setAttribute("ui.stylesheet",nodeStyle);
		this.viewer = this.g.display();
		this.nbEdges=0;
	}

	/**
	 * Associate to a node an attribute in order to identify them by type. 
	 * @param id
	 * @param mapAttribute
	 */
	//retourne la liste des cases de la map
	public List<Case> getMapCase(){
		List<Case> lc= new ArrayList<Case>();
		for(Node n : g.getEachNode()) {
			if(n.getAttribute("ui.label")!=null) {
				lc.add(new Case(n.getAttribute("ui.label"),n.getAttribute("ui.class")/*open_node*/,n.getAttribute("gold"),n.getAttribute("tresor"),n.getAttribute("tresor_ouvert"),n.getAttribute("wumpus")));					
			}
		}
		return lc;
	}
	
	//retourne la liste des aretes de la map
	public List<Arete> getMapEdge(){
		List<Arete> lc= new ArrayList<Arete>();
		for(Edge e : g.getEachEdge()) {
			if(e.getAttribute("n1")!=null) {
				lc.add(new Arete(e.getNode0().toString(),e.getNode1().toString()));					
			}
		}
		return lc;
	}
	
	//adapter les fonctions addnodes
	
	public void addNode(Case c) {
		MapAttribute etat=null;
		//boolean node_open=false;
		if(c.is_Open()==true) {
			etat=MapAttribute.open;
			//node_open=true;
		}
		addNode(c.getId(),etat,c.getGold(),c.is_Tresor(),c.is_TresorOuvert(),c.isWumpus());
	}
	
	//mise a jour des information (a faire)
	public void addNode(String id,MapAttribute etat,int gold,boolean tresor,boolean tresor_ouvert, boolean wumpus) {
		
	}
	
	
	
	public void addNode(String id,MapAttribute mapAttribute){
		Node n;
		if (this.g.getNode(id)==null){
			n=this.g.addNode(id);
		}else{
			n=this.g.getNode(id);
		}
		n.clearAttributes();
		n.addAttribute("ui.class", mapAttribute.toString());
		n.addAttribute("ui.label",id);
	}

	/**
	 * Add the node id if not already existing
	 * @param id
	 */
	public void addNode(String id){
		Node n=this.g.getNode(id);
		if(n==null){
			n=this.g.addNode(id);
		}else{
			n.clearAttributes();
		}
		n.addAttribute("ui.label",id);
	}

   /**
    * Add the edge if not already existing.
    * @param idNode1
    * @param idNode2
    */
	public void addEdge(String idNode1,String idNode2){
		try {
			this.nbEdges++;
			this.g.addEdge(this.nbEdges.toString(), idNode1, idNode2);
		}catch (EdgeRejectedException e){
			//Do not add an already existing one
			this.nbEdges--;
		}
		
	}
	
	//ajoute une arete a la repreaentation de la map a partir d'une arete
	public void addEdge(Arete a) {
		try {
			this.nbEdges++;
			this.g.addEdge(this.nbEdges.toString(), a.getN1(), a.getN2());
		}catch (EdgeRejectedException e){
			//Do not add an already existing one
			this.nbEdges--;
		}
		}
		
	

	/**
	 * Compute the shortest Path from idFrom to IdTo. The computation is currently not very efficient
	 * 
	 * @param idFrom id of the origin node
	 * @param idTo id of the destination node
	 * @return the list of nodes to follow
	 */
	public List<String> getShortestPath(String idFrom,String idTo){
		List<String> shortestPath=new ArrayList<String>();

		Dijkstra dijkstra = new Dijkstra();//number of edge
		dijkstra.init(g);
		dijkstra.setSource(g.getNode(idFrom));
		dijkstra.compute();//compute the distance to all nodes from idFrom
		List<Node> path=dijkstra.getPath(g.getNode(idTo)).getNodePath(); //the shortest path from idFrom to idTo
		Iterator<Node> iter=path.iterator();
		while (iter.hasNext()){
			shortestPath.add(iter.next().getId());
		}
		dijkstra.clear();
		shortestPath.remove(0);//remove the current position
		return shortestPath;
	}
	
	
	//l idee de cette fonction est d'all� au noeud ouvert le plus proche de la position actuelle de mon agent
	//une sorte de parcours en profondeur 
	//ne pas oublier de changer les arguments dans exploremultibehaviour
	public List<String> getMyShortestPath(String idFrom, List<String> openNodes){
		List<String> shortestPath=new ArrayList<String>();
		int size=openNodes.size();//longueur de la liste de noeud ouvert

		Dijkstra dijkstra = new Dijkstra();//number of edge
		dijkstra.init(g);
		dijkstra.setSource(g.getNode(idFrom));
		dijkstra.compute();//compute the distance to all nodes from idFrom
		double min =dijkstra.getPathLength(g.getNode(openNodes.get(0)));
		int indice=0;
		for(int i=0; i<size;i++) {
			if(min>dijkstra.getPathLength(g.getNode(openNodes.get(i)))) {
				min=dijkstra.getPathLength(g.getNode(openNodes.get(i)));
				indice=i;
			}
		}
		List<Node> path=dijkstra.getPath(g.getNode(openNodes.get(indice))).getNodePath(); //the shortest path from idFrom to node indice
		Iterator<Node> iter=path.iterator();
		while (iter.hasNext()){
			shortestPath.add(iter.next().getId());
		}
		
		dijkstra.clear();
		shortestPath.remove(0);//remove the current position
		return shortestPath;
	}
}
