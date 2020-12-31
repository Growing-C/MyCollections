package com.example.testsourcelib;

import com.example.testsourcelib.compare.JniInterface;
import com.example.testsourcelib.compare.JniInterface1;

import java.lang.reflect.Method;

/**
 * author: chengy
 * created on: 2020-12-31 11:23
 * description:
 */
public class CompareUtils {
    public static void main(String[] args) {
//        compareMethodsOf(MethodClass1.class, MethodClass2.class);
        compareMethodsOf(JniInterface1.class, JniInterface.class);
        compareMethodsOf(JniInterface.class, JniInterface1.class);
    }

    /**
     * 比较两个类的方法，第一个是根类，看看第二个类是否拥有第一个类的所有方法
     *
     * @param source
     * @param target
     */
    public static void compareMethodsOf(Class<?> source, Class<?> target) {
        Method[] sourceMethods = source.getDeclaredMethods();//这个只会获取当前类中的方法，不会获取继承的类中的，比如Object中的。 getMethods会获取Object中的方法
        Method[] targetMethods = target.getDeclaredMethods();
        boolean targetHasThisMethod;
        System.out.println("source 方法数：" + sourceMethods.length);
        System.out.println("target 方法数：" + targetMethods.length);
        for (Method sourceMethod : sourceMethods) {
            String methodName = sourceMethod.getName();
//            System.out.println("11 source method name:" + methodName);
            targetHasThisMethod = false;
            A:
            for (Method targetMethod : targetMethods) {
                if (methodName.equals(targetMethod.getName())) {
                    //返回类型不同
                    if (!sourceMethod.getReturnType().getName().equals(targetMethod.getReturnType().getName())) {
                        System.out.println("22 找到了方法" + methodName + " 但是返回类型不同 source:" + sourceMethod.getReturnType().getName() + " -target:" + targetMethod.getReturnType().getName());
                        continue;
                    }
                    //参数数目不同
                    if (sourceMethod.getParameterCount() != targetMethod.getParameterCount()) {
                        System.out.println("22 找到了方法" + methodName + " 但是参数数目不同 source:" + sourceMethod.getParameterCount() + " -target:" + targetMethod.getParameterCount());
                        continue;
                    }
                    //参数类型不一致
                    if (sourceMethod.getParameterCount() > 0) {
                        for (Class<?> sourceParamType : sourceMethod.getParameterTypes()) {
                            boolean hasThisParam = false;
                            for (Class<?> targetParamType : targetMethod.getParameterTypes()) {
                                if (sourceParamType.getName().equals(targetParamType.getName())) {
                                    hasThisParam = true;
                                    break;
                                }
                            }
                            if (!hasThisParam) {
                                System.out.println("22 找到了方法" + methodName + " 但是参数类型不存在 source:" + sourceParamType.getName());
                                continue A;
                            }
                        }
                    }
                    targetHasThisMethod = true;
                    break;
                }
            }
            if (!targetHasThisMethod) {
                System.out.println(target.getSimpleName() + " 中没有 " + methodName + " 这个方法");
            }
        }
    }

    private static class MethodClass1 {
        public String test1(String a) {
            return "";
        }

        public void test2(int b, int a) {
        }

        public void test3(int a, String b) {
        }

        public String test4(int a, String b) {
            return "";
        }
    }

    private static class MethodClass2 {
        public void test1(int a) {
        }

        public void test2(int b) {
        }

        public void test3(int b, int c) {
        }

        public String test4(int a, String b) {
            return "";
        }
    }
}
