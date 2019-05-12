package edu.mccc.cos210.fp.pupp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PuppPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int FPS = 30;
	private Head Jacob = new Head("1");
	private Limb JaNeck = new Limb(new RoundRectangle2D.Double(-455, 200, 20, 32, 15, 15));
	private Limb JaTorso = new Limb(new RoundRectangle2D.Double(-493, 140, 100, 200, 15, 15));
	private Limb JaLeftBicep = new Limb(new Ellipse2D.Double(-525, 200, 32, 128));
	private Limb JaRightBicep = new Limb(new Ellipse2D.Double(-395, 200, 32, 128));
	private Limb JaLeftForeArm = new Limb(new Ellipse2D.Double(-525, 5, 30, 100));
	private Limb JaRightForeArm = new Limb(new Ellipse2D.Double(-392, 5, 30, 100));
	private Limb JaLeftThigh = new Limb(new RoundRectangle2D.Double(-492, 10, 32, 128, 10, 10));
	private Limb JaRightThigh = new Limb(new RoundRectangle2D.Double(-425, 10, 32, 128, 10, 10));
	private Limb JaLeftShin = new Limb(new RoundRectangle2D.Double(-492, -200, 32, 128, 10, 10));
	private Limb JaRightShin = new Limb(new RoundRectangle2D.Double(-425, -200, 32, 128, 10, 10));
	public PuppPanel() {
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
	void updateRotations(String part) {
		double rot = Math.toRadians(10.0);
		switch(part) {
		case "LBicepRight" :
			JaLeftBicep.rot = JaLeftBicep.rot + rot;
			//rest of other puppets left biceps
			break;
		case "LBicepLeft" :
			JaLeftBicep.rot = JaLeftBicep.rot - rot;
			//rest of other puppets left biceps
			break;
		case "RBicepRight" : 
			JaRightBicep.rot = JaRightBicep.rot + rot;
			break;
		case "RBicepLeft" : 
			JaRightBicep.rot = JaRightBicep.rot - rot;
			break;
		case "LForeArmRight" :
			JaLeftForeArm.rot = JaLeftForeArm.rot + rot;
			break;
		case "LForeArmLeft" :
			JaLeftForeArm.rot = JaLeftForeArm.rot - rot;
			break;
		case "RForeArmRight" :
			JaRightForeArm.rot = JaRightForeArm.rot + rot;
			break;
		case "RForeArmLeft" :
			JaRightForeArm.rot = JaRightForeArm.rot - rot;
			break;
		case "LThighRight" :
			JaLeftThigh.rot = JaLeftThigh.rot + rot;
			break;
		case "LThighLeft" :
			JaLeftThigh.rot = JaLeftThigh.rot - rot;
			break;
		case "RThighRight" :
			JaRightThigh.rot = JaRightThigh.rot + rot;
			break;
		case "RThighLeft" :
			JaRightThigh.rot = JaRightThigh.rot - rot;
			break;
		case "LShinRight" :
			JaLeftShin.rot = JaLeftShin.rot + rot;
			break;
		case "LShinLeft" :
			JaLeftShin.rot = JaLeftShin.rot - rot;
			break;
		case "RShinRight" :
			JaRightShin.rot = JaRightShin.rot + rot;
			break;
		case "RShinLeft" :
			JaRightShin.rot = JaRightShin.rot - rot;
			break;
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		AffineTransform gat = new AffineTransform();
		gat.translate(getWidth() / 2.0, getHeight() / 2.0);
		gat.scale(1.0, -1.0);
		g2d.transform(gat);
		g2d.setStroke(new BasicStroke(3.0f));
		g2d.setPaint(Color.BLACK);
		
		AffineTransform hat = new AffineTransform();
		hat.translate(-400, 350);
		hat.scale(0.25, 0.25);
		hat.rotate(Math.PI);
		g2d.draw(JaNeck.shape);
		g2d.fill(JaNeck.shape);
		g2d.drawImage(Jacob.head, hat, this);
		
		Path2D innerLimbs = new Path2D.Double();
		innerLimbs.append(JaNeck.shape, false);
		innerLimbs.append(JaTorso.shape, false);
		innerLimbs.append(JaLeftThigh.shape, false);
		innerLimbs.append(JaRightThigh.shape, false);
		innerLimbs.append(JaLeftBicep.shape, false);
		innerLimbs.append(JaRightBicep.shape, false);
		
		AffineTransform iat = new AffineTransform();
		iat.translate(0.0, -128.0);
		iat.rotate(JaLeftThigh.rot);
		iat.rotate(JaRightThigh.rot);
		iat.rotate(JaLeftBicep.rot);
		iat.rotate(JaRightBicep.rot);
		Shape base = iat.createTransformedShape(innerLimbs);
		
		Path2D outerLimbs = new Path2D.Double();
		outerLimbs.append(base, false);
		outerLimbs.append(JaLeftShin.shape, false);
		outerLimbs.append(JaRightShin.shape, false);
		outerLimbs.append(JaLeftForeArm.shape, false);
		outerLimbs.append(JaRightForeArm.shape, false);
		
		AffineTransform oat = new AffineTransform();
		oat.rotate(JaLeftShin.rot);
		oat.rotate(JaRightShin.rot);
		oat.rotate(JaLeftForeArm.rot);
		oat.rotate(JaRightForeArm.rot);
		Shape puppet = oat.createTransformedShape(outerLimbs);
		
		g2d.draw(puppet);
		g2d.fill(puppet);
		
		g2d.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
	protected BufferedImage applyTransform(BufferedImage bi, AffineTransform atx, int interpolationType){
		Dimension d = getSize();
		BufferedImage displayImage = new BufferedImage(d.width, d.height, interpolationType);
		Graphics2D dispGc = displayImage.createGraphics();
		dispGc.drawImage(bi, atx, this);
		return displayImage;
}
}	