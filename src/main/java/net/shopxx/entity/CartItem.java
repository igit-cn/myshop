/*
 * Copyright 2005-2015 jshop.com. All rights reserved.
 * File Head

 */
package net.shopxx.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.shopxx.Setting;
import net.shopxx.util.SystemUtils;

/**
 * Entity - 购物车项
 * 
 * @author JSHOP Team
 \* @version 3.X
 */
@Entity
@Table(name = "xx_cart_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_cart_item")
public class CartItem extends BaseEntity<Long> {

	private static final long serialVersionUID = 7074622316150453355L;

	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 10000;

	/** 数量 */
	private Integer quantity;

	/** 商品 */
	private Product product;

	/** 购物车 */
	private Cart cart;

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Cart getCart() {
		return cart;
	}

	/**
	 * 设置购物车
	 * 
	 * @param cart
	 *            购物车
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		if (getProduct() != null && getProduct().getWeight() != null && getQuantity() != null) {
			return getProduct().getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getRewardPoint() {
		if (getProduct() != null && getProduct().getRewardPoint() != null && getQuantity() != null) {
			return getProduct().getRewardPoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取兑换积分
	 * 
	 * @return 兑换积分
	 */
	@Transient
	public long getExchangePoint() {
		if (getProduct() != null && getProduct().getExchangePoint() != null && getQuantity() != null) {
			return getProduct().getExchangePoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	@Transient
	public BigDecimal getPrice() {
		if (getProduct() != null && getProduct().getPrice() != null) {
			Setting setting = SystemUtils.getSetting();
			if (getCart() != null && getCart().getMember() != null && getCart().getMember().getMemberRank() != null) {
				MemberRank memberRank = getCart().getMember().getMemberRank();
				if (memberRank.getScale() != null) {
					return setting.setScale(getProduct().getPrice().multiply(new BigDecimal(String.valueOf(memberRank.getScale()))));
				}
			}
			return setting.setScale(getProduct().getPrice());
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获取是否上架
	 * 
	 * @return 是否上架
	 */
	@Transient
	public boolean getIsMarketable() {
		return getProduct() != null && getProduct().getIsMarketable();
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@Transient
	public boolean getIsDelivery() {
		return getProduct() != null && getProduct().getIsDelivery();
	}

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@Transient
	public boolean getIsLowStock() {
		return getQuantity() != null && getProduct() != null && getQuantity() > getProduct().getAvailableStock();
	}

	/**
	 * 增加商品数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@Transient
	public void add(int quantity) {
		if (quantity < 1) {
			return;
		}
		if (getQuantity() != null) {
			setQuantity(getQuantity() + quantity);
		} else {
			setQuantity(quantity);
		}
	}
	
	/**
	 * 获取综合税税率
	 */
	@Transient
	public BigDecimal getComprehensiveTaxRate() {
		return getProduct() != null && getProduct().getGoods() != null && getProduct().getGoods().getTaxRates() != null ? getProduct().getGoods().getTaxRates().getComprehensiveTaxRate() : BigDecimal.ZERO;
	}
	
	/**
	 * 获取税费
	 */
	@Transient
	public BigDecimal getTax() {
		return getSubtotal().multiply(getComprehensiveTaxRate());
	}
	
	/**
	 * 获取综合费用
	 */
	@Transient
	public BigDecimal getComprehensivePrice() {
		return getSubtotal().add(getTax());
	}

}
