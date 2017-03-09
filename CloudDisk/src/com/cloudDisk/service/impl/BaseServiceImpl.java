package com.cloudDisk.service.impl;

import java.io.Serializable;
import java.util.List;

import com.cloudDisk.dao.BaseDao;
import com.cloudDisk.page.PageResult;
import com.cloudDisk.service.BaseService;
import com.cloudDisk.utils.QueryHelper;

// Service ����ʵ����
public class BaseServiceImpl<T> implements BaseService<T> {
	// service��Ȼ��ȡ��һ������ ��������ÿ�������ľ���ʵ�ֶ�Ҫ����Ӧ��dao����� ��
	// Ϊ�˴ﵽ�����Ч�����Ǳ����ڸ�����н��վ����dao����

	BaseDao<T> baseDao;

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public void save(T entity) {
		baseDao.save(entity);
	}

	public void update(T entity) {
		baseDao.update(entity);
	}

	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	public T findById(Serializable id) {
		return baseDao.findById(id);
	}

	public List<T> findObjects() {
		return baseDao.findObjects();
	}

	public List<T> findObjects(QueryHelper helper) {
		
		return baseDao.findObjects(helper);
	}

	public PageResult getPageResult(QueryHelper queryHelper, int currentPage,
			int pageSize) {
		return baseDao.getPageResult(queryHelper, currentPage, pageSize);
	}

}
