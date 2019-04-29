package edu.mccc.cos210.fp.pupp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import edu.mccc.cos210.ds.Array;

public class PuppPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Array<Puppet> puppets = new Array<>(4);
	BufferedImage bi;
	int offset = 0;
	int curPuppet = 0;
	
	public PuppPanel(Array<Puppet> puppets, int offset) {
		this.setBackground(Color.BLACK);
		this.puppets = puppets;
		this.offset = offset;
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
			for(Puppet puppet : puppets) {
			for(Datatypes.Part part : puppet.getLimbs().keySet()) {
				if(part != null) {// && part == Datatypes.Part.LEFT_LOWER_ARM) {
				Limb curLimb = (Limb) puppet.getLimbs().get(part);
				Joint upper = curLimb.getTopJoint();
				Joint lower = curLimb.getBottomJoint();
				if(upper == null) 
					upper = new Joint(offset * curPuppet,0, curLimb);
					//g2d.drawImage((Image) curLimb.image, curLimb.getAT(), this);
					//g2d.drawImage((Image)curLimb.image, 45, 25, lower.getX() + curLimb.image.getWidth(), lower.getY() + 40, 0, 0, curLimb.image.getWidth(), curLimb.image.getHeight(), this);
				if(upper != null) {
					AffineTransform at = new AffineTransform();
					at.translate((double) upper.getX() - 7, (double) upper.getY() + 5);
					at.scale(1.5,1.5);
					at.rotate(curLimb.getRotation());
					switch(part) {
						case TORSO : at.scale(1.0, 1.5);
							break;
						case LEFT_UPPER_ARM :
						case RIGHT_UPPER_ARM : at.scale(0.8,0.75);
							break;
						case LEFT_LOWER_ARM :
						case RIGHT_LOWER_ARM : at.scale(0.8,0.5);
							break;
						case LEFT_UPPER_LEG :
						case RIGHT_UPPER_LEG : at.scale(0.5, 0.6);
							break;
						case LEFT_LOWER_LEG :
						case RIGHT_LOWER_LEG : at.scale(0.5,0.6);
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
					//g2d.drawImage(curImage, topJoint.getX() + width/2, topJoint.getY(), topJoint.getX() - width/2, botJoint.getY(), 0, 0, curImage.getWidth(this), curImage.getHeight(this), this);
				}
					//g2d.drawImage((Image)curLimb.image, upper.getX() - upper.getX()/2, upper.getY() - upper.getY()/2, lower.getX() + lower.getX()/2, lower.getY( + lower.getY()/2), 0, 0, curLimb.image.getWidth(), curLimb.image.getHeight(), this);
			}}
			curPuppet++;
			}
			//g2d.drawLine(100, 100, 100, 235);
			g2d.dispose();
		}
	}
