package com.squareup.haha.perflib;

import com.squareup.haha.perflib.io.HprofBuffer;
import com.squareup.haha.trove.TLongObjectHashMap;
import java.io.EOFException;
import java.io.IOException;
import kotlin.UByte;
import kotlin.UShort;

/* loaded from: classes.dex */
public class HprofParser {
    private static final int ALLOC_SITES = 6;
    private static final int CONTROL_SETTINGS = 14;
    private static final int CPU_SAMPLES = 13;
    private static final int END_THREAD = 11;
    private static final int HEAP_DUMP = 12;
    private static final int HEAP_DUMP_END = 44;
    private static final int HEAP_DUMP_SEGMENT = 28;
    private static final int HEAP_SUMMARY = 7;
    private static final int LOAD_CLASS = 2;
    private static final int ROOT_CLASS_DUMP = 32;
    private static final int ROOT_DEBUGGER = 139;
    private static final int ROOT_FINALIZING = 138;
    private static final int ROOT_HEAP_DUMP_INFO = 254;
    private static final int ROOT_INSTANCE_DUMP = 33;
    private static final int ROOT_INTERNED_STRING = 137;
    private static final int ROOT_JAVA_FRAME = 3;
    private static final int ROOT_JNI_GLOBAL = 1;
    private static final int ROOT_JNI_LOCAL = 2;
    private static final int ROOT_JNI_MONITOR = 142;
    private static final int ROOT_MONITOR_USED = 7;
    private static final int ROOT_NATIVE_STACK = 4;
    private static final int ROOT_OBJECT_ARRAY_DUMP = 34;
    private static final int ROOT_PRIMITIVE_ARRAY_DUMP = 35;
    private static final int ROOT_PRIMITIVE_ARRAY_NODATA = 195;
    private static final int ROOT_REFERENCE_CLEANUP = 140;
    private static final int ROOT_STICKY_CLASS = 5;
    private static final int ROOT_THREAD_BLOCK = 6;
    private static final int ROOT_THREAD_OBJECT = 8;
    private static final int ROOT_UNKNOWN = 255;
    private static final int ROOT_UNREACHABLE = 144;
    private static final int ROOT_VM_INTERNAL = 141;
    private static final int STACK_FRAME = 4;
    private static final int STACK_TRACE = 5;
    private static final int START_THREAD = 10;
    private static final int STRING_IN_UTF8 = 1;
    private static final int UNLOAD_CLASS = 3;
    int mIdSize;
    private final HprofBuffer mInput;
    Snapshot mSnapshot;
    TLongObjectHashMap<String> mStrings = new TLongObjectHashMap<>();
    TLongObjectHashMap<String> mClassNames = new TLongObjectHashMap<>();

    public HprofParser(HprofBuffer buffer) {
        this.mInput = buffer;
    }

