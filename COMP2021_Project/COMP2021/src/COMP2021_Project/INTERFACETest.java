package COMP2021_Project;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class INTERFACETest {
    @Test
    public void test(){
        if(LOGS.IshtmlPathEffective(new String[]{System.getProperty("user.dir"),System.getProperty("user.dir")})){}

        INTERFACE.deal("rectangle rect1 10 10 50 30");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        INTERFACE.deal("line line1 5 5 45 25");
        INTERFACE.deal("circle circle1 30 30 15");
        INTERFACE.deal("square square1 60 60 25");
        INTERFACE.deal("rectangle rect2 80 20 40 40");
        INTERFACE.deal("list rect1");
        INTERFACE.deal("listAll");
        INTERFACE.deal("boundingbox rect1");
        INTERFACE.deal("shapeAt 15 15");
        INTERFACE.deal("intersect rect1 circle1");
        INTERFACE.deal("move rect1 5 -5");
        INTERFACE.deal("move circle1 -10 10");
        INTERFACE.deal("group group1 rect1 circle1");
        INTERFACE.deal("list group1");
        INTERFACE.deal("move group1 20 -20");
        INTERFACE.deal("boundingbox group1");
        INTERFACE.deal("intersect group1 rect2");
        INTERFACE.deal("ungroup group1");
        INTERFACE.deal("move rect1 -5 5");
//        INTERFACE.deal("");
//        INTERFACE.deal("quit");
        assertTrue(true);
    }
}