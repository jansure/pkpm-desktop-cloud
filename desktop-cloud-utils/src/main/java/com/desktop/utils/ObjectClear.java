package com.desktop.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ObjectClear {
    public static void clearObject(Object obj) {
        Class objectClass = obj.getClass();
        Method[] objectMethods = objectClass.getDeclaredMethods();
        Map objMeMap = new HashMap();
        for (int i = 0; i < objectMethods.length; i++) {
            Method method = objectMethods[i];
            objMeMap.put(method.getName(), method);
        }
        for (int i = 0; i < objectMethods.length; i++) {
            {
                String methodName = objectMethods[i].getName();
                if (methodName != null && methodName.startsWith("get")) {
                    try {
                        Object returnObject = objectMethods[i].invoke(obj, new Object[0]);
                        Method setMethod = (Method) objMeMap.get("set" + methodName.split("get")[1]);
                        if (returnObject != null) {
                            returnObject = null;
                        }
                        setMethod.invoke(obj, returnObject);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

