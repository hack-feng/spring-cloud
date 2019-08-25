### ELK统一日志系统搭建

ELK是Elasticsearch、Logstash、Kibana的简称，这三者是核心套件，但并非全部。

Elasticsearch是实时全文搜索和分析引擎，提供搜集、分析、存储数据三大功能；是一套开放REST和JAVA API等结构提供高效搜索功能，可扩展的分布式系统。它构建于Apache Lucene搜索引擎库之上。

Logstash是一个用来搜集、分析、过滤日志的工具。它支持几乎任何类型的日志，包括系统日志、错误日志和自定义应用程序日志。它可以从许多来源接收日志，这些来源包括 syslog、消息传递（例如 RabbitMQ）和JMX，它能够以多种方式输出数据，包括电子邮件、websockets和Elasticsearch。

Kibana是一个基于Web的图形界面，用于搜索、分析和可视化存储在 Elasticsearch指标中的日志数据。它利用Elasticsearch的REST接口来检索数据，不仅允许用户创建他们自己的数据的定制仪表板视图，还允许他们以特殊的方式查询和过滤数据

项目使用版本（基于Linux系统搭建）：
* elasticsearch-7.3.0

下载地址：https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.3.0-linux-x86_64.tar.gz

* kibana-7.3.0

下载地址：https://artifacts.elastic.co/downloads/kibana/kibana-7.3.0-linux-x86_64.tar.gz

* logstash-7.3.0

下载地址：https://artifacts.elastic.co/downloads/logstash/logstash-7.3.0.tar.gz

## Elasticsearch搭建

搭建过程：https://blog.csdn.net/qq_34988304/article/details/100058049

## Logstash搭建

## Kibana搭建