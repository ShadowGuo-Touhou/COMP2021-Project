package COMP2021_Project;
import org.junit.Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * Test for line coverage
 */
public class INTERFACETest {
    /**
     * Test for line coverage
     */
    @Test
    public void testCoverage()  {
        INTERFACE.filePathTest(new String[]{"-txt",System.getProperty("user.dir"),"-html",System.getProperty("user.dir")});
        GUIInterface gui=INTERFACE.getGui();
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_UP);
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.keyPress(KeyEvent.VK_EQUALS);
        robot.keyPress(KeyEvent.VK_MINUS);
        //Key press test
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        robot.keyRelease(KeyEvent.VK_EQUALS);
        robot.keyRelease(KeyEvent.VK_MINUS);

        //Basic Operations
        INTERFACE.commandProcess("rectangle rect1 10 10 50 30");
        INTERFACE.commandProcess("line line1 5 5 45 25");
        INTERFACE.commandProcess("circle circle1 30 30 15");
        INTERFACE.commandProcess("square square1 60 60 25");
        INTERFACE.commandProcess("rectangle rect2 80 20 40 40");
        INTERFACE.commandProcess("group Group1 rect1 line1");
        INTERFACE.commandProcess("delete circle1");
        INTERFACE.commandProcess("move Group1 10 20");
        INTERFACE.commandProcess("group Group2 Group1 rect2 square1");
        INTERFACE.commandProcess("ungroup Group2");

        //Information Queries
        INTERFACE.commandProcess("boundingbox rect2");
        INTERFACE.commandProcess("list square1");
        INTERFACE.commandProcess("shapeAt 80.0001 20");
        INTERFACE.commandProcess("shapeAt 14.997 24.997");
        INTERFACE.commandProcess("shapeAt 20 20");
        INTERFACE.commandProcess("intersect Group1 square1");
        INTERFACE.commandProcess("listall");

        //Complex Group Operations&Queries
        INTERFACE.commandProcess("group Group3 rect1 rect2");
        INTERFACE.commandProcess("list Group3");
        INTERFACE.commandProcess("move Group3 200 10");
        INTERFACE.commandProcess("ungroup Group1");
        INTERFACE.commandProcess("list Group3");
        INTERFACE.commandProcess("boundingbox Group3");
        INTERFACE.commandProcess("delete Group3");

        //Undo & Redo
        INTERFACE.commandProcess("rectangle rect3 20 30 50 60");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("circle circle2 66 66 88");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("square square2 79 26 8");
        INTERFACE.commandProcess("group group4 rect3 circle2");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("move group4 -100 111");
        INTERFACE.commandProcess("line line1 29 30 29 60");
        INTERFACE.commandProcess("group Group1 rect3 square2 line1");
        INTERFACE.commandProcess("ungroup group4");
        INTERFACE.commandProcess("shapeAt 66 154");
        INTERFACE.commandProcess("listall");
        INTERFACE.commandProcess("intersect square1 Group1");
        INTERFACE.commandProcess("delete Group1");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("intersect Group1 square1");
        INTERFACE.commandProcess("move Group1 228 779");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("redo");

        //Wrong Handle
        INTERFACE.commandProcess("group Group1 Group1 Group1 Group1");
        INTERFACE.commandProcess("group Group2 exist? no");
        INTERFACE.commandProcess("move ghost 999 666");
        INTERFACE.commandProcess("circle errorcircle -1 -1 -11");
        INTERFACE.commandProcess("rectangle rect7 100 100 100 100 100 100");
        INTERFACE.commandProcess("intersect Group1 nonexist");
        INTERFACE.commandProcess("square square5 7u 7 7");
        INTERFACE.commandProcess("What is this operation?");
        INTERFACE.commandProcess("delete square1");
        INTERFACE.commandProcess("move square1 1 1");
        INTERFACE.commandProcess("undo then redo");
        INTERFACE.commandProcess("redo");
        INTERFACE.commandProcess("undo");
        INTERFACE.commandProcess("square square1 23 2 4");
        INTERFACE.commandProcess("ungroup square1");
        INTERFACE.commandProcess("move line1 87 67");
        INTERFACE.commandProcess("intersect Group1 line1");
        INTERFACE.commandProcess("intersect line1 Group1");
        INTERFACE.commandProcess("delete line1");
        INTERFACE.commandProcess("move square1 9u 8u");
        INTERFACE.commandProcess("move square1 -9 eight");
        INTERFACE.commandProcess("square square4 -2 -3 -4");
        INTERFACE.commandProcess("square square4 -2 -3 4u");
        INTERFACE.commandProcess("rectangle rect6 7 5 3 -2");
        INTERFACE.commandProcess("line line3 8 8 8 8");
        //quit
        INTERFACE.commandProcess("quit");
    }
}