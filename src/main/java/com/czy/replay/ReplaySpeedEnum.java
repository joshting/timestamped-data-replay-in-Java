package com.czy.replay;

/**
 * Enumeration for replay speed
 * 
 * @author Joshua
 *
 */
public enum ReplaySpeedEnum {

	SLOWDOWN_0_05(0.05f), SLOWDOWN_0_10(0.1f), SLOWDOWN_0_25(0.25f), SLOWDOWN_0_50(0.5f), SLOWDOWN_0_75(0.75f),
	SPEEDUP_1_25(1.25f), SPEEDUP_1_50(1.5f), SPEEDUP_2_00(2.0f), SPEEDUP_4_00(4.0f), SPEEDUP_8_00(8.0f),
	SPEEDUP_12_00(12.0f), SPEEDUP_16_00(16.0f);

	private float speedMultiplier;

	ReplaySpeedEnum(float speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	float getSpeedMultiplier() {
		return speedMultiplier;
	}

}
