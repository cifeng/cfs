package com.platform.cfs.config.generator;


import com.platform.cfs.entity.SystemDictionaries;
import com.platform.cfs.entity.SystemUser;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName GeneratorSQL
 * @Description 生成SQL语句帮助类
 * @author 刘非
 * @date 2019-01-22 16:39
 */
public final class GeneratorSQL {

    private GeneratorSQL() {
    }

    //生成where 的名称
    private static String id = "Base_Where_Clause";

    //如果想要生成日期类型的字段 则开启它 设置为true
    private static boolean dateFlag = false;

    //如果返回主键 则开启它 设置为true
    private static boolean useGeneratedKeys = false;

    //新增时是否忽略ID
    private static boolean insertIdFlag = false;
    //表名
    private static String tableName = "system_user";


    public static void main(String[] args) throws Exception {
        SystemUser obj = new SystemUser();
        // System.out.println("**************生成where条件sql*****************************************\n");
        reflectWhere(obj);
        // System.out.println("**************生成插入SQL语句*******************************************\n");
        reflectInsert(obj);
        // System.out.println("**************生成修改SQL语句*******************************************\n");
        reflectUpdate(obj);

    }

    public static void reflectWhere(Object object) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append("<sql id=\"" + id + "\">\n");
        sb.append(" <where>\n");
        sb.append("     1=1\n");
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String typeName = field.getType().getName();
            String dataName = getDataName(fieldName);

