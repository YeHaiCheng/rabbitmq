package com.xiaoye.rabbitmq.testmongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xiaoye.rabbitmq.model.User;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest
public class Test {
    @Autowired
    MongoTemplate  mongoTemplate;

    @org.junit.jupiter.api.Test
    public void test(){
        Query query = new Query(Criteria.where("id").is(1));
        List<Object> objects = mongoTemplate.find(query, Object.class);
        System.out.println(objects.toString());
    }

    @org.junit.jupiter.api.Test
    public void test1(){
        User user = new User();
        user.setId(2);
        user.setName("xiaoye");
        mongoTemplate.insert(user);
    }
    @org.junit.jupiter.api.Test
    public void test2(){
        Query query=new Query(Criteria.where("id").is(1));
        List<User> users = mongoTemplate.find(query, User.class);
        for (User use:users){
            System.out.println(use.id+" "+use.name);
        }
    }
    @org.junit.jupiter.api.Test
    public void test3(){
        Query query = new Query(Criteria.where("id").is(1));
        User one = mongoTemplate.findOne(query, User.class);
        System.out.println(one.id+" "+one.name);
    }
    @Autowired
    GridFsTemplate gridFsTemplate;

    @org.junit.jupiter.api.Test
    public  void test4() throws FileNotFoundException {
    File file = new File("d://a.txt");
    InputStream content = new FileInputStream(file);
    DBObject metadata = new BasicDBObject("userId","1");
    ObjectId fileId = gridFsTemplate.store(content, file.getName(),"file/files",metadata);
    System.out.println(fileId.toString());
    //5f06ba7b5d930a3064bf9ff5
        // 5f06d621e608942ea25ea3ae
    }

    @org.junit.jupiter.api.Test
    public void test5() throws IOException {
    }



    @Autowired
    GridFsTemplate gridFsTemplate1;

    // 存文件
    @org.junit.jupiter.api.Test
    public void storeFileInGridFs() {
        Resource file = new ClassPathResource("mongo.yml");
        // Resource file = new FileSystemResource("C:\\Users\\Chenggaowei\\Downloads\\Adblock.crx");
        try {
            gridFsTemplate.store(file.getInputStream(), file.getFilename(), "yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


     //下载文件
    @org.junit.jupiter.api.Test
    public void findFilesInGridFs() throws IOException {
        Query query = new Query();
        GridFSFindIterable gridFSFiles = gridFsTemplate.find(query);
//        gridFSFiles.forEach();
//        List<GridFSDBFile> result = gridFsTemplate.find(new Criteria(whereFilename().is("mongo.yml")));
//        GridFSDBFile gridFSDBFile = result.get(0);
//        gridFSDBFile.writeTo(new File("C:\\Users\\Chenggaowei\\Downloads\\" + gridFSDBFile.getFilename()));

    }
    @org.junit.jupiter.api.Test
    public void findFilesInGridFs2(){
        GridFsResource resource = gridFsTemplate.getResource("a.txt");
        System.out.println(resource.getFilename());
        System.out.println(resource.getId());
    }
    // 所有文件
    @org.junit.jupiter.api.Test
    public void readFilesFromGridFs() {
        GridFsResource[] txtFiles = gridFsTemplate.getResources("*");
        for (GridFsResource txtFile : txtFiles) {
            System.out.println(txtFile.getFilename());
        }
    }

}
