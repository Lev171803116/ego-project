# ego-project #
<img src="/img/wechat.jpg" width = "200" height = "200"/> 

[https://github.com/Lev171803116/ego-project](https://github.com/Lev171803116/ego-project)     

### 该项目是一个商城系统，采用SpringBoot+dubbo+Redis开发。 ###
## tips ##
### 由于整个项目功能比较多，也比较繁琐，使用的都是一些当前比较主流的技术，目的是在于把一些常用到的技术或框架整合在一个项目中，便于理解项目开发的流程以及各种技术、框架在项目中起到的作用，从而更好地进行学习。因为此次开发主要学习后台的一些开源框架，所以前端就采用模板了（有可能的话再更改前端部分）。 ###

## 技术栈 ##
1.Springboot  
2.Mybatis   
3.Redis（缓存服务器）  
4.Solr（搜索）  
5.Dubbo（调用系统服务）   
6.Mysql   
7.Nginx（web 服务器）  
8.jsonp 跨域数据请求格式   
9.HttpClient 使用 java 完成请求及响应的技术   
10.MyCat mysql 分库分表技术 

## 整个项目结构图 ##
 <img src="/img/whole-project.png" width = "250" height = "400"/>

## 整个电商结构图 ##
 <img src="/img/whole.png" width = "600" height = "400"/>

## 基于SOA架构 ##
 <img src="/img/soa.png" width = "600" height = "400"/>


## 整体效果： ##
#### Dubbo服务，通过dubbo访问mysql ####
<img src="/img/dubbo.png" width = "600" height = "400"/>  

#### EasyUI实现的后台管理界面,ego-manange子项目中完成CMS系统的开发，对于图片的上传，在虚拟机上安装VSFTPD和Nginx（代理VSFTPD）实现。相对传统的项目上传图片到tomcat服务器中，那么在分布式环境下，是有多个Tomcat存在的，当把图片直接上传到Tomcat服务器时，容易出现图片丢失的问题。所以这里用到了在虚拟机上安装VSFTPD和Nginx（代理VSFTPD）实现。####
<img src="/img/manage.png" width = "600" height = "400"/>    

#### SSO单点登录:Redis+Cookie模拟HttpSession功能,第一次请求时cookie中没有token,产生一个UUID,把token作为cookie的key,UUID作为Cookie的value,直接把 UUID 当作 redis 的key(个别需要还需要考虑 key 重复问题.),把需要存储的内容作为value，把 cookie 对象响应给客户端浏览器####
<img src="/img/sso.png" width = "600" height = "400"/>
<img src="/img/login.png" width = "600" height = "400"/>

#### Jsonp跨域请求，显示分类数据 （跨域 ajax 数据请求的解决方案）因为springboot很好地支持CORS，使用CORS可以帮助我们快速实现跨域访问，只需在服务端进行授权即可，无需在前端添加额外设置，比传统的JSONP跨域更安全和便捷。所以项目中采用的是CORS实现跨域请求。####
<img src="/img/item.png" width = "600" height = "400"/>  


#### solr的使用，solr就是一个war项目，大量数据检索时使用solr能提升检索效率。但是solr默认对中文拆词功能不支持，解决方案使用IK Analyzer拆词器。通过搭建solrColud来实现分布式，需要借助zookeeper。####
<img src="/img/solr-cloudPic.png" width = "600" height = "200"/>
<img src="/img/solr-cloud.png" width = "600" height = "200"/>


#### redis实现购物车,借助redis来实现####
<img src="/img/cart.png" width = "600" height = "400"/>











