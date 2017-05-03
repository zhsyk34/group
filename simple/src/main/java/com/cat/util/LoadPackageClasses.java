package com.cat.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

public class LoadPackageClasses {

    private static final String RESOURCE_PATTERN = "/**/*.class";

    private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private List<String> packages = new LinkedList<>();

    private List<TypeFilter> typeFilters = new LinkedList<>();

    private Set<Class<?>> classSet = new HashSet<>();

    public LoadPackageClasses(String[] packagesToScan, Class<? extends Annotation>... annotationFilter) {
        Optional.ofNullable(packagesToScan).map(Arrays::stream).ifPresent(stream -> stream.forEach(packageName -> this.packages.add(packageName)));
        Optional.ofNullable(annotationFilter).map(Arrays::stream).ifPresent(stream -> stream.map(annotation -> new AnnotationTypeFilter(annotation, false)).forEach(filter -> this.typeFilters.add(filter)));
    }

    public Set<Class<?>> getClassSet() throws IOException, ClassNotFoundException {
        this.classSet.clear();
        if (!this.packages.isEmpty()) {
            for (String pkg : this.packages) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
                Resource[] resources = resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        if (matchesEntityTypeFilter(reader, readerFactory)) {
                            this.classSet.add(Class.forName(className));
                        }
                    }
                }
            }
        }
        return this.classSet;
    }

    private boolean matchesEntityTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
        if (!this.typeFilters.isEmpty()) {
            for (TypeFilter filter : this.typeFilters) {
                if (filter.match(reader, readerFactory)) {
                    return true;
                }
            }
        }
        return false;
    }

}  