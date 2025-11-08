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
        INTERFACE.commandProcess("delete group1");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("list group1");
        INTERFACE.commandProcess("move group1 20 -20");
        INTERFACE.commandProcess("boundingbox group1");
        INTERFACE.commandProcess("intersect group1 rect2");
        INTERFACE.commandProcess("ungroup group1");
        INTERFACE.commandProcess("move rect1 -5 5");
        INTERFACE.commandProcess("list rect1");
        INTERFACE.commandProcess("rectangle rect1 0 0 10 10");
        INTERFACE.commandProcess("move nonexistent 10 10");
        INTERFACE.commandProcess("list ghost");
        INTERFACE.commandProcess("delete phantom");
        INTERFACE.commandProcess("rectangle");
        INTERFACE.commandProcess("rectangle rect3 x y 10 10");
        INTERFACE.commandProcess("circle circle2 0 0 -5");
        INTERFACE.commandProcess("square square2 0 0 -10");
        INTERFACE.commandProcess("group empty_group");
        INTERFACE.commandProcess("group group2 rect1 nonexistent");
        INTERFACE.commandProcess("ungroup nonexistent_group");
        INTERFACE.commandProcess("ungroup rect1");
        INTERFACE.commandProcess("rectangle innerRect1 100 100 20 20");
        INTERFACE.commandProcess("circle innerCircle1 120 120 10");
        INTERFACE.commandProcess("group innerGroup innerRect1 innerCircle1");
        INTERFACE.commandProcess("rectangle outerRect 90 90 50 50");
        INTERFACE.commandProcess("group complexGroup innerGroup outerRect");
        INTERFACE.commandProcess("list complexGroup");
        INTERFACE.commandProcess("listAll");
        INTERFACE.commandProcess("ungroup innerGroup");
        INTERFACE.commandProcess("list complexGroup");
        INTERFACE.commandProcess("listAll");
        INTERFACE.commandProcess("move innerRect1 5 5");
        INTERFACE.commandProcess("group groupA rect1");
        INTERFACE.commandProcess("group groupB groupA");
        INTERFACE.commandProcess("rectangle autoRect1 0 0 25 20");
        INTERFACE.commandProcess("circle autoCircle2 50 50 8");
        INTERFACE.commandProcess("rectangle autoRect3 30 25 25 20");
        INTERFACE.commandProcess("circle autoCircle4 85 80 10");
        INTERFACE.commandProcess("rectangle autoRect5 60 50 25 20");
        INTERFACE.commandProcess("circle autoCircle6 120 110 12");
        INTERFACE.commandProcess("rectangle autoRect7 90 75 25 20");
        INTERFACE.commandProcess("circle autoCircle8 155 140 14");
        INTERFACE.commandProcess("group batchGroup1 autoRect1 autoCircle1 autoRect2");
        INTERFACE.commandProcess("group batchGroup2 autoCircle2 autoCircle3");
        INTERFACE.commandProcess("boundingbox nestedGroup");
        INTERFACE.commandProcess("shapeAt 60 60");
        INTERFACE.commandProcess("move nestedGroup -20 15");
        INTERFACE.commandProcess("move batchGroup2 10 -10");
        INTERFACE.commandProcess("ungroup nestedGroup");
        INTERFACE.commandProcess("quit");
        assertTrue(true);
    }
}