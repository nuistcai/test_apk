package com.squareup.leakcanary;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class ExcludedRefs implements Serializable {
    public final Map<String, Exclusion> classNames;
    public final Map<String, Map<String, Exclusion>> fieldNameByClassName;
    public final Map<String, Map<String, Exclusion>> staticFieldNameByClassName;
    public final Map<String, Exclusion> threadNames;

    public interface Builder {
        ExcludedRefs build();

        BuilderWithParams clazz(String str);

        BuilderWithParams instanceField(String str, String str2);

        BuilderWithParams staticField(String str, String str2);

        BuilderWithParams thread(String str);
    }

    public static Builder builder() {
        return new BuilderWithParams();
    }

    ExcludedRefs(BuilderWithParams builder) {
        this.fieldNameByClassName = unmodifiableRefStringMap(builder.fieldNameByClassName);
        this.staticFieldNameByClassName = unmodifiableRefStringMap(builder.staticFieldNameByClassName);
        this.threadNames = unmodifiableRefMap(builder.threadNames);
        this.classNames = unmodifiableRefMap(builder.classNames);
    }

    private Map<String, Map<String, Exclusion>> unmodifiableRefStringMap(Map<String, Map<String, ParamsBuilder>> mapmap) {
        LinkedHashMap<String, Map<String, Exclusion>> fieldNameByClassName = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, ParamsBuilder>> entry : mapmap.entrySet()) {
            fieldNameByClassName.put(entry.getKey(), unmodifiableRefMap(entry.getValue()));
        }
        return Collections.unmodifiableMap(fieldNameByClassName);
    }

    private Map<String, Exclusion> unmodifiableRefMap(Map<String, ParamsBuilder> fieldBuilderMap) {
        Map<String, Exclusion> fieldMap = new LinkedHashMap<>();
        for (Map.Entry<String, ParamsBuilder> fieldEntry : fieldBuilderMap.entrySet()) {
            fieldMap.put(fieldEntry.getKey(), new Exclusion(fieldEntry.getValue()));
        }
        return Collections.unmodifiableMap(fieldMap);
    }

    public String toString() {
        String string = "";
        for (Map.Entry<String, Map<String, Exclusion>> classes : this.fieldNameByClassName.entrySet()) {
            String clazz = classes.getKey();
            for (Map.Entry<String, Exclusion> field : classes.getValue().entrySet()) {
                String always = field.getValue().alwaysExclude ? " (always)" : "";
                string = string + "| Field: " + clazz + "." + field.getKey() + always + "\n";
            }
        }
        for (Map.Entry<String, Map<String, Exclusion>> classes2 : this.staticFieldNameByClassName.entrySet()) {
            String clazz2 = classes2.getKey();
            for (Map.Entry<String, Exclusion> field2 : classes2.getValue().entrySet()) {
                String always2 = field2.getValue().alwaysExclude ? " (always)" : "";
                string = string + "| Static field: " + clazz2 + "." + field2.getKey() + always2 + "\n";
            }
        }
        for (Map.Entry<String, Exclusion> thread : this.threadNames.entrySet()) {
            String always3 = thread.getValue().alwaysExclude ? " (always)" : "";
            string = string + "| Thread:" + thread.getKey() + always3 + "\n";
        }
        for (Map.Entry<String, Exclusion> clazz3 : this.classNames.entrySet()) {
            String always4 = clazz3.getValue().alwaysExclude ? " (always)" : "";
            string = string + "| Class:" + clazz3.getKey() + always4 + "\n";
        }
        return string;
    }

    static final class ParamsBuilder {
        boolean alwaysExclude;
        final String matching;
        String name;
        String reason;

        ParamsBuilder(String matching) {
            this.matching = matching;
        }
    }

    public static final class BuilderWithParams implements Builder {
        private ParamsBuilder lastParams;
        private final Map<String, Map<String, ParamsBuilder>> fieldNameByClassName = new LinkedHashMap();
        private final Map<String, Map<String, ParamsBuilder>> staticFieldNameByClassName = new LinkedHashMap();
        private final Map<String, ParamsBuilder> threadNames = new LinkedHashMap();
        private final Map<String, ParamsBuilder> classNames = new LinkedHashMap();

        BuilderWithParams() {
        }

        @Override // com.squareup.leakcanary.ExcludedRefs.Builder
        public BuilderWithParams instanceField(String className, String fieldName) {
            Preconditions.checkNotNull(className, "className");
            Preconditions.checkNotNull(fieldName, "fieldName");
            Map<String, ParamsBuilder> excludedFields = this.fieldNameByClassName.get(className);
            if (excludedFields == null) {
                excludedFields = new LinkedHashMap();
                this.fieldNameByClassName.put(className, excludedFields);
            }
            this.lastParams = new ParamsBuilder("field " + className + "#" + fieldName);
            excludedFields.put(fieldName, this.lastParams);
            return this;
        }

        @Override // com.squareup.leakcanary.ExcludedRefs.Builder
        public BuilderWithParams staticField(String className, String fieldName) {
            Preconditions.checkNotNull(className, "className");
            Preconditions.checkNotNull(fieldName, "fieldName");
            Map<String, ParamsBuilder> excludedFields = this.staticFieldNameByClassName.get(className);
            if (excludedFields == null) {
                excludedFields = new LinkedHashMap();
                this.staticFieldNameByClassName.put(className, excludedFields);
            }
            this.lastParams = new ParamsBuilder("static field " + className + "#" + fieldName);
            excludedFields.put(fieldName, this.lastParams);
            return this;
        }

        @Override // com.squareup.leakcanary.ExcludedRefs.Builder
        public BuilderWithParams thread(String threadName) {
            Preconditions.checkNotNull(threadName, "threadName");
            this.lastParams = new ParamsBuilder("any threads named " + threadName);
            this.threadNames.put(threadName, this.lastParams);
            return this;
        }

        @Override // com.squareup.leakcanary.ExcludedRefs.Builder
        public BuilderWithParams clazz(String className) {
            Preconditions.checkNotNull(className, "className");
            this.lastParams = new ParamsBuilder("any subclass of " + className);
            this.classNames.put(className, this.lastParams);
            return this;
        }

        public BuilderWithParams named(String name) {
            this.lastParams.name = name;
            return this;
        }

        public BuilderWithParams reason(String reason) {
            this.lastParams.reason = reason;
            return this;
        }

        public BuilderWithParams alwaysExclude() {
            this.lastParams.alwaysExclude = true;
            return this;
        }

        @Override // com.squareup.leakcanary.ExcludedRefs.Builder
        public ExcludedRefs build() {
            return new ExcludedRefs(this);
        }
    }
}