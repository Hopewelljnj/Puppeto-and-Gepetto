package edu.mccc.cos210.fp.pupp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


public class Stage {
	private JFrame jf;
	private ImageIcon ico = new ImageIcon("images/icon.png");
	private JToggleButton jtb = new JToggleButton("Play");
	private FileDialog fd = new FileDialog(jf, "Load File", FileDialog.LOAD);
	public Stage() {
		initswing();
	}

	private void initswing() {
		jf = new JFrame("Puppetto");
		jf.addWindowListener(new MyWindowListener());
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		jf.setUndecorated(false);
		jf.setIconImage(ico.getImage());
		JPanel jpx = new JPanel();
		jpx.setLayout(new BorderLayout());
		jpx.add(new MyJPanel (new Color(198, 25, 0)), BorderLayout.WEST);
		jpx.add(new MyJPanel (new Color(198, 25, 0)), BorderLayout.EAST);
		PuppPanel pup = new PuppPanel();
		jpx.add(pup);
		JPanel np = new JPanel();
		np.setLayout(new GridLayout(1, 2));
		JButton jb = new JButton("Load!");
		jb.addActionListener(
				ae -> {
					try {	
						fd.setVisible(true);
						if(fd.getFile() != null) {
							String file = fd.getFile();
							if(file.contains(".mid") || file.contains(".midi")) {
								if(MidiReader.sequencer != null) {
								if (MidiReader.sequencer.isRunning()) {
									jtb.setText("Play");
									MidiReader.sequencer.stop();
								}}
								new MidiReader(pup, new File(fd.getDirectory(),fd.getFile()));
								//pup.reset();
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.exit(-1);
					}
				}	
				);
		np.add(jb);
		jtb.addActionListener(
				ae -> {
					if (ae.getSource() instanceof JToggleButton) {
						try {
							if (!jtb.isSelected()) {
								if (MidiReader.sequencer.isRunning()) {
									jtb.setText("Play");
									MidiReader.sequencer.stop();
								}
							} else {
								if (!MidiReader.sequencer.isRunning()) {
									jtb.setText("Stop");
								MidiReader.sequencer.start();
								}
							}
						} catch(NullPointerException exp) {
							JOptionPane.showMessageDialog(null, "Choose a midi file first!", "Error", JOptionPane.ERROR_MESSAGE);
						}
						catch (Exception ex) {
							System.err.println(ex.getMessage());
							System.exit(-1);
					}
				}
			}
		);
		np.add(jtb);
		jpx.add(np, BorderLayout.SOUTH);
		jf.add(jpx, BorderLayout.CENTER);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
	}
	private class MyWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent e) {
			if(MidiReader.sequencer != null) {
				MidiReader.sequencer.close();
			}
			if(MidiReader.synth != null) {
				MidiReader.synth.close();
			}
			System.exit(-1);
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