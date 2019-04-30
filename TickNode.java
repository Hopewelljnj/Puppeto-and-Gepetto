package edu.mccc.cos210.fp.pupp;

import edu.mccc.cos210.ds.Vector;


@SuppressWarnings("rawtypes")
public class TickNode implements java.lang.Comparable {
	private long tick;
	private Vector<Integer> action = new Vector<>();
	private Vector<String> message = new Vector<>();
	public TickNode(long tick) {
		this.tick = tick;
	}
	public TickNode(long tick, int act) {
		this.tick = tick;
		this.action.pushBack(act);
	}
	public TickNode(long tick, String mes) {
		this.tick = tick;
		this.message.pushBack(mes);
		createAction(mes);
	}
	public void addMes(String mes) {
		this.message.pushBack(mes);
		createAction(mes);
	}
	public void setAction(Vector<Integer> action) {
		this.action = action;
	}
	public void update(int act) {
		Vector<Integer> na = new Vector<>();
		for(int i = 0; i < action.getSize(); i ++) {
			if(Math.abs(this.action.get(i)) != act) {
//			if(this.action.get(i) != act) {
				na.pushBack(this.action.get(i));
			}
		}
		this.action = na;
	}
	private void createAction(String mes) {
		action.pushBack(calcAction(mes));
	}
	public void createAction(int act) {
		action.pushBack(act);
	}
	private Integer calcAction(String mes) {   //optimize action here!===========================================
		String[] res = mes.split(" ");
		int action =  (Integer.parseInt(res[2], 16) % 16) + 1;
//		if ((Integer.parseInt(res[1], 16) < 144) || (Integer.parseInt(res[3], 16) <= 50)) {
//			action = 0 - action;	
//		}				
		return action;
	}
	public long getTick() {
		return this.tick;
	}
	public Vector<Integer> getAction() {
		return this.action;
	}
	public String toString() {
		return tick + " MES:  " + message.toString() + " ACTION: " + action.toString();
	}
	@Override
	public int compareTo(Object o) {
		return (int) (this.tick - ((TickNode) o).getTick());
	}	
}
