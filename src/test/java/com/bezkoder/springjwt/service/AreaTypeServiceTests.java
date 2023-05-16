package com.bezkoder.springjwt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AreaTypeServiceTests {
    @Autowired(required = false)
    private IAreaTypeService areaTpeService;

    @Test
    public void saveAreaType(){
        String name="零件A区";
        areaTpeService.saveAreaType(name);
    }
    @Test
    public void findAreaType(){
        String name="零件A区";
        areaTpeService.findAreaType(name);
    }
    @Test
    public void getAreaTypeList(){
        areaTpeService.getAreaTypeList();
    }
    @Test
    public void deleteAreaTypeByName(){
        String name="零件A区";
        areaTpeService.deleteAreaTypeByName(name);
    }
    @Test
    public void changeName(){
        String name="零件A区";
        String newName="零件新A区";
        areaTpeService.changeName(name,newName);
    }
}
