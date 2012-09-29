package com.cwq.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cwq.dao.ItemDao;
import com.cwq.model.Item;

public class ItemDaoHibernate extends HibernateDaoSupport implements ItemDao {

	/**
	 * 修改物品
	 * 
	 * @param id
	 * 需要删除的物品id
	 */
	public void delete(Integer id) {
		getHibernateTemplate().delete(
				getHibernateTemplate().get(Item.class, id));
	}

	/**
	 * 删除物品
	 * 
	 * @param item
	 * 需要删除的物品
	 */
	public void delete(Item item) {
		getHibernateTemplate().delete(item);
	}

	/**
	 * 模块5
	 * 根据产品分类，获取当前拍卖的全部物品
	 * 
	 * @param kindId
	 *            种类id;
	 * @return 该类的全部产品
	 */
	public List<Item> findItemByKind(Integer kindId) {
		return getHibernateTemplate().find(
				"from Item as i where i.kind.id = ? and i.itemState.id = 1",
				kindId);
	}

	/**
	 * 模块4
	 * 根据所有者查找拍卖中的物品
	 * 
	 * @param useId
	 * 所有者Id;
	 * @return 该人拍卖中的全部物品
	 */
	public List<Item> findItemByOwner(Integer userId) {
		return getHibernateTemplate().find(
				"from Item as i where i.owner.id = ? and i.itemState.id = 1",
				userId);
	}

	 /**
	  * 模块2 模块6
      * 根据物品状态查找物品
      * @param stateId 状态Id;
      * @return 该状态下的全部物品
      */
    public List<Item> findItemByState(Integer stateId){
        return getHibernateTemplate().find("from Item as i where i.itemState.id = ?" , stateId);
    }

    /**
     * 模块1
     * 根据赢取者查找物品
     * @param userId 赢取者Id;
     * @return 该人赢取的全部物品
     */
    public List<Item> findItemByWiner(Integer userId){
        return getHibernateTemplate().find("from Item as i where i.winer.id = ? and i.itemState.id = 2" ,userId);
    }

    /**
     * 模块5
     * 根据主键查找物品
     * @param itemId 物品id;
     * @return id对应的物品
     */
    public Item get(Integer itemId){
        return (Item)getHibernateTemplate().get(Item.class , itemId);
    }
    /**
     * 模块4
     * 保存物品
     * @param item 需要保存的物品
     */    
    public void save(Item item){
        getHibernateTemplate().save(item);
    }

    /**
     * 模块5 模块6
     * 修改物品
     * @param item 需要修改的物品
     */
    public void update(Item item){
        getHibernateTemplate().saveOrUpdate(item);
    }

}
