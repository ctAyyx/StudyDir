package com.ct.aac.synthesize.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ClassName : BaseResponse
 * @CreateDate : 2020/4/15 9:23
 * @Author : CT
 * @Description :
 */
public class BaseResponse<T> {

    /**
     * data : [{"_id":"5e7225662b34a0e184e1e4b5","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-07-15 07:44:24","desc":"一个强大的侧滑框架，内置十多种侧滑效果：你知道微信侧滑返回的联动效果，但你见过开门效果、百叶窗效果吗？","images":["https://gank.io/images/1935393046654943aa501b9fbcaba60d","https://gank.io/images/3eb1465eb68a4ab28898a1b64e7d943e","https://gank.io/images/a4fadab7899445da8943a7db3549cd98","https://gank.io/images/bf5c297b04bd488196ffb2bb4c63412c"],"likeCounts":0,"publishedAt":"2019-07-17 13:39:51","stars":5,"title":"SmartSwipe","type":"Android","url":"https://github.com/luckybilly/SmartSwipe","views":84},{"_id":"5e7225672b34a0e184e1e4b6","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-07-10 07:26:32","desc":"AndroidBLE蓝牙框架，包括扫描、连接、设置通知、发送数据、读取、接收数据和OTA升级以及各种直观的回调，近乎一行代码植入项目，可扩展配置蓝牙相关操作。","images":["https://gank.io/images/c8e54d48949744c9b08d295a7e143f63","https://gank.io/images/545d2b3c61f84bf38b2273914cec6729","https://gank.io/images/2c75229e8ea1449788ef35cfeb6ae371","https://gank.io/images/75d488a8a4e44fc5a69c5556290013a7"],"likeCounts":0,"publishedAt":"2019-07-13 08:40:56","stars":4,"title":"Android-BLE","type":"Android","url":"https://github.com/Alex-Jerry/Android-BLE","views":90},{"_id":"5e7225672b34a0e184e1e4b7","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-07-10 07:27:17","desc":"AopArms编写了Android开发中常用的一套注解，如日志、异步处理、缓存、SP、延迟操作、定时任务、重试机制、try-catch安全机制、过滤频繁点击、拦截等，后续还会有更多更强大的注解功能加入","images":["https://gank.io/images/3a044422fdef45598706ef62b2bf848c","https://gank.io/images/ec1e923f52994469945e260d9c1e2c01"],"likeCounts":0,"publishedAt":"2019-07-13 08:40:32","stars":4,"title":"AopArms","type":"Android","url":"https://github.com/AICareless/AopArms","views":77},{"_id":"5e7225672b34a0e184e1e4b8","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-06-24 02:30:34","desc":"Android 录屏 && 音轨剪辑（剔除环境声音），抗住百万级日活APP挑战，附带详细 Blog 实现思路，","images":["http://gank.io/images/35ef5727556048c9bc2520dc9293d901"],"likeCounts":0,"publishedAt":"2019-06-27 02:38:12","stars":4,"title":"ScreenRecordHelper","type":"Android","url":"https://github.com/nanchen2251/ScreenRecordHelper","views":71},{"_id":"5e7225672b34a0e184e1e4ba","author":"李金山","category":"GanHuo","createdAt":"2019-06-13 09:16:24","desc":"自定义红点控件，不用修改之前的代码，完全解耦，可以支持设置在TextView，Button，LinearLayout，RelativeLayout，TabLayout等等控件上\u2026\u2026","images":["https://gank.io/images/5e037a23454b4af29a9542fea23e0478"],"likeCounts":0,"publishedAt":"2019-06-13 09:16:45","stars":3,"title":"YCRedDotView","type":"Android","url":"https://github.com/yangchong211/YCRedDotView","views":60},{"_id":"5e7225672b34a0e184e1e4bb","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-06-12 13:25:08","desc":"🦌 Flutter 学习练手项目。包括完整UI设计图，更贴近真实项目的练习。","images":["https://gank.io/images/8ff6422e608649a88c76bc0fe4b5d052","https://gank.io/images/16087665f170440cbde904dcb778f25c","https://gank.io/images/a95fc216ec764b9e9023347a45c26cc6","https://gank.io/images/25da49f1c8504ffcb7cf0345dc69485e"],"likeCounts":0,"publishedAt":"2019-06-13 06:33:47","stars":5,"title":"flutter_deer","type":"Android","url":"https://github.com/simplezhli/flutter_deer","views":308},{"_id":"5e7225672b34a0e184e1e4bc","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-06-11 03:10:44","desc":"BaseUrlManager 主要用于开发时，有多个环境需要打包APK的场景，通过BaseUrlManager提供的BaseUrl动态设置入口，只需打一次包，即可轻松随意的切换不同的开发环境或测试环境。在打生产环境包时，关闭BaseUrl动态设置入口即可。","images":["https://gank.io/images/58f58261eb174413adb26764edfbe55c"],"likeCounts":0,"publishedAt":"2019-06-12 01:02:00","stars":3,"title":"BaseUrlManager","type":"Android","url":"https://github.com/jenly1314/BaseUrlManager","views":75},{"_id":"5e7225672b34a0e184e1e4bd","author":"李金山","category":"GanHuo","createdAt":"2019-06-10 06:16:50","desc":"Flutter 版本一系列设计简洁的 loading 动画","images":["https://gank.io/images/45736392e6734f39a613afc68bc60328"],"likeCounts":0,"publishedAt":"2019-06-10 10:43:48","stars":5,"title":"loading_indicator_view","type":"Android","url":"https://github.com/Hitomis/loading_indicator_view","views":74},{"_id":"5e7225672b34a0e184e1e4be","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-06-04 10:02:17","desc":"Flutter完整开发实战详解系列，本系列将完整讲述：如何快速从 0 开发一个完整的 Flutter APP，配套高完成度 Flutter 开源项目 GSYGithubAppFlutter ","images":[],"likeCounts":0,"publishedAt":"2019-06-04 10:02:20","stars":1,"title":"GSYFlutterBook","type":"Android","url":"https://github.com/CarGuo/GSYFlutterBook","views":39},{"_id":"5e7225682b34a0e184e1e4bf","author":"潇湘剑雨","category":"GanHuo","createdAt":"2019-04-08 13:27:23","desc":"高仿探探首页波纹扫描效果","images":["https://gank.io/images/aeb06b2e84994851a3d01b73b8cf0776"],"likeCounts":0,"publishedAt":"2019-05-29 01:31:18","stars":4,"title":"TanTanRippleView","type":"Android","url":"https://github.com/hewking/TanTanRippleView","views":80}]
     * page : 3
     * page_count : 243
     * status : 100
     * total_counts : 2428
     */

    @SerializedName("page")
    public int page;
    @SerializedName("page_count")
    public int pageCount;
    @SerializedName("status")
    public int status;
    @SerializedName("total_counts")
    public int totalCounts;
    @SerializedName("data")
    public T data;
}
