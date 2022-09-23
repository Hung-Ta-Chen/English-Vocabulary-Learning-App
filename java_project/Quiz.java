/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_project;

import java.util.ArrayList;

/**
 *
 * @author narut
 */
public class Quiz {
    int quiz_count;
    String set_name;
    ArrayList<String> quesArray;
    ArrayList<String> ansArray;
    ArrayList<String> replyArray;
    ArrayList<Boolean> correct;
    
    public Quiz(String setName, int cnt){
        set_name = setName;
        quiz_count = cnt;
        quesArray = new ArrayList<String>();
        ansArray = new ArrayList<String>();
        replyArray = new ArrayList<String>();
        correct = new ArrayList<Boolean>();
    }
}
