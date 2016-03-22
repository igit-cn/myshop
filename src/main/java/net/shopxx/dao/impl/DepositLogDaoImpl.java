/*
 * Copyright 2005-2015 jshop.com. All rights reserved.
 * File Head

 */
package net.shopxx.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.shopxx.Page;
import net.shopxx.Pageable;
import net.shopxx.dao.DepositLogDao;
import net.shopxx.entity.DepositLog;
import net.shopxx.entity.Member;

import org.springframework.stereotype.Repository;

/**
 * Dao - 预存款记录
 * 
 * @author JSHOP Team
 \* @version 3.X
 */
@Repository("depositLogDaoImpl")
public class DepositLogDaoImpl extends BaseDaoImpl<DepositLog, Long> implements DepositLogDao {

	public Page<DepositLog> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DepositLog> criteriaQuery = criteriaBuilder.createQuery(DepositLog.class);
		Root<DepositLog> root = criteriaQuery.from(DepositLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

}