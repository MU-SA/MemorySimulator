import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static double memorySize;
    private static Memory memory;
    private static ArrayList<Partition> partitions = new ArrayList<>();
    private static double startingAddress = 0;
    private static int type;
    private static int allocationType;
    private static int defType;

    public static void main(String[] args) {
        System.out.print("Add Memory size: ");
        memorySize = new Scanner(System.in).nextDouble();
        memory = new Memory(memorySize);


        option();
        while (type != 5) {

            switch (type) {
                case 1: // Allocation
                    allocationType();
                    switch (allocationType) {
                        case 1:
                            System.out.println("***BEST FIT*** \n\nAdd Data size");
                            bestFit(new Scanner(System.in).nextDouble());
                            break;
                        case 2:
                            System.out.println("***WORST FIT*** \n\nAdd Data size");
                            worstFit(new Scanner(System.in).nextDouble());
                            break;

                        case 3:
                            System.out.println("***FIRST FIT*** \n\nAdd Data size");
                            firstFit(new Scanner(System.in).nextDouble());
                            break;
                    }
                    break;

                case 2: // deallocate
                    System.out.print("Add Partition Starting Address: ");
                    deallocate(new Scanner(System.in).nextDouble());
                    break;

                case 3: // Defragmentation
                    defragmentationType();
                    switch (defType) {
                        case 1:
                            caseOne();
                            break;
                        case 2:
                            caseTwo();
                            break;
                    }
                    break;

                case 4: // Memory State
                    state();
                    break;
            }
            option();

        }


    }

    private static void defragmentationType() {
        System.out.println("Choose Defragmentation type option:");
        System.out.println("1 - Case 1");
        System.out.println("2 - Case 2");
        defType = new Scanner(System.in).nextInt();

    }

    private static void option() {
        System.out.println("Choose option:");
        System.out.println("1 - Allocate Memory");
        System.out.println("2 - Deallocate Memory");
        System.out.println("3 - Defragmentation");
        System.out.println("4 - Memory status");
        System.out.println("5 - Exit");

        type = new Scanner(System.in).nextInt();
    }

    private static void allocationType() {
        System.out.println("Choose Allocation Type:");
        System.out.println("1 - Best Fit:");
        System.out.println("2 - Worst Fit:");
        System.out.println("3 - First Fit:");
        allocationType = new Scanner(System.in).nextInt();
    }

    private static void state() {
        System.out.println("*******Memory status********");
        for (int i = 0; i < partitions.size(); i++) {
            if (i != 0) {

                System.out.println("\n\n");
            }
            if (i == 0) {
                System.out.println("Main Memory size = " + memory.getMemorySize());
                System.out.println("Main Memory Available Size = " + memory.getAvailableMemorySize());
                System.out.println("Main Memory Partitions = " + partitions.size());
                System.out.println("\n\n");
            }
            System.out.println("Partition Size: " + partitions.get(i).getPartitionSize() + " MB");
            System.out.println("Partition Allocated Memory Size: " + partitions.get(i).getAllocatedMemorySize() + " MB");
            System.out.println("Partition Available Size: " + partitions.get(i).getAvailableSize() + " MB");
            System.out.println("Partition Starting Address: " + partitions.get(i).getStartingAddress());

            if (i != partitions.size() - 1) {

                System.out.println("\n\n");
            }
        }
        System.out.println("*******eor********" + "\n\n");

    }

    private static Partition firstFit(double dataSize) {
        if (memory.getAvailableMemorySize() < dataSize) {
            System.out.println("There is no enough size!!");
        } else {
            if (partitions.size() == 0) {
                return addNewPartition(dataSize);
            }
            for (int i = 0; i < partitions.size(); i++) {
                if (partitions.get(i).getAvailableSize() >= dataSize) {
                    partitions.get(i).AddData(dataSize);
                    System.out.println("Starting address = " + partitions.get(i).getStartingAddress());
                    return partitions.get(i);
                }
            }
            addNewPartition(dataSize);
        }
        return null;

    }

    private static Partition addNewPartition(double dataSize) {
        Partition new1 = new Partition(dataSize, startingAddress);
        new1.AddData(dataSize);

        partitions.add(new1);
        memory.allocate(dataSize);

        System.out.println("Starting address = " + new1.getStartingAddress());

        startingAddress += new1.getPartitionSize();
        return new1;
    }

    private static Partition worstFit(double dataSize) {
        if (memory.getAvailableMemorySize() < dataSize) {
            System.out.println("There is no enough size!");
            return null;
        } else {
            if (partitions.size() < 1) {
                return addNewPartition(dataSize);
            } else {
                ArrayList<Partition> fiters = new ArrayList<>();
                Partition bestPartition = partitions.get(0);
                int index = 0;

                for (int i = 0; i < partitions.size(); i++) {

                    if (partitions.get(i).getAvailableSize() >= dataSize) {

                        fiters.add(partitions.get(i));
                    }

                }

                for (int i = 0; i < fiters.size(); i++) {
                    bestPartition = fiters.get(0);
                    if (bestPartition.getAvailableSize() <= fiters.get(i).getAvailableSize()) {
                        bestPartition = fiters.get(i);
                        index = i;
                    }
                }

                if (bestPartition.getAvailableSize() >= dataSize) {
                    fiters.get(index).AddData(dataSize);

                    System.out.println("Starting address = " + partitions.get(partitions.indexOf(fiters.get(index))).getStartingAddress());

                    return partitions.get(partitions.indexOf(fiters.get(index)));
                } else {
                    System.out.println("-");
                    return addNewPartition(dataSize);
                }
            }
        }

    }

    private static Partition bestFit(double dataSize) {

        if (memory.getAvailableMemorySize() < dataSize) {
            System.out.println("There is no enough size!");
            return null;
        } else {
            if (partitions.size() < 1) {
                return addNewPartition(dataSize);
            } else {
                ArrayList<Partition> fitters = new ArrayList<>();
                Partition bestPartition = partitions.get(0);
                int index = 0;

                for (int i = 0; i < partitions.size(); i++) {

                    if (partitions.get(i).getAvailableSize() >= dataSize) {

                        fitters.add(partitions.get(i));

                    }

                }

                for (int i = 0; i < fitters.size(); i++) {
                    bestPartition = fitters.get(0);
                    if (bestPartition.getAvailableSize() >= fitters.get(i).getAvailableSize()) {
                        bestPartition = fitters.get(i);
                        index = i;
                    }
                }

                if (bestPartition.getAvailableSize() >= dataSize) {

                    fitters.get(index).AddData(dataSize);

                    memory.allocate(dataSize);

                    System.out.println("Starting address = " + partitions.get(partitions.indexOf(fitters.get(index))).getStartingAddress());

                    return partitions.get(partitions.indexOf(fitters.get(index)));
                } else {
                    System.out.println("NO PARTITION");
                    return addNewPartition(dataSize);
                }
            }
        }


    }

    private static void deallocate(double startingAddress) {
        boolean valid = false;
        Partition free = partitions.get(0);
        double partitionSize = 0;
        for (int i = 0; i < partitions.size(); i++) {
            if (partitions.get(i).getStartingAddress() == startingAddress) {
                valid = true;
                partitionSize = partitions.get(i).getPartitionSize();
                partitions.get(i).setAllocatedMemorySize(0);

                free = partitions.get(i);
            }
        }
        if (valid) {
            memory.deallocate(partitionSize);
            free.isFree();

        } else {
            System.out.println("Starting Address is not valid");
        }
    }

    private static void caseOne() {
        double startingAddressDEF = 0;
        double avSize;
        if (partitions.size() == 0) {
            System.out.println("There is no partitions!");
        } else {
            for (int i = 0; i < partitions.size(); i++) {
                if (partitions.get(i).isFree()) {

                } else if (partitions.get(i).getAvailableSize() != 0) {
                    partitions.get(i).setStartingAddress(startingAddressDEF);
                    avSize = partitions.get(i).getAvailableSize();
                    partitions.get(i).setPartitionSize(partitions.get(i).getAllocatedMemorySize());
                    startingAddressDEF += partitions.get(i).getPartitionSize();

                    Partition new1 = new Partition(avSize, startingAddressDEF);
                    partitions.add(i + 1, new1);
                    i++;
                }
                startingAddressDEF += partitions.get(i).getPartitionSize();
            }
        }

    }

    private static void caseTwo() {

        for (int i = 0; i < partitions.size(); i++) {
            if (partitions.get(i).isFree()) {
                if ((i + 1) < partitions.size() && partitions.get(i + 1).isFree()) {
                    partitions.get(i).setPartitionSize(partitions.get(i).getPartitionSize() + partitions.get(i + 1).getPartitionSize());
                    System.out.println("Partition " + partitions.get(i).getStartingAddress() + " is merged with " + partitions.get(i+1).getStartingAddress());
                    partitions.remove(partitions.get(i + 1));
                    i--;
                }
            }
        }
    }



}
