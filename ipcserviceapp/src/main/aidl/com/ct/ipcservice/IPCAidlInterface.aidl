// IPCAidlInterface.aidl
package com.ct.ipcservice;

// Declare any non-default types here with import statements
import com.ct.ipcservice.vo.Rect;
interface IPCAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

     /**
     *获取当前进程ID
     */
     int getPid();

     /**
     * 计算两个数字的和
     */
    int sumNum(int num1,int num2);

      /**
      * 获取Rect
      */
     void getOutRect(out Rect rect);

     void getInRect(in Rect rect);

     void getInOutRect(inout Rect rect);


     /**
     *List
     */
     List<String> getStringList();

     List<Rect> getRectList();

     List<Rect> getInList(in List<Rect> list);

     List<Rect> getInOutList(inout List<Rect> list);

     /**
     * Map
     */
    void getInMap(in Map map);

    void getOutMap(out Map map);

    void getInOutMap(inout Map map);

}
