package com.led.model;

public class Product {
	//商品编号
	int id;
	//商品名称
	String name;
	//商品描述
	String description;
	//商品价格
	String price;
	//商品产地
	String area;
	//商品类型
	String type;

	public Product() {
		super();
	}

	public Product(int id, String name, String description, String price,
			String area, String type) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.area = area;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
