package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {350, 270, 250, 400, 600, 400, 300, 200};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 30, 20, 25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int round_number = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static void round() {
        round_number++;
        chooseDefence();
        thor();
        bossHits();
        heroesHit();
        medic();
        golem();
        lucky();
        berserk();
        printStatistics();
    }

    public static void medic() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                if (heroesHealth[i] == heroesHealth[3]) {
                    continue;
                }
                heroesHealth[i] += 35;
                System.out.println("Медик вылечил героя: " + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void golem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] > 0) {
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[4] -= bossDamage / 5;
            }
        }
        System.out.println("Golem взял на себя 1/5 урона от Босса");
    }

    public static void lucky() {
        Random random = new Random();
        boolean buf = random.nextBoolean();
        if (buf == true) {
            heroesHealth[5] += 40;
            System.out.println("Лаки увернулся от босса");
        }
    }

    public static void berserk() {
        if (heroesHealth[6] > 0) {
            bossHealth -= bossDamage / 2;
            heroesHealth[6] += bossDamage / 2;
            System.out.println("Берсерк заблокировал часть урона от босса");
        }
    }

    public static void thor() {
        Random random = new Random();
        boolean buf = random.nextBoolean();
        if (buf == true) {
            bossDamage = 0;
            System.out.println("Тор оглушил босса----------------------");
        } else {
            bossDamage = 50;
        }
    }


    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage; // heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(12) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND --------------------");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + "); " + bossDefenceType);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " (" + heroesDamage[i] + ")");
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}
