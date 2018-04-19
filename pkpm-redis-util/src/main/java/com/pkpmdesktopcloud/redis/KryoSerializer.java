package com.pkpmdesktopcloud.redis;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.Arrays;
import java.util.HashSet;

public enum KryoSerializer implements Serializer{
    INSTANCE;
    private Kryo kryo;
    private Output output;
    private Input input;
    private HashSet<Class<?>> unNormalClassSet;
    private HashSet<Integer> unNortmalBytesHashCodeSet;
    private Serializer fallbackSerielizer;
    private KryoSerializer() {
        kryo = new Kryo();
        output = new Output(200,-1);
        input = new Input();
        unNormalClassSet = new HashSet<>();
        unNortmalBytesHashCodeSet = new HashSet<>();
        fallbackSerielizer = JDKSerializer.INSTANCE;
    }

    @Override
    public byte[] serialize(Object object) {
        output.clear();
        if(!unNormalClassSet.contains(object.getClass())) {
            try {
                kryo.writeClassAndObject(output,object);
                return output.toBytes();
            }
            catch (Exception e) {
                unNormalClassSet.add(object.getClass());
                return fallbackSerielizer.serialize(object);
            }
        }
        else
            return fallbackSerielizer.serialize(object);
    }
    
    @Override
    public  Object deserialize(byte[] bytes) {
        if(bytes == null) return null;
        Integer hashCode = Arrays.hashCode(bytes);
        if(!unNortmalBytesHashCodeSet.contains(hashCode)) {
            try{
                input.setBuffer(bytes);
                return kryo.readClassAndObject(input);
            }
            catch (Exception e) {
                unNortmalBytesHashCodeSet.add(hashCode);
                return fallbackSerielizer.deserialize(bytes);
            }
        }
        else
            return fallbackSerielizer.deserialize(bytes);
    }
}
