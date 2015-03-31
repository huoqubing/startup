package com.sql.project.startup.spring.aspectj;

import com.sql.project.startup.common.Constant;
import com.sql.project.startup.common.SessionInfo;
import com.sql.project.startup.dto.LoginBean;
import com.sql.project.startup.entity.Privilege;
import com.sql.project.startup.spring.annotation.FuncNum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName: FuncAspect.java
 * @Description: TODO
 * @author FrankWong
 * @version V1.0
 * @Date 2013-07-13 14:48:38
 */
@Component("funcaspect")
public class FuncAspect extends CommonAspect {

	@Override
	public void doAfter(JoinPoint jp) throws Exception {

	}

	@Override
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
		Class<?>[] parameterTypes = joinPointObject.getMethod()
				.getParameterTypes();
		Method method = pjp.getTarget().getClass()
				.getMethod(pjp.getSignature().getName(), parameterTypes);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if (method.isAnnotationPresent(FuncNum.class)) {
			SessionInfo sessionInfo = (SessionInfo) request.getSession()
					.getAttribute(Constant.KEY_SESSION_INFO);
			if(null!=sessionInfo&&
					null!=sessionInfo.getLoginUser()){
				FuncNum funcnum = method.getAnnotation(FuncNum.class);
				List<Privilege> privilegeList = sessionInfo.getLoginUser().getPrivilegeList();
				for (Privilege privilege : privilegeList) {
					if (privilege.getPrivilegeId().equals(funcnum.value())) {
							return pjp.proceed();
						}
				}
			}
		}
		RedirectView rv = new RedirectView("/startup/");
		ModelAndView view = new ModelAndView(rv);
		view.addObject(new LoginBean());
		return view;
	}

	@Override
	public void doBefore(JoinPoint jp) {
	}

	@Override
	public void doThrowing(JoinPoint jp, Throwable ex) {
	}

}
