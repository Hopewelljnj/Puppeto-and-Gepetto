package edu.mccc.cos210.fp.pupp;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


public class Geppetto {
	private final Action ActionList[] = {
		new Action("Right-Thigh Rotate Right"),
		new Action("Right-Thigh Rotate Left"),
		new Action("Left-Thigh Rotate Right"),
		new Action("Left-Thigh Rotate Left"),
		new Action("Right-Arm Rotate Right"),
		new Action("Right-Arm Rotate Left"),
		new Action("Left-Arm Rotate Right"),
		new Action("Left-Arm Rotate Left"),
		new Action("Right-Bicep Rotate Right"),
		new Action("Right-Bicep Rotate Left"),
		new Action("Left-Bicep Rotate Right"),
		new Action("Left-Bicep Rotate Left"),
		new Action("Right-Shin Rotate Right"),
		new Action("Right-Shin Rotate Left"),
		new Action("Left-Shin Rotate Right"),
		new Action("Left-Shin Rotate Left"),
		new Action("Head Rotate Right"),
		new Action("Head Rotate Left"),
	};
	private int[][] Grid = {
			{ 00, 1,0,0,0,0,0,0,0,},
			{ 35, 1,0,0,0,0,0,0,0,},
			{ 36, 0,0,0,0,0,0,0,0,},
			{ 37, 1,0,0,0,0,0,1,0,},
			{ 38, 0,0,0,0,0,0,0,0,},
			{ 39, 0,0,0,0,0,0,0,0,},
			{ 40, 0,0,0,0,0,0,0,0,},
			{ 41, 0,0,0,0,0,0,0,0,},
			{ 42, 1,0,1,0,1,0,1,0,},
			{ 43, 0,0,0,0,0,0,0,0,},
			{ 44, 0,0,0,0,0,0,0,0,},
			{ 45, 0,0,0,0,0,0,0,0,},
			{ 46, 0,0,0,0,0,0,0,0,},
			{ 47, 0,0,0,0,0,0,0,0,},
			{ 48, 0,0,0,0,0,0,0,0,},
			{ 49, 0,0,0,0,0,0,0,0,},
			{ 50, 0,0,0,0,0,0,0,0,},
			{ 51, 0,0,0,0,0,0,0,0,},
			{ 52, 0,0,0,0,0,0,0,0,},
	};
	private Synthesizer synth;
	private Sequencer sequencer;
	private Sequence sequence;
	private Track track;
	private float BPMinute = 0;
	private final int DEFAULT_VELOCITY = 100;
	private final int PPQ = 4;
	private static final int META_EndofTrack = 47;
	private static final File song = new File("data/2testy.mid");
	private JFrame jf;
	private ImageIcon ico = new ImageIcon("images/Gort-Gorts-Icons-Vol4-Peppy-The-Puppet.ico");
	private FileDialog fd = new FileDialog(jf, "Save As", FileDialog.SAVE);
	private void initSwing() {
		jf = new JFrame("Geppetto");
		//jf.addIcon(ico);
		jf.addWindowListener(new MyWindowListener());
		fd.setVisible(false);
		JPanel jp = new JPanel();
		AddMenu am = new AddMenu(ActionList, sequence, Grid);
		jp.setLayout(new GridLayout(1, 2));
		JButton jb = new JButton("Clear!");
		jb.addActionListener(
			ae -> {
				for (int i = 1; i < Grid.length; i++) {
					for (int j = 1; j < Grid[0].length; j++) {
						Grid[i][j] = 0;
					}
				}
				am.repaint();
			}		
		);
		jp.add(jb);
		jb = new JButton("Save!");
		jb.addActionListener(
			ae -> {
				try {
					track = sequence.createTrack();
					//encodeIt(0, 31);
					for (int i = 0; i < Grid.length; i++) {
						for (int j = 1; j < Grid[0].length; j++) {
							if (Grid[i][j] != 0) {
								encodeIt(Grid[i][0], j -1);
							}
						}
					}
					fd.setVisible(true);
					if (fd.getFile() != null) {
						if(MidiSystem.isFileTypeSupported(1, sequence)) {
							System.out.println("yerp!");
						}
						MidiSystem.write(
							sequence,
							1,
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
		np.setLayout(new GridLayout(1, 2));
		JButton npb = new JButton("Previous");
		npb.addActionListener(
				ae -> {
					//temp save?
					//move sequencer pointers with set and get tick
					//repaint
				}
				);
		np.add(npb);
		npb = new JButton("Next");
		npb.addActionListener(
				ae -> {
					//temp save?
					//set/get tick
					//repaint
				}
				);
		np.add(npb);
		jf.add(np, BorderLayout.SOUTH);
		jf.add(jp, BorderLayout.NORTH);
		jf.add(am, BorderLayout.CENTER);
		jf.setSize(new Dimension(800, 600));
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
		
	}
	private class MyWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			sequencer.close();
			synth.close();
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
			sequence = MidiSystem.getSequence(song);
			sequencer.setSequence(sequence);
			BPMinute = sequencer.getTempoInBPM();
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
	private void encodeIt(int position, int tick) throws Exception {
		switch(position) {
		case 00:
			byte[] init = "init".getBytes();
			puppIt(init, tick);
			break;
		case 35:
			byte[] RLRR = "RTRR".getBytes();
			puppIt(RLRR , tick);
			break;
		case 36:
			byte[] RLRL = "RTRL".getBytes();
			puppIt(RLRL, tick);
			break;
		case 37:
			byte[] LLRR = "LTRR".getBytes();
			puppIt(LLRR, tick);
			break;
		case 38:
			byte[] LLRL = "LTRL".getBytes();
			puppIt(LLRL, tick);
			break;
		case 39:
			byte[] RARR = "RARR".getBytes();
			puppIt(RARR, tick);
			break;
		case 40:
			byte[] RARL = "RARL".getBytes();
			puppIt(RARL, tick);
			break;
		case 41:
			byte[] LARR = "LARR".getBytes();
			puppIt(LARR, tick);
			break;
		case 42:
			byte[] LARL = "LARL".getBytes();
			puppIt(LARL, tick);
			break;
		case 43:
			byte[] RHRR = "RBRR".getBytes();
			puppIt(RHRR, tick);
			break;
		case 44:
			byte[] RHRL = "RBRL".getBytes();
			puppIt(RHRL, tick);
			break;
		case 45:
			byte[] LHRR = "LBRR".getBytes();
			puppIt(LHRR, tick);
			break;
		case 46:
			byte[] LHRL = "LBRL".getBytes();
			puppIt(LHRL, tick);
			break;
		case 47:
			byte[] RFRR = "RSRR".getBytes();
			puppIt(RFRR, tick);
			break;
		case 48:
			byte[] RFRL = "RSRL".getBytes();
			puppIt(RFRL, tick);
			break;
		case 49:
			byte[] LFRR = "LSRR".getBytes();
			puppIt(LFRR, tick);
			break;
		case 50:
			byte[] LFRL = "LSRL".getBytes();
			puppIt(LFRL, tick);
			break;
		case 51:
			byte[] HERR = "HERR".getBytes();
			puppIt(HERR, tick);
			break;
		case 52:
			byte[] HERL = "HERL".getBytes();
			puppIt(HERL, tick);
			break;
		}
	}
	class Action {
		private String name;
		public Action(String s) {
			name = s;
		}
		public String getName() {
			return name;
		}
	}
	public class AddMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		private Action[] al;
		private Sequence sq;
		private int[][] Grid;
		private int textX = 8;
		private int gridX = 128;
		private int topY = 32;
		private int xSize = 64;
		private int ySize = 22;
		public AddMenu(Action[] al, Sequence sequence, int[][] grid) {
			this.al = al;
			this.sq = sequence;
			this.Grid = grid;
			addMouseListener(new MyMouseListener());
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			Font font = new Font("Verdana", Font.BOLD, 12);
			g2d.setFont(font);
			for (int i = 0; i< al.length; i++) {
				g2d.drawString(al[i].getName(), textX, topY + i * ySize);
			}
			for (int i = 1; i < Grid.length; i++) {
				for (int j = 1; j < Grid[0].length; j++) {
					Rectangle r = new Rectangle(xSize, ySize);
					r.translate(gridX + j *xSize, topY + (i - 2) * ySize);
					g2d.draw(r);
					if (Grid[i][j] != 0) {
						g2d.fill(r);
					}
				}
			}
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.setPaint(Color.blue);
			for (int i = 0; i < 5; i++) {
				g2d.drawLine(
					gridX + xSize + i * xSize * 2,
					topY - ySize + 1,
					gridX + xSize + i * xSize * 2,
					11 + al.length * ySize
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
				if (c > 0 && c <= 8 && r > 0 && r < 19) {
					Grid[r][c] = Grid[r][c] == 1 ? 0 : 1;
				}
				repaint();
			}
		}
	}
}
