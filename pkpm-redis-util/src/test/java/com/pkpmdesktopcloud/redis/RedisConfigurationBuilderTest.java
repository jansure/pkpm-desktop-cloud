
package com.pkpmdesktopcloud.redis;
import com.pkpmdesktopcloud.redis.sslconfig.TestHostnameVerifier;
import com.pkpmdesktopcloud.redis.sslconfig.TestSSLParameters;
import com.pkpmdesktopcloud.redis.sslconfig.TestSSLSocketFactory;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class RedisConfigurationBuilderTest {

  @Test
  public void testDefaults() throws Exception {
    System.setProperty(RedisConfigurationBuilder.SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME, "no-such-file.properties");
    RedisConfig redisConfig = RedisConfigurationBuilder.getInstance()
        .parseConfiguration(this.getClass().getClassLoader());
    assertEquals(JDKSerializer.class, redisConfig.getSerializer().getClass());
    assertFalse(redisConfig.isSsl());
    assertNull(redisConfig.getSslSocketFactory());
    assertNull(redisConfig.getSslParameters());
    assertNull(redisConfig.getHostnameVerifier());

  }

  @Test
  public void test1() throws Exception {
    System.setProperty(RedisConfigurationBuilder.SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME, "test1.properties");
    RedisConfig redisConfig = RedisConfigurationBuilder.getInstance()
        .parseConfiguration(this.getClass().getClassLoader());
    assertEquals(KryoSerializer.class, redisConfig.getSerializer().getClass());
    assertTrue(redisConfig.isSsl());
    assertEquals(TestSSLSocketFactory.class, redisConfig.getSslSocketFactory().getClass());
    assertEquals(TestSSLParameters.class, redisConfig.getSslParameters().getClass());
    assertEquals(TestHostnameVerifier.class, redisConfig.getHostnameVerifier().getClass());
  }

  @After
  public void after() {
    System.setProperty(RedisConfigurationBuilder.SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME,
        RedisConfigurationBuilder.REDIS_RESOURCE);
  }
}
