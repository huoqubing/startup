package com.sql.project.startup.controller;

import com.sql.project.startup.dto.AppVersionDto;
import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.entity.AppVersion;
import com.sql.project.startup.service.AppVersionService;
import com.sql.project.startup.spring.annotation.FuncNum;
import com.sql.project.startup.spring.exception.ConsoleException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/appVersion")
public class AppVersionController extends BaseController {
	private static Logger logger = Logger.getLogger(AppVersionController.class);
	
	@Autowired
	private AppVersionService service;
	
	@RequestMapping(value = "/list")
	@FuncNum(value = "p_app_version", remark = "")
	public ModelAndView listItem_Auth(@ModelAttribute AppVersionDto dto) {
		ModelAndView mav = new ModelAndView("mgr/appVersionList");
		if (dto == null) {
			dto = new AppVersionDto();
		}
		PageBean result = service.search(dto);
		mav.addObject("dto", dto);
		mav.addObject("list", result);
		return mav;
	}
	
	@RequestMapping(value = "/goAddItem")
	@FuncNum(value = "p_app_version_add", remark = "")
	public ModelAndView goAddItem_Auth() {
		ModelAndView mav = new ModelAndView("mgr/addAppVersion");
		mav.addObject("appVersionDto", new AppVersionDto());
		return mav;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@FuncNum(value = "p_app_version_add", remark = "")
	public ModelAndView doSaveItem_Auth(@ModelAttribute @Valid AppVersionDto appVersionDto,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			mav.setViewName("mgr/addAppVersion");
			return mav;
		}
		appVersionDto.setCreateTime(new Date());
		appVersionDto.setUpdateTime(new Date());
		AppVersion appVersion = new AppVersion();
		BeanUtils.copyProperties(appVersionDto, appVersion);
		service.add(appVersion);
		appVersionDto.clear();
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
		AppVersion item = service.getById(Integer.valueOf(itemKey));
		AppVersionDto appVersionDto = new AppVersionDto();
		BeanUtils.copyProperties(item, appVersionDto);
		mav.addObject("appVersionDto", appVersionDto);
		mav.setViewName("mgr/editAppVersion");
		return mav;
	}
	
	@RequestMapping(value = "/editItem", method = RequestMethod.POST)
	@FuncNum(value = "p_app_version_edit", remark = "")
	public ModelAndView doEditItem_Auth(@ModelAttribute @Valid AppVersionDto appVersionDto,
			BindingResult result, HttpServletRequest request) {
		ModelAndView mav;
		String oldItemKey = request.getParameter("oldItemKey");
		if (result.hasErrors()) {
			mav = new ModelAndView();
			mav.setViewName("mgr/editAppVersion");
			return mav;
		}
		AppVersion item = service.getById(Integer.valueOf(oldItemKey));
		BeanUtils.copyProperties(appVersionDto, item);
		item.setId(Integer.valueOf(oldItemKey));
		item.setUpdateTime(new Date());
		service.update(item);
		appVersionDto.clear();
		RedirectView rv = new RedirectView("list");
/*		mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
				SUCCESS_OPERATION, request));*/
		mav = new ModelAndView(rv);
		return mav;
	}	

	
	@RequestMapping(value = "/del")
	@FuncNum(value = "p_app_version_del", remark = "")
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
			mav.addObject("appVersionDto", new AppVersionDto());
		} catch (ConsoleException he) {
			RedirectView rv = new RedirectView("list");
			mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
					he.getErrorCode(), request));
		}
		return mav;
	}	
	
	@RequestMapping(value = "/checkIsExist", method = RequestMethod.POST)
	@ResponseBody
	public String checkIsExist(String type,String version) {
		JSONObject json = new JSONObject();
		String status = "0";
		if(StringUtils.isNotBlank(type)&&
				StringUtils.isNotBlank(version)){
			AppVersionDto dto = new AppVersionDto();
			dto.setType(type);
			dto.setVersion(version);
			if(null!=isExistTypeAndVersion(dto)){
				status = "1";
			}
		}
		logger.info("json.message" + json.toString());
		return json.toString();
	}
	
	
	private AppVersion isExistTypeAndVersion(AppVersionDto appVersion){
		AppVersion result = null;
		PageBean list = service.search(appVersion);
		if(null!=list&&
				null!=list.getResult()&&
				!list.getResult().isEmpty()){
			result =(AppVersion) list.getResult().get(0);
		}
		return result;
	}
	
}
