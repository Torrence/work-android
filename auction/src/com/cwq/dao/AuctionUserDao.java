package com.cwq.dao;

import java.util.List;

import com.cwq.model.AuctionUser;

public interface AuctionUserDao {
	/**
	 * 模块4
     * 根据id查找用户
     * @param id 需要查找的用户id
     */  
    AuctionUser get(Integer id);
    /**
     * 增加用户
     * @param user 需要增加的用户
     */       
    void save(AuctionUser user);

    /**
     * 修改用户
     * @param user 需要修改的用户
     */  
    void update(AuctionUser user);

    /**
     * 删除用户
     * @param id 需要删除的用户id
     */  
    void delete(Integer id);

    /**
     * 删除用户
     * @param user 需要删除的用户
     */  
    void delete(AuctionUser user);

    /**
     * 查询全部用户
     * @return 获得全部用户
     */ 
    List<AuctionUser> findAll();

    /**
     * 根据用户名，密码查找用户
     * @param username 查询所需的用户名
     * @param pass 查询所需的密码
     * @return 对应的用户
     */ 
    AuctionUser findUserByNameAndPass(String username , String pass);
    
    boolean userExsited(AuctionUser user);
}
