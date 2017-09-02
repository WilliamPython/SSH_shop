package cn.ccnu.shop.utils;

import java.util.List;

/**
 * 分页显示数据类
 * 
 * @author William Python
 * 
 */
public class PageBean<T> {
	// 分页基本属性
	private Integer page;// 当前页数.
	private Integer limit;// 每页显示记录数
	private Integer totalCount;// 总记录数
	private Integer totalPage;// 总页数
	// 加入下面三个属性，模拟百度分页策略,若采用其他策略，可以去掉这三个属性
	private Integer pageStart;// 显示的起始页码
	private Integer pageEnd;// 显示的尾页
	private final Integer showPages = 4;// 显示的总页数

	private List<T> list; // 显示到浏览器的数据.

	public Integer getPageStart() {
		return Math.max(1, this.getPage() - showPages / 2);
	}

	public Integer getPageEnd() {
		return Math.min(this.getTotalPage(), this.getPageStart() + showPages
				- 1);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
