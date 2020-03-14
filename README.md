# ui-admin


## shiro

1. 不要用`shiro-spring-boot-web-starter`
2. 不要用`shiro-spring-boot-web-starter`
3. 不要用`shiro-spring-boot-web-starter`

最要是事情说3边,因为第一次看这个starter是出自apache官方,就过度高兴了.

不推荐使用的理由有如下:

1. 和aop冲突,springboot2 aop默认使用的是cglib基于类的动态代理,使用这个starter会多次配置,最后转化成使用jdk基于接口的动态代理,导致
	
	1. shiro基于注解不生效
	2. aop切面不生效(如果没有接口的话)

2. 如果使用jwt,其实自己要定义配置的东西还有很多,没有起到多大好处


## Spring Boot缓存 Caffeine使用


为什么需要本地缓存？在系统中，有些数据，访问十分频繁（例如数据字典数据、国家标准行政区域数据），往往把这些数据放入分布式缓存中，但为了减少网络传输，加快响应速度，缓存分布式缓存读压力，会把这些数据缓存到本地JVM中，大多是先取本地缓存中，再取分布式缓存中的数据

而Caffeine是一个高性能Java 缓存库，使用Java8对Guava缓存重写版本，在Spring Boot 2.0中将取代Guava


###　主要的几个缓存注解

- @Cacheable：对方法配置，能够根据方法的请求参数对其进行缓存
- @CacheEvict：清除缓存
- @CachePut：主要用来更新缓存
- @CacheConfig：统一配置类的缓存注解的属性


### @Cacheable/@CachePut/@CacheEvict主要的参数

* value：缓存的名称，在配置文件中定义
* key：缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合
* condition：缓存的条件，只有为 true才进行缓存/清除缓存
* unless：与condition相反，只有为 false才进行缓存/清除缓存
* allEntries：是否清空所有缓存内容，缺省为 false
* beforeInvocation：是否在方法执行前就清空，缺省为 false；缺省情况下，如果方法 执行抛出异常，则不会清空缓存

### Cacheable 执行流程

1）方法运行之前，按照cacheNames指定的名字先去查询Cache 缓存组件
2）第一次获取缓存如果没有Cache组件会自动创建
3）Cache中查找缓存的内容，使用一个key，默认就是方法的参数
4）key是按照某种策略生成的；默认是使用keyGenerator生成的，这里使用自定义配置
5）没有查到缓存就调用目标方法；
6）将目标方法返回的结果，放进缓存中

Cacheable 注解属性

cacheNames/value：指定方法返回结果使用的缓存组件的名字，可以指定多个缓存
key：缓存数据使用的key
key/keyGenerator：key的生成器，可以自定义
cacheManager：指定缓存管理器
cacheResolver：指定缓存解析器
condition：指定符合条件的数据才缓存
unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存
sync：是否使用异步模式


### @CacheEvict 清除缓存

CacheEvict：缓存清除
key：指定要清除的数据
allEntries = true：指定清除这个缓存中所有的数据
beforeInvocation = false：方法之前执行清除缓存,出现异常不执行
beforeInvocation = true：代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除


### @CachePut

保证方法被调用，又希望结果被缓存。
与@Cacheable区别在于是否每次都调用方法，常用于更新，写入
CachePut：执行方法且缓存方法执行的结果
修改了数据库的某个数据，同时更新缓存；
执行流程
 1)先调用目标方法
 2)然后将目标方法的结果缓存起来









