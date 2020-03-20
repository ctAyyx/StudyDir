// RemoteCallbackAidlInterface.aidl
package com.ct.ipcservice;

// Declare any non-default types here with import statements

interface RemoteCallbackAidlInterface {

   /**
   *回调接口
   */
   void onCallBack(String str);
}
