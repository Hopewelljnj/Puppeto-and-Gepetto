package edu.mccc.cos210.fp.pupp;

import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.mccc.cos210.ds.Array;
import edu.mccc.cos210.ds.IMap;

public class Stage {
	private static final String SONG = "data/yup.mid";
	private static final int META_EndofTrack = 47;
	private static final int META_Data = 127;
	private Synthesizer synth;
	private Sequencer sequencer;
	private Sequence sequence;
	private JFrame jf;
	private ImageIcon ico = new ImageIcon("images/icon.png");
	private FileDialog fd = new FileDialog(jf, "Load File", FileDialog.LOAD);
	private BufferedImage bi;
	public Array<Puppet> puppets = new Array<>(4);
	public Stage() {
		createPuppets();
		initswing();
	}
	//Insert real numbers in here once we have an idea of sizes
	private void initswing() {
		jf = new JFrame("Puppetto");
		JPanel jp = new PuppPanel(puppets);
		jp.setSize(800,600);
		jf.setSize(800,600);
		jf.add(jp);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setVisible(true);
		try {
			bi = ImageIO.read(new File("./images/dinos/Dinos.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		
		
		
		
		
		fd.setVisible(true);
		if (fd.getFile() != null) {
			String file = fd.getFile();
			
			if(file.contains(".mid") || file.contains(".midi")) {
				System.out.println("yerp!");
				new MidiReader(this, new File(fd.getDirectory(),fd.getFile()));
			}
		}

	}
	private void createPuppets() {
		try {
			bi = ImageIO.read(new File("./data/cut2.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		for(int i = 0;i < 4; i ++) {
			int puppetOffset = 200*i;
			Limb head = new Limb(bi.getSubimage(4, 8, 44, 74));
			Joint neck = new Joint(100 + puppetOffset, 100, head);
			Limb torso = new Limb(0, neck, bi.getSubimage(66, 8, 70, 84));
			Joint lShoulder = new Joint(100 + puppetOffset,120,torso);
			Joint rShoulder = new Joint(165 + puppetOffset,120,torso);
			Limb rUpperArm = new Limb(0, rShoulder, bi.getSubimage(246, 10, 28, 100));
			Limb lUpperArm = new Limb(0, lShoulder, bi.getSubimage(246, 10, 28, 100));
			Joint rElbow = new Joint(25 + puppetOffset, 150, rUpperArm);
			Joint lElbow = new Joint(175 + puppetOffset, 150, lUpperArm);
			Limb lLowerArm = new Limb(0 , lElbow, bi.getSubimage(300, 8, 22, 148));
			Limb rLowerArm = new Limb(0, rElbow, bi.getSubimage(300, 8, 22, 148));
			//hands?
			Hip hip = new Hip(100 + puppetOffset, neck.getY() + 135, torso, bi.getSubimage(146, 8, 70, 68));
			Limb lUpperLeg = new Limb(0, hip, bi.getSubimage(344, 10, 36, 142));
			Limb rUpperLeg = new Limb(0, hip, bi.getSubimage(344, 10, 36, 142));
			hip.setLeftLowerLimb(lUpperLeg);
			hip.setrLowerLimb(rUpperLeg);
			Joint lKnee = new Joint(50 + puppetOffset, 450, lUpperLeg);
			Joint rKnee = new Joint(150 + puppetOffset, 450, rUpperLeg);
			Limb lLowerLeg = new Limb(0 + puppetOffset, lKnee, bi.getSubimage(398, 10, 34, 162));
			Limb rLowerLeg = new Limb(0 + puppetOffset, rKnee, bi.getSubimage(398, 10, 34, 162));
			//feet?
			
			IMap<Datatypes.Joint, Joint> joints = new edu.mccc.cos210.ds.Map<>();
			IMap<Datatypes.Part, ILimb> limbs = new edu.mccc.cos210.ds.Map<>();
			
			joints.put(Datatypes.Joint.NECK, neck);
			joints.put(Datatypes.Joint.LEFT_SHOULDER, lShoulder);
			joints.put(Datatypes.Joint.RIGHT_SHOULDER, rShoulder);
			joints.put(Datatypes.Joint.LEFT_ELBOW, lElbow);
			joints.put(Datatypes.Joint.RIGHT_ELBOW, rElbow);
			joints.put(Datatypes.Joint.HIP, hip);
			joints.put(Datatypes.Joint.LEFT_KNEE, lKnee);
			joints.put(Datatypes.Joint.RIGHT_KNEE, rKnee);
			
			limbs.put(Datatypes.Part.HEAD, head);
			limbs.put(Datatypes.Part.TORSO, torso);
			limbs.put(Datatypes.Part.LEFT_UPPER_ARM, lUpperArm);
			limbs.put(Datatypes.Part.RIGHT_UPPER_ARM, rUpperArm);
			limbs.put(Datatypes.Part.LEFT_LOWER_ARM, lLowerArm);
			limbs.put(Datatypes.Part.RIGHT_LOWER_ARM, rLowerArm);
			limbs.put(Datatypes.Part.LEFT_UPPER_LEG, rUpperLeg);
			limbs.put(Datatypes.Part.RIGHT_UPPER_LEG, rUpperLeg);
			limbs.put(Datatypes.Part.LEFT_LOWER_LEG, lLowerLeg);
			limbs.put(Datatypes.Part.RIGHT_LOWER_LEG, rLowerLeg);
			
			puppets.set(i, new Puppet(("Puppet" + i), limbs, joints));
			
			
		}
	}
	/*public static void main(String... args) {
		new MidiReader();
		new Stage();
	} */
}