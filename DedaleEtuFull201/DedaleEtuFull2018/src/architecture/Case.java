package architecture;

import java.sql.Date;
import jade.util.leap.Serializable;

public class Case implements Serializable{
	private String id;
	private boolean node_open=false;
	private Date date;
	//private Agent agent;
	private int gold;
	private int diamond;
	private boolean tresor=false;
	private boolean tresor_ouvert=false;
	
	
	
	public Case() {
		
		
	}
}
