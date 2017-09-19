
## 引入依赖

见于Fragmentation库的github地址：
[https://github.com/YoKeyword/Fragmentation](https://github.com/YoKeyword/Fragmentation)

## 创建自己的Activity基类和Fragment基类

使用以下两种方法实现自己的Activity基类和Fragment基类，以后创建的Activity和Fragment均继承该基类，就可使用Fragmentation框架的功能。

### 方法一

Fragmentation v1.0.0开始，不强制继承SupportActivity，可使用接口＋委托形式来实现自己的SupportActivity或SupportFragment

**Activity基类**：

委托部分生命周期方法（其他的在**Fragment基类**中实现）和ISupportActivity接口方法

```java
public abstract class ProxyActivity extends AppCompatActivity implements ISupportActivity {

    private final SupportActivityDelegate DELEGATE = new SupportActivityDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DELEGATE.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        // wxm:设置Activity的容器
        setContentView(container);
        if(savedInstanceState == null) {
            // wxm:加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
            DELEGATE.loadRootFragment(R.id.delegate_container, getRootDelegate());
        }
    }

    /**
     * 设置根Delegate
     * @return LatteDelegate
     */
    public abstract LatteDelegate getRootDelegate();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DELEGATE.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DELEGATE.onBackPressed();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 实现 ISupportActivity 接口
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public void onBackPressedSupport() {
        DELEGATE.onBackPressedSupport();
    }

}
```

**Fragment基类**：

委托所有生命周期方法和ISupportFragment接口方法。

```java

    private final SupportFragmentDelegate DELEGATE = new SupportFragmentDelegate(this);
    protected FragmentActivity _mActivity = null;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DELEGATE.onAttach((Activity) context);
        _mActivity = DELEGATE.getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DELEGATE.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DELEGATE.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        DELEGATE.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (getLayout() instanceof Integer) {
            rootView = inflater.inflate((int) getLayout(), container, false);
        } else if (getLayout() instanceof View) {
            rootView = (View) getLayout();
        } else {
            throw new ClassCastException("getLayout() 方法返回类型必须是int或View");
        }

        // wxm:使用ButterKnife框架
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);

        return rootView;
    }

    /**
     * 设置Fragment的根布局
     *
     * @return 返回 int 或 View 类型
     */
    public abstract Object getLayout();

    /**
     * onCreateView 方法中，ButterKnife.bind 执行后
     *
     * @param savedInstanceState budle
     * @param rootView           Fragment 根布局
     */
    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);

    @Override
    public void onResume() {
        super.onResume();
        DELEGATE.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        DELEGATE.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        DELEGATE.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DELEGATE.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DELEGATE.onDestroy();
    }


    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 实现ISupportFragment接口
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public SupportFragmentDelegate getSupportDelegate() {
        return DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return DELEGATE.extraTransaction();
    }

    @Override
    public void enqueueAction(Runnable runnable) {
        DELEGATE.enqueueAction(runnable);
    }

    @Override
    public void onEnterAnimationEnd(@Nullable Bundle savedInstanceState) {
        DELEGATE.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        DELEGATE.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onSupportVisible() {
        DELEGATE.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        DELEGATE.onSupportInvisible();
    }

    @Override
    public boolean isSupportVisible() {
        return DELEGATE.isSupportVisible();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        DELEGATE.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public void setFragmentResult(int resultCode, Bundle bundle) {
        DELEGATE.setFragmentResult(resultCode, bundle);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        DELEGATE.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewBundle(Bundle args) {
        DELEGATE.onNewBundle(args);
    }

    @Override
    public void putNewBundle(Bundle newBundle) {
        DELEGATE.putNewBundle(newBundle);
    }

    @Override
    public boolean onBackPressedSupport() {
        return DELEGATE.onBackPressedSupport();
    }
}
```


### 方法二

Fragmentation v1.0.0之前，只能继承已有的SupportActivity/SupportFragment。

**Activity基类**：

```java
public abstract class ProxyActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        // wxm:设置Activity的容器
        setContentView(container);
        if(savedInstanceState == null) {
            // wxm:加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
            loadRootFragment(R.id.delegate_container, getRootDelegate());
        }
    }

    /**
     * 设置根Delegate
     * @return LatteDelegate
     */
    public abstract LatteDelegate getRootDelegate();
}
```

**Fragment基类**：

```java
abstract class BaseDelegate extends SupportFragment {

    private Unbinder mUnbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (getLayout() instanceof Integer) {
            rootView = inflater.inflate((int) getLayout(), container, false);
        } else if (getLayout() instanceof View) {
            rootView = (View) getLayout();
        } else {
            throw new ClassCastException("getLayout() 方法返回类型必须是int或View");
        }

        // wxm:使用ButterKnife框架
        mUnbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);

        return rootView;
    }

    /**
     * 设置Fragment的根布局
     *
     * @return 返回 int 或 View 类型
     */
    public abstract Object getLayout();

    /**
     * onCreateView 方法中，ButterKnife.bind 执行后
     *
     * @param savedInstanceState budle
     * @param rootView           Fragment 根布局
     */
    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }
}
```