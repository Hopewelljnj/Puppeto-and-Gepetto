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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.mccc.cos210.ds.ISortedList;
import edu.mccc.cos210.ds.Vector;
import edu.mccc.cos210.fp.pupp.MidiWriter;
import edu.mccc.cos210.fp.pupp.Stage.MyJPanel;


//===================================================================================================
//check file when load.... if file is not midi type but with extension name of midi .... => error...
//may delete action when use clear button....   done!!!!!!!!!!!
//clear one page save then read again === > error....
//===================================================================================================

public class Geppetto {
	private final String[] saction = {"init","RTRR","RTRL","LTRR",
							  		  "LTRL","RARR","RARL","LARR",
							  		  "LARL","RBRR","RBRL","LBRR",
							  		  "LBRL","RSRR","RSRL","LSRR",
							  		  "LSRL","HERR","HERL"};
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
	};
	private static int[][] Grid = {
			{ 00, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 35, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 36, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 37, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 38, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 39, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 40, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 41, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 42, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 43, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 44, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 45, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 46, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 47, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 48, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 49, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
			{ 50, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,},
	};
	private static File song;
	private int beatNumber = 1;
	private int measureNumber = 1;
	private JFrame jf;
	private ImageIcon ico = new ImageIcon("images/icon.png");
	private FileDialog fd = new FileDialog(jf, "Save As", FileDialog.SAVE);
	private FileDialog load = new FileDialog(jf, "Load File", FileDialog.LOAD);
	private MidiEditor edit; 

	private void initSwing() {
		jf = new JFrame("Geppetto");
		jf.addWindowListener(new MyWindowListener());
		fd.setVisible(false);
		JPanel jp = new JPanel();
		JPanel jpx = new JPanel();
		jpx.setLayout(new BorderLayout());
		jpx.add(new MyJPanel (Color.BLUE), BorderLayout.WEST);
		jpx.add(new MyJPanel (Color.BLUE), BorderLayout.EAST);
		AddMenu am = new AddMenu(ActionList, Grid);
		jp.setLayout(new GridLayout(1, 4));
		JButton jb = new JButton("ClearMidi");
		jb.addActionListener(
			ae -> {
				this.resetGrid();
				this.edit = null;
				am.repaint();
			}		
		);
		jp.add(jb);
		JButton jb2 = new JButton("Clear!");
		jb2.addActionListener(     
			ae -> {
				this.resetGrid();
				this.clearActionInMidi();
				am.repaint();
			}		
		);
		jp.add(jb2);
		jb = new JButton("Save!");
		jb.addActionListener(
			ae -> {
				try {
					fd.setVisible(true);
					if (fd.getFile() != null && edit != null) {
						@SuppressWarnings("unused")
						MidiWriter myWriter = new MidiWriter(
											saction,
											edit,
											new File(
													fd.getDirectory(),
													fd.getFile()
												));

					} else {
						JOptionPane.showMessageDialog(null, "Choose a midi file first...", "Error", JOptionPane.ERROR_MESSAGE); 
					}
				} catch (Exception ex) {
					System.err.println("cao2");
					System.err.println(ex.getMessage());
					System.exit(-1);
				}
			}		
		);
		jp.add(jb);
		jb = new JButton("Load!");
		jb.addActionListener(
				ae -> {
					try {
						this.edit = null;
						load.setVisible(true);
						if(load.getFile() != null) {
							String file = load.getFile();
							if(file.contains(".mid") || file.contains(".midi")) {
								song = new File(load.getDirectory(), load.getFile());
								edit = new MidiEditor(song);
								calcGrid(edit.getCurrentList(),edit.getPointer(),edit.getResolution());
								jf.repaint();														
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
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
					if (edit != null) {
						int pre = edit.getPointer();
						calcGrid(edit.getPreList(),edit.getPointer(),edit.getResolution());
						if (pre == edit.getPointer()) {
							JOptionPane.showMessageDialog(null, "NO PREVIOUS.....", "Error", JOptionPane.ERROR_MESSAGE); 
							beatNumber = beatNumber + 4;
							measureNumber++;
						}
						beatNumber = beatNumber - 4;
						measureNumber--;
						am.repaint();
						jf.repaint();
					} else {
						JOptionPane.showMessageDialog(null, "LOAD MIDI!!!!!", "Error", JOptionPane.ERROR_MESSAGE); 
					}
				}
			);
		np.add(npb);
		npb = new JButton("Next");
		npb.addActionListener(
				ae -> {
					if (edit != null) {
						int pre = edit.getPointer();
						calcGrid(edit.getnextList(),edit.getPointer(),edit.getResolution());
						if (pre == edit.getPointer()) {
							JOptionPane.showMessageDialog(null, "NO NEXT....", "Error", JOptionPane.ERROR_MESSAGE);
							beatNumber = beatNumber - 4;
							measureNumber--;
						}
						beatNumber = beatNumber + 4;
						measureNumber++;
						am.repaint();
						jf.repaint();    
					} else {
						JOptionPane.showMessageDialog(null, "LOAD MIDI!!!!!", "Error", JOptionPane.ERROR_MESSAGE); 				
					}
				}
			);
		np.add(npb);
		jpx.add(np, BorderLayout.SOUTH);
		jpx.add(jp, BorderLayout.NORTH);
		jpx.add(am, BorderLayout.CENTER);
		jf.add(jpx);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		jf.setUndecorated(false);
		jf.setIconImage(ico.getImage());
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
		
	}
	private void calcGrid(ISortedList<TickNode> current, int pointer, int resolution) {
		this.resetGrid();
		if ((current.getSize() != 0) && (((long)(pointer * 4 * resolution) <= (current.getFirst().getTick())) 
										&& ((long)((pointer + 1) * 4 * resolution) > (current.getFirst().getTick())))) {
			for(TickNode a : current) {
				Vector<Integer> actions = a.getAction();
				double interval = 4 * resolution;
				int column = (int)(((a.getTick() - (pointer * 4 * resolution)) / interval) * 32);   //  multi-track WILL BE FAILED HERE.
				column += 1; 
				for(int b : actions) {
					if (b < 0) {
						Grid[Math.abs(b)][column] = -1;
					} else {
						Grid[b][column] = 1;
					}
				}
			}
		}
	}
	public void clearActionInMidi() {
		ISortedList<TickNode> current = edit.getCurrentList();
		for (TickNode tc : current) {
			tc.setAction(new Vector<Integer>());
		}
	}
	public void resetGrid() {
		for (int i = 1; i < Grid.length; i++) {
			for (int j = 1; j < Grid[0].length; j++) {
				Grid[i][j] = 0;
			}
		}	
	}
	private class MyWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent we) {
			System.exit(-1);
		}
	}
	public Geppetto() {
		initSwing();
	}
	public static void main(String... args) {
		EventQueue.invokeLater(Geppetto::new);
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
		private int textX = 8;
		private int gridX = 160;
		private int topY = 64;
		private int xSize = 32;
		private int ySize = 40;
		public AddMenu(Action[] al, int[][] grid) {
			this.al = al;
			addMouseListener(new MyMouseListener());
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			Font font = new Font("Comic Sans", Font.BOLD, 12);
			g2d.setFont(font);
			for (int i = 0; i < al.length; i++) {
				g2d.drawString(al[i].getName(), textX, topY + i * ySize - 15);
			}
			Font numberfont = new Font("Comic Sans", Font.BOLD, 64);
			g2d.setFont(numberfont);
			for(int i = 0; i < 4; i++) {
				int number = beatNumber + i;
				g2d.drawString(Integer.toString(number), 180 + 260 * i, 740);
			}
			g2d.setPaint(Color.RED);
			g2d.drawString(Integer.toString(measureNumber), 1250 , 80);
			g2d.setPaint(Color.BLACK);
			for (int i = 1; i < Grid.length; i++) {
				for (int j = 1; j < Grid[0].length; j++) {
					Rectangle r = new Rectangle(xSize, ySize);
					r.translate(gridX + j *xSize, topY + (i - 2) * ySize);
					g2d.draw(r);
					 								
					if (Grid[i][j] == -1) {
						g2d.setColor(Color.red);
						g2d.fill(r);
						g2d.setColor(Color.black);
					} else {
						if (Grid[i][j] != 0) {
							g2d.fill(r);
						}
					} 								
				}
			}
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.setPaint(Color.blue);
			for (int i = 0; i < 5; i++) {
				g2d.drawLine(
					gridX + xSize + i * xSize * 8,
					topY - ySize + 1,
					gridX + xSize + i * xSize * 8,
					22 + al.length * ySize
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
				if (c > 0 && c <= 32 && r > 0 && r < 19) {
					Grid[r][c] = Grid[r][c] == 1 ? 0 : 1;
					if(edit != null) {
						edit.updateAction(Grid[r][c],r,calcTick(c));
					}
				}
				repaint();
			}
			public long calcTick(int c) {
				if(edit != null) {
					return (long)((c / 32.0) * 4 * edit.getResolution() + (4 * edit.getPointer() * edit.getResolution()) - edit.getResolution() / 8);
				} else {
					return -1;
				}
			}
		}
	}
	class MyJPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public MyJPanel(Color c) {
			this.setBackground(c);
			this.setPreferredSize(new Dimension (100, 100));
		}
		
		
	}
}
