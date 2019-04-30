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

public class MidiRead {
	private long tickLength;
	private long microsecondLength;
	private int resolution;
	private Sequence sequence;
	private Track[] track; 
	private IVector<ISortedList<TickNode>> infoArray =  new Vector<>();  
	private ISortedList<TickNode> currentNode = new SortedList<>();
	
	public MidiRead(File song) {
		try {
			sequence = MidiSystem.getSequence(song);
			resolution = sequence.getResolution();
			microsecondLength = sequence.getMicrosecondLength();
			tickLength = sequence.getTickLength();
			track = sequence.getTracks();
			if(haveAction()) {
				doFrom7f();
			} else {
				doReg();
			}
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
		this.sequence.deleteTrack(track[track.length - 1]);
		this.track = this.sequence.getTracks();
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
	
	public Sequence getSeq() {
		return this.sequence;
	}
	public Track[] getTrack() {
		return this.track;
	}
	public IVector<ISortedList<TickNode>> getAllInfo(){
		return this.infoArray;
	}
	public int getResolution() {
		return this.resolution;
	}
	public double getRealtime() {
		return ((double) microsecondLength / 1000000.0) / tickLength;
	}	
	private String printHex(byte b) {
		String s = Integer.toHexString(b & 0x000000ff);
		return s.length() == 1 ? "0" + s : s;
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
}
