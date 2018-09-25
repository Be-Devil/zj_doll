package com.imlianai.zjdoll.app.modules.core.user.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.imlianai.zjdoll.domain.user.OsType;
import com.imlianai.zjdoll.domain.user.UserEventType;
import com.imlianai.zjdoll.domain.user.UserGeneral;
import com.imlianai.zjdoll.domain.user.UserSimple;
import com.imlianai.rpc.support.utils.StringUtil;

public class UserUtil {

	public static final float invite_rate = 0.05f;
	public static final int invite_max_jewel = 300;
	public static final int live_value_max = 30;
	public static final int watch_value_max = 30;
	public static final String msg_head_common = "http://lianai-image-sys.qiniudn.com/live_web/logo_256.png";
	public static final String splitChat1 = "\\^@#";
	public static final String splitChat2 = "\\^#";
	public static final String city_default = "海外";
	private static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22,
			23, 23, 23, 23, 22, 22};

	/**
	 * 获取手机号码 (国家地区编号大陆除外)
	 * 
	 * @param jsonMap
	 * @return
	 */
	public static long getNumber(Map<String, Object> jsonMap) {
		String numberStr = (String) jsonMap.get("number");
		int areaCode = jsonMap.get("code") == null ? 86 : (Integer) jsonMap
				.get("code");
		if (areaCode != 86)
			numberStr = areaCode + numberStr;
		return Long.parseLong(numberStr);
	}

	/**
	 * 构建五位随机数字
	 * 
	 * @return
	 */
	public static String buildDynNmb() {
		int nLength = 5;
		Random rm = new Random();
		StringBuffer strPassWd = new StringBuffer();
		for (int i = 0; i < nLength; i++) {
			int x = rm.nextInt(10) % 10;
			if (i == 0 && x == 0)
				x = 1;
			strPassWd.append(x);
		}
		return strPassWd.toString();
	}

	/**
	 * 构建n位随机数字
	 * 
	 * @return
	 */
	public static String buildDynNmbN(int nLength) {
		Random rm = new Random();
		StringBuffer strPassWd = new StringBuffer();
		for (int i = 0; i < nLength; i++) {
			int x = rm.nextInt(10) % 10;
			if (i == 0 && x == 0) {
				x = 1;
			}
			strPassWd.append(x);
		}
		return strPassWd.toString();
	}

	/**
	 * 构建指定位数随机数字
	 * 
	 * @return
	 */
	public static String buildDynNmb(int size) {
		Random rm = new Random();
		StringBuffer strPassWd = new StringBuffer();
		for (int i = 0; i < size; i++) {
			strPassWd.append(rm.nextInt(10) % 10);
		}
		return strPassWd.toString();
	}

	private static String dynStr = "0123456789abcdefghijklmnopqrstuvwxyz";
	/**
	 * 构建指定位数随机数字
	 * 
	 * @return
	 */
	public static String buildDynStr(int size) {
		Random rm = new Random();
		StringBuffer strPassWd = new StringBuffer();
		for (int i = 0; i < size; i++) {
			strPassWd.append(dynStr.charAt(rm.nextInt(35)));
		}
		return strPassWd.toString();
	}

	/**
	 * 获取用户星座
	 * 
	 * @param briday
	 * @return
	 */
	public static int getStarSign(String birday) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sDateFormat.parse(birday);
			Calendar cld = Calendar.getInstance();
			cld.setTime(date);
			int month = (cld.get(Calendar.MONTH));
			int day = cld.get(Calendar.DAY_OF_MONTH);
			if (day < constellationEdgeDay[month]) {
				month = month - 1;
			}
			if (month >= 0 && month < 12) {
				return month + 1;
			}
			return 12;
		} catch (Exception e) {
			return 12;
		}
	}

	/**
	 * 获取用户年龄
	 * 
	 * @param birthday
	 * @return
	 */
	public static int getAge(String birthday) {
		if (StringUtils.isBlank(birthday))
			return 0;
		int iage = 0;
		try {
			Calendar birthDate = Calendar.getInstance();
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			birthDate.setTime(myFormatter.parse(birthday));
			Calendar today = Calendar.getInstance();
			if (today.get(Calendar.YEAR) > birthDate.get(Calendar.YEAR)) {
				iage = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
						- 1;
				if (today.get(Calendar.MONTH) + 1 > birthDate
						.get(Calendar.MONTH)) {
					iage++;
				} else if (today.get(Calendar.MONTH) + 1 == birthDate
						.get(Calendar.MONTH)) {
					if (today.get(Calendar.DAY_OF_MONTH) > birthDate
							.get(Calendar.DAY_OF_MONTH)) {
						iage++;
					}
				}
			}
		} catch (Exception e) {
		}
		return iage;
	}

	/**
	 * 是否为第三方登陆
	 * 
	 * @param srcType
	 * @return
	 */
	public static boolean isThirdParty(int srcType) {
		if (srcType > 0)
			return true;
		return false;
	}

	/**
	 * 是否为靓号
	 * 
	 * @param uid
	 * @return
	 */
	private static final String[] prettyIds = {"000", "111", "222", "333",
			"444", "555", "666", "777", "888", "999", "123", "234", "345",
			"456", "567", "678", "789", "321", "432", "543", "654", "765",
			"876", "987"};
	public static boolean isPrettyId(long uid) {
		if (uid <= 110000)
			return true;
		String uidStr = uid + "";
		for (String prettyId : prettyIds) {
			if (uidStr.indexOf(prettyId) > -1)
				return true;
		}
		return false;
	}

	/**
	 * 获取HTTP请求信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getHttpHead(HttpServletRequest request) {
		Map<String, String> headRequestMap = new HashMap<String, String>();
		@SuppressWarnings("rawtypes")
		Enumeration names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			headRequestMap.put(name, request.getHeader(name));
		}
		return headRequestMap;
	}

	/**
	 * 判断是否为android模拟器
	 * 
	 * @param request
	 * @return 模拟器返回true
	 */
	public static boolean isAndroidEmulator(HttpServletRequest request) {
		try {
			String userAgent = request.getHeader("user-agent");
			if (!StringUtils.isBlank(userAgent)) {
				if (userAgent.indexOf("Droid4X") > -1
						|| userAgent.indexOf("kaopu") > -1
						|| userAgent.indexOf("Windroye") > -1
						|| userAgent.indexOf("BlueStacks") > -1
						|| userAgent.indexOf("sdk Build") > -1
						|| userAgent.indexOf("TianTian") > -1)
					return true;
				Pattern pattern = Pattern.compile(".*Lan.*Build.*");
				Matcher matcher = pattern.matcher(userAgent);
				if (matcher.matches())
					return true;
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 获取短信文字
	 * 
	 * @param type
	 * @return
	 */
	public static String getWordSMS(int type) {
		switch (type) {
			case 1 :
				return "注册";
			case 2 :
				return "重置密码";
			case 3 :
				return "绑定手机验证";
			case 4 :
				return "账号验证";
			default :
				return "验证";
		}
	}

	/**
	 * 用户HTML显示
	 * 
	 * @param user
	 * @return
	 */
	public static String getUserTag(UserGeneral user) {
		return "<sb>" + user.getUid() + "@#" + user.getName() + "@#你</sb>";
	}

	/**
	 * 获取用户头像
	 * 
	 * @param userSimples
	 * @return
	 */
	public static String getUserHeads(List<UserSimple> userSimples) {
		if (userSimples == null || userSimples.isEmpty())
			return "nil";
		String heads = "";
		for (int i = 0, length = userSimples.size(); i < length; i++) {
			if (i != 0) {
				heads = heads + "," + userSimples.get(i).getHead();
			} else {
				heads = userSimples.get(i).getHead();
			}
		}
		return heads;
	}


	/**
	 * 检查用户名称的合法性
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkUsername(String name) {
		int length = getStrLength(name);
		return length >= 4 && length <= 30;
	}
	/**
	 * 获取字符串的长度，对双字符（包括汉字）按两位计数
	 * 
	 * @param value
	 * @return
	 */
	public static int getStrLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			int cint = (int) c;
			String cstr = c + "";
			if (cstr.matches(chinese) && cint != 65288 && cint != 65289) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
	/**
	 * 获取用户事件类类型
	 * 
	 * @param osType
	 * @return
	 */
	public static UserEventType getUserEventTypeByOsType(OsType osType) {
		UserEventType type = null;
		if (osType != null) {
			switch (osType) {
				case ANDROID :
					type = UserEventType.EDIT_AGREE;
					break;
				case IOS :
					type = UserEventType.EDIT_AGREE;
					break;
				case IOSMJ :
					type = UserEventType.EDIT_AGREE_MJ;
					break;
				default :
					type = UserEventType.EDIT_AGREE;
					break;
			}
		} else {
			type = UserEventType.EDIT_AGREE;
		}
		return type;
	}
	/**
	 * 获取用户名
	 * 
	 * @param name
	 * @return
	 */
	public static String getName(String name) {
		// 只允许字母和数字
		String regEx = "[^a-zA-Z0-9-_\u4e00-\u9fa5]";
		// 清除掉所有特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(name);
		return m.replaceAll("").trim();
	}
	
	private static final String DEFAULT_USER_HEAD="http://lianai-image-sys.qiniudn.com/zjapp/logo.png";
	
	public static String getDefaultHead() {
		return DEFAULT_USER_HEAD;
	}
	/**
	 * 检验并替换默认头像
	 * @param user
	 */
	public static void checkHead(UserGeneral user){
		if (user!=null&&StringUtil.isNullOrEmpty(user.getHead())) {
			user.setHead(DEFAULT_USER_HEAD);
		}
		if (user!=null&&!StringUtil.isNullOrEmpty(user.getName())) {
			if (user.getName().contains("码")||user.getName().contains("邀请")||user.getName().contains("福利")||user.getName().contains("币")) {
				user.setName("萌萌哒"+user.getUid());
			}
		}
	}
	
}
