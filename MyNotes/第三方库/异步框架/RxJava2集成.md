
## 引入依赖

以下**RxAndroid库**已经包含**RxJava2库**，后者可以不用引入，引入后会覆盖**RxAndroid库**中的**旧版本RxJava2库**。
使用**新版本RxJava2库**应注意和**RxAndroid库**兼容。

```gradle
dependencies {
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'io.reactivex.rxjava2:rxjava:2.1.3'
}
```