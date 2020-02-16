package com.zyr._16哈希表.model;

import java.util.Objects;

/**
 * @author 张业荣
 * @Description
 * @Date 2020/2/13
 */
public class People
{
	private String name;// 姓名

	private Integer age;//年龄

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	/**
	 * 严禁条件下
	 * 重写hashCode方法
	 *
	 * @return
	 */
	@Override public int hashCode()
	{
		// 严格上
		int hash = name.hashCode();
		hash = 31 * hash + age.hashCode();

		return hash;
	}

	/**
	 * 严禁条件下
	 * 重写equalss方法
	 *
	 * @param obj
	 * @returns
	 */
	@Override public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		People that = (People) obj;
		return Objects.equals(this.name, that.name) && this.age == that.age;
	}

}
