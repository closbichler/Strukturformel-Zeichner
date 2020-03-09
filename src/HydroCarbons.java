public enum HydroCarbons {
    //Used for storing the Carbon Count of the HydroCarb

    Meth(1), Eth(2), Prop(3),
    But(4), Pent(5), Hex(6),
    Hept(7), Oct(8), Non(9),
    Dec(10), Undec(11), Dodec(12),
    Tridec(13), Tetradec(14), Pentadec(15),
    Hexadec(16), Heptadec(17), Octadec(18),
    Nonadec(19), Eicos(20), Heneicos(21),
    Docos(22), Tricos(23), Tetracos(24),
    Pentacos(25), Hexacos(26), Heptacos(27),
    Octacos(28), Nonacos(29), Tricont(30);

    //Found those Names for HydroCarbons from 1 to 30 on the website
    // https://www.internetchemie.info/chemie-lexikon/stoffgruppen/a/alkane.php

    //Storing the carbon amout as integer
    private int carbon_count;

    HydroCarbons(int carbon_count) {
        this.carbon_count = carbon_count;
    }

    public int getValue() {
        return carbon_count;
    }
}
