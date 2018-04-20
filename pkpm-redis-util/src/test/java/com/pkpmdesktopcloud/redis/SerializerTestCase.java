package com.pkpmdesktopcloud.redis;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class SerializerTestCase {

  int max = 1000000;

  Serializer kryoSerializer;
  Serializer jdkSerializer;

  @Before
  public void setup() {
    kryoSerializer = KryoSerializer.INSTANCE;
    jdkSerializer = JDKSerializer.INSTANCE;
  }

  @Test
  public void testKryoUnserializeNull() {
    Object obj = kryoSerializer.deserialize(null);
    assertNull(obj);
  }

  @Test
  public void testJDKUnserializeNull() {
    Object obj = jdkSerializer.deserialize(null);
    assertNull(obj);
  }

  public void testKryoSerialize() {
    SimpleBeanStudentInfo rawSimpleBean = new SimpleBeanStudentInfo();

    for (int i = 0; i != max; ++i) {
      kryoSerializer.serialize(rawSimpleBean);
    }

    byte[] serialBytes = kryoSerializer.serialize(rawSimpleBean);
    SimpleBeanStudentInfo unserializeSimpleBean = (SimpleBeanStudentInfo) kryoSerializer.deserialize(serialBytes);

    for (int i = 0; i != max; ++i) {
      kryoSerializer.deserialize(serialBytes);
    }

    Assert.assertEquals(rawSimpleBean, unserializeSimpleBean);

  }

  @Test
  public void testKryoFallbackSerialize() throws IOException {

    SimpleBeanStudentInfo rawSimpleBean = new SimpleBeanStudentInfo();
    byte[] serialBytes = jdkSerializer.serialize(rawSimpleBean);

    SimpleBeanStudentInfo unserializeSimpleBean = (SimpleBeanStudentInfo) kryoSerializer.deserialize(serialBytes);
    Assert.assertEquals(rawSimpleBean, unserializeSimpleBean);

  }

  @Test
  public void testKryoUnserializeWithoutRegistry() throws IOException {
    SimpleBeanStudentInfo rawSimpleBean = new SimpleBeanStudentInfo();

    byte[] serialBytes = kryoSerializer.serialize(rawSimpleBean);

    Kryo kryoWithoutRegisty = new Kryo();
    Input input = new Input(serialBytes);
    SimpleBeanStudentInfo unserializeSimpleBean = (SimpleBeanStudentInfo) kryoWithoutRegisty.readClassAndObject(input);
    Assert.assertEquals(rawSimpleBean, unserializeSimpleBean);

  }

  /**
   * SimpleBeanSerializedFile contains serialized bytes of an default object of simpleBeanCourceInfo.
   * KryoSerializer can unserialize from bytes of file derectly
   * @throws IOException
   */
  @Test
  public void testKryoUnserializeWithoutRegistryWithFile() throws IOException {
    SimpleBeanCourseInfo rawSimpleBean = new SimpleBeanCourseInfo();

    InputStream inputStream = SerializerTestCase.class.getClass()
        .getResourceAsStream("/simpleBeanCourseInfoSerializedFile");
    if (inputStream == null) {
      return;
    }
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    int nRead;
    byte[] data = new byte[1000];

    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }
    buffer.flush();

    SimpleBeanCourseInfo unserializeSimpleBean = (SimpleBeanCourseInfo) kryoSerializer.deserialize(data);
    Assert.assertEquals(rawSimpleBean, unserializeSimpleBean);

  }

  @Test
  public void testJDKSerialize() {
    SimpleBeanStudentInfo rawSimpleBean = new SimpleBeanStudentInfo();

    for (int i = 0; i != max; ++i) {
      jdkSerializer.serialize(rawSimpleBean);
    }

    byte[] serialBytes = jdkSerializer.serialize(rawSimpleBean);
    SimpleBeanStudentInfo unserializeSimpleBean = (SimpleBeanStudentInfo) jdkSerializer.deserialize(serialBytes);

    for (int i = 0; i != max; ++i) {
      jdkSerializer.deserialize(serialBytes);
    }

    Assert.assertEquals(rawSimpleBean, unserializeSimpleBean);

  }

  @Test
  public void testSerializeCofig() {
    RedisConfig redisConfig = RedisConfigurationBuilder.getInstance().parseConfiguration();
    assertEquals(JDKSerializer.class, redisConfig.getSerializer().getClass());
  }
}
