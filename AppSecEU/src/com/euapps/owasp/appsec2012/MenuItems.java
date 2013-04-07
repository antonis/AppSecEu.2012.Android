package com.euapps.owasp.appsec2012;

public class MenuItems {

	public final static int iSchedule = 0;
	public final static String sSchedule = "Schedule";
	public final static int iNews = 1;
	public final static String sNews = "News";
	public final static int iTraining = 2;
	public final static String sTraining = "Training";
	public final static int iSocial = 3;
	public final static String sSocial = "Social Events";
	public final static int iMap = 4;
	public final static String sMap = "Map";
	public final static int iVenue = 5;
	public final static String sVenue = "Venue";
	public final static int iTransport = 6;
	public final static String sTransport = "Transport";
	public final static int iTravel = 7;
	public final static String sTravel = "Travel";
	public final static int iSponsors = 8;
	public final static String sSponsors = "Sponsors";
	public final static int iInfo = 9;
	public final static String sInfo = "Info";
	public final static int iAbout = 10;
	public final static String sAbout = "About";

	public final static String[] items = new String[] { sSchedule, sNews,
			sTraining, sSocial, sMap, sVenue, sTransport, sTravel, sSponsors,
			sInfo, sAbout };

	public static int getResourceDrawable(int id) {
		switch (id) {
		case iSchedule:
			return R.drawable.m_program;
		case iNews:
			return R.drawable.m_news;
		case iTraining:
			return R.drawable.m_training;
		case iSocial:
			return R.drawable.m_social;
		case iMap:
			return R.drawable.m_map;
		case iVenue:
			return R.drawable.m_venue;
		case iTransport:
			return R.drawable.m_transport;
		case iTravel:
			return R.drawable.m_travel;
		case iSponsors:
			return R.drawable.m_sponsors;
		case iInfo:
			return R.drawable.m_info;
		default:
			return R.drawable.m_about;
		}
	}

}
