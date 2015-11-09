/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keytyperserver;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Stack;

/**
 *
 * @author venca
 */
public class KeyPresser {

    private static KeyPresser instance = new KeyPresser();
    private Stack<String> commandStack = new Stack<String>();

    public static KeyPresser getInstance() {
        return instance;
    }

    private KeyPresser() {
    }

    private boolean pressBasicKey(String key) {
        try {
            Robot robot = new Robot();
            boolean flag = false;

            if (key.length() == 1 && Character.isLetterOrDigit(key.toCharArray()[0])) {
                flag = true;
            } else if (key.length() == 7) {
                for (int i = 0; i < 10; i++) {
                    if (key.equals("numpad" + i)) {
                        flag = true;
                        break;

                    }
                }
            } else if (key.length() == 2) {
                for (int i = 1; i < 13; i++) {
                    if (key.equals("f" + i)) {
                        flag = true;
                        break;
                    }
                }
            }

            if (flag) {
                String variableName = "VK_" + key;
                Class clazz = KeyEvent.class;
                Field field = clazz.getField(variableName);
                robot.keyPress(field.getInt(null));
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean pressSpecialKey(String key) {
        try {
            Robot robot = new Robot();
            switch (key) {
                case "SHIFT":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    return true;
                case "CTRL":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    return true;
                case "ALT":
                    robot.keyPress(KeyEvent.VK_ALT);
                    return true;
                case "UP":
                    robot.keyPress(KeyEvent.VK_UP);
                    return true;
                case "DOWN":
                    robot.keyPress(KeyEvent.VK_DOWN);
                    return true;
                case "RIGHT":
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    return true;
                case "LEFT":
                    robot.keyPress(KeyEvent.VK_LEFT);
                    return true;
                case ",":
                    robot.keyPress(KeyEvent.VK_COMMA);
                    return true;
                case ".":
                    robot.keyPress(KeyEvent.VK_PERIOD);
                    return true;
                case "-":
                    robot.keyPress(KeyEvent.VK_SUBTRACT);
                    return true;
                case "/":
                    robot.keyPress(KeyEvent.VK_SLASH);
                    return true;
                case "\\":
                    robot.keyPress(KeyEvent.VK_BACK_SLASH);
                    return true;
                case "`":
                    robot.keyPress(KeyEvent.VK_BACK_QUOTE);
                    return true;
                case "PLUS":
                    robot.keyPress(KeyEvent.VK_ADD);
                    return true;
                case "ENTER":
                    robot.keyPress(KeyEvent.VK_ENTER);
                    return true;
                case "SPACE":
                    robot.keyPress(KeyEvent.VK_SPACE);
                    return true;
                case ";":
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    return true;
                case "WIN":
                    robot.delay(100);
                    robot.keyPress(KeyEvent.VK_WINDOWS);
                    break;
                case "SUPER":
                    robot.keyPress(33);
                    return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean releaseBasicKey(String key) {
        try {
            Robot robot = new Robot();
            boolean flag = false;

            if (key.length() == 1 && Character.isLetterOrDigit(key.toCharArray()[0])) {
                flag = true;
            } else {
                for (int i = 0; i < 10; i++) {
                    if (key.equals("numpad" + i)) {
                        flag = true;
                        break;

                    }
                }
            }

            if (flag) {
                String variableName = "VK_" + key;
                Class clazz = KeyEvent.class;
                Field field = clazz.getField(variableName);
                robot.keyRelease(field.getInt(null));
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean releaseSpecialKey(String key) {
        try {
            Robot robot = new Robot();
            switch (key) {
                case "SHIFT":
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    return true;
                case "CTRL":
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    return true;
                case "ALT":
                    robot.keyRelease(KeyEvent.VK_ALT);
                    return true;
                case "UP":
                    robot.keyRelease(KeyEvent.VK_UP);
                    return true;
                case "DOWN":
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    return true;
                case "RIGHT":
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    return true;
                case "LEFT":
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    return true;
                case ",":
                    robot.keyRelease(KeyEvent.VK_COMMA);
                    return true;
                case ".":
                    robot.keyRelease(KeyEvent.VK_PERIOD);
                    return true;
                case "-":
                    robot.keyRelease(KeyEvent.VK_SUBTRACT);
                    return true;
                case "/":
                    robot.keyRelease(KeyEvent.VK_SLASH);
                    return true;
                case "\\":
                    robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                    return true;
                case "`":
                    robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
                    return true;
                case "PLUS":
                    robot.keyRelease(KeyEvent.VK_ADD);
                    return true;
                case "ENTER":
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    return true;
                case "SPACE":
                    robot.keyRelease(KeyEvent.VK_SPACE);
                    return true;
                case ";":
                    robot.keyRelease(KeyEvent.VK_SEMICOLON);
                    return true;
                case "WIN":
                    robot.keyRelease(KeyEvent.VK_WINDOWS);
                    return true;
                case "SUPER":
                    robot.keyRelease(33);
                    return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void KeyPress(String key) {
        try {
            key = key.toUpperCase();

            if (!pressBasicKey(key)) {
                pressSpecialKey(key);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void KeyRelease(String key) {
        try {
            key = key.toUpperCase();

            if (!releaseBasicKey(key)) {
                releaseSpecialKey(key);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processCommand(String command) {
        String delims = "[+]+";
        String[] tokens = command.toUpperCase().split(delims);
        for (String s : tokens) {
            if (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'') {
                for (int i = 0; i <= s.length() -1; i++) {
                    String tmp = new String();
                    tmp += s.charAt(i);

                    System.out.println("pressed: " + tmp);
                    KeyPress(tmp);
                    KeyRelease(tmp);
                }
            }
            KeyPress(s);
        }

        for (int i = tokens.length - 1; i >= 0; i--) {
            if (tokens[i].charAt(0) != '\'') {
                KeyRelease(tokens[i]);
            }
        }
    }
}
