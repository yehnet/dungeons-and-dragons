package PresentationLayer;

import BusinessLayer.*;

public class CLI extends Observer {
    private GameRunner runner;
    public CLI(String path, boolean deterministic) {
        runner = new GameRunner(this,path, deterministic);
        choosePlayer();
    }

    public void run(){
        runner.run();
    }

    private void choosePlayer(){
        String selectPlayer = "Select player:\n" +
                "1. Jon Snow\t\tHealth: 300\t\tAttack damage: 30\t\tDefense: 4\n" +
                "\t\tLevel: 1\t\tExperience: 0/50\t\tAbility cooldown: 6\t\tRemaining: 0\n" +
                "2. The Hound\t\tHealth: 400\t\tAttack damage: 20\t\tDefense: 6\n" +
                "\t\tLevel: 1\t\tExperience: 0/50\t\tAbility cooldown: 4\t\tRemaining: 0\n" +
                "3. Melisandre\t\tHealth: 160\t\tAttack damage: 10\t\tDefense: 1\n" +
                "\t\tLevel: 1\t\tExperience: 0/50\t\tSpellPower: 15\t\tMana: 75/300\n" +
                "4. Thoros of Myr\t\tHealth: 250\t\tAttack damage: 25\t\tDefense: 3\n" +
                "\t\tLevel: 1\t\tExperience: 0/50\t\tSpellPower: 20\t\tMana: 37/150\n" +
                "5. Arya Stark\t\tHealth: 150\t\tAttack damage: 40\t\tDefense: 2\n" +
                "\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n" +
                "6. Bronn\t\tHealth: 250\t\tAttack damage: 35\t\tDefense: 3\n" +
                "\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n";
                //"7. Leeroy Jenkins\t\tHealth: 15\t\tAttack damage: 15\t\tDefense: 1200\n" +
                //"\t\tLevel: 1\t\tExperience: 0/50\t\tAbility cooldown: 1\t\tRemaining: 0";

        System.out.println(selectPlayer);
    }


    @Override
    public void onEvent(String msg) {
        System.out.println(msg);
    }
}
