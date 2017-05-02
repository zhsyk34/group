package com.cat.repository.impl;

import com.cat.repository.CommonRepository;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@Transactional//test
public class CommonRepositoryImpl<E, K extends Serializable> implements CommonRepository<E, K> {

    private final Class<E> entityClass;
    private final Class<K> idClass;
    @PersistenceContext
    protected EntityManager manager;
    protected CriteriaBuilder builder;
    protected String idColumn;

    @SuppressWarnings("unchecked")
    protected CommonRepositoryImpl() {
        //method 1:
        /*
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass()).as(CommonRepository.class);
        this.entityClass = (Class<E>) resolvableType.getGeneric(0).resolve();
        this.idClass = (Class<K>) resolvableType.getGeneric(1).resolve();
        */

        //method 2:
        Class<?>[] classes = GenericTypeResolver.resolveTypeArguments(this.getClass(), CommonRepository.class);
        this.entityClass = (Class<E>) classes[0];
        this.idClass = (Class<K>) classes[1];

        Assert.notNull(this.entityClass, "entity class can't be null");
        Assert.notNull(this.idClass, "id class can't be null");
    }

    @PostConstruct
    protected final void init() {
        this.builder = manager.getCriteriaBuilder();

        EntityType<E> entityType = manager.getMetamodel().entity(entityClass);
        Assert.state(entityType.getIdType().getJavaType() == idClass, "check id class type");

        idColumn = entityType.getId(idClass).getName();
        Assert.hasText(idColumn, "id column can't empty");
    }

    @Override
    public final EntityManager manager() {
        return manager;
    }

    @Override
    public final CriteriaBuilder builder() {
        return builder;
    }

    @Override
    public final K id(E e) {
        EntityManagerFactory factory = manager.getEntityManagerFactory();
        @SuppressWarnings("unchecked")
        K id = (K) factory.getPersistenceUnitUtil().getIdentifier(e);
        return id;
    }

    @Override
    public final void save(E e) {
        Assert.notNull(e, "entity can't be null");
        manager.persist(e);
    }

    @Override
    public final void saves(Collection<E> es) {
        Assert.notEmpty(es, "entities can't be null");
        es.forEach(this::save);
    }

    @Override
    public final int deleteById(K k) {
        Assert.notNull(k, "id can't be null");

        CriteriaDelete<E> criteria = builder.createCriteriaDelete(entityClass);
        criteria.where(builder.equal(criteria.from(entityClass).get(idColumn), k));

        return manager.createQuery(criteria).executeUpdate();
    }

    @Override
    public final int deleteByIds(K[] ks) {
        return ObjectUtils.isEmpty(ks) ? 0 : this.deleteByIds(Arrays.asList(ks));
    }

    @Override
    public final int deleteByIds(Collection<K> ks) {
        if (CollectionUtils.isEmpty(ks)) {
            return 0;
        }
        CriteriaDelete<E> criteria = builder.createCriteriaDelete(entityClass);
        criteria.where(criteria.from(entityClass).get(idColumn).in(ks));
        return manager.createQuery(criteria).executeUpdate();
    }

    @Override
    public final int deleteByEntity(E e) {
        Assert.notNull(e, "entity can't be null");
        //manager.remove(e);
        return this.deleteById(id(e));
    }

    @Override
    public final int deleteByEntities(Collection<E> es) {
        Assert.notEmpty(es, "entities can't be null");
        return this.deleteByIds(es.stream().map(this::id).collect(Collectors.toList()));
    }

    @Override
    public final long deleteAll() {
        CriteriaDelete<E> criteria = builder.createCriteriaDelete(entityClass);
        criteria.from(entityClass);
        return manager.createQuery(criteria).executeUpdate();
    }

    @Override
    public final void update(E e) {
        Assert.notNull(e, "entity can't be null");
        K k = id(e);
        Assert.notNull(k, "id can't be null");//k > 0
        this.merge(e);
    }

    @Override
    public final void merge(E e) {
        Assert.notNull(e, "entity can't be null");
        manager.merge(e);
    }

    @Override
    public final boolean contains(E e) {
        return e != null && manager.contains(e);
    }

    @Override
    public final E findById(K k) {
        Assert.notNull(k, "id can't be null");
        return manager.find(entityClass, k);
    }

    @Override
    public final List<E> findList() {
        CriteriaQuery<E> criteria = builder.createQuery(entityClass);
        criteria.from(entityClass);
        return manager.createQuery(criteria).getResultList();
    }

    @Override
    public final long count() {
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        criteria.select(builder.count(criteria.from(entityClass)));
        return manager.createQuery(criteria).getSingleResult();
    }
//
//    /*------------------the following is setting options for criteria------------------*/
//    public final <T, X> CriteriaQuery<T> sort(CriteriaQuery<T> criteria, Root<X> root, Sort sort) {
//        sort = sort == null ? Sort.of(idColumn, Rule.DESC) : sort;
//        return Sorts.sort(builder, criteria, root, sort);
//    }
//
//    /*------------------the following is setting query before return------------------*/
//
//    public final <T> T find(TypedQuery<T> query) {
//        return query.getResultList().stream().findFirst().orElse(null);
//    }
//
//    public final <T> List<T> findList(TypedQuery<T> query, Page page) {
//        if (page != null) {
//            query.setFirstResult(page.offset()).setMaxResults(page.size());
//        }
//        return query.getResultList();
//    }
//
//    /*------------------the following is single query template by callback------------------*/
//
//    public final E find(SingleQuery<E> callback) {
//        return callback.find(manager, entityClass);
//    }
//
//    public final List<E> findList(Page page, List<Sort> sorts, SingleQuery<E> callback) {
//        return callback.findList(manager, entityClass, page, sorts);
//    }
//
//    public final List<E> findList(Page page, Sort sort, SingleQuery<E> callback) {
//        return callback.findList(manager, entityClass, page, sort);
//    }
//
//    public final long count(SingleCount<E> callback) {
//        return callback.count(manager, entityClass);
//    }
//
//    /*------------------the following is multi query template by callback------------------*/
//
//    public final <R> R find(Class<R> result, QueryCallback<R, E> callback) {
//        return callback.find(manager, result, entityClass);
//    }
//
//    public final <R> List<R> findList(Page page, List<Sort> sorts, Class<R> result, QueryCallback<R, E> callback) {
//        return callback.findList(manager, result, entityClass, page, sorts);
//    }
//
//    public final <R> List<R> findList(Page page, Sort sort, Class<R> result, QueryCallback<R, E> callback) {
//        return callback.findList(manager, result, entityClass, page, sort);
//    }
//
//    //count(F),allow E base
//    @Deprecated
//    public final <F> long count(Class<F> clazz, CountCallback<F> callback) {
//        return callback.count(manager, clazz);
//    }

}
