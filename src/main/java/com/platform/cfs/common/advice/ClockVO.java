package com.platform.cfs.common.advice;


import com.platform.cfs.common.annotation.Excel;
import com.platform.cfs.common.annotation.ImportExcel;

public class ClockVO {
    @ImportExcel(fieldName="姓名",order = 1)
    @Excel(fieldName="姓名",fieldWidth=30,exportConvertSign=0)
    private String  name;
    @Excel(fieldName="日期",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="日期",order = 1)
    private String  date;
    @Excel(fieldName="对应时段",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="对应时段",order = 1)
    private String  timeInterval;
    @Excel(fieldName="上班时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="上班时间",order = 1)
    private String  workTime;//上班班时间
    @Excel(fieldName="下班时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="下班时间",order = 1)
    private String offWorkTime;//下班时间
    @Excel(fieldName="签到时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="签到时间",order = 1)
    private String qiandao;
    @Excel(fieldName="签退时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="签退时间",order = 1)
    private String qiantui;
    @Excel(fieldName="应到",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="应到",order = 1)
    private String yingdao;
    @Excel(fieldName="实到",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="实到",order = 1)
    private String shidao;
    @Excel(fieldName="迟到时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="迟到时间",order = 1)
    private String chidaoshijian;
    @Excel(fieldName="早退时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="早退时间",order = 1)
    private String zaotuishijian;
    @Excel(fieldName="是否旷工",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="是否旷工",order = 1)
    private String shifoukuanggong;
    @Excel(fieldName="加班时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="加班时间",order = 1)
    private String jiabanshijian;
    @Excel(fieldName="工作时间",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="工作时间",order = 1)
    private String gongzuoshijian;
    @Excel(fieldName="例外情况",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="例外情况",order = 1)
    private String liwaiqingkuang;
    @Excel(fieldName="应签到",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="应签到",order = 1)
    private String yingqiandao;
    @Excel(fieldName="应签退",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="应签退",order = 1)
    private String yingqiantui;
    @Excel(fieldName="部门",fieldWidth=30,exportConvertSign=0)
    @ImportExcel(fieldName="部门",order = 1)
    private String bumen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getOffWorkTime() {
        return offWorkTime;
    }

    public void setOffWorkTime(String offWorkTime) {
        this.offWorkTime = offWorkTime;
    }

    public String getQiandao() {
        return qiandao;
    }

    public void setQiandao(String qiandao) {
        this.qiandao = qiandao;
    }

    public String getQiantui() {
        return qiantui;
    }

    public void setQiantui(String qiantui) {
        this.qiantui = qiantui;
    }

    public String getYingdao() {
        return yingdao;
    }

    public void setYingdao(String yingdao) {
        this.yingdao = yingdao;
    }

    public String getShidao() {
        return shidao;
    }

    public void setShidao(String shidao) {
        this.shidao = shidao;
    }

    public String getChidaoshijian() {
        return chidaoshijian;
    }

    public void setChidaoshijian(String chidaoshijian) {
        this.chidaoshijian = chidaoshijian;
    }

    public String getZaotuishijian() {
        return zaotuishijian;
    }

    public void setZaotuishijian(String zaotuishijian) {
        this.zaotuishijian = zaotuishijian;
    }

    public String getShifoukuanggong() {
        return shifoukuanggong;
    }

    public void setShifoukuanggong(String shifoukuanggong) {
        this.shifoukuanggong = shifoukuanggong;
    }

    public String getJiabanshijian() {
        return jiabanshijian;
    }

    public void setJiabanshijian(String jiabanshijian) {
        this.jiabanshijian = jiabanshijian;
    }

    public String getGongzuoshijian() {
        return gongzuoshijian;
    }

    public void setGongzuoshijian(String gongzuoshijian) {
        this.gongzuoshijian = gongzuoshijian;
    }

    public String getLiwaiqingkuang() {
        return liwaiqingkuang;
    }

    public void setLiwaiqingkuang(String liwaiqingkuang) {
        this.liwaiqingkuang = liwaiqingkuang;
    }

    public String getYingqiandao() {
        return yingqiandao;
    }

    public void setYingqiandao(String yingqiandao) {
        this.yingqiandao = yingqiandao;
    }

    public String getYingqiantui() {
        return yingqiantui;
    }

    public void setYingqiantui(String yingqiantui) {
        this.yingqiantui = yingqiantui;
    }

    public String getBumen() {
        return bumen;
    }

    public void setBumen(String bumen) {
        this.bumen = bumen;
    }
}
