package com.kaurihealth.datalib.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface ILocalSource {

    String getJsonCache(String key);

    void addJsonCache(String key, String json);

    void removeJsonCache(String key);

    /**
     * 插入一条记录
     *
     * @param t
     */
    <T> long insert(T t);

    <T> long insertEnsureByOne(T t);

    /**
     * 插入所有记录
     *
     * @param list
     */
    <T> void insertAll(List<T> list);

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    <T> List<T> getQueryAll(Class<T> cla);

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    <T> ArrayList getQueryByWhere(Class<T> cla, String field, Object[] value);

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    <T> List<T> getQueryByWhereLength(Class<T> cla, String field, Object[] value, int start, int length);

    /**
     * 删除一个数据
     *
     * @param t
     * @param <T>
     */
    <T> void delete(T t);

    /**
     * 删除一个表
     *
     * @param cla
     * @param <T>
     */
    <T> void delete(Class<T> cla);

    /**
     * 删除一个对象表 连同其关联的classes，classes关联的其他对象一带删除
     * e.g..deleteAll(TokenBean.UserBean.class);
     * user TABLE DELETE,but token TABLE still,associated still
     *
     * @param cla
     * @param <T>
     */
    <T> void deleteAll(Class<T> cla);

    /**
     * 删除数据库
     */
    void deleteDatabase();

    /**
     *当前运行的版本环境
     */
    String getEnvironment();

    <T> void insertEnsureByOneList(List<T> list);
}
