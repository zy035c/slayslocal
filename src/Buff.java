// continuous status on a player character
// not implemented yet

import core.Player;

class Buff {

    String name;
    Player owner;

    Buff(String name_, Player player) {
        this.name = name_;
        this.owner = player;
    }
}