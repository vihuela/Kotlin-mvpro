   
## [English README](https://github.com/vihuela/Kotlin-mvpro/blob/master/README_EN.md "English README") ##

# 介绍 

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)



这个项目是基于[**Kotlin**](https://kotlinlang.org)下的MVP实践，使用DataBinding、RxJava、Rxkoltin

![](http://i.imgur.com/TeDm72X.png)

### 项目结构



- 符合[MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) 架构，Presenter使用Fragment，分发生命周期、与[RxLifecycle](https://github.com/trello/RxLifecycle "RxLifecycle")协作方便
- 各模块解耦使用泛型创建，基类结构清晰，如Fragment的继承链，懒加载->数据绑定->业务基类
- 网络使用Retrofit2、RxJava2，缓存使用[RxCache](https://github.com/VictorAlbertos/RxCache "RxCache")，网络监测使用[ReactiveNetwork](https://github.com/pwittchen/ReactiveNetwork "ReactiveNetwork")，页面路由使用[ActivityRouter](https://github.com/mzule/ActivityRouter "ActivityRouter")
- 项目涵盖大部分kotlin操作，使用新特性封装eventBus、xml文件一句话添加loadingView、全局网络监测、网络重连逻辑、stateView、[独立进程WebView（点进入分支）](https://github.com/vihuela/Kotlin-mvpro/tree/webViewMulProcess "独立进程WebView分支")、简化类继承链
- 所有场景的操作均与Rxlifecycle绑定，跟随View的生命周期


![image](https://github.com/vihuela/Kotlin-mvpro/blob/master/gifdemo.gif ) 

### 使用

		compile 'com.ricky:mvpro-kotlin:1.3.2'

		或者 外部引用了rxlifecycle2
		compile('com.ricky:mvpro-kotlin:1.3.2', { exclude group: 'com.trello.rxlifecycle2' })

### apk

[点击下载](https://www.pgyer.com/naXB "点击下载")

![](https://www.pgyer.com/app/qrcode/naXB)

### 注意事项

- java下此项目的版本可以参考：[RAD](https://github.com/vihuela/RAD)，Presenter带数据缓存，欢迎一起讨论
- **任何与项目有关或者有关kotlin学习可以通过issue交流，定知无不言**
- 感谢[Kotlin-Android-Template](https://github.com/nekocode/Kotlin-Android-Template "Kotlin-Android-Template")
