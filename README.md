# Intern_Task

你是编程导航平台的文章创作者，现在你想要把自己在这个平台上发布的所有文章导出到自己的网站，并且根据文章的内容给每篇文章打上对应的多个标签（比如 Java、Dubbo），然后将这些文章按照标签分层级地进行归档，比如 {"Java": ["Java 文章", {"Dubbo": ["Dubbo 文章"]}]}，便于之后的阅读和搜索。

问题：原平台不提供导出、打标签、分层级归档、搜索等功能，请问如何自主设计实现上述系统。请尽量清晰详细地阐述思路，能具体实现更加分。

## 需求分析
- 将编程导航平台的文章导出（使用爬虫解决）
- 根据文章的内容给每篇文章打上标签（使用关键词提取获取每篇文章的关键词）
- 文章按照标签分层级进行归档（将文章的标签进行分级，然后将文章名称放置对应目录下）

## 技术选型

- 爬虫：动态网页（JS加载），因此使用selenium模拟浏览器进行加载，然后使用编程导航动态码来解决登录问题（TODO：怎样实现自动登录，不需要手动修改验证码）

- 关键词提取：使用Hanlp提取文章中的关键词（TODO：提取效果并不理想）

## 实现
Utils中的两个类分别对应着实现方法

- Spider Main方法，实现爬虫 + 分词 + 归档 + 文件保存
- ArtileTagger 实现将文章标签进行Json格式分级

files 用于最终保存文件


## 优化

。。。。待输出中