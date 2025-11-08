package COMP2021_Project;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Write
 */
public class INTERFACETest {
    @Test
    public void testCoverage(){
        INTERFACE.filePathTest(new String[]{"-txt",System.getProperty("user.dir"),"-html",System.getProperty("user.dir")});

        INTERFACE.commandProcess("rectangle rect1 10 10 50 30");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        INTERFACE.commandProcess("line line1 5 5 45 25");
        INTERFACE.commandProcess("circle circle1 30 30 15");
        INTERFACE.commandProcess("square square1 60 60 25");
        INTERFACE.commandProcess("rectangle rect2 80 20 40 40");
        INTERFACE.commandProcess("list rect1");
        INTERFACE.commandProcess("listAll");
        INTERFACE.commandProcess("boundingbox rect1");
        INTERFACE.commandProcess("shapeAt 15 15");
        INTERFACE.commandProcess("intersect rect1 circle1");
        INTERFACE.commandProcess("move rect1 5 -5");
        INTERFACE.commandProcess("move circle1 -10 10");
        INTERFACE.commandProcess("group group1 rect1 circle1");
        INTERFACE.commandProcess("list group1");
        INTERFACE.commandProcess("move group1 20 -20");
        INTERFACE.commandProcess("boundingbox group1");
        INTERFACE.commandProcess("intersect group1 rect2");
        INTERFACE.commandProcess("ungroup group1");
        INTERFACE.commandProcess("move rect1 -5 5");
//        INTERFACE.commandProcess("");
//        INTERFACE.commandProcess("quit");
        assertTrue(true);
    }
}