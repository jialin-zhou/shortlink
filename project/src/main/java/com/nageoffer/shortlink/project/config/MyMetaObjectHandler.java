package com.nageoffer.shortlink.project.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入数据时自动填充创建时间和更新时间，以及设置删除标志位。
     *
     * @param metaObject 元对象，代表待插入的数据对象。
     * 无返回值，但会对传入的metaObject进行操作，自动填充createTime、updateTime和delFlag字段。
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间和更新时间为当前时间
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        this.strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
        // 填充删除标志位为0，表示未删除
        this.strictInsertFill(metaObject, "delFlag", Integer.class, 0);

    }


    /**
     * 更新填充方法。
     * 此方法会为指定的元对象填充更新时间字段。
     *
     * @param metaObject 元对象，即将被更新的对象。
     * 注意：此方法不返回任何值，它对传入的元对象进行修改。
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 使用严格更新填充方式，为元对象设置"updateTime"字段的值，使用系统当前时间
        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
    }

}
