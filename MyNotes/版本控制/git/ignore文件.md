## 语法

目录前面有“/”符号的，表示和ignore文件同级目录下的文件或文件夹，如
```
/local.properties
/build
```

目录前面没有“/”符号的，表示和ignore文件同级目录及其所有子目录下的文件或者文件夹，如
```
local.properties
build
```

## Android工程的ignore文件配置：

在工程根目录下的`.gitignore`文件如下配置，其他目录下的`.gitignore`便不再需要，可将其删除。

```
*.iml
.gradle
bulid
.idea
local.properties
```