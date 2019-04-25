package edu.mccc.cos210.fp.pupp;

public class Datatypes {
	
	public enum Part {
		LEFT_UPPER_ARM,
		LEFT_LOWER_ARM,
		RIGHT_UPPER_ARM,
		RIGHT_LOWER_ARM,
		HEAD,
		LEFT_UPPER_LEG,
		LEFT_LOWER_LEG,
		RIGHT_UPPER_LEG,
		RIGHT_LOWER_LEG,
		LEFT_HAND,
		RIGHT_HAND,
		LEFT_FOOT,
		RIGHT_FOOT,
		TORSO
	}
	
	public enum Joint {
		LEFT_SHOULDER,
		RIGHT_SHOULDER,
		NECK,
		LEFT_ELBOW,
		RIGHT_ELBOW,
		LEFT_KNEE,
		RIGHT_KNEE,
		LEFT_WRIST,
		RIGHT_WRIST,
		LEFT_ANKLE,
		RIGHT_ANKLE,
		LHIP,
		RHIP,
		HIP
	}
	
	public enum Action {
		ROTATE,
		MOVE,
		MOVE_TO
	}
}
