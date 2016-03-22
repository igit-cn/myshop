/*
 * Copyright 2005-2015 jshop.com. All rights reserved.
 * File Head

 */
package net.shopxx.controller.admin;

import java.util.HashSet;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.PaymentMethod;
import net.shopxx.entity.ShippingMethod;
import net.shopxx.service.DeliveryCorpService;
import net.shopxx.service.PaymentMethodService;
import net.shopxx.service.ShippingMethodService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 配送方式
 * 
 * @author JSHOP Team
 \* @version 3.X
 */
@Controller("adminShippingMethodController")
@RequestMapping("/admin/shipping_method")
public class ShippingMethodController extends BaseController {

	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		return "/admin/shipping_method/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShippingMethod shippingMethod, Long defaultDeliveryCorpId, Long[] paymentMethodIds, RedirectAttributes redirectAttributes) {
		shippingMethod.setDefaultDeliveryCorp(deliveryCorpService.find(defaultDeliveryCorpId));
		shippingMethod.setPaymentMethods(new HashSet<PaymentMethod>(paymentMethodService.findList(paymentMethodIds)));
		if (!isValid(shippingMethod)) {
			return ERROR_VIEW;
		}
		shippingMethod.setFreightConfigs(null);
		shippingMethod.setOrders(null);
		shippingMethodService.save(shippingMethod);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethod", shippingMethodService.find(id));
		return "/admin/shipping_method/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShippingMethod shippingMethod, Long defaultDeliveryCorpId, Long[] paymentMethodIds, RedirectAttributes redirectAttributes) {
		shippingMethod.setDefaultDeliveryCorp(deliveryCorpService.find(defaultDeliveryCorpId));
		shippingMethod.setPaymentMethods(new HashSet<PaymentMethod>(paymentMethodService.findList(paymentMethodIds)));
		if (!isValid(shippingMethod)) {
			return ERROR_VIEW;
		}
		shippingMethodService.update(shippingMethod, "freightConfigs", "orders");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shippingMethodService.findPage(pageable));
		return "/admin/shipping_method/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= shippingMethodService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		shippingMethodService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}