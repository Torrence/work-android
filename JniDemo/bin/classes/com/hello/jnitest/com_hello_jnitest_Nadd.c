#include <stdlib.h>
#include "com_hello_jnitest_Nadd.h"

JNIEXPORT jint JNICALL Java_com_hello_jnitest_Nadd_nadd(JNIEnv *env, jobject c, jint a, jint b)
{
	return (a + b);
}
