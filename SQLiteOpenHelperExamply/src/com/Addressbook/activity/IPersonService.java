package com.Addressbook.activity;

import java.util.List;

public interface IPersonService {
	public void save(Person person);
	
	//更新
	public void update(Person person);
	
	//查找
	public Person find(Integer id);
	
	//删除
	public void delete(Integer id);
	
	//获得分页列表
	public List<Person> getScrollData(int firstResult, int maxResult);
	
	//获得记录数
	public long getCount();
}
