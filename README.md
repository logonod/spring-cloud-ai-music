
# 基于微服务的AI翻唱项目

> 最近发现了一个很好的AI翻唱项目So-VITS-SVC，可以通过GAN生成各种歌曲的音乐翻唱，为了学习Spring Cloud和Java，所以完成了这个项目，使用网关来统一校验认证和鉴权。目前完成了队列生产消费、后端接口、Gateway JWT鉴权、登录注册服务、前端页面，希望对大家有所帮助！
> Spring Cloud Gateway实现统一认证和鉴权 + Nacos服务发现配置管理+ Mysql数据库 + RabbitMq消息队列 + Openfeign服务调用 + Spring Cloud Stream生产消费 + JWT鉴权 + Vue前端 + So-VITS-SVC翻唱AI！

## 软件依赖
- Nacos 2.0.3
- JDK 1.8
- spring-cloud-dependencies 2020.0.1
- spring-cloud-alibaba-dependencies 2021.1
- Mysql 8.0.33
- RabbitMq 3.12.0

## 应用截图

> 主要要以下几个功能：登陆注册、创建翻唱任务、查看任务状态、听歌、随便听听。
![screen](http://asset.liuzeyu.me/music-screen1.png)
![screen](http://asset.liuzeyu.me/music-screen2.png)
![screen](http://asset.liuzeyu.me/music-screen3.png)
![screen](http://asset.liuzeyu.me/music-screen4.png)

## 服务划分

> 认证鉴权使用JWT，认证服务负责登录注册对应auth-serv，网关负责校验认证和鉴权，music-serv负责音乐相关的各种接口，spleeter-serv、svc-serv、ffmpeg-serv对应Spring Cloud Stream的生产和消费功能。安全相关的逻辑只存在于认证服务和网关服务中，其他服务只是单纯地提供服务而没有任何安全相关逻辑。

相关服务划分：

- middleware/gateway-serv：网关服务，负责请求转发和鉴权功能；
- auth-serv：认证服务，负责对登录用户进行登录注册，发放JWT令牌，整合Spring Security；
- music-serv：受保护的API服务，用户鉴权通过后可以访问音乐服务；
- spleeter-serv：负责干声伴奏分离服务，整合Spring Cloud Stream生产消费RabbitMq消息；
- svc-serv：负责调用Python干声翻唱服务，整合Spring Cloud Stream生产消费RabbitMq消息；
- ffmpeg-serv：负责调用ffmpeg干声伴奏合成服务，整合Spring Cloud Stream接收RabbitMq消息；

相关页面划分：

- auth-client：登录注册页面；
- music-client：音乐页面；


## PostMan接口请求

> 主要包括music、stream、user三个组，music组包括创建翻唱、修改翻唱状态、随便听听、已完成、未完成接口；user组包括注册、登录；stream组包括spleeter、svc、ffmpeg添加消费任务。
> 在postman目录下，可以导入到Postman软件中使用

## Mysql数据表

> Music表

```xml
CREATE TABLE `music` (  
`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',  
`user` bigint unsigned NOT NULL COMMENT '用户ID',  
`hash` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文件Hash',  
`title` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '歌曲标题',  
`artist` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '原唱歌手',  
`ai` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'AI翻唱歌手',  
`status` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'AI处理状态',  
`created_time` datetime(0) NOT NULL COMMENT '创建时间',  
`updated_time` datetime(0) NOT NULL COMMENT '修改时间',  
PRIMARY KEY (`id`) USING BTREE,  
INDEX `idx_user`(`user`) USING BTREE,  
UNIQUE INDEX `unique_user_hash`(`user`, `hash`) USING BTREE  
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '歌曲' ROW_FORMAT = Dynamic;
```

- user表；

```sql
CREATE TABLE `user` (  
`id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',  
`username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',  
`password` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',  
`email` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'email',  
`active` tinyint(1) NOT NULL COMMENT '启用',  
`created_time` datetime(0) NOT NULL COMMENT '创建时间',  
`updated_time` datetime(0) NOT NULL COMMENT '修改时间',  
PRIMARY KEY (`id`) USING BTREE,  
INDEX `idx_username`(`username`) USING BTREE,  
INDEX `idx_email`(`email`) USING BTREE  
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;
```

## Nginx配置

> 主要分为d.liuzeyu.me和aimusic.liuzeyu.me两个虚拟host。

在/etc/nginx/vhosts目录下，创建d.liuzeyu.me.conf文件

```bash
server {

    listen 80;

    server_name d.liuzeyu.me;

    access_log /data3/AI/log/d.liuzeyu.me.log;

    location / {

        root /data3/AI/output;

    }
}
```

在/etc/nginx/vhosts目录下，创建aimusic.liuzeyu.me.conf文件

```java
server {

    listen 80;

    server_name aimusic.liuzeyu.me;

    access_log /data3/AI/log/aimusic.liuzeyu.me.log;

    location / {

        root /data3/AI/music-client;

    }

    location /signin {

        root /data3/AI/auth-client;

    }

    location /signup {

        root /data3/AI/auth-client;

    }

    location /api {

        proxy_pass http://127.0.0.1:30000;

    }
}
```

## license 

GPL3
https://github.com/Illumina/licenses/blob/master/gpl-3.0.txt

本项目仅供学习娱乐，请勿滥用。请遵守知乎用户协议合理使用互联网

## donate

<img src="https://raw.githubusercontent.com/logonod/bookmark/master/images/donate.jpg" width = "400" align=center />

<img src="http://blog.liuzeyu.me/images/wechat.jpg" width = "400" align=center />
