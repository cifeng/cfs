package com.platform.cfs.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuEntity extends BaseEntity implements Comparable<MenuEntity>{
    //主键
    private Integer id;
    //标题
    private String title;
    //菜单还是功能点，菜单为1，功能点为2
    private Integer menuFunction;
    //父级id
    private Integer pid;
    //跳转的URL
    private String url;
    //图标，这块可以存类样式，后续配置一些固定的模板图标
    private  String icon;
    //排序使用的字段
    private  Integer sortNum;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //用来存放父级菜单名称
    private String pname;


    //用来存放子菜单的集合，只在查询菜单时使用
    private List<MenuEntity> child =new ArrayList<MenuEntity>();

    public List<MenuEntity> getChild() {
        return child;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getMenuFunction() {
        return menuFunction;
    }

    public void setMenuFunction(Integer menuFunction) {
        this.menuFunction = menuFunction;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public int compareTo(MenuEntity o) {
        if(this.sortNum>o.sortNum){
            return 1 ;
        }else if(this.sortNum<o.sortNum){
            return -1 ;
        }else{
            return this.sortNum.compareTo(o.sortNum) ;
        }
    }

    @Override
    public String toString() {
        return "MenuEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", menuFunction=" + menuFunction +
                ", pid=" + pid +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", sortNum=" + sortNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", pname='" + pname + '\'' +
                ", child=" + child +
                '}';
    }
}