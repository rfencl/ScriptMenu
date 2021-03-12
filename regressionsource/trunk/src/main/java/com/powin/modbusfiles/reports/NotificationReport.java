package com.powin.modbusfiles.reports;

import java.util.List;

public class NotificationReport {
	private List<String> notification;

	public void setNotification(List<String> notification) {
		this.notification = notification;
	}

	public List<String> getNotification() {
		return this.notification;
	}
}
