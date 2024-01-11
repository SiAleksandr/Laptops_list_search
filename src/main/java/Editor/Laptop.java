package Editor;

public class Laptop {
    public Laptop(String fullName, Integer ramGb, Integer ssdDiskSize, String representation,
                  String osName, String color) {
        this.fullName = fullName;
        this.ramGb = ramGb;
        this.ssdDiskSize = ssdDiskSize;
        this.representation = representation;
        this.osName = osName;
        this.color = color;
    }

    String fullName;
    Integer ramGb;
    Integer ssdDiskSize;
    String representation;
    String osName;
    String color;

    @Override
    public String toString() {
        return ("\nНоутбук " + fullName + " | " + osName + "\n" +
                "Оперативная память: " + ramGb + " Гб SSD диск: " + ssdDiskSize +
                " " + representation + " Цвет: " + color);
    }
}
