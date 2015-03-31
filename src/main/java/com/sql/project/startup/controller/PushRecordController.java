package com.sql.project.startup.controller;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.PushRecord;
import com.sql.project.startup.dto.PushTag;
import com.sql.project.startup.service.PushRecordService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pushRecord")
public class PushRecordController extends BaseController {
	private static Logger logger = Logger.getLogger(PushRecordController.class);
	@Autowired
	private PushRecordService service;
	@Autowired
	private PushTagService pushTagService;
	
	@RequestMapping(value = "/list")
	@FuncNum(value = "p_pushRecord", remark = "")
	public ModelAndView listItem_Auth(@ModelAttribute PushRecord dto) {
		ModelAndView mav = new ModelAndView("mgr/pushRecordList");
		if (dto == null) {
			dto = new PushRecord();
		}
		PageBean result = service.search(dto);
		mav.addObject("dto", dto);
		mav.addObject("list", result);
		return mav;
	}
	
	@RequestMapping(value = "/goAddItem")
	@FuncNum(value = "p_pushRecord_add", remark = "")
	public ModelAndView goAddItem_Auth() {
		ModelAndView mav = new ModelAndView("mgr/addPushRecord");
		List<PushTag> tagList = pushTagService.getList();
		mav.addObject("pushRecord", new PushRecord());
		mav.addObject("tagList", tagList);
		return mav;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@FuncNum(value = "p_pushRecord_add", remark = "")
	public ModelAndView doSaveItem_Auth(@ModelAttribute @Valid PushRecord pushRecord,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()){
			mav.setViewName("mgr/addPushRecord");
			return mav;
		}
		
		pushRecord.setCreatetime(new Date());
		
		service.add(pushRecord);
		pushRecord.clear();
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
		 PushRecord item = service.getById(Integer.valueOf(itemKey));
		mav.addObject("dto", item);
		mav.setViewName("mgr/editPushRecord");
		return mav;
	}
	
	@RequestMapping(value = "/editItem", method = RequestMethod.POST)
	@FuncNum(value = "p_pushRecord_edit", remark = "")
	public ModelAndView doEditItem_Auth(@ModelAttribute  PushRecord dto,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav;
		
		String oldItemKey = request.getParameter("oldItemKey");
		PushRecord item = service.getById(Integer.valueOf(oldItemKey));
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
	@FuncNum(value = "p_pushRecord_del", remark = "")
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
			mav.addObject("dto", new  PushRecord());
		} catch (ConsoleException he) {
			RedirectView rv = new RedirectView("list");
			mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
					he.getErrorCode(), request));
		}
		return mav;
	}
    
}
