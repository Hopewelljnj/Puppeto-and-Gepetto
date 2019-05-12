package edu.mccc.cos210.fp.pupp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import edu.mccc.cos210.ds.Array;
import edu.mccc.cos210.ds.IMap;
import edu.mccc.cos210.ds.Map;

public class Stage {
	private JFrame jf;
	private ImageIcon ico = new ImageIcon("images/icon.png");
	private JToggleButton jtb = new JToggleButton("Play");
	private FileDialog fd = new FileDialog(jf, "Load File", FileDialog.LOAD);
	private BufferedImage bi;
	private BufferedImage bi2;
	private boolean debug = true;
	public Array<Puppet> puppets = new Array<>(4);
	public Stage() {
		initswing();
		createPuppets();
	}

	private void initswing() {
		jf = new JFrame("Puppetto");
		jf.addWindowListener(new MyWindowListener());
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		jf.setUndecorated(false);
		jf.setIconImage(ico.getImage());
		JPanel jpx = new JPanel();
		jpx.setLayout(new BorderLayout());
		jpx.add(new MyJPanel (new Color(200, 80, 80)), BorderLayout.WEST);
		jpx.add(new MyJPanel (new Color(200, 80, 80)), BorderLayout.EAST);
		jpx.add(new MyJPanel (Color.GRAY), BorderLayout.SOUTH);
		jpx.add(new PuppPanel(puppets), BorderLayout.CENTER);
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
								createPuppets();
								if(MidiReader.sequencer != null) {
								if (MidiReader.sequencer.isRunning()) {
									jtb.setText("Play");
									MidiReader.sequencer.stop();
								}}
								new MidiReader(this, new File(fd.getDirectory(),fd.getFile()));
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
	
	private void createPuppets() {
		IMap<Datatypes.Joint, Joint> joints = new Map<>();
		IMap<Datatypes.Part, ILimb> limbs = new Map<>();
		
		try {
			bi = ImageIO.read(new File("./data/cut2.png"));
			bi2 = ImageIO.read(new File("./data/cut1.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		for(int i = 0;i < 4; i ++) {
			limbs = null;
			joints = null;
			joints = new Map<>();
			limbs = new Map<>();
			int puppetOffset = 280*i + 145;
			Joint topHead = new Joint(60 + puppetOffset, 32, null);
			Limb head;
			switch(i) {
			case 0 : head = new Limb(0, topHead, bi2.getSubimage(20, 40, 350, 520));
				break;
			case 1 : head = new Limb(0, topHead, bi2.getSubimage(400, 40, 350, 520));
				break;
			case 2 : head = new Limb(0, topHead, bi2.getSubimage(20, 630, 350, 520));
				break;
			case 3 : head = new Limb(0, topHead, bi2.getSubimage(400, 630, 350, 520));
				break;
			default : head = new Limb(0, topHead, bi2.getSubimage(4, 8, 44, 74));
			}
			Joint neck = new Joint(50 + puppetOffset, 172, head);
			Limb torso = new Limb(0, neck, bi.getSubimage(66, 8, 70, 94));
			Joint lShoulder = new Joint(40 + puppetOffset, 210, torso);
			Joint rShoulder = new Joint(160 + puppetOffset, 220, torso);
			Limb rUpperArm = new Limb(Math.toRadians(-15), rShoulder, bi.getSubimage(246, 10, 28, 100));
			Limb lUpperArm = new Limb(Math.toRadians(15), lShoulder, bi.getSubimage(246, 10, 28, 100));
			Joint rElbow = new Joint(200 + puppetOffset, 355, rUpperArm);
			Joint lElbow = new Joint(8 + puppetOffset, 355, lUpperArm);
			Limb lLowerArm = new Limb(0 , lElbow, null, bi.getSubimage(300, 8, 22, 148));
			Limb rLowerArm = new Limb(0, rElbow, null, bi.getSubimage(300, 8, 22, 148));
			Hip hip = new Hip(85 + puppetOffset, neck.getY() + 260, torso, bi.getSubimage(146, 8, 70, 68));
			Joint lHip = new Joint(85 + puppetOffset, neck.getY() + 260, torso);
			Limb lUpperLeg = new Limb(0, hip.getLeftHip(), null, bi.getSubimage(344, 10, 36, 142), Datatypes.Part.LEFT_UPPER_LEG);
			Limb rUpperLeg = new Limb(0, hip.getRightHip(), null, bi.getSubimage(344, 10, 36, 142), Datatypes.Part.RIGHT_UPPER_LEG);
			Joint lKnee = new Joint(85 + puppetOffset, hip.getY() + 160, lUpperLeg);
			Joint rKnee = new Joint(127 + puppetOffset, hip.getY() + 160, rUpperLeg);
			Limb lLowerLeg = new Limb(0, lKnee, null, bi.getSubimage(398, 10, 34, 162), Datatypes.Part.LEFT_LOWER_LEG);
			Limb rLowerLeg = new Limb(0, rKnee, null, bi.getSubimage(398, 10, 34, 162), Datatypes.Part.RIGHT_LOWER_LEG);
			lShoulder.setUpperLimb(torso);
			rShoulder.setUpperLimb(torso);
			lShoulder.setLowerLimb(lUpperArm);
			rShoulder.setLowerLimb(rUpperArm);
			lUpperArm.setTopJoint(lShoulder);
			rUpperArm.setTopJoint(rShoulder);
			lUpperArm.setBottomJoint(lElbow);
			rUpperArm.setBottomJoint(rElbow);
			lUpperArm.setLowerLimb(lLowerArm);
			rUpperArm.setLowerLimb(rLowerArm);
			rElbow.setUpperLimb(rUpperArm);
			lElbow.setUpperLimb(lUpperArm);
			rElbow.setLowerLimb(rLowerArm);
			lElbow.setLowerLimb(lLowerArm);
			lLowerArm.setTopJoint(lElbow);
			rLowerArm.setTopJoint(rElbow);
			lUpperLeg.setTopJoint(lHip);
			rUpperLeg.setTopJoint(hip.getRightHip());
			hip.setLeftLowerLimb(lUpperLeg);
			hip.setRightLowerLimb(rUpperLeg);
			hip.setUpperLimb(torso);
			lHip.setUpperLimb(torso);
			lHip.setLowerLimb(lUpperLeg);
			hip.setLowerLimb(rUpperLeg);
			lUpperLeg.setBottomJoint(lKnee);
			rUpperLeg.setBottomJoint(rKnee);
			rKnee.setUpperLimb(rUpperLeg);
			lKnee.setUpperLimb(lUpperLeg);
			rKnee.setLowerLimb(rLowerLeg);
			lKnee.setLowerLimb(lLowerLeg);
			lLowerLeg.setTopJoint(lKnee);
			rLowerLeg.setTopJoint(rKnee);
			
			joints.put(Datatypes.Joint.NECK, neck);
			joints.put(Datatypes.Joint.LEFT_SHOULDER, lShoulder);
			joints.put(Datatypes.Joint.RIGHT_SHOULDER, rShoulder);
			joints.put(Datatypes.Joint.LEFT_ELBOW, lElbow);
			joints.put(Datatypes.Joint.RIGHT_ELBOW, rElbow);
			joints.put(Datatypes.Joint.LHIP, lHip);
			joints.put(Datatypes.Joint.RHIP, hip);
			joints.put(Datatypes.Joint.LEFT_KNEE, lKnee);
			joints.put(Datatypes.Joint.RIGHT_KNEE, rKnee);
			
			limbs.put(Datatypes.Part.HEAD, head);
			limbs.put(Datatypes.Part.TORSO, torso);
			limbs.put(Datatypes.Part.LEFT_UPPER_ARM, lUpperArm);
			limbs.put(Datatypes.Part.RIGHT_UPPER_ARM, rUpperArm);
			limbs.put(Datatypes.Part.LEFT_LOWER_ARM, lLowerArm);
			limbs.put(Datatypes.Part.RIGHT_LOWER_ARM, rLowerArm);
			limbs.put(Datatypes.Part.LEFT_UPPER_LEG, lUpperLeg);
			limbs.put(Datatypes.Part.RIGHT_UPPER_LEG, rUpperLeg);
			limbs.put(Datatypes.Part.LEFT_LOWER_LEG, lLowerLeg);
			limbs.put(Datatypes.Part.RIGHT_LOWER_LEG, rLowerLeg);
			
			
			if(debug) {
			for(Datatypes.Part part : limbs.keySet()) {
				System.out.print(part + "\n");
				limbs.get(part).calcDistance();
				System.out.println(limbs.get(part));
				System.out.println();
			}}
			puppets.set(i, new Puppet(("Puppet" + i), limbs, joints));
			}
	}
	public void rotatePuppetLimb(int puppetIndex, Datatypes.Joint joint, double rotation) {
		for(puppetIndex = 0; puppetIndex < 5; puppetIndex ++) {
		Puppet curPup = puppets.get(puppetIndex);
		Puppet newPup = curPup.doRotate(joint, rotation);
		puppets.set(puppetIndex, newPup);
		}
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