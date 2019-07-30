package com.maple;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by badado on 2019/4/25.
 */
public class MybatisPlusGenerator {

    public static void main(String[] args){

        String auth = "maple";
        String packageName = "com.maple.demo";
        String path = "d:";
        String url = "127.0.0.1:3306/maple";
        String username = "root";
        String password = "123456";

        String authSc = scanner("请输入生成代码的作者，（默认maple）：");
        if(StringUtils.isNotEmpty(authSc)){
            auth = authSc;
        }

        String packageNameSc = scanner("请输入项目包名，例如（com.maple.demo），默认com.maple.demo：");
        if(StringUtils.isNotEmpty(packageNameSc)){
            packageName = packageNameSc;
        }

        String pathSc = scanner("请输入存放路径, (默认D:)：");
        if(StringUtils.isNotEmpty(pathSc)){
            path = pathSc;
        }

        String sqlInfo = scanner("请输入数据库连接信息，必须为以下格式(连接地址:端口号/数据库名称&用户名&密码)（默认：127.0.0.1:3306/maple&root&123456）：");
        if(StringUtils.isNotEmpty(sqlInfo)){
            String[] drivers = sqlInfo.split("&");
            if(StringUtils.isNotEmpty(drivers[0])){
                url = drivers[0];
            }
            if(StringUtils.isNotEmpty(drivers[1])){
                username = drivers[1];
            }
            if(StringUtils.isNotEmpty(drivers[2])){
                password = drivers[2];
            }
        }

        String allInfo = "作者：" + auth
                + "\n包名：" + packageName
                + "\n存放路径：" + path
                + "\n数据连接：" + url
                + "\n数据库用户名：" + username
                + "\n数据库密码：" + password;

        System.out.println(allInfo);

        String code = scanner("确认生成请输入'Y',取消请回车");
        if(code.toUpperCase().equals("Y")){
            generateTest(auth, packageName, path, url, username, password);
        }
    }

    public static void generateTest(String auth, String packageName, String path,
                                    String url, String username, String password){
        //全局配置
        GlobalConfig config = new GlobalConfig();
        //设置是否支持AR模式
        config.setActiveRecord(true)
                //设置生成代码的作者
                .setAuthor(auth)
                //设置输出代码的位置
                .setOutputDir(path)
                //.setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                //.setKotlin(true) 是否生成 kotlin 代码
                //设置是否覆盖原来的代码
                .setSwagger2(true)
                .setFileOverride(true);

        //******************************数据源配置***************************************
        //数据库连接url
        String dbUrl = "jdbc:mysql://" + url + "?characterEncoding=utf-8";
        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        //数据库类型 枚举
        dataSourceConfig.setDbType(DbType.MYSQL)
                //设置url
                .setUrl(dbUrl)
                //设置用户名
                .setUsername(username)
                //设置密码
                .setPassword(password)
                //设置数据库驱动
                .setDriverName("com.mysql.cj.jdbc.Driver")
                // 自定义数据库表字段类型转换【可选】
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        //将数据库中datetime转换成date
                        if ( fieldType.toLowerCase().contains( "datetime" ) ) {
                            return DbColumnType.DATE;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });

        //******************************策略配置******************************************************
        // 自定义需要填充的字段 数据库中的字段
        List<TableFill> tableFillList = new ArrayList<>();
//        tableFillList.add(new TableFill("gmt_modified", FieldFill.INSERT_UPDATE));
//        tableFillList.add(new TableFill("modifier_id", FieldFill.INSERT_UPDATE));
//        tableFillList.add(new TableFill("creator_id", FieldFill.INSERT));
//        tableFillList.add(new TableFill("gmt_creat", FieldFill.INSERT));
//        tableFillList.add(new TableFill("available_flag", FieldFill.INSERT));
//        tableFillList.add(new TableFill("deleted_flag", FieldFill.INSERT));
//        tableFillList.add(new TableFill("sync_flag", FieldFill.INSERT));
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                //全局大写命名是否开启
                .setCapitalMode(true)
                //【实体】是否为lombok模型
                .setEntityLombokModel(false)
                //表名生成策略  下划线转驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                //生成的去掉以system，可以配置多个
                .setTablePrefix(new String[]{"system_"})
                //自动填充设置
                .setTableFillList(tableFillList)
                //修改替换成你需要的表名，多个表名传数组
                .setInclude(".*.");
        //集成注入设置
        //注入全局设置
        new AutoGenerator().setGlobalConfig(config)
                //注入数据源配置
                .setDataSource(dataSourceConfig)
                //注入策略配置
                .setStrategy(strategyConfig)
                //设置包名信息
                .setPackageInfo(
                        new PackageConfig()
                                //提取公共父级包名
                                .setParent(packageName)
                                //设置controller信息
                                .setController("controller")
                                //设置实体类信息
                                .setEntity("bean")
                                .setMapper("dao")
                                .setXml("mapper")
                )
                //设置自定义模板
                .setTemplate(
                        new TemplateConfig()
                                //.setXml(null)//指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
                                //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
                                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                                // .setController("...");
                                // .setEntity("...");
                                // .setMapper("...");
                                // .setXml("...");
                                // .setService("...");
                                .setServiceImpl("templates/serviceImpl.java")
                )
                //开始执行代码生成
                .execute();
    }


    public static String scanner(String param){
        Scanner scanner = new Scanner(System.in);
        System.out.println(param);
        return scanner.nextLine();
    }
}
