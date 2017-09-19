
## 引入依赖

*下文依赖库的版本号根据实际情况而定。*

**retrofit2库**中已经含有**okhttp3库**和**okio库**，后两者可以不用引入。

```gradle
dependencies {
    compile 'com.squareup.retrofit2:retrofit:2.3.0'
}
```

请求结果默认为`okhttp3.ResponseBody`类，如果要转换成`String`类型，需要引入以下依赖：
```gradle
compile 'com.squareup.retrofit2:converter-scalars:2.3.0'
```

如果返回的是json数据，要将json数据转换成实体类对象，需要引入以下依赖：
```gradle
compile 'com.squareup.retrofit2:converter-gson:2.2.0'
```

如果配合**RxJava2框架**使用，将请求方法返回的`Call`类转换成`Observable`类，需要引入以下依赖。
```gradle
compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
```

如果需要输出日志，需要添加以下依赖：
```gradle
compile 'com.squareup.okhttp3:logging-interceptor:3.7.0'
```

## 基本使用步骤

### 创建Service接口

参考[Retrofit2-注解.md](Retrofit2-注解.md)


### 创建Retrofit对象

如果需要自定义OKHttpClient配置，可参考如下代码：
```java
private static OkHttpClient buildOKHttpClient() {
    // 添加日志拦截器，非debug模式不打印任何日志
    //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    //loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE) ;

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
    .addInterceptor(new NetInterceptor())                     // 添加自定义的拦截器
    //.addInterceptor(buildTokenInterceptor())                // 添加token拦截器
    //.addNetworkInterceptor(buildCacheInterceptor())           // 添加网络缓存拦截器
    .addInterceptor(loggingInterceptor)                       // 添加日志拦截器
    //.cache(getCache())                                        // 设置缓存文件
    .retryOnConnectionFailure(true)                           // 自动重连
    .connectTimeout(15, TimeUnit.SECONDS)                     // 15秒连接超时
    .readTimeout(20, TimeUnit.SECONDS)                        // 20秒读取超时
    .writeTimeout(20, TimeUnit.SECONDS);                      // 20秒写入超时

    return builder.build();
}
```


```java
Retrofit retrofit = new Retrofit.Builder()
    //传入服务器主机地址BASE_URL
    .baseUrl(BASE_URL)
    //传入配置后的OkHttpClient对象，可省略，省略后生成默认的OkHttpClient对象
    .client(buildOKHttpClient())
    //传入Scalars转换器，将返回结果转换成String类型，不传则返回okhttp3.ResponseBody类型
    .addConverterFactory(ScalarsConverterFactory.create())
    //使用RxJava2框架将请求方法返回的Call类转换成Observable类
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build();
```

### 使用

```java
Service service = retrofit.create(Service.class);
//service.get(params)方法是Service接口事先定义的方法
Call<String> call = service.get(params);
//执行异步方法call.enqueue。（同步方法是call.execute，不适合网络请求）
call.enqueue(new Callback<String>() {
    @Override
    public void onResponse(Call<String> call, Response<String> response) {

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
});
```

### 注意事项

**别忘了添加网络权限。**