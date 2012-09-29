package com.cwq.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cwq.dao.KindDao;
import com.cwq.model.Kind;

public class KindDaoHibernate extends HibernateDaoSupport implements KindDao {

	 /**
     * 删除种类
     * @param id 需要删除的种类id
     */  
    public void delete(Integer id){
        getHibernateTemplate().delete(getHibernateTemplate().get(Kind.class , id));  
    }

    /**
     * 删除种类
     * @param kind 需要删除的种类
     */  
    public void delete(Kind kind){
        getHibernateTemplate().delete(kind);  
    }

    /**
     * 模块3 模块5
     * 查询全部种类
     * @return 获得全部种类
     */ 
    public List<Kind> findAll(){
        return getHibernateTemplate().find("from Kind");
    }

    /**
     * 模块4
     * 根据id查找种类
     * @param id 需要查找的种类id
     */  
    public Kind get(Integer id){
        return (Kind)getHibernateTemplate().get(Kind.class , id);
    }


	 /**
	  * 模块3
     * 增加种类
     * @param kind 需要增加的种类
     */       
    public void save(Kind kind){
        getHibernateTemplate().save(kind);  
    }
      

    /**
     * 修改种类
     * @param kind 需要修改的种类
     */  
    public void update(Kind kind){
        getHibernateTemplate().saveOrUpdate(kind);  
    }

}
