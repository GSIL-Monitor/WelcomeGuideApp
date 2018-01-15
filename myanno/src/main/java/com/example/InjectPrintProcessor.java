package com.example;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

/**
 * Created by fqzhang on 2018/1/15.
 */
@SupportedAnnotationTypes("com.example.InjectPrint")
public class InjectPrintProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for(Element element : roundEnvironment.getElementsAnnotatedWith(InjectPrint.class)){
            //如果这个元素是一个方法
            if(element.getKind() == ElementKind.METHOD){
                //强转成方法对应的element，同
                // 理，如果你的注解是一个类，那你可以强转成TypeElement
                ExecutableElement executableElement = (ExecutableElement)element;

                //打印方法名
                System.out.println(executableElement.getSimpleName());

                //打印方法的返回类型
                System.out.println(executableElement.getReturnType().toString());

                //获取方法所有的参数
                List<? extends VariableElement> params = executableElement.getParameters();
                //逐个打印参数名
                for(VariableElement variableElement : params){
                    System.out.println(variableElement.getSimpleName());
                }

                //打印注解的值
                System.out.println(executableElement.getAnnotation(InjectPrint.class).value());
            }
            System.out.println("------------------------------");
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
