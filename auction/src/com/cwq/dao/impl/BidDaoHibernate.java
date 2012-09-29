package com.cwq.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cwq.dao.BidDao;
import com.cwq.model.AuctionUser;
import com.cwq.model.Bid;

public class BidDaoHibernate extends HibernateDaoSupport implements BidDao {

	/**
	 * 删除竞价
	 * @param id
	 * 需要删除的竞价id
	 */
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		getHibernateTemplate()
				.delete(getHibernateTemplate().get(Bid.class, id));
	}

	/**
	 * 删除竞价
	 * 
	 * @param bid
	 * 需要删除的竞价
	 */
	public void delete(Bid bid) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(bid);
	}

	/**
	 * 模块5
	 * 根据用户查找竞价 
	 * @param id
	 * 用户id
	 * @return 用户对应的全部竞价
	 */
	public List findByUser(Integer userId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find(
				"from Bid as bid where bid.bidUser.id = ?", userId);
	}
	
	 /**
	  * 模块6
	  * 根据物品id，以及出价查询用户
	  * @param itemId 物品id;
	  * @param price 竞价的价格
	  * @return 对指定物品出指定竞价的用户
	 */
	public AuctionUser findUserByItemAndPrice(Integer itemId, Double price) {
		// TODO Auto-generated method stub
		Object[] args = { itemId, price };
		List l = getHibernateTemplate()
				.find(
						"from Bid as bid where bid.bidItem.id = ? and bid.bidPrice = ?",
						args);
		if (l.size() >= 1) {
			Bid b = (Bid) l.get(0);
			return b.getBidUser();
		} else {
			return null;
		}

	}

	/**
     * 根据主键查找竞价
     * @param bidId 竞价id;
     * @return id对应的竞价
     */
	public Bid get(Integer bidId) {
		// TODO Auto-generated method stub
		return(Bid)getHibernateTemplate().get(Bid.class , bidId);
	}

	 /**
	  * 模块5
     * 保存竞价
     * @param bid 需要保存的竞价
     */    
	public void save(Bid bid) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(bid);
	}
	
	 /**
     * 修改竞价
     * @param bid 需要修改的竞价
     */
	public void update(Bid bid) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(bid);
	}

}
