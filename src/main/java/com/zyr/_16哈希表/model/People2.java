package com.zyr._16哈希表.model;

/**
 * @author 张业荣
 * @Description
 * @Date 2020/2/13
 */
public class People2
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
	 * 基于不严谨的条件下
	 * 重写hashCode方法
	 *
	 * @return
	 */
	@Override public int hashCode()
	{
		return 0;
		//		return unique().hashCode();
	}

	/**
	 * 基于不严谨的条件下
	 * 重写equals方法
	 *
	 * @param obj
	 * @return
	 */
	@Override public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		People2 that = (People2) obj;
		return this.unique().equals(that.unique());
	}

	/**
	 * 定义类的唯一性字符串
	 *
	 * @return
	 */
	public String unique()
	{
		// 这里是为了简便
		// 粗糙的认为说只要对象的核心字符串相等 就是equals了
		// 在实际应用上 可能会存在1定的问题
		return name + age;

	}

}
