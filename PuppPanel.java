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
	int offset = 0;
	int curPuppet = 0;
	
	public PuppPanel(Array<Puppet> puppets, int offset) {
		this.setBackground(Color.BLACK);
		this.puppets = puppets;
		this.offset = offset;
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
				if(part != null) {// && part == Datatypes.Part.LEFT_LOWER_ARM) {
				Limb curLimb = (Limb) puppet.getLimbs().get(part);
				Joint upper = null;
				switch(part) {
				case RIGHT_LOWER_ARM : upper = puppet.getLimbs().get(Datatypes.Part.RIGHT_UPPER_ARM).getBottomJoint();
					break;
				case LEFT_LOWER_ARM : upper = puppet.getLimbs().get(Datatypes.Part.LEFT_UPPER_ARM).getBottomJoint();
					break;
				case RIGHT_LOWER_LEG : upper = puppet.getLimbs().get(Datatypes.Part.RIGHT_UPPER_LEG).getBottomJoint();
					break;
				case LEFT_LOWER_LEG : upper = puppet.getLimbs().get(Datatypes.Part.LEFT_UPPER_LEG).getBottomJoint();
					break;
				default : upper = curLimb.getTopJoint();
					break;
				}
				if(upper == null) 
					  upper = new Joint(offset * curPuppet,0, curLimb);
				if(upper != null) {
					AffineTransform at = new AffineTransform();
					at.translate((double) upper.getX() - 7, (double) upper.getY() + 5);
					at.scale(1.5,1.5);
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
					int width = (int) (curImage.getWidth(this) * 2);
					int height = (int) (curImage.getHeight(this));
					Joint topJoint = curLimb.getTopJoint();
					Joint botJoint = curLimb.getBottomJoint();
					g2d.setColor(Color.WHITE);
					g2d.drawImage(curImage, at, this);
					
				}
			}}
			curPuppet++;
			}
			g2d.dispose();
		}
	}
