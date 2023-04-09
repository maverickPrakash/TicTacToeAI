import java.util.Scanner;

public class TicTacToe {
    int[][] Board;
    String PlayerSymbol;
    int PlayerNumber;
    String BotSymbol;
    int BotNumber;
    int countPlay;
    boolean player1;
    public TicTacToe(){
        PlayerNumber= 1;
        BotNumber=2;
        PlayerSymbol="X";
        BotSymbol="O";
        Board =new int[3][3];
        countPlay=0;
        player1=true;

    }
    public  int getUserInput(){
        Scanner input = new Scanner(System.in);
        int userIn=-1;
        while(userIn<1 || userIn>9){
            System.out.println("Enter the position it should be between 1-9");
            userIn = input.nextInt();
        }
        return userIn;

    }
    public boolean BotPlay(){
        for (int i = 0; i <9 ; i++) {
            int column=i/3;
            int row=i%3;
            if(Board[column][row]==0){
                Board[column][row]=2;

                return true;
            }
        }
        return false;
    }


    public boolean PlayerSymbol(){
        int PlaceSymbol = getUserInput();
        int row=(PlaceSymbol-1)/3;
        int column=(PlaceSymbol-1)%3;
        if(Board[row][column]==0){
            Board[row][column]=1;

            return true;
        }
        return false;
    }
    public boolean checkWinner(){
        for (int i = 0; i < 3; i++) {
            if(Board[i][0]==Board[i][1] && Board[i][1]==Board[i][2] && Board[i][0]!=0 )
                return true;
            if(Board[0][i]==Board[1][i] && Board[1][i]==Board[2][i] && Board[0][i]!=0 )
                return true;

        }

        if(Board[0][0]==Board[1][1] && Board[1][1]==Board[2][2] && Board[0][0]!=0 )
            return true;
        if(Board[0][2]==Board[1][1] && Board[2][0]==Board[1][1] && Board[0][2]!=0 )
            return true;
        return false;

    }
    public boolean isBoardFull() {
        return countPlay == 9;
    }

    public boolean isGameOver() {
        return checkWinner() || isBoardFull();
    }

    public String weakAI(){

        System.out.println(toString());
        while(!isGameOver()){
            countPlay++;
            if(player1){
                if(PlayerSymbol()){
                    player1=!player1;

                    System.out.println(toString());
                }else {
                    System.out.println("Position is already fill");
                    weakAI();
                }

            }else {
                BotPlay();
                player1=!player1;
                System.out.println(toString());
            }
        }
        int sc = score();
        if(sc==0){
            return "Draw";
        }else if(sc==10){
            return "AI Wins";
        }else {
            return "Player Wins";
        }

    }
    public int score() {
        if (checkWinner()) {
            if (player1) {
                return -10;
            } else {
                return 10;
            }
        } else {
            return 0;
        }
    }


    public int[] getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Board[i][j] == 0) {
                    Board[i][j] = BotNumber;
                    countPlay++;
                    int score = miniMax(8,false);
                    countPlay--;
                    Board[i][j] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }

        }

        return bestMove;
    }

    public int miniMax(int depth,boolean player){
            System.out.println(toString());

            if(checkWinner() && !player){
                return 10*(depth);
            }
            if(checkWinner() && player){
                return -10*(depth);

            }

        if(countPlay==10) {

            return 0;
        }

        if (player) {

            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j <3 ; j++) {
                    if (Board[i][j] == 0) {
                        Board[i][j] = BotNumber;
                        countPlay++;
                        int score = miniMax(depth - 1,false);
                        countPlay--;
                        Board[i][j] = 0;
                        bestScore = Math.max(bestScore, score);


                    }
                }
            }


            return bestScore;

        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j <3 ; j++) {
                    if (Board[i][j] == 0) {
                        Board[i][j] = PlayerNumber;
                        countPlay++;
                        int score = miniMax(depth - 1,true);
                        Board[i][j] = 0;
                        countPlay--;
                        bestScore = Math.min(bestScore, score);


                    }
                }
            }


            return bestScore;

        }


    }

    public String strongAI(){

        System.out.println(toString());
        while(!isGameOver()){
            countPlay++;
            if(player1){
                if(PlayerSymbol()){
                    player1=!player1;

                    System.out.println(toString());
                }else {
                    System.out.println("Position is already fill");
                    countPlay--;
                    strongAI();
                }

            }else {
                int[] place=getBestMove();
                int row = place[0];
                int column = place[1];
                Board[row][column]=BotNumber;

                player1=!player1;
                System.out.println(toString());
            }

        }
        int sc = score();
        if(sc==0){
            return "Draw";
        }else if(sc==-10){
            return "AI Wins";
        }else {
            return "Player Wins";
        }

    }




    @Override
    public String toString() {
        String s="--------\n";;

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j <3 ; j++) {
                if(Board[i][j]==0){
                    s+=" | ";
                }else if(Board[i][j]==1){
                    s+=PlayerSymbol+"|";
                } else if (Board[i][j]==2) {
                    s+=BotSymbol+"|";
                }

            }
            s+="\n--------\n";

        }
        return s;
    }
}