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
//Multi-track file failed, if the first track have no event code.
//Loss the last part information in the ending from MIDI file.
//bug: click outside of box => error
//====================================================================================================



public class MidiRead {
	private int pointer = 0;
	private long tickLength;
	private long microsecondLength;
	private int resolution;
	private IVector<ISortedList<TickNode>> infoArray =  new Vector<>();  
	private ISortedList<TickNode> currentNode = new SortedList<>();
//	public IVector<ISortedList<TickNode>> infoArray =  new Vector<>();  
//	public ISortedList<TickNode> currentNode = new SortedList<>();
//	public static void main(String... args) {
//		MidiRead a = new MidiRead(new File("data/wwiwiwo.mid"));
//		MidiRead a = new MidiRead(new File("data/2testy.mid"));
//		System.out.println(a.getRealtime());
//		System.out.println(a.getInfo().get((long)360).toString());
//		System.out.println(a.getInfo().get((long)360).getAction().toString());
//		System.out.println(a.infoArray.getSize());
//		for (ISortedList<TickNode> b : a.infoArray) {
//			System.out.println(b.toString());
//			for (TickNode c : b) {
//				System.out.print(" ! ");
//				System.out.println(c);
//				System.out.println(c.getAction());
//			}
//		}
//	}
	public MidiRead(File song) {
		try {
			boolean oneScreen = false;
			TickNode current = null;
			Sequence sequence = MidiSystem.getSequence(song);
			resolution = sequence.getResolution();
			microsecondLength = sequence.getMicrosecondLength();
			tickLength = sequence.getTickLength();
			Track[] track = sequence.getTracks();
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
								if (((double)tick / resolution) % 4 == 0.0) {
									oneScreen = true;
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
//						if (j == track[i].size() - 2) {
//							currentNode.add(current);
//							infoArray.pushBack(currentNode);
//						}
					}
				}
			}			
		} 
		catch (Exception e) {
			e.printStackTrace();
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
		TickNode nt = new TickNode(tick, row);
		infoArray.get(pointer).add(nt);		
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
	private String printHex(byte b) {
		String s = Integer.toHexString(b & 0x000000ff);
		return s.length() == 1 ? "0" + s : s;
	}
	@SuppressWarnings("rawtypes")
	public class TickNode implements java.lang.Comparable {
		private long tick;
		private Vector<Integer> action = new Vector<>();
		private Vector<String> message = new Vector<>();
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
		public void update(int act) {
			Vector<Integer> na = new Vector<>();
			for(int i = 0; i < action.getSize(); i ++) {
				if(this.action.get(i) != act) {
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
			int action =  Integer.parseInt(res[2], 16) % 16;
			if ((Integer.parseInt(res[1], 16) < 144) || (Integer.parseInt(res[3], 16) <= 50)) {
				action = 0 - action;
			}				
			return action;
		}
		public long getTick() {
			return this.tick;
		}
		public Vector<Integer> getAction() {
			return this.action;
		}
		public String toString() {
			return tick + " " + message.toString();
		}
		@Override
		public int compareTo(Object o) {
			return (int) (this.tick - ((TickNode) o).getTick());
		}	
	}
}
