package com.sql.project.startup.controller;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.PushTag;
import com.sql.project.startup.service.PushTagService;
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
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pushTag")
public class PushTagController extends BaseController {
	private static Logger logger = Logger.getLogger(PushTagController.class);
	private SimpleDateFormat sdf  =  new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdfShort  =  new SimpleDateFormat("yy-MM-dd");
	@Autowired
	private PushTagService service;
	
	@RequestMapping(value = "/list")
	@FuncNum(value = "p_pushTag", remark = "")
	public ModelAndView listItem_Auth(@ModelAttribute PushTag dto) {
		ModelAndView mav = new ModelAndView("mgr/pushTagList");
		if (dto == null) {
			dto = new PushTag();
		}
		PageBean result = service.search(dto);
		mav.addObject("dto", dto);
		mav.addObject("list", result);
		return mav;
	}
	
	@RequestMapping(value = "/goAddItem")
	@FuncNum(value = "p_pushTag_add", remark = "")
	public ModelAndView goAddItem_Auth() {
		ModelAndView mav = new ModelAndView("mgr/addPushTag");
		mav.addObject("pushTag", new PushTag());
		return mav;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@FuncNum(value = "p_pushTag_add", remark = "")
	public ModelAndView doSaveItem_Auth(@ModelAttribute @Valid PushTag pushTag,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()){
			mav.setViewName("mgr/addPushTag");
			return mav;
		}
		
		pushTag.setCreatetime(new Date());
		pushTag.setUpdatetime(new Date());
		
		service.add(pushTag);
		pushTag.clear();
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
		 PushTag item = service.getById(Integer.valueOf(itemKey));
		mav.addObject("pushTag", item);
		mav.setViewName("mgr/editPushTag");
		return mav;
	}
	
	@RequestMapping(value = "/editItem", method = RequestMethod.POST)
	@FuncNum(value = "p_pushTag_edit", remark = "")
	public ModelAndView doEditItem_Auth(@ModelAttribute @Valid  PushTag pushTag,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav;
		
		if(result.hasErrors()){
			mav = new ModelAndView("mgr/editPushTag");
			return mav;
		}
		
		String oldItemKey = request.getParameter("oldItemKey");
		PushTag item = service.getById(Integer.valueOf(oldItemKey));
		BeanUtils.copyProperties(pushTag, item);
		item.setId(Integer.valueOf(oldItemKey));
		item.setUpdatetime(new Date());
		
		service.update(item);
		pushTag.clear();
		RedirectView rv = new RedirectView("list");
/*		mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
				SUCCESS_OPERATION, request));*/
		mav = new ModelAndView(rv);
		return mav;
	}	

	
	@RequestMapping(value = "/del")
	@FuncNum(value = "p_pushTag_del", remark = "")
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
			mav.addObject("dto", new  PushTag());
		} catch (ConsoleException he) {
			RedirectView rv = new RedirectView("list");
			mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
					he.getErrorCode(), request));
		}
		return mav;
	}
    
}
