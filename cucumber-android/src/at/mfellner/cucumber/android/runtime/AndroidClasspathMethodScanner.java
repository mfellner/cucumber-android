package at.mfellner.cucumber.android.runtime;

import android.content.Context;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.CucumberException;
import cucumber.runtime.Utils;
import cucumber.runtime.java.StepDefAnnotation;
import ext.android.test.ClassPathPackageInfoSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class AndroidClasspathMethodScanner {
    private final Collection<Class<? extends Annotation>> mCucumberAnnotationClasses;
    private final ClassPathPackageInfoSource mSource;

    public AndroidClasspathMethodScanner(Context context) {
        mSource = classPathPackageInfoSource(context);
        mCucumberAnnotationClasses = findCucumberAnnotationClasses();
    }

    private static ClassPathPackageInfoSource classPathPackageInfoSource(Context context) {
        String apkPath = context.getPackageCodePath();
        ClassPathPackageInfoSource.setApkPaths(new String[]{apkPath});
        ClassPathPackageInfoSource source = new ClassPathPackageInfoSource();
        source.setClassLoader(context.getClassLoader());
        return source;
    }

    /**
     * Registers step definitions and hooks.
     *
     * @param androidBackend the backend where stepdefs and hooks will be registered
     * @param gluePaths      where to look
     */
    public void scan(AndroidBackend androidBackend, List<String> gluePaths) {
        for (String gluePath : gluePaths) {
            for (Class<?> glueCodeClass : mSource.getPackageInfo(gluePath).getTopLevelClassesRecursive()) {
                while (glueCodeClass != null && glueCodeClass != Object.class && !Utils.isInstantiable(glueCodeClass)) {
                    // those can't be instantiated without container class present
                    glueCodeClass = glueCodeClass.getSuperclass();
                }
                if (glueCodeClass != null) {
                    for (Method method : glueCodeClass.getMethods()) {
                        scan(androidBackend, method, glueCodeClass);
                    }
                }
            }
        }
    }

    /**
     * Registers step definitions and hooks.
     *
     * @param androidBackend the backend where stepdefs and hooks will be registered.
     * @param method         a candidate for being a stepdef or hook.
     * @param glueCodeClass  the class where the method is declared.
     */
    public void scan(AndroidBackend androidBackend, Method method, Class<?> glueCodeClass) {
        for (Class<? extends Annotation> cucumberAnnotationClass : mCucumberAnnotationClasses) {
            Annotation annotation = method.getAnnotation(cucumberAnnotationClass);
            if (annotation != null) {
                if (!method.getDeclaringClass().isAssignableFrom(glueCodeClass)) {
                    throw new CucumberException(String.format("%s isn't assignable from %s", method.getDeclaringClass(), glueCodeClass));
                }
                if (!glueCodeClass.equals(method.getDeclaringClass())) {
                    throw new CucumberException(String.format("You're not allowed to extend classes that define Step Definitions or hooks. %s extends %s", glueCodeClass, method.getDeclaringClass()));
                }
                if (isHookAnnotation(annotation)) {
                    androidBackend.addHook(annotation, method);
                } else if (isStepdefAnnotation(annotation)) {
                    androidBackend.addStepDefinition(annotation, method);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Collection<Class<? extends Annotation>> findCucumberAnnotationClasses() {
        List<Class<? extends Annotation>> annotationClasses = new ArrayList<Class<? extends Annotation>>();
        for (Class clazz : mSource.getPackageInfo("cucumber.api").getTopLevelClassesRecursive()) {
            if (clazz.isAnnotation()) annotationClasses.add(clazz);
        }
        return annotationClasses;
    }

    private boolean isHookAnnotation(Annotation annotation) {
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        return annotationClass.equals(Before.class) || annotationClass.equals(After.class);
    }

    private boolean isStepdefAnnotation(Annotation annotation) {
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        return annotationClass.getAnnotation(StepDefAnnotation.class) != null;
    }
}