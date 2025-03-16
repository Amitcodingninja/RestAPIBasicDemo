package com.api;

import javax.naming.PartialResultException;

public class Main {
    public static void main(String[] args) {
        String s = "Dinakar";
//        char[] charArray = s.toCharArray();
//        for (int i = charArray.length-1; i >= 0; i--) {
//            System.out.print(charArray[i]+" ");
//        }
        System.out.println(s.toUpperCase());
    }
}


/*
//        List<String> errorList = new ArrayList<>();
//        Set<String> fieldNames = new HashSet<>(Arrays.asList("name", "address", "salary"));
//        for (Entry<String, Object>:map.entrySet()){
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            if (!fieldNames.contains(key)) {
//                errorList.add("Key is not correct " + key);
//            }
//            if (key.equals("name")) {
//                String name = (String) value;
//                if (name.length() < 2 || name.length() > 30) {
//                    errorList.add("Name Size Validation ERROR");
//                }
//            }
//            if (key.equals("address")) {
//                String address = (String) value;
//                if (address.length() < 2 || address.length() > 100) {
//                    errorList.add("Address Size Validation ERROR");
//                }
//            }
//            if (key.equals("salary")) {
//                String salary = (String) value;
//                if (salary.length() < 2 || salary.length() > 100) {
//                    errorList.add("Invalid Salary");
//                }
//            }
//
//        }
 */
