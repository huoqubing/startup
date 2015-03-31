package com.sql.project.startup.controller;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.SMSRecord;
import com.sql.project.startup.service.SMSRecordService;
import com.sql.project.startup.spring.annotation.FuncNum;
import com.sql.project.startup.spring.exception.ConsoleException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/smsRecord")
public class SMSRecordController extends BaseController {
	private static Logger logger = Logger.getLogger(SMSRecordController.class);
	
	@Autowired
	private SMSRecordService service;
	
	@RequestMapping(value = "/list")
	@FuncNum(value = "p_smsRecord", remark = "")
	public ModelAndView listItem_Auth(@ModelAttribute SMSRecord dto) {
		ModelAndView mav = new ModelAndView("mgr/smsRecordList");
		if (dto == null) {
			dto = new SMSRecord();
		}
		PageBean result = service.search(dto);
		mav.addObject("dto", dto);
		mav.addObject("list", result);
		return mav;
	}
	
	@RequestMapping(value = "/goAddItem")
	@FuncNum(value = "p_smsRecord_add", remark = "")
	public ModelAndView goAddItem_Auth() {
		ModelAndView mav = new ModelAndView("mgr/addSMSRecord");
		mav.addObject("dto", new SMSRecord());
		return mav;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@FuncNum(value = "p_smsRecord_add", remark = "")
	public ModelAndView doSaveItem_Auth(@ModelAttribute SMSRecord dto,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		dto.setCreatetime(new Date());
		service.add(dto);
		dto.clear();
		RedirectView rv = new RedirectView("list");
/*		mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
				SUCCESS_OPERATION, request));*/
		mav = new ModelAndView(rv);
		return mav;
	}
	
	@RequestMapping(value = "prepareEditItem")
	public ModelAndView prepareEditItem(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String itemKey = request.getParameter("itemKey");
		 SMSRecord item = service.getById(Integer.valueOf(itemKey));
		mav.addObject("dto", item);
		mav.setViewName("mgr/editSMSRecord");
		return mav;
	}
	
	@RequestMapping(value = "/editItem", method = RequestMethod.POST)
	@FuncNum(value = "p_smsRecord_edit", remark = "")
	public ModelAndView doEditItem_Auth(@ModelAttribute  SMSRecord dto,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav;
		String oldItemKey = request.getParameter("oldItemKey");
		SMSRecord item = service.getById(Integer.valueOf(oldItemKey));
		BeanUtils.copyProperties(dto, item);
		item.setId(Integer.valueOf(oldItemKey));
		service.update(item);
		dto.clear();
		RedirectView rv = new RedirectView("list");
/*		mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
				SUCCESS_OPERATION, request));*/
		mav = new ModelAndView(rv);
		return mav;
	}	

	
	@RequestMapping(value = "/del")
	@FuncNum(value = "p_smsRecord_del", remark = "")
	public ModelAndView delItem_Auth(HttpServletRequest request) {
		ModelAndView mav;
		try {
			String action = request.getParameter("action");
			if ("single".equals(action)) {
				String id = request.getParameter("selected_item");
				service.deleteById(Integer.valueOf(id));
			} else {
				String[] idsStr = request.getParameterValues("selected_item");
				List<Integer> ids = new ArrayList<Integer>();
				for(String id:idsStr){
					ids.add(Integer.valueOf(id));
				}
				service.deleteByIds(ids);
			}
			RedirectView rv = new RedirectView("list");
/*			mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
					SUCCESS_OPERATION, request));*/
			mav = new ModelAndView(rv);
			mav.addObject("dto", new  SMSRecord());
		} catch (ConsoleException he) {
			RedirectView rv = new RedirectView("list");
			mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
					he.getErrorCode(), request));
		}
		return mav;
	}
    
}