    public final Snapshot parse() {
        Snapshot snapshot = new Snapshot(this.mInput);
        this.mSnapshot = snapshot;
        try {
            try {
                readNullTerminatedString();
                this.mIdSize = this.mInput.readInt();
                this.mSnapshot.setIdSize(this.mIdSize);
                this.mInput.readLong();
                while (this.mInput.hasRemaining()) {
                    int unsignedByte = readUnsignedByte();
                    this.mInput.readInt();
                    long unsignedInt = readUnsignedInt();
                    switch (unsignedByte) {
                        case 1:
                            loadString(((int) unsignedInt) - this.mIdSize);
                            break;
                        case 2:
                            loadClass();
                            break;
                        case 4:
                            loadStackFrame();
                            break;
                        case 5:
                            loadStackTrace();
                            break;
                        case 12:
                            loadHeapDump(unsignedInt);
                            this.mSnapshot.setToDefaultHeap();
                            break;
                        case 28:
                            loadHeapDump(unsignedInt);
                            this.mSnapshot.setToDefaultHeap();
                            break;
                        default:
                            skipFully(unsignedInt);
                            break;
                    }
                }
            } catch (EOFException e) {
            }
            this.mSnapshot.resolveClasses();
            this.mSnapshot.resolveReferences();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mClassNames.clear();
        this.mStrings.clear();
        return snapshot;
    }

    private String readNullTerminatedString() throws IOException {
        StringBuilder s = new StringBuilder();
        byte c = this.mInput.readByte();
        while (c != 0) {
            s.append((char) c);
            c = this.mInput.readByte();
        }
        return s.toString();
    }

    private long readId() throws IOException {
        switch (this.mIdSize) {
            case 1:
                return this.mInput.readByte();
            case 2:
                return this.mInput.readShort();
            case 4:
                return this.mInput.readInt();
            case 8:
                return this.mInput.readLong();
            default:
                throw new IllegalArgumentException("ID Length must be 1, 2, 4, or 8");
        }
    }

    private String readUTF8(int length) throws IOException {
        byte[] b = new byte[length];
        this.mInput.read(b);
        return new String(b, "utf-8");
    }

    private int readUnsignedByte() throws IOException {
        return this.mInput.readByte() & UByte.MAX_VALUE;
    }

    private int readUnsignedShort() throws IOException {
        return this.mInput.readShort() & UShort.MAX_VALUE;
    }

    private long readUnsignedInt() throws IOException {
        return this.mInput.readInt() & 4294967295L;
    }

    private void loadString(int length) throws IOException {
        long id = readId();
        String string = readUTF8(length);
        this.mStrings.put(id, string);
    }

    private void loadClass() throws IOException {
        this.mInput.readInt();
        long id = readId();
        this.mInput.readInt();
        String name = this.mStrings.get(readId());
        this.mClassNames.put(id, name);
    }

    private void loadStackFrame() throws IOException {
        long id = readId();
        String methodName = this.mStrings.get(readId());
        String methodSignature = this.mStrings.get(readId());
        String sourceFile = this.mStrings.get(readId());
        int serial = this.mInput.readInt();
        int lineNumber = this.mInput.readInt();
        StackFrame frame = new StackFrame(id, methodName, methodSignature, sourceFile, serial, lineNumber);
        this.mSnapshot.addStackFrame(frame);
    }

    private void loadStackTrace() throws IOException {
        int serialNumber = this.mInput.readInt();
        int threadSerialNumber = this.mInput.readInt();
        int numFrames = this.mInput.readInt();
        StackFrame[] frames = new StackFrame[numFrames];
        for (int i = 0; i < numFrames; i++) {
            frames[i] = this.mSnapshot.getStackFrame(readId());
        }
        StackTrace trace = new StackTrace(serialNumber, threadSerialNumber, frames);
        this.mSnapshot.addStackTrace(trace);
    }

    private void loadHeapDump(long length) throws IOException {
        while (length > 0) {
            int tag = readUnsignedByte();
            long length2 = length - 1;
            switch (tag) {
                case 1:
                    readId();
                    length = (length2 - loadBasicObj(RootType.NATIVE_STATIC)) - this.mIdSize;
                    break;
                case 2:
                    length = length2 - loadJniLocal();
                    break;
                case 3:
                    length = length2 - loadJavaFrame();
                    break;
                case 4:
                    length = length2 - loadNativeStack();
                    break;
                case 5:
                    length = length2 - loadBasicObj(RootType.SYSTEM_CLASS);
                    break;
                case 6:
                    length = length2 - loadThreadBlock();
                    break;
                case 7:
                    length = length2 - loadBasicObj(RootType.BUSY_MONITOR);
                    break;
                case 8:
                    length = length2 - loadThreadObject();
                    break;
                case 32:
                    length = length2 - loadClassDump();
                    break;
                case 33:
                    length = length2 - loadInstanceDump();
                    break;
                case 34:
                    length = length2 - loadObjectArrayDump();
                    break;
                case 35:
                    length = length2 - loadPrimitiveArrayDump();
                    break;
                case ROOT_INTERNED_STRING /* 137 */:
                    length = length2 - loadBasicObj(RootType.INTERNED_STRING);
                    break;
                case ROOT_FINALIZING /* 138 */:
                    length = length2 - loadBasicObj(RootType.FINALIZING);
                    break;
                case ROOT_DEBUGGER /* 139 */:
                    length = length2 - loadBasicObj(RootType.DEBUGGER);
                    break;
                case ROOT_REFERENCE_CLEANUP /* 140 */:
                    length = length2 - loadBasicObj(RootType.REFERENCE_CLEANUP);
                    break;
                case ROOT_VM_INTERNAL /* 141 */:
                    length = length2 - loadBasicObj(RootType.VM_INTERNAL);
                    break;
                case ROOT_JNI_MONITOR /* 142 */:
                    length = length2 - loadJniMonitor();
                    break;
                case ROOT_UNREACHABLE /* 144 */:
                    length = length2 - loadBasicObj(RootType.UNREACHABLE);
                    break;
                case ROOT_PRIMITIVE_ARRAY_NODATA /* 195 */:
                    System.err.println("+--- PRIMITIVE ARRAY NODATA DUMP");
                    loadPrimitiveArrayDump();
                    throw new IllegalArgumentException("Don't know how to load a nodata array");
                case ROOT_HEAP_DUMP_INFO /* 254 */:
                    int heapId = this.mInput.readInt();
                    long heapNameId = readId();
                    String heapName = this.mStrings.get(heapNameId);
                    this.mSnapshot.setHeapTo(heapId, heapName);
                    length = length2 - (this.mIdSize + 4);
                    break;
                case 255:
                    length = length2 - loadBasicObj(RootType.UNKNOWN);
                    break;
                default:
                    throw new IllegalArgumentException("loadHeapDump loop with unknown tag " + tag + " with " + this.mInput.remaining() + " bytes possibly remaining");
            }
        }
    }

    private int loadJniLocal() throws IOException {
        long id = readId();
        int threadSerialNumber = this.mInput.readInt();
        int stackFrameNumber = this.mInput.readInt();
        ThreadObj thread = this.mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = this.mSnapshot.getStackTraceAtDepth(thread.mStackTrace, stackFrameNumber);
        RootObj root = new RootObj(RootType.NATIVE_LOCAL, id, threadSerialNumber, trace);
        this.mSnapshot.addRoot(root);
        return this.mIdSize + 4 + 4;
    }

    private int loadJavaFrame() throws IOException {
        long id = readId();
        int threadSerialNumber = this.mInput.readInt();
        int stackFrameNumber = this.mInput.readInt();
        ThreadObj thread = this.mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = this.mSnapshot.getStackTraceAtDepth(thread.mStackTrace, stackFrameNumber);
        RootObj root = new RootObj(RootType.JAVA_LOCAL, id, threadSerialNumber, trace);
        this.mSnapshot.addRoot(root);
        return this.mIdSize + 4 + 4;
    }

    private int loadNativeStack() throws IOException {
        long id = readId();
        int threadSerialNumber = this.mInput.readInt();
        ThreadObj thread = this.mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = this.mSnapshot.getStackTrace(thread.mStackTrace);
        RootObj root = new RootObj(RootType.NATIVE_STACK, id, threadSerialNumber, trace);
        this.mSnapshot.addRoot(root);
        return this.mIdSize + 4;
    }

    private int loadBasicObj(RootType type) throws IOException {
        long id = readId();
        RootObj root = new RootObj(type, id);
        this.mSnapshot.addRoot(root);
        return this.mIdSize;
    }

    private int loadThreadBlock() throws IOException {
        long id = readId();
        int threadSerialNumber = this.mInput.readInt();
        ThreadObj thread = this.mSnapshot.getThread(threadSerialNumber);
        StackTrace stack = this.mSnapshot.getStackTrace(thread.mStackTrace);
        RootObj root = new RootObj(RootType.THREAD_BLOCK, id, threadSerialNumber, stack);
        this.mSnapshot.addRoot(root);
        return this.mIdSize + 4;
    }

    private int loadThreadObject() throws IOException {
        long id = readId();
        int threadSerialNumber = this.mInput.readInt();
        int stackSerialNumber = this.mInput.readInt();
        ThreadObj thread = new ThreadObj(id, stackSerialNumber);
        this.mSnapshot.addThread(thread, threadSerialNumber);
        return this.mIdSize + 4 + 4;
    }

    private int loadClassDump() throws IOException {
        long id = readId();
        int stackSerialNumber = this.mInput.readInt();
        StackTrace stack = this.mSnapshot.getStackTrace(stackSerialNumber);
        long superClassId = readId();
        long classLoaderId = readId();
        readId();
        readId();
        readId();
        readId();
        int instanceSize = this.mInput.readInt();
        int bytesRead = (this.mIdSize * 7) + 4 + 4;
        int numEntries = readUnsignedShort();
        int bytesRead2 = bytesRead + 2;
        for (int i = 0; i < numEntries; i++) {
            readUnsignedShort();
            bytesRead2 += skipValue() + 2;
        }
        ClassObj theClass = new ClassObj(id, stack, this.mClassNames.get(id), this.mInput.position());
        theClass.setSuperClassId(superClassId);
        theClass.setClassLoaderId(classLoaderId);
        int numEntries2 = readUnsignedShort();
        int bytesRead3 = bytesRead2 + 2;
        Field[] staticFields = new Field[numEntries2];
        int i2 = 0;
        while (i2 < numEntries2) {
            String name = this.mStrings.get(readId());
            Type type = Type.getType(this.mInput.readByte());
            staticFields[i2] = new Field(type, name);
            skipFully(this.mSnapshot.getTypeSize(type));
            bytesRead3 += this.mIdSize + 1 + this.mSnapshot.getTypeSize(type);
            i2++;
            stack = stack;
            superClassId = superClassId;
        }
        theClass.setStaticFields(staticFields);
        int numEntries3 = readUnsignedShort();
        int bytesRead4 = bytesRead3 + 2;
        Field[] fields = new Field[numEntries3];
        for (int i3 = 0; i3 < numEntries3; i3++) {
            String name2 = this.mStrings.get(readId());
            fields[i3] = new Field(Type.getType(readUnsignedByte()), name2);
            bytesRead4 += this.mIdSize + 1;
        }
        theClass.setFields(fields);
        theClass.setInstanceSize(instanceSize);
        this.mSnapshot.addClass(id, theClass);
        return bytesRead4;
    }

    private int loadInstanceDump() throws IOException {
        long id = readId();
        int stackId = this.mInput.readInt();
        StackTrace stack = this.mSnapshot.getStackTrace(stackId);
        long classId = readId();
        int remaining = this.mInput.readInt();
        long position = this.mInput.position();
        ClassInstance instance = new ClassInstance(id, stack, position);
        instance.setClassId(classId);
        this.mSnapshot.addInstance(id, instance);
        skipFully(remaining);
        return this.mIdSize + 4 + this.mIdSize + 4 + remaining;
    }

    private int loadObjectArrayDump() throws IOException {
        long id = readId();
        int stackId = this.mInput.readInt();
        StackTrace stack = this.mSnapshot.getStackTrace(stackId);
        int numElements = this.mInput.readInt();
        long classId = readId();
        ArrayInstance array = new ArrayInstance(id, stack, Type.OBJECT, numElements, this.mInput.position());
        array.setClassId(classId);
        this.mSnapshot.addInstance(id, array);
        int remaining = this.mIdSize * numElements;
        skipFully(remaining);
        return this.mIdSize + 4 + 4 + this.mIdSize + remaining;
    }

    private int loadPrimitiveArrayDump() throws IOException {
        long id = readId();
        int stackId = this.mInput.readInt();
        StackTrace stack = this.mSnapshot.getStackTrace(stackId);
        int numElements = this.mInput.readInt();
        Type type = Type.getType(readUnsignedByte());
        int size = this.mSnapshot.getTypeSize(type);
        ArrayInstance array = new ArrayInstance(id, stack, type, numElements, this.mInput.position());
        this.mSnapshot.addInstance(id, array);
        int remaining = numElements * size;
        skipFully(remaining);
        return this.mIdSize + 4 + 4 + 1 + remaining;
    }

    private int loadJniMonitor() throws IOException {
        long id = readId();
        int threadSerialNumber = this.mInput.readInt();
        int stackDepth = this.mInput.readInt();
        ThreadObj thread = this.mSnapshot.getThread(threadSerialNumber);
        StackTrace trace = this.mSnapshot.getStackTraceAtDepth(thread.mStackTrace, stackDepth);
        RootObj root = new RootObj(RootType.NATIVE_MONITOR, id, threadSerialNumber, trace);
        this.mSnapshot.addRoot(root);
        return this.mIdSize + 4 + 4;
    }

    private int skipValue() throws IOException {
        Type type = Type.getType(readUnsignedByte());
        int size = this.mSnapshot.getTypeSize(type);
        skipFully(size);
        return size + 1;
    }

    private void skipFully(long numBytes) throws IOException {
        this.mInput.setPosition(this.mInput.position() + numBytes);
    }
}