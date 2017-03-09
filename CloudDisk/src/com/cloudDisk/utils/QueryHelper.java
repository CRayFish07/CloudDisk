package com.cloudDisk.utils;

import java.util.ArrayList;
import java.util.List;

// ��ѯ������
// ���ã������Ѿ������ϵĴ�ռλ���Ĳ�ѯ����Լ���ѯ����
//      ��װ��ѯ��䣬�Ͳ�ѯ����������dao����в���
public class QueryHelper {

	private String whereClause="";
	private String fromClause="";
	private String orderByClause ="";
	
	//����������ڴ�Ų�ѯ���� ����¶�ӿڸ����ȡ�ã�
    private List<Object> parameter;
    
    //������������ָ������˳��	    
    public static String ORDER_BY_DESC="DESC"; 
    public static String ORDER_BY_ASC="ASC"; 
    
    // һ����ѯһ������һ�� from �Ӿ�
    /**
     * 
     * @param clazz Ҫ��ѯ��Ŀ�����
     * @param alias ��ѯ����
     */
    public QueryHelper(Class clazz,String alias){
    	
    	fromClause="FROM "+clazz.getSimpleName()+" "+alias;
    	
    }
    
    
    /**
     * ����where�Ӿ�
     * @param condition ��ѯ������ ������name like ? 
     * @param params ��ѯ������������"��%"
     */
    public void setWhereClause(String condition,Object... params) {
    	if(whereClause.length()>1){
    		whereClause+=" "+"AND"+" "+condition;
    	}else{
    		whereClause+="WHERE"+" "+condition;
    	}
    	
    	if(params!=null&&params.length>0){
    		if(parameter==null){
	             //	��������Ϊ�յ�ʱ����new			
					parameter=new ArrayList<Object>();
				}
			for(Object obj:params){
				parameter.add(obj);
			}
    	}
		
	}


 /**
  * ���ò�ѯ����
  * @param orderBy ��ѯ˳�򣺿�ѡ�Ѿ�����õĳ���ֵ��
  * @param columnIndex Ҫ����������ֶ�
  */
	public void setOrderByClause(String orderBy,String columnIndex) {
		
		if(orderByClause.length()>1){
				orderByClause+=", "+columnIndex+" "+orderBy;
		}else{
		    // ��һ��׷��
			orderByClause+="ORDER BY"+" "+columnIndex+" "+orderBy;
		}
		
	}

	
	/**
	 * Ϊ�û��ṩ�Ѿ���֯�õ�Hql��ѯ���
	 * @return
	 */
    public String getHql(){
    	return fromClause+"  "+whereClause+" "+orderByClause;
    }
    
    
    /**
     * @return ��װ��ϵĲ�ѯ�������
     */
    public String getCountHql(){
    	return "SELECT COUNT(*) "+fromClause+"  "+whereClause;
    }
	
	 /**
	  * Ϊ�û���װ�˲�ѯ����
	  * @return ��ѯ������
	  */
    public List<Object> getParameter(){
    	return parameter;
    }

    
}
