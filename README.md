# ssh-spring

## 前言
```base
    程序的耦合:
        调用者和被调用者的依赖关系
    我们在开发中遵循的原则:
        编译时不依赖,运行时才依赖
    解决依赖关系
        使用反射创建类对象
    使用反射创建类对象引发新问题
        使用配置文件,通过读取配置文件来反射创建类对象
```

## 第一部分 Spring的IOC(xml)
```base
bean.xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
                                http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!--配置资源:把对象的创建交给spring来管理-->
        <bean id="customerService" class="com.zler.service.impl.CustomerServiceImpl"></bean>
        <bean id="customerDao" class="com.zler.dao.impl.CustomerDaoImpl"></bean>
    </beans>
    
ClassPathXmlApplicationContext: 它是加载类路径下的配置文件
FileSystemXmlApplicationContext: 它可以加载磁盘任意位置的配置文件

Bean创建的两种规则:
    BeanFactory:
        提供的是一种延迟加载思想来创建bean对象。bean对象什么时候用什么时候加载、
    ApplicationContext  
        提供的是一种立即加载思想来创建bean对象。只要一解析完配置文件,就立马创建bean对象
Bean的三种创建方式
    第一种方式: 调用默认无参构造函数创建     此种方式用的对多
        默认情况下如果类中没有默认构造方法,则创建失败,会报异常
    第二种方式: 使用静态工厂中的方法创建对象
        需要bean标签的factory-method属性,指定静态工厂创建对象方法
        <bean id="staticCustomerService" 
            class="com.zler.factory.StaticFactory" 
            factory-method="getCustomerService">
        </bean>
    第三种方法: 使用实例工厂中的方法创建
        <bean id="instanceFactory" class="com.zler.factory.InstranceFactory"></bean>
        <bean id="instanceCustomerService" 
            factory="instanceFactory" 
            factory-method="getCustomerService">
        </bean>
Bean的作用范围
    它是通过配置的方式来调整作用范围。
    配置属性: bean标签的scope属性
    属性取值:
        singleton: 单利的 (默认值)
        prototype: 多列的 (当我们让spring接管struts2的action创建时,action必须配置此值)
        request: 作用范围是一次请求,和当前请求的转发
        session: 作用范围是一次会话
        globalsession: 作用范围是一次全局会话
Bean的生命周期
    涉及bean标签的两个属性:
        init-method
        destroy-method
    单利:
        出生: 容器创建,对象就出生了
        活着: 只要容器在,对象就一直存在
        死亡: 容器销毁,对象死亡
    多利:
        出生: 每次使用时,创建对象
        活着: 只要对象在使用中,就一直活着
        死亡: 当对象长时间不使用,并且也没有别的对象引用时,由java的垃圾回收器回收

spring的依赖注入
    注入的方式有3种:
        第一种: 使用构造函数注入
        第二种: 使用set方法注入
        第三种: 使用注解注入
    注入的数据类型有3类
        第一类: 基本类型和String类型
        第二类: 其他bean类型(必须是在spring的配置文件中出现过的bean)
        第三类: 复杂类型集合(集合类型)
                结构相同,标签可以互换
    
    构造函数注入:
        涉及的标签: contructor-arg
            标签的属性:
                type: 指定参数的类型
                index: 指定参数的索引位置,从0开始
                name: 指定参数的名称      一般用它
                ===========上面三个属性是指定给哪个参数赋值的,下面两个属性是指定赋什么值的==========
                value: 指定基本数据类型或String类型的数据
                ref: 指定其他bean类型数据
            标签出现的位置:
                写在bean变迁内部
    set方法注入:           
        涉及的标签: property
            标签的属性: 
                name: 指定参数的set方法名称
                value: 指定基本数据类型或String类型的数据
                ref: 指定其他bean类型数据
            标签出现的位置:
                写在bean变迁内部
    
```

