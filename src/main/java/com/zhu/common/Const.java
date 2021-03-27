package com.zhu.common;

public class Const {
    public static  final String CURRENT_USER="current_user";

    public static final String PHONE="phone";
    public static final String USERNAME="username";


    public interface Path{
        int profilePic=0;//头像路径
        int blogPic=1;//博客图片
        int commentPic=2;//评论图片
    }


    public enum ProductStatusEnum{
        ON_SALE(1,"在线");

        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code=code;
            this.value=value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

}
