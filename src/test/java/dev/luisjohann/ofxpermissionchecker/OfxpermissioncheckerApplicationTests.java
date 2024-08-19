package dev.luisjohann.ofxpermissionchecker;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
@ActiveProfiles("test")
class OfxpermissioncheckerApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(Boolean.TRUE);
	}

}