## 第二部分 Spring的IOC(注解)
```base
bean.xml
    告知spring在创建容器时要扫描的包。
    当配置了此标签之后,spring创建容器就会去指定包及其子包下找对应的注解
    标签是一个context的名称空间里,所以必须先导入context名称空间

    <?xml version="1.0" encoding="UTF-8" ?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
                                http://www.springframework.org/schema/beans/spring-beans.xsd
                                http://www.springframework.org/schema/context
                                http://www.springframework.org/schema/context/spring-context.xsd">
        <context:component-scan base-package="com.zler"></context:component-scan>
    </beans>
1.用于创建bean对象
    @Component
        作用: 就相当于配置了一个bean标签
        它能出现的位置: 类上面
        属性: value 含义是指定bean的id 当不写时,它有默认值,默认值是:当前类的短名首字母改小写
    由此注解衍生的三个注解
        @Controller     一般用于表现层的注解
        @Service        一般用于业务层
        @Repository     一般用于持久层
        他们和@Component的作用及属性都是一模一样
2.用于注入数据的
    @Autowired
        作用: 自动按照类型注入 只要有唯一的类型匹配就能注入成功
              如果注入的bean在容器中类型不唯一时,它会把变量名称作为bean的id,在容器中查找,找到后也能注入成功
              如果没有找到一致的bean的id,则报错
              当我们使用注解注入时,set方法就不是必须的
    @Qualifier
        作用:在自动按照类型注入的基础上,再按照bean的id注入
            它再在给类成员注入数据时,不能独立使用
            但是再给方法的形参注入数据时,可以独立使用
        属性:
            value: 用于指定bean的id
    @Resource
        作用: 直接按照bean的id注入
        属性:
            name: 用于指定bean的id
    以上三个注解都是用于注入其他bean类型的。用于注入基本类型和String类型需要使用value
    @Value:
        作用: 用于注入基本类型和String类型数据
              它可以借助spring的el表达式读取properties文件中的配置
        属性:
            value:用于指定要注入的数据
3.用于改变作用范围的
    @Scope
        作用: 用于改变bean的作用范围
        属性:
            value: 用于指定范围的取值
            取值和xml中scope属性的取值是一样的 singleton prototype request session globalsession
4.和生命周期相关的

5.spring的新注解
    @ComponentScan
        告诉spring要加载的包
    @Bean:
        作用: 它是把方法的返回值存入spring容器
        属性:
            name: 指定bean的id 当不指定时它有默认值,默认值是方法的名称
    
    @Configuration:
            把当前类看成是spring的配置类
    @Import 导入其他配置类
    @PropertySource 加载配置文件
        版本的区别 PropertySourcesPlaceholderConfigurer
            资源占位符解析器
6.spring整合junit
    拷贝spring提供的整合jar包
        spring-test-4.2.4.RELEASE.jar
    使用junit提供的一个注释,把原有的main函数替换掉,换成spring提供的
        @RunWith
        要换的类: SpringJunit4ClassRunner
    使用spring提供的注释告知spring,配置文件或者注解类所在位置
        @ContextConfiguration
```

## 第三部分 Spring的AOP
```base
动态代理代码

编写核心代码
抽取公共方法
配置哪些方法需要增强 告诉spring 在哪个位置增强

Joinpoint连接点(看接口): 打开业务层接口里面的方法
Pointcut切入点: 被增强的方法是切入点 
                被增强和没被增强的方法是连接点
Advice通知: 要增加的代码(通知类) 
    前置通知 
    后置通知 
    异常通知 
    最终通知  
        try{  前置通知  切入点 后置通知 }catch{异常通知}finally{最终通知}
    环绕通知: 是全部代码 环绕通知中都有明确的切入点方法调用


1.xml配置AOP
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/aop
                            http://www.springframework.org/schema/aop/spring-aop.xsd>
    把通知类交给spring来管理
        <bean id="logger" class="com.zler.tools.Logger">
    导入aop名称空间,并且使用aop:config开始aop的配置
    使用aop:aspect配置切面 
        id属性用于给切面提供一个唯一标识。
        ref属性:用于应用通知Bead的id
        
        配置通知类型(aop:before)指定增强方法何时执行 
            method属性: 用于指定增强方法的名称 
            pointcut属性:用于指定切入点表达式 execution(表达式)
                表达式写法:
                    访问修饰符 返回值 包名.包名...类名.方法名(参数列表)
                全匹配方式
                    execution(public void com.zler.service.impl.CustomerServiceImpl.saveCustomer())
                访问修饰符可以省略
                    void com.zler.service.impl.CustomerServiceImpl.saveCustomer()
                返回值可以使用通配符,表示任意返回值。
                    * com.zler.service.impl.CustomerServiceImpl.saveCustomer()
                包名可以使用通配符,表示任意包。但是,有几个包需要写几个*
                    *.*.*.*.CustomerServiceImpl.saveCustomer()
                包名可以使用..表示当前包及其子包
                    com..CustomerServiceImpl.saveCustomer()
                类名和方法名都可以使用通配符
                    execution(* com..*.*())
                参数列表可以使用具体类型,来表示参数类型
                    基本类型直接写类名名称: int
                    引用类型必须是包名.类名.java.lang.Integer
                参数列表可以使用通配符,表示任意参数类型,但是必须有参数
                    * com..*.*(*))
                参数列表可以使用..表示有无参数均可,有参数可以是任意参数
                    * com..*.*(..)
                全通配方式
                    * *..*.*(..)
                实际开发中,我们一般情况下,我们都是对业务层方法进行增强
                    所以写法: * com.zler.service.impl.*.*(..)

        通知类型
            aop:before 配置前置通知 永远在切入点方法之前执行
            aop:after-returning 配置后置通知 切入点方法正常执行后执行
            aop:after-throwing 异常通知 切入点方法执行异常之后执行.它和后置通知永远只能执行一个
            aop:around 配置环绕通知
                当我们配置了环绕通知之后,切入点方法没有执行,而环绕通知里的代码执行了
                有动态代理可知,环绕通知指的是invoke方法,并且里面明确的切入点方法调用。而我们现在的环绕通知没有明确的切入点方法调用
                spring为我们提供了一个接口:ProceedingJoinPoint 该接口可以作为环绕通知方法的参数来使用
                程序运行时,spring框架会为我们提供该接口的实现类,供我们使用
                该接口中有一个方法,proceed() 它的作用就等用于
            
            环绕通知:
                spring框架为我们提供了一种可以在代码中手动控制通知类型的方法方式

<aop:config>
        <aop:aspect id="logAdvice" ref="logger">
            <aop:before method="printLog" pointcut="execution(* com..*.*(..))"/>
        </aop:aspect>
</aop:config>
    
2.注解配置AOP
<aop:aspectj-autoproxy />
或者 如果连bean.xml都不想要的话 要写配置类中SpringConfiguration
    @Configuration 
    @ComponentScan("com.zler") 
    @EnableAspectJAutoProxy

@Aspect
@Pointcut("execution(* com.zler.service.impl.*.*(..))")
@Before("pt1()")
@AfterReturning("pt1()")
@AfterThrowing("pt1()")
@After("pt1()")
@Around("pt1()")

```

