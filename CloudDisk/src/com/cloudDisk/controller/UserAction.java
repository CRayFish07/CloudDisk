package com.cloudDisk.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.cloudDisk.entity.User;
import com.cloudDisk.service.UserService;
import com.cloudDisk.service.impl.UserServiceImpl;
import com.cloudDisk.utils.QueryHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
	
	
	
	@Resource
	UserService userService; // ע�ⷽʽע��userService;
	
	/**
	 * ȥ����¼ҳ��
	 */
	public String toLoginUI(){
		return "login";
	}
	
	
	// ȥ���û���Ϣ���½���
	public String toUpdateUserInfoUI(){
		
		// ���¸���һ��session�е�����
		return "userInfoUI";
	}
	
	// ���û����޸ĵ���Ϣ���浽���ݿ�
  public String	updateUser(){
	  
	  if(user!=null){
		  System.out.println(user.getUserId()); // Ϊ����
		  System.out.println(user.getUserEmail());
		  System.out.println(user.getUserPwd());
		  System.out.println(user.getUserName()); // Ϊnull
		  userService.update(user);
	  }
	  return "input"; // ��ַ������ (��Ҫ�õ�ַ���ı�)
  }
	
	
	
	/**
	 * ��½������֤
	 * @return
	 */
	public String login(){ 
		 emailOrName=emailOrName.trim();
	    if(emailOrName!=null&&!emailOrName.equals("")){
	    	  QueryHelper helper=new QueryHelper(User.class, "u");
			  String reg = "\\w+[\\w]*@[\\w]+\\.[\\w]+$";
				// ��һ���жϵ�ǰ���ַ�����email������ͨ�û���
		      if(emailOrName.matches(reg)){
					// ʹ����������½
					helper.setWhereClause("userEmail=?", emailOrName);
				}else{
					// ʹ���û�����½
					helper.setWhereClause("userName=?", emailOrName);
				}
			
				// �ڶ���������Ϣȥ���ݿ�����йؼ�¼
		      if(user!=null&& !user.getUserPwd().trim().equals("")){
		    	      helper.setWhereClause("userPwd=?", user.getUserPwd().trim());
					  List<User> users=null;
					  users = userService.findObjects(helper);
					   if(users!=null&&users.size()>0){
							// ��¼�ɹ� ȥ������������ ������ ���û���Ϣ���뵽session��
							ActionContext.getContext().getSession().put("userInfo", users.get(0));
							return "success";
					   }
	            }
	    }
		return "input";
	}

	
	/**
	 * �˳���¼ ���ص�¼����
	 * @return
	 */
	public String logOut(){
		// ���session����
		ActionContext.getContext().getSession().remove("userInfo");
		return "input";
	}
	
	
	
	/**
	 * ע���߼�
	 * @return
	 */
	public String register(){
		if(user!=null){
				// ע������ �����ݿ��в���һ������  ע��ĸ�����֤�����Ѿ�ͨ��ǰ̨js�������
				userService.save(user);
		}
        // ���ص�½���� ʹ����ע���˺ŵ�½
		return "input";
	}
   	
	
	/**
	 * ����û����Ƿ��ظ�
	 */
	public void verifyUserName(){	
		String result="false";
		// ��֤��ע����˺��Ƿ�����
		if(user!=null&&user.getUserName()!=null){
			QueryHelper helper=new QueryHelper(User.class, "u");
			String name=user.getUserName();
			helper.setWhereClause("userName=?", name);
			
			if(userService==null){
				System.out.println("not null");
			}	
			List<User> users = userService.findObjects(helper);
		
			if(users==null||users.size()==0){
				// �û������ظ�
				result="true";
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html");
			try {
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(result.getBytes());
	            outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
  // ����struts�Ĳ����Զ���װ�����ձ��Ĳ���
	private User user;
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
  
// ��ǰ�˻�ȡ�����û�������email�ַ�����
	String emailOrName;

	public String getEmailOrName() {
		return emailOrName;
	}
	public void setEmailOrName(String emailOrName) {
		this.emailOrName = emailOrName;
	}

}
