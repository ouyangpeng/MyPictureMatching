>这是《我的Android进阶之旅------>Android疯狂连连看游戏的实现》的源代码
>博客地址：http://blog.csdn.net/ouyang_peng/article/details/14115627

>下面是该游戏的预览图片

- 1、游戏最开始的界面，可以点击“开始”按钮开始游戏

![](http://img.blog.csdn.net/20131104050550046?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvb3V5YW5nX3Blbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

- 2、点击“开始”按钮后，会显示出计时文本框，并开始计时。

![](http://img.blog.csdn.net/20131104050705218?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvb3V5YW5nX3Blbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

- 3、游戏过程中

![](http://img.blog.csdn.net/20131104051824484)

- 4、游戏结果：成功或者失败

![](http://img.blog.csdn.net/20131104051033281?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvb3V5YW5nX3Blbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

![](http://img.blog.csdn.net/20131104051055671?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvb3V5YW5nX3Blbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



>开发这个流行的小游戏，难度适中，而且能充分激发学习热情，适合Android初学者来说是一个不错的选择。对于该游戏的开发，需要重点掌握单机游戏的界面分析和数据建模能力：游戏玩家严重看到的是游戏界面，但是在开发者眼中看到的应该是数据模型。除此之外，单机游戏通常需要一个比较美观的界面，需要通过自定义View来实现游戏主界面。
>开发连连看游戏除了需要理解游戏界面的数据模型之外，还需要判断两个方块是否可以相连，为了判断两个方块是否可以相连，需要对两个方块所处的位置进行分类，然后针对不同的情况采用不同的判断算法进行判断，还需要采用条理化的思维方式分析、处理，这也是学习这个游戏开发所需要重点掌握的能力。
     
<p style="text-align:left"><span style="font-size:18px"><span style="white-space:pre"></span>关于具体的实现步骤，请参考下面的链接：</span></p>
<ul>
<li><span style="font-size:18px; color:#cc0000"><em><strong><a target="_blank" target="_blank" href="http://blog.csdn.net/ouyang_peng/article/details/14115627" style="text-decoration:none; font-family:'Microsoft YaHei'; line-height:30px">我的Android进阶之旅------&gt;Android疯狂连连看游戏的实现之游戏效果预览(一)</a><br>
</strong></em></span></li><li><span style="font-size:18px; color:#cc0000"><em><strong><a target="_blank" target="_blank" href="http://blog.csdn.net/ouyang_peng/article/details/14116701" style="font-family:'Microsoft YaHei'; line-height:30px">我的Android进阶之旅------&gt;Android疯狂连连看游戏的实现之开发游戏界面(二)</a><br>
</strong></em></span></li><li><span style="font-size:18px; color:#cc0000"><em><strong><a target="_blank" target="_blank" href="http://blog.csdn.net/ouyang_peng/article/details/14117487" style="font-family:'Microsoft YaHei'; line-height:30px">我的Android进阶之旅------&gt;Android疯狂连连看游戏的实现之状态数据模型(三)</a></strong></em></span></li><li><span style="font-size:18px; color:#cc0000"><em><strong><span style="font-family:'Microsoft YaHei'; line-height:30px"><a target="_blank" target="_blank" href="http://blog.csdn.net/ouyang_peng/article/details/14118001" style="font-family:'Microsoft YaHei'; line-height:30px">我的Android进阶之旅------&gt;Android疯狂连连看游戏的实现之加载界面图片和实现游戏Activity(四)</a></span></strong></em></span></li><li><span style="font-size:18px; color:#cc0000"><em><strong><span style="font-family:'Microsoft YaHei'; line-height:30px"><a target="_blank" target="_blank" href="http://blog.csdn.net/ouyang_peng/article/details/14118213" style="font-family:'Microsoft YaHei'; line-height:30px">我的Android进阶之旅------&gt;Android疯狂连连看游戏的实现之实现游戏逻辑(五)</a></span></strong></em></span></li></ul>
