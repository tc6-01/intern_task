package com.beeran.spider.Utils;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class ArticleTagger {
    private HashMap<String, ArrayList<String>> Dict; // 存储专有名词字典

    private HashMap<String, ArrayList<Object>> resultDict;
    // 保存结果目录
    public ArticleTagger() {
        Dict = new HashMap<>();
        resultDict = new HashMap<>();
        ArrayList<String> PythonContent = new ArrayList<>();
        PythonContent.add("Flask");
        PythonContent.add("DJinGo");
        Dict.put("Python",PythonContent);
        ArrayList<String> JavaContent = new ArrayList<>();
        JavaContent.add("SpringBoot");
        JavaContent.add("Dubbo");
        JavaContent.add("MybatisPlus");
        JavaContent.add("Spring");
        JavaContent.add("SpringCloud");
        JavaContent.add("RPC");
        Dict.put("Java", JavaContent);
    }

    /**
     * 为文章添加标签
     *
     * @param article 文章名称
     * @param tags      文章的标签列表 ["Java","SpringBoot","RPC"] 、["Python", "Flask"]
     */
    public void addTags(String article, List<String> tags) {

//        {"Java": ["Java 文章", {"Dubbo": ["Dubbo 文章"]}]}
        // 将标签信息添加到 tagMap 中,首先查找一级标签，然后继续向下找
        for (int i = 0; i < tags.size(); i++) {
            // 判断一级标签
            if(Dict.containsKey(tags.get(i))){
                // 得到字典对应的名录
                ArrayList<Object> goal ;
                // 判断结果目录中是否已经有一级目录,如果没有创建一级目录，就需要先创建一级目录
                boolean flag = true;
                ArrayList<String> secondDict = Dict.get(tags.get(i));
                if (resultDict.isEmpty()){
                    goal = new ArrayList<>();
                }else{
                    goal = resultDict.get(tags.get(i));
                }
                if (!resultDict.isEmpty() && resultDict.containsKey(tags.get(i))) {
                  for(int j = i; j < tags.size() && flag; j++){
                        // 继续向下继续判断，第二层如果出现，创建一个字典将其加入并结束循环
                        if(secondDict.contains(tags.get(j))){
                            HashMap<String, ArrayList<String>> sonDict = new HashMap<>();
                            ArrayList<String> arrayList = new ArrayList<>();
                            arrayList.add(article);
                            sonDict.put(tags.get(j), arrayList);
                            goal.add(sonDict);
                            flag = false;
                        }
                    }
                }
                if (flag){
                    goal.add(article);
                    // 第一层直接加入文章名
                    resultDict.put(tags.get(i), goal);
                }
            }
        }
    }
}