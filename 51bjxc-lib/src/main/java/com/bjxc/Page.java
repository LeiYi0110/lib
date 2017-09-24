package com.bjxc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page<U> implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1119108689571941587L;

	private static int DEFAULT_PAGE_SIZE = 20;
    
    // 每页的记录数
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    // 当前页
    private int pageNo = 1;
    
    // 总记录数
    private long rowCount;
    
    // 当前页数据
    private List<U> data = Collections.EMPTY_LIST;
    
    
    /**
     * 构造方法，只构造空页.
     */
    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<U>());
    }
    
    /**
     * <默认构造函数>
     */
    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
    
    /**
     * 默认构造方法.
     * 
     * @param start
     *            本页数据在数据库中的起始位置
     * @param totalSize
     *            数据库中总记录条数
     * @param pageSize
     *            本页容量
     * @param data
     *            本页包含的数据
     */
    public Page(int pageNo, int totalSize, int pageSize, List<U> data) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.rowCount = totalSize;
        this.data = data;
    }
    
    /**
     * 取总页数.
     */
    public long getTotalPageCount() {
        if (rowCount % pageSize == 0)
            return rowCount / pageSize;
        else
            return rowCount / pageSize + 1;
    }
    
    public int getStartRow(){
    	return this.pageNo * this.pageSize;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getPageNo() {
        return pageNo;
    }
    
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    
    public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public List<U> getData() {
        return data;
    }
    
    public void setData(List<U> data) {
        this.data = data;
    }

}
