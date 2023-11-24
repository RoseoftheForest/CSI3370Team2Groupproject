package adventuregame;

import java.util.Scanner;

import adventuregame.Room.FightRoom;

public class Driver {
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Game game = Game.instance();
        System.out.println(game);
        game.addPlayer("Player1");
        
        game.nextRoom(1);
        System.out.println("Room: " + game.getPlayer(1).getCurrentRoom().getClass());
        fightRoom(scan);
        game.nextRoom(1);
        System.out.println("Room: " + game.getPlayer(1).getCurrentRoom().getClass());
        fightRoom(scan);
        
        System.out.println("GAME");
        scan.close();
    }

    public static void fightRoom(Scanner scan) {
        //Scanner scan = new Scanner(System.in);
        Game game = Game.instance();
        System.out.println(game);
        FightRoom room = (FightRoom) game.getPlayer(1).getCurrentRoom();
        System.out.println(room.getMonster().getName() + " : " + room.getMonster().getHealth() + " / " + room.getMonster().getStats().getMaxHealth());
        System.out.println(game.getPlayer(1).getName() + " : " + game.getPlayer(1).getHealth() + " / " + game.getPlayer(1).getStats().getMaxHealth());
        while (game.getPlayer(1).isAlive() && room.getMonster().isAlive()) {
            System.out.println("Use special attack? Enter 1: ");
            int input = scan.nextInt();
            if (input == 1) {
                game.useSpecialAttack(1);
            } else {
                game.useBasicAttack(1);
            }
            System.out.println(room.getMonster().getName() + " : " + room.getMonster().getHealth() + " / " + room.getMonster().getStats().getMaxHealth());
            System.out.println(game.getPlayer(1).getName() + " : " + game.getPlayer(1).getHealth() + " / " + game.getPlayer(1).getStats().getMaxHealth());
        }
    }
}
