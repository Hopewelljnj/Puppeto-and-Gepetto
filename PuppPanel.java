package edu.mccc.cos210.fp.pupp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PuppPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int FPS = 30;
	private double puppetOffset = 300;
	private Head Jacob = new Head("1");
	private Head Nick = new Head("2");
	private Head Dan = new Head("3");
	private Head Junfeng = new Head("4");
	private BufferedImage biAbs;
	private BufferedImage biTorso;
	private BufferedImage biLeftBicep;
	private BufferedImage biRightBicep;
	private BufferedImage biLeftArm;
	private BufferedImage biRightArm;
	private BufferedImage biLeftThigh;
	private BufferedImage biRightThigh;
	private BufferedImage biLeftShin;
	private BufferedImage biRightShin;
	private double LBicepRot = 0.0;
	private double RBicepRot = 0.0;
	private double LArmRot = 0.0;
	private double RArmRot = 0.0;
	private double LThighRot = 0.0;
	private double RThighRot = 0.0;
	private double LShinRot = 0.0;
	private double RShinRot = 0.0;
	public PuppPanel() {
		try {
			initParts();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
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
	private void initParts() throws IOException {
		BufferedImage bi = ImageIO.read(new File("./data/cut2.png"));
		
		for(int i = 0; i < 4; i++) {
			
			this.biAbs = new BufferedImage(68, 66, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = biAbs.createGraphics();
			AffineTransform at = new AffineTransform();
			at.translate(-147.0 + (puppetOffset * i), -9.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biTorso = new BufferedImage(65, 85, BufferedImage.TYPE_INT_ARGB);
			g2d = biTorso.createGraphics();
			at = new AffineTransform();
			at.translate(-71.0 + (puppetOffset * i), -10.0);
			g2d.drawRenderedImage(bi, at);

			/*this.biHead = new BufferedImage(64, 72, BufferedImage.TYPE_INT_ARGB);
			g2d = biHead.createGraphics();
			at = new AffineTransform();
			at.translate(7.0, -10.0);
			g2d.drawRenderedImage(bi, at);*/
			
			this.biLeftBicep = new BufferedImage(70, 90, BufferedImage.TYPE_INT_ARGB);
			g2d = biLeftBicep.createGraphics();
			at = new AffineTransform();
			at.translate(-80.0  + (puppetOffset * i), -10.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biRightBicep = new BufferedImage(70, 90, BufferedImage.TYPE_INT_ARGB);
			g2d = biRightBicep.createGraphics();
			at = new AffineTransform();
			at.translate(-60.0  + (puppetOffset * i), -10.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biLeftArm = new BufferedImage(72, 105, BufferedImage.TYPE_INT_ARGB);
			g2d = biLeftArm.createGraphics();
			at = new AffineTransform();
			at.translate(-80.0  + (puppetOffset * i), -40.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biRightArm = new BufferedImage(72, 105, BufferedImage.TYPE_INT_ARGB);
			g2d = biRightArm.createGraphics();
			at = new AffineTransform();
			at.translate(-60.0  + (puppetOffset * i), -40.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biLeftThigh = new BufferedImage(74, 100, BufferedImage.TYPE_INT_ARGB);
			g2d = biLeftThigh.createGraphics();
			at = new AffineTransform();
			at.translate(-70.0  + (puppetOffset * i), -60.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biRightThigh = new BufferedImage(74, 100, BufferedImage.TYPE_INT_ARGB);
			g2d = biRightThigh.createGraphics();
			at = new AffineTransform();
			at.translate(-65.0  + (puppetOffset * i), -60.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biLeftShin = new BufferedImage(77, 130, BufferedImage.TYPE_INT_ARGB);
			g2d = biLeftShin.createGraphics();
			at = new AffineTransform();
			at.translate(-70.0  + (puppetOffset * i), -100.0);
			g2d.drawRenderedImage(bi, at);
			
			this.biRightShin = new BufferedImage(77, 130, BufferedImage.TYPE_INT_ARGB);
			g2d = biRightShin.createGraphics();
			at = new AffineTransform();
			at.translate(-65.0  + (puppetOffset * i), -100.0);
			g2d.drawRenderedImage(bi, at);
		}		
}
	void updateRotations(String part) {
		switch(part) {
		case "LBicepRight" :
			LBicepRot += Math.toRadians(10.0);
			break;
		case "LBicepLeft" :
			LBicepRot -= Math.toRadians(10.0);
			break;
		case "RBicepRight" : 
			RBicepRot += Math.toRadians(10.0);
			break;
		case "RBicepLeft" : 
			RBicepRot -= Math.toRadians(10.0);
			break;
		case "LForeArmRight" :
			LArmRot += Math.toRadians(10.0);
			break;
		case "LForeArmLeft" :
			LArmRot -= Math.toRadians(10.0);
			break;
		case "RForeArmRight" :
			RArmRot += Math.toRadians(10.0);
			break;
		case "RForeArmLeft" :
			RArmRot -= Math.toRadians(10.0);
			break;
		case "LThighRight" :
			LThighRot += Math.toRadians(10.0);
			break;
		case "LThighLeft" :
			LThighRot -= Math.toRadians(10.0);
			break;
		case "RThighRight" :
			RThighRot += Math.toRadians(10.0);
			break;
		case "RThighLeft" :
			RThighRot -= Math.toRadians(10.0);
			break;
		case "LShinRight" :
			LShinRot += Math.toRadians(10.0);
			break;
		case "LShinLeft" :
			LShinRot -= Math.toRadians(10.0);
			break;
		case "RShinRight" :
			RShinRot += Math.toRadians(10.0);
			break;
		case "RShinLeft" :
			RShinRot -= Math.toRadians(10.0);
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

		g2d.dispose();
		
		Toolkit.getDefaultToolkit().sync();
	}
}	