package com.sql.project.startup.controller;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.RoleDto;
import com.sql.project.startup.entity.Role;
import com.sql.project.startup.service.RoleService;
import com.sql.project.startup.spring.annotation.FuncNum;
import com.sql.project.startup.spring.exception.ConsoleException;
import net.sf.json.JSONObject;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * TODO
 * @author SQL
 *  
 * 2014-1-5 下午12:32:32
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private static Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private RoleService service;

	@RequestMapping(value = "/list")
	@FuncNum(value = "p_role", remark = "")
	public ModelAndView listItem_Auth(@ModelAttribute RoleDto dto) {
		ModelAndView mav = new ModelAndView("mgr/roleList");
		if (dto == null) {
			dto = new RoleDto();
		}
		PageBean result = service.search(dto);
		mav.addObject("dto", dto);
		mav.addObject("list", result);
		return mav;
	}

	@RequestMapping(value = "/goAddItem")
	@FuncNum(value = "p_role_add", remark = "")
	public ModelAndView toAddItem_Auth() {
		ModelAndView mav = new ModelAndView("mgr/addRole");
		mav.addObject("roleDto",new RoleDto());
		mav.addObject("privilege", service.getPrivilegeList());
		return mav;
	}

	@RequestMapping(value = "/prepareEditItem")
	@FuncNum(value = "p_role_edit", remark = "")
	public ModelAndView prepareEditItem(HttpServletRequest request) {
		String itemKey = request.getParameter("itemKey");
		ModelAndView mav = new ModelAndView("mgr/editRole");
		mav.addObject("roleDto",service.getById(Integer.valueOf(itemKey)));
		mav.addObject("privilege", service.getPrivilegeList());
		return mav;
	}

	@RequestMapping(value = "/save")
	@FuncNum(value = "p_role_add", remark = "")
	public ModelAndView saveItem_Auth(@ModelAttribute @Valid RoleDto roleDto,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		boolean pass = true;
		
		if (!result.hasErrors()) {
			try {
				Role role = new Role();
				BeanUtils.copyProperties(roleDto, role);
				role.setUpdateTime(new Date());
				role.setCreateTime(new Date());
				service.add(role);
				RedirectView rv = new RedirectView("list");
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
						SUCCESS_OPERATION, request));
			} catch (Exception e) {
				RedirectView rv = new RedirectView("list");
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
						e.getMessage(), request));
			}
		} else {
			mav.setViewName("mgr/addRole");
			mav.addObject("privilege", service.getPrivilegeList());
		}
		return mav;
	}

	@RequestMapping(value = "/editItem")
	@FuncNum(value = "p_role_edit", remark = "")
	public ModelAndView updateItem_Auth(@ModelAttribute @Valid RoleDto roleDto,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		boolean pass = true;
		if (!result.hasErrors()) {
			try {
				Role role = new Role();
				String oldItemKey = request.getParameter("oldItemKey");
				BeanUtils.copyProperties(roleDto, role);
				role.setId(Integer.valueOf(oldItemKey));
				role.setUpdateTime(new Date());
				service.update(role);
				RedirectView rv = new RedirectView("list");
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
						SUCCESS_OPERATION, request));
			} catch (Exception e) {
				logger.warn("exception when update user.", e);
				RedirectView rv = new RedirectView("list");
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
						e.getMessage(), request));
			}

		} else {
			mav.setViewName("mgr/editRole");
			mav.addObject("privilege", service.getPrivilegeList());
		}
		return mav;
	}

	@RequestMapping(value = "/del")
	@FuncNum(value = "p_role_del", remark = "")
	public ModelAndView delItem_Auth(HttpServletRequest request) {
		ModelAndView mav;
		try {
			String action = request.getParameter("action");
			boolean isUsed = false;
			if ("single".equals(action)) {
				String id = request.getParameter("selected_item");
				isUsed = service.isUsed(id);
				if(!isUsed){
					service.deleteById(Integer.valueOf(id));
				}
			} else {
				String[] idsStr = request.getParameterValues("selected_item");
				List<Integer> ids = new ArrayList<Integer>();
				for(String id:idsStr){
					isUsed = service.isUsed(id);
					if(isUsed){
						break;
					}
					ids.add(Integer.valueOf(id));
				}
				if(!isUsed){
					service.deleteByIds(ids);
				}
			}
			
			RedirectView rv = new RedirectView("list");
			if(isUsed){
				mav = new ModelAndView(rv,getMessage(MESSAGE_TYPE_ERROR, ERROR_ROLE_IS_USED, request));
			}else{
				mav = new ModelAndView(rv);
			}
			mav.addObject("dto", new RoleDto());
		} catch (ConsoleException he) {
			RedirectView rv = new RedirectView("list");
			mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
					he.getErrorCode(), request));
		}
		return mav;
	}	
	
	
	@RequestMapping(value = "checkRoleId", method = RequestMethod.POST)
	@ResponseBody
	public String checkRoleId(String roleId) {
		JSONObject json = new JSONObject();
		Role role = service.getByRoleId(roleId);
		if (null==role) {
			json.put("status", "1");
			json.put("message","exists");
			json.put("data", "");
		} else {
			json.put("status", "0");
			json.put("message","not exists");
			json.put("data", "");
		}
		return json.toString();
	}
}