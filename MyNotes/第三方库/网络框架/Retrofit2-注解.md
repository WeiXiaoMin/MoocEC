
# Retrofit注解的含义

参考：[简书-Retrofit 2.0 注解篇](http://www.jianshu.com/p/bf884248cb37)
和[简书-你真的会用Retrofit2吗?Retrofit2完全教程](http://www.jianshu.com/p/308f3c54abdd)

示例代码可参考[简书-你真的会用Retrofit2吗?Retrofit2完全教程](http://www.jianshu.com/p/308f3c54abdd)中的示例代码，
其仓库地址为[https://github.com/ikidou/Retrofit2Demo](https://github.com/ikidou/Retrofit2Demo)

Retrofit 共22个注解，根据功能大概分为三类：
1. 请求方法类
2. 标记类
3. 参数类

*因为get请求和post请求比较常用，以下说明主要主要针对这两者说明。*


### 1. 请求方法类
| 注解 | 含义 |
| :--- | :--- |
| @GET | 该方法是get请求 |
| @POST | 该方法是post请求 |
| @PUT | 该方法是put请求 |
| @DELETE | 该方法是delete请求 |
| @HEADER | 该方法是header请求 |
| @OPTIONS | 该方法是options请求 |
| @HTTP | 可以配置多种属性，包括以上7中方法 |

### 2. 标记类
| 注解 | 含义 |
| :--- | :--- |
| @FormUrlEncoded | 用于post请求，表示请求体是Form表单 |
| @Multipart | 用于post请求，表示请求体是支持文件上传的 From 表单 |
| @Streaming | 响应体的数据以流的形式返回，否则将一次性加载到内存中 |

### 3. 参数类
*没有特殊说明表示该注解作用于方法参数（即在方法参数前面注解）。*

| 注解 | 含义 |
| :--- | :--- |
| @Headers | 作用于方法（即在方法前面注解），添加请求头 |
| @Header | 作用于方法参数（即在形参面前注解），通过传参的方式添加请求头 |
| @Body | 用于post的普通请求，表示请求体非Form表单，该方法不含@FormUrlEncoded注解 |
| @Field | 用于post的表单请求（方法含@FormUrlEncoded注解），表示表单的参数（字段） |
| @FieldMap | 用于post的表单请求，表示表单参数（字段）以map集合形式传入，非String类型的参数会调用其toString方法 |
| @Part | 用于post的文件上传（方法含@Multipart注解），表示多段请求体（MultipartBody）中的其中一段 |
| @PartMap | 用于post的文件上传，@Part类型参数的map集合形式 |
| @Url | 和baseUrl拼接后形成完整的url |
| @Path | url中的一个字段，用于补全url |
| @Query | 用于get请求，url中的一个参数 |
| @QueryMap | 用于get请求，url中的参数以map集合的形式传入 |

**补充说明：**

* **@Part**
    1. 参数默认是`okhttp3.MultipartBody.Part`类型,可直接使用，如`@Part MultipartBody.Part part`。
    2. 如果参数类型是`okhttp3.RequestBody`，在注解中提供一个名字，如`@Part("foo") RequestBody foo`。
    3. 如果参数是其他类型，需通过`Converter`类来转换，并在注解中提供一个名字，如`@Part("foo") Image photo`。
    `Converter`类通过`Retrofit.Builder`的`addConverterFactory`方法添加，详情见[Retrofit2集成.md](Retrofit2集成.md)。

* **@PartMap**
    1. 参数默认是value为`okhttp3.RequestBody`类型的map集合，如`@PartMap Map<String, RequestBody> params`。
    2. 如果该map集合的value不是`okhttp3.RequestBody`类型，需要`Converter`类来转换。
    3. `okhttp3.MultipartBody.Part`类型不适用。
    4. map中的key作用相当于@Part注解中提供的名字（猜测）。