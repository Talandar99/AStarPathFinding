package basic_components;
public class Map {
    public static String[] map = new String[9];
    public static boolean[][] mapBin = new boolean[16][9];

    public static void TranslateBinary(){
        map[0]="0000000000000010";
        map[1]="1110000000011110";
        map[2]="0101010011110010";
        map[3]="0101010010010010";
        map[4]="0100100000010010";
        map[5]="0101011110010010";
        map[6]="0011010011100110";
        map[7]="0000000000101100";
        map[8]="0001110000100001";

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {
                if(map[i].charAt(j)=='1'){
                    mapBin[j][i]=false;
                    //System.out.println(mapBin[j][i]);
                }
                else{
                    mapBin[j][i]=true;
                    //System.out.println(mapBin[j][i]);
                }
            }
        }
    }
}
