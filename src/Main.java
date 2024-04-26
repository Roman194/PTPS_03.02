import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sequenceLength;
        int sequenceDepth;
        while (true){
            try {
                String inputDigits = sc.nextLine();

                String [] inpDigits = inputDigits.split(" ");
                sequenceLength = Integer.parseInt(inpDigits[0]);
                sequenceDepth = Integer.parseInt(inpDigits[1]);
                break;
            } catch (java.lang.NumberFormatException | java.lang.ArrayIndexOutOfBoundsException e){
                System.out.println("Wrong input parameters: try again");
            }
        }
        System.out.println(sequenceLength+" "+sequenceDepth);
        if(sequenceLength % 2 == 1){
            System.out.println("Wrong sequence: Input length can't be odd");
        }else{
            if(sequenceLength / 2 < sequenceDepth){
                System.out.println("0");
            }else if(sequenceLength / 2 == sequenceDepth || sequenceDepth == 1){
                System.out.println("1");
            }else{
                System.out.println(bracketsCombine(sequenceDepth, sequenceLength / 2, 0, 0, (sequenceLength / 2)/ sequenceDepth));
            }
        }
    }

    public static  int bracketsCombine(int sequenceDepth, int externalBrackets, int allPreviousBracketsCount, int previousOneInternalBrackets, int maxDividedCase){//3 3 1 4

        int rightSequencesCount = 0;
        int internalBrackets = 0;
        int minInternalBracketsCount = 1;
        while(externalBrackets > sequenceDepth - 1){ //приведение к первому виду
            internalBrackets++;
            externalBrackets--;
        }
        if(previousOneInternalBrackets >= internalBrackets || previousOneInternalBrackets == 0){
            if(allPreviousBracketsCount + 1 >= maxDividedCase && previousOneInternalBrackets == internalBrackets){
                rightSequencesCount += 1;
            }else{
                rightSequencesCount += (allPreviousBracketsCount + 1);
            }
        }
        if(internalBrackets == 0){

            for(int i = 0; i< externalBrackets - 1; i++){//если не добрали до глубины
                //здесь не учитываем внутренние скобки прошлой итерации
                rightSequencesCount += (allPreviousBracketsCount + i + 2);
            }
        }else{
            if(allPreviousBracketsCount > 0)
                minInternalBracketsCount = 0;

            while(internalBrackets > minInternalBracketsCount){ //если добрали до глубины
                internalBrackets--;
                externalBrackets++;
                if(previousOneInternalBrackets >= internalBrackets || previousOneInternalBrackets == 0){ //исключение повторяющихся случаев
                    int currExternalBrackets;
                    if(previousOneInternalBrackets == 0)
                        currExternalBrackets = externalBrackets - (sequenceDepth - 2);//вычесть зарезервированные скобки
                    else
                        currExternalBrackets = externalBrackets;
                    if(currExternalBrackets > 1 && (internalBrackets > 0)){
                        //System.out.println("Hey");
                        rightSequencesCount+= bracketsCombine(sequenceDepth, currExternalBrackets - 1, allPreviousBracketsCount + 1, internalBrackets, maxDividedCase);
                    }else{
                        rightSequencesCount += (allPreviousBracketsCount + currExternalBrackets);//5 6 7
                        //System.out.println(allPreviousBracketsCount);
                    }
                }

            }
        }
        return rightSequencesCount;
    }

}
