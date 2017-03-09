package com.cloudDisk.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cloudDisk.dao.BaseDao;
import com.cloudDisk.page.PageResult;
import com.cloudDisk.utils.QueryHelper;

//   HibernateDaoSupport ������sessionFactory���� ��Ҫ��spring�����ļ��н�������ע��
public class BaseDaoImpl<T> extends HibernateDaoSupport  implements BaseDao<T> {
	//------------------ ����clazz������������װ��ѯ�����------------------------
	Class<T> clazz; 
	public BaseDaoImpl(){
		//��ȡ���Ͳ��� 
		 ParameterizedType type=(ParameterizedType) this.getClass().getGenericSuperclass();
		 Type[] genericType = type.getActualTypeArguments();
		 clazz=(Class)genericType[0];
	}

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	public void delete(Serializable id) {
		getHibernateTemplate().delete(findById(id));
		
	}

	public T findById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public List<T> findObjects() {
		Session session=getSession();
		Query query = session.createQuery("FROM"+" "+clazz.getSimpleName());
		// ��ʵ������������䲻�᷵��null ������Ҳ���Ԫ��Ҳ�᷵��һ������Ϊ0 ��list����
		return query.list();
	}

	public List<T> findObjects(QueryHelper helper) {
				
		Session session=getSession();
		if(session==null){
			System.out.println("sessionΪ��");
		}
		Query query = session.createQuery(helper.getHql());
		List<Object> parameter = helper.getParameter();
		if(parameter!=null){
			for(int i=0;i<parameter.size();i++){
				query.setParameter(i, parameter.get(i));
			}
		}
		return query.list();
	}

	
	/**
	 * ��ҳ��ѯ
	 * currentPage��ǰҳ��
	 * pageSize ҳ��С
	 * @return ��ҳbean
	 */
	public PageResult getPageResult(QueryHelper queryHelper, int currentPage,
			int pageSize) {

		
		Query query = getSession().createQuery(queryHelper.getHql());
		List<Object> parameter = queryHelper.getParameter();
		
		if(parameter!=null){
			for(int i=0;i<parameter.size();i++){
				query.setParameter(i, parameter.get(i));
			}
		}
		if(currentPage<1){
			// �����ǰҳС��1 ����Խ�紦��
			currentPage=1;
		}
		query.setFirstResult((currentPage-1)*pageSize);
		query.setMaxResults(pageSize);
		
		//��ȡ�ܼ�¼��
		Query queryCount = getSession().createQuery(queryHelper.getCountHql());
		if(parameter!=null){
			for(int i=0;i<parameter.size();i++){
				queryCount.setParameter(i, parameter.get(i));
			}
		}
		long totalCount=(Long) queryCount.uniqueResult();
		List items=query.list();
		return new PageResult(totalCount, currentPage, pageSize, items);
	}

}
