package com.cat.persistence.build;

import com.cat.entity.Gateway;
import com.cat.entity.TenantLock;
import com.cat.entity.auth.User;
import com.cat.entity.auth.UserGateway;
import com.cat.persistence.mapping.ColumnMapping;
import com.cat.persistence.mapping.EntityMapping;
import com.cat.persistence.mapping.EntityMappingManager;
import com.cat.repository.CommonRepository;
import com.cat.repository.impl.CommonRepositoryImpl;
import com.cat.service.CommonService;
import com.cat.service.impl.CommonServiceImpl;
import com.cat.util.FileUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class FileBuilder {
    //common class
    private static final Class<?> COMMON_REPOSITORY = CommonRepository.class;
    private static final Class<?> COMMON_REPOSITORY_IMPL = CommonRepositoryImpl.class;
    private static final Class<?> COMMON_SERVICE = CommonService.class;
    private static final Class<?> COMMON_SERVICE_IMPL = CommonServiceImpl.class;
    //path
    private static final String ROOT_DIR = FileUtils.getRootPath() + "/src/main/java/";
    private static final String RESOURCES_DIR = FileUtils.getRootPath() + "/src/main/resources/";
    //xml
    private static final String MAPPING_DIR = "mapper";
    private static final String ENTITY_PREFIX = "entity";
    private static final String COLUMN_SEPARATOR = ", ";
    private static final String UN_INSERTABLE_VALUE = "NULL";

    private static String columns(List<ColumnMapping> mappings) {
        List<String> columnList = mappings.stream().map(ColumnMapping::getColumn).collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(columnList, COLUMN_SEPARATOR);
    }

    private static String expression(List<ColumnMapping> mappings, String loopPrefix) {
        final String prefix = StringUtils.hasText(loopPrefix) ? loopPrefix + "." : "";
        List<String> columnList = mappings.stream().map(mapping -> mapping.isInsertable() ? "#{" + prefix + mapping.getField() + "}" : UN_INSERTABLE_VALUE).collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(columnList, COLUMN_SEPARATOR);
    }

    //赋值
    private static String assignment(List<ColumnMapping> mappings) {
        List<String> columnList = mappings.stream()
                .filter(ColumnMapping::isUpdatable)
                .map(mapping -> mapping.getColumn() + " = #{" + mapping.getField() + "}")
                .collect(Collectors.toList());
        return StringUtils.collectionToDelimitedString(columnList, COLUMN_SEPARATOR);
    }

    public static void main(String[] args) {
        Set<Class<?>> classes = FileUtils.scanPackage(User.class.getPackage().getName());
        classes.stream().filter(
                clazz -> clazz != TenantLock.class && clazz != UserGateway.class && EntityMappingManager.from(clazz) != null
        ).forEach(clazz -> create(clazz, true));
//        create(Token.class, true);
        createXml(Gateway.class, true);
    }

    private static void create(Class<?> clazz, boolean cover) {
        createXml(clazz, cover);
        createRepository(clazz, cover);
        createService(clazz, cover);
    }

    private static void createFile(String packageName, String content, boolean cover) {
        Path path = Paths.get(ROOT_DIR, StringUtils.replace(packageName, ".", "/") + ".java");
        FileUtils.write(path, content, cover);
    }

    private static void createService(Class<?> clazz, boolean cover) {
        String packageName = COMMON_SERVICE.getPackage().getName();
        String implPackageName = COMMON_SERVICE_IMPL.getPackage().getName();
        //impl
        String impl = implPackageName.replace(packageName, "").replace(".", "");
        //service
        String suffix = packageName.substring(packageName.lastIndexOf('.') + 1);
        //Entity
        String entityName = clazz.getSimpleName();

        String supClassName = entityName + StringUtils.capitalize(suffix);
        String supPackageName = packageName + "." + supClassName;

        String subClassName = supClassName + StringUtils.capitalize(impl);
        String subPackageName = implPackageName + "." + subClassName;

//        String genericType = "<" + entityName + ", " + Long.class.getSimpleName() + ">";

        //super service
        StringBuilder builder = new StringBuilder("package ").append(packageName).append(";\n\n");
        builder.append("public interface ").append(supClassName).append(" {\n}");

        createFile(supPackageName, builder.toString(), cover);

        //sub service
        builder = new StringBuilder("package ").append(implPackageName).append(";\n\n");
        builder.append("import ").append(supPackageName).append(";\n")//import
                .append("import ").append(Service.class.getName()).append(";\n\n")//import

                .append("@").append(Service.class.getSimpleName()).append("\n")//@Repository

                .append("public class ").append(subClassName)//class
                .append(" implements ").append(supClassName)//implements
                .append(" {\n}");

        createFile(subPackageName, builder.toString(), cover);
    }

    private static void createRepository(Class<?> clazz, boolean cover) {
        String packageName = COMMON_REPOSITORY.getPackage().getName();
        String implPackageName = COMMON_REPOSITORY_IMPL.getPackage().getName();
        //impl
        String impl = implPackageName.replace(packageName, "").replace(".", "");
        //repository
        String suffix = packageName.substring(packageName.lastIndexOf('.') + 1);
        //Entity
        String entityName = clazz.getSimpleName();

        String supClassName = entityName + StringUtils.capitalize(suffix);
        String supPackageName = packageName + "." + supClassName;

        String subClassName = supClassName + StringUtils.capitalize(impl);
        String subPackageName = implPackageName + "." + subClassName;

        String genericType = "<" + entityName + ", " + Long.class.getSimpleName() + ">";

        //super class
        StringBuilder builder = new StringBuilder("package ").append(packageName).append(";\n\n");
        builder.append("import ").append(clazz.getName()).append(";\n\n")//import
                .append("public interface ").append(supClassName)//class
                .append(" extends ").append(COMMON_REPOSITORY.getSimpleName()).append(genericType).append(" {\n}");//extends

        createFile(supPackageName, builder.toString(), cover);

        //sub class
        builder = new StringBuilder("package ").append(implPackageName).append(";\n\n");
        builder.append("import ").append(clazz.getName()).append(";\n")//import
                .append("import ").append(supPackageName).append(";\n")//import
                .append("import ").append(Repository.class.getName()).append(";\n\n")//import

                .append("@").append(Repository.class.getSimpleName()).append("\n")//@Repository

                .append("public class ").append(subClassName)//class
                .append(" extends ").append(COMMON_REPOSITORY_IMPL.getSimpleName()).append(genericType)//extends
                .append(" implements ").append(supClassName)//implements
                .append(" {\n}");

        createFile(subPackageName, builder.toString(), cover);
    }

    private static void createXml(Class<?> clazz, boolean cover) {
        Path path = Paths.get(RESOURCES_DIR, MAPPING_DIR + "/" + clazz.getSimpleName() + ".xml");
        String xmlContent = createXmlContent(clazz);
        FileUtils.write(path, xmlContent, cover);
    }

    private static String createXmlContent(Class<?> clazz) {
        EntityMapping mapping = EntityMappingManager.from(clazz);
        if (mapping == null) {
            System.out.println(clazz.getSimpleName() + " :entity can't be null");
            return null;
        }

        String entity = mapping.getTableMapping().getClazz().getSimpleName();
        String table = mapping.getTableMapping().getTable();
        ColumnMapping idColumnMapping = mapping.getIdColumnMapping();
        //TODO:
        String idField = idColumnMapping == null ? "" : idColumnMapping.getField();// idColumn = idColumnMapping.getColumn();

        List<ColumnMapping> mappings = mapping.getColumnMappings();
        String columns = columns(mappings);
        String insertableColumns = expression(mappings, null);
        String insertableLoopColumns = expression(mappings, ENTITY_PREFIX);
        String updatableColumns = assignment(mapping.getColumnMappings().stream().filter(ColumnMapping::isUpdatable).collect(Collectors.toList()));

        StringBuilder mappingBuilder = new StringBuilder("    <resultMap id=\"" + entity + "\" type=\"" + entity + "\">\n");
        mappings.forEach(columnMapping -> mappingBuilder.append("        <")
                .append(columnMapping.isPrimary() ? "id" : "result")
                .append(" property=\"")
                .append(columnMapping.getField())
                .append("\" column=\"")
                .append(columnMapping.getColumn())
                .append("\"/>\n"));
        mappingBuilder.append("    </resultMap>\n");

        String packageName = COMMON_REPOSITORY.getPackage().getName();
        //repository
        String suffix = packageName.substring(packageName.lastIndexOf('.') + 1);

        return
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                        "<mapper namespace=\"" + packageName + "." + entity + StringUtils.capitalize(suffix) + "\">\n" +
                        "    <sql id=\"table\">" + table + "</sql>\n" +
                        "    <sql id=\"key\">" + idField + "</sql>\n" +
                        "    <sql id=\"columns\">" + columns + "</sql>\n" +
                        "    <sql id=\"sort\">\n" +
                        "        <if test=\"sort != null and !sort.isEmpty() and order != null and !order.isEmpty()\">\n" +
                        "            ORDER BY ${sort} ${order}\n" +
                        "        </if>\n" +
                        "    </sql>\n" +
                        "    <sql id=\"page\">\n" +
                        "        <if test=\"offset >= 0 and limit > 0\">\n" +
                        "            LIMIT #{offset}, #{limit}\n" +
                        "        </if>\n" +
                        "    </sql>\n" +
                        "\n" +
                        mappingBuilder +
                        "\n" +
                        "    <insert id=\"save\" keyProperty=\"" + idField + "\">\n" +
                        "        INSERT INTO\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        (<include refid=\"columns\"/>)\n" +
                        "        VALUES\n" +
                        "        (" + insertableColumns + ")\n" +
                        "    </insert>\n" +
                        "\n" +
                        "    <insert id=\"saves\" keyProperty=\"" + idField + "\">\n" +
                        "        INSERT INTO\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        (<include refid=\"columns\"/>)\n" +
                        "        VALUES\n" +
                        "        <foreach collection=\"list\" item=\"" + ENTITY_PREFIX + "\" separator=\",\">\n" +
                        "            (" + insertableLoopColumns + ")\n" +
                        "        </foreach>\n" +
                        "    </insert>\n" +
                        "\n" +
                        "    <delete id=\"deleteById\">\n" +
                        "        DELETE FROM\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        WHERE\n" +
                        "        <include refid=\"key\"/>\n" +
                        "        = #{id}\n" +//接口arg名称
                        "    </delete>\n" +
                        "\n" +
                        "    <delete id=\"deleteByEntity\">\n" +
                        "        DELETE FROM\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        WHERE\n" +
                        "        <include refid=\"key\"/>\n" +
                        "        = #{" + idField + "}\n" +
                        "    </delete>\n" +
                        "\n" +
                        "    <delete id=\"deleteByIds\">\n" +
                        "        DELETE FROM\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        WHERE\n" +
                        "        <include refid=\"key\"/>\n" +
                        "        IN\n" +
                        "        <foreach collection=\"array\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
                        "            #{id}\n" +
                        "        </foreach>\n" +
                        "    </delete>\n" +
                        "\n" +
                        "    <delete id=\"deleteByEntities\">\n" +
                        "        DELETE FROM\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        WHERE\n" +
                        "        <include refid=\"key\"/>\n" +
                        "        IN\n" +
                        "        <foreach collection=\"list\" item=\"" + ENTITY_PREFIX + "\" open=\"(\" separator=\",\" close=\")\">\n" +
                        "            #{" + ENTITY_PREFIX + "." + idField + "}\n" +
                        "        </foreach>\n" +
                        "    </delete>\n" +
                        "\n" +
                        "    <update id=\"update\">\n" +
                        "        UPDATE\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        SET\n" +
                        "        " + updatableColumns + "\n" +
                        "        WHERE\n" +
                        "        <include refid=\"key\"/>\n" +
                        "        = #{id}\n" +
                        "    </update>\n" +
                        "\n" +
                        "    <select id=\"getById\" resultMap=\"" + entity + "\">\n" +
                        "        SELECT * FROM\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        WHERE\n" +
                        "        <include refid=\"key\"/>\n" +
                        "        = #{id}\n" +
                        "    </select>\n" +
                        "\n" +
                        "    <select id=\"list\" resultMap=\"" + entity + "\">\n" +
                        "        SELECT * FROM\n" +
                        "        <include refid=\"table\"/>\n" +
                        "        <include refid=\"sort\"/>\n" +
                        "        <include refid=\"page\"/>\n" +
                        "    </select>\n" +
                        "\n" +
                        "    <select id=\"count\" resultType=\"int\">\n" +
                        "        SELECT COUNT(*) FROM\n" +
                        "        <include refid=\"table\"/>\n" +
                        "    </select>\n" +
                        "\n" +
                        "    <sql id=\"search\">\n" +
                        "        <where>\n" +
                        "            <if test=\"\">\n" +
                        "            </if>\n" +
                        "        </where>\n" +
                        "    </sql>\n" +
                        "\n" +
                        "</mapper>";

    }

}
