package edu.mccc.cos210.fp.pupp;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.swing.*;


public class Geppetto {
	private final Action ActionList[] = {
		new Action("Leg", 35),
		new Action("Arm", 36),
		new Action("Head", 37),
		new Action("Hand", 38),
		new Action("Foot", 39)	
	};
	private final int DEFAULT_VELOCITY = 100;
	private final int PPQ = 8;
	private final int BPMinute = 120;//actual bpm of song
	private Synthesizer synth;
	private Sequencer sequencer;
	private Sequence sequence;
	private Track track;
	private static final String SONG = "data/cos210.mid";
	private static final File song = new File(SONG);
	private static final int META_EndofTrack = 47;
	private AddMenu am = new AddMenu(ActionList, song);
	private ImageIcon icon = new ImageIcon("data/Gort-Gorts-Icons-Vol4-Peppy-The-Puppet.ico");
	private JFrame jf;
	private FileDialog fd = new FileDialog(jf, "Save As", FileDialog.SAVE);
	private void initSwing() {
		jf = new JFrame("Geppetto");
		jf.addWindowListener(new MyWindowListener());
		fd.setVisible(false);
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1, 2));
		JButton jb = new JButton("Clear!");
		jb.addActionListener(
			ae -> {
				//clear all the bits of this section in the actual file
				//AddMenu.repaint();
			}		
		);
		jp.add(jb);
		jb = new JButton("Save!");
		jb.addActionListener(
			ae -> {
				try {
					sequence = new Sequence(Sequence.PPQ, PPQ);
					track = sequence.createTrack();
					//setchannel
					//play 0-31 for some reason
					//puppIt what we made
					fd.setVisible(true);
					if (fd.getFile() != null) {
						MidiSystem.write(
							sequence,
							0,
							new File(
								fd.getDirectory(),
								fd.getFile()
							)
						);
					}
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
					System.exit(-1);
				}
			}		
		);
		jp.add(jb);
		JPanel np = new JPanel();
		//declare a type of doubly linked list of 4 beat sections of the submitted song
		np.setLayout(new GridLayout(1, 2));
		JButton npb = new JButton("Previous");
		npb.addActionListener(
				ae -> {
					//temp save?
					//cycle through the DLL to get the previous chunk
					//repaint
				}
				);
		np.add(npb);
		npb = new JButton("Next");
		npb.addActionListener(
				ae -> {
					//temp save?
					//cycle and send the next chunk
					//repaint
				}
				);
		np.add(npb);
		jf.add(np, BorderLayout.SOUTH);
		jf.add(jp, BorderLayout.NORTH);
		jf.add(am, BorderLayout.CENTER);
		jf.setSize(new Dimension(800, 600));
		jf.setIconImage(icon.getImage());
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
		
	}
	private class MyWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(-1);
		}
	}
	public Geppetto() {
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			Soundbank defsb = synth.getDefaultSoundbank();
			synth.unloadAllInstruments(defsb);
			Soundbank sb = MidiSystem.getSoundbank(new File("data/FluidR3_GM.sf2"));
			synth.loadAllInstruments(sb);
			sequencer = MidiSystem.getSequencer(true);
			sequencer.open();
			sequence = MidiSystem.getSequence(new File(SONG));
			sequencer.setSequence(sequence);
		}catch (Exception ex) {
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
		initSwing();
	}
	public static void main(String... args) {
		EventQueue.invokeLater(Geppetto::new);
	}
	private void puppIt(byte[] msg, int tick) throws Exception {
		MetaMessage message = new MetaMessage();
		message.setMessage(0x7f, msg, msg.length);
		track.add(new MidiEvent(message, tick));
	}
	private void setChannel() throws Exception {
		/*createEvent(
			ShortMessage.PROGRAM_CHANGE,
			CHANNEL,
			PRESET,
			0
		);*/
	}
	private void playIt(int number, long tick) throws Exception {
		/*createEvent(
			ShortMessage.NOTE_ON,
			CHANNEL,
			number,
			tick
		);
		createEvent(
			ShortMessage.NOTE_OFF,
			//CHANNEL,
			number,
			tick + 1
		);*/
	}
	class Action {
		//might need to talk to action list
		private String name;
		private int number;
		public Action(String s, int n) {
			name = s;
			number = n;
		}
		public String getName() {
			return name;
		}
		public int getNumber() {
			return number;
		}
	}
	public class AddMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		private Action[] al;
		private File song;
		private int textX = 8;
		private int gridX = 128;
		private int topY = 128;
		private int xSize = 64;
		private int ySize = 44;
		//not actually int array but whatever song ends up being
		public AddMenu(Action[] al, File song) {
			this.al = al;
			this.song = song;
			addMouseListener(new MyMouseListener());
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			Font font = new Font("Verdana", Font.BOLD, 32);
			g2d.setFont(font);
			for (int i = 0; i< al.length; i++) {
				g2d.drawString(al[i].getName(), textX, topY + i * ySize);
			}
			for (int i = 1; i < al.length + 1; i++) {
				for (int j = 1; j < 9; j++) {
					Rectangle r = new Rectangle(xSize, ySize);
					r.translate(gridX + j *xSize, topY + (i - 2) * ySize);
					g2d.draw(r);
					/*if (song[i][j] != 0) {
						g2d.fill(r);
					}animations present check*/
				}
			}
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.setPaint(Color.blue);
			for (int i = 0; i < 5; i++) {
				g2d.drawLine(
					gridX + xSize + i * xSize * 2,
					topY - ySize + 1,
					gridX + xSize + i * xSize * 2,
					topY + al.length * ySize
				);
			}
			g2d.dispose();
		}
		private class MyMouseListener extends MouseAdapter {
			public void mouseReleased(MouseEvent me) {
				int x = me.getX();
				int y = me.getY();
				int c = (x - gridX) / xSize;
				int r;
				if (y > topY) {
					r = (y - topY) / ySize + 2;
				} else {
					r = 1;
				}
				if (c > 0 && c <= 32 && r > 0 && r < 48) {
					//song[r][c] = song[r][c] == 1 ? 0 : 1;
					//toggle value
				}
				repaint();
			}
		}
	}
}
