1、目前常见1.8之前的GC收集器
常见的GC回收算法有标记-清除、复制算法、标记-整理
复制算法适用年轻代，因为正常能留下来的数量比较小，复制的对象比较小
标记-清除、标记-整理 适用老年代，因为正常能留下来的数量比较小，清除的对象比较小，
一、Serial  SerialOld  分别采集年轻代和老年代，年轻用的是复制算法，老年代用的是标记-整理，由于都是串行的，都会发生STW。适用在单核CPU时代。只会发生两种类型的GC，
普通的 GC、Full GC,GC 作用于年轻代，只会回收年轻代里的对象，Full GC会回收整个堆，以及非堆的内容。

二、Parallel Scavenge Parallel Old，这是常用JDK1.8的默认收集器，分别采集年轻代和老年代，年轻用的是复制算法，老年代用的是标记-整理，由于，都会发生STW。适用在单核CPU时代。只会发生两种类型的GC，普通的 GC、Full GC,GC 作用于年轻代，只会回收年轻代里的对象，Full GC会回收整个堆，以及非堆的内容。 其中Full GC (Ergonomics) 是并行收集器特有的。

三、ParNew+CMS，年轻代，有三种类型的GC，采用ParNew 并行收集年轻代内容，用并发标记清楚来并发收集年老代，还有退化的Full GC 会回收整个堆，以及非堆的内容。


四、G1将堆内存分成一个个region，默认启动年轻代的大小会比老年代的大，其余的收集器都是相反的，总共有4种类型的GC ，GC pause (G1 Evacuation Pause) (young) 主要发生于刚启动的应用里，收集年轻代的内容，GC pause (G1 Humongous Allocation) (young) (initial-mark) 并发收集年轻代的内容，用于收集年轻代，标记存活对象以备下面mixGC使用，GC pause (G1 Evacuation Pause) (mixed) 用来收集年轻代和老年代，最后还有退化的Full GC 会回收整个堆，以及非堆的内容。

同样不变堆的总内存，年轻代比例提升，FullGC就会变得频繁但是时间变小，YoungGC时间提高，频率减少。
