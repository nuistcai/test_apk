package com.squareup.haha.perflib;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ClassInstance extends Instance {
    private final long mValuesOffset;

    public ClassInstance(long id, StackTrace stack, long valuesOffset) {
        super(id, stack);
        this.mValuesOffset = valuesOffset;
    }

    List<FieldValue> getFields(String name) {
        ArrayList<FieldValue> result = new ArrayList<>();
        for (FieldValue value : getValues()) {
            if (value.getField().getName().equals(name)) {
                result.add(value);
            }
        }
        return result;
    }

    public List<FieldValue> getValues() {
        ArrayList<FieldValue> result = new ArrayList<>();
        getBuffer().setPosition(this.mValuesOffset);
        for (ClassObj clazz = getClassObj(); clazz != null; clazz = clazz.getSuperClassObj()) {
            Field[] arr$ = clazz.getFields();
            for (Field field : arr$) {
                result.add(new FieldValue(field, readValue(field.getType())));
            }
        }
        return result;
    }

    @Override // com.squareup.haha.perflib.Instance
    public final void accept(Visitor visitor) {
        visitor.visitClassInstance(this);
        for (FieldValue field : getValues()) {
            if (field.getValue() instanceof Instance) {
                if (!this.mReferencesAdded) {
                    ((Instance) field.getValue()).addReference(field.getField(), this);
                }
                visitor.visitLater(this, (Instance) field.getValue());
            }
        }
        this.mReferencesAdded = true;
    }

    @Override // com.squareup.haha.perflib.Instance
    public boolean getIsSoftReference() {
        return getClassObj().getIsSoftReference();
    }

    public final String toString() {
        return String.format("%s@%d (0x%x)", getClassObj().getClassName(), Long.valueOf(getUniqueId()), Long.valueOf(getUniqueId()));
    }

    public static class FieldValue {
        private Field mField;
        private Object mValue;

        public FieldValue(Field field, Object value) {
            this.mField = field;
            this.mValue = value;
        }

        public Field getField() {
            return this.mField;
        }

        public Object getValue() {
            return this.mValue;
        }
    }
}