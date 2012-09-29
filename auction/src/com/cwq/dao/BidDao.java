package com.cwq.dao;

import java.util.List;

import com.cwq.model.AuctionUser;
import com.cwq.model.Bid;

public interface BidDao {
	/**
	 * 根据主键查找竞价
	 * 
	 * @param bidId
	 * 竞价id;
	 * @return id对应的竞价
	 */
	Bid get(Integer bidId);

	/**
	 * 模块5
	 * 保存竞价
	 * 
	 * @param bid
	 * 需要保存的竞价
	 */
	void save(Bid bid);

	/**
	 * 修改竞价
	 * 
	 * @param bid
	 * 需要修改的竞价
	 */
	void update(Bid bid);

	/**
	 * 删除竞价
	 * 
	 * @param id
	 * 需要删除的竞价id
	 */
	void delete(Integer id);

	/**
	 * 删除竞价
	 * 
	 * @param bid
	 * 需要删除的竞价
	 */
	void delete(Bid bid);

	/**
	 * 模块5
	 * 根据用户查找竞价
	 * 
	 * @param id
	 * 用户id
	 * @return 用户对应的全部竞价
	 */
	List findByUser(Integer userId);

	/**
	 * 模块6
	 * 根据物品id，以及出价查询用户
	 * @param itemId
	 * 物品id;
	 * @param price
	 * 竞价的价格
	 * @return 对指定物品出指定竞价的用户
	 */
	AuctionUser findUserByItemAndPrice(Integer itemId, Double price);

}