## 第四部分 Spring的JdbcTemplate和声明事务
```base
1.spring基于xml配置
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                            http://www.springframework.org/schema/beans/spring-beans.xsd
                            http://www.springframework.org/schema/aop
                            http://www.springframework.org/schema/aop/spring-aop.xsd
                            http://www.springframework.org/schema/context
                            http://www.springframework.org/schema/context/spring-context.xsd">
       <!-- 配置service -->
       <bean id = "userService" class="com.zler.service.impl.UserServiceImpl">
            <property name="userDao" ref="userDao"></property>
       </bean>
       
       <!-- 配置dao -->
       <bean id="userDao" class="com.zler.dao.impl.UserDaoImpl">
            <property name="dataSource" ref="dataSource"></property>
       </bean>
       
       <!-- 配置spring内置数据源 -->
       <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
            <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
            <property name="url" value="jdbc:mysql://localhost:3306/spring"></property>
            <property name="username" value="root"></property>
            <property name="password" value="root"></property>
       </bean>
       
       <!-- spring基于xml的声明式事务控制 -->
       
       <!-- 第一步:配置事务管理器 -->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <!-- 注入数据源 -->
            <property name="dataSource" ref="dataSource"></property>
       </bean>
       
       <!-- 第二步:配置事务通知 -->
       <tx:advice id="txAdvice" transaction-manager="transactionManager">
            <!-- 第四步配置事务的属性 
                isolation: 配置事务的隔离级别。默认值:Defaul,使用数据库的默认隔离级别。mysql是REPEATABLE_READ
                propagation: 配置事务传播行为。默认值:REQUIRED。一般的选择。(增删改方法)。当是查询方法时,选择SUPPORTS
                timeout: 指定事务的超时事件。默认值是:-1,永不超时。当指定其他值时,以秒为单位
                read-only: 配置是否只读事务。默认值是:false,读写型事务。当设置为true时,表示只读,只能用于查询方法
                rollback-for: 用于指定一个异常,当执行产生异常时,事务回滚。产生其他异常时,事务不回滚。没有默认值,任何异常都回滚 
                no-rollback-for: 用于指定一个异常,当执行产生异常时,事务不回滚。产生其他异常时,事务回滚。没有默认值,任何异常都回滚
            -->
            <tx:attributes>
                <tx:method name="*" propagation="REQUIRED" read-only="fale"/>
                <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            </tx:attributes>
       </tx:advice>
       
       <!-- 第三步:配置aop 
            要配的是:切入点表达式
                    通知和切入表达式的关联
       -->
       <aop:config>
            <!-- 配置切入点表达式 -->
            <aop:pointcut expression="execution(* com.zler.service.impl.*.*(..))" id="pt1" />
            <!-- 配置事务通知和切入点表达式的关联 -->
            <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1" />
       </aop:config>
</beans>



```

## 第五部分 SSH整合
```base
第一个版本: 纯XML的整合 保留spring struts2和hibernate各自的主配置文件
第二个版本: 纯XML的整合 保留spring struts2的主配置文件,hibernate的主配置文件内容配置到spring的配置文件中
第三个版本: XML和注解的组合式整合 仅保留spring的主配置文件(根据实际开发中的需要,struts2的配置文件也可以保留)

整合步骤:严格安装此步骤去整合
第一步: 保证spring的ioc容器能够在web工程中独立运行
第二步: 保证hibernate框架能够在web工程中独立运行
第三步: 整合spring和hibernate
    思考:怎么算是整合了?
        spring接管hibernate的sessionFactory对象创建(把sessionFactory存入spring容器中)
        使用了spring声明式事务控制
第四步: 保证struts2框架能够在web工程中独立运行
第五步: 整合spring和struts2
    思考:怎么才算struts2和spring整到了一起?
        action的创建交给spring来管理
        保证web工程中的容器只有一个
第六步: 优化已有的整合配置
    配置文件的位置存放可以调整
    配置文件的内容可以分不同文件来编写
```