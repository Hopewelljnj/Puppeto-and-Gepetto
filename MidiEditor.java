package edu.mccc.cos210.fp.pupp;

import java.io.File;

//import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import edu.mccc.cos210.ds.ISortedList;
import edu.mccc.cos210.ds.IVector;
import edu.mccc.cos210.ds.Vector;

public class MidiEditor {
//	private MidiReader reader;
	private int pointer = 0;
	private IVector<ISortedList<TickNode>> infoArray =  new Vector<>();  
	private int resolution;
//	private Sequence sequence;
	private Track[] track; 

	public MidiEditor(File song) {
		MidiRead reader = new MidiRead(song);
		this.resolution = reader.getResolution();
//		this.sequence = reader.getSeq();
		this.track = reader.getTrack();
		this.infoArray = reader.getAllInfo();	
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
	public int getPointer() {
		return this.pointer;
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
	public Track[] getTrack() {
		return this.track;
	}
	public IVector<ISortedList<TickNode>> getAllInfo(){
		return this.infoArray;
	}
	public int getResolution() {
		return this.resolution;
	}
}

