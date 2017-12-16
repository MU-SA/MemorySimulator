public class Memory {


    public double MemorySize;
    public double AvailableMemorySize;

    public Memory(double memorySize) {
        MemorySize = memorySize;
        AvailableMemorySize = memorySize;
    }

    public double getMemorySize() {
        return MemorySize;
    }

    public double getAvailableMemorySize() {
        return AvailableMemorySize;
    }

    public void allocate(double data) {
        this.AvailableMemorySize -= data;
    }

    public void deallocate(double data) {
        this.AvailableMemorySize += data;
    }
}
