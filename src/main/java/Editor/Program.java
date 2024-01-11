package Editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        System.out.println();
        List<Laptop> dataList = addingData();
        Integer[] indexes = new Integer[dataList.size()];
        String[] count = new String[]{"первый", "второй", "третий", "четвёртый"};
        String[] criteria = getCriteria(count.length + 1);
        Integer[] sequence = new Integer[criteria.length];
        sequence = rePrint(sequence);
        boolean flag = true;
        Integer c = criteria.length;
        int n = 0;
        int exit = 1;
        while (flag) {
            System.out.println("Выберете " + count[n] + " критерий для поиска ноутбуков");
            for (Integer i = 1; i < c; i++)
                System.out.println(i + criteria[sequence[i]]);
            if (exit == 1 || n == 0) {
                indexes = clearing(indexes);
                System.out.println("0 - ВЫХОД");
            } else
                System.out.println("0 - НОВЫЙ ПОИСК");
            try {
                Scanner iScanner = new Scanner(System.in);
                System.out.printf(" -> ");
                Integer choice = iScanner.nextInt();
                while (choice < 0 || choice >= c) {
                    System.out.printf("\nВыберете из указанных чисел -> ");
                    choice = iScanner.nextInt();
                }
                choice = sequence[choice];
                switch (choice) {
                    case 0: {
                        exit += 1;
                        if (exit == 2)
                            flag = false;
                        else {
                            n = 0;
                            c = count.length;
                        }
                    }
                    break;
                    case 1:
                        indexes = getRamSearch(dataList, indexes);
                        break;
                    case 2:
                        indexes = ssdDiskSearch(dataList, indexes);
                        break;
                    case 3:
                        indexes = osSearch(dataList, indexes);
                        break;
                    case 4:
                        indexes = colorSearch(dataList, indexes);
                        break;
                }
                sequence = correct(sequence, choice);
                n += 1;
                c -= 1;
                exit = 0;
                if (c == 1 || choice == 0) {
                    n = 0;
                    c = criteria.length;
                    sequence = rePrint(sequence);
                    exit = 1;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] getCriteria(Integer len) {
        String[] criteria = new String[len];
        criteria[0] = null;
        criteria[1] = " - Объём оперативной памяти";
        criteria[2] = " - Объём установленного SSD";
        criteria[3] = " - Операционная система";
        criteria[4] = " - Цвет";
        return criteria;
    }

    public static Integer[] correct(Integer[] sequence, int choice) {
        int s = choice;
        while (s < sequence.length - 1) {
            sequence[s] = sequence[s + 1];
            s += 1;
        }
        return sequence;
    }

    public static void printList(List<Laptop> dataList, Integer[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] == 0)
                System.out.println(dataList.get(i));
        }
        System.out.println();
    }

    public static Integer[] clearing(Integer[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = 0;
        }
        return indexes;
    }
    public static Integer[] rePrint(Integer[] sequence){
        for(int i = 0; i < sequence.length; i++)
            sequence[i] = i;
        return sequence;
    }

    public static Integer[] getRamSearch(List<Laptop> dataList, Integer[] indexes) {
        try {
            System.out.printf("\nМинимальный объём оперативной памяти (Гб) -> ");
            Scanner iScanner = new Scanner(System.in);
            Integer minRam = iScanner.nextInt();
            if (minRam < 0) {
                System.out.printf("\nВведите положительное число (Гб) -> ");
                minRam = iScanner.nextInt();
            }

            for (Integer i = 0; i < indexes.length; i++) {
                if (dataList.get(i).ramGb < minRam) indexes[i] = 1;
            }
            printList(dataList, indexes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return indexes;
    }

    public static Integer[] ssdDiskSearch(List<Laptop> dataList, Integer[] indexes) {
        try {
            Scanner iScanner = new Scanner(System.in);
            System.out.printf("\nМинимальный бъём установленного SSD в Тб (если менее 1 Тб, введите 0) -> ");
            Integer minSsd = iScanner.nextInt();
            while (minSsd < 0) {
                System.out.printf("\nВедите положительное число Тб (если менее 1 Тб, введите 0)");
                minSsd = iScanner.nextInt();
            }
            int tbSize = 1024;
            int tbIndicator = tbSize;
            int tbCheck;
            if (minSsd == 0 || minSsd > 36) {
                tbIndicator = 1;
                System.out.printf("\nМинимальный бъём установленного SSD в Гб -> ");
                minSsd = iScanner.nextInt();
                while (minSsd < 0) {
                    System.out.printf("\nВедите положительное число Гб -> ");
                    minSsd = iScanner.nextInt();
                }
            }

            for (Integer i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).representation.equals("Тб")) tbCheck = tbSize;
                else tbCheck = 1;
                if (dataList.get(i).ssdDiskSize * tbCheck < minSsd * tbIndicator)
                    indexes[i] = 1;
            }
            printList(dataList, indexes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return indexes;
    }

    public static Integer[] osSearch(List<Laptop> dataList, Integer[] indexes) {
        int kindsNumber = 5;
        System.out.println();
        String[] os = new String[kindsNumber];
        os[0] = "Windows 11 Professional";
        os[1] = "Windows 11 Home";
        os[2] = "Windows 10 Professional (64 bit)";
        os[3] = "Mac OS";
        os[4] = "Linux";
        for (int c = 0; c < os.length; c++) {
            int pos = c + 1;
            System.out.println(pos + " - " + os[c]);
        }
        try {
            Scanner iScanner = new Scanner(System.in);
            System.out.printf("\nПод каким числом желаемая ОС -> ");
            Integer osNum = iScanner.nextInt();
            while (osNum <= 0 || osNum > kindsNumber) {
                System.out.printf("\nВыберете из указанных чисел -> ");
                osNum = iScanner.nextInt();
            }
            for (Integer i = 0; i < indexes.length; i++)
                if (dataList.get(i).osName.equals(os[osNum - 1])) ;
                else indexes[i] = 1;
            printList(dataList, indexes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return indexes;
    }

    public static Integer[] colorSearch(List<Laptop> dataList, Integer[] indexes) {
        int colorsNumber = 4;
        String[] color = new String[colorsNumber];
        color[0] = "серый";
        color[1] = "чёрный";
        color[2] = "серебристый";
        color[3] = "белый";
        System.out.println();
        for (int c = 0; c < color.length; c++) {
            int pos = c + 1;
            System.out.println(pos + " - " + color[c]);
        }
        try {
            Scanner iScanner = new Scanner(System.in);
            System.out.printf("Под каким числом желаемый цвет -> ");
            Integer colorPos = iScanner.nextInt();
            while (colorPos <= 0 || colorPos > color.length) {
                System.out.printf("\nВыберите из указанных чисел -> ");
                colorPos = iScanner.nextInt();
            }
            for (Integer i = 0; i < indexes.length; i++)
                if (dataList.get(i).color.equals(color[colorPos - 1])) ;
                else indexes[i] = 1;
            printList(dataList, indexes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return indexes;
    }

    public static List<Laptop> addingData() {
        List<Laptop> dataList = new ArrayList<>();
        Laptop lp0 = new Laptop("ASUS P1511CEA", 8, 256,
                "Гб", "Windows 11 Professional", "серый");
        dataList.add(lp0);

        Laptop lp1 = new Laptop("Lenovo ThinkBook 14 Gen 4", 16, 512, "Гб",
                "Windows 11 Professional", "серый");
        dataList.add(lp1);
        Laptop lp2 = new Laptop("ASUS B1500CBA ExpertBook B1", 8, 512, "Гб",
                "Windows 11 Professional", "чёрный");
        dataList.add(lp2);
        Laptop lp3 = new Laptop("ASUS UX325EA Zenbook 13 OLED", 8, 512, "Гб",
                "Windows 11 Home", "серый");
        dataList.add(lp3);
        Laptop lp4 = new Laptop("ASUS E1504FA Vivobook Go ", 16, 512, "Гб",
                "Windows 11 Home", "чёрный");
        dataList.add(lp4);
        Laptop lp5 = new Laptop("Digma Pro Sprint M 16", 8, 256, "Гб",
                "Windows 11 Professional", "серебристый");
        dataList.add(lp5);
        Laptop lp6 = new Laptop("Huawei MateBook D 16 RLEF-X", 16, 512, "Гб",
                "Windows 11 Home", "серый");
        dataList.add(lp6);
        Laptop lp7 = new Laptop("HP ProBook 450 G10", 16, 256, "Гб",
                "Windows 11 Professional", "серебристый");
        dataList.add(lp7);
        Laptop lp8 = new Laptop("Dell Vostro 3520", 8, 256, "Гб",
                "Windows 11 Home", "чёрный");
        dataList.add(lp8);
        Laptop lp9 = new Laptop("ASUS UX3402VA ZenBook 14", 16, 1, "Тб",
                "Windows 11 Home", "серебристый");
        dataList.add(lp9);
        Laptop lp10 = new Laptop("ASUS GU603ZX ROG Zephyrus M16", 32, 2, "Тб",
                "Windows 11 Professional", "чёрный");
        dataList.add(lp10);
        Laptop lp11 = new Laptop("Lenovo IdeaPad 1 15IGL7", 4, 128, "Гб",
                "Windows 11 Home", "серый");
        dataList.add(lp11);
        Laptop lp12 = new Laptop("MSI Bravo 17 ", 16, 1, "Тб",
                "Windows 11 Home", "чёрный");
        dataList.add(lp12);
        Laptop lp13 = new Laptop("ASUS X515EA", 4, 256, "Гб",
                "Windows 11 Home", "серебристый");
        dataList.add(lp13);
        Laptop lp14 = new Laptop("Digma EVE P5416", 4, 128, "Гб",
                "Windows 11 Professional", "серебристый");
        dataList.add(lp14);
        Laptop lp15 = new Laptop("Apple MacBook Air 13", 16, 512, "Гб",
                "Mac OS", "синий");
        dataList.add(lp15);
        Laptop lp16 = new Laptop("Apple MacBook Air 15", 8, 256, "Гб",
                "Mac OS", "синий");
        dataList.add(lp16);
        Laptop lp17 = new Laptop("Maibenben M555 (M5551SF0LWRE0)", 16, 512, "Гб",
                "Linux", "белый");
        dataList.add(lp17);
        Laptop lp18 = new Laptop("Maibenben M555 (M5551SB0LWRE0)", 8, 512, "Гб",
                "Linux", "белый");
        dataList.add(lp18);
        Laptop lp19 = new Laptop("Apple MacBook Pro 14", 18, 512, "Гб",
                "Mac OS", "чёрный");
        dataList.add(lp19);
        Laptop lp20 = new Laptop("Apple MacBook Pro 16 (MRW13LL/A)", 18,
                512, "Гб", "Mac OS", "чёрный");
        dataList.add(lp20);
        Laptop lp21 = new Laptop("Apple MacBook Pro 16 (MRW23LL/A)", 36,
                512, "Гб", "Mac OS", "чёрный");
        dataList.add(lp21);
        Laptop lp22 = new Laptop("HIPER G16 (G16RTX3070B10400LX)", 16,
                1, "Тб", "Linux", "чёрный");
        dataList.add(lp22);
        Laptop lp23 = new Laptop("Maibenben M565 (M5651HB0LBRE0)", 8,
                512, "Гб", "Linux", "чёрный");
        dataList.add(lp23);
        Laptop lp24 = new Laptop("Acer TravelMate P214-41-G2-R0JA", 8,
                256, "Гб", "Windows 10 Professional (64 bit)", "чёрный");
        dataList.add(lp24);
        return dataList;
    }
}

