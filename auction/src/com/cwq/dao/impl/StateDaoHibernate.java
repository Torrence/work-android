package com.cwq.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cwq.dao.StateDao;
import com.cwq.model.State;

public class StateDaoHibernate extends HibernateDaoSupport implements StateDao {

	/**
	 * 删除状态
	 * 
	 * @param id
	 *            需要删除的状态id
	 */
	public void delete(Integer id) {
		getHibernateTemplate().delete(
				getHibernateTemplate().get(State.class, id));
	}

	/**
	 * 删除状态
	 * 
	 * @param state
	 *            需要删除的状态
	 */
	public void delete(State state) {
		getHibernateTemplate().delete(state);
	}

	/**
	 * 查询全部状态
	 * 
	 * @return 获得全部状态
	 */
	public List<State> findAll() {
		return getHibernateTemplate().find("from State");
	}

	/**
	 * 模块4
	 * 根据id查找状态
	 * 
	 * @param id
	 * 需要查找的状态id
	 */
	public State get(Integer id) {
		return (State) getHibernateTemplate().get(State.class, id);
	}

	/**
	 * 增加状态
	 * 
	 * @param state
	 * 需要增加的状态
	 */
	public void save(State state) {
		getHibernateTemplate().save(state);
	}

	/**
	 * 修改状态
	 * 
	 * @param state
	 * 需要修改的状态
	 */
	public void update(State state) {
		getHibernateTemplate().saveOrUpdate(state);
	}

}
