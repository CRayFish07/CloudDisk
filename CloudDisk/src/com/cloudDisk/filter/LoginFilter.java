package com.cloudDisk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



// ����������Ŀ����Ϊ�˷�ֹ�û��ƹ���¼ҳ��ֱ�ӷ���������ҳ��
public class LoginFilter implements Filter {

	public void destroy() {
		
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		// �õ���ǰ����ĵ�ַ
		String uri = request.getRequestURI();
		if(uri.contains("user/user_toLoginUI")){
			chain.doFilter(request, response);
		} else if(!uri.contains("user/user_login")){
			// ������ǵ�¼��������Ҫ�жϵ�ǰ��session���Ƿ��������
			if(request.getSession().getAttribute("userInfo")!=null){
				// session �ػ��д������� �Ѿ���¼
				chain.doFilter(request, response);
			}else{
				//û�е�¼����ת����¼ҳ��
				response.sendRedirect(request.getContextPath() + "/user/user_toLoginUI.action");
			}
			
		}else{
			//��ǰ����ִ�е�¼���� ����
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig config) throws ServletException {
	}

	

}
