package com.example.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo4Application {

  public static void main(String[] args) {
    SpringApplication.run(Demo4Application.class, args);
    Scanner sc = null;
    Map<String, List<String>> dic = new HashMap<>();
    int i = -1;
    try {
      File file = new File(
          "/Users/bianca.radu/Software/demo4/src/main/java/com/example/demo/example2.in");
      sc = new Scanner(file);
      String line;
      BufferedWriter writer = new BufferedWriter(new FileWriter(
          "/Users/bianca.radu/Software/demo4/src/main/java/com/example/demo/example.out"));
      while (sc.hasNextLine()) {
        line = sc.nextLine();
        try {
          int number = Integer.parseInt(line);
          i++;
        } catch (NumberFormatException ex) {
          if (i > 0) {
            if (i % 2 != 0) {
              addToDictionary(line, dic);
            } else {
              printMap(dic);
              // test(line, dic, writer);
            }
          }
        }
      }
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (sc != null) {
        sc.close();
      }
    }
  }

  private static void test(String line, HashMap<String, String> dic, BufferedWriter writer) {

  }

  private static void addToDictionary(String line, Map<String, List<String>> dic) {
    String items[] = line.split(" ");
    for (int i = 0; i < items.length - 1; i++) {
      if (dic.containsKey(items[i])) {
        List<String> l1=dic.get(items[i]);
        List<String> newL1=new ArrayList<>();
        newL1.addAll(l1);
        newL1.add(items[i+1]);
        dic.put(items[i], newL1);
        if(dic.containsKey(items[i+1])){
          List<String> l2=dic.get(items[i+1]);
          List<String> newL2=new ArrayList<>();
          newL2.addAll(l2);
          newL2.add(items[i]);
          dic.put(items[i+1], newL2);
        }else{
          dic.put(items[i+1], Arrays.asList(items[i]));
        }
      }else{
        if(dic.containsKey(items[i+1])){
          List<String> l3=dic.get(items[i+1]);
          List<String> newL3=new ArrayList<>();
          newL3.addAll(l3);
          newL3.add(items[i]);
          dic.put(items[i+1], newL3);
          dic.put(items[i],Arrays.asList(items[i+1]));
        } else if(dic.containsValue(items[i])){
          Set<String> keys = getKeysByValue(dic, items[i]);
          List<String> l4=new ArrayList<>();
          l4.addAll(keys);
          l4.add(items[i+1]);
          dic.put(items[i], l4);
          l4.remove(items[i+1]);
          l4.add(items[i]);
          dic.put(items[i+1],l4);
        }else if(dic.containsValue(items[i+1])){
          Set<String> keys = getKeysByValue(dic, items[i+1]);
          List<String> l4=new ArrayList<>();
          l4.addAll(keys);
          l4.add(items[i]);
          dic.put(items[i+1], l4);
          l4.remove(items[i]);
          l4.add(items[i+1]);
          dic.put(items[i],l4);
        }
        else{
          dic.put(items[i], Arrays.asList(items[i + 1]));
          dic.put(items[i + 1], Arrays.asList(items[i]));
        }
      }
    }
  }

  private static void printMap(Map<String, List<String>> dic) {
    dic.forEach((K, V) -> System.out.println(K + " : " + V + "\n"));
  }

  public static <T, E> Set<T> getKeysByValue(Map<T, List<E>> map, E value) {
    return map.entrySet()
        .stream()
        .filter(entry -> entry.getValue().contains(value))
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }

}
