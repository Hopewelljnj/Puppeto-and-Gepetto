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
import javax.swing.JPanel;

public class MidiReader implements MetaEventListener, ControllerEventListener {
	private static final int META_EndofTrack = 47;
	private static final int META_Data = 127;
	public static Synthesizer synth;
	public static Sequencer sequencer;
	public static Sequence sequence;
	private PuppPanel panel;
	public MidiReader(PuppPanel panel, File midiFile) {
		this.panel = panel;
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
	private void decode(String a) {
		
		System.out.println(a);
		switch(a) {
		case "init" : System.out.println("initializing");
			break;
		case "RTRR":
			panel.updateRotations("RThighRight");
			break;
		case "RTRL":
			panel.updateRotations("RThighLeft");
			break;
		case "LTRR":
			panel.updateRotations("LThighRight");
			break;
		case "LTRL":
			panel.updateRotations("LThighLeft");
			break;
		case "RARR":
			panel.updateRotations("RForeArmRight");
			break;
		case "RARL":
			panel.updateRotations("RForeArmLeft");
			break;
		case "LARR":
			panel.updateRotations("LForeArmRight");
			break;
		case "LARL":
			panel.updateRotations("LForeArmLeft");
			break;
		case "RBRR":
			panel.updateRotations("RBicepRight");
			break;
		case "RBRL":
			panel.updateRotations("RBicepLeft");
			break;
		case "LBRR":
			panel.updateRotations("LBicepRight");
			break;
		case "LBRL":
			panel.updateRotations("LBicepLeft");
			break;
		case "RSRR":
			panel.updateRotations("RShinRight");
			break;
		case "RSRL":
			panel.updateRotations("RShinLeft");
			break;
		case "LSRR":
			panel.updateRotations("LShinRight");
			break;
		case "LSRL":
			panel.updateRotations("LShinLeft");
			break;
		}
		
	}
}

