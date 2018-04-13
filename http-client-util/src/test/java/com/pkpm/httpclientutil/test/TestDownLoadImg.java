package com.pkpm.httpclientutil.test;

import com.pkpm.httpclientutil.HttpClientUtil;
import com.pkpm.httpclientutil.common.HttpConfig;
import com.pkpm.httpclientutil.exception.HttpProcessException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 下载demo
 *
 * @author zhangshuai
 * @version 1.0
 * @date now
 */
public class TestDownLoadImg {

    public static void main(String[] args) throws FileNotFoundException, HttpProcessException {
        String imgUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png"; //百度logo
        File file = new File("c:/baidu.png");
        HttpClientUtil.down(HttpConfig.custom().url(imgUrl).out(new FileOutputStream(file)));
        if (file.exists()) {
            System.out.println("图片下载成功了！存放在：" + file.getPath());
        }

        String mp3Url = "http://win.web.rh01.sycdn.kuwo.cn/resource/n1/24/6/707126989.mp3"; //四叶草-好想你
        file = new File("c:/好想你.mp3");
        HttpClientUtil.down(HttpConfig.custom().url(mp3Url).out(new FileOutputStream(file)));
        if (file.exists()) {
            System.out.println("mp3下载成功了！存放在：" + file.getPath());
        }
    }
}
