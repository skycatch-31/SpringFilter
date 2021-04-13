#### Spring Filter
+ What is Filter?
  + Servlet의 ServletContext 기능으로 사용자에 의해 Servlet이 호출 되기 전/후로 사용자의 요청.응답 헤더 정보 등을 검사 및 설정 할 수 있다.

+ Spring MVC request life cycle
  ![Spring MVC](https://github.com/suhojang/SpringbootFilter/blob/master/mvc.png)

+ Custom Annotation Rule
    + Annotation Type은 @interface로 정의해야합니다.
      모든 Annotation은 자동적으로 java.lang.Annotation interface를 상속하기 때문에 다른 Class나 interface를 상속 받으면 안됨.
    + Parameter 멤버들의 접근자는 public 이거나 default여야만 함
    + Parameter 멤버들은 byte,short,char,int,float,double,boolean의 기본타입과
      String, Enum, Class Annotation만 사용할 수 있음.
    + Class Method와 Feild에 관한 Annotation 정보를 얻고 싶으면, reflection만 이용해서 얻을 수 있습니다.
      다른 방법으로는 Annotation 객체를 얻을 수 없습니다.

+ Dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
  ```
+ Gradle
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.projectlombok:lombok:1.18.20'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
```

+ FruitColor - 과일에 색을 지정해주는 Annotation
```java
package com.jsh.customAnnotation.fruit;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    enum Color{
        BLUE, RED, GREEN
    }

    Color fruitColor() default Color.GREEN;
}
```

+ FruitName - 과일의 이름을 지정해주는 Annotation
```java
package com.jsh.customAnnotation.fruit;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName {
    String value() default "";
}
```

+ FruitProvider - 과일을 파는 곳을 지정해주는 Annotation
```java
package com.jsh.customAnnotation.fruit;

import java.lang.annotation.*;

@Target({ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    enum Color{
        BLUE, RED, GREEN
    }

    Color fruitColor() default Color.GREEN;
}
```

+ Apple
```java
package com.jsh.customAnnotation.component;

import com.jsh.customAnnotation.fruit.FruitColor;
import com.jsh.customAnnotation.fruit.FruitName;
import com.jsh.customAnnotation.fruit.FruitProvider;
import org.springframework.stereotype.Component;

@Component
public class Apple {
    @FruitName("Apple")
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String appleColor;

    @FruitProvider(id = 1, name = "JSH", address = "Seoul")
    private String appleProvider;
}
```

+ FruitInfoUtil
```java
package com.jsh.customAnnotation.component;

import com.jsh.customAnnotation.fruit.FruitColor;
import com.jsh.customAnnotation.fruit.FruitName;
import com.jsh.customAnnotation.fruit.FruitProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class FruitInfoUtil {
    private static Logger logger    = LoggerFactory.getLogger(FruitInfoUtil.class);
    private static String _LINE     = System.getProperty("line.separator");

    public static String getFruitInfo(Class<?> clazz) {
        StringBuffer sb = new StringBuffer();

        String strFruitName     = " 과일 이름 :";
        String strFruitColor    = " 과일 색 :";
        String strFruitProvider = "과일 파는 곳";

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitName.class)) {
                FruitName fruitName = field.getAnnotation(FruitName.class);
                strFruitName += fruitName.value();

                logger.info(strFruitName);
                sb.append(strFruitName).append(_LINE);
            } else if (field.isAnnotationPresent(FruitColor.class)) {
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                strFruitColor += fruitColor.fruitColor().toString();

                logger.info(strFruitColor);
                sb.append(strFruitColor).append(_LINE);
            } else if (field.isAnnotationPresent(FruitProvider.class)) {
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                strFruitProvider = " 과일 파는 곳의 ID: " + fruitProvider.id() + ", 지점 이름 : " + fruitProvider.name() + ", 지점 주소 : " + fruitProvider.address();

                logger.info(strFruitProvider);
                sb.append(strFruitProvider);
            }
        }
        return sb.toString();
    }
}
```

+ FruitController
```java
package com.jsh.customAnnotation.controller;

import com.jsh.customAnnotation.component.Apple;
import com.jsh.customAnnotation.component.FruitInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FruitController {
    @Autowired
    private FruitInfoUtil fruitInfoUtil;

    @GetMapping(value = "/getFruit")
    public String getFruit() {
        return fruitInfoUtil.getFruitInfo(Apple.class);
    }
}
```


+ getFruit Call
```
2021-04-12 17:46:39.051  INFO 22548 --- [nio-8080-exec-3] c.j.c.component.FruitInfoUtil            :  과일 이름 :Apple
2021-04-12 17:46:39.051  INFO 22548 --- [nio-8080-exec-3] c.j.c.component.FruitInfoUtil            :  과일 색 :RED
2021-04-12 17:46:39.051  INFO 22548 --- [nio-8080-exec-3] c.j.c.component.FruitInfoUtil            :  과일 파는 곳의 ID: 1, 지점 이름 : JSH, 지점 주소 : Seoul
```

+ Spring에서 Application 실행 시 @Service, @Component가 붙은 Class들을 Scan해서 IoC Container에 등록해주는 과정
    + @Service Annotation
```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}
```
+ Service Annotation은, Target이 TYPE으로 지정되어 있음. Class나 Interface를 Target으로 삼는다는 의미
  또한 같은 패키지인 org.springframework.stereotype 의 Component Annotation이을 쓰고 있는걸 볼 수 있음
    + @Component Annotation
```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {
    String value() default "";
}
```

+ Spring 에서는 @Component, @Service, @Controller등 Annotation이 사용 된 Class는 Bean으로 등록
    + doScan() 메소드가 ClassPath에 있는 Package의 모든 Class를 읽어 Annotation이 붙은 Class를 처리 해 주는 Method.
```java
/**
 * Perform a scan within the specified base packages,
 * returning the registered bean definitions.
 * <p>This method does <i>not</i> register an annotation config processor
 * but rather leaves this up to the caller.
 * @param basePackages the packages to check for annotated classes
 * @return set of beans registered if any for tooling registration purposes (never {@code null})
 */
protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
    Assert.notEmpty(basePackages, "At least one base package must be specified");
    Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
    for (String basePackage : basePackages) {
        Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
        for (BeanDefinition candidate : candidates) {
            ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
            candidate.setScope(scopeMetadata.getScopeName());
            String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
            if (candidate instanceof AbstractBeanDefinition) {
                postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
            }
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
            if (checkCandidate(beanName, candidate)) {
                BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
                definitionHolder =
                        AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
                beanDefinitions.add(definitionHolder);
                registerBeanDefinition(definitionHolder, this.registry);
            }
        }
    }
    return beanDefinitions;
}
```
