package com.example.ring.bean;

import org.w3c.dom.Text;

import java.sql.Blob;

public class GoodsBean {
    private int gid; //id 号
    private Integer goodsimage;
    private String goodsname; //商品名称
    private String goodsId; //商品条形码
    private String goodsms; //商品信息
    private String goodsus; //商品使用方法
    private String goodsbs; //商品保质期
    private String goodsts;//商品备注

    public Integer getImage() {
        return goodsimage;
    }

    public void setImage(Integer image) {
        this.goodsimage = image;
    }

    public int getgId() {
        return gid;
    }
    public void setgId(int id) {
        this.gid = id;
    }

    public  String getGoodsname() {
        return goodsname;
    }
    public void setGoodsname(String username) {
        this.goodsname = username;
    }

    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsbs() {return goodsbs;}
    public void setGoodsbs(String goodsbs) {
        this.goodsbs = goodsbs;
    }

    public void setGoodsus(String goodsus) {
        this.goodsus = goodsus;
    }
    public String getGoodsus() {
        return goodsus;
    }

    public void setGoodsts(String goodsts) {
        this.goodsts = goodsts;
    }
    public String getGoodsts() {
        return goodsts;
    }

    public String getGoodsms() {
        return goodsms;
    }
    public void setGoodsms(String goodsms) {
        this.goodsms = goodsms;
    }
}
