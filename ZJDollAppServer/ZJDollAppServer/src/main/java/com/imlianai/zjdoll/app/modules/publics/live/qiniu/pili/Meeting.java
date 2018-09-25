package com.imlianai.zjdoll.app.modules.publics.live.qiniu.pili;

import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class Meeting {
	private static String baseUrl=Config.APIHTTPScheme + Config.RTCAPIHost + "/v1";
    Meeting() {
    }
	public static String createRoom(String ownerId, String roomName)
			throws PiliException {
		CreateArgs args = new CreateArgs(ownerId, roomName);
		return createRoom(args);
	}

	public static String createRoom(String ownerId) throws PiliException {
		CreateArgs args = new CreateArgs(ownerId);
		return createRoom(args);
	}

	private static String createRoom(CreateArgs args) throws PiliException {
		String path = baseUrl + "/rooms";
		String json = JSON.toJSONString(args);
		System.out.println("createRoom args:"+args+" json:"+json);
		try {
			String resp = RPC.callWithJson(path, json);
			RoomName ret = JSON.parseObject(resp, RoomName.class);
			return ret.roomName;
		} catch (PiliException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PiliException(e);
		}
	}

	public static Room getRoom(String roomName) throws PiliException {
		String path = baseUrl + "/rooms/" + roomName;
		try {
			String resp = RPC.callWithGet(path);
			Room room = JSON.parseObject(resp, Room.class);
			return room;
		} catch (PiliException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PiliException(e);
		}
	}

	public static void deleteRoom(String room) throws PiliException {
		String path = baseUrl + "/rooms/" + room;
		try {
			RPC.callWithDelete(path);
		} catch (PiliException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PiliException(e);
		}
	}

	public static String roomToken(String roomName, String userId, String perm,
			Date expireAt) throws Exception {
		RoomAccess access = new RoomAccess(roomName, userId, perm, expireAt);
		String json = JSON.toJSONString(access);
		return Mac.signRoomToken(json);
	}
	
	public static String getDefaultRoomToken(String roomName, String userId, String perm) throws Exception {
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, +1);
	    date=cal.getTime(); 
		return roomToken(roomName, userId, perm, date);
	}

	private static class RoomAccess {
		@JSONField(name = "room_name")
		String roomName;
		@JSONField(name = "user_id")
		String userId;
		@JSONField(name = "perm")
		String perm;
		@JSONField(name = "expire_at")
		long expireAt;

		RoomAccess(String roomName, String userId, String perm, Date expireDate) {
			this.roomName = roomName;
			this.userId = userId;
			this.perm = perm;
			this.expireAt = expireDate.getTime() / 1000;// seconds
		}

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getPerm() {
			return perm;
		}

		public void setPerm(String perm) {
			this.perm = perm;
		}

		public long getExpireAt() {
			return expireAt;
		}

		public void setExpireAt(long expireAt) {
			this.expireAt = expireAt;
		}
	}

	/**
     *
     */
	public static enum Status {
		@JSONField(name = "0")
		NEW, @JSONField(name = "1")
		MEETING, @JSONField(name = "2")
		FINISHED,
	}

	public static class Room {
		@JSONField(name = "room_name")
		public String name;
		@JSONField(name = "room_status")
		public Status status;
		@JSONField(name = "owner_id")
		public String ownerId;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		public String getOwnerId() {
			return ownerId;
		}
		public void setOwnerId(String ownerId) {
			this.ownerId = ownerId;
		}
	}

	/**
     *
     */
	private static class RoomName {
		@JSONField(name = "room_name")
		String roomName;

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}
	}

	private static class CreateArgs {
		@JSONField(name = "owner_id")
		String ownerId;
		@JSONField(name = "room_name")
		String room;

		public CreateArgs(String ownerId, String room) {
			this.ownerId = ownerId;
			this.room = room;
		}

		public CreateArgs(String ownerId) {
			this.ownerId = ownerId;
		}

		@Override
		public String toString() {
			return "CreateArgs [ownerId=" + ownerId + ", room=" + room + "]";
		}

		public String getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(String ownerId) {
			this.ownerId = ownerId;
		}

		public String getRoom() {
			return room;
		}

		public void setRoom(String room) {
			this.room = room;
		}
		
	}

}
