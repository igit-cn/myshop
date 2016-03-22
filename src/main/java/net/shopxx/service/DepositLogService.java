/*
 * Copyright 2005-2015 jshop.com. All rights reserved.
 * File Head

 */
package net.shopxx.service;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.entity.DepositLog;
import net.shopxx.entity.Member;

/**
 * Service - 预存款记录
 * 
 * @author JSHOP Team
 \* @version 3.X
 */
public interface DepositLogService extends BaseService<DepositLog, Long> {

	/**
	 * 查找预存款记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 预存款记录分页
	 */
	Page<DepositLog> findPage(Member member, Pageable pageable);

}