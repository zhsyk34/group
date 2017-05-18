package com.cat.persistence.session;

import com.cat.util.BeanUtils;
import lombok.Setter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.annotation.Resource;

//@Repository()
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DefaultSession extends AbstractSession {
    @Resource
    private SqlSessionFactory sqlSessionFactory;
    @Setter
    private Class<?> targetClass;
    @Setter
    private Class<?> superClass;

    @Override
    public String namespace() {
        String targetInterfaceName = targetClass.getInterfaces()[0].getName();
        String namespace = targetInterfaceName + "." + methodCaller();
        System.err.println("---------------namespace: " + namespace);
        return namespace;
    }

    @Override
    public SqlSession session() {
        return sqlSessionFactory.openSession();
    }

    private String methodCaller() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        //first to find target namespace in caller at last
        int i = BeanUtils.findIndex(elements, targetClass.getName(), (reference, target) -> target.startsWith(reference), false);
        if (i < 0) {
            //if not find,try to find in super common class at last?
            i = BeanUtils.findIndex(elements, superClass.getName(), (reference, target) -> target.startsWith(reference), true);
        }
        return i < 0 ? null : elements[i].getMethodName();
    }

}
