package  com.cabr.pkpm.mapper.mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 排除多余变量
 *
 * @author zhangshuai
 * @since 12/01/2018
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invisible {
}
