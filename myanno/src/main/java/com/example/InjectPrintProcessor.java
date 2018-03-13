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
@SupportedAnnotationTypes("com.example.InjectPrint")//������ָ��ע�����͵�ȫ·��
public class InjectPrintProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //��ȡInjectPrint����ע�⣬Ȼ�����
        for(Element element : roundEnvironment.getElementsAnnotatedWith(InjectPrint.class)){
            //Ԫ��������һ������
            if(element.getKind() == ElementKind.METHOD){
                //ǿת�ɷ�����Ӧ��element��ͬ
                // ��������ע����һ���࣬�������ǿת��TypeElement
                ExecutableElement executableElement = (ExecutableElement)element;
                //��ӡ������
                System.out.println(executableElement.getSimpleName());
                //��ӡ�����ķ�������
                System.out.println(executableElement.getReturnType().toString());
                //��ȡ�������еĲ���
                List<? extends VariableElement> params = executableElement.getParameters();
                for(VariableElement variableElement : params){//��������ӡ������
                    System.out.println(variableElement.getSimpleName());
                }
                //��ӡע���ֵ
                System.out.println("AnnotationValue:"+executableElement.getAnnotation(InjectPrint.class).value());
            }
        }
        return false;
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
