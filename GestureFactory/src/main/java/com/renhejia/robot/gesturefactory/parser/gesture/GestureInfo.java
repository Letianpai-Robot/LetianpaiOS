package com.renhejia.robot.gesturefactory.parser.gesture;

/**
 * @author liujunbin
 */
public class GestureInfo {
    private int version;
    private String name;
    private int length;

    public int getLength() {
        return length;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GestureInfo{" +
                "version=" + version +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
