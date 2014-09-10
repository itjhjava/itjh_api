package cn.com.itjh.mobileServer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.itjh.mobileServer.dao.ArticlesDao;
import cn.com.itjh.mobileServer.domain.Articles;
import cn.com.itjh.mobileServer.service.ArticlesService;

/**
 * 
 * 操作文章的Service实现类. <br>
 * 操作文章的Service实现类
 * 
 * @Copyright itjh
 * @Project
 * @author 宋立君
 * @date 2014年8月7日 下午5:42:10
 * @Version
 * @JDK version used 8.0
 * @Modification history none
 * @Modified by none
 */
@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Resource
    private ArticlesDao ArticlesDao;

    @Override
    public List<Articles> getArticlesByProgrammingInsights(int pageNum, int showNum) {
        Map<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put("pageNum", pageNum);
        pageMap.put("showNum", showNum);
        List<Articles> articles = ArticlesDao.getArticlesByProgrammingInsights(pageMap);
        return articles;
    }
 
    @Override
    public Articles getArticlesByArtticsId(String artticsId) {
        Articles articles = ArticlesDao.getArticlesByArtticsId(artticsId);
        return articles;
    }

    @Override
    public List<Articles> getArticlesByMoblieDevelopment(int pageNum, int showNum) {
        Map<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put("pageNum", pageNum);
        pageMap.put("showNum", showNum);
        List<Articles> articles = ArticlesDao.getArticlesByMoblieDevelopment(pageMap);
        return articles;
    }

    @Override
    public List<Articles> getArticlesByNew(int pageNum, int showNum) {
        Map<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put("pageNum", pageNum);
        pageMap.put("showNum", showNum);
        List<Articles> articles = ArticlesDao.getArticlesByNew(pageMap);
        List<String> listImgUrl = new ArrayList<String>();  
        List<String> listImgUrlsrc = new ArrayList<String>();  
        for (int i = 0; i < articles.size(); i++) {
            String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";  
            Matcher matcher = Pattern.compile(IMGURL_REG).matcher(articles.get(i).getContent()); 
            if (matcher.find()) {  
                listImgUrl.add(matcher.group());
            } else{
                listImgUrl.add("<img src=\"null\" />");
            }
        }
        for (int i = 0; i < listImgUrl.size(); i++) {
            String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\"";
            Matcher matcher = Pattern.compile(regxpForImaTagSrcAttrib).matcher(listImgUrl.get(i)); 
            
            if (matcher.find()) {  
                listImgUrlsrc.add(matcher.group());  
            }   else{
                listImgUrlsrc.add(null);  
            }
        }
//        System.out.println(listImgUrlsrc.size());
//        System.out.println(listImgUrlsrc);
        for (int i = 0; i < articles.size(); i++) {
            articles.get(i).setContent("");
            if (!"null".equals(listImgUrlsrc.get(i))) {
                articles.get(i).setTopImg(listImgUrlsrc.get(i).replaceAll("src=", "").replaceAll("\"", ""));
            }else{
                articles.get(i).setTopImg("null");
            }
        }
        
        return articles;
    }

}
