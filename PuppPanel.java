package edu.mccc.cos210.fp.pupp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.mccc.cos210.ds.Array;

public class PuppPanel extends JPanel {
	public Array<Puppet> puppets = new Array<>(4);
	BufferedImage bi;
	
	public PuppPanel(Array<Puppet> puppets) {
		this.puppets = puppets;
		javax.swing.Timer t = new javax.swing.Timer(
				100,
				ae -> repaint()
			);
			t.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		for(int i = 0; i < 4; i++) {
			try {
			for(Datatypes.Part part : puppets.get(i).getLimbs().keySet()) {
				if(part != null) {
				Limb curLimb = (Limb) puppets.get(i).getLimbs().get(part);
				if(curLimb != null) {
				Joint upper = curLimb.getTopJoint();
				Joint lower = curLimb.getBottomJoint();
				if(upper != null && lower != null)
				g2d.drawImage(curLimb.image, upper.getX(), upper.getY(), lower.getX(), lower.getY(), 0, 0, curLimb.image.getWidth(), curLimb.image.getHeight(), this);
			}}}
			}
			catch(Exception e) {}
			
		}
	}
}
