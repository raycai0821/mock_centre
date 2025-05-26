
/**
* Copyright (c) 2020-2023 恒新  All rights reserved.
* https://www.cleattle.com
*
*/

package com.huifu.hw.dto.risk;

import com.huifu.hw.enums.ChannelRespCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xukaijin
 * @description 通道返回
 * @create time Jul 25, 2023 2:24:25 PM
*/
@Data
public class BaseChannelResponse implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 请求ID，原请求ID
	 */
	private String requestId;

	/**
	 * HP的会员号
	 */
	private String memberCode;

	/**
	 * 风控响应编码
	 */
	private String respCode;

	/**
	 * 风控响应描述
	 */
	private String respDesc;

	/**
	 * 用于外部客户展示(持续拓展)
	 */
	private String outerRespCode;

	/**
	 * 用于外部客户展示(持续拓展)
	 */
	private String outerRespDesc;

	/**
	 * 内部具体原因（持续拓展）
	 */
	private String innerRespCode;

	/**
	 * 风控响应描述[内部]
	 */
	private String innerRespDesc;

	public Boolean isApproved() {
		return ChannelRespCodeEnum.APPROVED.getCode().equals(respCode);
	}

	public Boolean isRejected() {
		return ChannelRespCodeEnum.REJECTED.getCode().equals(respCode);
	}
}
