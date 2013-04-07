package com.euapps.owasp.appsec2012;

import java.util.Date;

import android.util.Log;

public class PlistSpeach {

	public static String TITLE = "title";
	public static String DESCRIPTION = "description";
	public static String START = "start";
	public static String END = "end";
	public static String PLACE = "place";
	public static String TRACK = "track";
	public static String SPEAKERS = "speakers";

	private String title;
	private String description;
	private Date start;
	private Date end;
	private String place;
	private int track;
	private String speakers;

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public String getSpeakers() {
		return speakers;
	}

	public void setSpeakers(String speakers) {
		this.speakers = speakers;
	}

	@Override
	public String toString() {
		return TITLE + ": " + title;
	}

	public static void print(PlistSpeach book) {
		Log.v(book.getClass().getName(), book.toString());
	}

}
