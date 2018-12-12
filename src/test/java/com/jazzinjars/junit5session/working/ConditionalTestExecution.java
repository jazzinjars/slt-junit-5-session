package com.jazzinjars.junit5session.working;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.condition.JRE.JAVA_10;
import static org.junit.jupiter.api.condition.JRE.JAVA_8;
import static org.junit.jupiter.api.condition.JRE.JAVA_9;

class ConditionalTestExecution {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Test
    @EnabledOnOs(OS.MAC)
    @interface TestOnMac {
    }

    @Test
    @EnabledOnJre(JAVA_8)
    void onlyOnJava8() {
	// ...
    }

    @Test
    @EnabledOnJre({ JAVA_9, JAVA_10 })
    void onJava9Or10() {
	// ...
    }

    @Test
    @DisabledOnJre(JAVA_9)
    void notOnJava9() {
	// ...
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {
	// ...
    }

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    void notOnCiServer() {
	// ...
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    void onlyOnStagingServer() {
	// ...
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
    void notOnDeveloperWorkstation() {
	// ...
    }

    @Test // Multi-line script, custom engine name and custom reason.
    @EnabledIf(value = {
			"load('nashorn:mozilla_compat.js')",
			"importPackage(java.time)",
			"",
			"var today = LocalDate.now()",
			"var tomorrow = today.plusDays(1)",
			"tomorrow.isAfter(today)"
		},
		engine = "nashorn",
		reason = "Self-fulfilling: {result}")
    void theDayAfterTomorrow() {
	LocalDate today = LocalDate.now();
	LocalDate tomorrow = today.plusDays(1);
	assertTrue(tomorrow.isAfter(today));
    }
}
