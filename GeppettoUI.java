package edu.mccc.cos210.fp.pupp;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GEPUI {
	public GEPUI() {
		initSwing();
	}
	public static void main(String... args) {
		EventQueue.invokeLater(GEPUI::new);
	}
	private void initSwing() {
		JFrame jf = new JFrame("Geppetto");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel jp = new DrawPanel();
		jf.add(jp, BorderLayout.CENTER);
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
		ImageIcon icon = new ImageIcon("data/Gort-Gorts-Icons-Vol4-Peppy-The-Puppet.ico");
		jf.setIconImage(icon.getImage());
	}
	static class DrawPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private static final int FPS = 60;
		public DrawPanel() {
			setBackground(Color.WHITE);
			setPreferredSize(new Dimension(800, 600));
		}
	}
}
