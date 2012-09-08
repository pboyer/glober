jobject Java_android_peter_geometry_utility_allocNativeBuffer(JNIEnv* env, jobject thiz, jlong size)
{
    void* buffer = malloc(size);
    jobject directBuffer = env->NewDirectByteBuffer(buffer, size);
    jobject globalRef = env->NewGlobalRef(directBuffer);

    return globalRef;
}

void Java_android_peter_geometry_utility_freeNativeBuffer(JNIEnv* env, jobject thiz, jobject globalRef)
{
    void *buffer = env->GetDirectBufferAddress(globalRef);

    env->DeleteGlobalRef(globalRef);
    free(buffer);
}

jstring Java_com_peter_dummyjni_NdkFooActivity_invokeNativeFunction(JNIEnv* env, jobject javaThis) {
  return (*env)->NewStringUTF(env, "Hello from native code!");
}
