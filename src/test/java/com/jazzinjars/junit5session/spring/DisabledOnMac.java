package com.jazzinjars.junit5session.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.junit.jupiter.DisabledIf;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DisabledIf(expression = "#{systemProperties['os.name'].toLowerCase().contains('mac')}", reason = "Disabled on Mac OS")
public @interface DisabledOnMac {
}
