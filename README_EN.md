# README

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

base [**Kotlin**](https://kotlinlang.org)，include DataBinding、RxJava、Rxkoltin

![](http://i.imgur.com/TeDm72X.png)

### Structure



- Presenter uses Fragment，dispatch lifeCycle、coordinative with [RxLifecycle](https://github.com/trello/RxLifecycle "RxLifecycle") more convenience
- The modules are decoupled using generic creation, the base class structure is clear, such as Fragment's inheritance chain, lazy loading -> dataBinding -> business base class
- NetWork use Retrofit2、RxJava2，Cache use [RxCache](https://github.com/VictorAlbertos/RxCache "RxCache")，Net Monitor use [ReactiveNetwork](https://github.com/pwittchen/ReactiveNetwork "ReactiveNetwork")，PageRouter use [ActivityRouter](https://github.com/mzule/ActivityRouter "ActivityRouter")
- The project covers most of the kotlin operations，Use new features to encapsulate, such as **extended functions**、 **interface default implementation** compile：
 EventBus、GlobalNetMonitor、StateView、[multiProcessWebView（Branch）](https://github.com/vihuela/Kotlin-mvpro/tree/webViewMulProcess "独立进程WebView分支")、Simplified class inheritance chain
- All scenes are bundled with Rxlifecycle, follow View's lifecycle, reject memory leaks


![image](https://github.com/vihuela/Kotlin-mvpro/blob/master/gifdemo.gif ) 

### Usage

		compile 'com.ricky:mvpro-kotlin:1.3.2'

		OR
		compile('com.ricky:mvpro-kotlin:1.3.2', { exclude group: 'com.trello.rxlifecycle2' })

### apk

[点击下载](https://www.pgyer.com/naXB "点击下载")

![](https://www.pgyer.com/app/qrcode/naXB)

