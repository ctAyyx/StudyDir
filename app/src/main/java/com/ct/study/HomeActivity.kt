package com.ct.study

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.ct.study.notification.NotificationActivity
import com.ct.study.rv.RvActivity
import com.ct.study.test.GankDb
import com.ct.study.ui.DialogActivity
import com.ct.study.ui.MethodActivity
import com.ct.study.ui.ScrollerActivity
import kotlinx.android.synthetic.main.activity_home.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.regex.Pattern

import kotlin.reflect.KClass

class HomeActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_home_net -> {//关于网络模块的使用
                val db = Room.databaseBuilder(this, GankDb::class.java, "test.db").build()
                Log.e("TAG", "$db")
                db.userDao()
            }
            R.id.btn_home_dialog -> {
                //关于Dialog模块的使用
                readyGo(DialogActivity::class)
            }
            R.id.btn_home_method -> readyGo(MethodActivity::class)
            R.id.btn_home_scroller -> readyGo(ScrollerActivity::class)
            R.id.btn_home_file -> {
                //请求权限
                EasyPermissions.requestPermissions(
                    this,
                    "权限",
                    100,
                    *arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }

            R.id.btn_home_replace -> {
                formatName(btn_home_replace, "名字")

            }
            R.id.btn_home_recycler -> startActivity(Intent(this, RvActivity::class.java))
            R.id.btn_home_notification -> startActivity(
                Intent(
                    this,
                    NotificationActivity::class.java
                )
            )
            R.id.btn_home_matcher -> {
                //getImage()
                tv_img.text = Html.fromHtml(getStr2())
            }

        }
    }

    fun formatName(view: TextView, name: String?) {
        if (name.isNullOrEmpty()) {
            view.text = ""
            return
        }

        if (name.length < 3)
            view.text = name
        else {
            var format = name.substring(0, 2)
            (0..name.length - 3).forEach {
                format += "*"
            }
            view.text = format
        }
    }

    private fun readyGo(cls: KClass<*>) {
        val intent = Intent(this, cls.java)
        startActivity(intent)
    }


    /**
     * 获取权限 创建文件
     * */

    private fun createFile() {
        val rootFile = Environment.getRootDirectory()

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        createFile()
    }

    private val pattern =
        "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?|(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?"


    private fun getStr() = """        
        目前对于中国篮球来说是非常平静的，我们看到CBA至今无法恢复比赛，国家队的奥运会落选赛也延期了
        。但此刻仍然两支国家队正在集训，他们就是中国男篮U17和U18。其实他们原本都有各自的比赛任务，U17准备参加今年的世青赛，U18则是要打亚青赛
        ，但现在因为疫情的缘故这些比赛到底何时进行变成未知了。对于这些年轻球员来说，首先训练肯定是基础，但没有热身赛可打显然不利于提高，
        于是经过教练组的安排，U17和U18来了一场热身赛。最终的结果竟然是低一级别的U17获胜，这样的状况堪比二年级打赢了三年级，这些年轻球员的表现值得表扬。
        http://static.qiecdn.com/static/04142120/news/res/common/load-default.png
        http://images.qiecdn.com/news-aHR0cDovL2luZXdzLmd0aW1nLmNvbS9uZXdzYXBwX21hdGNoLzAvMTE2NTA1MjE0NjcvMA
        去年中国男篮在世界杯没能拿到奥运会门票，当时引发了很多讨论，其中重视青训一再被提起。而篮协也给出了回应，现在姚明为首的管理层非常重视国家队的各级梯队，现任U18的主教练是刘鹏，他曾经执教过CBA上海男篮，而且取得了不错的成绩，此前还曾是中国男篮主教练李楠的助手。而U17主教练是王锡东，他有着多年梯队的执教经验。
        由于U17和U18的球员年纪相仿，所以在比赛时谁也不服输，对抗非常激烈。其中海外军团之一的张知垚发挥非常亮眼，这位内线球员身高有207公分，有一手三分球，全场比赛8投3中，最终拿下20分9篮板。同时移动速度还不错，具备了现代篮球的内线球员特点，是这支U18的当家球星。张知垚目前在西班牙皇家贝蒂斯梯队打球，多年的欧洲磨练让他迅速成长，预计以后会是中国男篮的一员。
        http://static.qiecdn.com/static/04142120/news/res/common/load-default.png
        表现不错的还有同样是海外留学的戴昊，这位小将身高193公分左右，场上司职后卫，目前在德国阿尔巴柏林梯队打球。这场比赛他得到12分6篮板，展示了自己不错的技术水准。
        而U17这边林葳的表现最为亮眼，来自福建队的他全场比赛砍下17分，还有上海队的马典成拿下了13分9篮板，在这两人的带领下球队最终获胜。
        http://static.qiecdn.com/static/04142120/news/res/common/load-default.png
        在男篮世界杯结束后中国男篮进行了人员变动，其中杜锋将出任新的国家主教练。之前他接受过新华社的采访，当时透露未来的国家队要为以后的奥运会、世界杯考虑，显然是想球员组成更年轻化。而现在这批U17、U18就是未来的球员基础，特别是那些在海外锻炼的年轻球员更被看好。比如张知垚，有身高，还能投射，移动速度不慢，这显然是杜锋的战术体系需要的内线球员。最后希望这些球员能够快速成长，中国男篮真的靠你们了。
    """


    private fun getStr2() = """
        
        <div class="art-content is-data"><div class="item-img"><p class="img_wrap"><img style="display: inline;" src="http://images.qiecdn.com/news-aHR0cDovL2luZXdzLmd0aW1nLmNvbS9uZXdzYXBwX21hdGNoLzAvMTE2NTAyODU4MDUvMA" data-thumb="//images.qiecdn.com/undefined" data-original="http://images.qiecdn.com/news-aHR0cDovL2luZXdzLmd0aW1nLmNvbS9uZXdzYXBwX21hdGNoLzAvMTE2NTAyODU4MDUvMA" title=""></p></div><div class="item-text"><p class="text">更衣室里另一个经历过10年前那次总决赛的人是韩德君，在那个所有人都忙着分享喜悦的时刻里，韩德君也不例外的咧着嘴，脸上挂着看上去颇为憨厚的笑容。但出乎很多人意料的是，在性格上显得有点内敛的韩德君，这一次竟然敢于在电视直播的镜头前奔放了一把，当着千千万万的观众说出了一句最动人的情话：“亲爱的，我爱你！</p></div><div class="item-text"><p class="text">这句话是韩德君说给自己的妻子，简单朴实，但至情至性，可以说是当着全国球迷的面秀了一把恩爱。但是在这句情话中，除了流露出韩德君对妻子浓浓的爱意之外，在其背后还隐藏着一丝丝的愧疚，那是因为韩德君曾对妻子许下的一个诺言。</p></div><div class="item-text"><p class="text">其实关于这段故事，很多球迷都早已是耳熟能详。在2013年第十二届全运会期间，当时韩德君正随着辽篮全力准备冲击冠军。在一次接受媒体公开采访的时候，韩德君被问到了有关个人婚姻方面的问题，于是他当时有点半开玩笑的说道：“还没能帮助辽宁拿过一个冠军就结婚，那我多对不起辽宁的球迷啊。”</p></div><div class="item-img"><p class="img_wrap"><img src="//static.qiecdn.com/static/04142120/news/res/common/load-default.png" data-thumb="//images.qiecdn.com/undefined" data-original="http://images.qiecdn.com/news-aHR0cDovL2luZXdzLmd0aW1nLmNvbS9uZXdzYXBwX21hdGNoLzAvMTE2NTAyODU4MDYvMA" title=""></p></div><div class="item-text"><p class="text">没想到在那一年的全运会上，辽篮虽然一度拥有很好的机会，但最终还是在决赛上不敌易建联领衔的广东队，再次遗憾的错失冠军。这让韩德君之前说过的那句话，竟变相地成为了他和妻子之间的一个承诺，或者说是约定，关于韩德君“不夺冠就不结婚”的说法，从那时起就开始广为流传了。</p></div><div class="item-text"><p class="text">但是过了没两年，大家却突然发现韩德君虽然一直没有传出来结婚的消息，但却已经有儿子了！这还是因为在2015年初国家队集训的时候，宫鲁鸣一次说走嘴泄露的消息，当时韩德君曾推迟向国家队报道，理由就是尚在襁褓的儿子生病了需要照顾。这一下可让不少球迷既感到惊喜的同时，又不禁纳闷，这丝毫没有结婚的消息就突然先有了这么大的儿子出来，到底是怎么一回事儿？</p></div><div class="item-text"><p class="text">不过韩德君对于外界的种种猜测丝毫没有在意，反而是在2016年重新开启了个人微博之后，突然变身为“晒娃狂魔”，频频发布自己与儿子互动的照片、视频，也算是间接的证实了自己已有接班人的事实。</p></div><div class="item-img"><p class="img_wrap"><img src="//static.qiecdn.com/static/04142120/news/res/common/load-default.png" data-thumb="//images.qiecdn.com/undefined" data-original="http://images.qiecdn.com/news-aHR0cDovL2luZXdzLmd0aW1nLmNvbS9uZXdzYXBwX21hdGNoLzAvMTE2NTAyODU4MDcvMA" title=""></p></div><div class="item-text"><p class="text">一直到在2017年的全明星赛期间，我曾经借一次单独采访的机会和韩德君聊起了这个话题，韩德君才第一次袒露心声，谈起了自己与妻子、儿子之间的一些故事，并解释了自己“未婚先有子”的原因。</p></div><div class="item-text"><p class="text">事实上，韩德君也算不得真正的“未婚”，他和妻子早在2013年全运会之后就走进了婚姻的殿堂，只不过他们仅仅是低调的到民政局登记并领取了《结婚证》，却并么有举办正式的婚礼。关于这一点，韩德君在当时也坦言：“我还欠她一个婚礼没有办呢！”</p></div><div class="item-text"><p class="text">原来，韩德君的老家在大连旅顺的农村，按照当地的习俗，结婚的新人如果没办过婚礼喜宴，就算不得真正的结婚。当然，这只是一种民俗，并不与国家法定的婚姻事实产生矛盾。但韩德君却因为当初许下的那个承诺，一直希望能够真正做到夺得一次冠军之后，再和妻子补上婚礼。</p></div><div class="item-text"><p class="text">在和我谈到这个话题的时候，韩德君正随着辽篮经历那个格外艰难的2016-2017赛季，多名主力因伤缺阵，再加上此前两个赛季连续与冠军失之交臂，当时让韩德君对于自己是否真能等到实现承诺的那一天，也显得有一些迷茫。他说内心里其实很感激妻子的体贴与理解，从来没有在办婚礼这件事儿上催促过自己，但是他也不想让妻子就这样一直等着。韩德君甚至在内心里做了最坏的打算，“如果一直等到我退役了，还没能拿个冠军的话，我肯定也会给她一个交代！”</p></div><div class="item-text"><p class="text">幸好这个“交代”并没有等的太久，在2017年的第十三届全运会上，韩德君就跟随辽篮夺得了球队在58年全运会历史上的首个冠军，从那一刻起韩德君其实就已经完成了自己的承诺，帮助辽篮拿到了一个很有分量的冠军。</p></div><div class="item-img"><p class="img_wrap"><img src="//static.qiecdn.com/static/04142120/news/res/common/load-default.png" data-thumb="//images.qiecdn.com/undefined" data-original="http://images.qiecdn.com/news-aHR0cDovL2luZXdzLmd0aW1nLmNvbS9uZXdzYXBwX21hdGNoLzAvMTE2NTAyODU4MDgvMA" title=""></p></div><div class="item-text"><p class="text">但是在那个时候，距离新赛季CBA联赛开始也已经近了，而且韩德君和他的队友们也都还对联赛冠军抱有十分的渴望，更是期待能够借助全运会夺冠的这股气势，在联赛中再次发起冲击。因此韩德君在全运会夺冠后对妻子说的第一句话就是，“联赛让我再努力下，再等我一年！”</p></div><div class="item-text"><p class="text">这一等，不到一年，韩德君终于实现了心中最圆满的夙愿，全运会和联赛的双料冠军，这已经是一个最为圆满的故事结局，韩德君也终于可以和妻子补办上这个迟来的婚礼。</p></div><div class="item-text"><p class="text">只不过略微有些遗憾的是，韩德君还是决定将婚礼再推迟一段时间，他解释说，自己太看重这个婚礼了，非常希望能够自己亲自参与到策划当中，给妻子办一个完美的婚礼。但是因为在联赛中的出色表现，他再次接到了国家队的征召，今年夏天的大部分时间都将要随国家队一起集训、比赛，这势必让他很难抽出时间去亲力亲为的策划一场婚礼。</p></div><div class="item-text"><p class="text">所以在这样的情况下，韩德君才不得不做出再次推迟婚礼的决定，这样一番解释想必也能够被妻子所理解。只不过他自己心中也清楚，这必然还是对妻子有所亏欠，毕竟她已经等了自己那么长的时间，却还要继续再等下去，所以最后韩德君当着全国观众面说出的“亲爱的，我爱你！”，既是最动情的表白，也是对妻子的感激！</p></div><div class="item-text"><p class="text">下期预告：《更衣室里的特别来宾》</p></div><div class="item-text"><p class="text">连载内容选自《我们是冠军》——辽篮冠军之路全纪实</p></div></div>
        
    """.trimIndent()


    private fun getImage() {
        Log.e("TAG", "开始匹配")
        val pattern = Pattern.compile(pattern)
        val matcher = pattern.matcher(getStr())
        val build = SpannableStringBuilder()

        val map = mutableMapOf<Int, Int>()


        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            Log.e("TAG", "截取的地址:$start -- $end")
            map[start] = end
            Log.e("TAG", "${getStr().substring(start, end)}")


        }

        build.append(getStr())

        val image1 = ImageSpan(
            this,
            R.drawable.bg,
            DynamicDrawableSpan.ALIGN_BASELINE
        )

        val image2 = ImageSpan(
            this,
            R.drawable.bg,
            DynamicDrawableSpan.ALIGN_BASELINE
        )
        val image3 = ImageSpan(
            this,
            R.drawable.bg,
            DynamicDrawableSpan.ALIGN_BASELINE
        )
        val image4 = ImageSpan(
            this,
            R.drawable.bg,
            DynamicDrawableSpan.ALIGN_BASELINE
        )
        build.setSpan(image1, 312, 385, ImageSpan.ALIGN_BASELINE)
        build.setSpan(image2, 394, 494, ImageSpan.ALIGN_BASELINE)
        build.setSpan(image3, 858, 931, ImageSpan.ALIGN_BASELINE)
        build.setSpan(image4, 1106, 1179, ImageSpan.ALIGN_BASELINE)
        tv_img.text = build

    }
}
