/**
 * 
 */

package common.interceptor;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.logistics.persistence.manager.UserManager;
import com.logistics.persistence.model.User;

import common.utils.ValidateUtils;
import common.utils.exception.ServiceException;
import common.utils.response.Res;

/**
 * 权限验证类
 * 
 * @author huhaichao
 *
 */
@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private UserManager userManager;
	private String[] publicUrls;

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isOnline = false;

		try {
			if (publicUrls != null) {
				for (String publicUrl : publicUrls) {
					String requestURI = request.getRequestURI();
					if (publicUrl != null && !publicUrl.isEmpty() && requestURI.startsWith(publicUrl)) {
						isOnline = true;
					}
				}
			}

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String sessionKey1 = request.getParameter("sessionKey");

			User user = null;
			// 用过sessionKey验证
			if (!ValidateUtils.isNull(sessionKey1)) {
				user = userManager.getBySessionKey(sessionKey1);
				if (user != null && user.getIsOnline() == 1) {
					isOnline = true;
					request.getSession().setAttribute("sessionKey", user.getSessionKey());
				}
			} else {// 通过cookie验证
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("sessionKey")) {
							String sessionKey = cookie.getValue();
							user = userManager.getBySessionKey(sessionKey);
							if (user != null && user.getIsOnline() == 1) {
								isOnline = true;
								request.getSession().setAttribute("sessionKey", user.getSessionKey());
							}
						}
					}
				}
			}
			if (!isOnline) {
				Res res = new Res();
				res.addNoPermission("未登录");
				res.toView(response);
			}
			return isOnline;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public String[] getPublicUrls() {
		return publicUrls;
	}

	public void setPublicUrls(String[] publicUrls) {
		this.publicUrls = publicUrls;
	}

}
