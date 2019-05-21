package com.known.annotation;
import com.known.common.enums.Logical;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface RequirePermissions {
	
	 String[] key() default "";
	 Logical logical() default Logical.AND;
	 
}
