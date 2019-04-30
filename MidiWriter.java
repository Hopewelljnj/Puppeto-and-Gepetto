package edu.mccc.cos210.fp.pupp;

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
import edu.mccc.cos210.fp.pupp.MidiEdit;
import edu.mccc.cos210.fp.pupp.MidiEdit.TickNode;


//==========================================================================================
//
//
//==========================================================================================

public class MidiWriter {
	private Synthesizer synth;
	private Sequencer sequencer;
	private Sequence sequence;
	private String[] actionlist;
	
	public MidiWriter(String[] saction, MidiEdit b, File output) throws Exception {
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
		Track newtrack;
		Track oldtrack;
		for(int j = 0; j < b.getTrack().length; j++) {    //================================================
			newtrack = sequence.createTrack();
			oldtrack = b.getTrack()[j];
			for(int i = 0; i < oldtrack.size(); i++) {
				newtrack.add(oldtrack.get(i));
			}
		}
		newtrack = sequence.createTrack();
		addTrack(b.getAllInfo(),newtrack);
		MidiSystem.write(
				sequence,
				1,
				output
			);
		sequencer.close();
		synth.close();
	}
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
