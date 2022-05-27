package com.czy.replay;

/**
 * Class to hold replay control events
 * 
 * @author Joshua
 *
 */
public class ReplayControlEvent {

	private ReplayControlEnum control;
	private String value;

	public ReplayControlEvent(ReplayControlEnum control, String value) {
		super();
		this.control = control;
		this.value = value;
	}

	public ReplayControlEnum getControl() {
		return control;
	}

	public void setControl(ReplayControlEnum control) {
		this.control = control;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
