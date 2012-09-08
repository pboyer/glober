LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
# Here we give our module name and source file(s)
LOCAL_MODULE    := native_allocator
LOCAL_SRC_FILES := native_allocator.c
 
include $(BUILD_SHARED_LIBRARY)

