package me.chrislee.resumeGeneralResolve.utils;

import java.text.MessageFormat;

public class MessageUtils {
	/**
	 * 异常信息格式化：类名|||方法名|||异常描述
	 */
	public static String exceptionMsg(String className, String methordName, String msg){
		return MessageFormat.format("class {0}|||methord {1}|||msg: {2}", className, methordName, msg);
	}
}