            if (Type.INTEGER.getName().equals(typeName) || Type.LONG.getName().equals(typeName)
                    || Type.FLOAT.getName().equals(typeName)
                    || Type.DOUBLE.getName().equals(typeName)
                    || Type.DECIMAL.getName().equals(typeName)
                    || Type.INT.getName().equals(typeName)
                    || Type.REAL.getName().equals(typeName)) {
                sb.append("     <if test=\"" + fieldName + " != null \" > \n");
                sb.append("            and " + dataName + " = #{" + fieldName + "} \n");
                sb.append("     </if>\n");
            } else if (Type.STR.getName().equals(typeName)) {
                sb.append("     <if test=\"" + fieldName + " != null and " + fieldName + " !=''\" > \n");
                sb.append("            and " + dataName + " = #{" + fieldName + "}\n");
                sb.append("     </if>\n");
            } else if (Type.DATE.getName().equals(typeName) || Type.TIMESTAMP.getName().equals(typeName)) {
                if (!dateFlag) {
                    //如果设置了不生成日期类型的字段直接跳过
                    continue;
                }
                if ("createTime".equals(fieldName)) {
                    sb.append("     <if test=\" startTime != null \" >\n");
                    sb.append("            and " + dataName + " <![CDATA[>=]]> #{startTime} \n");
                    sb.append("     </if>\n");
                    sb.append("     <if test=\" endTime != null \" >\n");
                    sb.append("            and " + dataName + " <![CDATA[<=]]> #{endTime} \n");
                    sb.append("     </if>\n");
                    continue;
                }
                sb.append("     <if test=\"" + fieldName + " != null \" >\n");
                sb.append("            and " + dataName + " <![CDATA[>=]]> #{" + fieldName + "} \n");
                sb.append("            and " + dataName + " <![CDATA[<=]]> #{" + fieldName + "} \n");
                sb.append("     </if>\n");


            } else {
                out(fieldName);
            }

        }
        sb.append(" </where>\n");
        sb.append("</sql>\n");
        System.out.println(sb);

    }


    public static void reflectInsert(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String className = object.getClass().getName();
        StringBuilder sb = new StringBuilder();
        String useStr = useGeneratedKeys ? "useGeneratedKeys=\"true\" keyProperty=\"id\" " : "";
        sb.append("<insert id=\"save\" " + useStr + " parameterType=\"" + className + "\" >\n");
        sb.append("   insert into  ");
        sb.append(tableName);
        sb.append("\n");
        sb.append("   <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n");
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String typeName = field.getType().getName();
            String dataName = getDataName(fieldName);
            if (insertIdFlag) {
                if ("id".equals(fieldName)) {
                    continue;
                }
            }

            if (Type.INTEGER.getName().equals(typeName) || Type.LONG.getName().equals(typeName)
                    || Type.FLOAT.getName().equals(typeName)
                    || Type.DOUBLE.getName().equals(typeName)
                    || Type.DATE.getName().equals(typeName)
                    || Type.DECIMAL.getName().equals(typeName)
                    || Type.TIMESTAMP.getName().equals(typeName)
                    || Type.INT.getName().equals(typeName)
                    || Type.REAL.getName().equals(typeName)) {

                sb.append("     <if test=\"" + fieldName + " != null \" > \n");
                sb.append("            " + dataName + ", \n");
                sb.append("     </if>\n");
            } else if (Type.STR.getName().equals(typeName)) {
                sb.append("     <if test=\"" + fieldName + " != null and " + fieldName + " !=''\" > \n");
                sb.append("            " + dataName + ", \n");
                sb.append("     </if>\n");
            } else {
                out(fieldName);
            }

        }
        sb.append("   </trim>\n");
        sb.append("   <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n");
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String typeName = field.getType().getName();
            String dataName = getDataName(fieldName);
            String jdbcType = getJdbcType(typeName);
            if (insertIdFlag) {
                if ("id".equals(fieldName)) {
                    continue;
                }
            }
            if (Type.INTEGER.getName().equals(typeName) || Type.LONG.getName().equals(typeName)
                    || Type.FLOAT.getName().equals(typeName)
                    || Type.DOUBLE.getName().equals(typeName)
                    || Type.DATE.getName().equals(typeName)
                    || Type.DECIMAL.getName().equals(typeName)
                    || Type.TIMESTAMP.getName().equals(typeName)
                    || Type.INT.getName().equals(typeName)
                    || Type.REAL.getName().equals(typeName)) {
                sb.append("     <if test=\"" + fieldName + " != null \" > \n");
                sb.append("            #{" + fieldName + ",jdbcType=" + jdbcType + "}, \n");
                sb.append("     </if>\n");
            } else if (Type.STR.getName().equals(typeName)) {
                sb.append("     <if test=\"" + fieldName + " != null and " + fieldName + " !=''\" > \n");
                sb.append("            #{" + fieldName + ",jdbcType=" + jdbcType + "}, \n");
                sb.append("     </if>\n");
            } else {
                out(fieldName);
            }

        }
        sb.append("   </trim>\n");
        sb.append("</insert>\n");

        System.out.println(sb);

    }

    public static void reflectUpdate(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        String className = object.getClass().getName();
        StringBuilder sb = new StringBuilder();
        sb.append("<update id=\"update\" parameterType=\"" + className + "\" >\n");
        sb.append("  update  ");
        sb.append(tableName);
        sb.append("\n");
        sb.append("   <set>\n");
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String typeName = field.getType().getName();
            String dataName = getDataName(fieldName);
            String jdbcType = getJdbcType(typeName);
            if ("id".equals(fieldName)) {
                continue;
            }
            if (Type.INTEGER.getName().equals(typeName) || Type.LONG.getName().equals(typeName)
                    || Type.FLOAT.getName().equals(typeName)
                    || Type.DOUBLE.getName().equals(typeName)
                    || Type.DATE.getName().equals(typeName)
                    || Type.DECIMAL.getName().equals(typeName)
                    || Type.TIMESTAMP.getName().equals(typeName)
                    || Type.INT.getName().equals(typeName)
                    || Type.REAL.getName().equals(typeName)) {
                sb.append("     <if test=\"" + fieldName + " != null \" > \n");
                sb.append("           " + dataName + " = #{" + fieldName + ",jdbcType=" + jdbcType + "},\n");
                sb.append("     </if>\n");
            } else if (Type.STR.getName().equals(typeName)) {
                sb.append("     <if test=\"" + fieldName + " != null and " + fieldName + " !=''\" > \n");
                sb.append("           " + dataName + " = #{" + fieldName + ",jdbcType=" + jdbcType + "},\n");
                sb.append("     </if>\n");
            } else {
                out(fieldName);
            }

        }
        sb.append("   </set>\n");
        sb.append("   where id = #{id}\n");
        sb.append("</update>\n");

        System.out.println(sb);

    }


    private static String getJdbcType(String typeName) {
        String jdbcType = "";
        if (Type.INTEGER.getName().equals(typeName)) {
            jdbcType = Type.INTEGER.getJdbcType();
        } else if (Type.LONG.getName().equals(typeName)) {
            jdbcType = Type.LONG.getJdbcType();
        } else if (Type.FLOAT.getName().equals(typeName)) {
            jdbcType = Type.FLOAT.getJdbcType();
        } else if (Type.DOUBLE.getName().equals(typeName)) {
            jdbcType = Type.DOUBLE.getJdbcType();
        } else if (Type.STR.getName().equals(typeName)) {
            jdbcType = Type.STR.getJdbcType();
        } else if (Type.DATE.getName().equals(typeName)) {
            jdbcType = Type.DATE.getJdbcType();
        } else if (Type.DECIMAL.getName().equals(typeName)) {
            jdbcType = Type.DECIMAL.getJdbcType();
        } else if (Type.TIMESTAMP.getName().equals(typeName)) {
            jdbcType = Type.TIMESTAMP.getJdbcType();
        } else if (Type.INT.getName().equals(typeName)) {
            jdbcType = Type.INT.getJdbcType();
        } else if (Type.REAL.getName().equals(typeName)) {
            jdbcType = Type.REAL.getJdbcType();
        }
        return jdbcType;
    }


    public static String getDataName(String param) {
        Pattern p = Pattern.compile("[A-Z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
            builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    public static void out(String fieldName) {
        if ("serialVersionUID".equals(fieldName)) {
            return;
        }
        System.out.println("======================有未出现的类型，需要修改生成类了===================" + fieldName);
    }

    /**
     * @ClassName Type
     * @Description java包名加类名和数据库映射关系
     * @author 刘非
     * @date 2019-01-22 16:40
     */
    private enum Type {
        INTEGER("java.lang.Integer", "INTEGER"),
        INT("int", "INTEGER"),
        LONG("java.lang.Long", "BIGINT"),
        FLOAT("java.lang.Double", "FLOAT"),
        DOUBLE("java.lang.Double", "DOUBLE"),
        STR("java.lang.String", "VARCHAR"),
        DATE("java.util.Date", "TIMESTAMP"),
        DECIMAL("java.math.BigDecimal", "DECIMAL"),
        TIMESTAMP("java.sql.Timestamp", "TIMESTAMP"),
        REAL("java.lang.Float", "REAL");

        private String name;
        private String jdbcType;

        Type(String name, String jdbcType) {
            this.name = name;
            this.jdbcType = jdbcType;

        }

        public String getName() {
            return name;
        }

        public String getJdbcType() {
            return jdbcType;
        }

    }


}
