package com.sql.project.startup.controller;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sql.project.startup.dto.LoginBean;
import com.sql.project.startup.entity.User;
import com.sql.project.startup.service.SessionService;
import com.sql.project.startup.service.UserService;
import com.sql.project.startup.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName:    LoginController.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-07 10:54:52 
 */
@Controller
public class LoginController extends BaseController {

	@SuppressWarnings("rawtypes")
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index() {
		return "forward:login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView goLoginPage(){
		ModelAndView mav=new ModelAndView("user/login");
		LoginBean bean = new LoginBean();
		mav.addObject("loginBean", bean);
		return mav;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(@ModelAttribute @Valid LoginBean loginBean,BindingResult result,HttpServletRequest request){
		ModelAndView mav=new ModelAndView("main");
		if(result.hasErrors()){
			for (Object object : result.getAllErrors()) {
			    if(object instanceof FieldError) {
			        FieldError fieldError = (FieldError) object;
			    }
			    if(object instanceof ObjectError) {
			        ObjectError objectError = (ObjectError) object;
			    }
			}
		}
		String capText = (String) request.getSession().getAttribute("KAPTCHA");
		if(capText.equalsIgnoreCase(loginBean.getValidCode())){
			User user = (User) userService.login(loginBean.getLoginName(),loginBean.getPassword());
			if(null!=user){
				super.getSessionInfo(request.getSession()).setLoginUser(user);
			}else{
				setMessage(MESSAGE_TYPE_ERROR, ERROR_LOGIN_NAME_PASSWORD_WRONG, request);
				mav = new ModelAndView("user/login");
				LoginBean bean = new LoginBean();
				bean.setLoginName(loginBean.getLoginName());
				mav.addObject("loginBean", bean);
			}
		}else{
			mav = new ModelAndView("user/login");
			LoginBean bean = new LoginBean();
			bean.setLoginName(loginBean.getLoginName());
			mav.addObject("loginBean", bean);
			setMessage(MESSAGE_TYPE_ERROR, ERROR_CAPTCHA, request);
		}
		return mav;
	}
	
	@RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
	public ModelAndView doAutoLogin(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException, Base64DecodingException{
		ModelAndView mav = new ModelAndView();
		User user = CookieUtil.compareCookie(request, response, userService);
		if(user == null){
			mav.setViewName("user/login");
			return mav;
		}
		return mav;
	}
	
	@RequestMapping(value = "/logout")
	public ModelAndView doLogout(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("forward:login");
		super.clearSession(request);
		mav.addObject(new LoginBean());
		return mav;
	}
}
