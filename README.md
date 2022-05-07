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
    implementation 'com.github.wong1988:TBSCore:0.0.1'
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
