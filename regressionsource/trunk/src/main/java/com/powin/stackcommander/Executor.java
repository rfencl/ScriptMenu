package com.powin.stackcommander;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the container class holding the parsed command and its parameters.
 *
 * @author richa
 *
 */
public class Executor {
	enum Status {
		READY, NOT_READY, COMPLETE;

	}

	private Command command = null;
	private final List<Directive> directive = new ArrayList<>();
	private Duration duration = null;
	private Condition condition = Condition.Undefined;
	private Power power = null;
	private Status status = Status.NOT_READY;

	public Command getCommand() {
		return command;
	}
    
	public Condition getCondition() {
		return condition;
	}
	
	public List<Directive> getDirective() {
		return directive;
	}

	public Duration getDuration() {
		return duration;
	}

	public Power getPower() {
		return this.power;
	}

	public String getPowerString() {
		return this.power.toString();
	}

	public Status getStatus() {
		return status;
	}

	public void setCommand(final Command command) {
		this.command = command;
	}
	
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public void setDirective(final Directive directive) {
		this.directive.add(directive);
	}

	public void setDuration(final Duration d) {
		this.duration = d;
	}

	public void setPower(final Power p) {
		this.power = p;

	}

	/**
	 * Verify that we parsed this line correctly.
	 */
	public void setStatus() {
		boolean isReady = false;
		if (command != null) {

			switch (command) {
			case Using:
			case Balancing:
			case Title:
				isReady |= (!this.directive.isEmpty());
				break;
			case FullCharge:
			case FullDischarge:
				isReady |= (!this.directive.isEmpty()) && (this.power != null);
				break;
			case Rest:
				isReady |= (!this.directive.isEmpty() && this.directive.get(0) == Directive.Until)
						|| (this.duration != null);
				break;
			case Charge:
			case Discharge:
				isReady |= this.directive.isEmpty() || this.directive.get(0) == Directive.Undefined
						|| (null != this.directive && this.directive.get(0) == Directive.FULL && (this.power != null))
						|| (null != this.directive && this.directive.get(0) == Directive.Until && (this.condition != null))
						|| (this.directive.size() == 2 && (this.power != null));
				break;
			case Comment:
				isReady = true;
				break;
			case Record:
			case Turnoff:
			case Restart:
				isReady |= !this.directive.isEmpty();
				break;
			case Repeat:
				isReady |= this.directive.get(0).getRepeatStep() > 0; 
			default:
				break;
			}
			if (isReady) {
				status = Status.READY;
			}
		}

	}

	public void setStatusComplete() {
		this.status = Status.COMPLETE;
	}

	@Override
	public String toString() {
		return "Executor [command=" + command + ", directive=" + directive + ", duration=" + duration + ", power="
				+ power + ", status=" + status + "]";
	}


}
