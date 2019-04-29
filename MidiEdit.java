package edu.mccc.cos210.fp.pupp;

import java.io.File;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import edu.mccc.cos210.ds.ISortedList;
import edu.mccc.cos210.ds.IVector;
import edu.mccc.cos210.ds.SortedList;
import edu.mccc.cos210.ds.Vector;

//====================================================================================================
//optimize action
//Multi-track file failed, if the first track have no event code 
//										===>!!! may use "4 * resolution" to separate instead of "%"
//Loss the last part information in the ending of MIDI file.
//bug: 1. click outside of box => error  fixed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MyMouseListener->mouseReleased
//====================================================================================================


public class MidiEdit {

	private int pointer = 0;
	private long tickLength;
	private long microsecondLength;
	private int resolution;
	private Sequence sequence;
	private Track[] track; 
//	private IVector<ISortedList<TickNode>> infoArray =  new Vector<>();  
//	private ISortedList<TickNode> currentNode = new SortedList<>();

	public IVector<ISortedList<TickNode>> infoArray =  new Vector<>();  
	public ISortedList<TickNode> currentNode = new SortedList<>();
	//public static void main(String... args) {
//		MidiRead a = new MidiRead(new File("data/wwiwiwo.mid"));
//		MidiRead a = new MidiRead(new File("data/2testy.mid"));
//		MidiRead a = new MidiRead(new File("data/2222.mid"));
		//MidiEdit a = new MidiEdit(new File("data/cos210.mid"));
		//System.out.println(a.infoArray.getSize());
		//for (ISortedList<TickNode> b : a.infoArray) {
			//System.out.println(b.toString());
//			for (TickNode c : b) {
//				System.out.print(" ! ");
//				System.out.println(c);
//				System.out.println(c.getAction());
//			}
	public MidiEdit(File song) {
		try {
			sequence = MidiSystem.getSequence(song);
			resolution = sequence.getResolution();
			microsecondLength = sequence.getMicrosecondLength();
			tickLength = sequence.getTickLength();
			track = sequence.getTracks();
			doFrom7f();
		} 	
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doFrom7f() {
		long bound = 0;
		TickNode current = null;
		Track infoTrack = track[track.length - 1];
		for (int j = 0; j < infoTrack.size() - 1; j++) {
			MidiEvent midiEvent = infoTrack.get(j);
			long tick = midiEvent.getTick();	
			current = new TickNode(tick);
			MidiMessage midiMessage = midiEvent.getMessage();
			int status = midiMessage.getStatus();
			if (status == 255) {
				byte[] message = midiMessage.getMessage();
				byte[] a = new byte[message.length-2];
				for(int i = 2; i<message.length; i++) {   //remove ff 7f
					a[i-2] = message[i];
				}
				Vector<Integer> asss = decode(new String(a).trim().split(":"));
				if(asss.getSize() !=0 ){
					current.setAction(asss);
				}
				if ((tick - bound) >= 4 * resolution) { 
					infoArray.pushBack(currentNode);
					currentNode = new SortedList<>();
					bound += 4 * resolution;
				}
				currentNode.add(current);
			}
		}	
		infoArray.pushBack(currentNode);
	}  		 	
	
	public boolean haveAction() {
		MidiEvent testEvent = null;
		MidiMessage testMessage = null;
		Track testTrack = track[track.length-1];
		byte[] message;
		for(int i = 2; i <= 4; i++) {
			testEvent = testTrack.get(testTrack.size()/i);
			testMessage = testEvent.getMessage();
			message = testMessage.getMessage();
			if(printHex(message[1]).equals("7f")) {
				return true;
			}	
		}
		return false;
	}
	public void doReg() {
		boolean oneScreen = false;
		TickNode current = null;
		long bound = 0;
		for (int i = 0; i < track.length; i++) {
			Long preTick = (long) -1;
			for (int j = 0; j < track[i].size(); j++) {
				MidiEvent midiEvent = track[i].get(j);
				long tick = midiEvent.getTick();			
				MidiMessage midiMessage = midiEvent.getMessage();
				int status = midiMessage.getStatus();
				byte[] message = midiMessage.getMessage();
				if (status >= 128 && status < 160 ) {
					String mes = "";
					for (int k = 0; k < message.length; k++) {
						mes += " " + printHex(message[k]);							
					}				
					if (preTick == tick) {
						current.addMes(mes);
					} else {
						if (current != null) {
							currentNode.add(current);
							if ((tick - bound) >= 4 * resolution) { 
//							if (((double)tick / resolution) % 4 == 0.0) {
								oneScreen = true;
								bound += 4 * resolution;
							}
						}
						current = new TickNode(tick, mes);
						preTick = tick;
					}
					if (oneScreen) {
						infoArray.pushBack(currentNode);
						currentNode = new SortedList<>();
						oneScreen = false;
					} 
//					if (j == track[i].size() - 2) {
//						currentNode.add(current);
//						infoArray.pushBack(currentNode);
//					}
				}
			}
		}			
	}
		
	public void updateAction(int action, int row, long tick) { 
		for(TickNode a : getCurrentList()) {
			if(a.getTick() == tick) {
				if(action == 1) {
					a.createAction(row);
				} else {
					a.update(row);
				}
				return;
			} 
		}	
		TickNode nt = new TickNode(tick);
		nt.createAction(row);
		infoArray.get(pointer).add(nt);		
	}
	public Sequence getSeq() {
		return this.sequence;
	}
	public Track[] getTrack() {
		return this.track;
	}
	public IVector<ISortedList<TickNode>> getAllInfo(){
		return this.infoArray;
	}
	public int getPointer() {
		return this.pointer;
	}
	public int getResolution() {
		return this.resolution;
	}
	public ISortedList<TickNode> getCurrentList() {
		return infoArray.get(pointer);
	}
	public ISortedList<TickNode> getnextList() {
		return (pointer <= infoArray.getSize() - 2) ? infoArray.get(++pointer) : infoArray.get(pointer);
	}
	public ISortedList<TickNode> getPreList() {
		return (pointer >= 1) ? infoArray.get(--pointer) : infoArray.get(pointer);
	}
	public double getRealtime() {
		return ((double) microsecondLength / 1000000.0) / tickLength;
	}	
	public Vector<Integer> decode(String[] mes) {
		Vector<Integer> act = new Vector<>();
		for(String ss : mes) {
			switch(ss) {		
				case "RTRR":
					act.pushBack(1);
					break;
				case "RTRL":
					act.pushBack(2);
					break;
				case "LTRR":
					act.pushBack(3);
					break;
				case "LTRL":
					act.pushBack(4);
					break;
				case "RARR":
					act.pushBack(5);
					break;
				case "RARL":
					act.pushBack(6);
					break;
				case "LARR":
					act.pushBack(7);
					break;
				case "LARL":
					act.pushBack(8);
					break;
				case "RBRR":
					act.pushBack(9);
					break;
				case "RBRL":
					act.pushBack(10);
					break;
				case "LBRR":
					act.pushBack(11);
					break;
				case "LBRL":
					act.pushBack(12);
					break;
				case "RSRR":
					act.pushBack(13);
					break;
				case "RSRL":
					act.pushBack(14);
					break;
				case "LSRR":
					act.pushBack(15);
					break;
				case "LSRL":
					act.pushBack(16);
					break;
				case "HERR":
					act.pushBack(17);
					break;
				case "HERL":
					act.pushBack(18);
					break;
			}
		}
		return act;
	}
	private String printHex(byte b) {
		String s = Integer.toHexString(b & 0x000000ff);
		return s.length() == 1 ? "0" + s : s;
	}
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
			System.out.print("in TICKNODE" + action.getSize());
			this.action.pushBack(act);
			System.out.println("         " + action.getSize());
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
//				if(this.action.get(i) != act) {
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
//			if ((Integer.parseInt(res[1], 16) < 144) || (Integer.parseInt(res[3], 16) <= 50)) {
//				action = 0 - action;	
//			}				
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
}
