package edu.mccc.cos210.fp.pupp;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Head {
	BufferedImage bi;
	BufferedImage head;
	double rot;

	public Head(String headnumber) {
		try {
			bi = ImageIO.read(new File("./data/cut1.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		switch(headnumber) {
		case "1" :
			head = bi.getSubimage(20, 40, 350, 520);
			break;
		case "2" :
			head = bi.getSubimage(400, 40, 350, 520);
			break;
		case "3" :
			head = bi.getSubimage(20, 630, 350, 520);
			break;
		case "4" :
			head = bi.getSubimage(400, 630, 350, 520);
		}
	}
}
