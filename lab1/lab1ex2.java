import java.util.ArrayList;

class Computer {
    private int ram;
    private String cpu;
    private ArrayList<SoftwareProducts> softwareProducts;
    public Computer(int ram, String cpu) {
        if (ram == 4 || ram == 8 || ram == 16 || ram == 32) {
            this.ram = ram;
        } else {
            throw new IllegalArgumentException("Invalid RAM");
        }

        if (cpu.equals("i3") || cpu.equals("i5") || cpu.equals("i7")) {
            this.cpu = cpu;
        } else {
            throw new IllegalArgumentException("Invalid CPU");
        }

        this.softwareProducts = new ArrayList<>();
    }
    public boolean addProducts(SoftwareProducts sp) {
        if (sp.checkCompatibility(this)) {
            softwareProducts.add(sp);
            System.out.println("Software " + sp.getName() + " is compatible");
            return true;
        } else {
            System.out.println("Software " + sp.getName() + " is not compatible.");
            return false;
        }
    }
    public int getRam() {
        return ram;
    }
    public String getCpu() {
        return cpu;
    }
}

abstract class SoftwareProducts {
    private String name;
    @SuppressWarnings("unused")
    private String program;
    private int minRam;
    private String requiredCpu;
    public SoftwareProducts(String name, String program, int minRam, String requiredCpu) {
        this.name = name;
        this.program = program;
        this.minRam = minRam;
        this.requiredCpu = requiredCpu;
    }
    public boolean checkCompatibility(Computer c) {
        return (c.getRam() >= minRam && requiredCpu.equals(c.getCpu()));
    }
    public String getName() {
        return name;
    }
}

public class lab1ex2 {
    public static void main(String[] args) {
        Computer comp1 = new Computer(32, "i5");
        SoftwareProducts sp1 = new SoftwareProducts("Video Editor", "program", 8, "i3"){};
        SoftwareProducts sp2 = new SoftwareProducts("Photo Editor", "program", 16, "i5"){};
        SoftwareProducts sp3 = new SoftwareProducts("Text Editor", "program", 32, "i7"){};
        comp1.addProducts(sp1);
        comp1.addProducts(sp2);
        comp1.addProducts(sp3);
    }
}
