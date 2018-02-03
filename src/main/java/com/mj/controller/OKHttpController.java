package com.mj.controller;

import com.alibaba.fastjson.JSONObject;
import com.mj.configuration.RetryInterceptor;
import okhttp3.*;
import okio.Buffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ancun on 2018/1/30.
 */
@Controller
@RequestMapping(value = "/com/mj/http")
public class OKHttpController {
    //    @Autowired
    public AsyncRestTemplate asyncRestTemplate;

    @RequestMapping("/async")
    public String asyncReq() {
        String url = "http://localhost:8070/echar/getHistogram";
        ListenableFuture<ResponseEntity<JSONObject>> future = asyncRestTemplate.getForEntity(url, JSONObject.class);
        future.addCallback(new SuccessCallback<ResponseEntity<JSONObject>>() {
            public void onSuccess(ResponseEntity<JSONObject> result) {
                System.out.println(result.getBody().toJSONString());
            }
        }, new FailureCallback() {
            public void onFailure(Throwable ex) {
                System.out.println("onFailure:" + ex);
            }
        });
        return "this is async sample";
    }


    @Autowired
    RestTemplate restTemplate;

    /***********HTTP GET method*************/
    @RequestMapping("/sync")
    @ResponseBody
    public String hello() {
        String url = "http://localhost:8070/echar/getHistogram";
        JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();
        return json.toJSONString();
    }





    //设置连接超时时间
    public final static int CONNECT_TIMEOUT =60;
    ///设置读取超时时间
    public final static int READ_TIMEOUT=100;
    //设置写入超时时间
    public final static int WRITE_TIMEOUT=60;
//    Dispatcher dispatcher = new Dispatcher();

    private static  OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .readTimeout(6000, TimeUnit.MILLISECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .addInterceptor(new RetryInterceptor())//重试
            .build();;
    @RequestMapping("/okgo")
    @ResponseBody
    public String test3() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(100);
        dispatcher.setMaxRequestsPerHost(15);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(6000, TimeUnit.MILLISECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .dispatcher(dispatcher)
                .connectionPool(new ConnectionPool(10,5,TimeUnit.SECONDS))
                .addInterceptor(new RetryInterceptor())                         //重试
                .build();;



        String url = "http://localhost:8070/echar/getHistogram";
        for (int j = 0; j < 20; j++) {
            RequestBody body = new FormBody.Builder()
                    .add("key", String.valueOf(j))
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Call call = okHttpClient.newCall(request);
            System.out.println("线程Id为:" + Thread.currentThread().getId() );
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (e instanceof SocketTimeoutException) {
                        System.out.println("超时异常1" + call.request().body().toString());
//                        ((FormBody) ((RealCall) call).originalRequest.body()).encodedValues.toString();
                        Buffer buffer = new Buffer();
                        try {
                            call.request().body().writeTo(buffer);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        System.out.println(buffer);

                    }
                    if (e instanceof ConnectException) {
                        System.out.println("连接异常");
                        Buffer buffer = new Buffer();
                        try {
                            call.request().body().writeTo(buffer);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        System.out.println(buffer);

                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    System.out.println("我是异步线程,线程Id为:" + Thread.currentThread().getId() + "内容：" + response.body().string());
                }
            });
            for (int i = 0; i < 1; i++) {
                System.out.println("我是主线程,线程Id为:" + Thread.currentThread().getId());

//                    Thread.currentThread().sleep(100);
            }
        }

        return "true";
    }


}
