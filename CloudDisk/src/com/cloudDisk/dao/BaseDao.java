package com.cloudDisk.dao;

import java.io.Serializable;
import java.util.List;

import com.cloudDisk.page.PageResult;
import com.cloudDisk.utils.QueryHelper;

public interface BaseDao<T> {
	  //����
		public void save(T entity);
		//����
		public void update(T entity);
		//ɾ��
		public void delete(Serializable id);
		//����id���� ʹ��Serializable �ӿ���Ϊ��ͨ���ԡ���Ϊid�����в�ͬ������
		public T findById(Serializable id);
		//����ȫ��
		public List<T> findObjects();
		//��������
		public List<T> findObjects(QueryHelper helper);
		
		// ���ڷ�ҳ
		public PageResult getPageResult(QueryHelper queryHelper, int currentPage,
				int pageSize);
}
