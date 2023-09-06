package com.saleswift.mybatis; /**
 * @author Ansel Zhong
 * coding time
 */



import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 @title mybatis-plus-01
 @author Ansel Zhong
 @Date 2023/2/22
 @Description
 */
public class FastAutoGeneratorTest {
  public static void main(String[] args) {
              FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/saleswift_dev?serverTimezone=GMT%2B8&characterEncoding=utf-8&userSSL=false", "root", "root").globalConfig(builder -> {
              builder.author("ansel") // 设置作者
               //.enableSwagger() // 开启 swagger 模式
                      .fileOverride() // 覆盖已生成文件
                      .outputDir("D:\\Project-Java\\project-SaleSwift\\SaleSwift-Backend\\src\\test\\java\\com\\saleswift"); // 指定输出目录
            })
            .packageConfig(builder -> {
              builder.parent("com.saleswift") // 设置父包名
                      .moduleName("") // 设置父包模块名
                      .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\Project-Java\\project-SaleSwift\\SaleSwift-Backend\\src\\test\\java\\com\\saleswift"));
// 设置mapperXml生成路径
            })
            .strategyConfig(builder -> {
              builder.addInclude("swift_order") // 设置需要生成的表名
                      .addTablePrefix("t_", "c_"); // 设置过滤表前缀
            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker 引擎模板，默认的是Velocity引擎模板
            .execute();
  }
  }

