package edu.mccc.cos210.fp.pupp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import edu.mccc.cos210.ds.Array;

public class PuppPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public Array<Puppet> puppets = null;
	private static final int FPS = 30;
	BufferedImage bi;
	int curPuppet = 0;
	
	public PuppPanel(Array<Puppet> puppets) {
		this.puppets = puppets;
		this.setSize(1200,600);
		this.setBackground(Color.GRAY);
		this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		javax.swing.Timer t = new javax.swing.Timer(
				1000 / FPS,
				ae ->{
					repaint();
				}
			);
			t.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
			for(Puppet puppet : puppets) {
			for(Datatypes.Part part : puppet.getLimbs().keySet()) {
				if(part != null) {
				Limb curLimb = (Limb) puppet.getLimbs().get(part);
				Joint upper = curLimb.getTopJoint();
				if(upper != null) {
					AffineTransform at = new AffineTransform();
					at.translate((double) upper.getX() - 7, (double) upper.getY() + 5);
					at.scale(2.0, 2.0);
					at.rotate(curLimb.getRotation());
					switch(part) {
						case TORSO : at.scale(1.0, 1.5);
							break;
						case LEFT_UPPER_ARM : at.scale(0.8,0.75);
							break;
						case RIGHT_UPPER_ARM : at.scale(0.8,0.75);
							break;
						case LEFT_LOWER_ARM : at.scale(0.8,0.5);
							break;
						case RIGHT_LOWER_ARM : at.scale(0.8,0.5);
							break;
						case LEFT_UPPER_LEG : at.scale(0.5, 0.6);
							break;
						case RIGHT_UPPER_LEG : at.scale(0.5, 0.6);
							break;
						case LEFT_LOWER_LEG :  at.scale(0.5,0.6);
							break;
						case RIGHT_LOWER_LEG : at.scale(0.5,0.6);
							break;
						case HEAD : at.translate(5.0,0.0);
							at.scale(0.15,0.15);
							break;
						default : at = curLimb.getAT();
							break;
					}
					Image curImage = (Image)curLimb.image;
					g2d.drawImage(curImage, at, this);
					
				}
			}}
			curPuppet++;
			}
			g2d.dispose();
		}
	}
