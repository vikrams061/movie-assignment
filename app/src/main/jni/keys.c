#include <jni.h>

JNIEXPORT jstring JNICALL

Java_movie_searchmovies_SearchMoviesActivity_GetApi(JNIEnv* env, jobject thiz ) {
    return (*env)-> NewStringUTF(env, "dd650fb7");
}

