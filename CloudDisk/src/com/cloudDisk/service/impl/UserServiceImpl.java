package com.cloudDisk.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.cloudDisk.dao.UserDao;
import com.cloudDisk.entity.User;
import com.cloudDisk.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	// ע��DAO�� ���DAO�Ѿ���spring�ļ��н��������� ��Ҫ���set�����������ע��
	 // �������Ʋ���
	private UserDao userDao;
	// ���ﻹ��Ҫ����@Resourceע��
	@Resource
	public void setUserDao(UserDao userDao) {
		// �ȸ�����һ�������dao �ø���ķ����ܹ�ִ�гɹ�	
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}
	
}
