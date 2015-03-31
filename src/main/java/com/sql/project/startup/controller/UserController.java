package com.sql.project.startup.controller;

import com.sql.project.startup.dto.PageBean;
import com.sql.project.startup.dto.UserDto;
import com.sql.project.startup.entity.User;
import com.sql.project.startup.service.UserService;
import com.sql.project.startup.spring.annotation.FuncNum;
import com.sql.project.startup.spring.exception.ConsoleException;
import com.sql.project.startup.util.EncryptionMD5;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
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
 * 2014-1-2 下午7:31:49
 */
@Controller
@RequestMapping("/loginUser")
public class UserController extends BaseController {

	private static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService service;

	@RequestMapping(value = "/list")
	@FuncNum(value = "p_login_user", remark = "")
	public ModelAndView listItem_Auth(@ModelAttribute UserDto dto,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("mgr/userList");
		if (dto == null) {
			dto = new UserDto();
		}
		PageBean result = service.search(dto);
		mav.addObject("dto", dto);
		mav.addObject("list", result);
		String psw = request.getParameter("psw");
		if(StringUtils.isNotBlank(psw)){
			mav.addObject("psw", psw);
			mav.addObject("un", request.getParameter("un"));
		}
		return mav;
	}

	@RequestMapping(value = "/goAddItem")
	@FuncNum(value = "p_login_user_add", remark = "")
	public ModelAndView toAddItem_Auth() {
		ModelAndView mav = new ModelAndView("mgr/addUser");
		mav.addObject("userDto",new UserDto());
		mav.addObject("role", service.getRoleList());
		return mav;
	}

	@RequestMapping(value = "/prepareEditItem")
	@FuncNum(value = "p_login_user_edit", remark = "")
	public ModelAndView prepareEditItem(HttpServletRequest request) {
		String itemKey = request.getParameter("itemKey");
		ModelAndView mav = new ModelAndView("mgr/editUser");
		mav.addObject("userDto",service.getById(Integer.valueOf(itemKey)));
		mav.addObject("role", service.getRoleList());
		return mav;
	}
	
	
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	@FuncNum(value = "p_login_user_add", remark = "")
	public ModelAndView saveUser_Auth(@ModelAttribute @Valid UserDto userDto,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		boolean pass = true;
		if(result.hasErrors()){
			mav.setViewName("mgr/addUser");
			mav.addObject("role", service.getRoleList());
			return mav; 
		}
		if (pass) {
			try {
				User user = new User();
				BeanUtils.copyProperties(userDto, user);
				String psw = RandomStringUtils.randomNumeric(6);
				user.setPassword(EncryptionMD5.getMD5(psw));
				user.setUpdateTime(new Date());
				user.setCreateTime(new Date());
				service.add(user);
				RedirectView rv = new RedirectView("list?psw="+psw+"&un="+user.getLoginName());
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
						SUCCESS_OPERATION, request));
			} catch (Exception e) {
				RedirectView rv = new RedirectView("list");
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
						e.getMessage(), request));
			}
		} 
		return mav;
	}

	@RequestMapping(value = "/editItem")
	@FuncNum(value = "p_login_user_edit", remark = "")
	public ModelAndView updateUser_Auth(@ModelAttribute @Valid UserDto userDto,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		boolean pass = true;
		
		if(result.hasErrors()){
			mav.setViewName("mgr/editUser");
			mav.addObject("role", service.getRoleList());
			return mav; 
		}
		
		
		if (pass) {
			try {
				User user = new User();
				String oldItemKey = request.getParameter("oldItemKey");
				BeanUtils.copyProperties(userDto, user);
				user.setId(Integer.valueOf(oldItemKey));
				user.setUpdateTime(new Date());
				service.update(user);
				RedirectView rv = new RedirectView("list");
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_SUCCESS,
						SUCCESS_OPERATION, request));
			} catch (Exception e) {
				logger.warn("exception when update user.", e);
				RedirectView rv = new RedirectView("list");
				mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
						e.getMessage(), request));
			}

		} 
		return mav;
	}

	@RequestMapping(value = "checkLoginName", method = RequestMethod.POST)
	@ResponseBody
	public String checkLoginName(String loginName) {
		JSONObject json = new JSONObject();
		User user = service.getByLoginName(loginName);
		if (null!=user) {
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
	
	@RequestMapping(value = "toupdatepwd")
	public ModelAndView toUpdatePwd() {
		ModelAndView mav = new ModelAndView("mgr/updatepwd");
		mav.addObject(new UserDto());
		return mav;
	}

	@RequestMapping(value = "/updatePwd")
	public ModelAndView updatePwd(@ModelAttribute UserDto userDto,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("mgr/updatepwd");
		boolean pass = true;
		if (pass) {
			try {
				User user = getCurretnUser(request);
				if (user != null) {
					if (user.getPassword().equals(userDto.getPassword())) {
						if(userDto.getConfirmPassword().equals(userDto.getNewPassword())){
							user.setPassword(userDto.getNewPassword());
							service.updatePsw(user);
							setMessage(MESSAGE_TYPE_SUCCESS, SUCCESS_OPERATION, request);
						}else{
							setMessage(MESSAGE_TYPE_ERROR, ERROR_CONFIRM_PSW, request);
						}
					} else {
						setMessage(MESSAGE_TYPE_ERROR, ERROR_ORIGINAL_PSW, request);
					}
				} else {
					setMessage(MESSAGE_TYPE_ERROR, ERROR_USER_NOT_EXIST, request);
				}
			} catch (Exception e) {
				logger.warn("exception when update password.", e);
				setMessage(MESSAGE_TYPE_EXCEPTION, e.getMessage(), request);
			}
		}
		mav.addObject(userDto);
		return mav;
	}
	@RequestMapping(value = "/del")
	@FuncNum(value = "p_login_user_del", remark = "")
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
			mav.addObject("dto", new UserDto());
		} catch (ConsoleException he) {
			RedirectView rv = new RedirectView("list");
			mav = new ModelAndView(rv, getMessage(MESSAGE_TYPE_EXCEPTION,
					he.getErrorCode(), request));
		}
		return mav;
	}
}