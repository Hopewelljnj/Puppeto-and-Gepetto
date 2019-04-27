package edu.mccc.cos210.jp;

import java.io.File;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import edu.mccc.cos210.ds.ISortedList;
import edu.mccc.cos210.ds.IVector;
import edu.mccc.cos210.ds.Vector;
import edu.mccc.cos210.fp.pupp.MidiRead;
import edu.mccc.cos210.fp.pupp.MidiRead.TickNode;


//==========================================================================================
//track is created by function, not sure where it is.(not the last track)
//
//==========================================================================================

public class MidiWriter {
	private Synthesizer synth;
	private Sequencer sequencer;
	private Sequence sequence;
	private String[] actionlist;
	
	public MidiWriter(String[] saction, MidiRead b, File output) throws Exception {
		this.actionlist  = saction;
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			Soundbank defsb = synth.getDefaultSoundbank();
			synth.unloadAllInstruments(defsb);
			Soundbank sb = MidiSystem.getSoundbank(new File("data/FluidR3_GM.sf2"));
			synth.loadAllInstruments(sb);
			sequencer = MidiSystem.getSequencer(true);
			sequence = new Sequence(Sequence.PPQ, b.getResolution());	
			sequencer.setSequence(sequence);
//			sequencer.setTempoInBPM();
			sequencer.open();
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
		for(int j = 0; j <= b.getTrack().length; j++) {
			
		}
		Track newtrack = sequence.createTrack();
		Track oldtrack = b.getTrack()[0];
		for(int i = 0; i < oldtrack.size(); i++) {
			newtrack.add(oldtrack.get(i));
		}
		newtrack = sequence.createTrack();
		addTrack(b.getAllInfo(),newtrack);
		MidiSystem.write(
				sequence,
				1,
				output
			);
//		for(int i = 0; i < sequence.getTracks()[1].size(); i++) {    for checking only
//			MidiEvent midiEvent = sequence.getTracks()[1].get(i);
//			MidiMessage midiMessage = midiEvent.getMessage();
//			byte[] message = midiMessage.getMessage();
//			for (int k = 0; k < message.length; k++) {
//				System.out.print(" ");
//				if (message[k] > 0) {
//					System.out.print((char)message[k]);
//				}
//				System.out.println(message[k] + " : " + printHex(message[k]));
//			}
//		}
		sequencer.close();
		synth.close();
	}
//	private String printHex(byte b) {
//		String s = Integer.toHexString(b & 0x000000ff);
//		return s.length() == 1 ? "0" + s : s;
//	}
	private void addTrack(IVector<ISortedList<TickNode>> infoArray, Track track) throws Exception {
		for(ISortedList<TickNode> one : infoArray) {
			for(TickNode tn : one) {
				puppIt(getActionMessage(tn.getAction()),(int)tn.getTick(),track);
			}
		}
	}
	private byte[] getActionMessage(Vector<Integer> actionList) {
		StringBuilder sb = new StringBuilder();
		for (int acts : actionList) {
			sb.append(encodeIt(acts));
			sb.append(":");
		}
		return sb.substring(0,sb.length()-1).getBytes();
	}
	private String encodeIt(int action) {
		action = Math.abs(action);       // <===================================================== abs
		return this.actionlist[action];
	}	
	private void puppIt(byte[] msg, int tick, Track track) throws Exception {
		MetaMessage message = new MetaMessage();
		message.setMessage(0x7f, msg, msg.length);
		track.add(new MidiEvent(message, tick));
	}	
}
