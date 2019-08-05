package com.maple.common.core.constant;

/**
 * @author zhua
 * @date 2019/7/30
 */
public interface CommonConstants {

	/**
	 * 删除
	 */
	String STATUS_DEL = "1";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单
	 */
	String MENU = "1";

	/**
	 * 按钮
	 */
	String BUTTON = "2";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * 验证码前缀
	 */
	String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;
	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 默认存储bucket
	 */
	String BUCKET_NAME = "";

	/**
	 * 默认token有效时间(天)
	 */
	int DUFAULT_ACCESS_TOKEN_TIME = 7;

	/**
	 * 默认刷新token时间(天)
	 */
	int DUFAULT_REFRESH_TOKEN_TIME = 7;

	/**
	 * 用户token有效时间(天)
	 */
	int USER_ACCESS_TOKEN_TIME = 1;

	/**
	 * 用户刷新token时间(天)
	 */
	int USER_REFRESH_TOKEN_TIME = 1;

	/**
	 * 客户端token有效时间(天)
	 */
	int CLIENT_ACCESS_TOKEN_TIME = 1;
	/**
	 * 客户端刷新token时间(天)
	 */
	int CLIENT_REFRESH_TOKEN_TIME = 1;
}
