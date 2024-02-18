## 使用ElasticSearch优化的部分
- 商品的搜索
- 更具分类，属性，商品名，搜索
## 如何同步ES、Redis、Mysql数据库
- 可使用Canal+消息对列 
- 在MySql修改后，同步更新ES与Redis中的数据

## 版本太高怎么办？
- 降低es版本
- 降到当前 Spring 版本推荐的 8.7.1
```shell
docker run \
-p 9201:9200 -p 9301:9300 \
--name elasticsearch8.7.1 \
--restart=always \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms256m -Xmx256m" \
-v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
-v /mydata/elasticsearch/config:/usr/share/elasticsearch/configCP \
-d elasticsearch:8.7.1
```
- kibana8.7.1安装
```shell
docker run --name kibana --restart=always -e ELASTICSEARCH_URL=http://jfl.kiroe.cn:9201 -p 5602:5601 -d kibana:8.7.1
```

#
# ** THIS IS AN AUTO-GENERATED FILE **
#

# Default Kibana configuration for docker target
server.host: "0.0.0.0"
server.shutdownTimeout: "5s"
elasticsearch.hosts: [ "http://jfl.kiroe.cn:9201" ]

