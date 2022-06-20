package org.work;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class moneyToChinese {
    public static void main(String[] args){
        moneyToChinese x=new moneyToChinese();
        BigDecimal c1=new BigDecimal("15000.48");
        String a=x.valueToString("3250000");
        System.out.println(a);
        System.out.println(a.getClass().getSimpleName());
        //System.out.println(c1);
        String f=x.moneyInChinese("987654321");
        System.out.println(f);
    }
    public String moneyInChinese(BigDecimal x){
        String result=moneyInChinese(x.toString());
        return result;
    }

    public String moneyInChinese(String x){
        String result=null;
        String tmp=null;
        String[] splits=null;
        String integerStr;
        String decimalStr;
        final String[] chineseNumbers={"零","壹","貳","參","肆","伍","陸","柒","捌","玖"};
        final String[] chinese_Unit={"元","拾","佰","仟","萬","拾萬","佰萬","仟萬","億","拾億","佰億","仟億","兆","拾兆","佰兆","仟兆"};

        //如果單位超過系統能計算的單位範圍(千兆)
        if(x.length()>chinese_Unit.length){
            System.out.println("抱歉!您輸入的金額超出系統受理的範圍");

        }


        //先判斷輸入的參數是否為空白或是非數字
        //如果傳進來的數字是負的,也會在這邊一樣被過濾掉(因為這邊的正規表示法沒有把負號算進去)
        if(x.isEmpty() || !x.matches("\\d+")){
            if(x.isEmpty()){
                //System.out.println("無內容,請重新輸入!");
                return "無內容,請重新輸入!";
            }
            return "規格不符!請重新輸入,謝謝";
        }

        //初始化：分離整數部分和小數部分
        if (x.indexOf(".")!=-1) {
            splits=x.split("\\.");
            integerStr=splits[0];
            decimalStr=splits[1];
            System.out.println("整數部分:"+integerStr);
            System.out.println("小數部分:"+decimalStr);
        }else {
            integerStr=x;
            decimalStr="";
        }



        //判斷是否為0元(因為之後的判斷如果遇到0會做特殊處理)
        if("0".equals(x)){
            return result="零元";
        }



        //將字串轉入int陣列(有利於後續判斷)
        int[] integers=new int[integerStr.length()];
        for (int i = 0; i <integerStr.length() ; i++) {
            integers[i]=Integer.parseInt(integerStr.substring(i,i+1));
        }

        //將整數部分轉為大寫金額
        int length=integers.length;
        String key="";
        for(int i=0;i<integers.length;i++){
            if(integers[i]==0 && i==integers.length-1)
            {
                key+="元";
            }
            if(integers[i]==0) {
                continue;
            }

            if(integers[i]!=0) {
                int lastIndexValue=integers.length-1;
                key += chineseNumbers[integers[i]] + chinese_Unit[lastIndexValue-i];
            }
        }

        //這邊就是用上面的方法這樣找的話單位會有重複的(玖仟兆陸佰兆元)
        //然後下面這邊是把key從字串變成陣列後再丟入arraylist時去做判斷
        List<String> list=new ArrayList<>();
        int[] duplicateNum = new int[0];
        String[] s=key.split("");
        for(int i=s.length-1;i>=0;i--){
            if(s[i].equals("萬")&&list.contains("萬")|| s[i].equals("億")&&list.contains("億")|| s[i].equals("兆")&& list.contains("兆")){
                continue;
            }else {
                list.add(s[i]);
            }
        }
        String out="";
        for (int i = list.size()-1; i >=0 ; i--) {
            out+=list.get(i);
        }
        result=out;
        return result;
    }


    public String valueToString(String x){
        BigDecimal bg=new BigDecimal(x);
        String result=valueToString(bg);
        return result;
    }
    public String valueToString(BigDecimal x) {
       //double tmp=Double.parseDouble(x);
       DecimalFormat df=new DecimalFormat("#,###");
       String result=df.format(x);
       return result;
    }
}