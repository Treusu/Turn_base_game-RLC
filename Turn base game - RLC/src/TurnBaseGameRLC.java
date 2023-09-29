import java.util.*;

class Character {
    String name;
    int health;
    int attack;
    int speed;
    int defense;
    int maxHealth;  // Add maximum health to track the original health value

    public Character(String name, int health, int attack, int speed, int defense) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.speed = speed;
        this.defense = defense;
        this.maxHealth = health;
    }

    // Implement methods for character abilities if needed

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);  // Ensure health doesn't exceed the maximum
    }
}

class Battle {
    ArrayList<Character> playersTeam;
    ArrayList<Character> enemies;

    public Battle(ArrayList<Character> playersTeam, ArrayList<Character> enemies) {
        this.playersTeam = playersTeam;
        this.enemies = enemies;
    }

    public void simulateBattle() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playersTurn = true;

        while (!playersTeam.isEmpty() && !enemies.isEmpty()) {
            // Display players' and enemies' HP before the turn
            System.out.println("Players' HP before the turn:");
            for (Character player : playersTeam) {
                System.out.println(player.name + ": " + player.health);
            }
            System.out.println("Enemies' HP before the turn:");
            for (Character enemy : enemies) {
                System.out.println(enemy.name + ": " + enemy.health);
            }
            if (playersTurn) {
                // Player's turn to attack or defend
                Character attacker = playersTeam.get(random.nextInt(playersTeam.size()));

                System.out.println("Player " + attacker.name + "'s turn:");
                System.out.println("1. Attack");
                System.out.println("2. Defend");
                System.out.println("3. Heal");  // Option for healing skill
                System.out.print("Choose your action: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    // Player chooses to attack
                    System.out.println("Choose an enemy to attack:");
                    for (int i = 0; i < enemies.size(); i++) {
                        System.out.println((i + 1) + ". " + enemies.get(i).name);
                    }
                    int enemyChoice = scanner.nextInt();

                    if (enemyChoice >= 1 && enemyChoice <= enemies.size()) {
                        Character target = enemies.get(enemyChoice - 1);
                        int damage = attacker.attack - target.defense;
                        damage = Math.max(damage, 0); // Ensure damage is non-negative

                        target.health -= damage;

                        System.out.println(attacker.name + " attacks " + target.name + " for " + damage + " damage!");

                        if (target.health <= 0) {
                            System.out.println(target.name + " is defeated!");
                            enemies.remove(target);
                        }
                    } else {
                        System.out.println("Invalid enemy choice. Player " + attacker.name + " attacks by default.");
                        // Implement default attack logic if the enemy choice is invalid
                    }
                } else if (choice == 2) {
                    // Player chooses to defend
                    attacker.defense += 3;
                    System.out.println(attacker.name + " is defending! Defense increased by 3.");
                } else if (choice == 3) {
                    // Player chooses to use the healing skill
                    System.out.println("Choose a target to heal:");
                    for (int i = 0; i < playersTeam.size(); i++) {
                        System.out.println((i + 1) + ". " + playersTeam.get(i).name);
                    }
                    int targetChoice = scanner.nextInt();

                    if (targetChoice >= 1 && targetChoice <= playersTeam.size()) {
                        Character target = playersTeam.get(targetChoice - 1);
                        int healingAmount = 30;  // You can adjust the healing amount as needed
                        target.heal(healingAmount);

                        System.out.println(attacker.name + " heals " + target.name + " for " + healingAmount + " health!");
                    } else {
                        System.out.println("Invalid target choice. Healing skill wasted.");
                    }
                } else {
                    System.out.println("Invalid choice. Player " + attacker.name + " attacks by default.");
                    // Implement default attack logic if the choice is invalid
                }
            } else {
                // Enemies' turn to attack players
                for (Character enemy : enemies) {
                    Character target = playersTeam.get(random.nextInt(playersTeam.size()));
                    int damage = enemy.attack - target.defense;
                    damage = Math.max(damage, 0); // Ensure damage is non-negative

                    target.health -= damage;

                    System.out.println(enemy.name + " attacks Player " + target.name + " for " + damage + " damage!");

                    // Display updated HP after the attack
                    System.out.println("Player " + target.name + " HP: " + target.health);

                    if (target.health <= 0) {
                        System.out.println("Player " + target.name + " is defeated!");
                        playersTeam.remove(target);
                    }
                }
            }

            // Display players' HP after the enemies' turn
            System.out.println("Players' HP after enemies' attack:");
            for (Character player : playersTeam) {
                System.out.println(player.name + ": " + player.health);
            }
            System.out.println("Enemies' HP after players' attack:");
            for (Character enemy : enemies) {
                System.out.println(enemy.name + ": " + enemy.health);
            }

            playersTurn = !playersTurn;
        }


        if (playersTeam.isEmpty()) {
            System.out.println("Game over. Enemies win!");
        } else {
            System.out.println("Congratulations! Players win!");
        }
    }
}

public class TurnBaseGameRLC {
    public static void main(String[] args) {
        // Create characters for players and enemies
        ArrayList<Character> playersTeam = new ArrayList<>();
        playersTeam.add(new Character("Bronya", 100, 20, 26, 5));
        playersTeam.add(new Character("Natasha", 120, 18, 26, 4));

        ArrayList<Character> enemies = new ArrayList<>();
        enemies.add(new Character("Goblin1", 80, 22, 25, 4));
        enemies.add(new Character("Goblin2", 90, 18, 25, 4));

        // Start the battle
        Battle battle = new Battle(playersTeam, enemies);
        battle.simulateBattle();
    }
}