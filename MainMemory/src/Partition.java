public class Partition {

    private double startingAddress;
    private double partitionSize;
    private double allocatedMemorySize;

    public Partition(double size, double startingAddress) {

        this.startingAddress = startingAddress;
        this.partitionSize = size;
    }

    public double getAllocatedMemorySize() {
        return allocatedMemorySize;
    }


    public void setStartingAddress(double startingAddress) {
        this.startingAddress = startingAddress;
    }

    public void setPartitionSize(double partitionSize) {
        this.partitionSize = partitionSize;
    }

    public void setAllocatedMemorySize(double allocatedMemorySize) {
        this.allocatedMemorySize = allocatedMemorySize;
    }

    public double getAvailableSize() {
        return (partitionSize - allocatedMemorySize);
    }

    public boolean isFree() {

        return allocatedMemorySize == 0;
    }


    public double getStartingAddress() {
        return startingAddress;
    }

    public void AddData(double data) {
        this.allocatedMemorySize += data;

    }

    public double getPartitionSize() {
        return partitionSize;
    }
}
