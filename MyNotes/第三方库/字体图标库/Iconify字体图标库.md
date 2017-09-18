
## 相关网页

[Iconify库的github地址](https://github.com/JoanZapata/android-iconify)

[阿里巴巴字体图标库](http://www.iconfont.cn/)

[android-iconify 使用详解](http://www.cnblogs.com/zyw-205520/p/7266225.html?utm_source=debugrun&utm_medium=referral)


## android-iconify 的集成

### 1. 添加依赖

以下是全部的库，实际开发中可按照需要从中选择一个或几个库添加。

```groovy
dependencies {
    compile 'com.joanzapata.iconify:android-iconify-fontawesome:2.2.2' // (v4.5)
    compile 'com.joanzapata.iconify:android-iconify-entypo:2.2.2' // (v3,2015)
    compile 'com.joanzapata.iconify:android-iconify-typicons:2.2.2' // (v2.0.7)
    compile 'com.joanzapata.iconify:android-iconify-material:2.2.2' // (v2.0.0)
    compile 'com.joanzapata.iconify:android-iconify-material-community:2.2.2' // (v1.4.57)
    compile 'com.joanzapata.iconify:android-iconify-meteocons:2.2.2' // (latest)
    compile 'com.joanzapata.iconify:android-iconify-weathericons:2.2.2' // (v2.0)
    compile 'com.joanzapata.iconify:android-iconify-simplelineicons:2.2.2' // (v1.0.0)
    compile 'com.joanzapata.iconify:android-iconify-ionicons:2.2.2' // (v2.0.1)
}
```

### 2. 初始化库

在自定义的Application中初始化，根据所添加的依赖初始化。

```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify
            .with(new FontAwesomeModule())
            .with(new EntypoModule())
            .with(new TypiconsModule())
            .with(new MaterialModule())
            .with(new MaterialCommunityModule())
            .with(new MeteoconsModule())
            .with(new WeathericonsModule())
            .with(new SimpleLineIconsModule())
            .with(new IoniconsModule());
    }
}
```

### 3. 在布局文件中使用

在“{}”中使用字体图标的key获得字体图标，“fa-”开头的是FontAwesome库的图标。
FontAwesome库中的所有图标在`FontAwesomeIcons`类中查看。

可以在[http://fontawesome.io/icons/](http://fontawesome.io/icons/)网站或者[Font Awesome 中文网](http://www.fontawesome.com.cn/faicons/)网站
预览图标。使用方法：
复制`FontAwesomeIcons`枚举类实例名“fa_”后面的字符串到以上网站查询，即可预览对应的图标大概的样子。

```xml
<com.joanzapata.iconify.widget.IconTextView
    android:text="I {fa-heart-o} to {fa-code} on {fa-android}"
    android:shadowColor="#22000000"
    android:shadowDx="3"
    android:shadowDy="3"
    android:shadowRadius="1"
    android:textSize="40sp"
    android:textColor="#FF..."
    ... />
```

### 4. 其他使用方法

其他使用方法见于：[Iconify库的github地址](https://github.com/JoanZapata/android-iconify)

## android-iconify 的封装

以下代码是将Iconify初始化的过程封装到Configurator类中

```java
public final class Configurator {
    // wxm:保存将要初始化的字体图标
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    /* Configurator单例构建代码及其他初始化配置代码省略…… */

    // wxm:配置完成
    public void cofigure() {
        initIcons();
    }

    // wxm:添加将要初始化的字体图标
    public Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    // wxm:初始化添加的字体图标
    private Configurator initIcons() {
        if (ICONS.size() > 0) {
            // Iconify库初始化方法
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
        return this;
    }
}
```


## 使用阿里巴巴图标库的图标（自定义FontModule）

### 1. 下载图标代码

前往[阿里巴巴字体图标库](http://www.iconfont.cn/)网站，将所需图标添加到购物车，添加完完毕后到购物车页面，点击“**下载代码**”。

### 2. 添加资源文件

将下载的压缩包解压后，复制 **iconfont.ttf** 文件到项目的 **assets** 目录下。这里MoocEC项目则添加到**业务Model**下的 **assets** 目录下。
在解压后的文件中，点击**demo_unicode.html**文件可以查看字体图标的Unicode代码。

### 3. 自定义FontModule

参考`FontAwesomeModule`类，实现`IconFontDescriptor`接口和`Icon`接口。
点击**demo_unicode.html**文件可以查看字体图标的Unicode代码，将“&#x”替换成“\u”，保存在`Icon`的实现类（枚举）中。

```java
public class FontEcModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return EcIcons.values();
    }
}
```

```java
public enum EcIcons implements Icon {
    icon_scan('\ue66a'),
    icon_ali_pay('\ue67c');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        //将下划线替换为中划线，方便使用
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
```

### 4. 自定义FontModule的使用

参考`FontAwesomeModule`的使用。

初始化：
```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify
            .with(new FontEcModule());
    }
}
```

在布局文件中使用：
```xml
  <com.joanzapata.iconify.widget.IconTextView
        android:text="你好{icon-ali-pay}"
        android:layout_centerInParent="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

