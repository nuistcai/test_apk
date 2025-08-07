package kotlin.jvm.internal;

import java.util.List;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty3;
import kotlin.reflect.KProperty4;
import kotlin.reflect.KProperty5;
import kotlin.reflect.KProperty6;
import kotlin.reflect.KProperty7;
import kotlin.reflect.KProperty8;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;

/* loaded from: classes.dex */
public class ReflectionFactory {
    private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";

    public KClass createKotlinClass(Class javaClass) {
        return new ClassReference(javaClass);
    }

    public KClass createKotlinClass(Class javaClass, String internalName) {
        return new ClassReference(javaClass);
    }

    public KDeclarationContainer getOrCreateKotlinPackage(Class javaClass, String moduleName) {
        return new PackageReference(javaClass, moduleName);
    }

    public KClass getOrCreateKotlinClass(Class javaClass) {
        return new ClassReference(javaClass);
    }

    public KClass getOrCreateKotlinClass(Class javaClass, String internalName) {
        return new ClassReference(javaClass);
    }

    public String renderLambdaToString(Lambda lambda) {
        return renderLambdaToString((FunctionBase) lambda);
    }

    public String renderLambdaToString(FunctionBase lambda) {
        String result = lambda.getClass().getGenericInterfaces()[0].toString();
        return result.startsWith(KOTLIN_JVM_FUNCTIONS) ? result.substring(KOTLIN_JVM_FUNCTIONS.length()) : result;
    }

    public KFunction function(FunctionReference f) {
        return f;
    }

    public KProperty6 property0(PropertyReference0 p) {
        return p;
    }

    public KProperty3 mutableProperty0(MutablePropertyReference0 p) {
        return p;
    }

    public KProperty7 property1(PropertyReference1 p) {
        return p;
    }

    public KProperty4 mutableProperty1(MutablePropertyReference1 p) {
        return p;
    }

    public KProperty8 property2(PropertyReference2 p) {
        return p;
    }

    public KProperty5 mutableProperty2(MutablePropertyReference2 p) {
        return p;
    }

    public KType typeOf(KClassifier klass, List<KTypeProjection> arguments, boolean isMarkedNullable) {
        return new TypeReference(klass, arguments, isMarkedNullable);
    }
}