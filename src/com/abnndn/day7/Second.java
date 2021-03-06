package com.abnndn.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Second {

    List<ArrayList<Entity>> graph;

    public BigDecimal recur(BigDecimal ans, Integer color) {
        if (graph.get(color).size() == 0) {
            System.out.println(color + " pp " + ans);
            return ans;
        }
        BigDecimal internal = BigDecimal.ZERO;
        for (int i=0;i<graph.get(color).size();i++) {
            internal = internal.add(recur(new BigDecimal(graph.get(color).get(i).getQuantity()), graph.get(color).get(i).getBagNumber()));
        }
        System.out.println(internal + " pp " + ans);
        return internal.multiply(ans).add(ans);
    }

    public void calculateAnswer() {
        try {
            List<String> input = new ArrayList<>();
            Map<String, Integer> colorMapper = new HashMap<>();

            graph = new ArrayList<>();
            for (int i=0;i<1000;i++) {
                graph.add(new ArrayList<>());
            }

            File myObj = new File("/Users/abhmitta/Desktop/AdventOfCode2020/src/com/abnndn/day7/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                input.add(data);
            }
            myReader.close();

            int colorNumbering = 1;
            for (String s: input) {
                List<String> splitted = Arrays.asList(s.split(" bags contain "));

                if (!colorMapper.containsKey(splitted.get(0))) {
                    colorMapper.put(splitted.get(0), colorNumbering++);
                    System.out.println(splitted.get(0) + ":: " + colorNumbering);
                }
                int outerBagNumber = colorMapper.get(splitted.get(0));

                List<String> bagsInside = Arrays.asList(splitted.get(1).split(" bag"));

                //5 bright purple
                //s, 1 pale black
                //, 5 muted lime
                //s.

                for (int i=0;i<bagsInside.size()-1;i++) {
                    List<String> entity = Arrays.asList(bagsInside.get(i).split(" "));

                    if (entity.get(0).equals("no")) {
                        continue;
                    }

                    String colour = entity.get(entity.size()-2) + " " + entity.get(entity.size()-1);

                    if (!colorMapper.containsKey(colour)) {
                        colorMapper.put(colour, colorNumbering++);
                        System.out.println(colour + ":: " + colorNumbering);
                    }

                    Entity entity1 = Entity.builder()
                            .bagNumber(colorMapper.get(colour))
                            .quantity(Integer.valueOf(entity.get(entity.size()-3)))
                            .build();

                    graph.get(outerBagNumber).add(entity1);
                }
            }

            int goldBagNumber = colorMapper.get("shiny gold");
            System.out.println("goldBagNumber: " + goldBagNumber);

            for (int i=1;i<15;i++) {
                System.out.print(i + ": ");
                for (int j=0;j<graph.get(i).size();j++) {
                    System.out.print(graph.get(i).get(j).getBagNumber() + "," + graph.get(i).get(j).getQuantity() + " | ");
                }
                System.out.println();
            }

            BigDecimal ans = recur(BigDecimal.ONE, goldBagNumber);

            System.out.println(ans.subtract(BigDecimal.ONE));

        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
