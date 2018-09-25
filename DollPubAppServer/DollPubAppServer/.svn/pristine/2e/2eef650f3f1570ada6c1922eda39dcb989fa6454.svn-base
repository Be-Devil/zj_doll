package com.imlianai.dollpub.app.modules.publics.qiniu.pili;

import java.util.Date;

public final class Client {
	private RPC cli;

	public Client(String accessKey, String secretKey) {
		this.cli = new RPC(new Mac(accessKey, secretKey));
	}

	/*
	 * RTMPPublishURL generates RTMP publish URL expireAfterSeconds means URL
	 * will be invalid after expireAfterSeconds.
	 */
	public static String RTMPPublishURL(String domain, String hub,
			String streamKey, int expireAfterSeconds) {
		long expire = new Date().getTime() + expireAfterSeconds;
		String path = String.format("/%s/%s?e=%d", hub, streamKey, expire);
		String token;
		try {
			token = Mac.sign(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return String.format("rtmp://%s%s&token=%s", domain, path, token);
	}

	/*
	 * RTMPPlayURL generates RTMP play URL
	 */
	public static String RTMPPlayURL(String domain, String hub, String streamKey) {
		return String.format("rtmp://%s/%s/%s", domain, hub, streamKey);
	}

	/*
	 * HLSPlayURL generates HLS play URL
	 */
	public static String HLSPlayURL(String domain, String hub, String streamKey) {
		return String.format("http://%s/%s/%s.m3u8", domain, hub, streamKey);
	}

	/*
	 * HDLPlayURL generates HDL play URL
	 */
	public static String HDLPlayURL(String domain, String hub, String streamKey) {
		return String.format("http://%s/%s/%s.flv", domain, hub, streamKey);
	}

	/*
	 * SnapshotPlayURL generates snapshot URL
	 */
	public static String SnapshotPlayURL(String domain, String hub,
			String streamKey) {
		return String.format("http://%s/%s/%s.jpg", domain, hub, streamKey);
	}

	public Hub newHub(String hub) {
		return new Hub(this.cli, hub);
	}

	public Meeting newMeeting() {
		return new Meeting();
	}

	
}
