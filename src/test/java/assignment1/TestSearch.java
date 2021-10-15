package assignment1;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;

public class TestSearch extends TestCase {

    public void testSearch(){
        text_editor textEditor = new text_editor();
        Boolean flag = true;
        ArrayList<Integer> index = textEditor.Search("w","wooow");
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(4);
        if(list == index){
            flag = true;
        }
        else if ((list == null && index != null && index.size() == 0) || (index == null && list != null && list.size() == 0)) {
            flag = true;
        }
        else if (list.size() != index.size()) {
            flag = false;
        }
        else if (!list.containsAll(index)) {
            flag = false;
        }
        Assert.assertTrue(flag);
    }
}
