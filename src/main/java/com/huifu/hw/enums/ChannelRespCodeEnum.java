
/**
* Copyright (c) 2020-2023 恒新  All rights reserved.
* https://www.cleattle.com
*
*/

package com.huifu.hw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xukaijin
 * @description TODO(这里用一句话描述这个方法的作用)
 * @create time Jul 26, 2023 10:02:12 AM
*/
@AllArgsConstructor
@Getter
public enum ChannelRespCodeEnum {
	APPROVED("00", "通过"), // 业务侧流程继续
	REJECTED("01", "拒绝"),	// 业务侧流程中断
	PENDING("99", "处理中"), // 等待风控终态响应
	;

	private String code;
	private String desc;
}
