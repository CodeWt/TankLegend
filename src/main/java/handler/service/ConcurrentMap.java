//package handler.service;
//
//import java.util.Map;
//
///**
// * @Created by  qiao
// * @date 18-3-10 下午7:35
// */
///*oncurrentMap接口定义了几个基于 CAS（Compare and Set）操作，很简单，但非常有用*/
//interface ConcurrentMap<K, V> extends Map<K, V> {
//
//    /* 如果key对应的value不存在，则put进去，返回null。否则不put，返回已存在的value。  */
//    V putIfAbsent(K key, V value);
//
//    /*如果key对应的值是value，则移除K-V，返回true。否则不移除，返回false。  */
//    boolean remove(Object key, Object value);
//
//    /*如果key对应的当前值是oldValue，则替换为newValue，返回true。否则不替换，返回false。*/
//    boolean replace(K key, V oldValue, V newValue);
//
//    V replace(K key, V value);
//}