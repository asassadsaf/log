package com.fkp.springboot3log4j2.methodhandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author fengkunpeng
 * @version 1.0
 * @description MethodHandler，MethodHandles，MethodHandles.Lookup
 * @date 2024/4/28 20:11
 */
public class Test {
    public static void main(String[] args) throws Throwable {
        //MethodType定义了方法的返回值类型和入参类型，第一个参数代表返回值类型，之后的参数代表方法的入参类型
        MethodType methodType = MethodType.methodType(String.class, char.class, char.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        //lookup对象查找类的方法，refc代表那个类，name代表方法名，type代表MethodType，也就是返回值类型和参数类型
        MethodHandle methodHandle = lookup.findVirtual(String.class, "replace", methodType);
        //MethodHandle#invoke代表实际调用，若方法是静态方法则invoke方法参数为指代方法的参数，
        //若是实例方法，则invoke方法第一个参数为实例对象，后面参数为指代方法的参数
        //与该方式相同 "abcc".replace('c', 'd')
        String result = (String) methodHandle.invokeExact("abcc", 'c', 'd');
        System.out.println(result);
        System.out.println("abcc".replace('c', 'd').equals(result));
    }
}
