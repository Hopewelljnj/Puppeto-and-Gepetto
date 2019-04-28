package edu.mccc.cos210.fp.pupp;

import java.io.File;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class MidiReader implements MetaEventListener, ControllerEventListener {
	private static final int META_EndofTrack = 47;
	private static final int META_Data = 127;
	public static Synthesizer synth;
	public static Sequencer sequencer;
	public static Sequence sequence;
	private Stage stage;
	public MidiReader(Stage stage, File midiFile) {
		this.stage = stage;
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			Soundbank defsb = synth.getDefaultSoundbank();
			synth.unloadAllInstruments(defsb);
			Soundbank sb = MidiSystem.getSoundbank(new File("data/FluidR3_GM.sf2"));
			synth.loadAllInstruments(sb);
			sequencer = MidiSystem.getSequencer(true);
			sequencer.open();
			sequence = MidiSystem.getSequence(midiFile);
			sequencer.setSequence(sequence);
			sequencer.addMetaEventListener(this);
			sequencer.addControllerEventListener(this, new int[] { 7, 16, 17, 18, 19 });
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
	public void meta(MetaMessage message) {
		byte[] ba = message.getData();
		if (message.getType() == META_Data) {
			String s = new String(ba);
			decode(s);
		}
		if (message.getType() == META_EndofTrack) {
			try {
				Thread.sleep(1000);
				sequencer.close();
			} catch (Exception ex) {
				// ignore
			} finally {
				System.exit(0);
			}
		}
	}
	@Override
	public void controlChange(ShortMessage event) {
		System.out.print("CC:");
		byte[] ba = event.getMessage();
		for (int i = 0; i < ba.length; i++) {
			printHex(ba[i]);
		}
		System.out.println();
	}
	private void printHex(byte b) {
		String s = Integer.toHexString(b & 0x000000ff);
		switch (s.length()) {
			case 1:
				System.out.print("0" + s);
				break;
			case 2:
				System.out.print(s);
				break;
		}
	}
	private void decode(String s) {
		System.out.println(s);
		System.out.println("were getting here");
		switch(s) {
		case "init" : System.out.println("initializing");
			break;
		case "RTRR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RHIP, 10.0);
			break;
		case "RTRL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RHIP, -10.0);
			break;
		case "LTRR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LHIP, 10.0);
			break;
		case "LTRL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LHIP, -10.0);
			break;
		case "RARR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RIGHT_ELBOW, 10.0);
			break;
		case "RARL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RIGHT_ELBOW, -10.0);
			break;
		case "LARR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LEFT_ELBOW, 10.0);
			break;
		case "LARL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LEFT_ELBOW, -10.0);
			break;
		case "RBRR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RIGHT_SHOULDER, 10.0);
			break;
		case "RBRL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RIGHT_SHOULDER, -10.0);
			break;
		case "LBRR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LEFT_SHOULDER, 10.0);
			break;
		case "LBRL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LEFT_SHOULDER, -10.0);
			break;
		case "RSRR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RIGHT_KNEE, 10.0);
			break;
		case "RSRL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.RIGHT_KNEE, -10.0);
			break;
		case "LSRR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LEFT_KNEE, 10.0);
			break;
		case "LSRL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.LEFT_KNEE, -10.0);
			break;
		case "HERR":
			stage.rotatePuppetLimb(0, Datatypes.Joint.NECK, 10.0);
			break;
		case "HERL":
			stage.rotatePuppetLimb(0, Datatypes.Joint.NECK, -10.0);
			break;
		}
		
	}
}

