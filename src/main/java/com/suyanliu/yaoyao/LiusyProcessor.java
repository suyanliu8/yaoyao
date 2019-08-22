package com.suyanliu.yaoyao;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("com.suyanliu.yaoyao.Liusy")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class LiusyProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // // 遍历所有被注解了的元素
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Liusy.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        Liusy.class.getSimpleName());
                return true;
            }
            // //解析，并生成代码
            analysisAnnotated(annotatedElement);
        }
        return false;
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    private static final String SUFFIX = "Test";
    private static final String packageName = "com.suyanliu.yaoyao";
    private static final String retStr = "";

    private void analysisAnnotated(Element classElement) {
        Liusy annotation = classElement.getAnnotation(Liusy.class);
        String name = annotation.name();

        String newClassName = name + SUFFIX;

        StringBuilder builder = new StringBuilder().append("package " + packageName + ";\n\n")
                .append("public class ").append(newClassName).append(" {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");

        // this is appending to the return statement
        builder.append(retStr).append(" !\\n");

        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class

        try { // write the file
            JavaFileObject source = mFiler.createSourceFile(packageName + "." + newClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
        builder = new StringBuilder().append("package " + packageName + ".impl;\n\n")
                .append("public class ").append(newClassName + "2").append(" {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");

        // this is appending to the return statement
        builder.append(retStr).append("000 !\\n");

        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class
        try { // write the file
            JavaFileObject source = mFiler
                    .createSourceFile(packageName + ".impl." + newClassName + "2");
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }

    }
}