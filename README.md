# TBSCore [![](https://jitpack.io/v/wong1988/TBSCore.svg)](https://jitpack.io/#wong1988/TBSCore)

Android开发工具包

Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Step 2. Add the dependency
```
dependencies {
    implementation 'com.github.wong1988:TBSCore:1.0.0'
}
```
Step 3. 在Application进行初始化
```
TBSCore.init(application,true);
```
Step 4. 清单文件配置 networkSecurityConfig(部分内核功能访问需要依赖http请求)
```
<application
    ...
     android:networkSecurityConfig="@xml/xxx">
```


## Method

```
// @return TbsReaderView  如果不为null，加入到容器里即可预览
// 为null 可以使用第三方软件进行打开
TbsReader.preOpen()

// 使用第三方软件进行打开文件
TbsReader.openByThirdPartyApp()

// 页面销毁时需要停用，否则下个文件打不开
TbsReader.stopTbsReader()
```