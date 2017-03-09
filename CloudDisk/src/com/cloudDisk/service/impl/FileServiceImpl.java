package com.cloudDisk.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cloudDisk.dao.FileDao;
import com.cloudDisk.dao.UserDao;
import com.cloudDisk.entity.User;
import com.cloudDisk.entity.Userfile;
import com.cloudDisk.service.FileService;
import com.cloudDisk.service.UserService;

@Service("fileService")
public class FileServiceImpl extends BaseServiceImpl<Userfile> implements FileService{

	// ע��DAO�� ���DAO�Ѿ���spring�ļ��н��������� ��Ҫ���set�����������ע��
	 // �������Ʋ���
	private FileDao fileDao;
	@Resource
	public void setFileDao(FileDao fileDao) {
		super.setBaseDao(fileDao);
		this.fileDao = fileDao;
	}

}
