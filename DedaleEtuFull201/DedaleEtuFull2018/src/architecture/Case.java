package architecture;

import java.util.Date;
import jade.util.leap.Serializable;




//class reprensentant un noeud du graphe 




public class Case implements Serializable{
	private String id;
	private boolean node_open=true;//mis a true car si on cree la case c'est que le noeud est ouvert
	private Date date;
	//private Agent agent;
	private int gold;
	private int diamond;
	private boolean tresor=false;
	private boolean tresor_ouvert=false;
	
	
	
	public Case(String id,boolean node_open,/*Date date,*/int gold,int diamond,boolean tresor,boolean tresor_ouvert) {
		this.id=id;
		this.node_open=node_open;
		date=new Date();
		this.gold=gold;
		this.diamond=diamond;
		this.tresor=tresor;
		this.tresor_ouvert=tresor_ouvert;
		}
	
	//recupere l'id de la case
	public String getId() {
		return this.id;
	}
	
	//change l'id de la case
	public void setId(String id) {
		this.id=id;
	}
	
	public boolean is_Open() {
		return this.node_open;
	}
	
	public Date getDate() {
		return date;
	}
/*	
	public boolean is_Agent() {
		return this.agent;
	}
*/	
	public int getGold() {
		return this.gold;
	}
	
	public void SetGold(int gold) {
		this.gold=gold;
	}
	
	public int getDioamond() {
		return this.diamond;
	}
	
	public void SetDiamond(int diamond) {
		this.diamond=diamond;
	}
	
	public boolean is_Tresor() {
		return this.tresor;
	}
	
	public void SetTresor(boolean tresor) {
		this.tresor=tresor;
	}
	
	public boolean is_TresorOuvert() {
		return this.tresor_ouvert;
	}
	
	public void SetTresorOuvert(boolean tresor_ouvert) {
		this.tresor_ouvert=tresor_ouvert;
	}
}
