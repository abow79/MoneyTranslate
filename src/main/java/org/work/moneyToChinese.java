package org.work;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class moneyToChinese {
    public static void main(String[] args){
        moneyToChinese x=new moneyToChinese();
        BigDecimal c1=new BigDecimal("15000.48");
        String a=x.valueToString("3250000");
        System.out.println(a);
        System.out.println(a.getClass().getSimpleName());
        //System.out.println(c1);
        String f=x.moneyInChinese("9600000000000000");
        System.out.println(f);
    }
    public String moneyInChinese(BigDecimal x){
        String result=moneyInChinese(x.toString());
        return result;
    }

    public String moneyInChinese(String x){
        String result=null;
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





        //判斷是否為0元(因為之後的判斷如果遇到0會做特殊處理)
        if("0".equals(x)){
            return result="零元";
        }



        //將字串轉入int陣列(有利於後續判斷)
        int[] integers=new int[x.length()];
        for (int i = 0; i <x.length() ; i++) {
            integers[i]=Integer.parseInt(x.substring(i,i+1));
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
        List<String> resultList=new ArrayList<>();
        int[] duplicateNum = new int[0];
        String[] s=key.split("");
        for(int i=s.length-1;i>=0;i--){
            if(s[i].equals("萬")&&resultList.contains("萬")|| s[i].equals("億")&&resultList.contains("億")|| s[i].equals("兆")&& resultList.contains("兆")){
                continue;
            }else {
                resultList.add(s[i]);
            }
        }
        String out="";
        for (int i = resultList.size()-1; i >=0 ; i--) {
            out+=resultList.get(i);
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

    public String moneyInEnglish(BigDecimal x){
        String result=moneyInEnglish(x.toString());
        return result;
    }

    public String moneyInEnglish(String x){
        //以下是各個數字的英文都集中在這邊
        final String[] numbers={"","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
        final String[] tenNumbers={"Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
        final String[] tensNumbers={"Twenty","Thirty","Fourty","Fifty","Sixty","Seventy","Eighty","Ninety"};
        //這區要特別講一下(Billion=10億,如果要表達1億的話要寫成One Hundred Million,unit的陣列裡面最後一個Trillion是兆)
        final String[] unit={"Hundred","Thousand","Million","Billion","Trillion"};

        String result = "";

        //如果單位超過兆的話就會回復說超出受理範圍(16位是到千兆所以如果超過就無法受理)
        if(x.length()>16){
            System.out.println("抱歉!您輸入的金額超出系統受理的範圍");
        }

        //先判斷輸入的參數是否為空白或是非數字
        //如果傳進來的數字是負的,也會在這邊一樣被過濾掉(因為這邊的正規表示法沒有把負號算進去)
        if(x.isEmpty() || x.matches("\\d+")){
            if(x.isEmpty()) {
                System.out.println("");
            }
            System.out.println("格式不符!請重新輸入,謝謝");
        }

        //如果輸入進來的數字是0的話,這邊就會直接回傳0對應的英文給他(Zero)
        if("0".equals(x)){
            return result="Zero";
        }

        //先將輸入進來的金額轉成int型態然後將數字以3個單位為一組加上分隔符去區分(以利於之後的計算)
        //這邊就是把輸入進來的數字以三個為一組然後依序加入到digitGroups
        int input_number=Integer.parseInt(x);
        int[] digitGroups=new int[]{0,0,0,0,0};
        for(int i=0;i<digitGroups.length;i++){
            digitGroups[i]=input_number%1000;
            input_number=input_number/1000;
        }

        //處理每段金額轉英文(百位+十位+各位)
        String combine1="";
        String combine2="";
        String[] groupText=new String[]{"","","","",""};
        for (int i = 0; i <5 ; i++) {
            int hundred=digitGroups[i]/100;
            int tenUnits=digitGroups[i]%100;
        }

































        return result;

    }

}